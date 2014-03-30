/**
 * Created on: 03/03/2010 18:41:16 by: Dario L. Garcia
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
 * This annotation marks bean fields that can be populated from and to another bean using a getter
 * expression.<br>
 * The attributes in this annotation define the mapping between the source property and the
 * destination property, adn vice versa.<br>
 * <br>
 * This annotation is expected to be used on properties that will be copied in both direction
 * {@link CopyFrom} and {@link CopyTo} and need little or none configuration. Using this annotation
 * you don't need to use {@link CopyFrom} and {@link CopyTo}. however if you require too much
 * configuration for the property mappings it is recommended that you use separated annotations.<br>
 * <br>
 * All the configuration present on this annotation will be overriden by {@link CopyFrom} or
 * {@link CopyTo} if present.<br>
 * <br>
 * This annotation configures basically three aspects of the copying process: the getter expression,
 * the setter expression and the expectedType for any conversion needed in the assignment. You will
 * need to do it twice, once for copying the valur from local property to remote, and once for
 * copying remote to local property.<br>
 * The local property is the one that is annotated.<br>
 * 
 * @author D. Garcia
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CopyFromAndTo {

	/**
	 * Expression used as default getter and setter for remote property. This expression is used as
	 * a default value to copy the property value from and to remote property, unless you define a
	 * more specific expression (see the others attributes of this annotations).<br>
	 * The expression defined in this property is interpreted using the interpreter indicated on
	 * {@link #valueInterpreter()}<br>
	 * The expression specified will be used for all the mappings unless you override it in any of
	 * the specific expression: {@link #remoteSetter()},{@link #remoteGetter()} <br>
	 * <br>
	 * You will normally use a sequence of properties separated by ".", indicating the getters to
	 * call until the final object is obtained.<br>
	 * An empty string (default value) indicates that the name of annotated field should be used.<br>
	 * <br>
	 * If a null value is found while looking on the property path, the action taken is defined by
	 * {@link #whenMissing()}.
	 * 
	 * @return Property path or custom expression used to all the mappings
	 */
	String value() default "";

	/**
	 * Interpreter type to use when executing {@link #value()} expression.<br>
	 * The interpreter defines the language you can use as getter and setter expressions (be careful
	 * that the same expression is used for both operations unless you define a specific expression
	 * for each).<br>
	 * Default value is {@link InterpreterType#REFLECTION}
	 * 
	 * @return Language used in the {@link #value()} expression
	 */
	InterpreterType valueInterpreter() default InterpreterType.REFLECTION;

	/**
	 * Action that will be taken if a null value is found while accesing desired value, or while
	 * setting desired value in a property chain.<br> {@link MissingPropertyAction#TREAT_AS_NULL} should
	 * be used if you prefer to get or set null as the value insted of throwing an Exception.<br>
	 * Use {@link MissingPropertyAction#CREATE_MISSING_INSTANCES} if you want bean2bean to use
	 * reflection to create new instances as needed
	 * 
	 * @return {@link MissingPropertyAction#THROW_ERROR} by default
	 */
	MissingPropertyAction whenMissing() default MissingPropertyAction.THROW_ERROR;

	/**
	 * Annotation types also present on annotated field that are used by the converter as conversion
	 * context.<br> {@link #contextAnnotations()} is used when you need extra parameters in the
	 * conversion and you know beforehand the used converter. Converter will use extra annotations
	 * as extra configuration<br>
	 * <br>
	 * Annotations present on this attribute and on annotated field will be passed over to the
	 * converter as an annotation array during conversion.<br>
	 * If you specify AnnotationTypes not present on current field an null value will be on passed
	 * array on the corresponding index.<br>
	 * The annotations array order is the same as this annotation type array. Absent annotations
	 * will be null.<br>
	 * Default value is empty array,<br>
	 * <br>
	 * Note that this annotations will be passed in the local to remote conversion, and the remote
	 * to local canversion also.
	 * 
	 * @return Annotation classes related to the conversion
	 */
	Class<? extends Annotation>[] contextAnnotations() default {};

	/**
	 * Expression for getting the value from remote property to copy on local property. This
	 * expression is evaluated in source object (not the annotated one). This can be any valid value
	 * in the language defined by {@link #remoteGetterInterpreter()}.<br> {@link #value()} expression
	 * will be used if you don't specify a specific expression here.<br>
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
	String remoteGetter() default "";

	/**
	 * Interpreter type to use when executing {@link #remoteGetter()} expression.<br>
	 * The interpreter defines the language you can use as getter expression on remote property.<br>
	 * Default value is {@link InterpreterType#REFLECTION}
	 * 
	 * @return Language used in the {@link #value()} getter expression
	 */
	InterpreterType remoteGetterInterpreter() default InterpreterType.REFLECTION;

	/**
	 * Expression for setting the final value on the local bean (the annotated one).<br>
	 * You can use any valid value in the language defined by {@link #localSetterInterpreter()}.<br>
	 * <br>{@link #value()} expression will be used if you don't specify a specific expression here.<br>
	 * You will normally use a property sequence separated by "."<br>
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
	String localSetter() default "";

	/**
	 * Interpreter type to use when executing {@link #localSetter()} expression.<br>
	 * The interpreter defines the language you can use as setter expression on local property.<br>
	 * Default value is {@link InterpreterType#REFLECTION}
	 * 
	 * @return Language used in the {@link #setter()} setter expression
	 */
	InterpreterType localSetterInterpreter() default InterpreterType.REFLECTION;

	/**
	 * Expression that indicates what the expected type on local property is. This type is used for
	 * a type conversion before assigning desired value.<br>
	 * If using {@link InterpreterType#REFLECTION} just write the complete class name.<br>
	 * <br>
	 * If empty is used, expected type will be determined using {@link #localSetter()} expression
	 * and try to guess local property type.<br>
	 * <br>
	 * This expression should return a {@link Type} object and is evaluated from null as root
	 * object. You can use context variables through {@link OgnlConstants} or
	 * {@link GroovyConstants}.<br>
	 * Any valid value in laguage defined by {@link #typeInterpreter()} is accepted.
	 * 
	 * @return An expression to get a {@link Type} assignable to destination property
	 */
	String localExpectedType() default "";

	/**
	 * Interpreter language to execute {@link #localExpectedType()} expression.<br>
	 * Default is {@link InterpreterType#REFLECTION}
	 * 
	 * @return Language of {@link #expectedType()} expression
	 */
	InterpreterType localTypeInterpreter() default InterpreterType.REFLECTION;

	/**
	 * Identifier name used when registering the converter in the {@link TypeConverter}. This name
	 * is used to force a conversion using a custom converter. <br>
	 * You can pass additional context to the converter using related annotations defined by
	 * {@link #contextAnnotations()}.<br>
	 * <br>
	 * An empty string indicates that used converter should be determined dinamically using source
	 * an destination types
	 * 
	 * @return A converter name. Normally the complete class name (with package)
	 */
	String remote2LocalConversor() default "";

	/**
	 * Expression for setting the final value on the remote bean.<br>
	 * You can use any valid value in the language defined by {@link #remoteSetterInterpreter()}.<br>
	 * You will normally use a property sequence separated by "."<br> {@link #value()} expression will
	 * be used if you don't specify a specific expression here.<br>
	 * <br>
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
	String remoteSetter() default "";

	/**
	 * Interpreter type to use when executing {@link #remoteSetter()} expression.<br>
	 * The interpreter defines the language you can use as setter expression.<br>
	 * Default value is {@link InterpreterType#REFLECTION}
	 * 
	 * @return Language used in the {@link #setter()} setter expression
	 */
	InterpreterType remoteSetterInterpreter() default InterpreterType.REFLECTION;

	/**
	 * Expression for getting the value from local property to copy to remote property. This
	 * expression is evaluated in local object (the annotated one). This can be any valid value in
	 * the language defined by {@link #localGetterInterpreter()}.<br>
	 * <br>
	 * You will normally use a sequence of properties separated by ".", indicating the getters to
	 * call until the final object is obtained.<br>
	 * An empty string (default value) indicates that the name of annotated field should be used.<br>
	 * <br>
	 * If a null value is found while looking on the property path, the action taken is defined by
	 * {@link #whenMissing()}
	 * 
	 * @return Property path or custom expression applied to source bean to get desired value
	 */
	String localGetter() default "";

	/**
	 * Interpreter type to use when executing {@link #localGetter()} expression.<br>
	 * The interpreter defines the language you can use as getter expression.<br>
	 * Default value is {@link InterpreterType#REFLECTION}
	 * 
	 * @return Language used in the {@link #getter()} getter expression
	 */
	InterpreterType localGetterInterpreter() default InterpreterType.REFLECTION;

	/**
	 * Expression that indicates what the expected type is on the remote property. This type is used
	 * for a type conversion before assigning desired value.<br>
	 * If using {@link InterpreterType#REFLECTION} just write the complete class name.<br>
	 * <br>
	 * If empty is used, expected type will be determined using setter expression and try to guess
	 * destination property type.<br>
	 * <br>
	 * This expression should return a {@link Type} object and is evaluated from null as root
	 * object. You can use context variables through {@link OgnlConstants} or
	 * {@link GroovyConstants}.<br>
	 * Any valid value in laguage defined by {@link #remoteTypeInterpreter()} is accepted.
	 * 
	 * @return An expression to get a {@link Type} assignable to destination property
	 */
	String remoteExpectedType() default "";

	/**
	 * Interpreter language to execute {@link #remoteExpectedType()} expression.<br>
	 * Default is {@link InterpreterType#REFLECTION}
	 * 
	 * @return Language of {@link #remoteExpectedType()} expression
	 */
	InterpreterType remoteTypeInterpreter() default InterpreterType.REFLECTION;

	/**
	 * Identifier name used when registering a custom converter in the {@link TypeConverter}. This
	 * name is used to force a conversion using a custom converter when copying a value from local
	 * to remote property. <br>
	 * You can pass additional context to the converter using related annotations defined by
	 * {@link #contextAnnotations()}.<br>
	 * <br>
	 * An empty string indicates that used converter should be determined dinamically using source
	 * an destination types
	 * 
	 * @return A converter name. Normally the complete class name (with package)
	 */
	String local2remoteConversor() default "";

}
