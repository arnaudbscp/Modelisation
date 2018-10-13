package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chargement.Matrice;
import chargement.Point;
import exception.MatriceFormatException;
import exception.MatriceNullException;

class TestMatrice {

	@Test
	void testMultiplier() throws MatriceNullException, MatriceFormatException {

		double[][] tab = new double[][] {{1,2,3},
			{3,2,1},
			{2,3,1}};

			double[][] tab2 = new double[][] {{2,2,2},
				{1,1,1},
				{3,3,3}};

				double[][] res = new double[][] {{13,13,13},
					{11,11,11},
					{10,10,10}};

					Matrice m = new Matrice();
					double[][] multiplier = m.MultiplierMatrice(tab, tab2);
					for(int i = 0; i<multiplier.length; i++) {
						for(int j = 0; j<multiplier[0].length; j++) {
							assertEquals(multiplier[i][j], res[i][j]);
						}
					}	
	}

	@Test
	void testCreerMatrice() {
		Point[] tabp = new Point[3];
		Point p1 = new Point(10, 5, 3);
		Point p2 = new Point(15, 3, 8);
		Point p3 = new Point(35, 42, 10);
		tabp[0] = p1;
		tabp[1] = p2;
		tabp[2] = p3;

		double[][] tabres = new double[][] {{10,15,35},
											{5 ,3 ,42},
											{3 ,8 ,10},
											{1 ,1 ,1 }};

			Matrice m = new Matrice();
			double[][] creer = m.CreerMatrice(tabp);
			for(int i = 0; i<creer.length; i++) {
				for(int j = 0; j<creer[0].length; j++) {
					assertEquals(tabres[i][j], creer[i][j]);
				}
			}	
	}
}

