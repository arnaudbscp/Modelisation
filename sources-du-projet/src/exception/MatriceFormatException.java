package src.exception;

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
	 * Titre du message d'erreur.
	 */
	private String title;
	
	/**
	 * Constructeur définissant le message d'erreur et son titre.
	 */
	public MatriceFormatException() {
		message = "La multiplication de deux matrices n'est possible que si le nombre de colonnes de la première est égal au nombre de lignes de la seconde.";
		title = "Erreur Produit Matriciel";
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
