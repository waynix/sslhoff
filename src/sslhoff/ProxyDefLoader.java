package sslhoff;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProxyDefLoader {
	private ArrayList<ProxyDefinition> proxyDefs;
	
	private Pattern linePattern;
	
	ProxyDefLoader()
	{
		String sep = "\u0020";
		String fieldPattern = "([^" + sep + "]*)";
		String patternStr = sep;
		
		for(int i = 0; i < 15; i++) {
			patternStr += fieldPattern + sep;
		}
		
		linePattern = Pattern.compile(patternStr);	
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
		Matcher matcher = linePattern.matcher(line);
		
		// todo process
	}
}
