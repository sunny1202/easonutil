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
			//�إ�Socket��listen port
			providerSocket = new ServerSocket(2244);
			//���ݳs�u�A�Ϋإ߳s�u�s�� ���o�إ߳s�u�q��hostname
			System.out.println("Waiting for connection");
			connection = providerSocket.accept();
			System.out.println("Connection received from " + connection.getInetAddress().getHostName());
			//�إ�input��output
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			sendMessage("Connection successful");
			do{
				try{
                    //���oclient���T��
					message = (String)in.readObject();
					System.out.println("client>" + message);
                    //�P�_client�ǨӪ��T������
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
     * �ǰe�T��
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
     * @return ���o�{�b���
     */
    public String CurrentDate(){
        Calendar cal=Calendar.getInstance();
        int y=cal.get(Calendar.YEAR)-1911;     
        int m=cal.get(Calendar.MONTH)+1;       
        int d=cal.get(Calendar.DAY_OF_MONTH);  
        return y+"�~"+m+"��"+d+"��";
    }
    /**
     * 
     * @return ���o�{�b�ɶ�
     */
    public String CurrentTime(){
        DecimalFormat df = new DecimalFormat("00");
        Calendar cal=Calendar.getInstance();
        int h=cal.get(Calendar.HOUR_OF_DAY); 
        int min=cal.get(Calendar.MINUTE);  
        int sec=cal.get(Calendar.SECOND);   
        return df.format(h)+":"+df.format(min)+":"+df.format(sec);
    }
    //�{�����i�J�I
	public static void main(String args[]){
        //�إ�Server listen port
		Provider server = new Provider();
		while(true){
			server.run();
		}
	}


}
