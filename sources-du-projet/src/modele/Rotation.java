package src.modele;

/**
 * Classe représentant un mouvement de rotation.
 * @author Valentin
 *
 */
public class Rotation implements Recopie{

	/**
	 * Méthode effectuant la rotation et retournant le tableau de points de la figure après avoir effectué la rotation. Cette méthode
	 * ne remplace pas directement les anciens points des faces de la figure par les nouveaux, il faudra pour cela appeler la méthode recopiePoint
	 * de l'interface Recopie.
	 * @param angle : angle de rotation en degré.
	 * @param p : le tableau de points de la figure sur lesquels effectuer la rotation.
	 * @param axe : l'axe sur lequel effectuer la rotation (X, Y ou Z).
	 * @return le nouveau tableau de points représentant la figure après avoir effectueé la rotation.
	 * @throws MatriceNullException
	 * @throws MatriceFormatException
	 */
	public Point[] creerPointRotate(double angle, Point[] p, char axe) {
		angle=Math.toRadians(angle);
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
		Point[] tabp = creerTabPoint(matriceRes);
		return tabp;
	}

	public Point[] creerTabPoint(double[][] matrice) {
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