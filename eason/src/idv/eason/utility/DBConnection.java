package idv.eason.utility;

import java.io.*;
import java.util.*;
import java.sql.*;
import org.sqlite.*;

public class DBConnection {
	private static Properties ps ;
	
	/**
	 * 讀取Propert檔
	 *
	 */
	private static void loadPropert(){
		ps = new Properties();
		try {
			ps.load(new FileInputStream("./config.ini"));
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param key
	 * @return value
	 */
	private static String getConfigValue(String key){
		return ps.getProperty(key);
	}
	
	public static Connection getConnection(){
		loadPropert();
		String driver = getConfigValue("driver");
		String url = getConfigValue("url");
		String user = getConfigValue("user");
		String password = getConfigValue("password");
		Connection con = null ;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,password);
			if(con == null || con.isClosed()){
				System.out.println("關閉資料庫連線");
				con.close();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("找不到驅動程式類別");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return con ;
	}
    public static Connection getSQLiteConnection(){
        loadPropert();
        String driver = getConfigValue("SQLiteDriver");
        String url = getConfigValue("SQLiteUrl");
        Connection con = null ;
        try {
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url);
            if(con == null || con.isClosed()){
                System.out.println("關閉資料庫連線");
                con.close();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("找不到驅動程式類別");
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   
        return con ;
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null ;
		Statement st = null ;
		ResultSet rs = null ;
		try {
			conn = DBConnection.getConnection();
            //conn = DBConnection.getSQLiteConnection();
			st = conn.createStatement();
            //System.out.println(conn.getTransactionIsolation());
            StringBuffer sb = new StringBuffer();
            StringBuffer tofile = new StringBuffer();
            sb.append("select * from slflib/secam where langtype='ZH_TW'");
            System.out.println(sb.toString());
			rs = st.executeQuery(sb.toString());
            //rs = st.executeQuery("select b.categname,sum(transamount) transamount from checkingaccount_v1 A left join category_v1 B on b.categid=a.categid where accountid='1' and transdate between '2008-01-01' and '2009-12-31' group by b.categname");
			while(rs.next()){
				tofile.append("insert into slflib/secam values('ZH_TW','");
				tofile.append(rs.getString("MSGID")+"','"+rs.getString("LANGDESC")+"',");
				tofile.append("1020815,144500,'YSC',0,0,'');");
				tofile.append("\r\n");
			}
			dejc2File.toFile("C:\\",tofile, "secam.txt");
			//FTP_DU.put("./temp", "", "SLDLVF_20120207.txt");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
    
}
