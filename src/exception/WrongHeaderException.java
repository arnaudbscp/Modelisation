package exception;

/**
 * Exception soulevée lorsque l'en-tête du fichier n'est pas écrit dans le format attendu.
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class WrongHeaderException extends Exception {
	
	/**
	 * Constructeur affichant le message d'erreur.
	 */
	public WrongHeaderException() {
		System.out.println("L'en-tête du fichier n'est pas écrit correctement... Il doit être sous cette forme: (i et j sont des entiers représentant respectivement le nombre de points et le nombre de faces de la figure)\n" +
				"ply\n" + 
				"format ascii 1.0\n" + 
				"element vertex i\n" + 
				"property float32 x\n" + 
				"property float32 y\n" + 
				"property float32 z\n" + 
				"element face j\n" + 
				"property list uint8 int32 vertex_indices\n" + 
				"end_header");
	}
}
