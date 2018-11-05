package src.exception;

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
	 * Titre du message d'erreur.
	 */
	private String title;
	
	/**
	 * Constructeur définissant le message d'erreur et son titre.
	 * @param i : le numero de la ligne où il y a une erreur.
	 */
	public WrongPointLineFormatException(int i) {
		message = "Le format de la ligne "+i+" n'est pas correct.\nIl doit être de la forme \"i j k \" avec i, j et k 3 nombres réels.\n(Ex: \"13.06 0 7.589 \")";
		title = "Erreur Format Ligne Point";
	}
	
	/**
	 * Retourne le message d'erreur.
	 * @return
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
