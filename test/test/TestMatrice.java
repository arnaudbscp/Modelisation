package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import donnees.Matrice;
import donnees.Point;

import exception.MatriceFormatException;
import exception.MatriceNullException;

/**
 * Classe de test sur les matrices, à savoir la création et la multiplication de matrices.
 * @author Valentin
 *
 */
class TestMatrice {

	/**
	 * Méthode testant que le résultat du produit de deux matrices est correct.
	 * @throws MatriceNullException
	 * @throws MatriceFormatException
	 */
	@Test
	void testMultiplier() throws MatriceNullException, MatriceFormatException {

		double[][] tab = new double[][] {{1, 2, 3},{3, 2, 1},{2, 3, 1}};

		double[][] tab2 = new double[][] {{2, 2, 2},{1, 1, 1},{3, 3, 3}};

		double[][] res = new double[][] {{13, 13, 13},{11, 11, 11},	{10, 10, 10}};

		Matrice m = new Matrice(tab);
		double[][] multiplier = m.multiplierMatrice(tab2);
		for(int i = 0; i < multiplier.length; i++) {
			for(int j = 0; j < multiplier[0].length; j++)
				assertEquals(multiplier[i][j], res[i][j]); 
		}	
	}

	//NE PASSE PAS
	@Test
	void testMultiplierBis() throws MatriceNullException, MatriceFormatException {

		double[][] tab = new double[][] {{1, 2}, {3, 4}, {5, 6}};

		double[][] tab2 = new double[][] {{1, 2, 3, 4}, {4, 5, 6, 7}};

		double[][] res = new double[][] {{13, 13, 13}, {11, 11, 11}, {10, 10, 10}};

		Matrice m = new Matrice(tab);
		double[][] multiplier = m.multiplierMatrice(tab2);
		for(int i = 0; i < multiplier.length; i++) {
			for(int j = 0; j < multiplier[0].length; j++)
				assertEquals(multiplier[i][j], res[i][j]);
		}	
	}

	/**
	 * Méthode testant la création d'une matrice à partir de 3 points.
	 */
	@Test
	void testCreerMatrice() {
		Point[] tabp = new Point[3];
		Point p1 = new Point(10, 5, 3);
		Point p2 = new Point(15, 3, 8);
		Point p3 = new Point(35, 42, 10);
		tabp[0] = p1;
		tabp[1] = p2;
		tabp[2] = p3;

		double[][] tabres = new double[][] {{10, 15, 35}, {5, 3, 42}, {1, 1, 1}};

		Matrice m = new Matrice(null);
		double[][] creer = m.creerMatriceY(tabp);
		for(int i = 0; i < creer.length; i++) {
			for(int j = 0; j < creer[0].length; j++)
				assertEquals(tabres[i][j], creer[i][j]);
		}	
	}
}