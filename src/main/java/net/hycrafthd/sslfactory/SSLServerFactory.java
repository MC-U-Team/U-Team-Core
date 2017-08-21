package net.hycrafthd.sslfactory;

import java.security.*;

import javax.net.ssl.*;

public class SSLServerFactory {
	
	public static SSLServerSocket create(String injarpath, String password, int port) throws Exception {
		KeyStore keystore = KeyStore.getInstance("JKS");
		KeyManagerFactory keymanagerfactory = KeyManagerFactory.getInstance("SunX509");
		TrustManagerFactory trustmanagerfactory = TrustManagerFactory.getInstance("SunX509");
		SSLContext sslcontext = SSLContext.getInstance("TLS");
		
		keystore.load(SSLServerFactory.class.getResourceAsStream(injarpath), password.toCharArray());
		keymanagerfactory.init(keystore, password.toCharArray());
		trustmanagerfactory.init(keystore);
		sslcontext.init(keymanagerfactory.getKeyManagers(), trustmanagerfactory.getTrustManagers(), new SecureRandom());
		
		return (SSLServerSocket) sslcontext.getServerSocketFactory().createServerSocket(port);
	}
	
}
