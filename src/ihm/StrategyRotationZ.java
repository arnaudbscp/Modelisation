package ihm;

public class StrategyRotationZ implements Strategy{

	double valeurrotation;

	public StrategyRotationZ(double i) {
		valeurrotation = i;
	}
	public char execute() {

		return 'Z';
	}

	public double getValeurrotation() {
		return valeurrotation;
	}
	public void setValeurrotation(double valeurrotation) {
		this.valeurrotation = valeurrotation;
	}
}
