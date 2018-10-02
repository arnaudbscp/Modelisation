package chargement;
/**
 * Représente une face, composée de 3 Point et de segments les liant.
 * @author Valentin
 *
 */
public class Face {
	/**
	 * Point 1 de la Face.
	 */
	private Point pt1;
	/**
	 * Point 2 de la Face.
	 */
	private Point pt2;
	/**
	 * Point 3 de la Face.
	 */
	private Point pt3;
	/**
	 * Centre de gravité de la face triangulaire. Il s'agit du Point situé à l'intersection des 3 médianes.
	 */
	private Point centre_gravite;
	
	/**
	 * Constructeur.
	 * @param pt1
	 * @param pt2
	 * @param pt3
	 */
	public Face(Point pt1, Point pt2, Point pt3) {
		super();
		this.pt1 = pt1;
		this.pt2 = pt2;
		this.pt3 = pt3;
	}
	
	/**
	 * Retourne les 3 Point de la Face, sous forme d'un tableau.
	 * @return
	 */
	public Point[] getPoints() {
		return new Point[] {pt1,pt2,pt3};
	}
	
	/**
	 * Retourne les coordonnées X des 3 points de la Face, sous forme d'un tableau de double.
	 * @return
	 */
	public double[] recupX() {
		double[] x = new double[] {pt1.getX()+500,pt2.getX()+500,pt2.getX()+500};
		return x;
	}
	
	/**
	 * Retourne les coordonnées Y des 3 points de la Face, sous forme d'un tableau de double.
	 * @return
	 */
	public double[] recupY() {
		double[] y = new double[] {pt1.getY()+250,pt2.getY()+250,pt2.getY()+250};
		return y;
	}
	
	/**
	 * Retourne le centre de gravité de la face triangulaire.
	 * @return
	 */
	public Point getCentre_gravite() {
		return centre_gravite;
	}
	
	/**
	 * Définit le centre de gravité de la face triangulaire.
	 * @param centre_gravite
	 */
	public void setCentre_gravite(Point centre_gravite) {
		this.centre_gravite = centre_gravite;
	}
	
	/**
	 * Calcule le centre de gravité de la Face triangulaire.
	 * @param f
	 * @return
	 */
	public Point calculCentreGravite() {
		float x = (this.getPoints()[0].getX()+this.getPoints()[1].getX()+this.getPoints()[2].getX())/3;
		float y = (this.getPoints()[0].getY()+this.getPoints()[1].getY()+this.getPoints()[2].getY())/3;
		float z = (this.getPoints()[0].getZ()+this.getPoints()[1].getZ()+this.getPoints()[2].getZ())/3;
		return new Point(x, y, z);
	}

	/**
	 * Représentation textuelle d'une Face.
	 */
	public String toString() {
		return "\n[Face:\nPoint 1:"+pt1.toString()+"\nPoint 2:"+pt2.toString()+"\nPoint 3:"+pt3.toString()+"\nBarycentre:"+centre_gravite+"]\n";
	}
}
