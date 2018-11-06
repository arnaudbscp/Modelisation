package src.ihm;

/**
 * Interface représentant le design-pattern Strategy, permettant de n'avoir qu'un seul Slider pour les 3 mouvements de rotation, 
 * et de switcher entre ces 3 mouvements grâce à des boutons.
 */
public interface Strategy {
	
	/**
	 * Retourne le caractère correspondant à l'axe de rotation selectionné, pour mettre à jour le Label du Slider ('X', 'Y' ou 'Z').
	 */
	public char execute();
	
	/**
	 * Retourne la valeur du Slider de rotation sur l'axe associé.
	 */
	public double getValeurRotation();
	
	/**
	 * Définit la valeur du Slider de rotation sur l'axe associé.
	 */
	public void setValeurRotation(double valeurRotation);
}
