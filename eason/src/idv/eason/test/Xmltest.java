package idv.eason.test;

import java.io.*;
import java.util.Iterator;
import java.util.List;

import org.jdom.*;
import org.jdom.filter.ElementFilter;
import org.jdom.input.*;
public class Xmltest {

	public static void main(String[] args) {

		SAXBuilder sb=new SAXBuilder();
        try{
        	//File f = new File("D:/Project_ERP/eason/idv/eason/utility/Test.xml");
        	//FileInputStream fi = new FileInputStream(new File("D:/Project_ERP/eason/idv/eason/utility/Test.xml"));
            //Document doc=sb.build(new FileInputStream(new File("D:/Project_ERP/eason/idv/eason/utility/Test.xml")));

            Document doc=sb.build(new FileInputStream("D:/Project_ERP/eason/src/idv/eason/test/Test.xml"));
            /**方法一**/
            System.out.println("************方法一**************");
            Iterator itr = doc.getDescendants(new ElementFilter("Row"));
            int i = 1 ;
            while (itr.hasNext()) {
            	Element c = (Element) itr.next();
                System.out.println("********第"+(i++)+"筆資料***************");
            	List al = c.getChildren("Cell",Namespace.getNamespace(c.getNamespaceURI()));
            	for (int j = 0 ; j < al.size() ; j++){
            		Element data = (Element)al.get(j);
            		System.out.println(data.getChildText("Data",Namespace.getNamespace(data.getNamespaceURI())));
            	}
            }
            /**方法二**/
            System.out.println("************方法二**************");
            itr = doc.getDescendants(new ElementFilter("Cell"));
            while(itr.hasNext()){
            	Element c = (Element)itr.next();
            	System.out.println(c.getChildText("Data",Namespace.getNamespace(c.getNamespaceURI())));
            }

        }catch(Exception ex){
        	System.out.println(ex);
        }
    
		

	}

}
