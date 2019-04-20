package com.api_ctv.controller;

import static java.lang.Math.sqrt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.api_ctv.model.Item;
import com.api_ctv.model.ObjectItem;
import com.api_ctv.model.UserHistory;

public class DataProcess {

	public DataProcess() {
		// TODO Auto-generated constructor stub
	}

	public static double callCaculator(HashMap<String, Integer> hash) {
		double temp = 0;
		for (double i : hash.values()) {
			temp = temp + i * i;
		}
		return sqrt(temp);
	}

	public static double CuckooCalProcess(List<UserHistory> liCentroid, List<UserHistory> liPoint) {
		HashMap<String, Integer> hash = new HashMap<String, Integer>();

		for (UserHistory uh : liCentroid) {
			hash.put(uh.getMid(), uh.getN());
		}
		System.out.println();

		for (UserHistory uh : liPoint) {
			if (hash.containsKey(uh.getMid())) {
				hash.put(uh.getMid(), Math.abs(hash.get(uh.getMid()) - uh.getN()));
			} else {
				hash.put(uh.getMid(), uh.getN());
			}
		}

		return callCaculator(hash);
	}

	public static List<ObjectItem> relativePickup(List<ObjectItem> li, String owner, String point) {
		List<ObjectItem> list = new ArrayList<>();
		double radius = 0;
		for (int i = 0; i < li.size(); i++) {
			if (point.equals(li.get(i).getUid())==false) {
				radius = li.get(i).getRadius();
				break;
			}
		}

		System.out.println("--------------------" + point + ":"+ radius +"--------------------------");
		for (int i = 0; i < li.size(); i++) {		
			if (li.get(i).getUid().equals(point) == false) {
				if (li.get(i).getRadius() <= radius) {
//					list.add(new ObjectItem(li.get(i).getUid(), li.get(i).getRadius()));
					System.out.println("--" + point + ":" + radius + " | "+ li.get(i).getUid());
					radius = li.get(i).getRadius();
				} else if (li.get(i).getRadius() > radius)
					continue;
			}
		}

		for (int i = 0; i < li.size(); i++) {
			System.out.println(i+"/"+li.size());
			if (li.get(i).getUid().equals(point) == false) {
				if (li.get(i).getRadius() <= radius+1) {
					list.add(new ObjectItem(li.get(i).getUid(), li.get(i).getRadius()));
//					radius = li.get(i).getRadius();
					System.out.println(li.get(i).getUid() + "::" + point);
				} else if (li.get(i).getRadius() > radius)
					continue;
			}			
		}

		System.out.println("END");
		return list;
	}

	public static List<UserHistory> MID_contain(List<ObjectItem> li) throws ClassNotFoundException, SQLException {
		HashMap<String, Integer> hash = new HashMap<String, Integer>();
		for (int i = 0; i < li.size(); i++) {
			for (UserHistory uh : com.api_ctv.jdbc.Query.QueryItem(li.get(i).getUid())) {
				if (hash.containsKey(uh.getMid())) {
					hash.put(uh.getMid(), hash.get(uh.getMid()) + uh.getN());
				} else {
					hash.put(uh.getMid(), uh.getN());
				}
			}
		}

		List<UserHistory> temp = new ArrayList<>();

		for (Entry<String, Integer> entry : hash.entrySet()) {
			String key = entry.getKey();
			int value = entry.getValue();
			UserHistory uh = new UserHistory();
			uh.setMid(key);
			uh.setN(value);

			temp.add(uh);
		}

		if (li.size() != 0) {
			Collections.sort(temp, new Comparator<UserHistory>() {

				@Override
				public int compare(UserHistory o1, UserHistory o2) {
					if (o1.getN() > o2.getN())
						return -1;
					if (o1.getN() < o2.getN())
						return 1;
					return 0;
				}

			});
		}

		return temp;
	}
}