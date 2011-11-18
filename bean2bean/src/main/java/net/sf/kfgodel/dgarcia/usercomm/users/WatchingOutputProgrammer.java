/**
 * Created on 09/01/2007 22:42:13 Copyright (C) 2007 Dario L. Garcia
 * 
 * <a rel="license" href="http://creativecommons.org/licenses/by/2.5/ar/"><img
 * alt="Creative Commons License" style="border-width:0"
 * src="http://i.creativecommons.org/l/by/2.5/ar/88x31.png" /></a><br />
 * <span xmlns:dc="http://purl.org/dc/elements/1.1/"
 * href="http://purl.org/dc/dcmitype/InteractiveResource" property="dc:title"
 * rel="dc:type">Bean2Bean</span> by <a xmlns:cc="http://creativecommons.org/ns#"
 * href="https://sourceforge.net/projects/bean2bean/" property="cc:attributionName"
 * rel="cc:attributionURL">Dar&#237;o Garc&#237;a</a> is licensed under a <a rel="license"
 * href="http://creativecommons.org/licenses/by/2.5/ar/">Creative Commons Attribution 2.5 Argentina
 * License</a>.<br />
 * Based on a work at <a xmlns:dc="http://purl.org/dc/elements/1.1/"
 * href="https://bean2bean.svn.sourceforge.net/svnroot/bean2bean"
 * rel="dc:source">bean2bean.svn.sourceforge.net</a>
 */

package net.sf.kfgodel.dgarcia.usercomm.users;

import net.sf.kfgodel.dgarcia.comm.handlers.ActionRepertoire;
import net.sf.kfgodel.dgarcia.comm.handlers.DefaultActionRepertoire;
import net.sf.kfgodel.dgarcia.comm.handlers.MessageHandler;
import net.sf.kfgodel.dgarcia.usercomm.msgs.BadException;
import net.sf.kfgodel.dgarcia.usercomm.msgs.ErroneousExecution;
import net.sf.kfgodel.dgarcia.usercomm.msgs.ErrorType;
import net.sf.kfgodel.dgarcia.usercomm.msgs.ProgrammerMessage;

/**
 * /** Esta clase representa al programador default. Todas las comunicaciones con este programador
 * son llevadas a cabo a traves de la salida estandar y de errores, paralizando la ejecucion del
 * programa cuando se produce un error.
 * 
 * @version 1.0
 * @since 09/01/2007
 * @author D. Garcia
 */
public class WatchingOutputProgrammer implements Programmer {

	/**
	 * Repertorio de acciones a realizar al recibir un mensaje
	 */
	private ActionRepertoire repertoire;

	/**
	 * @see net.sf.kfgodel.dgarcia.usercomm.users.Programmer#receive(net.sf.kfgodel.dgarcia.usercomm.msgs.ProgrammerMessage)
	 */
	public void receive(ProgrammerMessage message) {
		Class<? extends ProgrammerMessage> messageType = message.getClass();
		MessageHandler<ProgrammerMessage> handler = this.getRepertoire().getHandlerFor(messageType);
		if (handler != null) {
			handler.handle(message);
		}
		ErroneousExecution.hasHappened("Message type not expected: " + messageType + " " + message,
				ErrorType.BAD_CONFIGURATION);
	}

	/**
	 * @return Obtiene el repertorio de esta instancia
	 */
	public ActionRepertoire getRepertoire() {
		if (repertoire == null) {
			repertoire = new DefaultActionRepertoire();
			this.configure(repertoire);
		}
		return repertoire;
	}

	/**
	 * Configura el repertorio de acciones de esta instancia
	 * 
	 * @param createdRepertoire
	 *            Repertorio recien creado
	 */
	private void configure(ActionRepertoire createdRepertoire) {
		createdRepertoire.doFor(BadException.class,
				new net.sf.kfgodel.dgarcia.comm.handlers.MessageHandler<BadException>() {
					public void handle(BadException message) {
						throw new RuntimeException(message.getSituationType() + ": " + message.getContextualMessage(),
								message.getOccuredException());
					}
				});
		createdRepertoire.doFor(ErroneousExecution.class,
				new net.sf.kfgodel.dgarcia.comm.handlers.MessageHandler<ErroneousExecution>() {
					public void handle(ErroneousExecution message) {
						System.err.println(message.getSituationType());
						throw new RuntimeException(message.getSituationType() + ": " + message.getErrorDescription(),
								message.getContextStack());
					}
				});
	}

	/**
	 * Establece el repertorio de acciones de este programador
	 * 
	 * @param repertoire
	 *            Acciones a realizar cuando se reciben los mensajes
	 */
	public void setRepertoire(ActionRepertoire repertoire) {
		this.repertoire = repertoire;
	}

}
