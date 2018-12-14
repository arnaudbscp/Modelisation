package src.modele;

/**
 * Interface contenant la méthode de remplacement des anciens points de chaque face de la figure par les nouveaux points générés 
 * par un mouvement quelconque (rotation, translation ou homothétie), ainsi que la méthode permettant la création de ces nouveaux points.
 * @author Valentin
 *
 */
public interface Recopie {
	
	/**
	 * Créer le nouveau tableau de points à partir d'une matrice résultant de la multiplication de la matrice type du mouvement concerné
	 * et de la matrice des anciens points de la figure.
	 * @param matrice
	 * @param p
	 * @return
	 */
	public Point[] creerTabPoint(double[][] matrice);
		
	/**
	 * Méthode de remplacement des anciens points de chaque face de la figure par les nouveaux points générés 
	 * par un mouvement quelconque (rotation, translation ou homothétie).
	 * @param faces
	 * @param points
	 */
	public void recopiePoint(Face[] faces, Point[] points);
}
