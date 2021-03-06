package src.exception;

import javax.swing.JOptionPane;

/**
 * Exception soulevée lorsqu'il manque une ou plusieurs lignes représentant un point dans le fichier, en se fiant au nombre
 * de points donné dans l'en-tête du fichier.
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class MissingPointLineException extends Exception {

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
	 * @param expected : le nombre de ligne attendu.
	 * @param real : le nombre de ligne qu'il y a.
	 */
	public MissingPointLineException(int expected, int real) {
		message = "Il y a une ou plusieurs lignes représentant un point manquantes dans le fichier, selon le nombre de lignes fournis dans l'en-tête.\nNombre de ligne de point attendu: " + expected + "\nNombre de ligne de point du fichier: " + real;
		title = "Erreur Nombre Ligne Point";
	}

	/**
	 * Affiche le message d'erreur dans un JOptionPane.
	 */
	public void showMessage() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
}
