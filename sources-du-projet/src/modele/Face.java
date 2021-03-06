package src.modele;

/**
 * Représente une face, composée de 3 Point et de segments les liant.
 * @author Valentin
 *
 */
public class Face implements Comparable<Face>{
	
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
	private Point centreGravite;
	
	/**
	 * Tableau d'entiers représentant la position de la face dans le plan.
	 */
	private int[] position;
	
	/**
	 * Constructeur créant une face à partir des 3 points spécifiés.
	 * @param pt1
	 * @param pt2
	 * @param pt3
	 */
	public Face(Point pt1, Point pt2, Point pt3) {
		super();
		this.pt1 = pt1;
		this.pt2 = pt2;
		this.pt3 = pt3;
		this.position = new int[3];
	}
	
	/**
	 * Retourne la position de la Face, sous forme d'un tableau.
	 * @return position
	 */
	public int[] getPosition() {
		return position;
	}

	/**
	 * Définit la position de la face.
	 * @param position
	 */
	public void setPosition(int[] position) {
		this.position = position;
	}

	/**
	 * Retourne le premier point composant la face. 
	 * @return
	 */
	public Point getPt1() {
		return pt1;
	}

	/**
	 * Retourne le deuxieme point composant la face. 
	 * @return
	 */
	public Point getPt2() {
		return pt2;
	}

	/**
	 * Retourne le troisieme point composant la face. 
	 * @return
	 */
	public Point getPt3() {
		return pt3;
	}
	
	/**
	 * Definit le premier point composant la face.
	 * @param pt1
	 */
	public void setPt1(Point pt1) {
		this.pt1 = pt1;
	}

	/**
	 * Definit le deuxieme point composant la face.
	 * @param pt2
	 */
	public void setPt2(Point pt2) {
		this.pt2 = pt2;
	}

	/**
	 * Definit le troisieme point composant la face.
	 * @param pt3
	 */
	public void setPt3(Point pt3) {
		this.pt3 = pt3;
	}

	/**
	 * Retourne les 3 Point de la Face, sous forme d'un tableau.
	 * @return
	 */
	public Point[] getPoints() {
		return new Point[] {pt1, pt2, pt3};
	}
	
	/**
	 * Retourne le centre de gravite de la face triangulaire.
	 * @return centreGravite
	 */
	public Point getCentreGravite() {
		return centreGravite;
	}
	
	/**
	 * Definit le centre de gravite de la face triangulaire.
	 * @param centreGravite
	 */
	public void setCentreGravite(Point centreGravite) {
		this.centreGravite = centreGravite;
	}
	
	/**
	 * Calcule le centre de gravite de la Face triangulaire.
	 */
	public void calculCentreGravite() {
		float x = (this.getPoints()[0].getX() + this.getPoints()[1].getX() + this.getPoints()[2].getX()) / 3;
		float y = (this.getPoints()[0].getY() + this.getPoints()[1].getY() + this.getPoints()[2].getY()) / 3;
		float z = (this.getPoints()[0].getZ() + this.getPoints()[1].getZ() + this.getPoints()[2].getZ()) / 3;
		setCentreGravite(new Point(x, y, z));
	}

	/**
	 * Representation textuelle d'une Face.
	 */
	public String toString() {
		return "\n[Face:\nPoint 1:" + pt1.toString() + "\nPoint 2:" + pt2.toString() + "\nPoint 3:" + pt3.toString() + "\nBarycentre:" + centreGravite + "]\n";
	}

	/**
	 * Compare la coordonnée Y du barycentre de la face avec celle de la face passée en paramètre.
	 * Retourne 1 si la coordonnée Y du barycentre de la face est supérieure à celle de la face passée en paramètre.
	 * Retourne 0 si la coordonnée Y du barycentre de la face est égale à celle de la face passée en paramètre.
	 * Retourne -1 si la coordonnée Y du barycentre de la face est inférieure à celle de la face passée en paramètre.
	 */
	@Override
	public int compareTo(Face arg0) {
		float sub = this.getCentreGravite().getY() - arg0.getCentreGravite().getY();
		if (sub<0) return -1;
		if (sub>0) return 1;
		return 0;
	}

	/**
	 * Teste si la face est egale a celle passee en parametre.
	 * @param f
	 * @return
	 */
	public boolean equals(Face f) {
		return this.pt1.equals(f.pt1) && this.pt2.equals(f.pt2) && this.pt3.equals(f.pt3) && this.centreGravite.equals(f.centreGravite);
	}
}
