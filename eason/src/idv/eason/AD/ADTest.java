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

            System.out.println("認證成功!");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

     

    /******************************

     * LDAP認證

     * @throws Exception

     ******************************/

    public static void LDAP_AUTH_AD(String ldap_url, String account, String password) throws Exception {

        if (account.isEmpty() || password.isEmpty())

            throw new Exception("認證失敗!");

         

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

            throw new Exception("認證失敗!");

        } catch (javax.naming.CommunicationException e) {

            throw new Exception("找不到伺服器!");

        } catch (Exception e) {

            throw new Exception("發生未知的錯誤!");

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
