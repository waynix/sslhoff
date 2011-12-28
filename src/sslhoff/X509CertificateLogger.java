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
import java.util.ArrayList;

import sun.misc.BASE64Encoder;

public class X509CertificateLogger
{
	private String folder;
	FileOutputStream log;

	public X509CertificateLogger(String folder, String logFileName)
			throws IOException
	{
		this.log = new FileOutputStream(logFileName);
		this.folder = folder;
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
