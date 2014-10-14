package idv.eason.test;

import java.io.*;

public class RuntimeTest {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args){
//        String mag[] = new String[4];
//        mag[0] = "cmd.exe";
//        mag[1] = "net use B: \\\\192.168.30.92\\D$ S100082388 /user:administrator";
//        mag[2] = "mkdir B:\\EASON";
//        mag[3] = "net use B /delete";
//        //mag[1] = "pause";
//        Runtime runtime = Runtime.getRuntime();
//        Process process = null;
//        String line = null;
//        InputStream is = null;
//        InputStreamReader isr = null;
//        BufferedReader br = null;
//        boolean result=false;
//        try {
//            process = runtime.exec(mag);
//            is = process.getInputStream();
//            isr = new InputStreamReader(is);
//            br = new BufferedReader(isr);
//            while ( (line = br.readLine()) != null) {
//               System.out.println(line);
//               System.out.flush();
//            }
//            mag[1] = "net use B /delete /PERSISTENT:YES" ;
//            process = runtime.exec(mag);
//            is = process.getInputStream();
//            isr = new InputStreamReader(is);
//            br = new BufferedReader(isr);
//            while ( (line = br.readLine()) != null) {
//               System.out.println(line);
//               System.out.flush();
//            }

//            is.close();
//            isr.close();
//            br.close();
        try{
            String cmd[] = {"cmd.exe","net use K \\\\192.168.34.55\\C$ 671202 /user:eason"};
//            cmd[0] = "cmd.exe";
//            cmd[1] = "cd c:\\";
//            cmd[2] = "mkdir EASON";
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(cmd);
           // BufferedWriter buffOut = new BufferedWriter(
            //                         new OutputStreamWriter(
           //                                 process.getOutputStream()));
            BufferedReader buffIn = new BufferedReader(
                                    new InputStreamReader(
                                            process.getInputStream()));
            BufferedReader buffErr = new BufferedReader(
                    new InputStreamReader(
                            process.getErrorStream()));
            //buffOut.write("//Some String");
            //buffOut.flush(); //Ensure that the output reaches the process
     
            String s;
     
//            if((s=buffIn.readLine())!= null)
//                System.out.println(s);
//            
            if((s=buffErr.readLine()) != null)
                System.out.println(s);
     
            //buffOut.close();
            buffIn.close();
            buffErr.close();
            int exitVal = process.waitFor();
            System.out.println("ExitValue: " + exitVal);  
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }

}
