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
	 * Représentation textuelle d'une Face.
	 */
	public String toString() {
		return "\n[Face:\nPoint 1:"+pt1.toString()+"\nPoint 2:"+pt2.toString()+"\nPoint 3:"+pt3.toString()+"]\n";
	}
}
