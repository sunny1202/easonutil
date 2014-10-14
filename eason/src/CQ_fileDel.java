import java.io.File;
import java.io.IOException;

public class CQ_fileDel {
    
    public static Object del_file(Object path){
        String file_path = "Z:"+path.toString();
        File file = new File(file_path);
        System.err.println("*********************AAAAAAA*********************************");
        if(file.exists()){
            file.delete();
            System.err.println("*********************BBBBBBB*********************************");
        }
        System.err.println("*********************CCCCCCCCC*********************************");
        return "1";
    }
    /**
     * @param args
     */
//    public static void main(String[] args) {
//        String cmd[] = {"cmd.exe","/C","net use K: \\\\192.168.30.92\\D$ S100082388 /user:administrator"};
//        Runtime runtime = Runtime.getRuntime();
//        try {
//            Process process = runtime.exec(cmd);
//        
//            File dir = new File("K:\\Upload\\eason\\AAA.txt");
//            if(dir.isDirectory()){
//                System.out.println("是資料夾");
//                System.out.println(dir.getAbsolutePath());
//            }else
//                System.out.println("不是資料夾");
//            if(!dir.delete())
//                System.out.println("不成功");
//            else
//                System.out.println("成功");
//            
//            String cmd1[] = {"cmd.exe","/C","net use K: /delete"};
//            process = runtime.exec(cmd1);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

}
