package src.modele;

/**
 * Implémentation du design-pattern Strategy pour le mouvement de Rotation Z.
 */
public class StrategyRotationZ implements Strategy{

	/**
	 * Stockage de la valeur du Slider de rotation Z, pour garder sa valeur malgré les switch sur les autres sliders.
	 */
	private double valeurRotation;

	/**
	 * Constructeur spécifiant la valeur du Slider de rotation Z, à conserver.
	 */
	public StrategyRotationZ(double i) {
		valeurRotation = i;
	}
	
	public char execute() {
		return 'Z';
	}

	public double getValeurRotation() {
		return valeurRotation;
	}
	
	public void setValeurRotation(double valeurRotation) {
		this.valeurRotation = valeurRotation;
	}
}
