/**
 * 23/12/2011 14:55:04 Copyright (C) 2011 Darío L. García
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
package ar.com.dgarcia.mavenknowhow.plugins;

import org.apache.maven.model.Plugin;

/**
 * Esta clase representa un plugin utilizado en la sección de build del proyecto
 * 
 * @author D. García
 */
public abstract class BuildPlugin {

	private final Plugin mavenPlugin;

	public BuildPlugin() {
		mavenPlugin = new Plugin();
		configurePlugin(mavenPlugin);
	}

	/**
	 * Define los datos de identificación group/artifact/version
	 * 
	 * @param plugin
	 */
	protected abstract void configurePlugin(Plugin plugin);

	public Plugin getMavenPlugin() {
		return mavenPlugin;
	}
}
