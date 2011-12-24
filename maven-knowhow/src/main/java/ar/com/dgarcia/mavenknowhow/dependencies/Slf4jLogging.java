/**
 * 23/12/2011 14:41:07 Copyright (C) 2011 Darío L. García
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
package ar.com.dgarcia.mavenknowhow.dependencies;

import java.util.List;

import org.apache.maven.model.Dependency;

import ar.com.dgarcia.mavenknowhow.generator.ProjectDependency;
import ar.com.dgarcia.mavenknowhow.helpers.Dependencies;

/**
 * Agrega logueo usando slf4j y logback como engine
 * 
 * @author D. García
 */
public class Slf4jLogging extends ProjectDependency {

	/**
	 * @see ar.com.dgarcia.mavenknowhow.generator.ProjectDependency#declareJarDependencies(java.util.List)
	 */
	@Override
	protected void declareJarDependencies(final List<Dependency> dependencies) {
		// <!-- Facade para todo lo que es logueo -->
		dependencies.add(Dependencies.create("org.slf4j", "slf4j-api", "1.6.1"));
		// <!-- Implementación nativa de slf4j -->
		dependencies.add(Dependencies.create("ch.qos.logback", "logback-core", "0.9.28"));
		dependencies.add(Dependencies.create("ch.qos.logback", "logback-classic", "0.9.28"));
		// <!-- Adapter que las llamadas a commons-logging pasen por Slf4j-->
		dependencies.add(Dependencies.create("org.slf4j", "jcl-over-slf4j", "1.6.1"));
		// <!-- Adapter que las llamadas a log4j pasen por Slf4j-->
		dependencies.add(Dependencies.create("org.slf4j", "log4j-over-slf4j", "1.6.1"));
	}

	public static Slf4jLogging create() {
		final Slf4jLogging logging = new Slf4jLogging();
		return logging;
	}
}
