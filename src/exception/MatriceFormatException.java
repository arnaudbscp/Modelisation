package exception;

/**
 * Exception soulevée lorsque l'on cherche à multiplier deux matrices qui ne peuvent être multipliées à cause de 
 * leurs formats incompatibles, c'est-à-dire que le nombre de colonnes de l'une n'est pas égal au nombre de 
 * lignes de l'autre.
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class MatriceFormatException extends Exception{
	
	/**
	 * Message d'erreur.
	 */
	private String message;
	
	/**
	 * Constructeur créant le message.
	 */
	public MatriceFormatException() {
		message="La multiplication de deux matrices n'est possible que si le nombre de colonnes de la première est égal au nombre de lignes de la seconde.";
	}
	
	/**
	 * Retourne le message d'erreur.
	 */
	public String toString() {
		return message;
	}
}
