package sslhoff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.net.Proxy;

/***
 * SSLhoff
 * @author paul
 *
 */
public class Main
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
//		try
//		{
//			BufferedReader n = new BufferedReader(new FileReader(args[0]));
//			
//		} catch (FileNotFoundException e)
//		{
//			System.out.println("Inputfile not found");
//			return;
//		}
		
		try
		{
		System.out.println("Welcome to SSLhoff");
		SslProxyConnect tester = new SslProxyConnect(new ProxyDefinition(new InetSocketAddress("203.12.220.48", 80), Proxy.Type.HTTP));
		
		System.out.println(tester.connectTo("https://rupek.net"));
		
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

	}

}
