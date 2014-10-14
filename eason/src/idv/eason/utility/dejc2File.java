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
	 * 將StringBuffer轉成File
	 * @param sb 組合而成的StringBuffer
	 * @param name 檔案名稱包含副檔名(ex:xxx.txt,xxx.csv)
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
	 * 刪除檔案 需指定正確的檔案名稱含副檔名
	 * @param file_name 檔案名稱含副檔名
	 * @return 回傳是否成功
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
	 * 範例
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		try{
//			StringBuffer sb = new StringBuffer(dejcFieldFormat.StringfieldB("陳奕勝", 10));
//			sb.append(dejcFieldFormat.StringfieldB("Q2153", 8));
//			sb.append(dejcFieldFormat.Decmalfield(550, 7));
//			sb.append("\r\n");
//			sb.append(dejcFieldFormat.StringfieldB("林清龍", 10));
//			sb.append(dejcFieldFormat.StringfieldB("Q1905", 8));
//			sb.append(dejcFieldFormat.Decmalfield(150, 7));
//			sb.append("\r\n");
//			dejc2File.toFile(sb,"test.txt");
//		}catch(Exception e){
//			System.out.println(e);
//		}
//	}

}
