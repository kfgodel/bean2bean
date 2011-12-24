/**
 * 23/12/2011 14:16:09 Copyright (C) 2011 Darío L. García
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

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;

/**
 * Esta clase representa una dependencia del POM simplificada
 * 
 * @author D. García
 */
public abstract class ProjectDependency {

	private List<Dependency> jarDependencies;

	public List<Dependency> getJarDependencies() {
		if (jarDependencies == null) {
			jarDependencies = new ArrayList<Dependency>();
			declareJarDependencies(jarDependencies);
		}
		return jarDependencies;
	}

	/**
	 * Define las dependencias que esta dependencia de proyecto tiene
	 * 
	 * @param dependencies
	 *            La lista base para agregar las propias
	 */
	protected abstract void declareJarDependencies(final List<Dependency> dependencies);

	/**
	 * Agrega las dependencias necesarias del pom para esta dependencia del proyecto
	 * 
	 * @param mavenModel
	 *            Modelo en el cual agregar las dependencias
	 */
	public void addDependenciesTo(final Model mavenModel) {
		for (final Dependency dependency : getJarDependencies()) {
			mavenModel.addDependency(dependency);
		}
	}

	/**
	 * Verifica que las dependencias existentes en el modelo pasado no sea incompatibles con las de
	 * esta dependencias
	 * 
	 * @param mavenModel
	 *            Modelo a validar
	 */
	public void validateCompatibilityWith(final Model mavenModel) {
		// No hacemos nada en esta clase

	}

}
