package exception;

@SuppressWarnings("serial")
public class MatriceformatException extends Exception{
	
	public MatriceformatException() {
		System.out.println("La multiplication de deux matrices n'est possible que si le nombre de colonne de la première est égal au nombre de ligne de la seconde");
	}
}
