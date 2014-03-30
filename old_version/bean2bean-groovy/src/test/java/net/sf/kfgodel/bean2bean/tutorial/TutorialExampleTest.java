/**
 * Created on: 13/02/2010 17:13:28 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.tutorial;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

import net.sf.kfgodel.bean2bean.Bean2Bean;
import net.sf.kfgodel.bean2bean.tutorial.Food;
import net.sf.kfgodel.bean2bean.tutorial.FoodDto;
import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;
import net.sf.kfgodel.dgarcia.lang.reflection.iterators.SuperTypeIterator;

import org.junit.Test;


/**
 * This class is a simple test to demonstrate the capabilities of Bean2Bean
 * 
 * @author D. Garcia
 */
public class TutorialExampleTest {

	@Test
	public void testCopyFrom() {
		Food spicyCrispy = new Food();
		spicyCrispy.setServingSizeGrams(178d);
		spicyCrispy.setCalories(478);
		spicyCrispy.setTotalFat(25);
		spicyCrispy.setSaturatedFatGrams(5);
		spicyCrispy.setTransFatGrams(0);
		spicyCrispy.setCholesterolMiligrams(110);
		spicyCrispy.setSodiumMiligrams(1250d);
		spicyCrispy.setCarbohydrateGrams(12d);
		spicyCrispy.setDietaryFiber(1d);
		spicyCrispy.setSugarGrams(0d);
		spicyCrispy.setProteinGrams(38d);

		FoodDto nutritionFact = Bean2Bean.getInstance().createFrom(spicyCrispy, FoodDto.class);

		Food converted = Bean2Bean.getInstance().convertTo(Food.class, nutritionFact);

		System.out.println(converted);
	}

	@Test
	public void testConverterAccess() {

		Type setString = ReflectionUtils.getParametricType(Map.class, String.class, Object.class);
		Iterator<Type> iterator = SuperTypeIterator.createFor(setString);
		while (iterator.hasNext()) {
			Type type = (Type) iterator.next();
			System.out.println(type);
		}
	}
}
