package idv.eason.utility;

public class MoneyUtil {

    /** �j�g�Ʀr */
    private static final String[] NUMBERS = { "�s", "��", "�L", "��", "�v", "��", "��","�m", "��", "�h" };
    /** ��Ƴ�������� */
    private static final String[] IUNIT = { "����", "�B", "��", "�a", "�U", "�B", "��","�a", "��", "�B", "��", "�a", "��", "�B", "��", "�a" };
    /** �p�Ƴ�������� */
    private static final String[] DUNIT = { "��", "��", "��" };

    /**
     * �o��j�g���B�C
     */
    public static String toChinese(String str) {
      str = str.replaceAll(",", "");// �h��","
      String integerStr;// ��Ƴ����Ʀr
      String decimalStr;// �p�Ƴ����Ʀr
      //integerStr = str;
      // ��l�ơG������Ƴ����M�p�Ƴ���
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
//    integerStr�h����0�A�����h��decimalStr����0(�W�X�����˥h)
      if (!integerStr.equals("")) {
        integerStr = Long.toString(Long.parseLong(integerStr));
        if (integerStr.equals("0")) {
          integerStr = "";
        }
      }
      // overflow�W�X�B�z��O�A������^
      if (integerStr.length() > IUNIT.length) {
        System.out.println(str + ": �W�X�B�z��O");
        return str;
      }

      int[] integers = toArray(integerStr);// ��Ƴ����Ʀr
      //boolean isMust5 = isMust5(integerStr);// �]�m�U���
//      int[] decimals = toArray(decimalStr);// �p�Ƴ����Ʀr
      return getChineseInteger(integers);//, isMust5) + getChineseDecimal(decimals);
    }

    /**
     * ��Ƴ����M�p�Ƴ����ഫ���ƲաA�q����ܧC��
     */
    private static int[] toArray(String number) {
      int[] array = new int[number.length()];
      for (int i = 0; i < number.length(); i++) {
        array[i] = Integer.parseInt(number.substring(i, i + 1));
      }
      return array;
    }

    /**
     * �o�줤����B����Ƴ����C
     */
    private static String getChineseInteger(int[] integers){//, boolean isMust5) {
      StringBuffer chineseInteger = new StringBuffer("");
      int length = integers.length;
      for (int i = 0; i < length; i++) {
        // 0�X�{�b�����m�G1234(��)5678(��)9012(�U)3456(��)
        // �S���p�G10(�B���B���B���B���B�U���B�B�U��)
        String key = "";
        if (integers[i] == 0) {
          if ((length - i) == 13)// ��(����)
            key = IUNIT[4];
          else if ((length - i) == 9)// ��(����)
            key = IUNIT[8];
          else if ((length - i) == 5 )//&& isMust5)// �U(������)
            key = IUNIT[4];
          else if ((length - i) == 1)// ��(����)
            key = IUNIT[0];
          // 0�J�D0�ɸɹs�A���]�t�̫�@��
          if (key.equals("") && (length - i) > 1 && integers[i + 1] != 0)
            key += NUMBERS[0];
        }
        chineseInteger.append(integers[i] == 0 ? key:(NUMBERS[integers[i]] + IUNIT[length - i - 1]));
        //chineseInteger.append(integers[i] == 0 ? key:(integers[i]==1 && (length-i-1)==1)?IUNIT[length - i - 1]:(NUMBERS[integers[i]] + IUNIT[length - i - 1]));
      }
      return chineseInteger.toString();
    }
    /**
     * �o�줤����B���p�Ƴ����C
     */
    private static String getChineseDecimal(int[] decimals) {
      StringBuffer chineseDecimal = new StringBuffer("");
      for (int i = 0; i < decimals.length; i++) {
        // �˥h3��p�Ƥ��᪺
        if (i == 3)
          break;
        chineseDecimal.append(decimals[i] == 0 ? "":(NUMBERS[decimals[i]] + DUNIT[i]));
      }
      return chineseDecimal.toString();
    }

    /**
     * �P�_��5��Ʀr�����"�U"�O�_���[�C
     */
    private static boolean isMust5(String integerStr) {
      int length = integerStr.length();
      if (length > 4) {
        String subInteger = "";
        if (length > 8) {
          // ���o�q�C��ơA��5���8�쪺�r��
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
