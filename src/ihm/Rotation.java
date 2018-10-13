package ihm;

import chargement.LoadFile;
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
		return CreerTabPoint(matriceres,p);
	}

	private Point[] CreerTabPoint(double[][] matrice, Point[] p) {
		Point[] tabp = new Point[matrice[0].length];
		boolean premiertour = true;
		for (int i = 0; i < matrice.length; i++) {
			for (int j = 0; j < matrice[0].length; j++) {
				if(premiertour) {
					tabp[j] = new Point(0, 0, 0);
				}
				if(i == 0) {
					tabp[j].setX((float)matrice[i][j]);
				}else if(i == 1) {
					tabp[j].setY((float)matrice[i][j]);
				}else if(i == 2) {
					tabp[j].setZ(p[i].getZ());
				}
			}
			premiertour = false;
		}
		return tabp;
	}
}
