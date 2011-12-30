package sslhoff;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class OneCall
{

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{

		X509CertificateLogger logger = new X509CertificateLogger("certs/",
				"ohneproxylog.csv", "error.log");
		BufferedReader lineSeparatedUrls = new BufferedReader(new FileReader(
				args[0]));
		LinkedList<String> urls = new LinkedList<String>();
		LinkedList<LinkedList<String>> superlist = new LinkedList<LinkedList<String>>();
		int i = 0;
		while (lineSeparatedUrls.ready())
		{
			if (i > 100)
			{
				superlist.add(urls);
				urls = new LinkedList<String>();
				i = 0;
			}
			String line = lineSeparatedUrls.readLine();
			urls.add(line);
			i++;
			
		}
		ArrayList<ProxyDefinition> proxyDefs = new ArrayList<ProxyDefinition>();
		ProxyDefinition noProxy = null;
		proxyDefs.add(noProxy);
		for (LinkedList<String> urlsublist : superlist)
		{
			
			
			SiteChecker checker = new SiteChecker(urlsublist, proxyDefs,logger);
			try {
				checker.check();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
				
			}
		}
	}

}
