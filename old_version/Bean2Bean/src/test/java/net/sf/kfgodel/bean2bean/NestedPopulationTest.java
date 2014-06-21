/**
 * 18/07/2013 11:47:54 Copyright (C) 2011 10Pines S.R.L.
 */
package net.sf.kfgodel.bean2bean;

import net.sf.kfgodel.bean2bean.annotations.MissingPropertyAction;
import net.sf.kfgodel.bean2bean.exceptions.BadMappingException;
import net.sf.kfgodel.bean2bean.testbeans.nested.ObjetoAnidado;
import net.sf.kfgodel.bean2bean.testbeans.nested.ObjetoAnidadoToCopyFromAndToCreateInstance;
import net.sf.kfgodel.bean2bean.testbeans.nested.ObjetoAnidadoToCopyFromAndToDefault;
import net.sf.kfgodel.bean2bean.testbeans.nested.ObjetoAnidadoToCopyFromAndToNull;
import net.sf.kfgodel.bean2bean.testbeans.nested.ObjetoAnidadoToCopyFromCreateInstance;
import net.sf.kfgodel.bean2bean.testbeans.nested.ObjetoAnidadoToCopyFromDefault;
import net.sf.kfgodel.bean2bean.testbeans.nested.ObjetoAnidadoToCopyFromNull;
import net.sf.kfgodel.bean2bean.testbeans.nested.ObjetoAnidadoToCopyToCreateInstance;
import net.sf.kfgodel.bean2bean.testbeans.nested.ObjetoAnidadoToCopyToDefault;
import net.sf.kfgodel.bean2bean.testbeans.nested.ObjetoAnidadoToCopyToNull;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta clase prueba distintos esquemas de populacion de propiedades anidadas, con distintos valores
 * para {@link MissingPropertyAction}, y para distintas direcciones de mapeos
 * 
 * @author D. García
 */
public class NestedPopulationTest {
	private static final Logger LOG = LoggerFactory.getLogger(NestedPopulationTest.class);

	private Bean2Bean bean2bean;

	@Before
	public void crearConverter() {
		this.bean2bean = Bean2Bean.createDefaultInstance();

	}

	// ****
	// Con CopyFrom
	// *****

	@Test
	public void itShouldThrowAnErrorByDefaultDuringConversionWhenMissingPropertyInCopyFrom() {
		final ObjetoAnidado raiz = ObjetoAnidado.create("raiz");
		Assert.assertNull("No debería crear una instancia intermedia", raiz.getNested());

		try {
			final ObjetoAnidadoToCopyFromDefault converted = bean2bean.createFrom(raiz,
					ObjetoAnidadoToCopyFromDefault.class);
			LOG.error("No se debería haber obtenido este objeto: {}", converted);
			Assert.fail("Debería haber fallado la conversion");
		} catch (final BadMappingException e) {
			// Es la excepción que esperabamos
		}
		Assert.assertNull("No debería crear una instancia intermedia", raiz.getNested());
	}

	@Test
	public void itShouldUseANullOnDestinationValueWhenTreatAsNullSelectedOnCopyFrom() {
		final ObjetoAnidado raiz = ObjetoAnidado.create("raiz");
		Assert.assertNull("No debería crear una instancia intermedia", raiz.getNested());

		final ObjetoAnidadoToCopyFromNull converted = bean2bean.createFrom(raiz, ObjetoAnidadoToCopyFromNull.class);
		Assert.assertNull("Debería tener null en la propiedad", converted.getCadena());
		Assert.assertNull("No debería crear una instancia intermedia", raiz.getNested());
	}

	@Test
	public void itShouldCreateANewInstanceInSourceObjectWhenCreateInstanceSelectedInCopyFrom() {
		final ObjetoAnidado raiz = ObjetoAnidado.create("raiz");
		Assert.assertNull("No Debería existir el objeto intermedio", raiz.getNested());

		final ObjetoAnidadoToCopyFromCreateInstance converted = bean2bean.createFrom(raiz,
				ObjetoAnidadoToCopyFromCreateInstance.class);
		Assert.assertNotNull("Debería tener un valor en el objeto creado", converted.getCadena());
		Assert.assertEquals("Debería tener el valor de la cadena default", ObjetoAnidado.DEFAULT_CADENA,
				converted.getCadena());
		Assert.assertNotNull("Deberia crear un objeto en el objeto origen", raiz.getNested());
	}

	// ****
	// Con CopyTo
	// *****

	@Test
	public void itShouldThrowAnErrorByDefaultDuringConversionWhenMissingPropertyInCopyTo() {
		final ObjetoAnidadoToCopyToDefault toObject = ObjetoAnidadoToCopyToDefault.create("toObject");

		try {
			final ObjetoAnidado converted = bean2bean.convertTo(ObjetoAnidado.class, toObject);
			LOG.error("No se debería haber obtenido este objeto: {}", converted);
			Assert.fail("Debería haber fallado la conversion");
		} catch (final BadMappingException e) {
			// Excepcion esperada
		}
	}

	@Test
	public void itShouldUseANullOnDestinationValueWhenTreatAsNullSelectedOnCopyTo() {
		final ObjetoAnidadoToCopyToNull toObject = ObjetoAnidadoToCopyToNull.create("toObject");
		final ObjetoAnidado converted = bean2bean.convertTo(ObjetoAnidado.class, toObject);
		Assert.assertNull("Debería tener null en la propiedad", converted.getNested());
		Assert.assertEquals("Debería tener la cadena default", ObjetoAnidado.DEFAULT_CADENA, converted.getCadena());
	}

	@Test
	public void itShouldCreateANewInstanceInDestinationObjectWhenCreateInstanceSelectedInCopyTo() {
		final ObjetoAnidadoToCopyToCreateInstance toObject = ObjetoAnidadoToCopyToCreateInstance.create("toObject");

		final ObjetoAnidado converted = bean2bean.convertTo(ObjetoAnidado.class, toObject);
		final ObjetoAnidado nested = converted.getNested();
		Assert.assertNotNull("Debería tener un objeto anidado", nested);
		Assert.assertEquals("Debería tener el valor de la cadena TO", toObject.getCadena(), nested.getCadena());
	}

	// ****
	// Con CopyFromAndTo copiando desde
	// *****

	@Test
	public void itShouldThrowAnErrorByDefaultDuringConversionWhenMissingPropertyInCopyFromAndToAndCopyingFrom() {
		final ObjetoAnidado raiz = ObjetoAnidado.create("raiz");
		Assert.assertNull("No debería crear una instancia intermedia", raiz.getNested());

		try {
			final ObjetoAnidadoToCopyFromAndToDefault converted = bean2bean.createFrom(raiz,
					ObjetoAnidadoToCopyFromAndToDefault.class);
			LOG.error("No se debería haber obtenido este objeto: {}", converted);
			Assert.fail("Debería haber fallado la conversion");
		} catch (final BadMappingException e) {
			// Excepcion esperada
		}
		Assert.assertNull("No debería crear una instancia intermedia", raiz.getNested());
	}

	@Test
	public void itShouldUseANullOnDestinationValueWhenTreatAsNullSelectedOnCopyFromAndToAndCopyingFrom() {
		final ObjetoAnidado raiz = ObjetoAnidado.create("raiz");
		Assert.assertNull("No debería crear una instancia intermedia", raiz.getNested());

		final ObjetoAnidadoToCopyFromAndToNull converted = bean2bean.createFrom(raiz,
				ObjetoAnidadoToCopyFromAndToNull.class);
		Assert.assertNull("Debería tener null en la propiedad", converted.getCadena());
		Assert.assertNull("No debería crear una instancia intermedia", raiz.getNested());
	}

	@Test
	public void itShouldCreateANewInstanceInDestinationObjectWhenCreateInstanceSelectedInCopyFromAndToAndCopyingFrom() {
		final ObjetoAnidado raiz = ObjetoAnidado.create("raiz");
		Assert.assertNull("No Debería existir el objeto intermedio", raiz.getNested());

		final ObjetoAnidadoToCopyFromAndToCreateInstance converted = bean2bean.createFrom(raiz,
				ObjetoAnidadoToCopyFromAndToCreateInstance.class);
		Assert.assertNotNull("Debería tener un valor en el objeto creado", converted.getCadena());
		Assert.assertEquals("Debería tener el valor de la cadena default", ObjetoAnidado.DEFAULT_CADENA,
				converted.getCadena());
		Assert.assertNotNull("Debería existir el objeto intermedio", raiz.getNested());
	}

	// ****
	// Con CopyFromAndTo copiando hacia
	// *****

	@Test
	public void itShouldThrowAnErrorByDefaultDuringConversionWhenMissingPropertyInCopyFromAndToAndCopyingTo() {
		final ObjetoAnidadoToCopyFromAndToDefault toObject = ObjetoAnidadoToCopyFromAndToDefault.create("toObject");
		try {
			final ObjetoAnidado converted = bean2bean.convertTo(ObjetoAnidado.class, toObject);
			LOG.error("No se debería haber obtenido este objeto: {}", converted);
			Assert.fail("Debería haber fallado la conversion");
		} catch (final BadMappingException e) {
			// Excepcion esperada
		}

	}

	@Test
	public void itShouldUseANullOnDestinationValueWhenTreatAsNullSelectedOnCopyFromAndToAndCopyingTo() {
		final ObjetoAnidadoToCopyFromAndToNull toObject = ObjetoAnidadoToCopyFromAndToNull.create("toObject");

		final ObjetoAnidado converted = bean2bean.convertTo(ObjetoAnidado.class, toObject);
		Assert.assertNull("Debería tener null en la propiedad", converted.getNested());
	}

	@Test
	public void itShouldCreateANewInstanceInDestinationObjectWhenCreateInstanceSelectedInCopyFromAndToAndCopyingTo() {
		final ObjetoAnidadoToCopyFromAndToCreateInstance toObject = ObjetoAnidadoToCopyFromAndToCreateInstance
				.create("toObject");

		final ObjetoAnidado converted = bean2bean.convertTo(ObjetoAnidado.class, toObject);
		final ObjetoAnidado nested = converted.getNested();
		Assert.assertNotNull("Debería tener un objeto anidado", nested);
		Assert.assertEquals("Debería tener el valor de la cadena del TO", toObject.getCadena(), nested.getCadena());
	}

}
