package src.exception;

import javax.swing.JOptionPane;

/**
 * Exception soulevée lorsque le format du fichier n'est pas correct, c'est-à-dire que le fichier n'est pas un .ply
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class WrongFormatFileException extends Exception {
	
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
	 */
	public WrongFormatFileException() {
		message = "Le format du fichier est incorrect. Vous devez choisir un fichier .ply";
		title = "Erreur Format Fichier";
	}
	
	/**
	 * Affiche le message d'erreur dans un JOptionPane.
	 */
	public void showMessage() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
}
