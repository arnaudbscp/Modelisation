package exception;

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
