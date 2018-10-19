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
	 * Constructeur affichant le message d'erreur.
	 */
	public WrongFormatFileException() {
		message="Le format du fichier est incorrect. Vous devez choisir un fichier .ply";
	}
	
	/**
	 * Retourne le message d'erreur.
	 */
	public String toString() {
		return message;
	}
}
