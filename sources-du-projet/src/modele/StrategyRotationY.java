package src.modele;

/**
 * Implémentation du design-pattern Strategy pour le mouvement de Rotation Y.
 */
public class StrategyRotationY implements Strategy{

	/**
	 * Stockage de la valeur du Slider de rotation Y, pour garder sa valeur malgré les switch sur les autres sliders.
	 */
	private double valeurRotation;
	
	/**
	 * Constructeur spécifiant la valeur du Slider de rotation Y, à conserver.
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
