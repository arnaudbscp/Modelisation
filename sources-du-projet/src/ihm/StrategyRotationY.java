package src.ihm;

/**
 * A DOCUMENTER
 */
public class StrategyRotationY implements Strategy{

	/**
	 * A DOCUMENTER
	 */
	private double valeurRotation;
	
	/**
	 * A DOCUMENTER
	 */
	public StrategyRotationY(double i) {
		valeurRotation = i;
	}
	
	public char execute() {
		return 'Y';
	}

	public double getValeurRotation() {
		return valeurRotation;
	}
	
	public void setValeurRotation(double valeurRotation) {
		this.valeurRotation = valeurRotation;
	}
}
