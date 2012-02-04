package ar.dgarcia.http.simple.impl.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;

import ar.dgarcia.http.simple.impl.ConnectionClient;

/**
 * Esta clase permite que el cliente realize conexiones https con dominios que no poseen un
 * certificado autenticado por una entidad registrante.<br>
 * Se utiliza en conjunto con {@link ConnectionClient}.
 * 
 * @author Maximiliano Vázquez
 * 
 */
@SuppressWarnings("deprecation")
public class AnyDomainSSLSocketFactory extends SSLSocketFactory {

	private final javax.net.ssl.SSLSocketFactory factory;

	public AnyDomainSSLSocketFactory() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException,
			UnrecoverableKeyException {
		super((KeyStore) null);

		final SSLContext sslcontext = SSLContext.getInstance("TLS");
		sslcontext.init(null, new TrustManager[] { new X509TrustManagerWithoutValidation() }, null);
		this.factory = sslcontext.getSocketFactory();
		this.setHostnameVerifier(new AllowAllHostnameVerifier());
	}

	public static SocketFactory getDefault() throws Exception {
		return new AnyDomainSSLSocketFactory();
	}

	@Override
	public Socket createSocket() throws IOException {
		return this.factory.createSocket();
	}

	@Override
	public Socket createSocket(final Socket socket, final String s, final int i, final boolean flag) throws IOException {
		return this.factory.createSocket(socket, s, i, flag);
	}

	public Socket createSocket(final InetAddress inaddr, final int i, final InetAddress inaddr1, final int j)
			throws IOException {
		return this.factory.createSocket(inaddr, i, inaddr1, j);
	}

	public Socket createSocket(final InetAddress inaddr, final int i) throws IOException {
		return this.factory.createSocket(inaddr, i);
	}

	public Socket createSocket(final String s, final int i, final InetAddress inaddr, final int j) throws IOException {
		return this.factory.createSocket(s, i, inaddr, j);
	}

	public Socket createSocket(final String s, final int i) throws IOException {
		return this.factory.createSocket(s, i);
	}

	public String[] getDefaultCipherSuites() {
		return this.factory.getDefaultCipherSuites();
	}

	public String[] getSupportedCipherSuites() {
		return this.factory.getSupportedCipherSuites();
	}
}