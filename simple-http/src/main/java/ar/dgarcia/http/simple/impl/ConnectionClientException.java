package ar.dgarcia.http.simple.impl;

/**
 * Indica un error en las comunicaciones. Generalmente algo que está mal configurado en el request
 * 
 * @author D.Garcia
 * @since 05/10/2009
 */
public class ConnectionClientException extends RuntimeException {
	private static final long serialVersionUID = -3883119664126591256L;

	public ConnectionClientException(final Throwable e) {
		super(e);
	}

	public ConnectionClientException(final String detailMessage, final Throwable throwable) {
		super(detailMessage, throwable);
	}

	public ConnectionClientException(final String detailMessage) {
		super(detailMessage);
	}

}
