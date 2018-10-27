package mouvements;

import donnees.Face;
import donnees.Matrice;
import donnees.Point;

import exception.MatriceFormatException;
import exception.MatriceNullException;

import outils.BoiteaOutils;

/**
 * 
 * @author bascopa
 *
 */
public class Zoom implements Recopie{
	
	public Point[] creerPointZoom(double z, Point[] p) throws MatriceNullException, MatriceFormatException {
		BoiteaOutils bo = new BoiteaOutils();
		double[][] matriceZoom = bo.creerZoom(z);
		
		Matrice m = new Matrice(matriceZoom);
		
		double[][] f = m.creerMatrice(p);
		double[][] matriceFigure = m.multiplierMatrice(f);
		
		Point[] fp = creerTabPoint(matriceFigure,p);
		
		return fp;
	}
	
	private Point[] creerTabPoint(double[][] matrice, Point[] p) {
		Point[] tabp = new Point[matrice[0].length];
		boolean premierTour = true;
		for (int i = 0; i < matrice.length; i++) {
			for (int j = 0; j < matrice[0].length; j++) {
				if(premierTour)
					tabp[j] = new Point(0, 0, 0);
				if(i == 0)
					tabp[j].setX((float)matrice[i][j]);
				else if(i == 1)
					tabp[j].setY((float)matrice[i][j]);
				else
					tabp[j].setZ((float)matrice[i][j]);
			}
			premierTour = false;
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