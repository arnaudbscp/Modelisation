package src.controleur;

import java.io.File;

import src.modele.Face;
import src.modele.Initialisation;
import src.modele.Modele;
import src.modele.Strategy;

/**
 * Classe correspondant au Controleur du design-pattern MVC, permettant la liaison entre la Vue et le Modèle, en effectuant les calculs
 * sur les données reçues de la Vue et en affectant le résultat aux attributs du Modèle.
 * @author Valentin
 *
 */
public class Controleur {

	/**
	 * Le Modèle du design-pattern MVC, contenant les différentes valeurs utiles au bon fonctionnement du logiciel.
	 */
	private Modele m;

	/**
	 * Constructeur du Controleur, spécifiant le Modèle à utiliser.
	 * @param m
	 */
	public Controleur(Modele m) {
		this.m = m;
	}

	/**
	 * Désactive le bouton X, réactive les deux autres.
	 */
	public void updateX() {
		m.setFlagX(true);
		m.setFlagY(false);
		m.setFlagZ(false);
	}

	/**
	 * Désactive le bouton Y, réactive les deux autres.
	 */
	public void updateY() {
		m.setFlagX(false);
		m.setFlagY(true);
		m.setFlagZ(false);
	}

	/**
	 * Désactive le bouton Z, réactive les deux autres.
	 */
	public void updateZ() {
		m.setFlagX(false);
		m.setFlagY(false);
		m.setFlagZ(true);
	}
	
	/**
	 * Retourne l'Initialisation du fichier.
	 * @return
	 */
	public Initialisation getInit() {
		return m.getInit();
	}

	/**
	 * Retourne la valeur de la translation sur l'axe horizontal.
	 */
	public float getCptTranslateGD() {
		return m.getCptTranslateGD();
	}

	/**
	 * Retourne la valeur de la translation sur l'axe vertical.
	 */
	public float getCptTranslateHB() {
		return m.getCptTranslateHB();
	}

	/**
	 * Retourne le niveau moyen de zoom de la figure, en fonction de la taille de celle-ci.
	 * @param d
	 */
	public double getDefaultZoom() {
		return m.getDefaultZoom();
	}

	/**
	 * Définit la valeur de la translation sur l'axe vertical.
	 */
	public void setCptTranslateHB(float f) {
		m.setCptTranslateHB(f);
	}

	/**
	 * Définit le niveau moyen de zoom de la figure, en fonction de la taille de celle-ci.
	 * @param d
	 */
	public void setDefaultZoom(double d) {
		m.setDefaultzoom(d);
	}

	/**
	 * Définit la valeur de la translation sur l'axe horizontal.
	 */
	public void setCptTranslateGD(float f) {
		m.setCptTranslateGD(f);
	}

	/**
	 * Retourne la Strategy Z.
	 * @return
	 */
	public Strategy getStrategyZ() {
		return m.getStratZ();
	}

	/**
	 * Retourne la Strategy Y.
	 * @return
	 */
	public Strategy getStrategyY() {
		return m.getStratY();
	}

	/**
	 * Retourne la Strategy X.
	 * @return
	 */
	public Strategy getStrategyX() {
		return m.getStratX();
	}
	/**
	 * Importe le fichier et effectue les calculs initiaux jusqu'au premier affichage de la figure.
	 * @param file
	 */
	public void updateFichier(File file) {
		m.setFichier(file);
	}

	/**
	 * Définit la valeur de rotation.
	 * @param value
	 */
	public void setValeurRotation(double value) {
		m.setRotationValue(value);
	}

	/**
	 * Définit la valeur de l'homothétie.
	 * @param value
	 */
	public void setZoomValue(double value) {
		m.setZoomValue(value);
	}

	/**
	 * Informe les Observers que l'état du modèle a changé.
	 */
	public void updateModele() {
		m.updateModele();
	}
	
	/**
	 * Effectue automatiquement une rotation de 360° de la figure autour de l'axe actif.
	 * @param b
	 */
	public void rotationAuto(boolean b) {
		if(b)
			m.rotationAuto(b);
	}
	
	public double calculVecteurNormal(Face face) {
		return m.calculVecteurNormal(face);
	}
}
