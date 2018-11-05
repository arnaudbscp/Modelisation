package src.ihm;

/**
 * A DOCUMENTER
 */
public class StrategyRotationZ implements Strategy{

	/**
	 * A DOCUMENTER
	 */
	private double valeurRotation;

	/**
	 * A DOCUMENTER
	 */
	public StrategyRotationZ(double i) {
		valeurRotation = i;
	}
	
	public char execute() {
		return 'Z';
	}

	public double getValeurRotation() {
		return valeurRotation;
	}
	
	public void setValeurRotation(double valeurRotation) {
		this.valeurRotation = valeurRotation;
	}
}
