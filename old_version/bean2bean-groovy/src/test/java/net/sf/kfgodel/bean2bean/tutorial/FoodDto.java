/**
 * Created on: 13/02/2010 18:13:28 by: Dario L. Garcia
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

import net.sf.kfgodel.bean2bean.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.annotations.CopyTo;

/**
 * This class is a simple representation of food for a list
 * 
 * @author D. Garcia
 */
public class FoodDto {

	@CopyFrom("servingSizeGrams")
	@CopyTo("servingSizeGrams")
	private String serving;

	@CopyFrom
	@CopyTo
	private String calories;

	@CopyFrom
	@CopyTo
	private String totalFat;

	@CopyFrom("saturatedFatGrams")
	@CopyTo("saturatedFatGrams")
	private String saturatedFats;

	@CopyFrom("transFatGrams")
	@CopyTo("transFatGrams")
	private String transFats;

	@CopyFrom("cholesterolMiligrams")
	@CopyTo("cholesterolMiligrams")
	private String cholesterol;

	@CopyFrom
	@CopyTo
	private String sodiumMiligrams;

	@CopyFrom
	@CopyTo
	private String carbohydrateGrams;

	@CopyFrom
	@CopyTo
	private String dietaryFiber;

	@CopyFrom
	@CopyTo
	private String sugarGrams;

	@CopyFrom
	@CopyTo
	private String proteinGrams;

	public String getServing() {
		return serving;
	}

	public void setServing(String serving) {
		this.serving = serving;
	}

	public String getCalories() {
		return calories;
	}

	public void setCalories(String calories) {
		this.calories = calories;
	}

	public String getTotalFat() {
		return totalFat;
	}

	public void setTotalFat(String totalFat) {
		this.totalFat = totalFat;
	}

	public String getSaturatedFats() {
		return saturatedFats;
	}

	public void setSaturatedFats(String saturatedFats) {
		this.saturatedFats = saturatedFats;
	}

	public String getTransFats() {
		return transFats;
	}

	public void setTransFats(String transFats) {
		this.transFats = transFats;
	}

	public String getCholesterol() {
		return cholesterol;
	}

	public void setCholesterol(String cholesterol) {
		this.cholesterol = cholesterol;
	}

	public String getSodiumMiligrams() {
		return sodiumMiligrams;
	}

	public void setSodiumMiligrams(String sodiumMiligrams) {
		this.sodiumMiligrams = sodiumMiligrams;
	}

	public String getCarbohydrateGrams() {
		return carbohydrateGrams;
	}

	public void setCarbohydrateGrams(String carbohydrateGrams) {
		this.carbohydrateGrams = carbohydrateGrams;
	}

	public String getDietaryFiber() {
		return dietaryFiber;
	}

	public void setDietaryFiber(String dietaryFiber) {
		this.dietaryFiber = dietaryFiber;
	}

	public String getSugarGrams() {
		return sugarGrams;
	}

	public void setSugarGrams(String sugarGrams) {
		this.sugarGrams = sugarGrams;
	}

	public String getProteinGrams() {
		return proteinGrams;
	}

	public void setProteinGrams(String proteinGrams) {
		this.proteinGrams = proteinGrams;
	}

}
