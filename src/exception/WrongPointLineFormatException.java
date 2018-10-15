package exception;

import javax.swing.JOptionPane;

/**
 * Exception soulevée lorsqu'une ligne représentant un point n'est pas écrite dans le format attendu.
 * @author Valentin
 *
 */
@SuppressWarnings("serial")
public class WrongPointLineFormatException extends Exception {
	
	/**
	 * Constructeur affichant le message d'erreur.
	 * @param i
	 */
	public WrongPointLineFormatException(int i) {
		JOptionPane.showMessageDialog(null,"Le format de la ligne "+i+" n'est pas correct.\nIl doit être de la forme \"i j k \" avec i, j et k 3 nombres réels.\n(Ex: \"13.06 0 7.589 \")","Erreur format ligne",JOptionPane.ERROR_MESSAGE);
	}
}
