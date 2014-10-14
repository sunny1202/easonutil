package idv.eason.utility;

public class dejcFieldFormat {
	/**
	 * 字串補空白
	 * @param sfield 欲處理的字串
	 * @param len 欄位的長度
	 * @return 右邊補滿空白的字串
	 */
	public static String Stringfield(String sfield,int len){
		StringBuffer field = new StringBuffer(sfield);
		for(int i = sfield.length() ; i < len ; i++){
			field.append(" ");
		}
		return field.toString();
	}
	/**
	 * 數字前補0 
	 * @param dfield 欲處理的數字(int)
	 * @param len 欄位的長度
	 * @return 前面補滿0的字串
	 */
	public static String Decmalfield(int dfield,int len) {		
		StringBuffer field = new StringBuffer();
		for(int i = String.valueOf(dfield).length() ; i < len ; i++){
			field.append("0");
		}
		return field.toString()+String.valueOf(dfield);
	}
	/**
	 * 數字前補0
	 * @param dfield 欲處理的字串
	 * @param len 欄位的長度
	 * @return 前面補滿0的字串
	 */
	public static String Decmalfield(String dfield , int len){
		StringBuffer field = new StringBuffer();
		for(int i = dfield.length() ; i < len ; i++){
			field.append("0");
		}
		return field.toString()+dfield;
	}
	/**
	 * 字串用Byte來計算長度並右邊補滿空白
	 * @param sfield 欲處理的字串
	 * @param len 欄位的長度
	 * @return 右邊補滿空白的字串
	 */
	public static String StringfieldB(String sfield,int len){
		StringBuffer field = new StringBuffer(sfield);
		for(int i = sfield.getBytes().length ; i < len ; i++){
			field.append(" ");
		}
		return field.toString();
	}
	/* 範例
	public static void main(String[] arg){
		System.out.println(dejcFieldFormat.Stringfield("Stjjj豐興鋼鐵kjkj", 20));
		System.out.println(dejcFieldFormat.StringfieldB("Stjjj豐興鋼鐵kjkj", 20));
		System.out.println(dejcFieldFormat.Decmalfield(55, 8));
		System.out.println(dejcFieldFormat.Decmalfield("55", 8));
	}
	*/
}
