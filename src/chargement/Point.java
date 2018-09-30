package chargement;

/**
 * Représente un point dans un espace à 3 dimensions.
 * @author genartv
 *
 */
public class Point {
	private float x;
	private float y;
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
	
	public String toString() {
		return "[Point: x="+x+" y="+y+" z="+z;
	}
}
