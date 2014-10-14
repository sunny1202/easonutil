package idv.eason.utility;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class rpgleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = null ;
		Statement st = null ;
		ResultSet rs = null ;
		try {
			conn = DBConnection.getConnection();
			CallableStatement cs = null;
			cs = conn.prepareCall("CALL SPBKBD2 (?,?,?,?,?,?,?,?)");
			cs.setString(1, "70642219");
			cs.setString(2, "");
			cs.registerOutParameter(3, Types.DECIMAL);
			cs.registerOutParameter(4, Types.DECIMAL);
			cs.registerOutParameter(5, Types.DECIMAL);
			cs.registerOutParameter(6, Types.DECIMAL);
			cs.registerOutParameter(7, Types.DECIMAL);
			cs.registerOutParameter(8, Types.DECIMAL);
			cs.execute();
			System.out.println(cs.getBigDecimal(3));
			System.out.println(cs.getBigDecimal(4));
			System.out.println(cs.getBigDecimal(5));
			System.out.println(cs.getBigDecimal(6));
			System.out.println(cs.getBigDecimal(7));
			System.out.println(cs.getBigDecimal(8));
		}catch (Exception e){
			System.out.println(e);
		} finally {
			try {
//				rs.close();
//				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
