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
import src.ihm.Strategy;
import src.mecanique.Initialisation;
import src.mecanique.ModeDessin;
import src.modele.Modele;
import src.mouvements.Rotation;
import src.mouvements.Translation;
import src.mouvements.Zoom;

public class Controleur {

	Modele m;

	public Controleur(Modele m) {

		this.m = m;
	}


	public void updateModele(double rotationValue, double zoomValue, float cptTranslateGD, float cptTranslateHB) {
		m.setModele(rotationValue, zoomValue, cptTranslateGD, cptTranslateHB);
	}
	
	public Strategy getStrategyX() {
		return m.getStratX();
	}
	
	public Strategy getStrategyY() {
		return m.getStratY();
	}
	
	public Strategy getStrategyZ() {
		return m.getStratZ();
	}
	
	public float getcptTranslateGD() {
		return m.getCptTranslateGD();
	}
	
	public float getcptTranslateHB() {
		return m.getCptTranslateGD();
	}
	
	public void setcptTranslateGD(float i) {
		m.setCptTranslateGD(i);
	}
	
	public void setcptTranslateHB(float i) {
		m.setCptTranslateGD(i);
	}
	
	public double getDefaultzoom() {
		return m.getDefaultzoom();
	}
	
	public void setDefaultzoom(double valeurzoom) {
		m.setDefaultzoom(valeurzoom);
	}
	
	public void setGc(GraphicsContext gc) {
		m.setGc(gc);
	}
	
	public void setModeDessin(ModeDessin  modeDessin) {
		m.setModeDessin(modeDessin);
	}
	
	public Color getCouleur() {
		return m.getCouleur();
	}

	/**
	 * Méthode mettant à jour la position de la figure dans l'espace à la suite d'un mouvement (rotation, translation ou homothétie).
	 * @param gc
	 * @param rotationValue
	 * @param zoomValue
	 */
	public void miseAJourVue(double rotationValue, double zoomValue, float cptTranslateGD, float cptTranslateHB) {
		if(m.getFilePly()!=null) {
			Translation translation = new Translation();
			Rotation rotation = new Rotation();
			Face[] tabf = m.getInit().getLoadFile().getFaces();
			Point[] tabp = m.getInit().getLoadFile().getPoints();
			Zoom zoom = new Zoom();

			try {
				if(m.isFlagX()) {
					tabp = rotation.creerPointRotate(rotationValue, tabp, m.getStratX().execute());
					m.getStratX().setValeurRotation(rotationValue);
				}else
					tabp = rotation.creerPointRotate(m.getStratX().getValeurRotation(), tabp, m.getStratX().execute());
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
					tabp = rotation.creerPointRotate(rotationValue, tabp, m.getStratY().execute());
					m.getStratY().setValeurRotation(rotationValue);
				}else
					tabp = rotation.creerPointRotate(m.getStratY().getValeurRotation(), tabp, m.getStratY().execute());
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
					tabp = rotation.creerPointRotate(rotationValue, tabp, m.getStratZ().execute());
					m.getStratZ().setValeurRotation(rotationValue);
				}else
					tabp = rotation.creerPointRotate(m.getStratZ().getValeurRotation(), tabp, m.getStratZ().execute());
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

			m.getGc().clearRect(0, 0, 1280, 700);
			m.getInit().creerFigure(m.getGc(), tabf,m.getCouleur(), m.getModeDessin());
		}
	}

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
					m.getGc().clearRect(0, 0, 1600, 800);
				m.setInit(null);
				try {
					m.setInit(new Initialisation(fileply));
					if(m.getInit().isGood()) {
						m.getGc().setLineWidth(1); //epaisseur des lignes
						m.getInit().creerFigure(m.getGc(), m.getInit().getLoadFile().getFaces(), m.getCouleur(), m.getModeDessin());
						m.setCptTranslateGD(0);
						m.setCptTranslateHB(0);
						m.setDefaultzoom(defaultZoom());
						//On simule un premier mouvement de la figure pour que tous les mouvements fonctionnent correctement.
						updateModele(m.getRotationValue(), m.getZoomValue(), m.getCptTranslateGD(), m.getCptTranslateHB());
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Erreur", "Erreur Fichier", JOptionPane.ERROR_MESSAGE);
				}
			} catch (WrongFormatFileException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	public void updateFichier(File fileply) {
		setFichier(fileply);
		m.setModele(m.getRotationValue(), m.getZoomValue(), m.getCptTranslateGD(), m.getCptTranslateHB());
	}
	
	public void updateCouleur(Color c) {
		m.setCouleur(c);
		if(m.getFilePly() != null) {
			m.getInit().creerFigure(m.getGc(), m.getInit().getLoadFile().getFaces(), m.getCouleur(), m.getModeDessin());
		}
	}
	
	public void updateX() {
		m.setFlagX(true);
		m.setFlagY(false);
		m.setFlagZ(false);
	}
	
	public void updateY() {
		m.setFlagX(false);
		m.setFlagY(true);
		m.setFlagZ(false);
	}
	
	public void updateZ() {
		m.setFlagX(false);
		m.setFlagY(false);
		m.setFlagZ(true);
	}
	
	private double defaultZoom() {
		double max = m.getInit().getLoadFile().getCoordMax(0);
		if(Math.abs(m.getInit().getLoadFile().getCoordMin(0)) > max)
			max = Math.abs(m.getInit().getLoadFile().getCoordMin(0));
		if(max > 500)
			return 0.5;
		else if(max > 300)
			return 1;
		else if(max > 200)
			return 1.5;
		else if(max > 100)
			return 2.5;
		else if(max > 75)
			return 5;
		else if(max > 50)
			return 7.5;
		else if(max > 25)
			return 10;
		else if (max > 10)
			return 12.5;
		else if(max > 5)
			return 15;
		else if(max > 1)
			return 20;
		return 30;
	}
}
