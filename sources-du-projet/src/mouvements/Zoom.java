package src.mouvements;

import src.donnees.Face;
import src.donnees.Matrice;
import src.donnees.Point;

import src.exception.MatriceFormatException;
import src.exception.MatriceNullException;

import src.outils.BoiteaOutils;

/**
 * Classe représentant un mouvement de zoom (homothétie).
 * @author bascopa
 *
 */
public class Zoom implements Recopie{
	
	/**
	 * Méthode effectuant l'homothétie (zoom) et retournant le tableau de points de la figure après avoir effectué l'homthétie. Cette méthode
	 * ne remplace pas directement les anciens points des faces de la figure par les nouveaux, il faudra pour cela appeler la méthode recopiePoint
	 * de l'interface Recopie.
	 * @param z : la valeur du zoom.
	 * @param p : le tableau de points de la figure sur lesquels effectuer l'homothétie.
	 * @return le nouveau tableau de points représentant la figure après avoir effectueé l'homothétie.
	 * @throws MatriceNullException
	 * @throws MatriceFormatException
	 */
	public Point[] creerPointZoom(double z, Point[] p) throws MatriceNullException, MatriceFormatException {
		BoiteaOutils bo = new BoiteaOutils();
		double[][] matriceZoom = bo.creerHomothetie(z);
		Matrice m = new Matrice(matriceZoom);
		double[][] f = m.creerMatrice(p);
		double[][] matriceFigure = m.multiplierMatrice(f);
		Point[] fp = creerTabPoint(matriceFigure);
		return fp;
	}
	
	public Point[] creerTabPoint(double[][] matrice) {
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
		for(int i = 0; i < faces.length; i++) {
			faces[i].setPt1(points[faces[i].getPosition()[0]]);
			faces[i].setPt2(points[faces[i].getPosition()[1]]);
			faces[i].setPt3(points[faces[i].getPosition()[2]]);
		}
	}
}