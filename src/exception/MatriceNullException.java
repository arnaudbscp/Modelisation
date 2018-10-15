package exception;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MatriceNullException extends Exception{
	
	public MatriceNullException() {
		JOptionPane.showMessageDialog(null,"Une des deux matrices est NULL.","Matrice NULL",JOptionPane.ERROR_MESSAGE);
	}
}
