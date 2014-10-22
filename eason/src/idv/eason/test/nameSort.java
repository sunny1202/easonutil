package idv.eason.test;

import idv.eason.utility.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class nameSort {
    String second[] = {"¾§","¾å","¿o","¿¢","ët","¿Ô","¿Õ","ÀO","ÀP"
                        ,"ÀR","»T","½@","æ¢","¼ä","ã¸","¾ì"};
    String third[] = {"»ö","¼_","ùÜ","¼b","¼Ç","¼ü","½n","º½","¼z","½Ë","¸«","è®","ß»","¼z"};
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
//        nameSort name = new nameSort();
//        System.out.println("²Ä¤T¦rµ§¹º¬°16ªº");
//        for(int i = 0 ; i < name.second.length ; i++){
//            for(int j = 0 ; j < name.second.length ; j++){
//                if(i!=j)
//                    System.out.print(name.second[i]+name.second[j]+",");
//            }
//            System.out.println();
//        }
//        System.out.println("²Ä¤T¦rµ§¹º¬°15ªº");
//        for(int i = 0 ; i < name.second.length ; i++){
//            for(int j = 0 ; j < name.third.length ; j++){
//                System.out.print(name.second[i]+name.third[j]+",");
//            }
//            System.out.println();
//        }
//    	List a = new ArrayList();
//    	a.add("TTT");
//    	a.add("abc");
//    	a.add("test");
//    	String[] b = (String[]) a.toArray(new String[a.size()]);
//    	System.out.println("OK");
    	
    	Connection conn = null ;
		Statement st = null ;
		ResultSet rs = null ;
		try {
			conn = DBConnection.getConnection();
            //conn = DBConnection.getSQLiteConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select AHOPNO from slflib/SLMATH where substring(AHOPNO,1,3)='BR2'");
			List AHOPNO = new ArrayList();
			while(rs.next()){
				AHOPNO.add(rs.getString("AHOPNO"));
			}
			AHOPNO.add("BR200");
			String opno="BR2";
			int first = java.util.Collections.max(AHOPNO).toString().getBytes()[3];
			int second = java.util.Collections.max(AHOPNO).toString().getBytes()[4];
			do{
				opno = "BR2";
				if(first >= 48 && first <= 57){
					//first 0~9
					second++ ;
					if(second > 57 && second < 65){
						second = 65 ;
					}else if (second > 90) {
						first++;
						second = 48 ;
						if(first > 57)
							first = 65 ;
					}
				}else {
					//first A~Z
					second++ ;
					if(second > 57 && second < 65){
						second = 65 ;
					}else if(second > 90){
						first++ ;
						if(first > 90){
							first = 48 ;
						}
						second = 48 ;
					}
				}
				opno = opno + String.valueOf((char)first) + String.valueOf((char)second); 
				//System.out.println(opno);
			}while (Collections.frequency(AHOPNO, opno) > 0);
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
