package exception;

/**
 * Exception soulevée si une matrice est NULL.
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class MatriceNullException extends Exception{
	
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
	public MatriceNullException() {
		message = "Une des deux matrices ne contient pas de données.";
		title = "Erreur Matrice NULL";
	}
	
	/**
	 * Retourne le message d'erreur.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Retourne le titre du message d'erreur.
	 * @return
	 */
	public String getTitle() {
		return title;
	}
}
