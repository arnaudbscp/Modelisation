package ihm;

import chargement.Face;
import chargement.Matrice;
import chargement.Point;
import exception.MatriceFormatException;
import exception.MatriceNullException;
import outils.BoiteaOutils;

public class Rotation {

	public Point[] creerPointrotate(double angle, Point[] p, int axe) throws MatriceNullException, MatriceFormatException {

		BoiteaOutils bo = new BoiteaOutils(); 
		double[][] matricerotate = bo.creerRotation(angle);
		Matrice m = new Matrice(matricerotate);
		double[][] matricefigure;
		if(axe == 0) {
			matricefigure = m.creerMatriceY(p);
		}else if(axe == 1) {
			matricefigure = m.creerMatriceX(p);
		}else {
			matricefigure = m.creerMatriceZ(p);
		}
		double[][] matriceres = m.multiplierMatrice(matricefigure);
		Point[] tabp = creerTabPoint(matriceres, p,axe);
		return tabp;
	}

	private Point[] creerTabPoint(double[][] matrice, Point[] p, int axe) {
		Point[] tabp = new Point[matrice[0].length];
		boolean premiertour = true;
		for (int i = 0; i < matrice.length; i++) {
			for (int j = 0; j < matrice[0].length; j++) {
				if(premiertour)
					tabp[j] = new Point(0, 0, 0);
				if(i == 0) {
					if(axe == 0 || axe == 2)
						tabp[j].setX((float)matrice[i][j]);
					else if(axe == 1)
						tabp[j].setY((float)matrice[i][j]);
				} else if(i == 1) {
					if(axe == 0) 
						tabp[j].setY((float)matrice[i][j]);
					else if(axe == 1 || axe == 2)
						tabp[j].setZ((float)matrice[i][j]);
				}
			}
			premiertour = false;
		}
		for(int idx = 0; idx<p.length;idx++)
			if(axe == 0)
				tabp[idx].setZ(p[idx].getZ());
			else if(axe == 1)
				tabp[idx].setX(p[idx].getX());
			else if(axe == 2)
				tabp[idx].setY(p[idx].getY());
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
