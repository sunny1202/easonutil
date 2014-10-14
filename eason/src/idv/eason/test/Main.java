/*
 * Main.java
 *
 * ²�檺�v���B�z�{���]�p�о�
 * �o�O�оǥνd��, �ҥH�èS����{�������c�@�̨Τ�, ��Ū�̪`�N
 *
 * ���v: ����ѵL������h, �A�i�H���N���R��, �ק�, ���G�P�ϥγo���{���X.
 * by ������
 * �ӤH����: http://debut.cis.nctu.edu.tw/~ching
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
        // �ϥΥثe�@�~�t�κD�Ϊ������˦�
        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }
        
        String Filename="Lena.bmp";
        // String Filename="c:\\Line.jpg";
        //String Filename="c:\\Wen.bmp";
        //String Filename="c:\\���K�W�H.JPG"; // 3264 x 2448
        // String Filename="c:\\�H���j�D_�s�I���q.jpg"; // 500 x 375
        ImageComponent image=new ImageComponent(Filename);
        image.Show();
        
       
        /*
        // �{���ާ@���d�� (�A�]�i�H�ǥ� ImageComponent �� GUI �ާ@ �Q�n���v���B�z�u�@)
        int[]newdata=new int[image.data.length];
        System.arraycopy(image.data,0,newdata,0,image.data.length);
        ImageComponent grayImage=new ImageComponent(newdata,image.Height,image.Width,"�Ƕ���");
        grayImage.doGray_Speed(); // ����֪���k
        // grayImage.doGray(); // ����C����k
        grayImage.Show();
         */
        
        
    }
}

//�ۤv�إߪ��v�����O
class ImageComponent implements Runnable, ActionListener{
    String Filename;
    BufferedImage image;
    JFrame f; // �ثe������
    int Height,Width;
    int[] data;
    // ���ɮצW�٫إ߼v������
    public ImageComponent(String aFilename){
        Filename=aFilename;
        image=LoadImage(Filename);
        
        Height=image.getHeight();
        Width=image.getWidth();
        
        // ���X�v�����
        data=image.getRGB(0,0,Width,Height,null,0,Width);
        // ���s�զX�إ߷s�� BufferedImage
        // �o�˰����ت��O���F�o��u����ƪ��ѦҦ�m,
        // ����ڭ̥u�n�w��v����ƶi��B�z, �B�z�᪺���G�|�����ϬM�b�s�� image �W
        image=CreateBufferedImage_Direct(data,Height,Width);
    }
    
    
    
    // �Ѽv����ƫإ߼v������
    public ImageComponent(int[] data,int Height,int Width,String Title){
        this.Height=Height;
        this.Width=Width;
        this.data=data;
        this.Filename=Title;
        image=CreateBufferedImage_Direct(data,Height,Width);
    }
    
    // �إ� BufferedImage   (int[]�}�C����)  (�D�`��) 20 ms for  3264 x 2448 x 24b
    // ��k:
    //      �����إ� BufferedImage, �� int[] �}�C���e��@��ƨӷ�
    // �u�I:
    //      �u�n���� rgbData �����e, �����|������ BufferedImage
    public static BufferedImage CreateBufferedImage_Direct(int[] rgbData,int Height,int Width){
        DataBuffer db = new DataBufferInt(rgbData, Height*Width);
        WritableRaster raster = Raster.createPackedRaster(db, Width, Height, Width, new int[] {0xff0000, 0xff00, 0xff}, null);
        ColorModel cm = new DirectColorModel(24, 0xff0000, 0xff00, 0xff);
        return new BufferedImage(cm, raster, false, null);
    }
    
    // ���J�@�i�v��
    public static BufferedImage LoadImage(String Filename){
        BufferedImage image;
        try{
            image=ImageIO.read(new File(Filename));
        }catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,"���J���ɿ��~: "+Filename);
            image=null;
        }
        return image;
    }
    
    
    
    // ��ܤ@�i�v��
    public void Show(){
        // �i�D�t��, ��Ҧ��ƥ󳣳B�z������, 
        // �A�Ӱ���,�ثe���� run ��k
        // �б��� run ��k, �~��l�ܵ{�����欰
        SwingUtilities.invokeLater(this);
    }
    
    
    public void run(){
        f = new JFrame("");
        
        // �[�J���ާ@
        JMenuBar menuBar = new JMenuBar();
        JMenu File_Menu=new JMenu("File");
        JMenu Operator_Menu = new JMenu("Operator");
        menuBar.add(File_Menu);
        menuBar.add(Operator_Menu);
        
        // == File ��泡�� ==
        // Open ���s
        JMenuItem OpenItem = new JMenuItem("Open");
        OpenItem.addActionListener(this);
        File_Menu.add(OpenItem);
        // Save ���s
        JMenuItem SaveItem = new JMenuItem("Save");
        SaveItem.addActionListener(this);
        File_Menu.add(SaveItem);
        
        
        // == Operator ��泡�� ==
        // �[�J�Ƕ��ƫ��s
        JMenuItem item = new JMenuItem("�ন�¥ռv��");
        item.addActionListener(this);
        Operator_Menu.add(item);
        
        // �[�J�m���o����s
        JMenuItem RedFilterItem = new JMenuItem("�����o��");
        JMenuItem GreenFilterItem = new JMenuItem("����o��");
        JMenuItem BlueFilterItem = new JMenuItem("�Ŧ��o��");
        RedFilterItem.addActionListener(this);Operator_Menu.add(RedFilterItem);
        GreenFilterItem.addActionListener(this);Operator_Menu.add(GreenFilterItem);
        BlueFilterItem.addActionListener(this);Operator_Menu.add(BlueFilterItem);
        
        // �[�J��m������s
        JMenuItem ColorInverseItem = new JMenuItem("��m����");
        ColorInverseItem.addActionListener(this);Operator_Menu.add(ColorInverseItem);
        
        f.setJMenuBar(menuBar);
        
        
        // �Y�v���W�L�ù�, �h�[�J���b
        JScrollPane scrollPane = new JScrollPane(new JLabel(new ImageIcon(image)));
       
       
        
        f.getContentPane().add(scrollPane);
        f.pack();
        
        // �]�w�I�� x �����������
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // �[�J�������D
        f.setTitle(Filename+" "+image.getWidth()+" x "+image.getHeight());
        
        // �]�w������ܦb�ù�����
        f.setLocationRelativeTo(null);
        
        // ��ܥX����
        f.setVisible(true);
    }
    
    // �ƥ�B�z�����{��
    public void actionPerformed(ActionEvent e) {
        long start=System.currentTimeMillis(); // �p��B�z�ɶ� (�_�l)
        
        boolean bDone=false;
        
        
        if(bDone==false && e.getActionCommand().equals("�ন�¥ռv��")){
            // doGray();  // �C������
            // doGray_Speed(); // ������糧����ƪ�����
            doGray_newWindow(); // �إ߷s������������
            
            bDone=true;
        }
        
        // ==== �m���o����s ====
        if(bDone==false && e.getActionCommand().equals("�����o��")){
            RedFilter_newWindow();
            bDone=true;
        }
        if(bDone==false && e.getActionCommand().equals("����o��")){
            GreenFilter_newWindow();
            bDone=true;
        }
        if(bDone==false && e.getActionCommand().equals("�Ŧ��o��")){
            BlueFilter_newWindow();
            bDone=true;
        }
        
        // === ��m������s ===
        if(bDone==false && e.getActionCommand().equals("��m����")){
            ColorInverse_newWindow();
            bDone=true;
        }
        
        f.repaint();
        
        
        if(bDone){
            // �p��B�z�ɶ� (����)
            long end=System.currentTimeMillis();
            long time=(end-start); // �p��B�z�ɶ�
            javax.swing.JOptionPane.showMessageDialog(null,"����ɶ�= "+time+" ms");
        }
        
        
        // == File �ﶵ ==
        if(bDone==false && e.getActionCommand().equals("Open")){
            Open();
            bDone=true;
        }
        if(bDone==false && e.getActionCommand().equals("Save")){
            Save();
            bDone=true;
        }
        
        
    }
    
    // �}�ҷs������
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
        // �ھڨϥΪ̪��ާ@, �զX�X�s�ɦW�� [Filename]_[�ϥΪ̪��ʧ@].jpg
        String SaveFilename=Filename+"_Result"+".jpg";
        try{
            ImageIO.write(image,"jpeg",new File(SaveFilename));
            /* ��L�s�ɮ榡�d��
             ImageIO.write(image,"jpeg",new File("c:\\Result.jpg"));
             ImageIO.write(image,"bmp",new File("c:\\Result.bmp"));
             ImageIO.write(image,"png",new File("c:\\Result.png"));
             ImageIO.write(image,"gif",new File("c:\\Result.gif"));
             */
            javax.swing.JOptionPane.showMessageDialog(null,"�ɮ�:"+SaveFilename+" �s�ɧ���");
            
        }catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,"�s�Ͽ��~: "+SaveFilename);
            image=null;
        }
    }
    
    // ================================ �᭱�����O �v���B�z�������d�ҵ{�� =========================
    // ²�檺�v���B�z�禡
    // �m��v���ܦ��¥ռv��
    public void doGray(){
        long start=System.currentTimeMillis(); // �p��B�z�ɶ� (�_�l)
        
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
        
        long end=System.currentTimeMillis(); // �p��B�z�ɶ� (����)
        long time=(end-start); // �p��B�z�ɶ�
        javax.swing.JOptionPane.showMessageDialog(null,"����ɶ�= "+time+" ms");
    }
    
    
    public void doGray_newWindow(){
        int[]newdata=new int[data.length];
        System.arraycopy(data,0,newdata,0,data.length);
        ImageComponent grayImage=new ImageComponent(newdata,Height,Width,Filename+"_�Ƕ���");
        grayImage.doGray_Speed(); // ����֪���k
        grayImage.Show();
    }
    // ����֪��Ƕ��Ƥ�k
    // ������v���}�C����, �|���������� BufferedImage ����
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
                int rgb=255-data[offset]; // �����C��
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
        ImageComponent grayImage=new ImageComponent(newdata,Height,Width,Filename+"_��m����");
        grayImage.ColorInverse();
        grayImage.Show();
        return newdata;
    }
    
    // �����o��
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
        // Step 1: �Q�Υثe���v����� data �}�C, �إߤ@�ӷs�� ImageComponent ����.
        int[]newdata=new int[data.length];
        System.arraycopy(data,0,newdata,0,data.length);
        ImageComponent grayImage=new ImageComponent(newdata,Height,Width,Filename+"_�����o��B�z");
        
        // Step 2: �I�s�s���󪺼v���B�z�禡, �̫���ܥX��
        grayImage.RedFilter();
        grayImage.Show();
        return newdata;
    }
    // ����o��
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
        ImageComponent grayImage=new ImageComponent(newdata,Height,Width,Filename+"_����o��B�z");
        grayImage.GreenFilter();
        grayImage.Show();
        return newdata;
    }
    // �Ŧ��o��
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
        ImageComponent grayImage=new ImageComponent(newdata,Height,Width,Filename+"_�Ŧ��o��B�z");
        grayImage.BlueFilter();
        grayImage.Show();
        return newdata;
    }
    
   
}
