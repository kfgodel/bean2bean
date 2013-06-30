/**
 * Archivo creado: 15/11/2007 - 14:59:43 <a rel="license"
 * href="http://creativecommons.org/licenses/by/2.5/ar/"><img alt="Creative Commons License"
 * style="border-width:0" src="http://i.creativecommons.org/l/by/2.5/ar/88x31.png" /></a><br />
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

import java.util.Deque;
import java.util.LinkedList;

/**
 * Esta clase permite procesar una tarea que puede expandirse en subtareas recursivas y que podrian
 * generar un bucle.<br>
 * La manera de evitar el ciclo es cortar el proceso recursivo si se repite la misma tarea como
 * subtarea de si misma.<br>
 * Al encontrar el ciclo, esta clase delega la responsabilidad en el transformador de las tareas
 * para que devuelva un resultado valido del ciclo producido y poder seguir procesando.<br>
 * 
 * Los resultados obtenidos se cachean de manera de no recalcular para un a tarea dada.
 * 
 * @author Dario García
 * @param <R>
 *            Tipo de los resultados procesados por las tareas de este procesador
 */
public class AntiCyclicTaskProcesor<R> {

	private Deque<Task<R>> tareasAbiertasPendientes;

	/**
	 * Devuelve la lisa de tareas que estan pendiente de resolucion
	 * 
	 * @return Una lista ordenada de tareas donde la primera es la proxima a resolver
	 */
	protected Deque<Task<R>> getTareasAbiertasPendientes() {
		return this.tareasAbiertasPendientes;
	}

	/**
	 * Procesa la tarea pasada decomponiendola en subtareas y procesando cada una de ellas, previo a
	 * la resolucion de la tarea pasada
	 * 
	 * @param newTask
	 *            Tarea a procesar
	 * @return El resultado de la tarea procesada
	 */
	public R process(final Task<R> newTask) {
		this.startTask(newTask);
		while (!this.getTareasAbiertasPendientes().isEmpty()) {
			final Task<R> nextTask = this.getTareasAbiertasPendientes().getFirst();
			// Si tiene subtareas, se procesan primero las subtareas
			if (nextTask.hasPendingSubTasks()) {
				final Task<R> subtask = nextTask.getNextSubTask();
				this.startTask(subtask);
				continue;
			}
			// Si ya no quedan subtareas, se procesa la tarea en si
			nextTask.endTask();
			this.getTareasAbiertasPendientes().removeFirst();
		}
		return newTask.getResult();
	}

	/**
	 * Comienza la tarea pasada y la agrega a la pila de tareas pendientes
	 * 
	 * @param newTask
	 *            Tarea a comenzar
	 */
	private void startTask(final Task<R> newTask) {
		if (this.getTareasAbiertasPendientes().contains(newTask)) {
			// Se detecto un ciclo por que la tarea depende de si misma
			newTask.cycleDetected();
			return;
		}
		newTask.prepareTask();
		this.getTareasAbiertasPendientes().addFirst(newTask);
	}

	/**
	 * Crea un procesador de tareas recursivas (o posiblemente recursivas) que puede detectar ciclos
	 * en las tareas y evitarlos
	 * 
	 * @param <R>
	 *            Tipo del resultado devuelto por las tareas
	 * @return El procesador de las tareas
	 */
	public static <R> AntiCyclicTaskProcesor<R> create() {
		final AntiCyclicTaskProcesor<R> antiCyclicTaskProcesor = new AntiCyclicTaskProcesor<R>();
		antiCyclicTaskProcesor.tareasAbiertasPendientes = new LinkedList<Task<R>>();
		return antiCyclicTaskProcesor;
	}

}
