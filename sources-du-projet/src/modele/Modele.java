package src.modele;

import java.io.File;
import java.util.Observable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe correspondant au Modèle du design-pattern MVC, stockant les différentes valeurs utiles au bon fonctionnement du logiciel,
 * mises à jour par l'intermédiaire du controleur.
 * @author Valentin
 *
 */
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
	 * La valeur de la rotation, donnée par le slider correspondant.
	 */
	
	private double rotationValue;
	
	/**
	 * La valeur de l'homothétie, donnée par le slider correspondant.
	 */
	
	private double zoomValue;
	
	/**
	 * La valeur de la translation sur l'axe horizontal. On l'incrémente lors de l'appui sur le bouton droite et on la 
	 * décrémente lors de l'appui sur le bouton gauche pour déplacer la figure horizontalement.
	 */
	
	private float cptTranslateGD;
	
	/**
	 * La valeur de la translation sur l'axe vertical. On l'incrémente lors de l'appui sur le bouton haut et on la 
	 * décrémente lors de l'appui sur le bouton bas pour déplacer la figure verticalement.
	 */
	
	private float cptTranslateHB;
	
	/**
	 * La valeur du zoom moyen par défaut, défini lors de l'importation du fichier en fonction de la taille de la figure.
	 */
	
	private double defaultZoom;
	
	/**
	 * Implémentation de cette Strategy lors de l'appui sur le Bouton X.
	 */
	
	private Strategy stratX = new StrategyRotationX(0);

	/**
	 * Implémentation de cette Strategy lors de l'appui sur le Bouton Y.
	 */
	
	private Strategy stratY = new StrategyRotationY(0);

	/**
	 * Implémentation de cette Strategy lors de l'appui sur le Bouton Z.
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
	
	/**
	 * A DOCUMENTER
	 */
	private GraphicsContext gc;
	
	/**
	 * Contient le mode selectionné pour dessiner la figure (faces + arrêtes, faces seulement ou arrêtes seulement).
	 */
	private ModeDessin modeDessin = ModeDessin.FACES_ARRETES;
	
	/**
	 * Couleur de la figure initialisée à  blanche. Elle sera modifiée grâce au colorpicker par l'utilisateur.
	 */
	private Color couleur = Color.WHITE;
	
	/**
	 * Retourne la couleur de la figure.
	 * @return
	 */
	public Color getCouleur() {
		return couleur;
	}

	/**
	 * Définit la couleur de la figure.
	 * @param couleur
	 */
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	/**
	 * Retourne le mode de dessin de la figure (faces+arrêtes, faces seulement ou arrêtes seulement).
	 * @return
	 */
	public ModeDessin getModeDessin() {
		return modeDessin;
	}

	/**
	 * Définit le mode de dessin de la figure (faces+arrêtes, faces seulement ou arrêtes seulement).
	 * @param modeDessin
	 */
	public void setModeDessin(ModeDessin modeDessin) {
		this.modeDessin = modeDessin;
	}

	/**
	 * Retourne le contexte graphique de la figure.
	 * @return
	 */
	public GraphicsContext getGc() {
		return gc;
	}

	/**
	 * Définit le contexte graphique de la figure.
	 * @param gc
	 */
	public void setGc(GraphicsContext gc) {
		this.gc = gc;
	}

	/**
	 * Retourne true si le slider de rotation est activé pour la Strategy X.
	 * @return
	 */
	public boolean isFlagX() {
		return flagX;
	}

	/**
	 * Définit le booléen pour le slider de rotation activé pour la Strategy X.
	 * @return
	 */
	public void setFlagX(boolean flagX) {
		this.flagX = flagX;
	}

	/**
	 * Retourne true si le slider de rotation est activé pour la Strategy Y.
	 * @return
	 */
	public boolean isFlagY() {
		return flagY;
	}

	/**
	 * Définit le booléen pour le slider de rotation activé pour la Strategy Y.
	 * @param flagY
	 */
	public void setFlagY(boolean flagY) {
		this.flagY = flagY;
	}

	/**
	 * Retourne true si le slider de rotation est activé pour la Strategy Z.
	 * @return
	 */
	public boolean isFlagZ() {
		return flagZ;
	}

	/**
	 * Définit le booléen pour le slider de rotation activé pour la Strategy Z.
	 * @param flagY
	 */
	public void setFlagZ(boolean flagZ) {
		this.flagZ = flagZ;
	}

	/**
	 * Retourne le niveau de zoom moyen de la figure, adapté en fonction de la taille de celle-ci.
	 */
	public double getDefaultZoom() {
		return defaultZoom;
	}
	
	/**
	 * Retourne la Strategy X.
	 */
	public Strategy getStratX() {
		return stratX;
	}

	/**
	 * Retourne la Strategy Y.
	 */
	public Strategy getStratY() {
		return stratY;
	}

	/**
	 * Retourne la Strategy Z.
	 */
	public Strategy getStratZ() {
		return stratZ;
	}

	/**
	 * Définit le niveau de zoom moyen de la figure, en fonction de la taille de celle-ci.
	 */
	public void setDefaultzoom(double defaultzoom) {
		this.defaultZoom = defaultzoom;
	}

	/**
	 * Retourne la valeur de la rotation.
	 */
	public double getRotationValue() {
		return rotationValue;
	}

	/**
	 * Retourne la valeur de l'homothétie.
	 */
	public double getZoomValue() {
		return zoomValue;
	}

	/**
	 * Retourne la valeur de la translation sur l'axe horizontal.
	 */
	public float getCptTranslateGD() {
		return cptTranslateGD;
	}

	/**
	 * Retourne la valeur de la translation sur l'axe vertical.
	 */
	public float getCptTranslateHB() {
		return cptTranslateHB;
	}
	
	/**
	 * Retourne le fichier contenant la figure.
	 */
	public File getFilePly() {
		return filePly;
	}
	
	/**
	 * Retourne l'Initialisation correspondant au fichier.
	 */
	public Initialisation getInit() {
		return init;
	}

	/**
	 * Définit la valeur de la translation sur l'axe horizontal.
	 */
	public void setCptTranslateGD(float cptTranslateGD) {
		this.cptTranslateGD = cptTranslateGD;
	}
	
	/**
	 * Définit la valeur de la translation sur l'axe vertical.
	 */
	public void setCptTranslateHB(float cptTranslateHB) {
		this.cptTranslateHB = cptTranslateHB;
	}

	/**
	 * Définit l'Initialisation du fichier.
	 */
	public void setInit(Initialisation init) {
		this.init = init;
	}
	
	/**
	 * Définit le fichier contenant la figure.
	 */
	public void setFilePly(File filePly) {
		this.filePly = filePly;
	}
	
	/**
	 * Définit les valeur de rotation, de zoom et de translation pour ensuite notifier aux Observers que les valeurs ont été modifiées
	 * afin qu'ils se mettent à jour, permettant la mise à jour de la figure.
	 */
	public void setModele(double rotationValue, double zoomValue, float cptTranslateGD, float cptTranslateHB) {
		setRotationValue(rotationValue);
		setZoomValue(zoomValue);
		setCptTranslateGD(cptTranslateGD);
		setCptTranslateHB(cptTranslateHB);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Définit la valeur de rotation.
	 * @param rotationValue
	 */
	public void setRotationValue(double rotationValue) {
		this.rotationValue = rotationValue;
	}

	/**
	 * Définit la valeur de l'homothétie.
	 * @param zoomValue
	 */
	public void setZoomValue(double zoomValue) {
		this.zoomValue = zoomValue;
	}
}
