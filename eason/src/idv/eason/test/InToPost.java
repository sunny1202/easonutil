package idv.eason.test;

import javax.swing.JOptionPane;

public class InToPost {
    private LinkList list;
     
    private String inString;
     
    private String outString = "";
     
    public InToPost myStack;
     
    public InToPost(String in) {
        inString = in;
        myStack = new InToPost();
    }
     
    public InToPost() {
        list = new LinkList();
    }
     
    public void push(char j) {
        list.insertFront(j);
    }
     
    public char pop() {
        return list.deleteFront();
    }
     
    public boolean isEmpty() {
        return (list.isEmpty());
    }
     
    public void displayStack() {
        System.out.print("Stack: ");
        list.displayList();
    }
     
    class LinkList {
        private Link head;
         
        public LinkList() {
            head = null;
        }
         
        public boolean isEmpty() {
            return (head == null);
        }
         
        public void insertFront(char d) {
            Link newLink = new Link(d);
            newLink.next = head;
            head = newLink;
        }
         
        public char deleteFront() {
            Link temp = head;
            head = head.next;
            return temp.data;
        }
         
        public void displayList() {
            Link current = head;
            while (current != null) {
                current.displayLink();
                current = current.next;
            }
            System.out.println("");
        }
         
        class Link {
            public char data; // data item
             
            public Link next; // next link in list
             
            public Link(char d) {
                data = d;
            }
             
            public void displayLink() {
                System.out.print(data + " ");
            }
        }
    }
    public String doTrans() {
        for (int j = 0; j < inString.length(); j++) {
            char ch = inString.charAt(j);
            switch (ch) {
                case '+':
                case '-':
                    gotOper(ch, 1);
                    break; // (precedence 1)
                case '*': // it's * or /
                case '/':
                    gotOper(ch, 2); // go pop operators
                    break; // (precedence 2)
                case '(': // it's a left paren
                    myStack.push(ch); // push it
                    break;
                case ')': // it's a right paren
                    gotParen(ch); // go pop operators
                    break;
                default: // must be an operand
                    outString = outString + ch; // write it to output
                    System.out.println(outString);
                    break;
            }
        }
     
        while (!myStack.isEmpty()) {
            outString = outString + myStack.pop();
        }
        System.out.println(outString);
        return outString; // return postfix
    }
    public void evaluate() {
        char a;
        char b;
        for (int j = 0; j < outString.length(); j++) {
            char result = 0;
            char ch = outString.charAt(j);
            switch (ch) {
                case '+':
                    a = myStack.pop();
                    b = myStack.pop();
                    result = (char) (a + b);
                    myStack.push(result);
                    break;
                case '-':
                    a = myStack.pop();
                    b = myStack.pop();
                    result = (char) (a - b);
                    myStack.push(result);
                    break;
                case '*':
                    a = myStack.pop();
                    b = myStack.pop();
                    result = (char) (a * b);
                    myStack.push(result);
                    break;
                case '/':
                    a = myStack.pop();
                    b = myStack.pop();
                    result = (char) (a / b);
                    myStack.push(result);
                    break;
                default:
                    myStack.push(ch);
                    break;
            }
        }
        System.out.println(myStack.pop());
    }
     
    public void gotOper(char opThis, int prec1) {
        while (!myStack.isEmpty()) {
            char opTop = myStack.pop();
            if (opTop == '(') {
                myStack.push(opTop);
                break;
            }// it's an operator
            else {// precedence of new op
                int prec2;
                if (opTop == '+' || opTop == '-')
                    prec2 = 1;
                else
                    prec2 = 2;
                if (prec2 < prec1) // if prec of new operator is less
                { // than prec of old
                    myStack.push(opTop); // save newly-popped operator
                    break;
                } else
                // prec of new operator is not less than prec of old
                outString = outString + opTop;
            }
        }
        myStack.push(opThis);
    }
    public void gotParen(char ch){
        while (!myStack.isEmpty()) {
            char chx = myStack.pop();
            if (chx == '(')
                break;
            else
                outString = outString + chx;
        }
    }
    public static void main(String[] args) {
        String inString = JOptionPane.showInputDialog("Enter your expression: ");
        System.out.println(inString);
        String outString;
        InToPost theTrans = new InToPost(inString);
        outString = theTrans.doTrans();
        System.out.println("Postfix is " +outString+ '\n');
        InToPost calculate = new InToPost(outString);
        calculate.evaluate();
    }
}