package src.modele;

import java.io.File;
import java.util.Observable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import src.ihm.Strategy;
import src.ihm.StrategyRotationX;
import src.ihm.StrategyRotationY;
import src.ihm.StrategyRotationZ;
import src.mecanique.Initialisation;
import src.mecanique.ModeDessin;


public class Modele extends Observable{
	
	/**
	 * le fichier .ply contenant les points et les faces à  dessiner.
	 */
	private File filePly;

	/**
	 * Interprète le LoadFile pour créer les points et les faces, trier les faces et ainsi créer la figure.
	 */
	private Initialisation init;
	
	/**
	 * A DOCUMENTER
	 */
	
	private double rotationValue;
	
	/**
	 * A DOCUMENTER
	 */
	
	private double zoomValue;
	
	/**
	 * A DOCUMENTER
	 */
	
	private float cptTranslateGD;
	
	/**
	 * A DOCUMENTER
	 */
	
	private float cptTranslateHB;
	
	/**
	 * A DOCUMENTER
	 */
	
	private double defaultzoom;
	
	/**
	 * A DOCUMENTER
	 */
	
	private Strategy stratX = new StrategyRotationX(0);

	/**
	 * A DOCUMENTER
	 */
	
	private Strategy stratY = new StrategyRotationY(0);

	/**
	 * A DOCUMENTER
	 */
	
	private Strategy stratZ = new StrategyRotationZ(0);
	
	/**
	 * Boolean true lorsque le slider de rotation X est activé (par défaut).
	 */
	
	private boolean flagX = true;

	/**
	 * Boolean true lorsque le slider de rotation Y est activé.
	 */
	private boolean flagY = false;

	/**
	 * Boolean true lorsque le slider de rotation Z est activé.
	 */
	private boolean flagZ = false;
	

	private GraphicsContext gc;
	
	/**
	 * Contient le mode selectionné pour dessiner la figure (faces + arrêtes, faces seulement ou arrêtes seulement).
	 */
	private ModeDessin modeDessin = ModeDessin.FACES_ARRETES;
	
	/**
	 * Couleur de la figure initialisée à  blanche. Elle sera modifiée grâce au colorpicker par l'utilisateur.
	 */
	private Color couleur = Color.WHITE;
	
	

	public Color getCouleur() {
		return couleur;
	}


	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}


	public ModeDessin getModeDessin() {
		return modeDessin;
	}


	public void setModeDessin(ModeDessin modeDessin) {
		this.modeDessin = modeDessin;
	}


	public GraphicsContext getGc() {
		return gc;
	}


	public void setGc(GraphicsContext gc) {
		this.gc = gc;
	}

	public boolean isFlagX() {
		return flagX;
	}

	public void setFlagX(boolean flagX) {
		this.flagX = flagX;
	}

	public boolean isFlagY() {
		return flagY;
	}

	public void setFlagY(boolean flagY) {
		this.flagY = flagY;
	}

	public boolean isFlagZ() {
		return flagZ;
	}

	public void setFlagZ(boolean flagZ) {
		this.flagZ = flagZ;
	}

	/**
	 * A DOCUMENTER
	 */
	
	public double getDefaultzoom() {
		return defaultzoom;
	}
	
	/**
	 * A DOCUMENTER
	 */
	
	public Strategy getStratX() {
		return stratX;
	}

	/**
	 * A DOCUMENTER
	 */
	
	public Strategy getStratY() {
		return stratY;
	}

	/**
	 * A DOCUMENTER
	 */
	
	public Strategy getStratZ() {
		return stratZ;
	}

	/**
	 * A DOCUMENTER
	 */
	
	public void setDefaultzoom(double defaultzoom) {
		this.defaultzoom = defaultzoom;
	}

	/**
	 * A DOCUMENTER
	 */
	
	public double getRotationValue() {
		return rotationValue;
	}

	/**
	 * A DOCUMENTER
	 */
	
	public double getZoomValue() {
		return zoomValue;
	}

	/**
	 * A DOCUMENTER
	 */
	
	public float getCptTranslateGD() {
		return cptTranslateGD;
	}

	/**
	 * A DOCUMENTER
	 */
	
	public float getCptTranslateHB() {
		return cptTranslateHB;
	}
	
	/**
	 * A DOCUMENTER
	 */
	
	public File getFilePly() {
		return filePly;
	}
	
	/**
	 * A DOCUMENTER
	 */
	
	public Initialisation getInit() {
		return init;
	}

	/**
	 * A DOCUMENTER
	 */
	
	public void setCptTranslateGD(float cptTranslateGD) {
		this.cptTranslateGD = cptTranslateGD;
	}
	
	/**
	 * A DOCUMENTER
	 */
	
	public void setCptTranslateHB(float cptTranslateHB) {
		this.cptTranslateHB = cptTranslateHB;
	}

	/**
	 * A DOCUMENTER
	 */
	
	public void setInit(Initialisation init) {
		this.init = init;
	}
	
	/**
	 * A DOCUMENTER
	 */
	
	public void setFilePly(File filePly) {
		this.filePly = filePly;
	}
	
	/**
	 * A DOCUMENTER
	 */
	
	public void setModele(double rotationValue, double zoomValue, float cptTranslateGD, float cptTranslateHB) {
		this.rotationValue = rotationValue;
		this.zoomValue = zoomValue;
		this.cptTranslateGD = cptTranslateGD;
		this.cptTranslateHB = cptTranslateHB;
		setChanged();
		notifyObservers();
	}


	public void setRotationValue(double rotationValue) {
		this.rotationValue = rotationValue;
	}


	public void setZoomValue(double zoomValue) {
		this.zoomValue = zoomValue;
	}
}
