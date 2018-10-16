package exception;

/**
 * Exception soulevée lorsque l'en-tête du fichier n'est pas écrit dans le format attendu.
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class WrongHeaderException extends Exception {
	
	/**
	 * Constructeur.
	 */
	public WrongHeaderException() {
	}
}
