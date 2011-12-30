package sslhoff;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
		if(args.length < 2)
		{
			System.out.println("Call this program with proxylistfile websitelistfile as parameters");
			return;
		}
		ProxyDefLoader loader = new ProxyDefLoader();
		loader.load(args[0]);
		LinkedList<String> urls = new LinkedList<String>();

		
		BufferedReader lineSeparatedUrls = new BufferedReader(new FileReader(args[1]));
		while(lineSeparatedUrls.ready())
		{
			String line = lineSeparatedUrls.readLine();
			urls.add(line);
		}
		
		ArrayList<ProxyDefinition> proxyDefs = loader.getProxyDefs();
		Iterator<ProxyDefinition> it = proxyDefs.iterator();
		
		while(it.hasNext()) {
			ProxyDefinition def = it.next();
			System.out.println(def);
		}
		X509CertificateLogger logger = new X509CertificateLogger("certs/", "log.csv", "error.log");
		SiteChecker checker = new SiteChecker(urls, proxyDefs,logger);
		
		try {
			checker.check();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}

}
