/**
 * Created on 03/01/2008 17:31:57 Copyright (C) 2008 Dario L. Garcia
 * 
 * <a rel="license" href="http://creativecommons.org/licenses/by/2.5/ar/"><img
 * alt="Creative Commons License" style="border-width:0"
 * src="http://i.creativecommons.org/l/by/2.5/ar/88x31.png" /></a><br />
 * <span xmlns:dc="http://purl.org/dc/elements/1.1/"
 * href="http://purl.org/dc/dcmitype/InteractiveResource" property="dc:title"
 * rel="dc:type">Bean2Bean</span> by <a xmlns:cc="http://creativecommons.org/ns#"
 * href="https://sourceforge.net/projects/bean2bean/" property="cc:attributionName"
 * rel="cc:attributionURL">Dar&#237;o Garc&#237;a</a> is licensed under a <a rel="license"
 * href="http://creativecommons.org/licenses/by/2.5/ar/">Creative Commons Attribution 2.5 Argentina
 * License</a>.<br />
 * Based on a work at <a xmlns:dc="http://purl.org/dc/elements/1.1/"
 * href="https://bean2bean.svn.sourceforge.net/svnroot/bean2bean"
 * rel="dc:source">bean2bean.svn.sourceforge.net</a>
 */
package net.sf.kfgodel.bean2bean;

import net.sf.kfgodel.bean2bean.BeanCreationTest;
import net.sf.kfgodel.bean2bean.ConverterTest;
import net.sf.kfgodel.bean2bean.PopulationTest;
import net.sf.kfgodel.bean2bean.PropertyCopyTest;
import net.sf.kfgodel.bean2bean.ReflectionPopulationTest;
import net.sf.kfgodel.bean2bean.SpringInjectionTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Esta clase es una suite de los tests de Bean2Bean
 * 
 * @version 1.0
 * @since 03/01/2008
 * @author D. Garcia
 */
@RunWith(Suite.class)
@SuiteClasses( { PopulationTest.class, BeanCreationTest.class, ConverterTest.class, PropertyCopyTest.class,
		SpringInjectionTest.class, ReflectionPopulationTest.class })
public class Bean2BeanTests {

}