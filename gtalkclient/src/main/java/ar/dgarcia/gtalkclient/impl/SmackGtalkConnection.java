/**
 * 31/12/2011 02:52:08 Copyright (C) 2011 Darío L. García
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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.dgarcia.gtalkclient.api.GtalkConnection;
import ar.dgarcia.gtalkclient.api.GtalkMessageListener;

import com.google.common.base.Objects;

/**
 * Esta clase representa una conexión al servidor de gtalk como un usuario en particular. A partir
 * de esta conexión es posible enviar y recibir mensajes
 * 
 * @author D. García
 */
public class SmackGtalkConnection implements GtalkConnection {
	private static final Logger LOG = LoggerFactory.getLogger(SmackGtalkConnection.class);

	private XMPPConnection userConnection;
	private GtalkMessageListener listener;
	private final PacketListener internalListener = new PacketListener() {
		@Override
		public void processPacket(final Packet packet) {
			if (packet instanceof Message) {
				final Message msg = (Message) packet;
				onMessageReceived(msg);
			} else {
				LOG.error("Recibimos un paquete que no es mensaje: " + packet);
			}
		}
	};

	/**
	 * Trata el mensaje recibido derivándolo al listener correspondiente
	 * 
	 * @param msg
	 *            El mensaje recibido
	 */
	protected void onMessageReceived(final Message msg) {
		if (listener == null) {
			// Como no hay listener no hacemos nada
			return;
		}
		final String messagebody = msg.getBody();
		if (messagebody == null) {
			LOG.debug("El mensaje vino vacio, omitiendo: " + msg);
			return;
		}
		final String sourceUser = msg.getFrom();
		try {
			listener.onMessageReceivedFrom(sourceUser, messagebody);
		} catch (final Exception e) {
			LOG.error("Se produjo un error en el listener del chat con el mensaje: " + msg, e);
		}
	}

	public static SmackGtalkConnection create(final XMPPConnection xmppConnection, final GtalkMessageListener listener) {
		final SmackGtalkConnection connection = new SmackGtalkConnection();
		connection.userConnection = xmppConnection;
		connection.listener = listener;
		connection.initializeInternalListener();
		return connection;
	}

	/**
	 * Establece y configura el listener actual
	 */
	private void initializeInternalListener() {
		// Hacemos que todos los mensajes vayan al receptor interno
		userConnection.addPacketListener(internalListener, new PacketTypeFilter(Message.class));
	}

	/**
	 * @see ar.dgarcia.gtalkclient.api.GtalkConnection#isConnected()
	 */
	@Override
	public boolean isConnected() {
		return userConnection.isConnected();
	}

	/**
	 * @see ar.dgarcia.gtalkclient.api.GtalkConnection#close()
	 */
	@Override
	public void close() {
		userConnection.disconnect();
	}

	/**
	 * @see ar.dgarcia.gtalkclient.api.GtalkConnection#sendMessageTo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void sendMessageTo(final String destinationUser, final String messageBody) {
		final Message xmppMessage = new Message();
		xmppMessage.setFrom(userConnection.getUser());
		xmppMessage.setTo(destinationUser);
		xmppMessage.setBody(messageBody);
		xmppMessage.setType(Message.Type.chat);
		userConnection.sendPacket(xmppMessage);
	}

	/**
	 * @see ar.dgarcia.gtalkclient.api.GtalkConnection#getContacts()
	 */
	@Override
	public Set<String> getContacts() {
		final LinkedHashSet<String> contactUsers = new LinkedHashSet<String>();
		final Roster roster = userConnection.getRoster();
		final Collection<RosterEntry> entries = roster.getEntries();
		final Iterator<RosterEntry> iterator = entries.iterator();
		while (iterator.hasNext()) {
			final RosterEntry entry = iterator.next();
			final String contactUser = entry.getUser();
			contactUsers.add(contactUser);
		}
		return contactUsers;
	}

	/**
	 * @see ar.dgarcia.gtalkclient.api.GtalkConnection#isContactPresent(java.lang.String)
	 */
	@Override
	public boolean isContactPresent(final String otherUser) {
		final Roster roster = userConnection.getRoster();
		final Presence presence = roster.getPresence(otherUser);
		final boolean isOtherUserPresent = presence.getType() == Presence.Type.available;
		return isOtherUserPresent;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("user", userConnection.getUser()).toString();
	}
}
