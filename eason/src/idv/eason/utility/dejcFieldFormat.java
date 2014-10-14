package idv.eason.utility;

public class dejcFieldFormat {
	/**
	 * �r��ɪť�
	 * @param sfield ���B�z���r��
	 * @param len ��쪺����
	 * @return �k��ɺ��ťժ��r��
	 */
	public static String Stringfield(String sfield,int len){
		StringBuffer field = new StringBuffer(sfield);
		for(int i = sfield.length() ; i < len ; i++){
			field.append(" ");
		}
		return field.toString();
	}
	/**
	 * �Ʀr�e��0 
	 * @param dfield ���B�z���Ʀr(int)
	 * @param len ��쪺����
	 * @return �e���ɺ�0���r��
	 */
	public static String Decmalfield(int dfield,int len) {		
		StringBuffer field = new StringBuffer();
		for(int i = String.valueOf(dfield).length() ; i < len ; i++){
			field.append("0");
		}
		return field.toString()+String.valueOf(dfield);
	}
	/**
	 * �Ʀr�e��0
	 * @param dfield ���B�z���r��
	 * @param len ��쪺����
	 * @return �e���ɺ�0���r��
	 */
	public static String Decmalfield(String dfield , int len){
		StringBuffer field = new StringBuffer();
		for(int i = dfield.length() ; i < len ; i++){
			field.append("0");
		}
		return field.toString()+dfield;
	}
	/**
	 * �r���Byte�ӭp����רåk��ɺ��ť�
	 * @param sfield ���B�z���r��
	 * @param len ��쪺����
	 * @return �k��ɺ��ťժ��r��
	 */
	public static String StringfieldB(String sfield,int len){
		StringBuffer field = new StringBuffer(sfield);
		for(int i = sfield.getBytes().length ; i < len ; i++){
			field.append(" ");
		}
		return field.toString();
	}
	/* �d��
	public static void main(String[] arg){
		System.out.println(dejcFieldFormat.Stringfield("Stjjj�׿����Kkjkj", 20));
		System.out.println(dejcFieldFormat.StringfieldB("Stjjj�׿����Kkjkj", 20));
		System.out.println(dejcFieldFormat.Decmalfield(55, 8));
		System.out.println(dejcFieldFormat.Decmalfield("55", 8));
	}
	*/
}
