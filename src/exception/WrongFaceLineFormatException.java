package exception;

/**
 * Exception soulevée lorsqu'une ligne représentant une face n'est pas écrite dans le format attendu.
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class WrongFaceLineFormatException extends Exception {
	
	/**
	 * Message d'erreur.
	 */
	private String message;
	
	/**
	 * Titre du message d'erreur.
	 */
	private String title;
	
	/**
	 * Constructeur affichant le message d'erreur.
	 * @param i
	 */
	public WrongFaceLineFormatException(int i) {
		message = "Le format de la ligne "+i+" n'est pas correct.\nIl doit être de la forme \"3 i j k \" avec i, j et k 3 nombres entiers représentant les indices dans le tableau de Point des points composants la face.\n(Ex: \"3 0 1 2 \")";
		title = "Erreur Format Ligne Face";
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
