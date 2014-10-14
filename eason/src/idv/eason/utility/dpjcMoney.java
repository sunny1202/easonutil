package idv.eason.utility;

import java.text.DecimalFormat;

public class dpjcMoney {
	
    private double dMoney; 
    private boolean bShowDoolars=true;
    
    public dpjcMoney(double money){
        dMoney=money;       
    }
    
    public dpjcMoney(double money, boolean showDollars){
        dMoney=money;       
        bShowDoolars=showDollars;       
    }
    
    public String getMoney(){
        long integral;
        long decimal;
        String strInt;
        String strDecimal;      
        integral = (int)dMoney; //眔俱计    
        if (integral<10){
              strInt = String.valueOf(integral+100).substring(1); //俱计锣﹃ぃìㄢ玡干箂
          }else{
              strInt=String.valueOf(integral);
          }
        DecimalFormat df=new DecimalFormat("0.00"); 
        // System.out.println("df.format(Num) "+df.format(Num));
        strDecimal=df.format(dMoney).substring(String.valueOf(integral).length()+1);      
        decimal=Integer.parseInt(strDecimal); //眔计
        
        return ConvertNum2US(strInt,strDecimal);
    }
    
    protected static String getOneUnit(int i){    
        String rs="";
        switch(i) {
        case 0: rs=""; break;
        case 1: rs= "ONE ";break;
        case 2: rs= "TWO ";break;
        case 3: rs= "THREE ";break;
        case 4: rs= "FOUR ";break;
        case 5: rs= "FIVE ";break;
        case 6: rs= "SIX ";break;
        case 7: rs= "SEVEN "; break;
        case 8: rs= "EIGHT ";break;
        case 9: rs= "NINE "; 
        }     
        return rs;
    }

    protected static String getTenUnit(int i){
        String rs="";
        switch (i) {
        case 10: rs = "TEN ";break;
        case 11: rs = "ELEVEN ";break;
        case 12: rs = "TWELVE ";break;
        case 13: rs = "THIRTEEN ";break;
        case 14: rs = "FOURTEEN ";break;
        case 15: rs = "FIFTEEN ";break;
        case 16: rs = "SIXTEEN ";break;
        case 17: rs = "SEVENTEEN ";break;
        case 18: rs = "EIGHTEEN ";break;
        case 19: rs = "NINETEEN ";break;
        }
        return rs;
    }
    
    protected static String getTwoUnit(int i){
        String s,rs;
        int d1,d2;
        s = String.valueOf(i);      
        d1=Integer.parseInt(s.substring(0,1));      
        d2=Integer.parseInt(s.substring(1,2));
        switch (d1){
        case 2: rs = "TWENTY ";break;
        case 3: rs = "THIRTY ";break;
        case 4: rs = "FORTY ";break;
        case 5: rs = "FIFTY ";break;
        case 6: rs = "SIXTY ";break;
        case 7: rs = "SEVENTY ";break;
        case 8: rs = "EIGHTY ";break;
        default : rs = "NINETY ";break;
        }
        
        return rs + getOneUnit(d2);
    }
    
    protected static String getMoneyUnit(int digit){
        String rs="";
        if ((digit>=3)&&(digit<=17)) {
          if(digit%3==0){
              rs = "HUNDRED ";
          }
          if((digit==4)||(digit==5)){
              rs = "THOUSAND ";
          }
          if((digit==7)||(digit==8)){
              rs = "MILLION ";
          }
          if((digit==10)||(digit==11)){
              rs = "BILLION ";
          }
          if((digit==13)||(digit==14)){
              rs = "TRILLION ";
          }
          if((digit==16)||(digit==17)){
              rs = "QUADRILLION ";
          }
        }
        return rs;
    }
    
    protected String ConvertNum2US(String Int,String Dec){
        return ConvertNum2US(Int,Dec,bShowDoolars);
    }   
    
//  盢计锣璣ゅ砯刽ボ
    protected String ConvertNum2US(String strInt,String strDecimal,boolean showDollars){    
      int i, lenInt;
      long integral;
      long decimal;
      //String strInt, strDecimal;
      String strFull;
      String[] saInt;;
      String dollar="", cent, strDollars=" ";
     
      integral = Integer.parseInt(strInt); //眔俱计     
      DecimalFormat df=new DecimalFormat("0.00");   
     // System.out.println("df.format(Num) "+df.format(Num));
      //strDecimal=df.format(Num).substring(String.valueOf(integral).length()+1);     
      decimal=Integer.parseInt(strDecimal); //眔计
     // System.out.println("strDecimal "+strDecimal);
    //  System.out.println("decimal "+decimal);
    //  if (integral<10){
    //    strInt = String.valueOf(integral+100).substring(1); //俱计锣﹃ぃìㄢ玡干箂
    //  }else{
    //    strInt=String.valueOf(integral);
    //  }
        
      lenInt = strInt.length();
      saInt=new String[lenInt];           
      strFull = strInt + '.' + strDecimal;
          
      for (i=0;i<lenInt;i++){
          saInt[i] = String.valueOf(strInt.charAt(i));
      }
      
      
      
      switch (lenInt){
      case 1:
      case 2:
            switch (Integer.parseInt(saInt[0])){
            case 0: dollar = getOneUnit(Integer.parseInt(saInt[1]));break;
            case 1: dollar = getTenUnit((int)integral);break;
            default: dollar = getTwoUnit((int)integral);break;         
            }           
            //--- 计场だ
            switch (Integer.parseInt((strDecimal.substring(0,1)))){
            case 0: cent = getOneUnit(Integer.parseInt(strDecimal.substring(1, 2)));break;
            case 1: cent = getTenUnit((int)decimal);break;
            default: cent = getTwoUnit((int)decimal);break;
            }
      
            if (cent !="") {            
              if ((dollar !="")||(strInt.equals("00"))) 
                cent = "AND " +(decimal == 1?"CENT ":"CENTS ")+ cent;
//            if (decimal == 1)
//              cent ="CENT " +cent;
//            else
//              cent ="CENTS "+ cent;
            }
            if ((dollar == "")&&(!strInt.equals("00"))) {
              dollar = cent;
            }
            else{           
             if (showDollars){
              if (integral == 1)
                  strDollars= "DOLLAR ";
              else
                  strDollars= "DOLLARS ";
             }
             dollar = dollar + strDollars + cent + "ONLY";
              
            }
            //---
          break;
      case 3: case 4: case 6: case 7: case 9:case 10:case 12:case 13:case 15:case 16:         
          switch (Integer.parseInt(saInt[0])){
          case 0:
              dollar = dollar + ConvertNum2US(strInt.substring(1),strDecimal,showDollars);
              break;
          default:
            dollar = getOneUnit(Integer.parseInt(saInt[0]));            
            dollar = dollar + getMoneyUnit(lenInt) + ConvertNum2US(strInt.substring(1),strDecimal,showDollars);
          break;
          }
          break;
      case 5:case 8:case 11:case 14:case 17:         
            switch (Integer.parseInt(saInt[0])){
            case 0:
                dollar = getOneUnit(Integer.parseInt(saInt[1]));
                break;
                case 1: dollar = getTenUnit(Integer.parseInt(saInt[0] + saInt[1]));break;
                default: dollar = getTwoUnit(Integer.parseInt(saInt[0] + saInt[1]));break;
            }
            dollar = dollar + getMoneyUnit(lenInt) + ConvertNum2US(strInt.substring(2),strDecimal,showDollars);
          break;          
      }
      return dollar;
    }
    
    public static void main(String[] arg)throws Exception{
        dpjcMoney money=new dpjcMoney(100600.00,true);
        String usa = money.getMoney();
        System.out.println(usa);
        
    }
}
