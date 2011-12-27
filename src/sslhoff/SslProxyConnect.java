package sslhoff;

import java.io.*;
import java.net.*;
import java.security.PublicKey;
import java.security.Security;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

import javax.net.ssl.*;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64EncoderStream;

public class SslProxyConnect
{
	private Proxy transceiver;

	public SslProxyConnect(String address, int port, Proxy.Type proxytype)
	{
		SocketAddress sa = new InetSocketAddress(address, port);
		transceiver = new Proxy(proxytype, sa);
	}

	public String connectTo(String urlString) throws Exception
	{
		String resp = "";

		URL url;
		HttpsURLConnection urlConn;

		DataInputStream input;
		String str = "";

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

			urlConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			input = new DataInputStream(urlConn.getInputStream());

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

			// BASE64EncoderStream b64 = new BASE64EncoderStream(System.out);
			for (X509Certificate x509Certificate : certlist)
			{
				PublicKey pk = x509Certificate.getPublicKey();
				if(pk instanceof RSAPublicKey)
				{
				RSAPublicKey k = (RSAPublicKey) pk;
				System.out.println(k.getModulus().toString(16));
				}
				if(pk instanceof DSAPublicKey)
				{
					DSAPublicKey k = (DSAPublicKey) pk;
					System.out.println("DSA G:"+k.getParams().getG().toString(16));
					System.out.println("DSA P:"+k.getParams().getP().toString(16));
					System.out.println("DSA Q:"+k.getParams().getQ().toString(16));
					
				
				}
				

			}
			System.out.println();
			input.close();
		} catch (MalformedURLException mue)
		{
			mue.printStackTrace();
		} catch (IOException ioe)
		{
			ioe.printStackTrace();
		}

		return resp;
	}

	// Just add these two functions in your program

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
