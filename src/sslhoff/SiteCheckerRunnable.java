package sslhoff;

import java.net.Proxy;

public class SiteCheckerRunnable implements Runnable {

	private ProxyDefinition proxyDefinition;
	private String urlString;
	
	SiteCheckerRunnable(ProxyDefinition proxyDefinition, String urlString)
	{
		this.proxyDefinition = proxyDefinition;
		this.urlString = urlString;
	}
	
	@Override
	public void run() {
		SslProxyConnect connector = new SslProxyConnect(proxyDefinition);
		try {
			connector.connectTo(urlString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
