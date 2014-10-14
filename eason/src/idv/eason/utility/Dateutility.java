/**
 * @author Eason
 * @date 2007/11/14
 */
package idv.eason.utility;

import java.text.*;
import java.util.*;

public class Dateutility {
	private Calendar cal ;
	/**
	 * 未指定日期，則取出現在日期
	 *
	 */
	public Dateutility(){
		this.cal = Calendar.getInstance();
	}
	/**
	 * 將calendar指定到某一日期
	 * @param ondate 西元年(yyyyMMdd)
	 * @throws ParseException
	 */
	public Dateutility(String ondate) throws ParseException{
		this.cal = Calendar.getInstance();
		cal.setTime(new SimpleDateFormat("yyyyMMdd").parse(ondate));
	}
	public String getROC_Date(){
		DecimalFormat df = new DecimalFormat("#00");
		
		String month = df.format(cal.get(Calendar.MONTH)+1);
		String day = df.format(cal.get(Calendar.DAY_OF_MONTH));
		cal.add(Calendar.YEAR,-1911);
		String year = df.format(cal.get(Calendar.YEAR));
		
		return year+month+day;
	}
	/**
	 * 
	 * @param 傳入Date物件
	 * @return 民國日期
	 */
	public String getROC_Date(Date now){
		//Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		DecimalFormat df = new DecimalFormat("#00");
		String month = df.format(cal.get(Calendar.MONTH)+1);
		String day = df.format(cal.get(Calendar.DAY_OF_MONTH));
		cal.add(Calendar.YEAR,-1911);
		String year = df.format(cal.get(Calendar.YEAR));
		
		return year+month+day;
	}
	/**
	 * 
	 * @param ymd 西元年月日 例：20071114
	 * @return 民國日期
	 */
	public String getROC_Date(String ymd){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String Roc_date = "" ;
		try {
			Roc_date = getROC_Date(sdf.parse(ymd));
		} catch (ParseException e) {
			System.err.println(e);
		}
		return Roc_date ;
	}
	/**
	 * Dug：不可計算小於西元1500年或大於民國1500年
	 * @param date 日期格式為西元年或民國年 ex.20071110、961010
	 * @param compute 加減的年
	 * @return 計算完日期 yyyyMMdd
	 */
	public String computeYear(String date,int compute){
		//Calendar cal = Calendar.getInstance();
		boolean isRoc = false ;
		//拆解日期改為Calendar物件
		int year = Integer.parseInt(date.substring(0,date.length()-4));
		int month = Integer.parseInt(date.substring(date.length()-4,date.length()-2))-1;
		int day = Integer.parseInt(date.substring(date.length()-2));
		//判斷民國年或西元年
		if(year < 1500){
			year += 1911 ;
			isRoc = true ;
		}
		cal.set(year,month,day);
		String final_date = "" ;
		cal.add(Calendar.YEAR,compute);
		if(isRoc){
			final_date = getROC_Date(cal.getTime());
		}else{
			final_date = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		}
		
		return final_date;
	}
	/**
	 * Dug：不可計算小於西元1500年或大於民國1500年
	 * @param date 日期格式為西元年或民國年 ex.20071110、961010
	 * @param compute 加減的月
	 * @return 計算完日期 yyyyMMdd
	 */
	public String computeMonth(String date,int compute){
		//Calendar cal = Calendar.getInstance();
		boolean isRoc = false ;
//		拆解日期改為Calendar物件
		int year = Integer.parseInt(date.substring(0,date.length()-4));
		int month = Integer.parseInt(date.substring(date.length()-4,date.length()-2))-1;
		int day = Integer.parseInt(date.substring(date.length()-2));
//		判斷民國年或西元年
		if(year < 1500){
			year += 1911 ;
			isRoc = true ;
		}
		cal.set(year,month,day);
		String final_date = "" ;
		cal.add(Calendar.MONTH,compute);
		if(isRoc){
			final_date = getROC_Date(cal.getTime());
		}else{
			final_date = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		}
		
		return final_date;
	}
	/**
	 * Dug：不可計算小於西元1500年或大於民國1500年
	 * @param date 日期格式為西元年或民國年 ex.20071110、961010
	 * @param compute 加減的日
	 * @return 計算完日期 yyyyMMdd
	 */
	public String computeDay(String date,int compute){
		//Calendar cal = Calendar.getInstance();
		boolean isRoc = false ;
//		拆解日期改為Calendar物件
		int year = Integer.parseInt(date.substring(0,date.length()-4));
		int month = Integer.parseInt(date.substring(date.length()-4,date.length()-2))-1;
		int day = Integer.parseInt(date.substring(date.length()-2));
//		判斷民國年或西元年
		if(year < 1500){
			year += 1911 ;
			isRoc = true ;
		}
		cal.set(year,month,day);
		String final_date = "" ;
		cal.add(Calendar.DATE,compute);
		if(isRoc){
			final_date = getROC_Date(cal.getTime());
		}else{
			final_date = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		}
		return final_date;
	}
	/**
	 * 計算二日期之間的天數
	 * @param day1 西元年
	 * @param day2 西元年
	 * @return
	 */
	public int CalcDays (String day1,String day2) {
		//將民國年格式改成	yyyyMMdd
		DecimalFormat df = new DecimalFormat("00000000");
		day1 = df.format(Double.parseDouble(day1));
		day2 = df.format(Double.parseDouble(day2));
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMdd");
		
		int days = 0;
		try {
			days = (int)((sdf.parse(day1).getTime() - sdf.parse(day2).getTime())/(24*60*60*1000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days < 0 ?days*-1:days ;
	}
	
    public String calnum(String str) {
        DecimalFormat df = new DecimalFormat("#,###");
        Number a = null;
        try {
            a = df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return a.toString() ;
    }
	public static void main(String[] args)  {
        double a,b ;
        a = 1.1 ;
        b = 100 ;
        
		// TODO Auto-generated method stub
		//Calendar rightNow = Calendar.getInstance();
		//Date tw_date = rightNow.getTime() ;
		Dateutility du = new Dateutility();
        //System.out.print(a/b);
		//System.out.println("now:"+du.getROC_Date());
//		System.out.println("961110:"+du.computeMonth("961110",10));
//		System.out.println("970229:"+du.computeMonth("960229",-1));
//		System.out.println(du.computeMonth("20070130",40));
//		System.out.println(du.computeMonth("20080229",-10));
//		System.out.println("961110:"+du.computeDay("961110",10));
		//System.out.println("970229:"+du.computeDay("1010227",2));
//		System.out.println(du.computeDay("20070130",40));
//		System.out.println(du.computeDay("20080227",2));
		//System.out.println("970201-961231:"+du.CalcDays("960201","970301"));
        
        //Calendar cc = Calendar.getInstance().set(2011,6,21);
		GregorianCalendar Win32Epoch = new GregorianCalendar(1601, Calendar.JANUARY, 1);  
		  Win32Epoch.setTimeZone(TimeZone.getTimeZone("zh_TW"));  
		  Date Win32EpochDate = Win32Epoch.getTime();  
		  long TimeSinceWin32Epoch = 129741924846774773L / 10000 + Win32EpochDate.getTime();   
		  Date lastLogon = new Date(TimeSinceWin32Epoch);  
		System.out.println(lastLogon);
		//du.cal.setTimeInMillis(11644473600L);
		Date lastLogon1 = new Date(129741924846774773L/10000-11644473600000L); // 
        System.out.println(lastLogon);
//		System.out.println("YEAR:"+du.cal.get(Calendar.YEAR));
//		System.out.println("Month:"+du.cal.get(Calendar.MONTH)+1);
//		System.out.println("Day:"+du.cal.get(Calendar.DAY_OF_MONTH));
	}
}
