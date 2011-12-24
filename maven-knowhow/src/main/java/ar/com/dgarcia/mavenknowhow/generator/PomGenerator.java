/**
 * 23/12/2011 13:56:42 Copyright (C) 2011 Darío L. García
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
package ar.com.dgarcia.mavenknowhow.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;

/**
 * Esta clase permite generar un POM básico a partir de los parámetros definidos.<br>
 * Para crear el pom es necesario pasar una definición completa
 * 
 * @author D. García
 */
public class PomGenerator {

	public static PomGenerator create() {
		final PomGenerator generator = new PomGenerator();
		return generator;
	}

	/**
	 * Genera el archivo XML a partir de la definición pasada
	 * 
	 * @param destinationDirPath
	 *            El path del archivo a generar
	 * @param definition
	 *            La definición para crear el modelo del pom
	 */
	public void generateOn(final String destinationDirPath, final PomDefinition definition) {
		final File destinationDir = new File(destinationDirPath);
		// Intentamos que existan los directorios padre
		destinationDir.mkdirs();

		final File destinationFile = new File(destinationDirPath, "pom.xml");
		FileWriter pomWriter;
		try {
			pomWriter = new FileWriter(destinationFile);
		} catch (final IOException e) {
			throw new MavenKnowHowException("No fue posible abrir el pom destino", e);
		}
		final Model mavenModel = definition.getMavenModel();
		try {
			new MavenXpp3Writer().write(pomWriter, mavenModel);
		} catch (final IOException e) {
			throw new MavenKnowHowException("No fue posible volcar el modelo en el pom destino", e);
		}
	}
}
