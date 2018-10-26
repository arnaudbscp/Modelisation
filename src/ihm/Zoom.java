package ihm;

import chargement.Face;
import chargement.Matrice;
import chargement.Point;
import exception.MatriceFormatException;
import exception.MatriceNullException;
import outils.BoiteaOutils;
/**
 * 
 * @author bascopa
 *
 */

public class Zoom {
	
	public Point[] creerPointZoom(double z, Point[] p) throws MatriceNullException, MatriceFormatException {
		BoiteaOutils bo = new BoiteaOutils();
		double[][] matricezoom = bo.creerZoom(z);
		
		Matrice m = new Matrice(matricezoom);
		
		double[][] f = m.creerMatrice(p);
		double[][] matricefigure = m.multiplierMatrice(f);
		
		Point[] fp = creerTabPoint(matricefigure,p);
		
		return fp;
	}
	
	private Point[] creerTabPoint(double[][] matrice, Point[] p) {
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
				else
					tabp[j].setZ((float)matrice[i][j]);
			}
			premiertour = false;
		}
		return tabp;
	}

	public void recopiePoint(Face[] faces, Point[] points) {
		for(int i = 0; i<faces.length; i++) {
			faces[i].setPt1(points[faces[i].getPosition()[0]]);
			faces[i].setPt2(points[faces[i].getPosition()[1]]);
			faces[i].setPt3(points[faces[i].getPosition()[2]]);
		}
	}
}
