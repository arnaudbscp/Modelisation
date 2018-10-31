package ihm;

public class StrategyRotationY implements Strategy{

	double valeurrotation;
	
	public StrategyRotationY(double i) {
		valeurrotation = i;
	}
	public char execute() {

		return 'Y';
	}

	public double getValeurrotation() {
		return valeurrotation;
	}
	public void setValeurrotation(double valeurrotation) {
		this.valeurrotation = valeurrotation;
	}
}
