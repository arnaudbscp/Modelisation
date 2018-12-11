package src.controleur;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import src.donnees.Face;
import src.donnees.Point;
import src.exception.MatriceFormatException;
import src.exception.MatriceNullException;
import src.exception.WrongFormatFileException;
import src.modele.Initialisation;
import src.modele.ModeDessin;
import src.modele.Modele;
import src.modele.Strategy;
import src.mouvements.Rotation;
import src.mouvements.Translation;
import src.mouvements.Zoom;

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
	 * Méthode mettant à jour la position de la figure dans l'espace à la suite d'un mouvement (rotation, translation ou homothétie).
	 * @param gc
	 * @param rotationValue
	 * @param zoomValue
	 */
	public void miseAJourVue(double rotationValue, double zoomValue, float cptTranslateGD, float cptTranslateHB) {
		if(m.getFilePly() != null) {
			Translation translation = new Translation();
			Rotation rotation = new Rotation();
			Face[] tabf = getInit().getLoadFile().getFaces();
			Point[] tabp = getInit().getLoadFile().getPoints();
			Zoom zoom = new Zoom();

			try {
				if(m.isFlagX()) {
					tabp = rotation.creerPointRotate(rotationValue, tabp, getStrategyX().execute());
					getStrategyX().setValeurRotation(rotationValue);
				}else
					tabp = rotation.creerPointRotate(getStrategyX().getValeurRotation(), tabp, getStrategyX().execute());
			} catch (MatriceNullException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			} catch (MatriceFormatException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			rotation.recopiePoint(tabf, tabp);

			try {
				if(m.isFlagY()) {
					tabp = rotation.creerPointRotate(rotationValue, tabp, getStrategyY().execute());
					getStrategyY().setValeurRotation(rotationValue);
				}else
					tabp = rotation.creerPointRotate(getStrategyY().getValeurRotation(), tabp, getStrategyY().execute());
			} catch (MatriceNullException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			} catch (MatriceFormatException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			rotation.recopiePoint(tabf, tabp);

			try {
				if(m.isFlagZ()) {
					tabp = rotation.creerPointRotate(rotationValue, tabp, getStrategyZ().execute());
					getStrategyZ().setValeurRotation(rotationValue);
				}else
					tabp = rotation.creerPointRotate(getStrategyZ().getValeurRotation(), tabp, getStrategyZ().execute());
			} catch (MatriceNullException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			} catch (MatriceFormatException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			rotation.recopiePoint(tabf, tabp);

			try {
				tabp = zoom.creerPointZoom(zoomValue, tabp);
			} catch (MatriceNullException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			} catch (MatriceFormatException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			zoom.recopiePoint(tabf, tabp);

			try {
				tabp = translation.creerPointsTranslate(cptTranslateGD, cptTranslateHB, tabp);
			} catch (MatriceNullException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			} catch (MatriceFormatException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			translation.recopiePoint(tabf, tabp);
			
			getGc().clearRect(0, 0, 1280, 800);
			getInit().creerFigure(getGc(), tabf, getCouleur(), getModeDessin());
		}
	}

	/**
	 * Importe le fichier et effectue les calculs initiaux jusqu'au premier affichage de la figure.
	 * @param fileply
	 */
	private void setFichier(File fileply) {
		m.setFilePly(fileply);
		if(m.getFilePly() != null) {
			String extension = "";
			short i = 0;
			while (m.getFilePly().getPath().charAt(i) != '.')
				i++;
			extension = m.getFilePly().getPath().substring(i, m.getFilePly().getPath().length());	

			try {
				if (!extension.equals(".ply"))
					throw new WrongFormatFileException();
				else
					getGc().clearRect(0, 0, 1600, 800);
				try {
					Initialisation init = Initialisation.getInstance();
					init.doInit(fileply);
					m.setInit(init);
					if(getInit().isGood()) {
						getGc().setLineWidth(1); //epaisseur des lignes
						setCptTranslateGD(0);
						setCptTranslateHB(0);
						setDefaultZoom(defaultZoom());
						//On simule un premier mouvement de la figure pour que tous les mouvements fonctionnent correctement.
						updateModele(m.getRotationValue(), m.getZoomValue(), getCptTranslateGD(), getCptTranslateHB());
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Erreur", "Erreur Fichier", JOptionPane.ERROR_MESSAGE);
				}
			} catch (WrongFormatFileException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Met à jour le fichier utilisé.
	 * @param fileply
	 */
	public void updateFichier(File fileply) {
		setFichier(fileply);
	}
	
	/**
	 * Initialise les valeurs du modèle lors de l'import d'une figure, pour pouvoir afficher la figure dès l'import et non après
	 * un premier mouvement.
	 * @param r
	 * @param z
	 */
	public void initModele(double rotation, double zoom) {
		m.setModele(rotation, zoom, 0, 0);
	}
	
	/**
	 * Met à jour la couleur de la figure.
	 * @param c
	 */
	public void updateCouleur(Color c) {
		m.setCouleur(c);
		if(m.getFilePly() != null) {
			getInit().creerFigure(getGc(), getInit().getLoadFile().getFaces(), getCouleur(), getModeDessin());
		}
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
	 * Calcule le niveau de zoom moyen adapté à la figure par rapport à sa taille, et le retourne.
	 * @return
	 */
	private double defaultZoom() {
		double max = getInit().getLoadFile().getCoordMax(0);
		if(Math.abs(getInit().getLoadFile().getCoordMin(0)) > max)
			max = Math.abs(getInit().getLoadFile().getCoordMin(0));
		if(max > 500) return 0.5;
		else if(max > 300) return 1;
		else if(max > 200) return 1.5;
		else if(max > 100) return 2.5;
		else if(max > 75) return 5;
		else if(max > 50) return 7.5;
		else if(max > 25) return 10;
		else if (max > 10) return 12.5;
		else if(max > 5) return 15;
		else if(max > 1) return 20;
		return 30;
	}
	
	/**
	 * Effectue automatiquement une rotation de 360° de la figure autour de l'axe actif.
	 * @param action
	 */
	public void rotationAuto(boolean action) {
		while(action) {
			m.setRotationValue(m.getRotationValue() + 1);
			updateModele(m.getRotationValue(), m.getZoomValue(), m.getCptTranslateGD(), m.getCptTranslateHB());
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Définit les nouvelles valeurs de rotation, de zoom et de translation du modèle.
	 * @param rotationValue
	 * @param zoomValue
	 * @param cptTranslateGD
	 * @param cptTranslateHB
	 */
	public void updateModele(double rotationValue, double zoomValue, float cptTranslateGD, float cptTranslateHB) {
		m.setModele(rotationValue, zoomValue, cptTranslateGD, cptTranslateHB);
	}

	/**
	 * Retourne la couleur de la figure.
	 * @return
	 */
	public Color getCouleur() {
		return m.getCouleur();
	}
	
	/**
	 * Retourne l'Initialisation du fichier.
	 * @return
	 */
	public Initialisation getInit() {
		return m.getInit();
	}

	/**
	 * Définit le contexte graphique de la figure.
	 * @param gc
	 */
	public void setGc(GraphicsContext gc) {
		m.setGc(gc);
	}
	
	/**
	 * Retourne le contexte graphique de la figure.
	 * @return
	 */
	private GraphicsContext getGc() {
		return m.getGc();
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
	 * Définit le mode de dessin de la figure (faces+arrêtes, faces seulement ou arrêtes seulement).
	 * @param modeDessin
	 */
	public void setModeDessin(ModeDessin modeDessin) {
		m.setModeDessin(modeDessin);		
	}

	/**
	 * Retourne le mode de dessin de la figure (faces+arrêtes, faces seulement ou arrêtes seulement).
	 * @return
	 */
	private ModeDessin getModeDessin() {
		return m.getModeDessin();
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
}
