/**
 * Created on 09/01/2007 22:34:12 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.usercomm;

import net.sf.kfgodel.dgarcia.sistema.Sistema;
import net.sf.kfgodel.dgarcia.spaceconf.SpaceConfiguration;
import net.sf.kfgodel.dgarcia.usercomm.users.Programmer;
import net.sf.kfgodel.dgarcia.usercomm.users.WatchingOutputProgrammer;

/**
 * Esta clase representa un "alcanzador" del programador que permite obtener una instancia de el
 * para comunicarse con la persona
 * 
 * @version 1.0
 * @since 09/01/2007
 * @author D. Garcia
 */
public class ProgrammerReacher {

	/**
	 * Busca una instancia del programador segun la configuracion del sistema. Si el sistema cuenta
	 * con un configurador espacial en runtime, se utilizara la instancia de este para llegar al
	 * programador. Si no existe una configuracion espacial en tiempo de ejecucion o la
	 * configuracion no incluye un programador especial, se utilizara el
	 * {@link WatchingOutputProgrammer}.
	 * 
	 * @return La instancia de programador que se pudo obtener
	 */
	public static Programmer reachInstance() {
		Programmer programmer = SpaceConfiguration.getInstance().findInside(Sistema.class, Programmer.class);
		if (programmer == null) {
			programmer = new WatchingOutputProgrammer();
		}
		return programmer;
	}

}
