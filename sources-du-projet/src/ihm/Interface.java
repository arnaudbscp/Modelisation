package src.ihm;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import src.donnees.Face;
import src.donnees.Point;

import src.mecanique.Initialisation;

import src.mouvements.Rotation;
import src.mouvements.Translation;
import src.mouvements.Zoom;

import src.exception.MatriceFormatException;
import src.exception.MatriceNullException;
import src.exception.WrongFormatFileException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Classe représentant la fenêtre graphique et tous les éléments graphiques contenus dedans.
 * @author bascopa & clarissa
 */
public class Interface extends Application {

	/**
	 * le fichier .ply contenant les points et les faces à  dessiner.
	 */
	private File filePly;

	/**
	 * Interprète le LoadFile pour créer les points et les faces, trier les faces et ainsi créer la figure.
	 */
	private Initialisation init;

	/**
	 * Compteur de translation gauche-droite. On l'incrémente lors de l'appui sur le bouton droite et on le 
	 * décrémente lors de l'appui sur le bouton gauche pour déplacer la figure horizontalement.
	 */
	private int cptTranslateGD = 0;

	/**
	 * Compteur de translation haut-bas. On l'incrémente lors de l'appui sur le bouton haut et on le décrémente lors de 
	 * l'appui sur le bouton bas pour déplacer la figure verticalement.
	 */
	private int cptTranslateHB = 0;

	/**
	 * Couleur de la figure initialisée à  blanche. Elle sera modifiée grâce au colorpicker par l'utilisateur.
	 */
	private Color couleur = Color.WHITE;

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
	 * Représente le niveau de zoom moyen adapté à la figure par rapport à sa taille.
	 */
	private double defaultZoom;

	/**
	 * Méthode d'affichage de l'interface graphique.
	 */
	public void start(Stage primaryStage){
		//ELEMENTS GRAPHIQUES
		HBox corps = new HBox();
		Scene scene = new Scene(corps, 1280, 600);
		VBox menu = new VBox();
		menu.setMinWidth(150);
		Button b1 = new Button("Importer");
		b1.setMinWidth(150);
		menu.getChildren().add(b1);
		Canvas canv = new Canvas(1100, 600);
		GraphicsContext gc = canv.getGraphicsContext2D();
		HBox.setMargin(menu, new Insets(50, 0, 0, 20));
		FileChooser importer = new FileChooser();
		VBox dessin = new VBox();
		Separator sep = new Separator(Orientation.VERTICAL);
		sep.setPrefSize(1, 800);
		dessin.getChildren().add(sep);
		corps.getChildren().add(menu);
		corps.getChildren().add(dessin);
		sep.setValignment(VPos.CENTER);
		sep.setMinHeight(300);
		sep.setStyle("-fx-padding : 0 0 0 30;");
		corps.getChildren().add(canv);

		//SLIDER ZOOM
		Slider zoom = new Slider();
		Label lblZoom = new Label();
		lblZoom.setText("Zoomer");
		lblZoom.setStyle("-fx-padding : 20 0 0 50;");
		zoom.setDisable(true);
		menu.getChildren().addAll(lblZoom, zoom);

		//SLIDER ROTATION X (par défaut)
		Slider sliderRotation = new Slider();
		Label lblTournerX = new Label();
		lblTournerX.setText("Tourner X");
		lblTournerX.setPadding(new Insets(50,1,1,50));
		sliderRotation.setMin(0);
		sliderRotation.setMax(360);
		sliderRotation.setMajorTickUnit(90);
		sliderRotation.setValue(0);
		sliderRotation.setShowTickLabels(true);
		menu.getChildren().addAll(lblTournerX, sliderRotation);

		//BOUTON DIRECTIONNEL ROTATION
		Button X = new Button();
		X.setText("X");
		X.setDisable(true);
		Button Y = new Button();
		Y.setText("Y");
		Button Z = new Button();
		Z.setText("Z");
		HBox alignementButton = new HBox();
		alignementButton.getChildren().add(X);
		alignementButton.getChildren().add(Y);
		alignementButton.getChildren().add(Z);
		alignementButton.setPadding(new Insets(25,1,1,10));
		alignementButton.setSpacing(30);
		menu.getChildren().addAll(alignementButton);

		//CROIX DIRECTIONNELLE TRANSLATION
		Label lblTranslation = new Label("Translation");
		lblTranslation.setPadding(new Insets(30,1,1,45));
		HBox hbHaut = new HBox();
		HBox hbGaucheDroite = new HBox();
		HBox hbBas = new HBox();
		Button haut = new Button("H");
		Button gauche = new Button("G");
		Button droite = new Button("D");
		Button bas = new Button("B");
		hbHaut.setPadding(new Insets(10, 0, 0, 60));
		hbBas.setPadding(new Insets(0, 0, 0,60));
		hbGaucheDroite.setPadding(new Insets(0, 0, 0,33));
		hbGaucheDroite.setSpacing(28);
		haut.setPrefWidth(25);
		bas.setPrefWidth(25);
		droite.setPrefWidth(25);
		gauche.setPrefWidth(25);
		hbHaut.getChildren().add(haut);
		hbGaucheDroite.getChildren().addAll(gauche, droite);
		hbBas.getChildren().add(bas);
		menu.getChildren().addAll(lblTranslation, hbHaut, hbGaucheDroite, hbBas);

		//CHOIX COULEURS
		Label lcolor = new Label("Couleur");
		lcolor.setPadding(new Insets(30, 0, 10,50));
		ColorPicker cp = new ColorPicker();
		cp.setValue(couleur);
		menu.getChildren().addAll(lcolor,cp);
		

		//---------------------GESTION DES EVENEMENTS------------------


		importer.setTitle("Selectionner un fichier 3D");
		b1.setOnAction(e -> {
			filePly = importer.showOpenDialog(primaryStage);
			if(filePly != null) {
				String extension = "";
				int i = 0;
				while (filePly.getPath().charAt(i) != '.')
					i++;
				extension = filePly.getPath().substring(i, filePly.getPath().length());	

				try {
					if (!extension.equals(".ply"))
						throw new WrongFormatFileException();
					else
						gc.clearRect(0, 0, 1600, 800);
				} catch (WrongFormatFileException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
				init = null;
				try {
					init = new Initialisation(filePly);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Erreur", "Erreur Fichier", JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
				gc.setLineWidth(1); //epaisseur des lignes
				init.creerFigure(gc, init.getLoadFile().getFaces(), couleur);
				cptTranslateGD = 0;
				cptTranslateHB = 0;
				defaultZoom = defaultZoom();
				zoom.setDisable(false);
				zoom.setMin(0);
				zoom.setMax(defaultZoom*2);
				zoom.setValue(defaultZoom);
				zoom.setMajorTickUnit(defaultZoom/2.5);
				zoom.setBlockIncrement(defaultZoom/12.5);
				zoom.setShowTickLabels(true);
				//On simule un premier mouvement de la figure pour que tous les mouvements fonctionnent correctement.
				miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
			}
		});

		cp.setOnAction(e ->{
			couleur = cp.getValue();
			if(filePly != null)
				init.creerFigure(gc, init.getLoadFile().getFaces(), couleur);
		});

		X.setOnAction(e -> {
			X.setDisable(true);
			Y.setDisable(false);
			Z.setDisable(false);
			lblTournerX.setText("Tourner X");
			sliderRotation.setValue(stratX.getValeurRotation());
			flagX = true;
			flagY = false;
			flagZ = false;
		});

		Y.setOnAction(e -> {
			Y.setDisable(true);
			X.setDisable(false);
			Z.setDisable(false);
			lblTournerX.setText("Tourner Y");
			sliderRotation.setValue(stratY.getValeurRotation());
			flagY = true;
			flagX = false;
			flagZ = false;
		});

		Z.setOnAction(e -> {
			Z.setDisable(true);
			Y.setDisable(false);
			X.setDisable(false);
			lblTournerX.setText("Tourner Z");
			sliderRotation.setValue(stratZ.getValeurRotation());
			flagZ = true;
			flagX = false;
			flagY = false;
		});

		sliderRotation.setOnMouseDragged(e-> {
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		sliderRotation.setOnMouseClicked(e-> {
			miseAJourVue(gc, sliderRotation.getValue(),  zoom.getValue());
		});

		zoom.setOnMouseDragged(e -> {
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		zoom.setOnMouseClicked(e -> {
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		gauche.setOnAction(e->{
			cptTranslateGD += 10;
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		gauche.setOnMouseDragged(e->{
			cptTranslateGD += 10;
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		droite.setOnAction(e->{
			cptTranslateGD -= 10;
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		droite.setOnMouseDragged(e->{
			cptTranslateGD -= 10;
			miseAJourVue(gc, sliderRotation.getValue(),zoom.getValue());
		});

		haut.setOnAction(e->{
			cptTranslateHB += 10;
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		haut.setOnMouseDragged(e->{
			cptTranslateHB += 10;
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		bas.setOnAction(e->{
			cptTranslateHB -= 10;
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		bas.setOnMouseDragged(e->{
			cptTranslateHB -= 10;
			miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue());
		});

		canv.setOnScroll(e->{
			if(e.getDeltaY() < 0 && zoom.getValue() < defaultZoom*2) { //scroll up
				miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue() + defaultZoom/12.5);
				zoom.setValue(zoom.getValue() + defaultZoom/12.5);
			} else if(e.getDeltaY() > 0 && zoom.getValue() > 0){ //scroll down
				miseAJourVue(gc, sliderRotation.getValue(), zoom.getValue() - defaultZoom/12.5);
				zoom.setValue(zoom.getValue() - defaultZoom/12.5);
			}
		});

		//----AFFICHAGE FENETRE------
		primaryStage.setScene(scene);
		primaryStage.setTitle("i3D"); 
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	/**
	 * Retourne le niveau de zoom adapté à la figure par rapport à sa taille.
	 * @return
	 */
	private double defaultZoom() {
		double max = init.getLoadFile().getCoordMax(0);
		if(Math.abs(init.getLoadFile().getCoordMin(0)) > max)
			max = Math.abs(init.getLoadFile().getCoordMin(0));
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
		else
			return 30;
	}

	/**
	 * Retourne le fichier .ply
	 * @return
	 */
	public File getFichier() {
		return filePly;
	}

	/**
	 * Définit le fichier .ply à  utiliser
	 * @param fichier
	 */
	public void setFichier(File fichier) {
		this.filePly = fichier;
	}

	/**
	 * Méthode mettant à jour la position de la figure dans l'espace à la suite d'un mouvement (rotation, translation ou homothétie).
	 * @param gc
	 * @param rotationValue
	 * @param zoomValue
	 */
	public void miseAJourVue(GraphicsContext gc, double rotationValue, double zoomValue) {
		if(filePly!=null) {
			Translation translation = new Translation();
			Rotation rotation = new Rotation();
			Face[] tabf = init.getLoadFile().getFaces();
			Point[] tabp = init.getLoadFile().getPoints();
			Zoom zoom = new Zoom();

			try {
				if(flagX) {
					tabp = rotation.creerPointRotate(rotationValue, tabp, stratX.execute());
					stratX.setValeurRotation(rotationValue);
				}else
					tabp = rotation.creerPointRotate(stratX.getValeurRotation(), tabp, stratX.execute());
			} catch (MatriceNullException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			} catch (MatriceFormatException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			rotation.recopiePoint(tabf, tabp);

			try {
				if(flagY) {
					tabp = rotation.creerPointRotate(rotationValue, tabp, stratY.execute());
					stratY.setValeurRotation(rotationValue);
				}else
					tabp = rotation.creerPointRotate(stratY.getValeurRotation(), tabp, stratY.execute());
			} catch (MatriceNullException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			} catch (MatriceFormatException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getTitle(), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			rotation.recopiePoint(tabf, tabp);

			try {
				if(flagZ) {
					tabp = rotation.creerPointRotate(rotationValue, tabp, stratZ.execute());
					stratZ.setValeurRotation(rotationValue);
				}else
					tabp = rotation.creerPointRotate(stratZ.getValeurRotation(), tabp, stratZ.execute());
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

			gc.clearRect(0, 0, 1280, 600);
			init.creerFigure(gc, tabf,couleur);
		}
	}

	/**
	 * Méthode lançant le programme.
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args); 
	}
}