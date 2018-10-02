package exception;

/**
 * Exception soulevée lorsqu'une ligne représentant une face n'est pas écrite dans le format attendu.
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class WrongFaceLineFormatException extends Exception {
	
	/**
	 * Constructeur affichant le message d'erreur.
	 * @param i
	 */
	public WrongFaceLineFormatException(int i) {
		System.out.println("Le format de la ligne "+i+" n'est pas correct.\nIl doit être de la forme \"3 i j k \" avec i, j et k 3 nombres entiers représentant les indices dans le tableau de Point des points composants la face.\n(Ex: \"3 0 1 2 \")");
	}
}
