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
	
	/**
	 * Constructeur créant le point dans l'espace 3D à partir des 3 coordonnées X,Y et Z.
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Définit la coordonnée X du point.
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * Définit la coordonnée Y du point.
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Définit la coordonnée Z du point.
	 * @param z
	 */
	public void setZ(float z) {
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
