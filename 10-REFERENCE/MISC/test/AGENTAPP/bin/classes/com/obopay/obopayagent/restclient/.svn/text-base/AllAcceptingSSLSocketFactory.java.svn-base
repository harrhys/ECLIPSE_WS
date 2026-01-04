package com.obopay.obopayagent.restclient;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

public class AllAcceptingSSLSocketFactory extends SSLSocketFactory {
	SSLContext sslContext = SSLContext.getInstance("TLS");

	public AllAcceptingSSLSocketFactory(KeyStore truststore)
			throws NoSuchAlgorithmException, KeyManagementException,
			KeyStoreException, UnrecoverableKeyException {
		super(truststore);

		TrustManager tm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}
		};

		sslContext.init(null, new TrustManager[] { tm }, null);
	}
	
	@Override
	public Socket createSocket(Socket socket, String host, int port,
			boolean autoClose) throws IOException {
		Socket createSocket = sslContext.getSocketFactory().createSocket(socket, host, port,
						autoClose);
		if(createSocket instanceof SSLSocket) {
			SSLSocket sslSocket = (SSLSocket) createSocket;
			String []enabledCipherSuites = sslSocket.getEnabledCipherSuites();
			sslSocket.setEnabledCipherSuites(new String[] { 
					"SSL_RSA_WITH_RC4_128_MD5", "SSL_RSA_WITH_RC4_128_SHA", "TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA", "TLS_DHE_DSS_WITH_AES_128_CBC_SHA", "SSL_RSA_WITH_3DES_EDE_CBC_SHA", "SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", "SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA", "SSL_RSA_WITH_DES_CBC_SHA", "SSL_DHE_RSA_WITH_DES_CBC_SHA", "SSL_DHE_DSS_WITH_DES_CBC_SHA", "SSL_RSA_EXPORT_WITH_RC4_40_MD5", "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA", "SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA"
					 });
		}
		return createSocket;
	}

	@Override
	public Socket createSocket() throws IOException {
		return sslContext.getSocketFactory().createSocket();
	}
}
