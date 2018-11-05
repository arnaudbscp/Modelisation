package src.exception;

/**
 * Exception soulevée lorsque l'en-tête du fichier n'est pas écrit dans le format attendu.
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class WrongHeaderException extends Exception {
	
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
	public WrongHeaderException() {
		message = "L'en-tête du fichier n'est pas écrit correctement... Il doit être écrit de cette manière: (i et j sont respectivement le nombre de points et le nombre de faces de la figure):\n" + 
									"ply\n" + 
									"format ascii 1.0\n" + 
									"element vertex i\n" + 
									"property float32 x\n" + 
									"property float32 y\n" + 
									"property float32 z\n" + 
									"element face j\n" + 
									"property list uint8 int32 vertex_indices\n" + 
									"end_header";
		title = "Erreur Format Entête Fichier";
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
