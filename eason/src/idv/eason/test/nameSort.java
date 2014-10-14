package idv.eason.test;

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
    	List a = new ArrayList();
    	a.add("TTT");
    	a.add("abc");
    	a.add("test");
    	String[] b = (String[]) a.toArray(new String[a.size()]);
    	System.out.println("OK");
    }

}
