package idv.eason.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import idv.eason.utility.DBConnection;

public class SqliteTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Connection conn = null ;
        //System.out.println(con == null?"YES":"NO");
        Statement st = null ;
        ResultSet rs = null ;
        try {
            conn = DBConnection.getSQLiteConnection();
            st = conn.createStatement();
            rs = st.executeQuery("select * from friend");
            while(rs.next()){
                System.out.println(rs.getString("NAME"));
            }
        } catch (SQLException e) {
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
