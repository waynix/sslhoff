package sslhoff;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
public class X509CertificateLogger
{
	private String folder;
	FileOutputStream log;
	FileOutputStream error;

	public X509CertificateLogger(String folder, String logFileName, String errorLogger)
			throws IOException
	{
		this.log = new FileOutputStream(logFileName);
		this.error = new FileOutputStream(logFileName);
		this.folder = folder;
	}
	public void logError(String errorMessage) 
	{
		try
		{
			error.write(errorMessage.getBytes());
		} catch (IOException e)
		{
			System.out.println("Could not log error");
		}
	}

	private boolean store(String fileName, X509Certificate cert)
			throws IOException, NoSuchAlgorithmException,
			CertificateEncodingException
	{
		if (cert instanceof Serializable)
		{
			Serializable k = (Serializable) cert;
			FileOutputStream certfile = new FileOutputStream(fileName);
			ObjectOutputStream x = new ObjectOutputStream(certfile);
			x.writeObject(k);
			certfile.close();
		} else
		{
			System.out.println("could not Serialize Cert");
			error.write("could not Serialize Cert".getBytes());
		}
		return false;
	}

	synchronized void log(String identifier,X509Certificate cert) throws CertificateEncodingException,
			NoSuchAlgorithmException, IOException, InterruptedException
	{
		//BASE64Encoder b64 = new BASE64Encoder();
		//System.out.println(b64.encode(cert.getEncoded()));
		String fileName = "";
		MessageDigest sha = MessageDigest.getInstance("SHA");
		sha.reset();
		sha.update(cert.getEncoded());
		
		fileName = folder + String.format("%X",new BigInteger(sha.digest())) + ".ser";
		String logMessage = identifier+";"+fileName+"\r";
		log.write(logMessage.getBytes());
		File test = new File(fileName);
		
		if (!test.exists())
		{
			this.store(fileName, cert);
		}
		else
		{
			System.out.println("Duplicate:"+fileName);

		}
		
	}

}
