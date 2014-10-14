package idv.eason.RS232;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TwoWaySerialComm
{
    public TwoWaySerialComm()
    {
        super();
    }
    
    void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() ){
            System.out.println("Error: Port is currently in use");
        }else{
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            byte[] messageString = new byte[13];
            messageString[0]=0x02;
            messageString[1]=0x30;//0
            messageString[2]=0x30;//0
            messageString[3]=0x30;//0
            messageString[4]=0x41;//A
            messageString[5]=0x31;//0
            messageString[6]=0x30;//0
            messageString[7]=0x31;//1
            messageString[8]=0x03;
            messageString[9]=0x36;//6
            messageString[10]=0x36;//5
            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600,SerialPort.DATABITS_7,SerialPort.STOPBITS_1,SerialPort.PARITY_EVEN);
                
                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();
                
                (new Thread(new SerialReader(in))).start();
//                (new Thread(new SerialWriter(out))).start();
                try {
                	out.write(messageString);
                } catch (IOException e) {}

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }     
    }
    
    /** */
    public static class SerialReader implements Runnable 
    {
        InputStream in;
        
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        
        public void run ()
        {
        	try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            byte[] buffer = new byte[20];
            int len = -1;
            //                while ( ( len = this.in.read(buffer)) > -1 )
//                {
//                    System.out.print(new String(buffer,0,len));
//                }
			 for (int i = 0 ; i < buffer.length ; i++){
			     System.out.println(buffer[i]);
			 }            
        }
    }

    /** */
    public static class SerialWriter implements Runnable 
    {
        OutputStream out;
        byte[] messageString = new byte[13] ;
        public SerialWriter ( OutputStream out )
        {

        	messageString[0]=0x02;
            messageString[1]=0x37;
            messageString[2]=0x30;
            messageString[3]=0x35;
            messageString[4]=0x30;
            messageString[5]=0x38;
            messageString[6]=0x03;
            messageString[7]=0x30;
            messageString[8]=0x32;
        	this.out = out;
        }
        
        public void run ()
        {
            try
            {                
//                int c = 0;
//                while ( ( c = System.in.read()) > -1 )
//                {
//                    this.out.write(c);
//                }    
            	this.out.write(messageString);
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }
    
    public static void main ( String[] args )
    {
        try
        {
            (new TwoWaySerialComm()).connect("COM1");
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}