package idv.eason.RS232;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

//import javax.comm.CommPortIdentifier;
//import javax.comm.PortInUseException;
//import javax.comm.SerialPort;
//import javax.comm.UnsupportedCommOperationException;

import gnu.io.*;

public class RS232Test {

    static Enumeration portList;
    static CommPortIdentifier portId;
    static byte[] messageString = new byte[11] ;
    static SerialPort serialPort;
    static OutputStream outputStream;
    static InputStream inputStream;

    public static void main(String[] args) {
        portList = CommPortIdentifier.getPortIdentifiers();
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

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            byte[] readBuffer = new byte[20];
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                 if (portId.getName().equals("COM1")) {
//                if (portId.getName().equals("/dev/term/a")) {
                    try {
                        serialPort = (SerialPort)portId.open("SimpleWriteApp", 2000);
                    } catch (PortInUseException e) {}
                    try {
                        outputStream = serialPort.getOutputStream();
                        inputStream = serialPort.getInputStream();
                    } catch (IOException e) {}
                    try {
                        serialPort.setSerialPortParams(9600,
                            SerialPort.DATABITS_7,
                            SerialPort.STOPBITS_1,
                            SerialPort.PARITY_EVEN);
                    } catch (UnsupportedCommOperationException e) {}
                    try {
                        outputStream.write(messageString);
                    } catch (IOException e) {}
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    try {
                        while (inputStream.available() > 0) {
                            int numBytes = inputStream.read(readBuffer);
                        }
                        //System.out.print(new String(readBuffer));
                        for (int i = 0 ; i < readBuffer.length ; i++){
                            System.out.println(readBuffer[i]);
                        }
                    } catch (IOException e) {}
                }
            }
        }
    }

}
