package idv.eason.test;

import java.util.*;
import java.io.*;
 
public class Runtime1 {
    public static void main(String args[]) {
        exec("javac");
    }
 
    public static boolean exec(String command) { try {            
        String osName = System.getProperty("os.name" );
        String[] cmd = { "cmd.exe", "/C", command } ;
        if (osName.equals( "Windows 95" ))
            cmd[0] = "command.exe";            
        Runtime rt = Runtime.getRuntime();
        System.out.println("Execute " + cmd[0] + " " + cmd[1] + " " + cmd[2]);
        Process proc = rt.exec(cmd);
        // any error message?
        StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");                        
        // any output?
        StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");                
        // kick them off
        errorGobbler.start();
        outputGobbler.start();
        // any error???
        int exitVal = proc.waitFor();
        System.out.println("ExitValue: " + exitVal);        
        return true;
    } catch (Throwable t) { t.printStackTrace(); return false;} }
}
 
class StreamGobbler extends Thread {
    InputStream is;
    String type;
 
    StreamGobbler(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }
 
    public void run() { try {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line=null;
        while ( (line = br.readLine()) != null)
            System.out.println(type + ">" + line);    
    } catch (IOException ioe) { ioe.printStackTrace(); }}
}