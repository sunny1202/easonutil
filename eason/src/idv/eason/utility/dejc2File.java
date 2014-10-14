package idv.eason.utility;

import java.io.*;

public class dejc2File {
	private static String path = "./temp";
	private static File file ;
	
	public static void mkdir(File mk){
		if(!mk.exists())
			mk.mkdirs();
	}
	/**
	 * �NStringBuffer�নFile
	 * @param sb �զX�Ӧ���StringBuffer
	 * @param name �ɮצW�٥]�t���ɦW(ex:xxx.txt,xxx.csv)
	 */
	public static void toFile(StringBuffer sb,String name) throws Exception{
		file = new File(path);
		dejc2File.mkdir(file);
		file = new File(path+"/"+name);
		
		if(!file.exists())
			file.createNewFile();
		
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(sb.toString().getBytes());
		fos.close();
		
	}

	public static void toFile(String filepath,StringBuffer sb,String name) throws Exception{
		setPath(filepath);
		file = new File(path);
		dejc2File.mkdir(file);
		file = new File(path+"/"+name);
		
		if(!file.exists())
			file.createNewFile();
		
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(sb.toString().getBytes());
		fos.close();
		
	}
	public static String getPath() {
		return path;
	}
	public static void setPath(String path) {
		dejc2File.path = path;
	}
	/**
	 * �R���ɮ� �ݫ��w���T���ɮצW�٧t���ɦW
	 * @param file_name �ɮצW�٧t���ɦW
	 * @return �^�ǬO�_���\
	 */
	public static boolean delFile(String file_name){
		boolean isok = false ;
		file = new File(path+"/"+file_name);
		if(!file.exists()){
			return isok ;
		}
		isok = file.delete();
		return isok ;
	}
	
	public void parseFile(String path,String name) throws Exception{
		String s = "";
		file = new File(path+"/"+name);
		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		while((s = br.readLine()) != null){
			System.out.println(s);
		}
	}
	/**
	 * �d��
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		try{
//			StringBuffer sb = new StringBuffer(dejcFieldFormat.StringfieldB("������", 10));
//			sb.append(dejcFieldFormat.StringfieldB("Q2153", 8));
//			sb.append(dejcFieldFormat.Decmalfield(550, 7));
//			sb.append("\r\n");
//			sb.append(dejcFieldFormat.StringfieldB("�L�M�s", 10));
//			sb.append(dejcFieldFormat.StringfieldB("Q1905", 8));
//			sb.append(dejcFieldFormat.Decmalfield(150, 7));
//			sb.append("\r\n");
//			dejc2File.toFile(sb,"test.txt");
//		}catch(Exception e){
//			System.out.println(e);
//		}
//	}

}
