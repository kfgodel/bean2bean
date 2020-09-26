/**
 * Created on: 22/09/2008 13:12:42 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.annotations;

import net.sf.kfgodel.bean2bean.exceptions.BadMappingException;
import net.sf.kfgodel.bean2bean.exceptions.MissingPropertyException;
import net.sf.kfgodel.bean2bean.exceptions.StopPopulationException;
import net.sf.kfgodel.bean2bean.population.setting.SetterInstruction;

/**
 * Este enum indica la accion a realizar si se encuentra un valor nulo en la cadena de propiedades
 * que permiten acceder al valor a copiar. O si no existe una de las propiedades indicadas en la
 * expresion que permite obtener el valor
 * 
 * @author Dario Garcia
 */
public enum MissingPropertyAction {
	/**
	 * Lanza la excepción por el error ocurrido
	 */
	THROW_ERROR {
		@Override
		public Object dealWithMissingPropertyOnGetter(final MissingPropertyException e) {
			throw new BadMappingException("Cannot get the value from the source bean: " + e.getMessage(), e);
		}

		@Override
		public Object dealWithMissingPropertyOnConversion(final MissingPropertyException e)
				throws StopPopulationException {
			throw new BadMappingException("Cannot determine expected in property: " + e.getMessage(), e);
		}

		@Override
		public void dealWithMissingPropertyOnSetter(final MissingPropertyException e,
				final SetterInstruction setterInstruction, final Object destination) throws StopPopulationException {
			throw new BadMappingException("Indicated property[" + setterInstruction.getOriginalExpression()
					+ "] was not found on destination bean: " + destination, e);
		}

		@Override
		public boolean allowPropertyChainsToCreateMissingInstances() {
			return false;
		}
	},
	/**
	 * Trata el caso como si el valor obtenido fuera null
	 */
	TREAT_AS_NULL {

		@Override
		public Object dealWithMissingPropertyOnGetter(final MissingPropertyException e) throws StopPopulationException {
			return null;
		}

		@Override
		public Object dealWithMissingPropertyOnConversion(final MissingPropertyException arg0)
				throws StopPopulationException {
			return null;
		}

		@Override
		public void dealWithMissingPropertyOnSetter(final MissingPropertyException arg0, final SetterInstruction arg1,
				final Object destination) throws StopPopulationException {
			throw new StopPopulationException();
		}

		@Override
		public boolean allowPropertyChainsToCreateMissingInstances() {
			return false;
		}

	},
	/**
	 * A medida que traversa el path de propiedades destino, si no encuentra una propiedad, trata de
	 * instanciar un objeto del tipo de la propiedad
	 */
	CREATE_MISSING_INSTANCES {
		@Override
		public Object dealWithMissingPropertyOnGetter(final MissingPropertyException e) throws StopPopulationException {
			throw e;
		}

		@Override
		public Object dealWithMissingPropertyOnConversion(final MissingPropertyException e)
				throws StopPopulationException {
			throw e;
		}

		@Override
		public void dealWithMissingPropertyOnSetter(final MissingPropertyException e,
				final SetterInstruction setterInstruction, final Object destination) throws StopPopulationException {
			throw e;
		}

		@Override
		public boolean allowPropertyChainsToCreateMissingInstances() {
			return true;
		}

	};

	/**
	 * Trata el caso de propiedad inexsitente al usarse el getter para obtener la propiedad
	 * 
	 * @param e
	 *            Excepcion que describe el error ocurrido con la propiedad faltante
	 * @return El valor que reemplaza al faltante
	 * @throws StopPopulationException
	 *             Si esta estrategia determina que se debe interrumpir la populación normalmente
	 * @throw BadMappingException Si esta estrategia determina que se debe interrumpir la populación
	 *        por un error de mapeo
	 * @throws MissingPropertyException
	 *             Si esta estrategia determina que se debe interrumpir la populación por un objeto
	 *             faltante
	 */
	public abstract Object dealWithMissingPropertyOnGetter(final MissingPropertyException e)
			throws StopPopulationException, BadMappingException, MissingPropertyException;

	/**
	 * Trata el caso de propiedad inexsitente al momento de determinar el tipo esperado
	 * 
	 * @param e
	 *            Excepcion que describe el error ocurrido
	 * @return El valor que corresponde al tipo esperado de la conversion que reemplazaria al que no
	 *         se pudo convertir
	 * @throws StopPopulationException
	 *             Si esta estrategia determina que se debe interrumpir la populación normalmente
	 * @throw BadMappingException Si esta estrategia determina que se debe interrumpir la populación
	 *        por un error de mapeo
	 * @throws MissingPropertyException
	 *             Si esta estrategia determina que se debe interrumpir la populación por un objeto
	 *             faltante
	 */
	public abstract Object dealWithMissingPropertyOnConversion(final MissingPropertyException e)
			throws StopPopulationException, MissingPropertyException;

	/**
	 * Trata el caso de propiedad inexistente al momento de asignar el valor en destino
	 * 
	 * @param e
	 *            La excepcion con el error ocurrido
	 * @param setterInstruction
	 *            Instruccion realizada para la asignacion
	 * @throws StopPopulationException
	 *             Si esta estrategia determina que se debe interrumpir la populación normalmente
	 * @throw BadMappingException Si esta estrategia determina que se debe interrumpir la populación
	 *        por un error de mapeo
	 * @throws MissingPropertyException
	 *             Si esta estrategia determina que se debe interrumpir la populación por un objeto
	 *             faltante
	 */
	public abstract void dealWithMissingPropertyOnSetter(final MissingPropertyException e,
			final SetterInstruction setterInstruction, final Object destination) throws StopPopulationException,
			BadMappingException, MissingPropertyException;

	/**
	 * Indica si las propiedades anidadas pueden crear instancias faltantes según esta estrategia
	 * 
	 * @return False si las propiedades faltantes ni pueden ser instanciadas
	 */
	public abstract boolean allowPropertyChainsToCreateMissingInstances();
}
