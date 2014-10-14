package idv.eason.utility;

import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.*;
import java.io.*;


public class FTP_DU {
	private static String server = "211.20.204.193";
	private static String user = "fm" ;
	private static String pw = "fm1234" ;
	private static Logger log = Logger.getLogger("FTFtest.class");

	private static FTPClient ftp = new FTPClient();
	/**
	 * download File
	 * @param local_path  ���a�x�s�ɮצ�m
	 * @param remote_path ���ݥؿ�
	 * @return 
	 */
	public static boolean get(String local_path,String remote_path) throws SocketException,IOException,Exception{
		return get(local_path,remote_path,null);
	}
	/**
	 * download File ���w�ɮ�
	 * @param local_path  ���a�x�s�ɮצ�m
	 * @param remote_path ���ݥؿ�
	 * @param file_name �u���o�ө��Y���ɮ�(ex:FNS_ID02xxx,FTQSLxxx)
	 * @return
	 */
	public static boolean get(String local_path,String remote_path,String file_name) throws SocketException,IOException,Exception{

		ftp.connect(server, 21);
		if(!ftp.login(user, pw)){
			throw new Exception("�n�J����");
		}
		
		ftp.setControlEncoding("UTF-8");  
        FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);    
        conf.setServerLanguageCode("zh-tw");
        //���ݸ��|
        ftp.changeWorkingDirectory(remote_path);
        FTPFile[] remoteFiles = ftp.listFiles();
	    if(remoteFiles != null){
	    	for(int i=0 ; i < remoteFiles.length ; i++){
	    		String name = remoteFiles[i].getName();
	    		if(file_name != null){
		    		//����ɮצW��(�u���}�Y���W��ex:SLORDTxxx,FHS_IDFDxxx)
		    		if(!name.startsWith(file_name))
		    			continue ;
	    		}
                File localFile = new File(local_path+"/"+name);  
                OutputStream is = new FileOutputStream(localFile);  
                //String fileName = name; 
                log.info("download file : "+name);
                //ftp.retrieveFile(new String(fileName.getBytes("Big5"),"ISO-8859-1"), is);  
                ftp.retrieveFile(name, is);
                is.close();
	    	}
	    }
	    ftp.logout();
		
		return true ;
	}
	/**
	 * Upload File �Ntemp��Ƨ����ɮץ����W��
	 * @param local_path  ���a�W���ɮצ�m
	 * @param remote_path ���ݥؿ�
	 * @return
	 */
	public static boolean put(String local_path,String remote_path) throws SocketException,IOException,Exception{
		return put(local_path,remote_path,null);
	}
	/**
	 * Upload File ���w�ɮ� 
	 * �|�H�ǤJ��file_name�Ptemp��Ƨ������ɮצW�ٰ�startsWith�����Y���ۦP�h�W�Ǧ�ftp
	 * @param local_path  ���a�W���ɮצ�m
	 * @param remote_path ���ݥؿ�
	 * @param file_name 
	 * @return
	 */
	public static boolean put(String local_path,String remote_path,String file_name) throws SocketException,IOException,Exception{

		ftp.connect(server, 21);
		if(!ftp.login(user, pw)){
			throw new Exception("�n�J����");
		}
		
		ftp.setControlEncoding("UTF-8");  
        FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);    
        conf.setServerLanguageCode("zh-tw");
        //���ݸ��|
        ftp.changeWorkingDirectory(remote_path);
        
        File upload = new File(local_path);
        File[] upload_file = upload.listFiles();
        if(upload_file == null){
			throw new Exception("�L�W�Ǫ��ɮ�");
        }
        
        for(int i = 0 ; i < upload_file.length ; i++){

            String destinationFileName = new String(upload_file[i].getName().getBytes("Big5"),"ISO-8859-1");
            if(file_name != null){
            	//����ɮצW��(�u���}�Y���W��ex:SLORDTxxx,FHS_IDFDxxx)
	            if(!destinationFileName.startsWith(file_name))
	            	continue ;
            }
        	FileInputStream fis = new FileInputStream(upload_file[i]);
//                String destinationFileName = upload_file[i].getName();
            String tempFileName = " temp_ " + destinationFileName;
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            boolean flag = ftp.storeFile(tempFileName, fis);
            if (flag) {
              log.info(upload_file[i].getName()+" upload success !!! ");
              ftp.rename(tempFileName, destinationFileName);
            }
            fis.close();
        }
        ftp.logout();
        if(file_name != null){
		//�R���ɮ�
	        for(int i = 0 ; i < upload_file.length ; i++){
	        	if(upload_file[i].getName().startsWith(file_name))
	        		upload_file[i].delete();
	        }
        }
		return true ;
	}
	/**
	 * �R�������ɮ�
	 * @param file_name �ݥ��T���w�ɮצW��
	 * @throws Exception 
	 */
	public static void delFile(String remote_path,String file_name) throws SocketException,IOException,Exception{

		ftp.connect(server, 21);
		if(!ftp.login(user, pw)){
			throw new Exception("�n�J����");
		}
		ftp.changeWorkingDirectory(remote_path);
		ftp.deleteFile(file_name);
		ftp.logout();
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			FTP_DU.get("./temp", "","SLORDT");
			FTP_DU.delFile("","SLORDT_20120202.txt");
				Thread.sleep(5000);
			FTP_DU.put("./temp", "","FTQSL");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
