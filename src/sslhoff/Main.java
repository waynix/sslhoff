package sslhoff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/***
 * SSLhoff
 * @author paul
 *
 */
public class Main
{

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{
		
		ProxyDefLoader loader = new ProxyDefLoader();
		loader.load(args[0]);
		LinkedList<String> urls = new LinkedList<String>();
		urls.add("https://www.google.com");
		urls.add("https://heise.de");
		
		
		ArrayList<ProxyDefinition> proxyDefs = loader.getProxyDefs();
		Iterator<ProxyDefinition> it = proxyDefs.iterator();
		
		while(it.hasNext()) {
			ProxyDefinition def = it.next();
			System.out.println(def);
		}
		X509CertificateLogger logger = new X509CertificateLogger("certs/", "log.csv");
		SiteChecker checker = new SiteChecker(urls, proxyDefs,logger);
		
		try {
			checker.check();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}

}
