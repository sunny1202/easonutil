package idv.eason.AD;

import java.util.Hashtable;

import javax.naming.Context;

import javax.naming.NamingException;

import javax.naming.ldap.InitialLdapContext;

import javax.naming.ldap.LdapContext;

 

public class ADTest {

 

    public static void main(String[] args)

    {

        String ldapURL = "ldap://192.168.30.30:389";

        String account = "Q2153";

        String password = "sunny83";

        try{

           LDAP_AUTH_AD(ldapURL, account, password);

            System.out.println("�{�Ҧ��\!");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

     

    /******************************

     * LDAP�{��

     * @throws Exception

     ******************************/

    public static void LDAP_AUTH_AD(String ldap_url, String account, String password) throws Exception {

        if (account.isEmpty() || password.isEmpty())

            throw new Exception("�{�ҥ���!");

         

        Hashtable env = new Hashtable();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

        env.put(Context.PROVIDER_URL, ldap_url);

        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        env.put(Context.SECURITY_PRINCIPAL, account+"@fenghsin.com.tw");

        env.put(Context.SECURITY_CREDENTIALS, password);

 

        LdapContext ctx = null;

        try {

            ctx = new InitialLdapContext(env, null);

        } catch (javax.naming.AuthenticationException e) {

            throw new Exception("�{�ҥ���!");

        } catch (javax.naming.CommunicationException e) {

            throw new Exception("�䤣����A��!");

        } catch (Exception e) {

            throw new Exception("�o�ͥ��������~!");

        } finally {

            if (ctx != null) {

                try {

                    ctx.close();

                } catch (NamingException e) {

                }

            }

        }     

    }

}
