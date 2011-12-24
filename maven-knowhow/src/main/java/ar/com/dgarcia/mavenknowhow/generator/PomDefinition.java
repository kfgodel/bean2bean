/**
 * 23/12/2011 13:57:23 Copyright (C) 2011 Darío L. García
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

import java.util.List;

import org.apache.maven.model.Build;
import org.apache.maven.model.Model;

import ar.com.dgarcia.mavenknowhow.helpers.Plugins;

/**
 * Esta clase representa la definición del POM a partir del cual se puede generar el POM real.<br>
 * A partir de instancias de esta clase se pueden definir los parámetros básicos necesarios para
 * definir el pom
 * 
 * @author D. García
 */
public class PomDefinition {

	private Model mavenModel;

	public static PomDefinition create(final String groupId, final String artifactIdId, final String versionId) {
		final PomDefinition definition = new PomDefinition();
		definition.mavenModel = new Model();
		definition.mavenModel.setArtifactId(artifactIdId);
		definition.mavenModel.setGroupId(groupId);
		definition.mavenModel.setVersion(versionId);
		definition.defineBasicConfiguration();
		return definition;
	}

	/**
	 * Define los parámetros del POM que son comunes a todos los proyectos que genero
	 */
	private void defineBasicConfiguration() {
		defineBasicProperties();
		defineBasicBuildPlugins();
	}

	/**
	 * Define la configuración básica para los plugins comunes
	 */
	private void defineBasicBuildPlugins() {
		final Build buildInfo = new Build();
		mavenModel.setBuild(buildInfo);
		Plugins.addCompilerTo(buildInfo);
		Plugins.addJarTo(buildInfo);
		Plugins.addSourceTo(buildInfo);
		Plugins.addEclipseTo(buildInfo);
	}

	/**
	 * Define las propiedades básicas para cualquier proyecto
	 */
	private void defineBasicProperties() {
		mavenModel.addProperty("project.build.sourceEncoding", "UTF-8");
		mavenModel.addProperty("project.jdk", "1.6");
	}

	/**
	 * Devuelve el modelo de maven generado a partir de esta definición
	 * 
	 * @return El modelo que se genera con esta definición
	 */
	public Model getMavenModel() {
		return mavenModel;
	}

	public void setProjectName(final String projectName) {
		mavenModel.setName(projectName);
	}

	public void setProjectDescription(final String projectDescription) {
		mavenModel.setDescription(projectDescription);
	}

	/**
	 * Agrega las dependencias pasadas a las existentes en esta definición
	 * 
	 * @param dependencies
	 *            Dependencias a agregar
	 */
	public void addDependencies(final List<ProjectDependency> dependencies) {
		// Primero agregamos todas las dependencias
		for (final ProjectDependency pomDependency : dependencies) {
			pomDependency.addDependenciesTo(mavenModel);
		}
		// Validamos por las dudas que hayan incompatibilidades
		for (final ProjectDependency pomDependency : dependencies) {
			pomDependency.validateCompatibilityWith(mavenModel);
		}
	}
}
