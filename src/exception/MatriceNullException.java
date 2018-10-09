package exception;

@SuppressWarnings("serial")
public class MatriceNullException extends Exception{
	
	public MatriceNullException() {
		System.out.println("Une des deux matrice est null");
	}
}
