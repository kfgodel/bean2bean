/**
 * 24/12/2011 19:20:12 Copyright (C) 2011 Darío L. García
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
import org.codehaus.plexus.util.xml.Xpp3Dom;

import ar.com.dgarcia.mavenknowhow.helpers.Plugins;

/**
 * 
 * @author D. García
 */
public class EclipsePlugin extends BuildPlugin {

	/**
	 * @see ar.com.dgarcia.mavenknowhow.plugins.BuildPlugin#configurePlugin(org.apache.maven.model.Plugin)
	 */
	@Override
	protected void configurePlugin(final Plugin plugin) {
		plugin.setArtifactId("maven-eclipse-plugin");
		plugin.setGroupId("org.apache.maven.plugins");
		plugin.setVersion("2.8");

		final Xpp3Dom configuration = Plugins.createConfiguration();
		Plugins.addChildValueTo(configuration, "downloadSources", "true");
		Plugins.addChildValueTo(configuration, "wtpversion", "2.0");

		plugin.setConfiguration(configuration);
	}

}
