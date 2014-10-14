package idv.eason.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Infix2Postfix {
    
    private Stack stack;
    private String infixExp;
    private String postfixExp = "";
     
    public Infix2Postfix(String exp){
     
    String str = "";
    infixExp = exp;
    stack = new Stack();
    /*
     * Ū�J�C�@�Ӧr��
     */
    for (int i=0;i<infixExp.length();i++){
        str = infixExp.substring(i,i+1);
        if(str.matches("\\d"))
            postfixExp += str;
        else if (isOperator(str)){
            String stackTop = null ;
            if (stack.isEmpty()){
                postfixExp += " ";
                stack.push(str);
            }else{
                if(str.equals("(")){
                    stack.push(str);
                }else if (str.equals(")")){
                    stackTop = (String)stack.peek();
                    do{
                        postfixExp += " "+stack.pop();
                        stackTop = (String)stack.peek();
                    }while(!stackTop.equals("("));
                    stack.pop();
                }else{
                    stackTop = (String)stack.peek();
                    while (getPrecedence(stackTop,str).equals(stackTop)&& !(stack.isEmpty())){
                        postfixExp +=" ";
                        postfixExp += stack.pop();
                        if (!(stack.isEmpty()))
                            stackTop = (String)stack.peek();
                    }
                    postfixExp += " ";
                    stack.push(str);
                }
            }
        }
    }
    //�����ñN�bstack��������X
    while(!(stack.isEmpty())){
        postfixExp += " ";
        postfixExp += stack.pop();
    }
    }
     
    /*
    * �^�ǰѼƬO���Ooperator
    */
    private boolean isOperator(String ch){
     
        String operators = "*/%+-()";
        if (operators.indexOf(ch) != -1)
            return true;
        else
            return false;
    }
         
    /*
     * ����G��operator
     * 
    */
    private String getPrecedence(String op1, String op2){
     
        String multiplicativeOps = "*/%";
        String additiveOps = "+-";
        String bracket = "(";
         
        if ((multiplicativeOps.indexOf(op1) != -1) && (additiveOps.indexOf(op2) != -1))
            return op1;
        else if ((multiplicativeOps.indexOf(op2) != -1) && (additiveOps.indexOf(op1) != -1))
            return op2;
        else if((multiplicativeOps.indexOf(op1) != -1) && (multiplicativeOps.indexOf(op2) != -1))
            return op1;
        else if(bracket.indexOf(op1) >= 0)
            return op2;
        else
            return op1;
    }
    /*
     * �Npostfix�ǤJ�íp��ӭ�
     */
    public void evaluate(String postfix) {
        //�ϥΪťչj�}�C�@�����
        StringTokenizer st = new StringTokenizer(postfix);
        int a;
        int b;
        stack = new Stack();
        while (st.hasMoreTokens()) {
            int result = 0;
            String var = st.nextToken();
            char ch = var.charAt(0);
            switch (ch) {
                case '+':
                    a = Integer.parseInt((String)stack.pop());
                    b = Integer.parseInt((String)stack.pop());
                    result = (b + a);
                    stack.push(String.valueOf(result));
                    break;
                case '-':
                    a = Integer.parseInt((String)stack.pop());
                    b = Integer.parseInt((String)stack.pop());
                    result = (b - a);
                    stack.push(String.valueOf(result));
                    break;
                case '*':
                    a = Integer.parseInt((String)stack.pop());
                    b = Integer.parseInt((String)stack.pop());
                    result = (b * a);
                    stack.push(String.valueOf(result));
                    break;
                case '/':
                    a = Integer.parseInt((String)stack.pop());
                    b = Integer.parseInt((String)stack.pop());
                    result = (b / a);
                    stack.push(String.valueOf(result));
                    break;
                default:
                    stack.push(var);
                    break;
            }
        }
    }
     
    public static void main(String[] args){
        BufferedReader keyin = new BufferedReader(new InputStreamReader(System.in));
        String expression = null;
        try {
            while(true){
                System.out.println("�п�JExpression in Infix format.���}�п�J'q':");
                expression = keyin.readLine();
                //��Jq���}
                if(expression.equals("q"))
                    break;
                
                Infix2Postfix conver2 = new Infix2Postfix(expression);
                System.out.println("Expression in postfix is �G"+conver2.postfixExp);
                //�Npostfix�ǤJ�íp��ӭ�
                conver2.evaluate(conver2.postfixExp);
                System.out.println("�p�⪺���G���G"+(String)conver2.stack.pop());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            try {
                keyin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}