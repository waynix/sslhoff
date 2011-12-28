package sslhoff;

import java.net.Proxy;
import java.util.List;

public class SiteCheckerRunnable implements Runnable {

	private ProxyDefinition proxyDefinition;
	private List<String> urlStrings;
	X509CertificateLogger logger;
	SiteCheckerRunnable(ProxyDefinition proxyDefinition, List<String> urlStrings, X509CertificateLogger logger)
	{
		this.proxyDefinition = proxyDefinition;
		this.urlStrings = urlStrings;
		this.logger = logger;

	}
	

	public void run() {
		for (String urlString : urlStrings)
		{
			SslProxyConnect connector = new SslProxyConnect(proxyDefinition,logger);
			try {
				connector.connectTo(urlString);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}


	}

}
