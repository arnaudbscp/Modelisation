package ihm;

import chargement.Face;
import chargement.Matrice;
import chargement.Point;
import exception.MatriceFormatException;
import exception.MatriceNullException;
import outils.BoiteaOutils;

public class Rotation {

	public Point[] CreerPointrotate(double angle, Point[] p) throws MatriceNullException, MatriceFormatException {

		BoiteaOutils bo = new BoiteaOutils(); 
		Matrice m = new Matrice();
		double[][] matricerotate = bo.CreerRotation(angle);
		double[][] matricefigure = m.CreerMatrice(p);
		double[][] matriceres = m.MultiplierMatrice(matricerotate, matricefigure);
		Point[] tabp = CreerTabPoint(matriceres, p);
		return tabp;
	}

	private Point[] CreerTabPoint(double[][] matrice, Point[] p) {
		Point[] tabp = new Point[matrice[0].length];
		boolean premiertour = true;
		for (int i = 0; i < matrice.length; i++) {
			for (int j = 0; j < matrice[0].length; j++) {
				if(premiertour)
					tabp[j] = new Point(0, 0, 0);
				if(i == 0)
					tabp[j].setX((float)matrice[i][j]);
				else if(i == 1)
					tabp[j].setY((float)matrice[i][j]);
			}
			premiertour = false;
		}
		for(int idx = 0; idx<p.length;idx++)
			tabp[idx].setZ(p[idx].getZ());
		return tabp;
	}
	
	public void RecopiePoint(Face[] faces, Point[] points) {
		
		for(int i = 0; i<faces.length; i++) {
			faces[i].setPt1(points[faces[i].getPosition()[0]]);
			faces[i].setPt2(points[faces[i].getPosition()[1]]);
			faces[i].setPt3(points[faces[i].getPosition()[2]]);
		}
	}
}
