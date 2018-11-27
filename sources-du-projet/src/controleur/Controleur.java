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
import src.mecanique.Initialisation;
import src.modele.Modele;
import src.mouvements.Rotation;
import src.mouvements.Translation;
import src.mouvements.Zoom;
import src.vue.DessinVue;

public class Controleur {

	Modele m;
	DessinVue dv;

	public Controleur(Modele m, DessinVue dv) {

		this.dv = dv;
		this.m = m;
	}


	public void updateModele(double rotationValue, double zoomValue, int cptTranslateGD, int cptTranslateHB) {
		m.setModele(rotationValue, zoomValue, cptTranslateGD, cptTranslateHB);
	}

	/**
	 * Méthode mettant à jour la position de la figure dans l'espace à la suite d'un mouvement (rotation, translation ou homothétie).
	 * @param gc
	 * @param rotationValue
	 * @param zoomValue
	 */
	public void miseAJourVue(double rotationValue, double zoomValue, int cptTranslateGD, int cptTranslateHB) {
		if(m.getFilePly()!=null) {
			Translation translation = new Translation();
			Rotation rotation = new Rotation();
			Face[] tabf = m.getInit().getLoadFile().getFaces();
			Point[] tabp = m.getInit().getLoadFile().getPoints();
			Zoom zoom = new Zoom();

			try {
				if(dv.isFlagX()) {
					tabp = rotation.creerPointRotate(rotationValue, tabp, dv.getStratX().execute());
					dv.getStratX().setValeurRotation(rotationValue);
				}else
					tabp = rotation.creerPointRotate(dv.getStratX().getValeurRotation(), tabp, dv.getStratX().execute());
			} catch (MatriceNullException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			} catch (MatriceFormatException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			rotation.recopiePoint(tabf, tabp);

			try {
				if(dv.isFlagY()) {
					tabp = rotation.creerPointRotate(rotationValue, tabp, dv.getStratY().execute());
					dv.getStratY().setValeurRotation(rotationValue);
				}else
					tabp = rotation.creerPointRotate(dv.getStratY().getValeurRotation(), tabp, dv.getStratY().execute());
			} catch (MatriceNullException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			} catch (MatriceFormatException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			rotation.recopiePoint(tabf, tabp);

			try {
				if(dv.isFlagZ()) {
					tabp = rotation.creerPointRotate(rotationValue, tabp, dv.getStratZ().execute());
					dv.getStratZ().setValeurRotation(rotationValue);
				}else
					tabp = rotation.creerPointRotate(dv.getStratZ().getValeurRotation(), tabp, dv.getStratZ().execute());
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

			dv.getGc().clearRect(0, 0, 1280, 700);
			m.getInit().creerFigure(dv.getGc(), tabf,dv.getCouleur(), dv.getModeDessin());
		}
	}

	private void setFichier(File fileply) {
		m.setFilePly(fileply);
		if(fileply != null) {
			String extension = "";
			short i = 0;
			while (fileply.getPath().charAt(i) != '.')
				i++;
			extension = fileply.getPath().substring(i, fileply.getPath().length());	

			try {
				if (!extension.equals(".ply"))
					throw new WrongFormatFileException();
				else
					dv.getGc().clearRect(0, 0, 1600, 800);
			} catch (WrongFormatFileException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			m.setInit(null);
			try {
				m.setInit(new Initialisation(fileply));
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Erreur", "Erreur Fichier", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			dv.getGc().setLineWidth(1); //epaisseur des lignes
			m.getInit().creerFigure(dv.getGc(), m.getInit().getLoadFile().getFaces(), dv.getCouleur(), dv.getModeDessin());
			m.setCptTranslateGD(0);
			m.setCptTranslateHB(0);
		}
	}
	public void updateFichier(File fileply) {
		setFichier(fileply);
		m.setModele(m.getRotationValue(), m.getZoomValue(), m.getCptTranslateGD(), m.getCptTranslateHB());
	}
	
	public void updateCouleur(Color c) {
		dv.setCouleur(c);
		if(m.getFilePly() != null) {
			m.getInit().creerFigure(dv.getGc(), m.getInit().getLoadFile().getFaces(), dv.getCouleur(), dv.getModeDessin());
		}
	}
	
	public void updateX() {
		dv.setFlagX(true);
		dv.setFlagY(false);
		dv.setFlagZ(false);
	}
	
	public void updateY() {
		dv.setFlagX(false);
		dv.setFlagY(true);
		dv.setFlagZ(false);
	}
	
	public void updateZ() {
		dv.setFlagX(false);
		dv.setFlagY(false);
		dv.setFlagZ(true);
	}
}
