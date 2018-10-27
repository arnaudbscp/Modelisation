package mouvements;

import donnees.Face;
import donnees.Matrice;
import donnees.Point;

import exception.MatriceFormatException;
import exception.MatriceNullException;

import outils.BoiteaOutils;

public class Translation {
	
	public Point[] creerPointsTranslate(double x1,double x2, Point[] p) throws MatriceNullException, MatriceFormatException {
		BoiteaOutils bo = new BoiteaOutils(); 
		double[][] matriceDeTranslation = bo.creerTranslation(x1, x2);
		Matrice m = new Matrice(matriceDeTranslation);
		double[][] matriceDeFigure = m.creerMatriceY(p);
		double[][] matriceResultat = m.multiplierMatrice(matriceDeFigure);
		Point[] tabPointsRes = creerTabPoint(matriceResultat, p);
		return tabPointsRes;
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
			}
			premierTour = false;
		}
		for(int idx = 0; idx<p.length;idx++)
			tabp[idx].setZ(p[idx].getZ());
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
