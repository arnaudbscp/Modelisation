package exception;

@SuppressWarnings("serial")
public class WrongLineFormatException extends Exception {
	public WrongLineFormatException() {
		System.out.println("Le format de la ligne n'est pas correct. IL doit être \"i j k\" avec i, j et k 3 nombres réels. (Ex:\"13.06 0 5.089)\n");
	}
}
