/**
 * 28/12/2011 19:55:20 Copyright (C) 2011 Darío L. García
 * 
 * <a rel="license" href="http://creativecommons.org/licenses/by/3.0/"><img
 * alt="Creative Commons License" style="border-width:0"
 * src="http://i.creativecommons.org/l/by/3.0/88x31.png" /></a><br />
 * <span xmlns:dct="http://purl.org/dc/terms/" href="http://purl.org/dc/dcmitype/Text"
 * property="dct:title" rel="dct:type">Software</span> by <span
 * xmlns:cc="http://creativecommons.org/ns#" property="cc:attributionName">Darío García</span> is
 * licensed under a <a rel="license" href="http://creativecommons.org/licenses/by/3.0/">Creative
 * Commons Attribution 3.0 Unported License</a>.
 */
package ar.dgarcia.gtalkclient.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import ar.dgarcia.gtalkclient.api.GtalkClientException;
import ar.dgarcia.gtalkclient.api.GtalkConnection;
import ar.dgarcia.gtalkclient.api.GtalkMessageListener;
import ar.dgarcia.gtalkclient.api.GtalkServer;
import ar.dgarcia.gtalkclient.api.exceptions.BadCredentialsException;
import ar.dgarcia.gtalkclient.api.exceptions.GtalkServerConnectionException;

import com.google.common.base.Objects;

/**
 * Esta clase es la implementación del servidor usando la librería Smack para las comunicaciones
 * 
 * @author D. García
 */
public class SmackGtalkServer implements GtalkServer {

	private List<GtalkConnection> createdConnections;
	public static final String createdConnections_FIELD = "createdConnections";

	public static SmackGtalkServer create() {
		final SmackGtalkServer server = new SmackGtalkServer();
		server.createdConnections = new ArrayList<GtalkConnection>(1);
		return server;
	}

	/**
	 * @see ar.dgarcia.gtalkclient.api.GtalkServer#openConnectionAs(java.lang.String,
	 *      java.lang.String, ar.dgarcia.gtalkclient.api.GtalkMessageListener)
	 */
	@Override
	public GtalkConnection openConnectionAs(final String userName, final String userPass,
			final GtalkMessageListener listener) throws GtalkClientException {
		final XMPPConnection xmppConnection = createLinkToGtalkServer();
		loginAs(userName, userPass, xmppConnection);
		final SmackGtalkConnection connection = SmackGtalkConnection.create(xmppConnection, listener);
		createdConnections.add(connection);
		return connection;
	}

	/**
	 * Modifica la conexión al servidor para que se loguee usando el usuario y pass indicados
	 * 
	 * @param userName
	 *            El usuario de gtalk
	 * @param userPass
	 *            El pass para validarlo
	 * @param xmppConnection
	 *            La conexión con la que se realizará
	 */
	private void loginAs(final String userName, final String userPass, final XMPPConnection xmppConnection) {
		try {
			// Necesario para evitar el error de autenticación en login
			SASLAuthentication.supportSASLMechanism("PLAIN", 0);
			xmppConnection.login(userName, userPass);
		} catch (final XMPPException e) {
			throw new BadCredentialsException("Se produjo un error al validar el usuario", e);
		}
	}

	/**
	 * Establece un enlace físico con el servidor de gtalk a partir del cual se puede establecer una
	 * conexión con login del usuario
	 * 
	 * @return La conexión creada
	 */
	private XMPPConnection createLinkToGtalkServer() throws GtalkServerConnectionException {
		try {
			final ConnectionConfiguration config = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
			final XMPPConnection xmppConnection = new XMPPConnection(config);
			xmppConnection.connect();
			return xmppConnection;
		} catch (final XMPPException e) {
			throw new GtalkClientException("No fue posible establecer un enlace al servidor de gtalk", e);
		}
	}

	/**
	 * @see ar.dgarcia.gtalkclient.api.GtalkServer#closeConnections()
	 */
	@Override
	public void closeConnections() {
		final Iterator<GtalkConnection> iterator = createdConnections.iterator();
		while (iterator.hasNext()) {
			final GtalkConnection gtalkConnection = iterator.next();
			if (gtalkConnection.isConnected()) {
				gtalkConnection.close();
			}
			iterator.remove();
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add(createdConnections_FIELD, createdConnections.size()).toString();
	}
}
