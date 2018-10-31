package ihm;

public class StrategyRotationX implements Strategy{

	double valeurrotation;
	
	
	public StrategyRotationX(double i) {
		valeurrotation = i;
	}
	public char execute() {
		
		return 'X';
	}
	
	public double getValeurrotation() {
		return valeurrotation;
	}
	public void setValeurrotation(double valeurrotation) {
		this.valeurrotation = valeurrotation;
	}
}
