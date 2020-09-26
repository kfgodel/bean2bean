/**
 * Created on: 15/02/2010 23:25:43 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.metadata;

import net.sf.kfgodel.bean2bean.annotations.MissingPropertyAction;
import net.sf.kfgodel.bean2bean.population.PopulationType;

import java.lang.annotation.Annotation;
import java.util.Arrays;


/**
 * Esta clase representa una declaración de un mapeo de propiedad. Esta clase solo refleja los
 * parámetros definidos por el usuario
 * 
 * @author D. Garcia
 */
public class PropertyMappingDeclaration {

	private ExpressionDeclaration getterExpression;
	private ExpressionDeclaration setterExpression;
	private ExpressionDeclaration expectedTypeDeclaration;
	private MissingPropertyAction actionWhenMissing;
	private String designatedConverter;
	private Class<? extends Annotation>[] relatedAnnotationTypes;
	private Annotation[] relatedAnnotations;
	private PopulationType populationType;

	public ExpressionDeclaration getGetterExpression() {
		return getterExpression;
	}

	public void setGetterExpression(ExpressionDeclaration getterExpression) {
		this.getterExpression = getterExpression;
	}

	public ExpressionDeclaration getSetterExpression() {
		return setterExpression;
	}

	public void setSetterExpression(ExpressionDeclaration setterExpression) {
		this.setterExpression = setterExpression;
	}

	public ExpressionDeclaration getExpectedTypeDeclaration() {
		return expectedTypeDeclaration;
	}

	public void setExpectedTypeDeclaration(ExpressionDeclaration expectedTypeDeclaration) {
		this.expectedTypeDeclaration = expectedTypeDeclaration;
	}

	public MissingPropertyAction getActionWhenMissing() {
		return actionWhenMissing;
	}

	public void setActionWhenMissing(MissingPropertyAction actionWhenMissing) {
		this.actionWhenMissing = actionWhenMissing;
	}

	public String getDesignatedConverter() {
		return designatedConverter;
	}

	public void setDesignatedConverter(String designatedConverter) {
		this.designatedConverter = designatedConverter;
	}

	public Class<? extends Annotation>[] getRelatedAnnotationTypes() {
		return relatedAnnotationTypes;
	}

	public void setRelatedAnnotationTypes(Class<? extends Annotation>[] relatedAnnotations) {
		this.relatedAnnotationTypes = relatedAnnotations;
	}

	public static PropertyMappingDeclaration create(ExpressionDeclaration getterDeclaration,
			ExpressionDeclaration expectedTypeDeclaration, ExpressionDeclaration setterDeclaration,
			MissingPropertyAction whenMissing, String useConversor, Class<? extends Annotation>[] contextAnnotations,
			PopulationType populationType) {
		PropertyMappingDeclaration declaration = new PropertyMappingDeclaration();
		declaration.setActionWhenMissing(whenMissing);
		declaration.setDesignatedConverter(useConversor);
		declaration.setExpectedTypeDeclaration(expectedTypeDeclaration);
		declaration.setGetterExpression(getterDeclaration);
		declaration.setRelatedAnnotationTypes(contextAnnotations);
		declaration.setSetterExpression(setterDeclaration);
		declaration.setPopulationType(populationType);

		return declaration;
	}

	public Annotation[] getRelatedAnnotations() {
		return relatedAnnotations;
	}

	public void setRelatedAnnotations(Annotation[] relatedAnnotations) {
		this.relatedAnnotations = relatedAnnotations;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName());
		builder.append("[ getter:");
		builder.append(getGetterExpression());
		builder.append(", setter:");
		builder.append(getSetterExpression());
		if (!getExpectedTypeDeclaration().isEmpty()) {
			builder.append(", type:");
			builder.append(getExpectedTypeDeclaration());
		}
		builder.append(", whenMissing:");
		builder.append(getActionWhenMissing());
		builder.append(", populationType:");
		builder.append(getPopulationType());
		if (getDesignatedConverter().length() > 0) {
			builder.append(", converter:\"");
			builder.append(getDesignatedConverter());
			builder.append("\"");
		}
		if (getRelatedAnnotationTypes().length > 0) {
			builder.append(", @types:[");
			builder.append(Arrays.toString(getRelatedAnnotationTypes()));
		}
		if (getRelatedAnnotationTypes().length > 0) {
			builder.append(", annotations:[");
			builder.append(Arrays.toString(getRelatedAnnotations()));
		}
		builder.append(" ]");
		return builder.toString();
	}

	public PopulationType getPopulationType() {
		return populationType;
	}

	public void setPopulationType(PopulationType populationType) {
		this.populationType = populationType;
	}

}
