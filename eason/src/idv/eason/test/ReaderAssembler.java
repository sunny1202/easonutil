package idv.eason.test;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class ReaderAssembler {
    
    String Loc = "" ;
    Map symtab = new HashMap() ;

    /**
     * @param args
     */
    public static void main(String[] args) {
        BufferedReader br = null ;
        try {
            //Opcode opcode = new Opcode();
            String reader = "" ;
            StringTokenizer st = null ;
            String[] array = null ;
            ReaderAssembler assembler = new ReaderAssembler() ;
            //讀入原始程式
            br = new BufferedReader(new FileReader("./AssemberCode.txt"));
            assembler.Pass1(br);
            //讀入pass1程式
            br = new BufferedReader(new FileReader("./AssemberCode_pass1.txt"));
            assembler.Pass2(br);
//            int rows = 0 ;
//            while((reader = br.readLine()) != null){
//                array = reader.split("\\t");
//                rows++;
//                System.out.print("第"+rows+"行");
//                for(int i = 0 ; i < array.length ; i++){
//                    System.out.print("["+i+"]="+array[i]+"  ");
//                }
//                System.out.println();
//            }
//            opcode.getOpocde("ADD");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Assembler's pass 1
     *
     */
    public void Pass1(BufferedReader pass1read) throws Exception{
        String[] asmb_code = new String[3] ;
        String[] pass1 = new String[4];
        Opcode opcode = new Opcode();
        //寫出成檔案
        BufferedWriter bwrite = new BufferedWriter(new FileWriter("./AssemberCode_pass1.txt"));

//        OutputStreamWriter osw = new OutputStreamWriter(new OutputStream(new File("C:\\Documents and Settings\\YCLIANG\\桌面\\AssemberCode_pass1.txt")),"UTF-8");
        int rows = 0 ;
        String line = "";
        while((line = pass1read.readLine()) != null){
            //line = line.toUpperCase();
            asmb_code = line.split("\\t");
            rows ++ ;
            if(asmb_code[1].equals("START")){
                setLoc(asmb_code[2]) ;
                pass1[0] = getLoc();
            }else if(asmb_code[1].equals("END")){
                pass1[0] = " ";
                pass1[1] = asmb_code[0];
                pass1[2] = asmb_code[1]==null?" ":asmb_code[1];
                pass1[3] = asmb_code[2]==null?" ":asmb_code[2];
            }else{
                pass1[0] = getLoc();
                if(!asmb_code[0].equals("")){
                    if(getSymtab(asmb_code[0]) != null)
                        System.err.println(asmb_code[0]+" is duplicate symbol");
                    else
                        setSymtab(asmb_code[0],getLoc());
                }
                if(opcode.getOpocde(asmb_code[1]) != null)
                    setLoc(Integer.toHexString(Integer.parseInt(getLoc(),16)+3));
                else if(asmb_code[1].equals("WORD"))
                    setLoc(Integer.toHexString(Integer.parseInt(getLoc(),16)+3));
                else if(asmb_code[1].equals("RESW"))
                    setLoc(Integer.toHexString(Integer.parseInt(getLoc(),16)+(3*Integer.parseInt(asmb_code[2]))));
                else if(asmb_code[1].equals("RESB"))
                    setLoc(Integer.toHexString(Integer.parseInt(getLoc(),16)+Integer.parseInt(asmb_code[2])));
                else if(asmb_code[1].equals("BYTE")){
                    String length  = asmb_code[2].substring(asmb_code[2].indexOf("'")+1,asmb_code[2].lastIndexOf("'")); 
                    if(asmb_code[2].startsWith("C")){ //Char
                        setLoc(Integer.toHexString(Integer.parseInt(getLoc(),16)+length.length()));
                    }else if(asmb_code[2].startsWith("X")){ //Hex
                        setLoc(Integer.toHexString(Integer.parseInt(getLoc(),16)+(length.length()/2)));
//                        length = Integer.toBinaryString(Integer.parseInt(length,16));
//                        setLoc(Integer.toHexString(Integer.parseInt(getLoc(),16)+(length.length()/8)));
                    }
                }else
                    System.err.println(asmb_code[2]+" invalid operation code");
            }
            pass1[1] = asmb_code[0];
            pass1[2] = asmb_code[1]==null?" ":asmb_code[1];
            if(asmb_code.length < 3)
                pass1[3] = " ";
            else
                pass1[3] = asmb_code[2]==null?"":asmb_code[2];
            //System.out.println(pass1[0]+"\t"+pass1[1]+"\t"+pass1[2]+"\t"+pass1[3]);
            bwrite.write(pass1[0]+"\t"+pass1[1]+"\t"+pass1[2]+"\t"+pass1[3]+"\r\n");
        }
        bwrite.flush();
    }
    public void Pass2(BufferedReader br2) throws Exception{
    	String[] pass1code = new String[4];

        Opcode opcode = new Opcode();
        //寫出成檔案
        BufferedWriter bwrite = new BufferedWriter(new FileWriter("./Objec_Code.txt"));
        String line = "";
        
        String padding = "";
        String Textline = "";
        String TextHead = "";
        String text = "" ;
        while((line = br2.readLine()) != null){
        	pass1code = line.split("\\t");
        	if(pass1code[2].equals("START")){
                bwrite.write("H");
                //程式名稱2~7
                String programName = "      ";
                programName = (pass1code[1]+programName).substring(0, 6);
                bwrite.write(programName);
                //程式起始位置8~13
                bwrite.write(Hex2padding(pass1code[3],6));
                //System.out.println(Hex2padding(pass1code[3],6));
                //計算程式長度getLoc()-起始位置14~19
                padding = Integer.toHexString(Integer.parseInt(getLoc(), 16)-Integer.parseInt(pass1code[3],16));
                bwrite.write(Hex2padding(padding,6).toUpperCase()+"\r\n");
                //System.out.println(Hex2padding(padding,6)+"\r\n");
                TextHead = "T"+Hex2padding(pass1code[3],6);
            }else if(pass1code[2].equals("END")){
                String textlength = Hex2padding(Integer.toHexString(Textline.length()/2),2) ;
                TextHead += (textlength+Textline) ;
                //System.out.println(TextHead);
                bwrite.write(TextHead.toUpperCase()+"\r\n");
        		bwrite.write("E");
        		//程式執行開始位置2~7
        		padding = getSymtab(pass1code[3]);
                bwrite.write(Hex2padding(padding,6).toUpperCase()+"\r\n");
                //System.out.println(Hex2padding(padding,6)+"\r\n");
        		break ;
        	}else{
        		if(opcode.getOpocde(pass1code[2]) != null){
        			String temp = opcode.getOpocde(pass1code[2]);
        			if(!pass1code[3].equals(" ") && pass1code[3].indexOf(",") < 0){
	        			temp = Hex2padding(Integer.toBinaryString(Integer.parseInt(getSymtab(pass1code[3]),16)),16);
	        			text = temp.substring(temp.length()-16);
        			}else if(pass1code[3].indexOf(",") > 0){ //BUFFER,X
                        //置換pass1code[3]
                        pass1code[3] = pass1code[3].substring(0,pass1code[3].indexOf(","));
                        temp = Hex2padding(Integer.toBinaryString(Integer.parseInt(getSymtab(pass1code[3]),16)),16);
                        text = "1"+temp.substring(1);
        			}else
                        text = "0000" ;
                    text = Hex2padding(opcode.getOpocde(pass1code[2]),2)+Hex2padding(Integer.toHexString(Integer.parseInt(text, 2)),4);
               }else if(!pass1code[2].equals("") && (pass1code[2].equals("WORD") || pass1code[2].equals("BYTE"))){
                   text = "";
                   if(pass1code[2].equals("WORD")){
                        text = Hex2padding(Integer.toHexString(Integer.parseInt(pass1code[3])),6);
                   }else { //char 轉成16進制
                       if(pass1code[3].startsWith("C")){
                           char[] charstr = pass1code[3].substring(pass1code[3].indexOf("'")+1,pass1code[3].lastIndexOf("'")).toCharArray();
                           for(int i = 0 ; i < charstr.length ; i ++){
                               text += Hex2padding(Integer.toHexString(charstr[i]),2);
                           }
                       }else{ //X為16進制直接加入本行
                           text = pass1code[3].substring(pass1code[3].indexOf("'")+1,pass1code[3].lastIndexOf("'")) ;
                       }
                   }
               }else {
                   //遇到RESW or RESB換行
                   if(Textline.length() > 0){
                       String textlength = Hex2padding(Integer.toHexString(Textline.length()/2),2) ;
                       TextHead += (textlength+Textline) ;
                       //System.out.println(TextHead);
                       bwrite.write(TextHead.toUpperCase()+"\r\n");
                   }
                   if(pass1code[2].equals("RESW")){
                       //每個word*3
                       TextHead ="T"+Hex2padding(Integer.toHexString(Integer.parseInt(pass1code[0],16)+Integer.parseInt(pass1code[3])*3),6);
                   }else{ //RESB
                       //將byte轉成16進制
                       TextHead ="T"+Hex2padding(Integer.toHexString(Integer.parseInt(pass1code[0],16)+Integer.parseInt(pass1code[3])),6);
                   }
                   Textline = "" ;
                   text = "" ;
               }
                //扣除T+起始位置(6碼)+長度(2碼)69-9 若加上本行若超過60則print
                if(Textline.length()+text.length() > 60){
                    String textlength = Hex2padding(Integer.toHexString(Textline.length()/2),2) ;
                    TextHead += (textlength+Textline) ;
                    //System.out.println(TextHead);
                    bwrite.write(TextHead.toUpperCase()+"\r\n");
                    TextHead ="T"+Hex2padding(pass1code[0],6);
                    Textline = text ;
                }else //未超過60個字，將本行加入
                    Textline += text ;
            }
            //bwrite.flush();
        }
        bwrite.flush();
    }
    //內部類別Opcode
    public static class Opcode{
        Map opcode = new HashMap();
        Opcode(){
            opcode.put("ADD","18");
            opcode.put("AND","40");
            opcode.put("COMP","28");
            opcode.put("DIV","24");
            opcode.put("J","3C");
            opcode.put("JEQ","30");
            opcode.put("JGT","34");
            opcode.put("JLT","38");
            opcode.put("JSUB","48");
            opcode.put("LDA","00");
            opcode.put("LDCH","50");
            opcode.put("LDL","08");
            opcode.put("LDX","04");
            opcode.put("MUL","20");
            opcode.put("OR","44");
            opcode.put("RD","D8");
            opcode.put("RSUB","4C");
            opcode.put("STA","0C");
            opcode.put("STCH","54");
            opcode.put("STL","14");
            opcode.put("STSW","E8");
            opcode.put("STX","10");
            opcode.put("SUB","1C");
            opcode.put("TD","E0");
            opcode.put("TIX","2C");
            opcode.put("WD","DC");
        }
        
        String getOpocde(String flag){
            return  (String)opcode.get(flag);
        }
    }
    
    public void setLoc(String loc){
        Loc = loc.toUpperCase() ;
    }
    public String getLoc(){
        return Loc ;
    }
    
    public void setSymtab(String key,String value){
        symtab.put(key,value);
    }
    
    public String getSymtab(String key){
        return (String)symtab.get(key);
    }
    /**
     * 將16進位補足位數
     * @param num
     * @param dig 位數
     * @return
     */
    public String Hex2padding(String num,int dig){
    	String int2hex = "" ;
    	for(int i = num.length() ; i < dig ; i++){
    		int2hex += "0" ;
    	}
    	return int2hex+num ;
    }
}
