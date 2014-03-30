/**
 * Created on: 13/02/2010 18:11:21 by: Dario L. Garcia
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

/**
 * This class represents a generalization of food
 * 
 * @author D. Garcia
 */
public class Food {

	private Double servingSizeGrams;
	private Integer calories;
	private Integer totalFat;
	private Integer saturatedFatGrams;
	private Integer transFatGrams;
	private Integer cholesterolMiligrams;
	private Double sodiumMiligrams;
	private Double carbohydrateGrams;
	private Double dietaryFiber;
	private Double sugarGrams;
	private Double proteinGrams;

	public Double getServingSizeGrams() {
		return servingSizeGrams;
	}

	public void setServingSizeGrams(Double servingSizeGrams) {
		this.servingSizeGrams = servingSizeGrams;
	}

	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	public Integer getTotalFat() {
		return totalFat;
	}

	public void setTotalFat(Integer totalFat) {
		this.totalFat = totalFat;
	}

	public Integer getSaturatedFatGrams() {
		return saturatedFatGrams;
	}

	public void setSaturatedFatGrams(Integer saturatedFatGrams) {
		this.saturatedFatGrams = saturatedFatGrams;
	}

	public Integer getTransFatGrams() {
		return transFatGrams;
	}

	public void setTransFatGrams(Integer transFatGrams) {
		this.transFatGrams = transFatGrams;
	}

	public Integer getCholesterolMiligrams() {
		return cholesterolMiligrams;
	}

	public void setCholesterolMiligrams(Integer cholesterolMiligrams) {
		this.cholesterolMiligrams = cholesterolMiligrams;
	}

	public Double getSodiumMiligrams() {
		return sodiumMiligrams;
	}

	public void setSodiumMiligrams(Double sodiumMiligrams) {
		this.sodiumMiligrams = sodiumMiligrams;
	}

	public Double getCarbohydrateGrams() {
		return carbohydrateGrams;
	}

	public void setCarbohydrateGrams(Double carbohydrateGrams) {
		this.carbohydrateGrams = carbohydrateGrams;
	}

	public Double getDietaryFiber() {
		return dietaryFiber;
	}

	public void setDietaryFiber(Double dietaryFiber) {
		this.dietaryFiber = dietaryFiber;
	}

	public Double getSugarGrams() {
		return sugarGrams;
	}

	public void setSugarGrams(Double sugarGrams) {
		this.sugarGrams = sugarGrams;
	}

	public Double getProteinGrams() {
		return proteinGrams;
	}

	public void setProteinGrams(Double proteinGrams) {
		this.proteinGrams = proteinGrams;
	}

}
