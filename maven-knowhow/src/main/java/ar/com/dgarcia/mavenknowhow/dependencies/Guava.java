/**
 * 23/12/2011 14:38:56 Copyright (C) 2011 Darío L. García
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
 * Agrega varias extensiones de Google para Java
 * 
 * @author D. García
 */
public class Guava extends ProjectDependency {

	/**
	 * @see ar.com.dgarcia.mavenknowhow.generator.ProjectDependency#declareJarDependencies(java.util.List)
	 */
	@Override
	protected void declareJarDependencies(final List<Dependency> dependencies) {
		dependencies.add(Dependencies.create("com.google.guava", "guava", "r09"));
	}

	public static Guava create() {
		final Guava guava = new Guava();
		return guava;
	}
}
