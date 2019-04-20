package com.api_ctv.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api_ctv.entities.Cluster;
import com.api_ctv.entities.DistinctUid;
import com.api_ctv.entities.Music;
import com.api_ctv.model.ObjectItem;
import com.api_ctv.model.UserHistory;
import com.api_ctv.service.ClusterService;
import com.api_ctv.service.DistinctUidService;
import com.api_ctv.service.MusicService;
import com.api_ctv.service.MusicServiceImpl;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/cluster")
public class ClusterController {

	@Autowired
	ClusterService clusterService;

	@Autowired
	DistinctUidService distinctUidService;

	@Autowired
	MusicService musicService;

	@RequestMapping(value = "/wuid={withUindex}", method = RequestMethod.GET)
	public ResponseEntity<Object> f1(@PathVariable String withUindex) {
		List<Cluster> cluster = clusterService.findSpecifyCentroid(withUindex);
		return new ResponseEntity<Object>(cluster, HttpStatus.OK);
	}

	@RequestMapping(value = "/distinct", method = RequestMethod.GET)
	public ResponseEntity<Object> f2() {
		List<String> cluster = clusterService.findDistinctItem();
		return new ResponseEntity<Object>(cluster, HttpStatus.OK);
	}

	@RequestMapping(value = "/isExist/uid={uid}", method = RequestMethod.GET)
	public ResponseEntity<List<JSONObject>> f3(@PathVariable("uid") String uid) {
		int isExist = distinctUidService.UserIsExist(uid);

		if (isExist > 0) {
			List<JSONObject> entities = new ArrayList<JSONObject>();
			JSONObject entity = new JSONObject();
			entity.put("uid", "Valid!");
			entities.add(entity);

			return new ResponseEntity<List<JSONObject>>(entities, HttpStatus.OK);
		} else {
			List<JSONObject> entities = new ArrayList<JSONObject>();
			JSONObject entity = new JSONObject();
			entity.put("uid", "Not Valid!");
			entity.put("comment", "http://localhost:8080/cluster/create/uid=");
			entities.add(entity);

			return new ResponseEntity<List<JSONObject>>(entities, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/create/uid={uid}", method = RequestMethod.GET)
	public ResponseEntity<List<JSONObject>> f4(@PathVariable("uid") String uid) throws SQLException {
		int isSuccess = com.api_ctv.jdbc.Query.CreateAccount(uid);

		if (isSuccess == 1) {
			List<JSONObject> entities = new ArrayList<JSONObject>();
			JSONObject entity = new JSONObject();
			entity.put("uid", uid);
			entity.put("comment", "Successfully created all steps of CreateAccount");
			entities.add(entity);

			return new ResponseEntity<List<JSONObject>>(entities, HttpStatus.OK);
		} else {
			List<JSONObject> entities = new ArrayList<JSONObject>();
			JSONObject entity = new JSONObject();
			entity.put("uid", -1);
			entity.put("comment", "ERROR. Check log!!");
			entities.add(entity);

			return new ResponseEntity<List<JSONObject>>(entities, HttpStatus.OK);
		}
	}

	@SuppressWarnings("finally")
	@RequestMapping(value = "/uid={uid}", method = RequestMethod.GET)
	public ResponseEntity<List<JSONObject>> f5(@PathVariable("uid") String uid)
			throws ClassNotFoundException, SQLException {
		
		int isExist = distinctUidService.UserIsExist(uid);
		
//		Music m = musicService.getMIDbyTrackId(trackid);
//		String mid = m.getMusicid();

		if (isExist < 1){
			List<JSONObject> entities = new ArrayList<JSONObject>();
			JSONObject entity = new JSONObject();
			entity.put("uid", -1);
			entity.put("url", "http://localhost:8080/cluster/isExist/uid="+uid);
			entity.put("comment", "Go url link to check this account exist or not");
			entities.add(entity);

			return new ResponseEntity<List<JSONObject>>(entities, HttpStatus.BAD_REQUEST);
		}
		
		DistinctUid Duid = null;

		List<String> centroidList = clusterService.findDistinctItem();
		try {
			Duid = distinctUidService.getNumberbyUID(uid);
		} catch (Exception e) {
			List<JSONObject> entities = new ArrayList<JSONObject>();
			JSONObject entity = new JSONObject();
			entity.put("recommendation_list", "failed!");
			entities.add(entity);

			return new ResponseEntity<List<JSONObject>>(entities, HttpStatus.BAD_REQUEST);
		} finally {
			String pointNumber = "uid_" + Duid.getId() + "";
			Cluster cluster = clusterService.findWithUindexFromUindex(pointNumber);
			String centroidNumber = cluster.getWith_uindex();

//			if (trackid != null) com.api_ctv.jdbc.Query.UpdateMusic(pointNumber, mid, uid);

			List<UserHistory> liPoint = com.api_ctv.jdbc.Query.QueryItem(pointNumber);
			List<UserHistory> liCentroid = com.api_ctv.jdbc.Query.QueryItem(centroidNumber);
			double root_radius = DataProcess.CuckooCalProcess(liCentroid, liPoint);
			System.out.println(root_radius+":"+ centroidNumber+"-"+pointNumber);
			
			String final_centroid = centroidNumber;
			
			for (String centroid : centroidList) {
				if (centroid != centroidNumber) {
					List<UserHistory> li = com.api_ctv.jdbc.Query.QueryItem(centroid);
					double object_radius = DataProcess.CuckooCalProcess(li, liPoint);
					System.out.println(object_radius+":"+ centroid);
					if (object_radius < root_radius) {
						// Move point to centroid						
//						com.api_ctv.jdbc.Query.UpdateClustering(centroid, pointNumber, object_radius);
						System.out.println("bé nhất "+object_radius+":"+root_radius);
						
						root_radius = object_radius;
						final_centroid = centroid;
//						centroidNumber = centroid;
					}
				}
			}
			
			com.api_ctv.jdbc.Query.UpdateClustering(final_centroid, pointNumber, root_radius);
			
			List<Cluster> points = clusterService.findSpecifyCentroid(centroidNumber);
			List<ObjectItem> liOb = new ArrayList<>();
			List<UserHistory> Centroids = com.api_ctv.jdbc.Query.QueryItem(pointNumber);
			
			for (Cluster point : points) {
				List<UserHistory> Points = com.api_ctv.jdbc.Query.QueryItem(point.getUindex());
				
				double radius = DataProcess.CuckooCalProcess(Centroids, Points);
				liOb.add(new ObjectItem(point.getUindex(), radius));
			}

			if (liOb.size() > 0) {
				Collections.sort(liOb, new Comparator<ObjectItem>() {
					@Override
					public int compare(ObjectItem o1, ObjectItem o2) {
						if (o1.getRadius() > o2.getRadius())
							return 1;
						if (o1.getRadius() < o2.getRadius())
							return -1;
						return 0;
					}
				});
			}

			List<ObjectItem> li = DataProcess.relativePickup(liOb, centroidNumber, pointNumber);
			List<UserHistory> recommend_list = DataProcess.MID_contain(li);
			List<JSONObject> entities = new ArrayList<JSONObject>();

			JSONObject entityChild = new JSONObject();

			for (UserHistory uh : recommend_list) {
//				Music music = musicService.getTrackIdbyMID(uh.getMid());
//				if (music == null) {
//					
//				}else {				
//					Thread thread = new Thread() {
//						public void run(){
//							entityChild.put(music.getTrackid(), uh.getN());							
//						 }
//					};
//					
//					thread.start();
//				}
				Thread thread = new Thread() {
					public void run(){
						entityChild.put(uh.getMid(), uh.getN());
					 }
				};
				
				thread.start();
			}

			JSONObject entity = new JSONObject();
			entity.put("recommendation_list", entityChild);
			entity.put("elements", recommend_list.size());
			entities.add(entity);

			return new ResponseEntity<List<JSONObject>>(entities, HttpStatus.OK);
		}
	}
	
	@SuppressWarnings("finally")
	public List<JSONObject> bk_f5(String uid) throws ClassNotFoundException, SQLException {
		
		int isExist = distinctUidService.UserIsExist(uid);
		
		if (isExist < 1){
			List<JSONObject> entities = new ArrayList<JSONObject>();
			JSONObject entity = new JSONObject();
			entity.put("uid", -1);
			entity.put("url", "http://localhost:8080/cluster/isExist/uid="+uid);
			entity.put("comment", "Go url link to check this account exist or not");
			entities.add(entity);

			return entities;
		}
		
		DistinctUid Duid = null;

		List<String> centroidList = clusterService.findDistinctItem();
		try {
			Duid = distinctUidService.getNumberbyUID(uid);
		} catch (Exception e) {
			List<JSONObject> entities = new ArrayList<JSONObject>();
			JSONObject entity = new JSONObject();
			entity.put("recommendation_list", "failed!");
			entities.add(entity);

			return entities;
		} finally {
			String pointNumber = "uid_" + Duid.getId() + "";
			Cluster cluster = clusterService.findWithUindexFromUindex(pointNumber);
			String centroidNumber = cluster.getWith_uindex();

			List<UserHistory> liPoint = com.api_ctv.jdbc.Query.QueryItem(pointNumber);
			List<UserHistory> liCentroid = com.api_ctv.jdbc.Query.QueryItem(centroidNumber);
			double root_radius = DataProcess.CuckooCalProcess(liCentroid, liPoint);
			System.out.println(root_radius+":"+ centroidNumber+"-"+pointNumber);
			
			String final_centroid = centroidNumber;
			
			for (String centroid : centroidList) {
				if (centroid != centroidNumber) {
					List<UserHistory> li = com.api_ctv.jdbc.Query.QueryItem(centroid);
					double object_radius = DataProcess.CuckooCalProcess(li, liPoint);
					System.out.println(object_radius+":"+ centroid);
					if (object_radius < root_radius) {
						// Move point to centroid						
						root_radius = object_radius;
						final_centroid = centroid;
					}
				}
			}
			
			com.api_ctv.jdbc.Query.UpdateClustering(final_centroid, pointNumber, root_radius);
			
			List<Cluster> points = clusterService.findSpecifyCentroid(centroidNumber);
			List<ObjectItem> liOb = new ArrayList<>();
			List<UserHistory> Centroids = com.api_ctv.jdbc.Query.QueryItem(pointNumber);
			
			for (Cluster point : points) {
				List<UserHistory> Points = com.api_ctv.jdbc.Query.QueryItem(point.getUindex());
				
				double radius = DataProcess.CuckooCalProcess(Centroids, Points);
				liOb.add(new ObjectItem(point.getUindex(), radius));
			}

			if (liOb.size() > 0) {
				Collections.sort(liOb, new Comparator<ObjectItem>() {
					@Override
					public int compare(ObjectItem o1, ObjectItem o2) {
						if (o1.getRadius() > o2.getRadius())
							return 1;
						if (o1.getRadius() < o2.getRadius())
							return -1;
						return 0;
					}
				});
			}

			List<ObjectItem> li = DataProcess.relativePickup(liOb, centroidNumber, pointNumber);
			List<UserHistory> recommend_list = DataProcess.MID_contain(li);
			List<JSONObject> entities = new ArrayList<JSONObject>();

			JSONObject entityChild = new JSONObject();

			for (UserHistory uh : recommend_list) {
				Thread thread = new Thread() {
					public void run(){
						entityChild.put(uh.getMid(), uh.getN());
					 }
				};
				
				thread.start();
			}

			JSONObject entity = new JSONObject();
			entity.put("recommendation_list", entityChild);
			entity.put("elements", recommend_list.size());
			entities.add(entity);

			return entities;
		}
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "/uid={uid}/trackid={trackid}", method = RequestMethod.GET)
	public ResponseEntity f6(@PathVariable("uid") String uid, @PathVariable("trackid") String trackid)
			throws ClassNotFoundException, SQLException {
		
		int isExist = distinctUidService.UserIsExist(uid);
		
		Music m = musicService.getMIDbyTrackId(trackid);
		String mid = m.getMusicid();

		if (isExist < 1){
			List<JSONObject> entities = new ArrayList<JSONObject>();
			JSONObject entity = new JSONObject();
			entity.put("uid", -1);
			entity.put("url", "http://localhost:8080/cluster/isExist/uid="+uid);
			entity.put("comment", "Go url link to check this account exist or not");
			entities.add(entity);

			return new ResponseEntity<List<JSONObject>>(entities, HttpStatus.BAD_REQUEST);
		}
		
		DistinctUid Duid = null;

		List<String> centroidList = clusterService.findDistinctItem();
		try {
			Duid = distinctUidService.getNumberbyUID(uid);
		} catch (Exception e) {
			List<JSONObject> entities = new ArrayList<JSONObject>();
			JSONObject entity = new JSONObject();
			entity.put("recommendation_list", "failed!");
			entities.add(entity);

			return new ResponseEntity<List<JSONObject>>(entities, HttpStatus.BAD_REQUEST);
		} finally {
			String pointNumber = "uid_" + Duid.getId() + "";
			Cluster cluster = clusterService.findWithUindexFromUindex(pointNumber);
			String centroidNumber = cluster.getWith_uindex();

			if (trackid != null) com.api_ctv.jdbc.Query.UpdateMusic(pointNumber, mid, uid);

			List<UserHistory> liPoint = com.api_ctv.jdbc.Query.QueryItem(pointNumber);
			List<UserHistory> liCentroid = com.api_ctv.jdbc.Query.QueryItem(centroidNumber);

			double root_radius = DataProcess.CuckooCalProcess(liCentroid, liPoint);
			
			for (String centroid : centroidList) {
				System.out.println(root_radius +":"+centroid);
				if (centroid != centroidNumber) {
					List<UserHistory> li = com.api_ctv.jdbc.Query.QueryItem(centroid);
					double object_radius = DataProcess.CuckooCalProcess(li, liPoint);					
					if (object_radius < root_radius) {
						// Move point to centroid
						System.out.println("-->"+ object_radius +":"+centroid);
						com.api_ctv.jdbc.Query.UpdateClustering(centroid, pointNumber, object_radius);
						centroidNumber = centroid;
					}
				}
			}
			
			return new ResponseEntity("OK", HttpStatus.OK);
		}
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "/refactor", method = RequestMethod.GET)
	public ResponseEntity f7()
			throws ClassNotFoundException, SQLException {
		
		List<Cluster> cluster_list = clusterService.GetAll_Cluster();
		List<String> centroidList = clusterService.findDistinctItem();
		
		for (Cluster c : cluster_list) {
			System.out.println(c.getIdcluster()+ "-----------------------------------------");			
			String Duid = c.getUindex();
			
			
			String pointNumber = Duid;
			Cluster cluster = clusterService.findWithUindexFromUindex(pointNumber);
			String centroidNumber = cluster.getWith_uindex();

			List<UserHistory> liPoint = com.api_ctv.jdbc.Query.QueryItem(pointNumber);
			List<UserHistory> liCentroid = com.api_ctv.jdbc.Query.QueryItem(centroidNumber);

			double root_radius = DataProcess.CuckooCalProcess(liCentroid, liPoint);									
			String final_centroid = centroidNumber;
			
			for (String centroid : centroidList) {
				if (centroid != centroidNumber) {
					List<UserHistory> li = com.api_ctv.jdbc.Query.QueryItem(centroid);
					double object_radius = DataProcess.CuckooCalProcess(li, liPoint);
//					System.out.println(object_radius+":"+ centroid);
					if (object_radius < root_radius) {
						// Move point to centroid						
//						com.api_ctv.jdbc.Query.UpdateClustering(centroid, pointNumber, object_radius);
//						System.out.println("bé nhất ----------------------------------------------- "+object_radius+":"+root_radius);
						
						root_radius = object_radius;
						final_centroid = centroid;
//						centroidNumber = centroid;
					}
				}
			}
			
			com.api_ctv.jdbc.Query.UpdateClustering(final_centroid, pointNumber, root_radius);						
		}
		
					
			return new ResponseEntity("OK", HttpStatus.OK);
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "/uid_history={uid}", method = RequestMethod.GET)
	public ResponseEntity<List<JSONObject>> f7(@PathVariable("uid") String uid)
			throws ClassNotFoundException, SQLException {
		
		int isExist = distinctUidService.UserIsExist(uid);
		
//		Music m = musicService.getMIDbyTrackId(trackid);
//		String mid = m.getMusicid();

		if (isExist < 1){
			List<JSONObject> entities = new ArrayList<JSONObject>();
			JSONObject entity = new JSONObject();
			entity.put("uid", -1);
			entity.put("url", "http://localhost:8080/cluster/isExist/uid="+uid);
			entity.put("comment", "Go url link to check this account exist or not");
			entities.add(entity);

			return new ResponseEntity<List<JSONObject>>(entities, HttpStatus.BAD_REQUEST);
		}
		
		DistinctUid Duid = null;

		List<String> centroidList = clusterService.findDistinctItem();
		try {
			Duid = distinctUidService.getNumberbyUID(uid);
		} catch (Exception e) {
			List<JSONObject> entities = new ArrayList<JSONObject>();
			JSONObject entity = new JSONObject();
			entity.put("recommendation_list", "failed!");
			entities.add(entity);

			return new ResponseEntity<List<JSONObject>>(entities, HttpStatus.BAD_REQUEST);
		} finally {
			String pointNumber = "uid_" + Duid.getId() + "";			
		
			List<UserHistory> recommend_list = com.api_ctv.jdbc.Query.QueryItem(pointNumber);
			List<JSONObject> entities = new ArrayList<JSONObject>();

			JSONObject entityChild = new JSONObject();

			for (UserHistory uh : recommend_list) {
				Music music = musicService.getTrackIdbyMID(uh.getMid());
				if (music == null) {
					
				}else {				
					Thread thread = new Thread() {
						public void run(){
							entityChild.put(music.getTrackid(), uh.getN());
						 }
					};
					
					thread.start();
				}
			}

			JSONObject entity = new JSONObject();
			entity.put("recommendation_list", entityChild);
			entity.put("elements", recommend_list.size());
			entities.add(entity);

			return new ResponseEntity<List<JSONObject>>(entities, HttpStatus.OK);
		}
	}
	
    public List<String> get_data_from_url(String user) throws MalformedURLException, IOException, JSONException {

        URL url = new URL("localhost:8090/cluster/create/uid=" + user);
        List<String> li = new ArrayList<String>();
        URLConnection connection = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String inputLine;
        JSONArray jsonarray = null;
        while ((inputLine = in.readLine()) != null) {
            jsonarray = new JSONArray(inputLine.toString());
        }        

        in.close();

        return null;
    }
    
//    List<Integer> liValue = new ArrayList<>();
//    public List<String> get_data(String user) throws MalformedURLException, IOException, JSONException {
//    	
//    	liValue = new ArrayList<>();
//        URL url = new URL("http://localhost:8090/cluster/uid=" + user);
//        List<String> li = new ArrayList<String>();
//        URLConnection connection = url.openConnection();
//        BufferedReader in = new BufferedReader(new InputStreamReader(
//                connection.getInputStream()));
//        String inputLine;
//        List<JSONObject> jsonarray = bk_f5(user);
//
//        for (int i = 0; i < jsonarray.size(); i++) {
//            JSONObject jsonobject = jsonarray.get(i);
//            jsonarray = new JSONArray("[" + jsonobject.getString("recommendation_list") + "]");
//            for (int j = 0; j < jsonarray.length(); j++) {
//                jsonobject = jsonarray.getJSONObject(i);
//                Iterator<String> keys = jsonobject.keys();
//                while (keys.hasNext()) {
//                    String key = keys.next();                    
//                    li.add(key);
//                    liValue.add(Integer.parseInt(jsonobject.getString(key)));
//                }
//                
//            }
//        }
//
//        in.close();
//
//        return null;
//    }
	
//	@SuppressWarnings("finally")
//	@RequestMapping(value = "/test", method = RequestMethod.GET)
//	public ResponseEntity<List<JSONObject>> f8() throws ClassNotFoundException, SQLException {
//
//		List<Double> sati = new ArrayList<>();
//		
//		int maximum = 45799;
//		int minimum = 1;
//		
//		List<Integer> li = new ArrayList<>();		
//		
//		while (true) {
//			Random rn = new Random();
//			
//			int range = maximum - minimum + 1;
//			int randomNum =  rn.nextInt(range) + minimum;
//			
//			if (li.contains(randomNum) == false) {
//				li.add(randomNum);
//				System.out.println(li);
//			}
//			if (li.size() == 2) break;
//		}
//		
//		for (int i : li) {
//			try {
//				get_data_from_url("n"+i);
//			} catch (MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		int start_id = 45809;
//		
//		for (int i = 0; i< li.size(); i++) {
//			List<UserHistory> list_history = com.api_ctv.jdbc.Query.QueryItem("uid_"+li.get(i));
//			int total = 0;
//			for (int j = 0 ; j< list_history.size(); j++) {
//				com.api_ctv.jdbc.Query.InsertRow("uid_"+start_id++, "n"+li.get(i), list_history.get(j).getMid(), list_history.get(j).getN());
//				if (j > 6) {
//					int n = 0;
//					try {
//						List<String>liName = get_data("n"+li.get(i));						
//						for (UserHistory uh : list_history) {
//							if (liName.contains(uh.getMid())) {
//								n++;
//							}
//						}
//					} catch (MalformedURLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}finally {
//						total = total + n;
//					}
//				}				
//			}
//			
//			double s = (double) total/(double) list_history.size() * 100.0;
//			System.out.println("Satifaction of uid_"+li.get(i)+"(uid_"+(start_id-1)+"): "+ s);
//			
//			sati.add(s);
//		}
//		
//		
//		return new ResponseEntity(sati, HttpStatus.OK);
//	}
	
}
