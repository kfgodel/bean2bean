/**
 * 23/12/2011 14:51:45 Copyright (C) 2011 Darío L. García
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
package ar.com.dgarcia.mavenknowhow.helpers;

import org.apache.maven.model.Build;
import org.apache.maven.model.Plugin;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import ar.com.dgarcia.mavenknowhow.plugins.CompilerPlugin;
import ar.com.dgarcia.mavenknowhow.plugins.EclipsePlugin;
import ar.com.dgarcia.mavenknowhow.plugins.JarPlugin;
import ar.com.dgarcia.mavenknowhow.plugins.SourcePlugin;

/**
 * Esta clase define operación para facilitar la definición de plugins en el pom
 * 
 * @author D. García
 */
public class Plugins {

	/**
	 * Agrega el plugin del compiler preconfigurado
	 */
	public static void addCompilerTo(final Build buildInfo) {
		final Plugin compilerPlugin = new CompilerPlugin().getMavenPlugin();
		buildInfo.addPlugin(compilerPlugin);
	}

	/**
	 * Agrega el plugin de empaquetado en jar preconfigurado
	 */
	public static void addJarTo(final Build buildInfo) {
		final Plugin compilerPlugin = new JarPlugin().getMavenPlugin();
		buildInfo.addPlugin(compilerPlugin);
	}

	/**
	 * Agrega el plugin que adjunta fuentes preconfigurado
	 */
	public static void addSourceTo(final Build buildInfo) {
		final Plugin compilerPlugin = new SourcePlugin().getMavenPlugin();
		buildInfo.addPlugin(compilerPlugin);
	}

	/**
	 * Agrega el plugin configurador de eclipse preconfigurado
	 */
	public static void addEclipseTo(final Build buildInfo) {
		final Plugin compilerPlugin = new EclipsePlugin().getMavenPlugin();
		buildInfo.addPlugin(compilerPlugin);
	}

	/**
	 * Crea el nodo de configuración de un plugin
	 * 
	 * @return El nodo raiz que define la configuración de un plugin
	 */
	public static Xpp3Dom createConfiguration() {
		return new Xpp3Dom("configuration");
	}

	/**
	 * Agrega un child al nodo pasado definiendo un valor como contenido
	 * 
	 * @param root
	 * 
	 * @param valueName
	 *            El nombre del child a agregar
	 * @param valueExpression
	 *            El valor del contenido
	 */
	public static Xpp3Dom addChildValueTo(final Xpp3Dom root, final String valueName, final String valueExpression) {
		final Xpp3Dom childValue = new Xpp3Dom(valueName);
		childValue.setValue(valueExpression);
		root.addChild(childValue);
		return childValue;
	}

	/**
	 * Agrega un child con le nombre dado a la raiz pasada
	 * 
	 * @param root
	 *            El nodo raiz
	 * @param childName
	 *            El nombre del nodo hijo agregado
	 * @return El nodo creado hijo
	 */
	public static Xpp3Dom addChildContainerTo(final Xpp3Dom root, final String childName) {
		final Xpp3Dom newChild = new Xpp3Dom(childName);
		root.addChild(newChild);
		return newChild;
	}

}
