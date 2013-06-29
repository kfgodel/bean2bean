/**
 * Archivo creado: 15/11/2007 - 15:07:25
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
 * rel="dc:source">bean2bean.svn.sourceforge.net</a> por Dario García
 */
package net.sf.kfgodel.tasks;

/**
 * Esta interfaz representa una tarea a ser procesada por un procesador, que devuelve un valor como
 * resultado
 * 
 * @author Dario García
 * @param <R>
 *            Tipo del resultado producido por esta tarea
 */
public interface Task<R> {

	/**
	 * Indica a esta tarea que tiene una dependencia ciclica consigo misma, y que debe modificar el
	 * resultado, ya que no sera posible obtenerlo con todas sus dependencias (debe excluirse a si
	 * misma como dependencia)
	 */
	void cycleDetected();

	/**
	 * Indica a esta tarea el final de su procesamiento. En este punto la tarea puede armar el
	 * resultado final (de ser necesario).<br>
	 * Normalmente este metodo o {@link #prepareTask()} es el qe sea el procesamiento real de la
	 * tarea
	 */
	void endTask();

	/**
	 * @return Devuelve la proxima subtarea pendiente para resolver esta tarea
	 */
	Task<R> getNextSubTask();

	/**
	 * Devuelve el resultado de esta tarea. Este metodo debe ser llamado despues de que la tarea
	 * haya sido procesada
	 * 
	 * @return El resultado obtenido por esta tarea (puede ser null)
	 */
	R getResult();

	/**
	 * Indica si esta tarea posee subtareas a resolver antes de si misma
	 * 
	 * @return true si todavia puede devolver subtareas
	 */
	boolean hasPendingSubTasks();

	/**
	 * Indica a esta tarea que se esta comenzando su procesamiento y que debe preparar el estado
	 * necesario para procesarse. Si esta es una tarea compuesta, lo normal es que prepare las
	 * subtareas.<br>
	 * Normalmente este metodo o {@link #endTask()} es el qe sea el procesamiento real de la tarea
	 */
	void prepareTask();

}
