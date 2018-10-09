package exception;

@SuppressWarnings("serial")
public class MatriceFormatException extends Exception{
	
	public MatriceFormatException() {
		System.out.println("La multiplication de deux matrices n'est possible que si le nombre de colonnes de la première est égal au nombre de lignes de la seconde.");
	}
}
