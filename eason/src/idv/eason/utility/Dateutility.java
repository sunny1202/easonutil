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
	 * �����w����A�h���X�{�b���
	 *
	 */
	public Dateutility(){
		this.cal = Calendar.getInstance();
	}
	/**
	 * �Ncalendar���w��Y�@���
	 * @param ondate �褸�~(yyyyMMdd)
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
	 * @param �ǤJDate����
	 * @return ������
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
	 * @param ymd �褸�~��� �ҡG20071114
	 * @return ������
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
	 * Dug�G���i�p��p��褸1500�~�Τj�����1500�~
	 * @param date ����榡���褸�~�Υ���~ ex.20071110�B961010
	 * @param compute �[��~
	 * @return �p�⧹��� yyyyMMdd
	 */
	public String computeYear(String date,int compute){
		//Calendar cal = Calendar.getInstance();
		boolean isRoc = false ;
		//��Ѥ���אּCalendar����
		int year = Integer.parseInt(date.substring(0,date.length()-4));
		int month = Integer.parseInt(date.substring(date.length()-4,date.length()-2))-1;
		int day = Integer.parseInt(date.substring(date.length()-2));
		//�P�_����~�Φ褸�~
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
	 * Dug�G���i�p��p��褸1500�~�Τj�����1500�~
	 * @param date ����榡���褸�~�Υ���~ ex.20071110�B961010
	 * @param compute �[���
	 * @return �p�⧹��� yyyyMMdd
	 */
	public String computeMonth(String date,int compute){
		//Calendar cal = Calendar.getInstance();
		boolean isRoc = false ;
//		��Ѥ���אּCalendar����
		int year = Integer.parseInt(date.substring(0,date.length()-4));
		int month = Integer.parseInt(date.substring(date.length()-4,date.length()-2))-1;
		int day = Integer.parseInt(date.substring(date.length()-2));
//		�P�_����~�Φ褸�~
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
	 * Dug�G���i�p��p��褸1500�~�Τj�����1500�~
	 * @param date ����榡���褸�~�Υ���~ ex.20071110�B961010
	 * @param compute �[���
	 * @return �p�⧹��� yyyyMMdd
	 */
	public String computeDay(String date,int compute){
		//Calendar cal = Calendar.getInstance();
		boolean isRoc = false ;
//		��Ѥ���אּCalendar����
		int year = Integer.parseInt(date.substring(0,date.length()-4));
		int month = Integer.parseInt(date.substring(date.length()-4,date.length()-2))-1;
		int day = Integer.parseInt(date.substring(date.length()-2));
//		�P�_����~�Φ褸�~
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
	 * �p��G����������Ѽ�
	 * @param day1 �褸�~
	 * @param day2 �褸�~
	 * @return
	 */
	public int CalcDays (String day1,String day2) {
		//�N����~�榡�令	yyyyMMdd
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
