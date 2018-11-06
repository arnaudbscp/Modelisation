package src.ihm;

/**
 * Implémentation du design-pattern Strategy pour le mouvement de Rotation X.
 */
public class StrategyRotationX implements Strategy{

	/**
	 * Stockage de la valeur du Slider de rotation X, pour garder sa valeur malgré les switch sur les autres sliders.
	 */
	private double valeurRotation;
	
	/**
	 * Constructeur spécifiant la valeur du Slider de rotation X, à conserver.
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
