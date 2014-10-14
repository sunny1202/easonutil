package idv.eason.test;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class URLtest {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        //URL url = new URL("http://www.google.com/finance?q=2028.TW");
        URL url = new URL("http://tw.money.yahoo.com/currency_exc_result?amt=1&from=AUD&to=TWD");

//        URLConnection urlc=url.openConnection();
        HttpURLConnection urlc = (java.net.HttpURLConnection)url.openConnection();
        urlc.setRequestProperty("user-agent", "Mozilla/4.0 (compatible¡F MSIE 6.0¡F Windows NT 5.1)");
        //urlc.setRequestProperty("Accept-Language","zh-tw,en-us;q=0.7,en;q=0.3");
        //urlc.setRequestProperty("Accept-Charse","Big5,utf-8;q=0.7,*;q=0.7");
        urlc.setRequestMethod("POST");
        BufferedReader is = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
        String str;
        StringBuffer text = new StringBuffer();
        String _companyId = "";
        String price = "";
        while((str = is.readLine())!=null){
//            if(_companyId != ""){
//                if(str.indexOf("\"ref_"+_companyId.trim()+"_l\"") > 0)
//                price = str.substring(str.indexOf(">")+1,str.lastIndexOf("<"));
//            }
//            if(str.indexOf("_companyId") > 0)
//                _companyId = str.substring(str.indexOf("=")+1,str.indexOf(";"));
            
            text.append(str+"\n");
        }
        is.close();
//        System.out.println(price);
        System.out.println(text.toString());
//        Object[] comID = _companyId.toArray();
//        for(int i=0 ; i < comID.length ; i++){
//            System.out.println((String)comID[i]);
//        }
    }

}
