package src.donnees;

import src.exception.MatriceFormatException;
import src.exception.MatriceNullException;

/**
 * Création de matrices (réprésente un tableau à deux dimensions), avec opérations de multiplications et additions.
 */
public class Matrice {
	
	/**
	 * Tableau à deux dimensions de double, matrice de base.
	 */
	double[][] MA;
	
	/**
	 * Initialisation de la matrice de base avec celle passée en paramètre.
	 */
	public Matrice(double[][] MA) {
		this.MA = MA;
	}

	/**
	 * Création d'une matrice de la figure à partir du tableau de point passé en paramètre.
	 * @return tab
	 */
	public double[][] creerMatrice(Point[] p) {
		double[][] tab = new double[3][p.length];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < p.length; j++) {
				if(i == 0)
					tab[i][j] = p[j].getX();
				else if(i == 1)
					tab[i][j] = p[j].getY();
				else if(i == 2)
					tab[i][j] = p[j].getZ();
			}
		}
		return tab;
	}
	
	/**
	 * Création d'une matrice de la figure à partir du tableau de point par rapport à l'axe des Y (qui reste donc inchangé).
	 * @return tab
	 */
	public double[][] creerMatriceY(Point[] p) {
		double[][] tab = new double[3][p.length];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < p.length; j++) {
				if(i == 0)
					tab[i][j] = p[j].getX();
				else if(i == 1)
					tab[i][j] = p[j].getY();
				else if(i == 2)
					tab[i][j] = 1;
			}
		}
		return tab;
	}
	
	/**
	 * Création d'une matrice de la figure à partir du tableau de point par rapport à l'axe des X (qui reste donc inchangé).
	 * @return tab
	 */
	public double[][] creerMatriceX(Point[] p) {
		double[][] tab = new double[3][p.length];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < p.length; j++) {
				if(i == 0)
					tab[i][j] = p[j].getY();
				else if(i == 1)
					tab[i][j] = p[j].getZ();
				else if(i == 2)
					tab[i][j] = 1;
			}
		}
		return tab;
	}

	/**
	 * Création d'une matrice de la figure à partir du tableau de point par rapport à l'axe des Z (qui reste donc inchangé).
	 * @return tab
	 */
	
	public double[][] creerMatriceZ(Point[] p) {
		double[][] tab = new double[3][p.length];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < p.length; j++) {
				if(i == 0)
					tab[i][j] = p[j].getX();
				else if(i == 1)
					tab[i][j] = p[j].getZ();
				else if(i == 2)
					tab[i][j] = 1;
			}
		}
		return tab;
	}
	
	/**
	 * Multiplie la matrice de base avec celle passée en paramètre. Retourne la matrice résultant de l'opération.
	 * @return MC
	 */
	public double[][] multiplierMatrice(double[][] MB) throws MatriceNullException, MatriceFormatException {
		double[][] MC;
		int l, c;

		if(MA == null || MB == null)
			throw new MatriceNullException();


		if(MA[0].length != MB.length)
			throw new MatriceFormatException();

		MC = new double[MA.length][MB[0].length];

		l = 0;
		for (int i = 0; i < MA.length; i++){
			c = 0;
			for (int n = 0; n < MB[0].length; n++){

				float calcul = 0;
				for (int m = 0; m < MB.length; m++)
					calcul += MA[i][m] * MB[m][n];
				MC[l][c] = calcul;
				c++;
			}
			l++;
		}
		return MC;
	}
	
	/**
	 * Additionne la matrice de base avec celle passée en paramètre. Retourne la matrice résultant de l'opération.
	 * @return MC
	 */
	public double[][] additionMatrice(double[][] MB) throws MatriceNullException, MatriceFormatException {
		double[][] MC;
		int l, c;

		if(MA == null || MB == null)
			throw new MatriceNullException();


		if(MA[0].length != MB.length)
			throw new MatriceFormatException();

		MC = new double[MA.length][MB[0].length];

		l = 0;
		for (int i = 0; i < MA.length; i++){
			c = 0;
			for (int n = 0; n < MB[0].length; n++){

				float calcul = 0;
				for (int m = 0; m < MB.length; m++)
					calcul += MA[i][m] * MB[m][n];
				MC[l][c] = calcul;
				c++;
			}
			l++;
		}
		return MC;
	}
}