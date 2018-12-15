package src.exception;

import javax.swing.JOptionPane;

/**
 * Exception appelée lorsqu'une erreur du système est soulevée (Erreur non personnalisée, Ex: IOException).
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class OtherException extends Exception {
	
	/**
	 * Message d'erreur.
	 */
	private String message;
	
	/**
	 * Titre du message d'erreur.
	 */
	private String title;
	
	/**
	 * Constructeur définissant le message d'erreur et sont titre.
	 */
	public OtherException() {
		message = "Une erreur a été rencontrée.";
		title = "Erreur";
	}
	
	/**
	 * Affiche le message d'erreur dans un JOptionPane.
	 */
	public void showMessage() {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
}
