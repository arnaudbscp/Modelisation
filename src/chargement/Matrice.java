package chargement;

import exception.MatriceNullException;
import exception.MatriceformatException;

public class Matrice {


	public double[][] CreerMatrice(Point[] p) {
		double[][] tab = new double[4][p.length];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; i < p.length; j++) {
				if(i == 0) {
					tab[i][j] = p[j].getX();
				}else if(i == 1) {
					tab[i][j] = p[j].getY();
				}else if(i == 2) {
					tab[i][j] = p[j].getZ();
				}else if(i == 3) {
					tab[i][j] = 1;
				}
			}
		}
		return tab;
	}

	public double[][] MultiplierMatrice(double[][] MA , double[][] MB) throws MatriceNullException, MatriceformatException {
		double[][] MC;
		int l,c;

		if(MA == null || MB == null){
			throw new MatriceNullException();
		}


		if(MA[0].length != MB.length){
			throw new MatriceformatException();
		}

		if(MA.length * MA[0].length < MB.length * MB[0].length){
			l= MB.length;
			c= MB[0].length;
		}else{
			l= MA.length;
			c= MA[0].length;
		}

		MC = new double[l][c];

		l = 0;
		for (int i = 0;i < MA.length;i++){
			c = 0;
			for (int n = 0;n < MB[0].length;n++){

				int calcul= 0;
				for (int m = 0;m < MB.length;m++){
					calcul += MA[i][m] * MB[m][n];
				}

				MC[l][c] = calcul;
				c++;
			}
			l++;
		}
		return MC;
	}
}

