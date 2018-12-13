package src.exception;

import javax.swing.JOptionPane;

/**
 * Exception soulevée lorsqu'une ligne représentant une face n'est pas écrite dans le format attendu.
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class WrongFaceLineFormatException extends Exception {
	
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
	public WrongFaceLineFormatException(int i) {
		message = "Le format de la ligne "+i+" n'est pas correct.\nIl doit être de la forme \"3 i j k \" avec i, j et k 3 nombres entiers représentant les indices dans le tableau de Point des points composants la face.\n(Ex: \"3 0 1 2 \")";
		title = "Erreur Format Ligne Face";
	}

	/**
	 * Affiche le message d'erreur dans un JOptionPane.
	 */
	public void showMessage() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
}
