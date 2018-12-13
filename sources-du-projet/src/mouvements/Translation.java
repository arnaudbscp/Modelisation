package src.mouvements;

import src.donnees.Face;
import src.donnees.Matrice;
import src.donnees.Point;

import src.outils.BoiteaOutils;

/**
 * Classe représentant un mouvement de translation.
 * @author Valentin
 *
 */
public class Translation implements Recopie{
	
	/**
	 * Méthode effectuant la translation et retournant le tableau de points de la figure après avoir effectué la translation. Cette méthode
	 * ne remplace pas directement les anciens points des faces de la figure par les nouveaux, il faudra pour cela appeler la méthode recopiePoint
	 * de l'interface Recopie.
	 * @param x1 : translation horizontale.
	 * @param x2 : translation verticale.
	 * @param p : le tableau de points de la figure sur lesquels effectuer la translation.
	 * @return le nouveau tableau de points représentant la figure après avoir effectueé la translation.
	 * @throws MatriceNullException
	 * @throws MatriceFormatException
	 */
	public Point[] creerPointsTranslate(double x1, double x2, Point[] p) {
		BoiteaOutils bo = new BoiteaOutils(); 
		double[][] matriceDeTranslation = bo.creerTranslation(x1, x2);
		Matrice m = new Matrice(matriceDeTranslation);
		double[][] matriceDeFigure = m.creerMatriceY(p);
		double[][] matriceResultat = m.additionMatrice(matriceDeFigure);
		Point[] tabPointsRes = creerTabPoint(matriceResultat);
		return tabPointsRes;
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
