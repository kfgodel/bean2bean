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
public class Reflection_Nested2Nested_Mixed implements PerformanceTestBean {

	private String privateSource;

	@CopyFrom(value = "nested1.nested2.nested3.nested4.nested5.source", getterInterpreter = InterpreterType.REFLECTION, // 
	setter = "nested1.nested2.nested3.nested4.nested5.destination", setterInterpreter = InterpreterType.REFLECTION)
	private String privateDestination;

	public Reflection_Nested2Nested_Mixed nested1;
	private Reflection_Nested2Nested_Mixed privateProperty2;
	public Reflection_Nested2Nested_Mixed nested3;
	private Reflection_Nested2Nested_Mixed privateProperty4;
	public Reflection_Nested2Nested_Mixed nested5;

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

	public Reflection_Nested2Nested_Mixed getNested2() {
		return privateProperty2;
	}

	public void setNested2(Reflection_Nested2Nested_Mixed nested2) {
		this.privateProperty2 = nested2;
	}

	public Reflection_Nested2Nested_Mixed getNested4() {
		return privateProperty4;
	}

	public void setNested4(Reflection_Nested2Nested_Mixed nested4) {
		this.privateProperty4 = nested4;
	}

	public Reflection_Nested2Nested_Mixed() {
		this.nested1 = new Reflection_Nested2Nested_Mixed(false);
		this.nested1.privateProperty2 = new Reflection_Nested2Nested_Mixed(false);
		this.nested1.privateProperty2.nested3 = new Reflection_Nested2Nested_Mixed(false);
		this.nested1.privateProperty2.nested3.privateProperty4 = new Reflection_Nested2Nested_Mixed(false);
		this.nested1.privateProperty2.nested3.privateProperty4.nested5 = new Reflection_Nested2Nested_Mixed(false);
	}

	public Reflection_Nested2Nested_Mixed(boolean b) {
		// Corta la recursividad
	}

	public void prepareValue(String string) {
		this.nested1 = new Reflection_Nested2Nested_Mixed();
		this.nested1.privateProperty2 = new Reflection_Nested2Nested_Mixed();
		this.nested1.privateProperty2.nested3 = new Reflection_Nested2Nested_Mixed();
		this.nested1.privateProperty2.nested3.privateProperty4 = new Reflection_Nested2Nested_Mixed();
		this.nested1.privateProperty2.nested3.privateProperty4.nested5 = new Reflection_Nested2Nested_Mixed();
		this.nested1.privateProperty2.nested3.privateProperty4.nested5.privateSource = string;
	}

	public String getSourceValue() {
		return this.nested1.privateProperty2.nested3.privateProperty4.nested5.privateSource;
	}

	public String getDestinationValue() {
		return this.nested1.privateProperty2.nested3.privateProperty4.nested5.privateDestination;
	}
}
