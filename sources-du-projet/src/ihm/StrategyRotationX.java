package ihm;

/**
 * A DOCUMENTER
 */
public class StrategyRotationX implements Strategy{

	/**
	 * A DOCUMENTER
	 */
	private double valeurRotation;
	
	/**
	 * A DOCUMENTER
	 */
	public StrategyRotationX(double i) {
		valeurRotation = i;
	}
	
	public char execute() {
		return 'X';
	}
	
	public double getValeurRotation() {
		return valeurRotation;
	}
	
	public void setValeurRotation(double valeurRotation) {
		this.valeurRotation = valeurRotation;
	}
}
