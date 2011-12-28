package sslhoff;

import java.io.*;
import java.net.*;
import java.security.cert.X509Certificate;
import java.util.NoSuchElementException;


import javax.net.ssl.*;

public class SslProxyConnect
{
	private Proxy transceiver;
	private X509CertificateLogger logger;

	public SslProxyConnect(ProxyDefinition proxyDef, X509CertificateLogger logger)
	{
		transceiver = new Proxy(proxyDef.getType(), proxyDef.getAddress());
		this.logger = logger;
	}

	public String connectTo(String urlString) throws Exception
	{
		
		String resp = "";

		URL url;
		HttpsURLConnection urlConn;

		DataInputStream input;

		try
		{

			HostnameVerifier hv = new HostnameVerifier()
			{
				public boolean verify(String urlHostName, SSLSession session)
				{
					System.out.println("Warning: URL Host: " + urlHostName
							+ " vs. " + session.getPeerHost());
					return true;
				}
			};
			// Now you are telling the JRE to trust any https server.
			// If you know the URL that you are connecting to then this should
			// not be a problem
			trustAllHttpsCertificates();
			HttpsURLConnection.setDefaultHostnameVerifier(hv);

			url = new URL(urlString);
			urlConn = (HttpsURLConnection) url.openConnection(transceiver);
			urlConn.setDoInput(true);
			urlConn.setUseCaches(false);

			urlConn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
			input = new DataInputStream(urlConn.getInputStream());
			String str = "";
			while (null != ((str = input.readLine())))
			{
				if (str.length() > 0)
				{
					str = str.trim();
					if (!str.equals(""))
					{
						// System.out.println(str);
						resp += str;
					}
				}
			}
			
			X509Certificate[] certlist = (X509Certificate[]) urlConn
					.getServerCertificates();
			for (X509Certificate x509Certificate : certlist)
			{
				try {
					logger.log(urlString+";"+this.transceiver.address(),x509Certificate);
				}
				catch(NullPointerException e) {
					
				}
			}
			urlConn.disconnect();
			
		} catch (MalformedURLException mue)
		{
			System.out.println("Malformed Url:"+mue.getMessage());
			return "";
		} catch (IOException ioe)
		{
			System.out.println("IOException connecting via " + transceiver+ "message:"+ioe.getMessage());
			return "";
		}
		catch (NoSuchElementException nse)
		{
			return "";
		}

		System.out.println("Success connecting via " + transceiver);
		return resp;
	}

	
	public static class miTM implements javax.net.ssl.TrustManager,
			javax.net.ssl.X509TrustManager
	{
		public java.security.cert.X509Certificate[] getAcceptedIssuers()
		{
			return null;
		}

		public boolean isServerTrusted(
				java.security.cert.X509Certificate[] certs)
		{
			return true;
		}

		public boolean isClientTrusted(
				java.security.cert.X509Certificate[] certs)
		{
			return true;
		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException
		{
			return;
		}

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException
		{
			return;
		}
	}

	private static void trustAllHttpsCertificates() throws Exception
	{

		// Create a trust manager that does not validate certificate chains:

		javax.net.ssl.TrustManager[] trustAllCerts =

		new javax.net.ssl.TrustManager[1];

		javax.net.ssl.TrustManager tm = new miTM();

		trustAllCerts[0] = tm;

		javax.net.ssl.SSLContext sc =

		javax.net.ssl.SSLContext.getInstance("SSL");

		sc.init(null, trustAllCerts, null);

		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(

		sc.getSocketFactory());

	}

}
