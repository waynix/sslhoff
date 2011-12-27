package sslhoff;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import sun.misc.Regexp;

public class ProxyDefLoader {
	private ArrayList<ProxyDefinition> proxyDefs;
	
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
		//Regexp re = "\u20([~\u20])\u20"
	}
}
