package src.exception;

import javax.swing.JOptionPane;

/**
 * Exception soulevée lorsqu'une ligne représentant un point n'est pas écrite dans le format attendu.
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class WrongPointLineFormatException extends Exception {
	
	/**
	 * Message d'erreur.
	 */
	private String message;
	
	/**
	 * Titre du message d'erreur.
	 */
	private String title;
	
	/**
	 * Constructeur définissant le message d'erreur et son titre.
	 * @param i : le numero de la ligne où il y a une erreur.
	 */
	public WrongPointLineFormatException(int i) {
		message = "Le format de la ligne "+i+" n'est pas correct.\nIl doit être de la forme \"i j k \" avec i, j et k 3 nombres réels.\n(Ex: \"13.06 0 7.589 \")";
		title = "Erreur Format Ligne Point";
	}
	
	/**
	 * Affiche le message d'erreur dans un JOptionPane.
	 */
	public void showMessage() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
}
