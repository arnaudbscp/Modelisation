package ihm;

public class StrategyRotationY implements Strategy{

	private double valeurRotation;
	
	public StrategyRotationY(double i) {
		valeurRotation = i;
	}
	
	public char execute() {
		return 'Y';
	}

	public double getValeurrotation() {
		return valeurRotation;
	}
	
	public void setValeurrotation(double valeurrotation) {
		this.valeurRotation = valeurrotation;
	}
}
