package idv.eason.test;

import java.io.*;
import java.net.*;
import java.text.DecimalFormat;
import java.util.Calendar;

public class Provider {
	ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
    
	Provider(){
        
    }
    
	void run(){
		try{
			//建立Socket及listen port
			providerSocket = new ServerSocket(2244);
			//等待連線，及建立連線存取 取得建立連線電腦hostname
			System.out.println("Waiting for connection");
			connection = providerSocket.accept();
			System.out.println("Connection received from " + connection.getInetAddress().getHostName());
			//建立input及output
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			sendMessage("Connection successful");
			do{
				try{
                    //取得client的訊息
					message = (String)in.readObject();
					System.out.println("client>" + message);
                    //判斷client傳來的訊息為何
					if (message.equals("bye")) 
						sendMessage("bye");
                    else if (message.equals("date"))
                        sendMessage(CurrentDate());
                    else if (message.equals("time"))
                        sendMessage(CurrentTime());
				}catch(ClassNotFoundException classnot){
					System.err.println("Data received in unknown format");
				}
			}while(!message.equals("bye"));
		}catch(IOException ioException){
			ioException.printStackTrace();
		}finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				providerSocket.close();
			}catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
    /**
     * 傳送訊息
     * @param msg
     */
	void sendMessage(String msg){
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("server>" + msg);
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
    /**
     * 
     * @return 取得現在日期
     */
    public String CurrentDate(){
        Calendar cal=Calendar.getInstance();
        int y=cal.get(Calendar.YEAR)-1911;     
        int m=cal.get(Calendar.MONTH)+1;       
        int d=cal.get(Calendar.DAY_OF_MONTH);  
        return y+"年"+m+"月"+d+"日";
    }
    /**
     * 
     * @return 取得現在時間
     */
    public String CurrentTime(){
        DecimalFormat df = new DecimalFormat("00");
        Calendar cal=Calendar.getInstance();
        int h=cal.get(Calendar.HOUR_OF_DAY); 
        int min=cal.get(Calendar.MINUTE);  
        int sec=cal.get(Calendar.SECOND);   
        return df.format(h)+":"+df.format(min)+":"+df.format(sec);
    }
    //程式的進入點
	public static void main(String args[]){
        //建立Server listen port
		Provider server = new Provider();
		while(true){
			server.run();
		}
	}


}
