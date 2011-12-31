/**
 * 28/12/2011 19:51:44 Copyright (C) 2011 Darío L. García
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
package ar.dgarcia.gtalkclient.tests;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import junit.framework.Assert;
import net.gaia.taskprocessor.api.TimeMagnitude;
import net.gaia.util.WaitBarrier;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.dgarcia.gtalkclient.api.GtalkConnection;
import ar.dgarcia.gtalkclient.api.GtalkMessageListener;
import ar.dgarcia.gtalkclient.api.GtalkServer;
import ar.dgarcia.gtalkclient.api.exceptions.BadCredentialsException;
import ar.dgarcia.gtalkclient.impl.SmackGtalkServer;

/**
 * Esta clase reune los tests de api del cliente gtalk
 * 
 * @author D. García
 */
public class GtalkApiTests {

	private GtalkServer server;

	@Before
	public void crearServer() {
		server = SmackGtalkServer.create();
	}

	@After
	public void cerrarServer() {
		server.closeConnections();
	}

	@Test
	public void deberiaPermitirConectarseConElServidorDeGtalkConUsuarioValido() {
		final GtalkConnection connection = server.openConnectionAs(EscenariosTests.VALID_USER_1,
				EscenariosTests.VALID_PASS_1, null);
		Assert.assertTrue("Debería indicar que está conectado apenas nos logueamos y todo salio bien",
				connection.isConnected());
	}

	@Test
	public void deberiaInformarErrorAlConectarseConUsuarioInvalido() {
		try {
			server.openConnectionAs(EscenariosTests.INVALID_USER, EscenariosTests.INVALID_PASS, null);
			Assert.fail("Debería tirar una excepcion por usuario invalido");
		} catch (final BadCredentialsException e) {
			// Es la excepcion correcta
		}
	}

	@Test
	public void deberiaPermitirCerrarLaConexion() {
		final GtalkConnection connection = server.openConnectionAs(EscenariosTests.VALID_USER_1,
				EscenariosTests.VALID_PASS_1, null);
		connection.close();
		Assert.assertFalse("Debería indicar que está desconectado", connection.isConnected());
	}

	@Test
	public void deberiaPermitirAbrirDosConexionesConUsuariosDistintos() {
		final GtalkConnection connection1 = server.openConnectionAs(EscenariosTests.VALID_USER_1,
				EscenariosTests.VALID_PASS_1, null);
		final GtalkConnection connection2 = server.openConnectionAs(EscenariosTests.VALID_USER_2,
				EscenariosTests.VALID_PASS_2, null);
		Assert.assertTrue("Debería estar conectado el primero", connection1.isConnected());
		Assert.assertTrue("Debería estar conectado el segundo", connection2.isConnected());
	}

	@Test
	public void deberiaPermitirEnviarMensajeDesdeUnUsuarioYRecibirloDesdeOtro() throws InterruptedException {
		final GtalkConnection connection1 = server.openConnectionAs(EscenariosTests.VALID_USER_1,
				EscenariosTests.VALID_PASS_1, null);
		final AtomicReference<String> receivedMessage = new AtomicReference<String>();
		final WaitBarrier lockDelMensaje = WaitBarrier.create();
		final GtalkConnection connection2 = server.openConnectionAs(EscenariosTests.VALID_USER_2,
				EscenariosTests.VALID_PASS_2, new GtalkMessageListener() {
					@Override
					public void onMessageReceivedFrom(final String sourceUser, final String message) {
						receivedMessage.set(message);
						lockDelMensaje.release();
					}
				});
		Assert.assertTrue("El segundo está conectado", connection2.isConnected());
		// Debemos esperar que el segundo aparezca conectado para el primero, de manera que el
		// mensaje llegue
		for (int i = 0; i < 100; i++) {
			if (!connection1.isContactPresent(EscenariosTests.VALID_USER_2)) {
				Thread.sleep(100);
			} else {
				// Ya está conectado!
				break;
			}
		}
		Assert.assertTrue("El primero debería ver al segundo como conectado",
				connection1.isContactPresent(EscenariosTests.VALID_USER_2));

		final String sendedMessage = "Mensaje de prueba";
		connection1.sendMessageTo(EscenariosTests.VALID_USER_2, sendedMessage);

		// Lamentablemente tenemos que esperar que el mensaje llegue de verdad
		lockDelMensaje.waitForReleaseUpTo(TimeMagnitude.of(10, TimeUnit.SECONDS));

		Assert.assertEquals("Deberíamos haber recibido el mensaje que mandamos", sendedMessage, receivedMessage.get());
	}

	@Test
	public void deberiaPermitirConocerTodosLosContactosDeUnUsuarioComoMails() {
		final GtalkConnection connection1 = server.openConnectionAs(EscenariosTests.VALID_USER_1,
				EscenariosTests.VALID_PASS_1, null);
		final Set<String> contactIds = connection1.getContacts();
		Assert.assertFalse("Debería tener al menos un contacto", contactIds.isEmpty());
		Assert.assertTrue("Debería tener al usuario 2 como contacto", contactIds.contains(EscenariosTests.VALID_USER_2));
	}

	@Test
	public void deberiaPermitirSaberSiUnContactoEstaConectado() {
		final GtalkConnection connection1 = server.openConnectionAs(EscenariosTests.VALID_USER_1,
				EscenariosTests.VALID_PASS_1, null);
		final boolean isOtherConnected = connection1.isContactPresent(EscenariosTests.VALID_USER_2);
		Assert.assertFalse("Debería estar desconectado", isOtherConnected);
	}

}
