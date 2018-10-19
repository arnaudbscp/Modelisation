package exception;

/**
 * Exception soulevée lorsqu'une ligne représentant un point n'est pas écrite dans le format attendu.
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class WrongPointLineFormatException extends Exception {
	
	/**
	 * Message d'erreur.
	 */
	private String message;
	
	/**
	 * Constructeur affichant le message d'erreur.
	 * @param i
	 */
	public WrongPointLineFormatException(int i) {
		message="Le format de la ligne "+i+" n'est pas correct.\nIl doit être de la forme \"i j k \" avec i, j et k 3 nombres réels.\n(Ex: \"13.06 0 7.589 \")";
	}
	
	/**
	 * Retourne le message d'erreur.
	 */
	public String toString() {
		return message;
	}
}
