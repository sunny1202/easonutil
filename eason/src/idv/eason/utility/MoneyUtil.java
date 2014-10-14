package idv.eason.utility;

public class MoneyUtil {

    /** 糶计 */
    private static final String[] NUMBERS = { "箂", "滁", "禠", "把", "竩", "ヮ", "嘲","琺", "", "╤" };
    /** 俱计场だ虫 */
    private static final String[] IUNIT = { "じ俱", "珺", "ㄕ", "", "窾", "珺", "ㄕ","", "货", "珺", "ㄕ", "", "", "珺", "ㄕ", "" };
    /** 计场だ虫 */
    private static final String[] DUNIT = { "à", "だ", "络" };

    /**
     * 眔糶肂
     */
    public static String toChinese(String str) {
      str = str.replaceAll(",", "");// 奔","
      String integerStr;// 俱计场だ计
      String decimalStr;// 计场だ计
      //integerStr = str;
      // ﹍てだ瞒俱计场だ㎝计场だ
      if (str.indexOf(".") > 0) {
        integerStr = str.substring(0, str.indexOf("."));
        decimalStr = str.substring(str.indexOf(".") + 1);
      } else if (str.indexOf(".") == 0) {
        integerStr = "";
        decimalStr = str.substring(1);
      } else {
        integerStr = str;
        decimalStr = "";
      }
//    integerStr奔0ぃゲ奔decimalStrЮ0(禬场だ彼)
      if (!integerStr.equals("")) {
        integerStr = Long.toString(Long.parseLong(integerStr));
        if (integerStr.equals("0")) {
          integerStr = "";
        }
      }
      // overflow禬矪瞶钡
      if (integerStr.length() > IUNIT.length) {
        System.out.println(str + ": 禬矪瞶");
        return str;
      }

      int[] integers = toArray(integerStr);// 俱计场だ计
      //boolean isMust5 = isMust5(integerStr);// 砞竚窾虫
//      int[] decimals = toArray(decimalStr);// 计场だ计
      return getChineseInteger(integers);//, isMust5) + getChineseDecimal(decimals);
    }

    /**
     * 俱计场だ㎝计场だ锣传计舱眖蔼
     */
    private static int[] toArray(String number) {
      int[] array = new int[number.length()];
      for (int i = 0; i < number.length(); i++) {
        array[i] = Integer.parseInt(number.substring(i, i + 1));
      }
      return array;
    }

    /**
     * 眔いゅ肂俱计场だ
     */
    private static String getChineseInteger(int[] integers){//, boolean isMust5) {
      StringBuffer chineseInteger = new StringBuffer("");
      int length = integers.length;
      for (int i = 0; i < length; i++) {
        // 0瞷闽龄竚1234()5678(货)9012(窾)3456(じ)
        // 疭薄猵10(珺じ滁珺じ滁珺窾じ珺窾じ)
        String key = "";
        if (integers[i] == 0) {
          if ((length - i) == 13)// (ゲ恶)
            key = IUNIT[4];
          else if ((length - i) == 9)// 货(ゲ恶)
            key = IUNIT[8];
          else if ((length - i) == 5 )//&& isMust5)// 窾(ぃゲ恶)
            key = IUNIT[4];
          else if ((length - i) == 1)// じ(ゲ恶)
            key = IUNIT[0];
          // 0笿獶0干箂ぃ程
          if (key.equals("") && (length - i) > 1 && integers[i + 1] != 0)
            key += NUMBERS[0];
        }
        chineseInteger.append(integers[i] == 0 ? key:(NUMBERS[integers[i]] + IUNIT[length - i - 1]));
        //chineseInteger.append(integers[i] == 0 ? key:(integers[i]==1 && (length-i-1)==1)?IUNIT[length - i - 1]:(NUMBERS[integers[i]] + IUNIT[length - i - 1]));
      }
      return chineseInteger.toString();
    }
    /**
     * 眔いゅ肂计场だ
     */
    private static String getChineseDecimal(int[] decimals) {
      StringBuffer chineseDecimal = new StringBuffer("");
      for (int i = 0; i < decimals.length; i++) {
        // 彼3计ぇ
        if (i == 3)
          break;
        chineseDecimal.append(decimals[i] == 0 ? "":(NUMBERS[decimals[i]] + DUNIT[i]));
      }
      return chineseDecimal.toString();
    }

    /**
     * 耞材5计虫"窾"琌莱
     */
    private static boolean isMust5(String integerStr) {
      int length = integerStr.length();
      if (length > 4) {
        String subInteger = "";
        if (length > 8) {
          // 眔眖计材5材8﹃
          subInteger = integerStr.substring(length - 8, length - 4);
        } else {
          subInteger = integerStr.substring(0, length - 4);
        }
        return Integer.parseInt(subInteger) > 0;
      } else {
        return false;
      }
    }

    public static void main(String[] args) {
      String number = "109119";
      System.out.println(number + " " + MoneyUtil.toChinese(number));
    }


}
