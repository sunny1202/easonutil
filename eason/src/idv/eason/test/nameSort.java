package idv.eason.test;

import java.util.*;

public class nameSort {
    String second[] = {"��","��","�o","��","�t","��","��","�O","�P"
                        ,"�R","�T","�@","�","��","�","��"};
    String third[] = {"��","�_","��","�b","��","��","�n","��","�z","��","��","�","߻","�z"};
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
//        nameSort name = new nameSort();
//        System.out.println("�ĤT�r������16��");
//        for(int i = 0 ; i < name.second.length ; i++){
//            for(int j = 0 ; j < name.second.length ; j++){
//                if(i!=j)
//                    System.out.print(name.second[i]+name.second[j]+",");
//            }
//            System.out.println();
//        }
//        System.out.println("�ĤT�r������15��");
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
