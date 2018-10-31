package ihm;

public class StrategyRotationZ implements Strategy{

	private double valeurRotation;

	public StrategyRotationZ(double i) {
		valeurRotation = i;
	}
	
	public char execute() {
		return 'Z';
	}

	public double getValeurrotation() {
		return valeurRotation;
	}
	
	public void setValeurrotation(double valeurrotation) {
		this.valeurRotation = valeurrotation;
	}
}
