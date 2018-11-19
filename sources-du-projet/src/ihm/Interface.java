package src.ihm;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import src.donnees.Face;
import src.donnees.Point;

import src.mecanique.Initialisation;
import src.mecanique.ModeDessin;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
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
	private float cptTranslateGD = 0;

	/**
	 * Compteur de translation haut-bas. On l'incrémente lors de l'appui sur le bouton haut et on le décrémente lors de 
	 * l'appui sur le bouton bas pour déplacer la figure verticalement.
	 */
	private float cptTranslateHB = 0;

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
	 * Boolean true si le pas de translation est valide, c'est à dire que c'est un bien un nombre réel.
	 */
	private boolean pasValide = true;
	
	/**
	 * Contient le mode selectionné pour dessiner la figure (faces + arrêtes, faces seulement ou arrêtes seulement).
	 */
	private ModeDessin md = ModeDessin.FACES_ARRETES;

	/**
	 * Méthode d'affichage de l'interface graphique.
	 */
	public void start(Stage primaryStage){
		//ELEMENTS GRAPHIQUES
		HBox corps = new HBox();
		Scene scene = new Scene(corps, 1280, 700);
		VBox menu = new VBox();
		menu.setMinWidth(150);
		Button b1 = new Button("Importer");
		b1.setDefaultButton(true);
		Button aide = new Button("Aide");
		VBox boutonsImportAide = new VBox();
		b1.setMinWidth(150);
		aide.setMinWidth(150);
		boutonsImportAide.setSpacing(3);
		boutonsImportAide.getChildren().addAll(b1, aide);
		menu.getChildren().add(boutonsImportAide);
		Canvas canv = new Canvas(1100, 600);
		GraphicsContext gc = canv.getGraphicsContext2D();
		HBox.setMargin(menu, new Insets(50, 0, 0, 20));
		FileChooser importer = new FileChooser();
		importer.setTitle("Selectionner un fichier 3D");
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
		Text textErreur = new Text(" Erreur.");
		textErreur.setFill(Color.RED);

		//SLIDER ZOOM
		Slider sliderZoom = new Slider();
		Label lblZoom = new Label();
		lblZoom.setText("Zoomer");
		lblZoom.setStyle("-fx-padding : 20 0 0 50;");
		sliderZoom.setDisable(true);
		menu.getChildren().addAll(lblZoom, sliderZoom);

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
		HBox hbPas = new HBox();
		hbPas.setPadding(new Insets(3, 0, 0, 0));
		Label lblPas = new Label("Pas: ");
		TextField tfPas = new TextField("10");
		tfPas.setPrefWidth(50);
		hbPas.getChildren().addAll(lblPas, tfPas);
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
		menu.getChildren().addAll(lblTranslation, hbHaut, hbGaucheDroite, hbBas, hbPas);

		//CHOIX COULEURS
		Label lcolor = new Label("Couleur");
		lcolor.setPadding(new Insets(30, 0, 10,50));
		ColorPicker cp = new ColorPicker();
		cp.setValue(couleur);
		menu.getChildren().addAll(lcolor,cp);
		
		//BOUTONS FACES/ARRETES/LES DEUX
		VBox boutonsFacesArretes = new VBox();
		boutonsFacesArretes.setPadding(new Insets(20, 0, 0, 0));
		Button buttonFacesEtArretes = new Button("Faces + Arrêtes");
		buttonFacesEtArretes.setDisable(true);
		Button buttonFaces = new Button("Faces");
		Button buttonArretes = new Button("Arrêtes");
		buttonArretes.setPrefWidth(150);
		buttonFacesEtArretes.setPrefWidth(150);
		buttonFaces.setPrefWidth(150);
		boutonsFacesArretes.getChildren().addAll(buttonFacesEtArretes, buttonFaces, buttonArretes);
		boutonsFacesArretes.setSpacing(2);
		menu.getChildren().add(boutonsFacesArretes);
		


		//---------------------GESTION DES EVENEMENTS------------------

		aide.setOnAction(e->{
			Stage stageAide = new Stage();
			stageAide.initOwner(primaryStage); //Définit la fenêtre principale comme fenêtre parente.
			stageAide.initModality(Modality.WINDOW_MODAL); //Verrouille la fenêtre parente.
			VBox rootAide = new VBox();
			Scene sceneAide = new Scene(rootAide, 610, 180);
			stageAide.setTitle("Aide");
			stageAide.setScene(sceneAide);
			Text textAide = new Text("Ce logiciel te permet d'afficher des figures géométriques dans un espace 3D. Pour cela, choisis le fichier .ply contenant les coordonnées des points et les faces de la figure que tu souhaites charger grâce au bouton \"Importer\". Tu pourras ensuite déplacer la figure dans l'espace grâce aux différents Sliders et Boutons, qui permettent d'effectuer des rotations, des translations et de zoomer. Tu peux régler le pas de la translation pour plus ou moins de précision dans le champ dédié. Tu peux également zoomer avec la molette de la souris. Enfin, tu peux customiser la figure en changeant sa couleur !");
			textAide.setWrappingWidth(sceneAide.getWidth()-10); //Adapte le texte à la largeur de la fenêtre.
			textAide.setLineSpacing(5); //Définit la valeur de l'interligne.
			textAide.setTextAlignment(TextAlignment.JUSTIFY); //Justifie le texte.
			Button okAide = new Button("J'ai compris !");
			okAide.setDefaultButton(true); //Définit le bouton "J'ai compris" comme bouton par défaut de la fenêtre, permettant son activation à l'appui de la touche Entrée.
			rootAide.getChildren().addAll(textAide, okAide);
			okAide.setOnAction(e2->{
				stageAide.close();
			});
			rootAide.setPadding(new Insets(5));
			rootAide.setSpacing(5);
			stageAide.setResizable(false);
			stageAide.show();
		});

		b1.setOnAction(e -> {
			filePly = importer.showOpenDialog(primaryStage);
			if(filePly != null) {
				String extension = "";
				short i = 0;
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
				init.creerFigure(gc, init.getLoadFile().getFaces(), couleur, md);
				cptTranslateGD = 0;
				cptTranslateHB = 0;
				defaultZoom = defaultZoom();
				sliderZoom.setDisable(false);
				sliderZoom.setMin(0);
				sliderZoom.setMax(defaultZoom*2);
				sliderZoom.setValue(defaultZoom);
				sliderZoom.setMajorTickUnit(defaultZoom/2.5);
				sliderZoom.setBlockIncrement(defaultZoom/12.5);
				sliderZoom.setShowTickLabels(true);
				//On simule un premier mouvement de la figure pour que tous les mouvements fonctionnent correctement.
				miseAJourVue(gc, sliderRotation.getValue(), sliderZoom.getValue());
			}
		});

		cp.setOnAction(e ->{
			couleur = cp.getValue();
			if(filePly != null)
				init.creerFigure(gc, init.getLoadFile().getFaces(), couleur, md);
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
			miseAJourVue(gc, sliderRotation.getValue(), sliderZoom.getValue());
		});

		sliderRotation.setOnMouseClicked(e-> {
			miseAJourVue(gc, sliderRotation.getValue(),  sliderZoom.getValue());
		});

		sliderZoom.setOnMouseDragged(e -> {
			miseAJourVue(gc, sliderRotation.getValue(), sliderZoom.getValue());
		});

		sliderZoom.setOnMouseClicked(e -> {
			miseAJourVue(gc, sliderRotation.getValue(), sliderZoom.getValue());
		});

		gauche.setOnAction(e->{
			if(pasValide) {
				cptTranslateGD += Float.parseFloat("0"+tfPas.getText());
				miseAJourVue(gc, sliderRotation.getValue(), sliderZoom.getValue());
			}
		});

		gauche.setOnMouseDragged(e->{
			if(pasValide) {
				cptTranslateGD += Float.parseFloat("0"+tfPas.getText());
				miseAJourVue(gc, sliderRotation.getValue(), sliderZoom.getValue());
			}
		});

		droite.setOnAction(e->{
			if(pasValide) {
				cptTranslateGD -= Float.parseFloat("0"+tfPas.getText());
				miseAJourVue(gc, sliderRotation.getValue(), sliderZoom.getValue());
			}
		});

		droite.setOnMouseDragged(e->{
			if(pasValide) {
				cptTranslateGD -= Float.parseFloat("0"+tfPas.getText());
				miseAJourVue(gc, sliderRotation.getValue(),sliderZoom.getValue());
			}
		});

		haut.setOnAction(e->{
			if(pasValide) {
				cptTranslateHB += Float.parseFloat("0"+tfPas.getText());
				miseAJourVue(gc, sliderRotation.getValue(), sliderZoom.getValue());
			}
		});

		haut.setOnMouseDragged(e->{
			if(pasValide) {
				cptTranslateHB += Float.parseFloat("0"+tfPas.getText());
				miseAJourVue(gc, sliderRotation.getValue(), sliderZoom.getValue());
			}
		});

		bas.setOnAction(e->{
			if(pasValide) {
				cptTranslateHB -= Float.parseFloat("0"+tfPas.getText());
				miseAJourVue(gc, sliderRotation.getValue(), sliderZoom.getValue());
			}
		});

		bas.setOnMouseDragged(e->{
			if(pasValide) {
				cptTranslateHB -= Float.parseFloat("0"+tfPas.getText());
				miseAJourVue(gc, sliderRotation.getValue(), sliderZoom.getValue());
			}
		});

		canv.setOnScroll(e->{
			if(e.getDeltaY() > 0 && sliderZoom.getValue() < defaultZoom*2) { //scroll up
				miseAJourVue(gc, sliderRotation.getValue(), sliderZoom.getValue() + defaultZoom/12.5);
				sliderZoom.setValue(sliderZoom.getValue() + defaultZoom/12.5);
			} else if(e.getDeltaY() < 0 && sliderZoom.getValue() > 0){ //scroll down
				miseAJourVue(gc, sliderRotation.getValue(), sliderZoom.getValue() - defaultZoom/12.5);
				sliderZoom.setValue(sliderZoom.getValue() - defaultZoom/12.5);
			}
		});

		tfPas.setOnKeyReleased(e -> {
			hbPas.getChildren().remove(textErreur);
			pasValide = true;
			if(!tfPas.getText().matches("^[0-9]+\\.?[0-9]*$")) {
				hbPas.getChildren().add(textErreur);
				pasValide = false;
			}
		});
		
		buttonFacesEtArretes.setOnAction(e->{
			buttonFacesEtArretes.setDisable(true);
			buttonArretes.setDisable(false);
			buttonFaces.setDisable(false);
			md = ModeDessin.FACES_ARRETES;
			miseAJourVue(gc, sliderRotation.getValue(), sliderZoom.getValue());
		});

		buttonFaces.setOnAction(e->{
			buttonFacesEtArretes.setDisable(false);
			buttonArretes.setDisable(false);
			buttonFaces.setDisable(true);
			md = ModeDessin.FACES;
			miseAJourVue(gc, sliderRotation.getValue(), sliderZoom.getValue());
		});
		
		buttonArretes.setOnAction(e->{
			buttonFacesEtArretes.setDisable(false);
			buttonArretes.setDisable(true);
			buttonFaces.setDisable(false);
			md = ModeDessin.ARRETES;
			miseAJourVue(gc, sliderRotation.getValue(), sliderZoom.getValue());
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
			init.creerFigure(gc, tabf,couleur, md);
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