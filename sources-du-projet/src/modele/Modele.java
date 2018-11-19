package src.modele;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

import javax.swing.JOptionPane;

import src.mecanique.Initialisation;


public class Modele extends Observable{
	
	/**
	 * le fichier .ply contenant les points et les faces à  dessiner.
	 */
	private File filePly;

	/**
	 * Interprète le LoadFile pour créer les points et les faces, trier les faces et ainsi créer la figure.
	 */
	private Initialisation init;

	public Modele(File filePly) {
		this.filePly = filePly;
		init = null;
		try {
			init = new Initialisation(filePly);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Erreur", "Erreur Fichier", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	public Initialisation getInit() {
		return init;
	}
}
