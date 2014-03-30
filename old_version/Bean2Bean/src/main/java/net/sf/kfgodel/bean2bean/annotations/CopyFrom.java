/**
 * Created on: 14/01/2009 16:19:38 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;

import net.sf.kfgodel.bean2bean.conversion.TypeConverter;
import net.sf.kfgodel.bean2bean.interpreters.InterpreterType;
import net.sf.kfgodel.bean2bean.interpreters.groovy.GroovyConstants;
import net.sf.kfgodel.bean2bean.interpreters.ognl.OgnlConstants;


/**
 * This annotation marks bean fields that can be populated from another bean using a getter
 * expression.<br>
 * The attributes in this annotation define the mapping between the source property and the
 * destination property.<br>
 * <br>
 * This annotation configures basically three aspects of the copying process: the getter expression,
 * the setter expression and the expectedType for any conversion needed in the assignment.<br>
 * <br>
 * All the configuration information on this annotation will override any configuration on
 * {@link CopyFromAndTo} if present.
 * 
 * @author D.Garcia
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CopyFrom {

	/**
	 * Expression for getting the value to copy. This expression is evaluated in source object (not
	 * the annotated one). This can be any valid value in the language defined by
	 * {@link #getterInterpreter()}.<br>
	 * <br>
	 * You will normally use a sequence of properties separated by ".", indicating the getters to
	 * call until the final object is obtained.<br>
	 * An empty string (default value) indicates that the name of annotated field should be used.<br>
	 * <br>
	 * If a null value is found while looking on the property path, the action taken is defined by
	 * {@link #whenMissing()}.
	 * 
	 * 
	 * @return Property path or custom expression applied to source bean to get desired value
	 */
	String value() default "";

	/**
	 * Interpreter type to use when executing {@link #value()} expression.<br>
	 * The interpreter defines the language you can use as getter expression.<br>
	 * Default value is {@link InterpreterType#REFLECTION}
	 * 
	 * @return Language used in the {@link #value()} getter expression
	 */
	InterpreterType getterInterpreter() default InterpreterType.REFLECTION;

	/**
	 * Expression for setting the final value on the destination bean.<br>
	 * You can use any valid value in the language defined by {@link #setterInterpreter()}.<br>
	 * <br>
	 * You will normally use a property sequence separated by "."<br>
	 * If empty is used annotated field name will be used to assign the value on annotated property. <br>
	 * <br>
	 * If null value is found while lokking for final property to set, action taken will be defined
	 * by {@link #whenMissing()}<br>
	 * <br>
	 * If you are using groovy, you shoud reference {@link GroovyConstants#OBJETO_DESTINO} as the
	 * destination bean, otherwise assignment will not affect the bean. Groovy expression is
	 * executed within a Closure that uses a local scope for variables so any local change will be
	 * lost.
	 * 
	 * @return A setter expression or a property chain
	 */
	String setter() default "";

	/**
	 * Interpreter type to use when executing {@link #setter()} expression.<br>
	 * The interpreter defines the language you can use as setter expression.<br>
	 * Default value is {@link InterpreterType#REFLECTION}
	 * 
	 * @return Language used in the {@link #setter()} setter expression
	 */
	InterpreterType setterInterpreter() default InterpreterType.REFLECTION;

	/**
	 * Action that will be taken if a null value is found while accesing desired value, or while
	 * setting desired value in a property chain.<br> {@link MissingPropertyAction#TREAT_AS_NULL} should
	 * be used if you prefer to get or set null as the value insted of throwing an Exception<br>
	 * Use {@link MissingPropertyAction#CREATE_MISSING_INSTANCES} if you want bean2bean to use
	 * reflection to create new instances as needed
	 * 
	 * @return {@link MissingPropertyAction#THROW_ERROR} by default
	 */
	MissingPropertyAction whenMissing() default MissingPropertyAction.THROW_ERROR;

	/**
	 * Expression that indicates what the expected type is. This type is used for a type conversion
	 * before assigning desired value.<br>
	 * If using {@link InterpreterType#REFLECTION} just write the complete class name.<br>
	 * <br>
	 * If empty is used, expected type will be determined using setter expression and try to guess
	 * destination property type.<br>
	 * <br>
	 * This expression should return a {@link Type} object and is evaluated from null as root
	 * object. You can use context variables through {@link OgnlConstants} or
	 * {@link GroovyConstants}.<br>
	 * Any valid value in laguage defined by {@link #typeInterpreter()} is accepted.
	 * 
	 * @return An expression to get a {@link Type} assignable to destination property
	 */
	String expectedType() default "";

	/**
	 * Interpreter language to execute {@link #expectedType()} expression.<br>
	 * Default is {@link InterpreterType#REFLECTION}
	 * 
	 * @return Language of {@link #expectedType()} expression
	 */
	InterpreterType typeInterpreter() default InterpreterType.REFLECTION;

	/**
	 * Identifier name used when registering a custom converter in the {@link TypeConverter}. This
	 * name is used to force a conversion using a custom converter. <br>
	 * You can pass additional context to the converter using related annotations defined by
	 * {@link #contextAnnotations()}.<br>
	 * <br>
	 * An empty string indicates that used converter should be determined dinamically using source
	 * an destination types
	 * 
	 * @return A converter name. Normally the complete class name (with package)
	 */
	String useConversor() default "";

	/**
	 * Annotation types also present on annotated field that are used by the converter as conversion
	 * context.<br> {@link #contextAnnotations()} is used when you need extra parameters in the
	 * conversion and you know the used converter.<br>
	 * <br>
	 * Annotations present on this attribute and on annotated field will be passed over to the
	 * converter as an annotation array during conversion.<br>
	 * If you specify AnnotationTypes not present on current field an null value will be on passed
	 * array on the corresponding index.<br>
	 * The annotations array order is the same as this annotation type array. Absent annotations
	 * will be null.<br>
	 * Default value is empty array
	 * 
	 * @return Annotation classes related to the conversion
	 */
	Class<? extends Annotation>[] contextAnnotations() default {};

}
