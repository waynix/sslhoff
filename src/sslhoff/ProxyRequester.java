package sslhoff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


import javax.net.ssl.*;
public class ProxyRequester
{

	private Proxy transceiver;
	public ProxyRequester(String address, int port, Proxy.Type proxytype)
	{
		SocketAddress sa = new InetSocketAddress(address, port);
		transceiver = new Proxy(proxytype, sa);
	}
	public boolean connectTo(String surl)
	{
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
	        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	            return null;
	        }

	        public void checkClientTrusted(
	                java.security.cert.X509Certificate[] certs, String authType) {
	        }

	        public void checkServerTrusted(
	                java.security.cert.X509Certificate[] certs, String authType) {
	            System.out.println("authType is " + authType);
	            System.out.println("cert issuers");
	            for (int i = 0; i < certs.length; i++) {
	                System.out.println("\t" + certs[i].getIssuerX500Principal().getName());
	                System.out.println("\t" + certs[i].getIssuerDN().getName());
	           }
	        }
	    } };
		try
		{
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            
			URL url = new URL(surl);
			URLConnection genericconnection = url.openConnection(transceiver);
			if(genericconnection instanceof HttpsURLConnection)
			{
				
				HttpsURLConnection uc = (HttpsURLConnection) genericconnection;
				
				uc.connect();
				String page = "ssl";
				BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
				String line;
				while ((line  = in.readLine()) != null){
				   page+=(line + "\n");
				}
				System.out.println(page);
				
				
			}
			else
			{
				genericconnection.connect();
				String page = "";
				BufferedReader in = new BufferedReader(new InputStreamReader(genericconnection.getInputStream()));
				String line;
				while ((line  = in.readLine()) != null){
				   page+=(line + "\n");
				}
				System.out.println(page);
				
				
			}
			
			
			// on success return true
			return true;
		} catch (MalformedURLException e)
		{
			System.out.println("Url error:"+surl+" "+e.getMessage());
		}
		catch(IOException e)
		{
			System.out.println("Connectionerror:"+e.getMessage());
		}
		catch(NoSuchAlgorithmException e)
		{
			System.out.println("NoSuchAlgorithmException:"+e.getMessage());
		}
		catch(KeyManagementException e)
		{
			System.out.println("NoSuchAlgorithmException:"+e.getMessage());
		}
		return false;
	}
	
	public boolean saveCertificate()
	{
		return false;
	} 
	public void close()
	{
		
	}
	
	
	

}
