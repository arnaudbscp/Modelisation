package exception;

/**
 * Exception soulevée lorsqu'il y a plus de lignes représentant une face dans le fichier que prévu, en se fiant au nombre
 * de face donné dans l'en-tête du fichier.
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class TooMuchFaceLineException extends Exception {
	
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
	 * @param i
	 */
	public TooMuchFaceLineException() {
		message = "Il y a plus de lignes représentant une face dans le fichier qu'il ne devait y en avoir selon le nombre de lignes fournis dans l'en-tête.";
		title = "Erreur Nombre Ligne Face";
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