import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class genNumber {
    static String BKCD ;
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        getBKCD("CBEAA");
    }
    
    public static void getBKCD(String bkcd){
        char first=55;
        int seq = 0;
        boolean flag = true ;
        DecimalFormat df_3 = new DecimalFormat("000");
        List al = new ArrayList();
        al.add("ttt");
        al.add("bbb");
        al.add("ccc");
        String[] usr = null ;
        try{
        usr = (String[])al.toArray(new String[0]);
        }catch(Exception e){
            
        }
        for(int i=0 ; i < usr.length ; i++)
            System.out.println(usr[i]);
        do{
            if(seq < 999){
                seq++;
                //if(treeMap.get(bkcd+df_3.format(seq))==null){
                    BKCD=bkcd+df_3.format(seq);
                    System.out.println(BKCD);
                    //flag = false ;
               // }
            }else{
                seq++;
                if(seq < 3600){ //max Z99
                    first = (char)(55+Integer.parseInt(String.valueOf(seq).substring(0,String.valueOf(seq).length()-2)));
                    BKCD = bkcd+String.valueOf(first)+String.valueOf(seq).substring(String.valueOf(seq).length()-2);
                    //flag = false;
                    System.out.println(BKCD);
                }
            }
        }while(seq < 3600);
    }
}
