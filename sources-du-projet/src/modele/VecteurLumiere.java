package src.modele;

/**
 * Classe représentant le vecteur lumière, soit le vecteur sur l'axe Z, permettant ensuite le calcul du vecteur normal de chaque face.
 * @author Valentin
 *
 */
public class VecteurLumiere {
	
	/**
	 * Le vecteur lumière, sous forme de Point.
	 */
	private final Point VECTEUR_LUMIERE = new Point(0, 0, -5);

	/**
	 * Retourne le vecteur lumière sous forme de Point.
	 * @return
	 */
	public Point getVecteur() {
		return VECTEUR_LUMIERE;
	}
	
	/**
	 * Retourne la coordonnée X du vecteur lumière.
	 * @return
	 */
	public float getX() {
		return VECTEUR_LUMIERE.getX();
	}
	
	/**
	 * Retourne la coordonnée Y du vecteur lumière.
	 * @return
	 */
	public float getY() {
		return VECTEUR_LUMIERE.getY();
	}
	
	/**
	 * Retourne la coordonnée Z du vecteur lumière.
	 * @return
	 */
	public float getZ() {
		return VECTEUR_LUMIERE.getZ();
	}
}
