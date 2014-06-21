/**
 * Created on 22/12/2007 23:16:12 Copyright (C) 2007 Dario L. Garcia
 * 
 * * <a rel="license" href="http://creativecommons.org/licenses/by/2.5/ar/"><img
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
package net.sf.kfgodel.tasks;

/**
 * Esta interfaz representa un procesador de tareas al cual es posible asignarle tareas para irlas
 * resolviendo una por una
 * 
 * @version 1.0
 * @since 22/12/2007
 * @author D. Garcia
 */
public interface TaskProcessor {
	public void addNextTask(Task<?> tarea);

	/**
	 * Devuelve el resultado obtenido para la tarea pasada o null si no se resolvio anteriormente
	 * esa tarea
	 * 
	 * @param <R>
	 *            Tipo del resultado
	 * @param tarea
	 *            Tarea para la que quiere obtenerse el resultado
	 * @return El resultado previamente calculado o null si no existia ninguno
	 */
	public <R> R getResultFor(Task<R> tarea);

	/**
	 * Procesa la tarea indicada devolviendo el resultado de su procesamiento
	 * 
	 * @param <R>
	 *            Tipo del resultado devuelto
	 * @param tarea
	 *            Tarea a procesar
	 * @return El resultado obtenido por la tarea
	 */
	public <R> R process(Task<R> tarea);
}
