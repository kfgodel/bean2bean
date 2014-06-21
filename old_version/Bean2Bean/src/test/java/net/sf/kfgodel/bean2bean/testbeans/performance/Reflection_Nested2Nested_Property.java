/**
 * Created on: 18/02/2010 20:58:54 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.testbeans.performance;

import net.sf.kfgodel.bean2bean.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.interpreters.InterpreterType;

/**
 * 
 * @author D. Garcia
 */
public class Reflection_Nested2Nested_Property implements PerformanceTestBean {

	private String privateSource;

	@CopyFrom(value = "nested1.nested2.nested3.nested4.nested5.source", getterInterpreter = InterpreterType.REFLECTION, // 
	setter = "nested1.nested2.nested3.nested4.nested5.destination", setterInterpreter = InterpreterType.REFLECTION)
	private String privateDestination;

	private Reflection_Nested2Nested_Property privateNested1;
	private Reflection_Nested2Nested_Property privateNested2;
	private Reflection_Nested2Nested_Property privateNested3;
	private Reflection_Nested2Nested_Property privateNested4;
	private Reflection_Nested2Nested_Property privateNested5;

	public String getSource() {
		return privateSource;
	}

	public void setSource(String source) {
		this.privateSource = source;
	}

	public String getDestination() {
		return privateDestination;
	}

	public void setDestination(String destination) {
		this.privateDestination = destination;
	}

	public Reflection_Nested2Nested_Property getNested1() {
		return privateNested1;
	}

	public void setNested1(Reflection_Nested2Nested_Property nested1) {
		this.privateNested1 = nested1;
	}

	public Reflection_Nested2Nested_Property getNested2() {
		return privateNested2;
	}

	public void setNested2(Reflection_Nested2Nested_Property nested2) {
		this.privateNested2 = nested2;
	}

	public Reflection_Nested2Nested_Property getNested3() {
		return privateNested3;
	}

	public void setNested3(Reflection_Nested2Nested_Property nested3) {
		this.privateNested3 = nested3;
	}

	public Reflection_Nested2Nested_Property getNested4() {
		return privateNested4;
	}

	public void setNested4(Reflection_Nested2Nested_Property nested4) {
		this.privateNested4 = nested4;
	}

	public Reflection_Nested2Nested_Property getNested5() {
		return privateNested5;
	}

	public void setNested5(Reflection_Nested2Nested_Property nested5) {
		this.privateNested5 = nested5;
	}

	public Reflection_Nested2Nested_Property() {
		this.privateNested1 = new Reflection_Nested2Nested_Property(false);
		this.privateNested1.privateNested2 = new Reflection_Nested2Nested_Property(false);
		this.privateNested1.privateNested2.privateNested3 = new Reflection_Nested2Nested_Property(false);
		this.privateNested1.privateNested2.privateNested3.privateNested4 = new Reflection_Nested2Nested_Property(false);
		this.privateNested1.privateNested2.privateNested3.privateNested4.privateNested5 = new Reflection_Nested2Nested_Property(
				false);
	}

	public Reflection_Nested2Nested_Property(boolean b) {
		// Corta la recursividad
	}

	public void prepareValue(String string) {
		this.privateNested1 = new Reflection_Nested2Nested_Property();
		this.privateNested1.privateNested2 = new Reflection_Nested2Nested_Property();
		this.privateNested1.privateNested2.privateNested3 = new Reflection_Nested2Nested_Property();
		this.privateNested1.privateNested2.privateNested3.privateNested4 = new Reflection_Nested2Nested_Property();
		this.privateNested1.privateNested2.privateNested3.privateNested4.privateNested5 = new Reflection_Nested2Nested_Property();
		this.privateNested1.privateNested2.privateNested3.privateNested4.privateNested5.privateSource = string;
	}

	public String getSourceValue() {
		return privateNested1.privateNested2.privateNested3.privateNested4.privateNested5.privateSource;
	}

	public String getDestinationValue() {
		return privateNested1.privateNested2.privateNested3.privateNested4.privateNested5.privateDestination;
	}
}
