/*
 * Main.java
 *
 * 簡單的影像處理程式設計教學
 * 這是教學用範例, 所以並沒有對程式的結構作最佳化, 請讀者注意
 *
 * 版權: 基於知識無價的原則, 你可以任意的刪除, 修改, 散佈與使用這份程式碼.
 * by 井民全
 * 個人網頁: http://debut.cis.nctu.edu.tw/~ching
 */

import javax.imageio.*; // for ImageIO
import java.io.*; // for File
import java.awt.image.*; // for BufferedImage
import javax.swing.*; // for JFrame
import java.awt.event.*; //for ActionListener
import java.awt.*; // for FileDialog
/**
 *
 * @author Jing
 */


public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // 使用目前作業系統慣用的視窗樣式
        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }
        
        String Filename="Lena.bmp";
        // String Filename="c:\\Line.jpg";
        //String Filename="c:\\Wen.bmp";
        //String Filename="c:\\火焰超人.JPG"; // 3264 x 2448
        // String Filename="c:\\淡蘭古道_山貂嶺段.jpg"; // 500 x 375
        ImageComponent image=new ImageComponent(Filename);
        image.Show();
        
       
        /*
        // 程式操作的範例 (你也可以藉由 ImageComponent 的 GUI 操作 想要的影像處理工作)
        int[]newdata=new int[image.data.length];
        System.arraycopy(image.data,0,newdata,0,image.data.length);
        ImageComponent grayImage=new ImageComponent(newdata,image.Height,image.Width,"灰階化");
        grayImage.doGray_Speed(); // 比較快的方法
        // grayImage.doGray(); // 比較慢的方法
        grayImage.Show();
         */
        
        
    }
}

//自己建立的影像類別
class ImageComponent implements Runnable, ActionListener{
    String Filename;
    BufferedImage image;
    JFrame f; // 目前的視窗
    int Height,Width;
    int[] data;
    // 由檔案名稱建立影像物件
    public ImageComponent(String aFilename){
        Filename=aFilename;
        image=LoadImage(Filename);
        
        Height=image.getHeight();
        Width=image.getWidth();
        
        // 取出影像資料
        data=image.getRGB(0,0,Width,Height,null,0,Width);
        // 重新組合建立新的 BufferedImage
        // 這樣做的目的是為了得到真正資料的參考位置,
        // 之後我們只要針對影像資料進行處理, 處理後的結果會直接反映在新的 image 上
        image=CreateBufferedImage_Direct(data,Height,Width);
    }
    
    
    
    // 由影像資料建立影像物件
    public ImageComponent(int[] data,int Height,int Width,String Title){
        this.Height=Height;
        this.Width=Width;
        this.data=data;
        this.Filename=Title;
        image=CreateBufferedImage_Direct(data,Height,Width);
    }
    
    // 建立 BufferedImage   (int[]陣列版本)  (非常快) 20 ms for  3264 x 2448 x 24b
    // 方法:
    //      直接建立 BufferedImage, 把 int[] 陣列內容當作資料來源
    // 優點:
    //      只要改變 rgbData 的內容, 直接會反應到 BufferedImage
    public static BufferedImage CreateBufferedImage_Direct(int[] rgbData,int Height,int Width){
        DataBuffer db = new DataBufferInt(rgbData, Height*Width);
        WritableRaster raster = Raster.createPackedRaster(db, Width, Height, Width, new int[] {0xff0000, 0xff00, 0xff}, null);
        ColorModel cm = new DirectColorModel(24, 0xff0000, 0xff00, 0xff);
        return new BufferedImage(cm, raster, false, null);
    }
    
    // 載入一張影像
    public static BufferedImage LoadImage(String Filename){
        BufferedImage image;
        try{
            image=ImageIO.read(new File(Filename));
        }catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,"載入圖檔錯誤: "+Filename);
            image=null;
        }
        return image;
    }
    
    
    
    // 顯示一張影像
    public void Show(){
        // 告訴系統, 當所有事件都處理完畢後, 
        // 再來執行,目前物件的 run 方法
        // 請接著 run 方法, 繼續追蹤程式的行為
        SwingUtilities.invokeLater(this);
    }
    
    
    public void run(){
        f = new JFrame("");
        
        // 加入選單操作
        JMenuBar menuBar = new JMenuBar();
        JMenu File_Menu=new JMenu("File");
        JMenu Operator_Menu = new JMenu("Operator");
        menuBar.add(File_Menu);
        menuBar.add(Operator_Menu);
        
        // == File 選單部分 ==
        // Open 按鈕
        JMenuItem OpenItem = new JMenuItem("Open");
        OpenItem.addActionListener(this);
        File_Menu.add(OpenItem);
        // Save 按鈕
        JMenuItem SaveItem = new JMenuItem("Save");
        SaveItem.addActionListener(this);
        File_Menu.add(SaveItem);
        
        
        // == Operator 選單部分 ==
        // 加入灰階化按鈕
        JMenuItem item = new JMenuItem("轉成黑白影像");
        item.addActionListener(this);
        Operator_Menu.add(item);
        
        // 加入彩色濾鏡按鈕
        JMenuItem RedFilterItem = new JMenuItem("紅色濾鏡");
        JMenuItem GreenFilterItem = new JMenuItem("綠色濾鏡");
        JMenuItem BlueFilterItem = new JMenuItem("藍色濾鏡");
        RedFilterItem.addActionListener(this);Operator_Menu.add(RedFilterItem);
        GreenFilterItem.addActionListener(this);Operator_Menu.add(GreenFilterItem);
        BlueFilterItem.addActionListener(this);Operator_Menu.add(BlueFilterItem);
        
        // 加入色彩反轉按鈕
        JMenuItem ColorInverseItem = new JMenuItem("色彩反轉");
        ColorInverseItem.addActionListener(this);Operator_Menu.add(ColorInverseItem);
        
        f.setJMenuBar(menuBar);
        
        
        // 若影像超過螢幕, 則加入捲軸
        JScrollPane scrollPane = new JScrollPane(new JLabel(new ImageIcon(image)));
       
       
        
        f.getContentPane().add(scrollPane);
        f.pack();
        
        // 設定點選 x 表示關閉視窗
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 加入視窗標題
        f.setTitle(Filename+" "+image.getWidth()+" x "+image.getHeight());
        
        // 設定視窗顯示在螢幕中央
        f.setLocationRelativeTo(null);
        
        // 顯示出視窗
        f.setVisible(true);
    }
    
    // 事件處理相關程式
    public void actionPerformed(ActionEvent e) {
        long start=System.currentTimeMillis(); // 計算處理時間 (起始)
        
        boolean bDone=false;
        
        
        if(bDone==false && e.getActionCommand().equals("轉成黑白影像")){
            // doGray();  // 慢的版本
            // doGray_Speed(); // 直接更改本身資料的版本
            doGray_newWindow(); // 建立新的視窗的版本
            
            bDone=true;
        }
        
        // ==== 彩色濾鏡按鈕 ====
        if(bDone==false && e.getActionCommand().equals("紅色濾鏡")){
            RedFilter_newWindow();
            bDone=true;
        }
        if(bDone==false && e.getActionCommand().equals("綠色濾鏡")){
            GreenFilter_newWindow();
            bDone=true;
        }
        if(bDone==false && e.getActionCommand().equals("藍色濾鏡")){
            BlueFilter_newWindow();
            bDone=true;
        }
        
        // === 色彩反轉按鈕 ===
        if(bDone==false && e.getActionCommand().equals("色彩反轉")){
            ColorInverse_newWindow();
            bDone=true;
        }
        
        f.repaint();
        
        
        if(bDone){
            // 計算處理時間 (結束)
            long end=System.currentTimeMillis();
            long time=(end-start); // 計算處理時間
            javax.swing.JOptionPane.showMessageDialog(null,"執行時間= "+time+" ms");
        }
        
        
        // == File 選項 ==
        if(bDone==false && e.getActionCommand().equals("Open")){
            Open();
            bDone=true;
        }
        if(bDone==false && e.getActionCommand().equals("Save")){
            Save();
            bDone=true;
        }
        
        
    }
    
    // 開啟新的圖檔
    public void Open(){
        FileDialog fd = new FileDialog(f, "Open...", FileDialog.LOAD);
        fd.setVisible(true);
        if(fd!=null){
            Filename=fd.getDirectory() + System.getProperty("file.separator").charAt(0) + fd.getFile();
            File file=new File(Filename);
            if(file.exists()){
                ImageComponent newImage=new ImageComponent(Filename);
                newImage.Show();
            }
        }
    }
    
    public void Save(){
        // 根據使用者的操作, 組合出存檔名稱 [Filename]_[使用者的動作].jpg
        String SaveFilename=Filename+"_Result"+".jpg";
        try{
            ImageIO.write(image,"jpeg",new File(SaveFilename));
            /* 其他存檔格式範例
             ImageIO.write(image,"jpeg",new File("c:\\Result.jpg"));
             ImageIO.write(image,"bmp",new File("c:\\Result.bmp"));
             ImageIO.write(image,"png",new File("c:\\Result.png"));
             ImageIO.write(image,"gif",new File("c:\\Result.gif"));
             */
            javax.swing.JOptionPane.showMessageDialog(null,"檔案:"+SaveFilename+" 存檔完成");
            
        }catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,"存圖錯誤: "+SaveFilename);
            image=null;
        }
    }
    
    // ================================ 後面全部是 影像處理相關的範例程式 =========================
    // 簡單的影像處理函式
    // 彩色影像變成黑白影像
    public void doGray(){
        long start=System.currentTimeMillis(); // 計算處理時間 (起始)
        
        for(int y=0;y<Height;y++){
            for(int x=0;x<Width;x++){
                int rgb=image.getRGB(x,y);
                int r=(rgb&0x00ff0000)>>16;
                int g=(rgb&0x0000ff00)>>8;
                int b=rgb&0x000000ff;
                int gray=(r+g+b)/3;
                rgb=(0xff000000|(gray<<16)|(gray<<8)|gray); // rgb=(0xff000000|(r<<16)|(g<<8)|b);
                image.setRGB(x,y,rgb);
            }
        }
        
        long end=System.currentTimeMillis(); // 計算處理時間 (結束)
        long time=(end-start); // 計算處理時間
        javax.swing.JOptionPane.showMessageDialog(null,"執行時間= "+time+" ms");
    }
    
    
    public void doGray_newWindow(){
        int[]newdata=new int[data.length];
        System.arraycopy(data,0,newdata,0,data.length);
        ImageComponent grayImage=new ImageComponent(newdata,Height,Width,Filename+"_灰階化");
        grayImage.doGray_Speed(); // 比較快的方法
        grayImage.Show();
    }
    // 比較快的灰階化方法
    // 直接改影像陣列的值, 會直接反應到 BufferedImage 物件
    public void doGray_Speed(){
        for(int y=0;y<Height;y++){
            for(int x=0;x<Width;x++){
                int offset=y*Width+x;
                int rgb=data[offset];
                int r=(rgb&0x00ff0000)>>16;
                int g=(rgb&0x0000ff00)>>8;
                int b=rgb&0x000000ff;
                int gray=(r+g+b)/3;
                rgb=(0xff000000|(gray<<16)|(gray<<8)|gray); // rgb=(0xff000000|(r<<16)|(g<<8)|b);
                data[offset]=rgb;
            }
        }
    }
    public void ColorInverse(){
        for(int y=0;y<Height;y++){
            for(int x=0;x<Width;x++){
                int offset=y*Width+x;
                int rgb=255-data[offset]; // 反轉顏色
                int r=(rgb&0x00ff0000)>>16;
                int g=(rgb&0x0000ff00)>>8;
                int b=rgb&0x000000ff;
                rgb=(0xff000000|(r<<16)|(g<<8)|b);
                data[offset]=rgb;
            }
        }
    }
    
    public int[] ColorInverse_newWindow(){
        int[]newdata=new int[data.length];
        System.arraycopy(data,0,newdata,0,data.length);
        ImageComponent grayImage=new ImageComponent(newdata,Height,Width,Filename+"_色彩反轉");
        grayImage.ColorInverse();
        grayImage.Show();
        return newdata;
    }
    
    // 紅色濾鏡
    public void RedFilter(){
        for(int y=0;y<Height;y++){
            for(int x=0;x<Width;x++){
                int offset=y*Width+x;
                int rgb=data[offset];
                int r=(rgb&0x00ff0000)>>16;
                rgb=(0xff000000|(r<<16)); // rgb=(0xff000000|(r<<16)|(g<<8)|b);
                data[offset]=rgb;
            }
        }
    }
    public  int[] RedFilter_newWindow(){
        // Step 1: 利用目前的影像資料 data 陣列, 建立一個新的 ImageComponent 物件.
        int[]newdata=new int[data.length];
        System.arraycopy(data,0,newdata,0,data.length);
        ImageComponent grayImage=new ImageComponent(newdata,Height,Width,Filename+"_紅色濾鏡處理");
        
        // Step 2: 呼叫新物件的影像處理函式, 最後顯示出來
        grayImage.RedFilter();
        grayImage.Show();
        return newdata;
    }
    // 綠色濾鏡
    public void GreenFilter(){
        for(int y=0;y<Height;y++){
            for(int x=0;x<Width;x++){
                int offset=y*Width+x;
                int rgb=data[offset];
                int g=(rgb&0x0000ff00)>>8;
                rgb=(0xff000000|(g<<8)); // rgb=(0xff000000|(r<<16)|(g<<8)|b);
                data[offset]=rgb;
            }
        }
    }
    public  int[] GreenFilter_newWindow(){
        int[]newdata=new int[data.length];
        System.arraycopy(data,0,newdata,0,data.length);
        ImageComponent grayImage=new ImageComponent(newdata,Height,Width,Filename+"_綠色濾鏡處理");
        grayImage.GreenFilter();
        grayImage.Show();
        return newdata;
    }
    // 藍色濾鏡
    public void BlueFilter(){
        for(int y=0;y<Height;y++){
            for(int x=0;x<Width;x++){
                int offset=y*Width+x;
                int rgb=data[offset];
                int b=rgb&0x000000ff;
                rgb=(0xff000000|b); // rgb=(0xff000000|(r<<16)|(g<<8)|b);
                data[offset]=rgb;
            }
        }
    }
    
    public int[] BlueFilter_newWindow(){
        int[]newdata=new int[data.length];
        System.arraycopy(data,0,newdata,0,data.length);
        ImageComponent grayImage=new ImageComponent(newdata,Height,Width,Filename+"_藍色濾鏡處理");
        grayImage.BlueFilter();
        grayImage.Show();
        return newdata;
    }
    
   
}
