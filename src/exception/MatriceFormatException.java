package exception;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MatriceFormatException extends Exception{
	
	public MatriceFormatException() {
		JOptionPane.showMessageDialog(null,"La multiplication de deux matrices n'est possible que si le nombre de colonnes de la première est égal au nombre de lignes de la seconde.","Erreur Matrice Format",JOptionPane.ERROR_MESSAGE);
	}
}
