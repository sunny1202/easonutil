package idv.eason.RS232;

/*
 * @(#)SimpleRead.java	1.12 98/06/25 SMI
 *
 * Copyright (c) 1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license 
 * to use, modify and redistribute this software in source and binary
 * code form, provided that i) this copyright notice and license appear
 * on all copies of the software; and ii) Licensee does not utilize the
 * software in a manner which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND
 * ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THE
 * SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS
 * BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES,
 * HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING
 * OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control
 * of aircraft, air traffic, aircraft navigation or aircraft
 * communications; or in the design, construction, operation or
 * maintenance of any nuclear facility. Licensee represents and
 * warrants that it will not use or redistribute the Software for such
 * purposes.
 */

import java.io.*;
import java.util.*;
//import javax.comm.*;
import gnu.io.* ;

public class ReadWrite_zb_plc extends Thread implements SerialPortEventListener {
    static CommPortIdentifier portId;
    static Enumeration portList;
    static byte[] messageString = new byte[13] ;
    private boolean isContinue = true;

    InputStream inputStream;
    OutputStream outputStream;
    SerialPort serialPort;
    Thread readThread;
    static boolean flag ;

    public static void main(String[] args) {
        portList = CommPortIdentifier.getPortIdentifiers();
        flag = true ;
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                 if (portId.getName().equals("COM1")) {
                //if (portId.getName().equals("/dev/term/a")) {
                	 ReadWrite_zb_plc reader = new ReadWrite_zb_plc();
                }
            }
        }
    }

    public ReadWrite_zb_plc() {

//        messageString[0]=0x02;
//        messageString[1]=0x31;//1
//        messageString[2]=0x30;//0
//        messageString[3]=0x30;//0
//        messageString[4]=0x41;//A
//        messageString[5]=0x32;//2
//        messageString[6]=0x30;//0
//        messageString[7]=0x31;//1
//        messageString[8]=0x46;//F
//        messageString[9]=0x46;//F
//        messageString[10]=0x03;//31+30+30+41+32+30+31+46+46+03=1F4
//        messageString[11]=0x46;
//        messageString[12]=0x34;

//        messageString[0]=0x02;
//        messageString[1]=0x30;//0
//        messageString[2]=0x30;//0
//        messageString[3]=0x30;//0
//        messageString[4]=0x41;//A
//        messageString[5]=0x30;//0
//        messageString[6]=0x30;//0
//        messageString[7]=0x31;//1
//        messageString[8]=0x03;//30+30+30+41+30+30+31+03=165 取後2碼
//        messageString[9]=0x36;//6
//        messageString[10]=0x35;//5
        //取得Y0~Y27
        messageString[0]=0x02;
        messageString[1]=0x30;//0
        messageString[2]=0x30;//0
        messageString[3]=0x30;//0
        messageString[4]=0x41;//A
        messageString[5]=0x30;//0
        messageString[6]=0x30;//0
        messageString[7]=0x33;//3
        messageString[8]=0x03;//30+30+30+41+30+30+33+03=165 取後2碼
        messageString[9]=0x36;//6
        messageString[10]=0x37;//7
        try {
            serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
        } catch (PortInUseException e) {}
        try {
        	outputStream = serialPort.getOutputStream();
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {}
		try {
	        serialPort.addEventListener(this);
		} catch (TooManyListenersException e) {}
        serialPort.notifyOnDataAvailable(true);
        try {
        	//zigbee SHT10
//            serialPort.setSerialPortParams(115200,
//                SerialPort.DATABITS_8,
//                SerialPort.STOPBITS_1,
//                SerialPort.PARITY_NONE);
        	//zigbee PLC
//        	serialPort.setSerialPortParams(115200,
//                    SerialPort.DATABITS_7,
//                    SerialPort.STOPBITS_1,
//                    SerialPort.PARITY_EVEN);
        	serialPort.setSerialPortParams(9600,
            SerialPort.DATABITS_7,
            SerialPort.STOPBITS_1,
            SerialPort.PARITY_EVEN);

    		outputStream.write(messageString);
        } catch (UnsupportedCommOperationException e) {
        	
        } catch (IOException e) {
			e.printStackTrace();
		}
        readThread = new Thread(this);
        readThread.start();
    }

    public void run() {
    	
        try {
        	while(isContinue){
            Thread.sleep(20000);
        	}
        } catch (InterruptedException e) {
        	
        }
    }
    public void terminate(){
    	isContinue = false ;
    	Thread.interrupted();
    }
    public void serialEvent(SerialPortEvent event) {
        switch(event.getEventType()) {
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
        case SerialPortEvent.DATA_AVAILABLE:
            byte[] readBuffer = new byte[20];

            try {
                while (inputStream.available() > 0) {
                    int numBytes = inputStream.read(readBuffer);
                }
                //System.out.print(new String(readBuffer));
                for(int i = 0 ; i < readBuffer.length && readBuffer[i] != 0 ; i++){
                	if (readBuffer[i] == 0x03){
                		terminate();
                		throw new IOException();
                	}
                	if(isContinue){
                		System.out.print(readBuffer[i]);
                	}
                }
                System.out.println();
            } catch (IOException e) {}
            break;
        }
    }
}
