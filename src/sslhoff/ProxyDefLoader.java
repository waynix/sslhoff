package sslhoff;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProxyDefLoader {
	private ArrayList<ProxyDefinition> proxyDefs = new ArrayList<ProxyDefinition>();
	
	ProxyDefLoader()
	{	
	}
	
	void load(String fileName)
	{
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			
			String line;
			while((line = reader.readLine()) != null) {
				processLine(line);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processLine(String line) {
		String[] fields = line.split("\\$\\$");
		assert(fields.length == 16);
		
		String ip = fields[1];
		//String domainName = fields[2]
		String port = fields[3];
		//String country = fields[13];
		
		InetSocketAddress address = new InetSocketAddress(ip, Integer.parseInt(port));
		ProxyDefinition def = new ProxyDefinition(address, Proxy.Type.HTTP);
		
		proxyDefs.add(def);
	}

	public ArrayList<ProxyDefinition> getProxyDefs() {
		return proxyDefs;
	}
}
