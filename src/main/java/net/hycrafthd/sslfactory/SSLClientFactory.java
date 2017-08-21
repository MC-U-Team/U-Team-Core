package net.hycrafthd.sslfactory;

import java.security.*;
import java.security.cert.CertificateFactory;

import javax.net.ssl.*;

public class SSLClientFactory {
	
	public static SSLSocket create(String injarpath, String hostname, int port) throws Exception {
		KeyStore keystore = KeyStore.getInstance("JKS");
		CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
		TrustManagerFactory trustmanagerfactory = TrustManagerFactory.getInstance("SunX509");
		SSLContext sslcontext = SSLContext.getInstance("TLS");
		
		keystore.load(null, null);
		keystore.setCertificateEntry("certificate", certificatefactory.generateCertificate(SSLClientFactory.class.getResourceAsStream(injarpath)));
		trustmanagerfactory.init(keystore);
		sslcontext.init(null, trustmanagerfactory.getTrustManagers(), new SecureRandom());
		
		return (SSLSocket) sslcontext.getSocketFactory().createSocket(hostname, port);
	}
	
}
