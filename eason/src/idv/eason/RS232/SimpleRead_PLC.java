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
package idv.eason.RS232 ;

import java.io.*;
import java.util.*;
//import javax.comm.*;
import gnu.io.* ;

public class SimpleRead_PLC implements Runnable, SerialPortEventListener {
    static CommPortIdentifier portId;
    static Enumeration portList;

    InputStream inputStream;
    SerialPort serialPort;
    Thread readThread;
    
    static byte[] messageString = new byte[13] ;
    static OutputStream outputStream;

    public static void main(String[] args) {
        portList = CommPortIdentifier.getPortIdentifiers();
        //讀取plc狀態
//        messageString[0]=0x02;
//        messageString[1]=0x30;//0
//        messageString[2]=0x30;//0
//        messageString[3]=0x30;//0
//        messageString[4]=0x41;//A
//        messageString[5]=0x30;//0
//        messageString[6]=0x30;//0
//        messageString[7]=0x31;//1
//        messageString[8]=0x03;
//        messageString[9]=0x36;//6
//        messageString[10]=0x35;//5
        
//        messageString[0]=0x02;
//        messageString[1]=0x31;//1
//        messageString[2]=0x30;//0
//        messageString[3]=0x31;//1
//        messageString[4]=0x30;//0
//        messageString[5]=0x30;//0
//        messageString[6]=0x30;//0
//        messageString[7]=0x31;//1
//        messageString[8]=0x32;//2
//        messageString[9]=0x30;//0
//        messageString[10]=0x03;
//        messageString[11]=0x42;//B
//        messageString[12]=0x38;//8
//        messageString[0]=0x02;
//        messageString[1]=0x31;
//        messageString[2]=0x30;
//        messageString[3]=0x30;
//        messageString[4]=0x41;
//        messageString[5]=0x32;
//        messageString[6]=0x30;
//        messageString[7]=0x31;
//        messageString[8]=0x46;
//        messageString[9]=0x46;
//        messageString[10]=0x03;
//        messageString[11]=0x46;
//        messageString[12]=0x34;
        String mes = "0080002";
		//"10000010B"; 停止PLC
		//"100000103" 啟動plc;
		//S0~S7狀態"0000001";//Y0~Y24狀態"000A003";
        //0080002 計時器
        //110000110 設定 D0
		byte[] msg = new byte[mes.length()+4];
		msg[0] = 0x02 ;
		int c = 0 ;
		for(int i = 0 ; i < mes.length() ; i++){
			c += mes.charAt(i);
			msg[i+1] = (byte)mes.charAt(i); 
		}
		c+=3;
		msg[msg.length-3] = 0x03 ;
		//System.out.println("final:"+c);
		c = c & 0xff ; 
		String checksum = Integer.toHexString(c).toUpperCase();
		msg[msg.length-2] = (byte)checksum.charAt(0);
		msg[msg.length-1] = (byte)checksum.charAt(1);

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                 if (portId.getName().equals("COM1")) {
                //if (portId.getName().equals("/dev/term/a")) {
                     SimpleRead_PLC reader = new SimpleRead_PLC();
                     try {
                         outputStream.write(msg);
                     } catch (IOException e) {}
                    
                }
            }
        }
    }

    public SimpleRead_PLC() {
        try {
            serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
        } catch (PortInUseException e) {}
        try {
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
        } catch (IOException e) {}
	try {
            serialPort.addEventListener(this);
	} catch (TooManyListenersException e) {}
        serialPort.notifyOnDataAvailable(true);
        try {
            serialPort.setSerialPortParams(9600,
                SerialPort.DATABITS_7,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_EVEN);
        } catch (UnsupportedCommOperationException e) {}
        readThread = new Thread(this);
        readThread.start();
    }

    public void run() {
//        try {
//            //Thread.sleep(500);
//        } catch (InterruptedException e) {}
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
                for (int i = 0 ; i < readBuffer.length ; i++){
                    System.out.println(readBuffer[i]);
                }
            } catch (IOException e) {}
            break;
        }
        serialPort.close();
    }
}
