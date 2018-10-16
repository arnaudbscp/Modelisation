package outils;

/**
 * Contient les différentes matrices définissant chaque mouvement possible.
 * @author genartv
 *
 */
public class BoiteaOutils {

	/**
	 * Retourne la matrice de rotation.
	 * @param angle
	 * @return
	 */
	public double[][] CreerRotation(double angle) {

		double[][] tab = new double[][] {{Math.cos(Math.toRadians(angle)), -Math.sin(Math.toRadians(angle)), 0 },
			                             {Math.sin(Math.toRadians(angle)),  Math.cos(Math.toRadians(angle)), 0 },
			                             {0,0,1}};
			return tab;
	}
	
	/**
	 * Retourne la matrice de translation.
	 * @param x1
	 * @param x2
	 * @return
	 */
	public double[][] CreerTranslation(double x1, double x2) {
		
		double[][] tab = new double[][] {{1 , 0 , x1 },
                                         {0 , 1 , x2 },
                                         {0 , 0 , 1  }};
                                         
        return tab;
	}
	
	/**
	 * Retourne la matrice d'homothétie.
	 * @param k
	 * @return
	 */
	public double[][] CreerHomothetie(double k) {
		
		double[][] tab = new double[][] {{k , 0 , 0 },
                                         {0 , k , 0 },
                                         {0 , 0 , 1}};
                                         
        return tab;
	}
}

