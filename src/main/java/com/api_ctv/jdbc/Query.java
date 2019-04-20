package com.api_ctv.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.api_ctv.model.UserHistory;

public class Query extends CommonSQL {
	static private String FAIL = "CONNECT DATABASE FAILED";

	public static boolean isDbConnected(Connection con) {
		try {
			if (!con.isClosed() || con != null) {
				return true;
			}
		} catch (SQLException ex) {
			Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}

	public static boolean checkConnect() throws SQLException, ClassNotFoundException {
		if (!isDbConnected(getMySQLConnection())) {
			System.err.println(FAIL);
			System.exit(0);
		}
		return true;
	}

	public static List<UserHistory> QueryItem(String uid_number) throws ClassNotFoundException, SQLException {
		List<UserHistory> li = new ArrayList<UserHistory>();

		Connection con = getMySQLConnection();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery("SELECT * FROM " + uid_number);
		while (rs.next()) {
			int index = rs.getInt(1);
			String uid = rs.getString(2);
			String mid = rs.getString(3);
			int n = rs.getInt(4);

			li.add(new UserHistory(index, uid, mid, n));
		}
		con.close();
		return li;
	}

	public static List<UserHistory> QueryItem(int uid_number) throws ClassNotFoundException, SQLException {
		List<UserHistory> li = new ArrayList<UserHistory>();

		Connection con = getMySQLConnection();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery("SELECT * FROM uid_" + uid_number);
		while (rs.next()) {
			int index = rs.getInt(1);
			String uid = rs.getString(2);
			String mid = rs.getString(3);
			int n = rs.getInt(4);

			li.add(new UserHistory(index, uid, mid, n));
		}

		con.close();
		return li;
	}

	public static int QueryNewestItem(String uid_number) throws ClassNotFoundException, SQLException {

		Connection con = getMySQLConnection();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery("SELECT * FROM distinct_uid WHERE uid = '" + uid_number + "'");
		rs.next();

		int id = rs.getInt(1);
		String uid = rs.getString(2);

		con.close();

		return id;
	}

	public static void insertUser(String uid, String mid, int n) throws SQLException {

		Connection con = null;
		try {
			con = getMySQLConnection();
			PreparedStatement stmt = null;
			stmt = con.prepareStatement("INSERT INTO draw(iddraw, uid, mid, n) VALUES (null,?, ?, ?)");
			stmt.setString(1, uid);
			stmt.setString(2, mid);
			stmt.setInt(3, n);
			stmt.executeUpdate();
			getMySQLConnection().close();
		} catch (SQLException ex) {
			Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public static int CreateAccount(String uid) throws SQLException {
		Connection con = null;
		try {
			con = getMySQLConnection();
			PreparedStatement stmt = null;
			stmt = con.prepareStatement("INSERT INTO distinct_uid(iddistinct_uid, uid) VALUES (null,?)");
			stmt.setString(1, uid);
			stmt.executeUpdate();

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int Unumber = QueryNewestItem(uid);

			stmt = null;
			stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS uid_" + Unumber
					+ " (idunit INT(11) NOT NULL AUTO_INCREMENT, uid VARCHAR(255),  mid VARCHAR(255),  n INT, PRIMARY KEY (idunit))");
			stmt.executeUpdate();

			stmt = null;
			stmt = con.prepareStatement(
					"INSERT INTO clustering(idclustering, uindex, have_radius, with_uindex) VALUES (null,?, ?, ?)");
			stmt.setString(1, "uid_" + Unumber);
			stmt.setDouble(2, 0);
			stmt.setString(3, "uid_2671");
			stmt.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
			return -1;
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
			return -1;
		} finally {
			if (con != null) {
				con.close();
			}
		}

		return 1;
	}

	public static void UpdateClustering(String centroid, String point, double radius) throws SQLException {
		Connection con = null;
		try {
			con = getMySQLConnection();
			PreparedStatement stmt = null;

			stmt = con.prepareStatement("UPDATE clustering SET with_uindex = ?, have_radius = ? WHERE uindex = ?");
			stmt.setString(1, centroid);
			stmt.setDouble(2, radius);
			stmt.setString(3, point);

			stmt.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public static void UpdateMusic(String uid, String mid, String uhex) throws ClassNotFoundException, SQLException {
		List<UserHistory> li = QueryItem(uid);
		boolean haveItem = false;
		int n = 1;

		for (UserHistory uh : li) {
			if (uh.getMid().equals(mid)) {
				haveItem = true;
				n = n + uh.getN();
				break;
			}
		}

		Connection con = null;
		try {
			con = getMySQLConnection();
			PreparedStatement stmt = null;
			if (haveItem) {
				stmt = con.prepareStatement("UPDATE " + uid + " SET n = ? Where mid = ?");
				stmt.setInt(1, n);
				stmt.setString(2, mid);
				stmt.executeUpdate();
			}

			if (!haveItem) {
				stmt = con.prepareStatement("INSERT INTO " + uid + " (idunit, uid, mid, n) VALUE(null,?,?,?)");
				stmt.setString(1, uhex);
				stmt.setString(2, mid);
				stmt.setInt(3, n);
				stmt.executeUpdate();
			}

		} catch (SQLException ex) {
			Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
	
	public static void InsertRow(String uid, String name, String mid, int n) throws ClassNotFoundException, SQLException {

		Connection con = null;
		try {
			con = getMySQLConnection();
			PreparedStatement stmt = null;

			stmt = con.prepareStatement("INSERT INTO " + uid + " (idunit, uid, mid, n) VALUE(null,?,?,?)");
			stmt.setString(1, name);
			stmt.setString(2, mid);
			stmt.setInt(3, n);
			stmt.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

//	public static void main (String[] args) throws ClassNotFoundException, SQLException {
//		Query(2);
//	}
}
