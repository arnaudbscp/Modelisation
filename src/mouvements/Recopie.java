package mouvements;

import donnees.Face;
import donnees.Point;

/**
 * Interface contenant la méthode de remplacement des anciens points de chaque face de la figure par les nouveaux points générés 
 * par un mouvement quelconque (rotation, translation ou homothétie).
 * @author Valentin
 *
 */
public interface Recopie {
	/**
	 * Méthode de remplacement des anciens points de chaque face de la figure par les nouveaux points générés 
	 * par un mouvement quelconque (rotation, translation ou homothétie).
	 * @param faces
	 * @param points
	 */
	public void recopiePoint(Face[] faces, Point[] points);
}
