/**
 * 29/06/2013 21:46:26 Copyright (C) 2011 Darío L. García
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
package net.sf.kfgodel.bean2bean.population.metadata;

import net.sf.kfgodel.bean2bean.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.annotations.CopyFromAndTo;
import net.sf.kfgodel.bean2bean.annotations.CopyTo;
import net.sf.kfgodel.bean2bean.instantiation.ObjectFactory;
import net.sf.kfgodel.bean2bean.metadata.ClassPopulationMetadata;
import net.sf.kfgodel.bean2bean.population.PopulationType;

/**
 * Esta interfaz representa el extractor de la información de omo popular las clases.<br>
 * Bean2bean la utiliza para obtener las instrucciones de populación.
 * 
 * @author D. García
 */
public interface ClassPopulationMetadataExtractor {

	/**
	 * Devuelve el conjunto de instrucciones que indican como realizar la populacion del tipo
	 * indicado, a partir de la metada de la clase pasada.<br>
	 * Las instrucciones son resultantes de los annotations {@link CopyFromAndTo}, {@link CopyTo} y
	 * {@link CopyFrom}.<br>
	 * Las intrucciones devueltas son calculadas la primera vez.
	 * 
	 * @param metadataClass
	 *            Clase anotada que define como realizar la populacion
	 * @param populationType
	 *            Tipo de populacion que se realizara, hacia o desde esa clase
	 * @param objectFactory
	 *            Factory de instancias para crear cuando son necesarias
	 * @return Conjunto de instrucciones para realizar la populacion indicada
	 */
	ClassPopulationMetadata getMetadataFor(final Class<?> metadataClass, final PopulationType populationType,
			ObjectFactory objectFactory);

}