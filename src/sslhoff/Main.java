package sslhoff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Iterator;

/***
 * SSLhoff
 * @author paul
 *
 */
public class Main
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ProxyDefLoader loader = new ProxyDefLoader();
		loader.load(args[0]);
		
		ArrayList<ProxyDefinition> proxyDefs = loader.getProxyDefs();
		Iterator<ProxyDefinition> it = proxyDefs.iterator();
		
		while(it.hasNext()) {
			ProxyDefinition def = it.next();
			System.out.println(def);
		}
		
		SiteChecker checker = new SiteChecker("https://www.google.com", proxyDefs);
		
		try {
			checker.check();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try
//		{
//		System.out.println("Welcome to SSLhoff");
//		//ProxyRequester tester = new ProxyRequester("121.192.32.221",1080, Proxy.Type.SOCKS);
//		SslProxyConnect tester = new SslProxyConnect("95.168.161.239",8080, Proxy.Type.HTTP);
//		
//		System.out.println(tester.connectTo("https://rupek.net"));
//		
//		} catch (Exception e)
//		{
//			// TODO Auto-generated catch block
//			System.out.println(e.getMessage());
//		}

	}

}
