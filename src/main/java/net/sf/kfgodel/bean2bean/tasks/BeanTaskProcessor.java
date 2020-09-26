/**
 * Created on 23/12/2007 00:23:51 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.tasks;

import net.sf.kfgodel.tasks.Task;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Esta clase representa el procesador de tareas de beans que permite resolver en forma iterativa la
 * creacion y copiado de datos entre beans
 * 
 * @version 1.0
 * @since 23/12/2007
 * @author D. Garcia
 */
public class BeanTaskProcessor {

	private Map<Task<?>, Task<?>> registeredTasks;
	private Deque<Task<?>> tareasPendientes;

	/**
	 * Agrega la tarea pasada para ser resuelta por este procesador. Si la tarea ya existe como
	 * tarea abierta, no sera agregada y se le indica
	 * 
	 * @param tarea
	 *            Tarea a resolver
	 */
	public void addTask(final Task<?> tarea) {
		if (this.getTareasPendientes().contains(tarea)) {
			// Se detecto un ciclo por que la tarea depende de si misma
			tarea.cycleDetected();
			return;
		}
		// Se registra la tarea en el mapa para facil obtencion
		this.getRegisteredTasks().put(tarea, tarea);
		this.getTareasPendientes().addFirst(tarea);
		tarea.prepareTask();
	}

	private Map<Task<?>, Task<?>> getRegisteredTasks() {
		return registeredTasks;
	}

	private Deque<Task<?>> getTareasPendientes() {
		return tareasPendientes;
	}

	/**
	 * Devuelve la tarea pendiente, o procesada por este procesador que es igual a la tarea pasada
	 * 
	 * @param <T>
	 *            Tipo del resultado de las tareas
	 * @param referenceTask
	 *            Tarea de referencia que sera usado para buscar la tarea registrada en este
	 *            procesador
	 * @return Una tarea pendiente, o resuelta, o null si no se registro ninguna
	 */
	@SuppressWarnings("unchecked")
	public <T> Task<T> getTaskLike(final Task<T> referenceTask) {
		return (Task<T>) this.getRegisteredTasks().get(referenceTask);
	}

	/**
	 * Procesa una por una todas las tareas pendientes
	 */
	public void processTasks() {
		while (!this.getTareasPendientes().isEmpty()) {
			final Task<?> nextTask = this.getTareasPendientes().getFirst();
			// Si tiene subtareas, se prepararan primero las subtareas
			if (nextTask.hasPendingSubTasks()) {
				final Task<?> subtask = nextTask.getNextSubTask();
				this.addTask(subtask);
				continue;
			}
			// Si ya no quedan subtareas, se procesa la tarea en si
			nextTask.endTask();
			this.getTareasPendientes().removeFirst();
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" ");
		builder.append(this.getTareasPendientes());
		return builder.toString();
	}

	public static BeanTaskProcessor create() {
		final BeanTaskProcessor beanTaskProcessor = new BeanTaskProcessor();
		beanTaskProcessor.registeredTasks = new HashMap<Task<?>, Task<?>>();
		beanTaskProcessor.tareasPendientes = new LinkedList<Task<?>>();
		return beanTaskProcessor;
	}
}
