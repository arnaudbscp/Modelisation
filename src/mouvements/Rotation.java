package mouvements;

import donnees.Face;
import donnees.Matrice;
import donnees.Point;

import exception.MatriceFormatException;
import exception.MatriceNullException;

import outils.BoiteaOutils;

public class Rotation implements Recopie{

	public Point[] creerPointRotate(double angle, Point[] p, char axe) throws MatriceNullException, MatriceFormatException {

		BoiteaOutils bo = new BoiteaOutils(); 
		double[][] matriceRotate = null;
		if(axe == 'X') {
			matriceRotate = bo.creerRotationX(angle);
		}else if(axe == 'Y') {
			matriceRotate = bo.creerRotationY(angle);
		}else if(axe == 'Z') {
			matriceRotate = bo.creerRotationZ(angle);
		}
		Matrice m = new Matrice(matriceRotate);
		double[][] matriceFigure;
		matriceFigure = m.creerMatrice(p);
		double[][] matriceRes = m.multiplierMatrice(matriceFigure);
		Point[] tabp = creerTabPoint(matriceRes, p);
		return tabp;
	}



	private Point[] creerTabPoint(double[][] matrice, Point[] p) {
		Point[] tabp = new Point[matrice[0].length];
		boolean premierTour = true;
		for (int i = 0; i < matrice.length; i++) {
			for (int j = 0; j < matrice[0].length; j++) {
				if(premierTour)
					tabp[j] = new Point(0, 0, 0);
				if(i == 0) {
					tabp[j].setX((float)matrice[i][j]);
				} else if(i == 1) {
					tabp[j].setY((float)matrice[i][j]);
				}else if(i == 2) {
					tabp[j].setZ((float)matrice[i][j]);
				}
			}
			premierTour = false;
		}
		return tabp;
	}

	public void recopiePoint(Face[] faces, Point[] points) {
		for(int i = 0; i < faces.length; i++) {
			faces[i].setPt1(points[faces[i].getPosition()[0]]);
			faces[i].setPt2(points[faces[i].getPosition()[1]]);
			faces[i].setPt3(points[faces[i].getPosition()[2]]);
		}
	}
}