package sslhoff;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class OneCall
{

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{
		
		
	
		X509CertificateLogger logger = new X509CertificateLogger("certs/", "log.csv");

		
		try {
			SslProxyConnect tester = new SslProxyConnect(new ProxyDefinition(new InetSocketAddress("218.83.175.129", 80), Proxy.Type.HTTP),logger);

			System.out.println(tester.connectTo("https://rupek.net"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}

}
