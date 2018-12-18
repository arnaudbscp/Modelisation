package src.modele;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

import src.exception.OtherException;
import src.exception.WrongFormatFileException;

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
	
	private double rotationValue =0;
	
	/**
	 * La valeur de l'homothétie, donnée par le slider correspondant.
	 */
	
	private double zoomValue =0;
	
	/**
	 * La valeur de la translation sur l'axe horizontal. On l'incrémente lors de l'appui sur le bouton droite et on la 
	 * décrémente lors de l'appui sur le bouton gauche pour déplacer la figure horizontalement.
	 */
	
	private float cptTranslateGD=0;
	
	/**
	 * La valeur de la translation sur l'axe vertical. On l'incrémente lors de l'appui sur le bouton haut et on la 
	 * décrémente lors de l'appui sur le bouton bas pour déplacer la figure verticalement.
	 */
	
	private float cptTranslateHB=0;
	
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
	 * Définit le booléen pour le slider de rotation activé pour la Strategy X.
	 * @return
	 */
	public void setFlagX(boolean flagX) {
		this.flagX = flagX;
	}

	/**
	 * Définit le booléen pour le slider de rotation activé pour la Strategy Y.
	 * @param flagY
	 */
	public void setFlagY(boolean flagY) {
		this.flagY = flagY;
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
	 * Retourne l'Initialisation correspondant au fichier.
	 */
	public Initialisation getInit() {
		return init;
	}

	/**
	 * Définit la valeur de la translation sur l'axe horizontal et informe les Observers que le modèle a changé.
	 */
	public void setCptTranslateGD(float cptTranslateGD) {
		this.cptTranslateGD = cptTranslateGD;
		updateModele();
	}
	
	/**
	 * Définit la valeur de la translation sur l'axe vertical et informe les Observers que le modèle a changé.
	 */
	public void setCptTranslateHB(float cptTranslateHB) {
		this.cptTranslateHB = cptTranslateHB;
		updateModele();
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
	 * A completer
	 * Définit la valeur de rotation, et informe les Observers que le modèle a changé.
	 * @param rotationValue
	 */
	public void setRotationValue(double rotationValue) {
		this.rotationValue = rotationValue;
		updateModele();
	}

	/**
	 * Définit la valeur de l'homothétie, et informe les Observers que le modèle a changé.
	 * @param zoomValue
	 */
	public void setZoomValue(double zoomValue) {
		this.zoomValue = zoomValue;
		updateModele();
	}
	
	/**
	 * Méthode mettant à jour la position de la figure dans l'espace à la suite d'un mouvement (rotation, translation ou homothétie).
	 * Elle appelle les différentes méthodes de mise à jour de chaque mouvement.
	 * @param gc
	 * @param rotationValue
	 * @param zoomValue
	 */
	public Face[] miseAJourVue() {
		Face[] tabf = null;
		if(filePly != null) {
			Translation translation = new Translation();
			Rotation rotation = new Rotation();
			tabf = getInit().getLoadFile().getFaces();
			Point[] tabp = getInit().getLoadFile().getPoints();
			Zoom zoom = new Zoom();

			tabp = miseAJourRotationX(rotationValue, rotation, tabf, tabp);
			tabp = miseAJourRotationY(rotationValue, rotation, tabf, tabp);
			tabp = miseAJourRotationZ(rotationValue, rotation, tabf, tabp);
			tabp = miseAJourZoom(zoomValue, tabf, tabp, zoom);
			miseAJourTranslation(cptTranslateGD, cptTranslateHB, translation, tabf, tabp);
		}
		return tabf;
	}
	
	/**
	 * Met à jour la rotation selon l'axe X de la figure.
	 * @param rotationValue
	 * @param rotation
	 * @param tabf
	 * @param tabp
	 * @return
	 */
	private Point[] miseAJourRotationX(double rotationValue, Rotation rotation, Face[] tabf, Point[] tabp) {
		if(flagX) {
			tabp = rotation.creerPointRotate(rotationValue, tabp, stratX.execute());
			stratX.setValeurRotation(rotationValue);
		}else
			tabp = rotation.creerPointRotate(stratX.getValeurRotation(), tabp, stratX.execute());
		rotation.recopiePoint(tabf, tabp);
		return tabp;
	}

	/**
	 * Met à jour la rotation selon l'axe Y de la figure.
	 * @param rotationValue
	 * @param rotation
	 * @param tabf
	 * @param tabp
	 * @return
	 */
	private Point[] miseAJourRotationY(double rotationValue, Rotation rotation, Face[] tabf, Point[] tabp) {
		if(flagY) {
			tabp = rotation.creerPointRotate(rotationValue, tabp, stratY.execute());
			stratY.setValeurRotation(rotationValue);
		}else
			tabp = rotation.creerPointRotate(stratY.getValeurRotation(), tabp, stratY.execute());
		rotation.recopiePoint(tabf, tabp);
		return tabp;
	}
	
	/**
	 * Met à jour la rotation selon l'axe Z de la figure.
	 * @param rotationValue
	 * @param rotation
	 * @param tabf
	 * @param tabp
	 * @return
	 */
	private Point[] miseAJourRotationZ(double rotationValue, Rotation rotation, Face[] tabf, Point[] tabp) {
		if(flagZ) {
			tabp = rotation.creerPointRotate(rotationValue, tabp, stratZ.execute());
			stratZ.setValeurRotation(rotationValue);
		}else
			tabp = rotation.creerPointRotate(stratZ.getValeurRotation(), tabp, stratZ.execute());
		rotation.recopiePoint(tabf, tabp);
		return tabp;
	}
	
	/**
	 * Met à jour le niveau de zoom de la figure.
	 * @param zoomValue
	 * @param tabf
	 * @param tabp
	 * @param zoom
	 * @return
	 */
	private Point[] miseAJourZoom(double zoomValue, Face[] tabf, Point[] tabp, Zoom zoom) {
		tabp = zoom.creerPointZoom(zoomValue, tabp);
		zoom.recopiePoint(tabf, tabp);
		return tabp;
	}

	/**
	 * Met à jour le niveau de translation de la figure sur les deux axes.
	 * @param cptTranslateGD
	 * @param cptTranslateHB
	 * @param translation
	 * @param tabf
	 * @param tabp
	 */
	private void miseAJourTranslation(float cptTranslateGD, float cptTranslateHB, Translation translation, Face[] tabf, Point[] tabp) {
		tabp = translation.creerPointsTranslate(cptTranslateGD, cptTranslateHB, tabp);
		translation.recopiePoint(tabf, tabp);
	}
	
	/**
	 * Importe le fichier et effectue les calculs initiaux jusqu'au premier affichage de la figure.
	 * @param fileply
	 */
	public void setFichier(File fileply) {
		setFilePly(fileply);
		if(filePly != null) {
			String extension = "";
			short i = 0;
			while (filePly.getPath().charAt(i) != '.')
				i++;
			extension = filePly.getPath().substring(i, filePly.getPath().length());	

			try {
				if (!extension.equals(".ply"))
					throw new WrongFormatFileException();
				
				try {
					Initialisation init = Initialisation.getInstance();
					init.doInit(fileply);
					setInit(init); 
					if(getInit().isGood()) {
						cptTranslateGD = 0;
						cptTranslateHB = 0;
						setDefaultzoom(defaultZoom());
						//On simule un premier mouvement de la figure pour que tous les mouvements fonctionnent correctement.
						setZoomValue(defaultZoom);
					}
				} catch (IOException e1) {
					OtherException e2 = new OtherException();
					e2.showMessage();
				}
			} catch (WrongFormatFileException e1) {
				e1.showMessage();
			}
		}
	}
	
	/**
	 * Calcule le niveau de zoom moyen adapté à la figure par rapport à sa taille, et le retourne.
	 * @return
	 */
	private double defaultZoom() {
		double max = init.getLoadFile().getCoordMax(0);
		if(Math.abs(init.getLoadFile().getCoordMin(0)) > max)
			max = Math.abs(init.getLoadFile().getCoordMin(0));
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
	public boolean rotationAuto(boolean action) {
		while(action) {
			try {
				Thread.sleep(1000);
				setRotationValue(getRotationValue() + 5);
				if(getRotationValue() >= 360) {
					setRotationValue(1);
					action = !action;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return action;
	}

	
	private int getRotationValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void calculVecteurNormal() {
		Face[] tabFace = init.getLoadFile().getFaces();
		VecteurLumiere vl = new VecteurLumiere();
		for(int i = 0; i<tabFace.length; i++) {
			Face f = tabFace[i];
			Point S1S2 = new Point(f.getPt2().getX()-f.getPt1().getX(), f.getPt2().getY()-f.getPt1().getY(), f.getPt2().getZ()-f.getPt1().getZ());
			Point S1S3 = new Point(f.getPt3().getX()-f.getPt1().getX(), f.getPt3().getY()-f.getPt1().getY(), f.getPt3().getZ()-f.getPt1().getZ());
			Point vecteurNormal = new Point((S1S2.getY())*(S1S3.getZ())-(S1S2.getZ())*(S1S3.getY()), (S1S2.getZ())*(S1S3.getX())-(S1S2.getX())*(S1S3.getZ()), (S1S2.getX())*(S1S3.getY())-(S1S2.getY())*(S1S3.getX()));
			double ProdScalaire = (vecteurNormal.getX()*vl.getVECTEUR_LUMIEREX()) + (vecteurNormal.getY()*vl.getVECTEUR_LUMIEREY()) + (vecteurNormal.getZ()*vl.getVECTEUR_LUMIEREZ());
			double LongueurVectNormal = Math.sqrt(Math.pow(vecteurNormal.getX(),2)+ Math.pow(vecteurNormal.getY(),2)+ Math.pow(vecteurNormal.getZ(), 2));
			double Longueurvl = Math.sqrt(Math.pow(vl.getVECTEUR_LUMIEREX(), 2)+ Math.pow(vl.getVECTEUR_LUMIEREY(),2)+ Math.pow(vl.getVECTEUR_LUMIEREZ(), 2));
			double cosinus = (ProdScalaire)/(LongueurVectNormal*Longueurvl);
			
		}
	}

	/**
	 * Informe les Observers que l'état du modèle a changé.
	 */
	public void updateModele() {
		setChanged();
		notifyObservers();	
	}	
}