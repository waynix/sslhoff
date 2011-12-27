package sslhoff;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Iterator;

public class SiteChecker {
	private String siteUrl;
	private ArrayList<ProxyDefinition> proxyDefs;
	
	
	public SiteChecker(String siteUrl, ArrayList<ProxyDefinition> proxyDefs)
	{
		this.siteUrl = siteUrl;
		this.proxyDefs = proxyDefs;
	}
	
	public void check() throws Exception
	{
		Iterator<ProxyDefinition> it = proxyDefs.iterator();
		while(it.hasNext())
		{
			Thread th = new Thread(new SiteCheckerRunnable(it.next(), this.siteUrl));
			th.start();
		}
	}
}
