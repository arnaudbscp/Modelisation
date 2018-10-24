package exception;

/**
 * Exception soulevée lorsqu'aucun fichier n'est selectionné dans le fileChooser, c'est-à-dire lorsque l'utilisateur appuie sur le bouton importer.
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class NoFileSelectedException extends Exception {
	
	/**
	 * Message d'erreur.
	 */
	private String message;
	
	/**
	 * Constructeur affichant le message d'erreur.
	 * @param i
	 */
	public NoFileSelectedException() {
		message="Aucun fichier selectionné.";
	}
	
	/**
	 * Retourne le message d'erreur.
	 */
	public String toString() {
		return message;
	}
}
