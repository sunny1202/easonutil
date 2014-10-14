package idv.eason.RS232;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * This version of the TwoWaySerialComm example makes use of the 
 * SerialPortEventListener to avoid polling.
 *
 */
public class SerialPortRW {
	private static SerialPort serialPort;
	private static InputStream in;
	private static OutputStream out;
	private static HashMap<String, String> tempertrue ;
	
	public SerialPortRW()
    {
        super();
    }
	HashMap connect ( String portName,byte[] write,boolean isPLC ) throws Exception {
		
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort )
            {
                serialPort = (SerialPort) commPort;
                if(isPLC){
                    serialPort.setSerialPortParams(9600,
                    		SerialPort.DATABITS_7,
                    		SerialPort.STOPBITS_1,
                    		SerialPort.PARITY_EVEN);
                }else{
	                serialPort.setSerialPortParams(115200,
	                		SerialPort.DATABITS_8,
	                		SerialPort.STOPBITS_1,
	                		SerialPort.PARITY_NONE);
                }
                in = serialPort.getInputStream();
                out = serialPort.getOutputStream();
                
                tempertrue = new HashMap();
                Thread SW = new Thread(new SerialWriter(out,write));
                SW.start();

                serialPort.addEventListener(new SerialReader(in,isPLC));
                serialPort.notifyOnDataAvailable(true);
                
            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
        
        return tempertrue ;
    }
	
	static void close() throws Exception{
		if(serialPort != null){
			out.close();
			in.close();
		}
		serialPort.close();
	}
    
    /**
     * Handles the input coming from the serial port. A new line character
     * is treated as the end of a block in this example. 
     */
    public static class SerialReader implements SerialPortEventListener 
    {
        private InputStream in;
        private byte[] buffer = new byte[1024];
        private boolean isPLC ;
        
        public SerialReader ( InputStream in,boolean isPLC )
        {
            this.in = in;
            this.isPLC = isPLC ;
        }
        
        public void serialEvent(SerialPortEvent arg0) {
            int data;
            try{
            	boolean flag = false ;
                int len = 0;
                while ( ( data = in.read()) > -1 ){
                    if ( data == '\n' ) {
                        break;
                    }
                    if(isPLC){
                    	if(data == 0x02){
                    		flag = true ;
                    		continue ;
                    	}else if(data == 0x03){
                    		break ;
                    	}
                    	if(flag){
                    		buffer[len++] = (byte) data;
                    	}
                    }else{
                    	if(data > 0x00 && data < 0x7F){
                    		buffer[len++] = (byte) data;
                    	//System.out.println((int)data);
                    	}
                    }
                }
                if(len > 0){
                String temp = new String(buffer,0,len) ;
                
//                System.out.print(new String(buffer,0,len));
                if(isPLC){
                	tempertrue.put("PLC", temp);
                	serialPort.close();
                }else{
                	if(tempertrue.size() == 3)
                		serialPort.close();
                	else{
                		if(tempertrue.get(temp.substring(temp.indexOf("[")+1, temp.indexOf("]"))) == null)
                			tempertrue.put(temp.substring(temp.indexOf("[")+1, temp.indexOf("]")), temp.substring(0, temp.indexOf("[")));
                		//塞滿資料
                		tempertrue.put("2", "22.2 22.2%");
                		tempertrue.put("3", "33.3 33.3%");
                	}
                }
                }
                //serialPort.close();
            }
            catch ( IOException e )
            {
                e.printStackTrace();
                System.exit(-1);
            }             
        }

    }

    /** */
    public static class SerialWriter implements Runnable {
        OutputStream out;
        byte[] write ;
        public SerialWriter ( OutputStream out,byte[] write )
        {
            this.out = out;
            this.write = write ;
        }
        
        public void run (){
            try{                
            	this.out.write(write);
            }catch ( IOException e ){
                e.printStackTrace();
                System.exit(-1);
            }            
        }
    }
    
    
    public static void main ( String[] args ) {
    	byte[] message = new byte[13];
        try{
        	message[0]=0x02;
        	message[1]=0x30;//0
        	message[2]=0x30;//0
        	message[3]=0x30;//0
        	message[4]=0x41;//A
        	message[5]=0x30;//0
        	message[6]=0x30;//0
        	message[7]=0x33;//3
        	message[8]=0x03;//30+30+30+41+30+30+33+03=165 取後2碼
        	message[9]=0x36;//6
        	message[10]=0x37;//7
        	SerialPortRW SRW = new SerialPortRW();
            HashMap hm = SRW.connect("COM4",message,false);
            
//            //PLC
//            while(true){
//            	if(hm.get("PLC")!=null){
//            		System.out.println((String)hm.get("PLC"));
//            		break ;
//            	}
//            }
            //tempertrue
            Thread.sleep(500);
            while(true){
            	if(hm.size() == 3){
            		Object[] seq = (Object[])hm.keySet().toArray();
            		for(int i = 0 ; i < seq.length ; i++){
            			System.out.println((String)hm.get(seq[i]));
            		}
            		break;
            	}
            }
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
