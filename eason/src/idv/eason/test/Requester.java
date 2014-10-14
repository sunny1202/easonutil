package idv.eason.test;

import java.io.*;
import java.net.*;

public class Requester {
    
	Socket requestSocket;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	String message;
    
	Requester(){
        
    }
    
	void run() {
		try{
			//建立與Server之間的socket及port
		    //指定server ip
            String server_ip = "192.168.34.55";
            //指定server port
            int port_nm = 2244 ; 
			requestSocket = new Socket(server_ip, port_nm);
			System.out.println("Connected to "+server_ip+" in port "+port_nm);
			//取得input及output
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			//開始與Server溝通
			do{
				try{
                    //讀取server訊息
					message = (String)in.readObject();
					System.out.println("server>" + message);
					sendMessage("Hi my server");
                    sendMessage("date");
                    message = (String)in.readObject();
                    System.out.println("server>" + message);
                    sendMessage("time");
                    message = (String)in.readObject();
                    System.out.println("server>" + message);
					message = "bye";
					sendMessage(message);
				}
				catch(ClassNotFoundException classNot){
					System.err.println("data received in unknown format");
				}
			}while(!message.equals("bye"));
		}
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
    /**
     * 傳送Message
     * @param msg
     */
	void sendMessage(String msg){
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	/*
     * 程式進入點
	 */
	public static void main(String[] args) {
		Requester client = new Requester();
		client.run();
	}

}
