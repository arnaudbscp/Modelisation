package src.outils;

/**
 * Contient les différentes matrices définissant chaque mouvement possible.
 * @author genartv
 *
 */
public class BoiteaOutils {

	/**
	 * Retourne la matrice de rotation sur l'axe X.
	 * @param angle
	 * @return
	 */
	public double[][] creerRotationX(double angle) {
		double[][] tab = new double[][] {
			{1, 0, 0},
			{0, Math.cos(angle), -Math.sin(angle)},
			{0, Math.sin(angle), Math.cos(angle)}};
			return tab;
	}

	/**
	 * Retourne la matrice de rotation sur l'axe Y.
	 * @param angle
	 * @return
	 */
	public double[][] creerRotationY(double angle) {
		double[][] tab = new double[][] {
			{Math.cos(angle), 0, Math.sin(angle)},
			{0, 1, 0},
			{-Math.sin(angle), 0, Math.cos(angle)}};
			return tab;
	}

	/**
	 * Retourne la matrice de rotation sur l'axe Z.
	 * @param angle
	 * @return
	 */
	public double[][] creerRotationZ(double angle) {
		double[][] tab = new double[][] {
			{Math.cos(angle), -Math.sin(angle), 0.0},
			{Math.sin(angle), Math.cos(angle), 0.0},
			{0.0, 0.0, 1.0}};
			return tab;
	}
	/**
	 * Retourne la matrice de translation.
	 * @param x1
	 * @param x2
	 * @return
	 */
	public double[][] creerTranslation(double x1, double x2) {
		double[][] tab = new double[][] {
			{1, 0, x1},
			{0, 1, x2},
			{0, 0, 1}};
			return tab;
	}

	/**
	 * Retourne la matrice d'homothétie (zoom).
	 * @param k
	 * @return
	 */
	public double[][] creerHomothetie(double k) {
		double[][] tab = new double[][] {{k, 0, 0},
			{0, k, 0},
			{0, 0, 1}};
			return tab;
	}
}