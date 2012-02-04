package ar.dgarcia.http.simple.impl.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import ar.dgarcia.http.simple.impl.ConnectionClient;

/**
 * Implementación de un {@link X509TrustManager} que no realiza ninguna validación.<br>
 * Se utiliza en conjunto con {@link ConnectionClient}.
 * 
 * @author Maximiliano Vázquez
 * 
 */
public class X509TrustManagerWithoutValidation implements X509TrustManager {

	@Override
	public void checkClientTrusted(final X509Certificate[] cert, final String authType) throws CertificateException {
	}

	@Override
	public void checkServerTrusted(final X509Certificate[] cert, final String authType) throws CertificateException {
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}