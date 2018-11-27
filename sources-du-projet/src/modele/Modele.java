package src.modele;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

import javax.swing.JOptionPane;

import javafx.scene.canvas.GraphicsContext;
import src.exception.WrongFormatFileException;
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
	
	private double rotationValue;
	
	private double zoomValue;
	
	private int cptTranslateGD;
	
	private int cptTranslateHB;

	public double getRotationValue() {
		return rotationValue;
	}

	public double getZoomValue() {
		return zoomValue;
	}

	public int getCptTranslateGD() {
		return cptTranslateGD;
	}

	public int getCptTranslateHB() {
		return cptTranslateHB;
	}

	public File getFilePly() {
		return filePly;
	}
	
	public Initialisation getInit() {
		return init;
	}

	public void setCptTranslateGD(int cptTranslateGD) {
		this.cptTranslateGD = cptTranslateGD;
	}

	public void setCptTranslateHB(int cptTranslateHB) {
		this.cptTranslateHB = cptTranslateHB;
	}

	public void setInit(Initialisation init) {
		this.init = init;
	}
	
	public void setFilePly(File filePly) {
		this.filePly = filePly;
	}

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
	public void setModele(double rotationValue, double zoomValue, int cptTranslateGD, int cptTranslateHB) {
		this.rotationValue = rotationValue;
		this.zoomValue = zoomValue;
		this.cptTranslateGD = cptTranslateGD;
		this.cptTranslateHB = cptTranslateHB;
		setChanged();
		notifyObservers();
	}
}
