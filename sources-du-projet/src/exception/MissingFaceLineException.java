package src.exception;

import javax.swing.JOptionPane;

/**
 * Exception soulevée lorsqu'il manque une ou plusieurs lignes représentant une face dans le fichier, en se fiant au nombre
 * de faces donné dans l'en-tête du fichier.
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class MissingFaceLineException extends Exception {
	
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
	 * @param expected : le nombre de ligne attendu.
	 * @param real : le nombre de ligne qu'il y a.
	 */
	public MissingFaceLineException(int expected, int real) {
		message = "Il y a une ou plusieurs lignes représentant une face manquantes dans le fichier, selon le nombre de lignes fournis dans l'en-tête.\nNombre de ligne de face attendu: " + expected + "\nNombre de ligne de face du fichier: " + real;
		title = "Erreur Nombre Ligne Face";
	}
	
	/**
	 * Affiche le message d'erreur dans un JOptionPane.
	 */
	public void showMessage() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
}
