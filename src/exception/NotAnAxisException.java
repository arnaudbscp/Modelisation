package exception;

@SuppressWarnings("serial")
public class NotAnAxisException extends Exception {
	public NotAnAxisException() {
		System.out.println("Aïe... Il y a une erreur dans l'identification de l'axe sur lequel effectuer le tri. L'axe doit être soit 0 pour x, soit 1 pour y, soit 2 pour z.");
	}
}
