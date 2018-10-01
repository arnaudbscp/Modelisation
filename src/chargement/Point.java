package chargement;

/**
 * Représente un point dans un espace à 3 dimensions.
 * @author genartv
 *
 */
public class Point {
	/**
	 * Coordonnée sur l'axe x du Point.
	 */
	private float x;
	/**
	 * Coordonnée sur l'axe y du Point.
	 */
	private float y;
	/**
	 * Coordonnée sur l'axe z du Point.
	 */
	private float z;
	public Point(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Retourne la coordonnée x du point.
	 * @return
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Retourne la coordonnée y du point.
	 * @return
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Retourne la coordonnée y du point.
	 * @return
	 */
	public float getZ() {
		return z;
	}
	
	/**
	 * Retourne un tableau contenant les 3 coordonnées du Point.
	 * @return
	 */
	public float[] getCoordonnees() {
		return new float[] {x,y,z};
	}
	
	/**
	 * Représentation textuelle d'un Point.
	 */
	public String toString() {
		return "[Point: x="+x+" y="+y+" z="+z+"]";
	}
}
