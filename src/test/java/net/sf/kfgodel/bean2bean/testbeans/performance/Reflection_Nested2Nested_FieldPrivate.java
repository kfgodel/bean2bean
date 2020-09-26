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
public class Reflection_Nested2Nested_FieldPrivate implements PerformanceTestBean {

	private String source;

	@CopyFrom(value = "nested1.nested2.nested3.nested4.nested5.source", getterInterpreter = InterpreterType.REFLECTION, // 
	setter = "nested1.nested2.nested3.nested4.nested5.destination", setterInterpreter = InterpreterType.REFLECTION)
	private String destination;

	private Reflection_Nested2Nested_FieldPrivate nested1;
	private Reflection_Nested2Nested_FieldPrivate nested2;
	private Reflection_Nested2Nested_FieldPrivate nested3;
	private Reflection_Nested2Nested_FieldPrivate nested4;
	private Reflection_Nested2Nested_FieldPrivate nested5;

	public Reflection_Nested2Nested_FieldPrivate() {
		this.nested1 = new Reflection_Nested2Nested_FieldPrivate(false);
		this.nested1.nested2 = new Reflection_Nested2Nested_FieldPrivate(false);
		this.nested1.nested2.nested3 = new Reflection_Nested2Nested_FieldPrivate(false);
		this.nested1.nested2.nested3.nested4 = new Reflection_Nested2Nested_FieldPrivate(false);
		this.nested1.nested2.nested3.nested4.nested5 = new Reflection_Nested2Nested_FieldPrivate(false);
	}

	public Reflection_Nested2Nested_FieldPrivate(boolean b) {
		// Corta la recursividad
	}

	public void prepareValue(String string) {
		this.nested1 = new Reflection_Nested2Nested_FieldPrivate();
		this.nested1.nested2 = new Reflection_Nested2Nested_FieldPrivate();
		this.nested1.nested2.nested3 = new Reflection_Nested2Nested_FieldPrivate();
		this.nested1.nested2.nested3.nested4 = new Reflection_Nested2Nested_FieldPrivate();
		this.nested1.nested2.nested3.nested4.nested5 = new Reflection_Nested2Nested_FieldPrivate();
		this.nested1.nested2.nested3.nested4.nested5.source = string;
	}

	public String getSourceValue() {
		return this.nested1.nested2.nested3.nested4.nested5.source;
	}

	public String getDestinationValue() {
		return this.nested1.nested2.nested3.nested4.nested5.destination;
	}
}