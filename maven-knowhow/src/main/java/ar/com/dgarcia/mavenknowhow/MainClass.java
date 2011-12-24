/**
 * 23/12/2011 13:48:48 Copyright (C) 2011 Darío L. García
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
package ar.com.dgarcia.mavenknowhow;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import ar.com.dgarcia.mavenknowhow.dependencies.Guava;
import ar.com.dgarcia.mavenknowhow.dependencies.MavenModel;
import ar.com.dgarcia.mavenknowhow.dependencies.Slf4jLogging;
import ar.com.dgarcia.mavenknowhow.generator.PomDefinition;
import ar.com.dgarcia.mavenknowhow.generator.PomGenerator;
import ar.com.dgarcia.mavenknowhow.generator.ProjectDependency;

import com.google.common.collect.Lists;

/**
 * Esta clase genera un POM de acuerdo a los parámetros definidos
 * 
 * @author D. García
 */
public class MainClass {

	public static void main(final String[] args) throws FileNotFoundException, IOException, XmlPullParserException {
		// final Model model = new MavenXpp3Reader().read(new FileReader(new File(
		// "G:\\Java\\Workspaces\\Activo\\maven-knowhow\\pom.xml")));
		// System.out.println(model);

		final String destinationDirPath = "G:\\Java\\Workspaces\\Activo\\maven-knowhow\\test";
		final String groupId = "ar.dgarcia";
		final String artifactIdId = "testArtifact";
		final String versionId = "v1";
		final String projectName = "nombre";
		final String projectDescription = "description";
		final List<ProjectDependency> dependencies = Lists.<ProjectDependency> newArrayList(MavenModel.create(),
				Guava.create(), Slf4jLogging.create());

		final PomDefinition definition = PomDefinition.create(groupId, artifactIdId, versionId);
		definition.setProjectName(projectName);
		definition.setProjectDescription(projectDescription);
		definition.addDependencies(dependencies);

		final PomGenerator generator = PomGenerator.create();
		generator.generateOn(destinationDirPath, definition);
	}
}
