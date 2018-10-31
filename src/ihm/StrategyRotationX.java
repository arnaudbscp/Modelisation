package ihm;

public class StrategyRotationX implements Strategy{

	private double valeurRotation;
	
	public StrategyRotationX(double i) {
		valeurRotation = i;
	}
	
	public char execute() {
		return 'X';
	}
	
	public double getValeurrotation() {
		return valeurRotation;
	}
	
	public void setValeurrotation(double valeurRotation) {
		this.valeurRotation = valeurRotation;
	}
}
