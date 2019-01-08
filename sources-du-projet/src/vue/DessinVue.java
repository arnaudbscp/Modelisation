package src.vue;

import java.io.File;

import java.util.Observable;
import java.util.Observer;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import src.controleur.Controleur;
import src.modele.Face;
import src.modele.ModeDessin;
import src.modele.QuickSort;

/**
 * Classe correspondant à la Vue du design-pattern MVC, implémentant toute la partie graphique du logiciel.
 * @author Valentin
 *
 */
public class DessinVue extends Application implements Observer{

	/**
	 * Boolean true si le pas de translation est valide, c'est à dire que c'est un bien un nombre réel.
	 */
	private boolean pasValide;

	/**
	 * Le contrôleur du MVC, liant les éléments graphiques aux éléments de traitement.
	 */
	private Controleur controleur;

	/**
	 * Contient le mode selectionné pour dessiner la figure (faces + arrêtes, faces seulement ou arrêtes seulement).
	 */
	private ModeDessin modeDessin;

	/**
	 * Couleur de la figure initialisée à blanche dans le constructeur. Elle sera modifiée grâce au colorpicker par l'utilisateur.
	 */
	private Color couleur;

	/**
	 * Définit le contexte graphique de la figure.
	 * @param gc
	 */
	private GraphicsContext gc;

	/**
	 * Constructeur de la vue, spécifiant le contrôleur et attribuant les valeurs initiales des attributs.
	 * @param controleur
	 */
	public DessinVue(Controleur controleur) {
		this.controleur = controleur;
		couleur = Color.WHITE;
		modeDessin = ModeDessin.FACES_ARRETES;
		pasValide = true;
	}

	/**
	 * Méthode permettant d'avoir des éléments graphiques.
	 */
	public void start(Stage primaryStage){

		//ELEMENTS GRAPHIQUES
		HBox corps = new HBox();
		Scene scene = new Scene(corps, 1280, 800);
		VBox menu = new VBox();
		menu.setMinWidth(150);
		Button boutonImport = new Button("Importer");
		boutonImport.setDefaultButton(true);
		Button boutonAide = new Button("Aide");
		VBox vbBoutonsImportAide = new VBox();
		boutonImport.setMinWidth(150);
		boutonAide.setMinWidth(150);
		vbBoutonsImportAide.setSpacing(3);
		vbBoutonsImportAide.getChildren().addAll(boutonImport, boutonAide);
		menu.getChildren().add(vbBoutonsImportAide);
		Canvas canv = new Canvas(1100, 800);
		gc = canv.getGraphicsContext2D();
		gc.setLineWidth(1);
		HBox.setMargin(menu, new Insets(50, 0, 0, 20));
		FileChooser importer = new FileChooser();
		importer.setTitle("Selectionner un fichier 3D");
		Separator sep = new Separator(Orientation.VERTICAL);
		sep.setPrefSize(1, 800);
		corps.getChildren().add(menu);
		corps.getChildren().add(sep);
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
		lblTournerX.setText("Rotation X");
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


		//BOUTON ROTATION AUTO 
		Button rotationAuto = new Button();
		rotationAuto.setDisable(true);
		HBox hboxRotAuto = new HBox();
		Image image = new Image(new File("img/360-degrees.png").toURI().toString());
		ImageView imgView = new ImageView(image);
		imgView.setFitHeight(30);
		imgView.setPreserveRatio(true);
		rotationAuto.setGraphic(imgView);
		hboxRotAuto.getChildren().add(rotationAuto);
		hboxRotAuto.setPadding(new Insets(15,10,10,55));
		hboxRotAuto.setSpacing(30);
		menu.getChildren().addAll(hboxRotAuto);


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


		//BOUTONS FACES/ARRETES/LES DEUX (MODE)
		Label lblMode = new Label("Mode de dessin:");
		lblMode.setPadding(new Insets(0, 0, 0, 20));
		VBox vbBoutonsFacesArretes = new VBox();
		vbBoutonsFacesArretes.setPadding(new Insets(20, 0, 0, 0));
		Button boutonFacesEtArretes = new Button("Faces + Arrêtes");
		boutonFacesEtArretes.setDisable(true);
		Button boutonFaces = new Button("Faces");
		Button boutonArretes = new Button("Arrêtes");
		boutonFacesEtArretes.setPrefWidth(150);
		boutonFaces.setPrefWidth(150);
		boutonArretes.setPrefWidth(150);
		vbBoutonsFacesArretes.getChildren().addAll(lblMode, boutonFacesEtArretes, boutonFaces, boutonArretes);
		vbBoutonsFacesArretes.setSpacing(2);
		menu.getChildren().add(vbBoutonsFacesArretes);
		
		Button butonNewVue = new Button("Nouvelle vue");
		VBox vbButonNewVue = new VBox();
		butonNewVue.setPrefWidth(150);
		vbButonNewVue.setPadding(new Insets(20,0,0,0));
		vbButonNewVue.getChildren().addAll(butonNewVue);
		menu.getChildren().add(vbButonNewVue);
		


		//---------------------GESTION DES EVENEMENTS------------------


		boutonAide.setOnAction(e->{	aide(primaryStage);});

		boutonImport.setOnAction(e -> {	importFichier(primaryStage, importer, sliderZoom, sliderRotation);});

		cp.setOnAction(e ->{ updateCouleur(gc, cp);});

		X.setOnAction(e -> { actionX(sliderRotation, lblTournerX, X, Y, Z);});

		Y.setOnAction(e -> { actionY(sliderRotation, lblTournerX, X, Y, Z);});

		Z.setOnAction(e -> { actionZ(sliderRotation, lblTournerX, X, Y, Z);});

		sliderRotation.setOnMouseDragged(e-> { controleur.setValeurRotation(sliderRotation.getValue());});

		sliderRotation.setOnMouseClicked(e-> { controleur.setValeurRotation(sliderRotation.getValue());});

		sliderZoom.setOnMouseDragged(e -> { controleur.setZoomValue(sliderZoom.getValue());});

		sliderZoom.setOnMouseClicked(e -> { controleur.setZoomValue(sliderZoom.getValue());});

		gauche.setOnAction(e->{ actionGauche(sliderZoom, sliderRotation, tfPas);});

		gauche.setOnMouseDragged(e->{ actionGauche(sliderZoom, sliderRotation, tfPas);});

		droite.setOnAction(e->{ actionDroite(sliderZoom, sliderRotation, tfPas);});

		droite.setOnMouseDragged(e->{ actionDroite(sliderZoom, sliderRotation, tfPas);});

		haut.setOnAction(e->{ actionHaut(sliderZoom, sliderRotation, tfPas);});

		haut.setOnMouseDragged(e->{ actionHaut(sliderZoom, sliderRotation, tfPas);});

		bas.setOnAction(e->{ actionBas(sliderZoom, sliderRotation, tfPas);});

		bas.setOnMouseDragged(e->{ actionBas(sliderZoom, sliderRotation, tfPas);});

		canv.setOnScroll(e->{ scrollZoom(sliderZoom, sliderRotation, e);});

		boutonFacesEtArretes.setOnAction(e->{ actionFacesEtArretes(sliderZoom, sliderRotation, boutonFacesEtArretes, boutonFaces, boutonArretes);});

		boutonFaces.setOnAction(e->{ actionFaces(sliderZoom, sliderRotation, boutonFacesEtArretes, boutonFaces, boutonArretes);});

		boutonArretes.setOnAction(e->{ actionArretes(sliderZoom, sliderRotation, boutonFacesEtArretes, boutonFaces, boutonArretes);});

		tfPas.setOnKeyReleased(e -> { verificationPas(textErreur, hbPas, tfPas);});

		rotationAuto.setOnMouseClicked(e -> { rotationAuto(); });
		
		butonNewVue.setOnAction(e -> { DessinVue dv1 = new DessinVue(controleur);
									   controleur.getModele().addObserver(dv1); 
									   Stage stage2 = new Stage();
									   dv1.start(stage2);
									   controleur.updateModele();});

		//----AFFICHAGE FENETRE------
		primaryStage.setScene(scene);
		primaryStage.setTitle("i3D"); 
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private void updateCouleur(GraphicsContext gc, ColorPicker cp) {
		couleur = cp.getValue();
		creerFigure(controleur.getInit().getLoadFile().getFaces());
	}

	/**
	 * Vérifie si la valeur entrée dans le TextField du pas de translation est une valeur correcte, c'est-à-dire que c'est une valeur
	 * réelle, pouvant contenir un point pour séparer la partie entière de la partie décimale. Si la valeur n'est pas au bon format,
	 * un message d'erreur s'affiche et la valeur vaudra 0 par défaut.
	 * @param textErreur Le message d'erreur à afficher
	 * @param hbPas La HBox contenant le TextField
	 * @param tfPas Le TextField
	 */
	private void verificationPas(Text textErreur, HBox hbPas, TextField tfPas) {
		hbPas.getChildren().remove(textErreur);
		pasValide = true;
		if(!tfPas.getText().matches("^[0-9]+\\.?[0-9]*$")) {
			hbPas.getChildren().add(textErreur);
			pasValide = false;
		}
	}

	/**
	 * Méthode appelée lors d'une action sur le bouton Arrêtes, permetant de spécifier que l'on ne veut afficher que les arrêtes de la
	 * figure. Elle désactive ce bouton et réactive les deux autres boutons des autres modes (Faces et Faces+Arrêtes).
	 * @param sliderZoom
	 * @param sliderRotation
	 * @param boutonFacesEtArretes
	 * @param boutonFaces
	 * @param boutonArretes
	 */
	private void actionArretes(Slider sliderZoom, Slider sliderRotation, Button boutonFacesEtArretes, Button boutonFaces, Button boutonArretes) {
		boutonFacesEtArretes.setDisable(false);
		boutonArretes.setDisable(true);
		boutonFaces.setDisable(false);
		modeDessin = ModeDessin.ARRETES;
		controleur.updateModele();
	}

	/**
	 * Méthode appelée lors d'une action sur le bouton Faces, permetant de spécifier que l'on ne veut afficher que les faces de la
	 * figure. Elle désactive ce bouton et réactive les deux autres boutons des autres modes (Arrêtes et Faces+Arrêtes).
	 * @param sliderZoom
	 * @param sliderRotation
	 * @param boutonFacesEtArretes
	 * @param boutonFaces
	 * @param boutonArretes
	 */
	private void actionFaces(Slider sliderZoom, Slider sliderRotation, Button boutonFacesEtArretes, Button boutonFaces, Button boutonArretes) {
		boutonFacesEtArretes.setDisable(false);
		boutonArretes.setDisable(false);
		boutonFaces.setDisable(true);
		modeDessin = ModeDessin.FACES;
		controleur.updateModele();
	}

	/**
	 * Méthode appelée lors d'une action sur le bouton Faces+Arrêtes, permetant de spécifier que l'on veut afficher les faces et les 
	 * arrêtes de la figure. Elle désactive ce bouton et réactive les deux autres boutons des autres modes (Faces et Arrêtes).
	 * @param sliderZoom
	 * @param sliderRotation
	 * @param boutonFacesEtArretes
	 * @param boutonFaces
	 * @param boutonArretes
	 */
	private void actionFacesEtArretes(Slider sliderZoom, Slider sliderRotation, Button boutonFacesEtArretes, Button boutonFaces, Button boutonArretes) {
		boutonFacesEtArretes.setDisable(true);
		boutonArretes.setDisable(false);
		boutonFaces.setDisable(false);
		modeDessin = ModeDessin.FACES_ARRETES;
		controleur.updateModele();
	}

	/**
	 * Méthode appelée lors du scroll sur le canvas, permettant de zoomer ou de dézoomer la figure.
	 * @param sliderZoom
	 * @param sliderRotation
	 * @param e
	 */
	private void scrollZoom(Slider sliderZoom, Slider sliderRotation, ScrollEvent e) {
		if(e.getDeltaY() > 0 && sliderZoom.getValue() < controleur.getDefaultZoom()*2) { //scroll up
			sliderZoom.setValue(sliderZoom.getValue() + controleur.getDefaultZoom()/12.5);
			controleur.setZoomValue(sliderZoom.getValue());
		} else if(e.getDeltaY() < 0 && sliderZoom.getValue() > 0){ //scroll down
			sliderZoom.setValue(sliderZoom.getValue() - controleur.getDefaultZoom()/12.5);
			controleur.setZoomValue(sliderZoom.getValue());
		}
	}

	/**
	 * Méthode appelée lors du click ou du drag sur le bouton bas, permettant une translation de la figure en bas selon le pas spécifié.
	 * @param sliderZoom
	 * @param sliderRotation
	 * @param tfPas
	 */
	private void actionBas(Slider sliderZoom, Slider sliderRotation, TextField tfPas) {
		if(pasValide) {
			controleur.setCptTranslateHB(controleur.getCptTranslateHB() + Float.parseFloat("0"+tfPas.getText()));
		}
	}

	/**
	 * Méthode appelée lors du click ou du drag sur le bouton haut, permettant une translation de la figure en haut selon le pas spécifié.
	 * @param sliderZoom
	 * @param sliderRotation
	 * @param tfPas
	 */
	private void actionHaut(Slider sliderZoom, Slider sliderRotation, TextField tfPas) {
		if(pasValide) {
			controleur.setCptTranslateHB(controleur.getCptTranslateHB() - Float.parseFloat("0"+tfPas.getText()));
		}
	}

	/**
	 * Méthode appelée lors du click ou du drag sur le bouton droite, permettant une translation de la figure à droite selon le pas spécifié.
	 * @param sliderZoom
	 * @param sliderRotation
	 * @param tfPas
	 */
	private void actionDroite(Slider sliderZoom, Slider sliderRotation, TextField tfPas) {
		if(pasValide) {
			controleur.setCptTranslateGD(controleur.getCptTranslateGD() + Float.parseFloat("0"+tfPas.getText()));
		}
	}

	/**
	 * Méthode appelée lors du click ou du drag sur le bouton gauche, permettant une translation de la figure à gauche selon le pas spécifié.
	 * @param sliderZoom
	 * @param sliderRotation
	 * @param tfPas
	 */
	private void actionGauche(Slider sliderZoom, Slider sliderRotation, TextField tfPas) {
		if(pasValide) {
			controleur.setCptTranslateGD(controleur.getCptTranslateGD() - Float.parseFloat("0"+tfPas.getText()));
		}
	}

	/**
	 * Change l'axe sur lequel la rotation s'effectue par l'axe Z, assigné au slider de rotation.
	 * @param sliderRotation
	 * @param lblTournerX
	 * @param X
	 * @param Y
	 * @param Z
	 */
	private void actionZ(Slider sliderRotation, Label lblTournerX, Button X, Button Y, Button Z) {
		Z.setDisable(true);
		Y.setDisable(false);
		X.setDisable(false);
		lblTournerX.setText("Rotation Z");
		sliderRotation.setValue(controleur.getStrategyZ().getValeurRotation());
		controleur.updateZ();
	}

	/**
	 * Change l'axe sur lequel la rotation s'effectue par l'axe Y, assigné au slider de rotation.
	 * @param sliderRotation
	 * @param lblTournerX
	 * @param X
	 * @param Y
	 * @param Z
	 */
	private void actionY(Slider sliderRotation, Label lblTournerX, Button X, Button Y, Button Z) {
		Y.setDisable(true);
		X.setDisable(false);
		Z.setDisable(false);
		lblTournerX.setText("Rotation Y");
		sliderRotation.setValue(controleur.getStrategyY().getValeurRotation());
		controleur.updateY();
	}

	/**
	 * Change l'axe sur lequel la rotation s'effectue par l'axe X, assigné au slider de rotation.
	 * @param sliderRotation
	 * @param lblTournerX
	 * @param X
	 * @param Y
	 * @param Z
	 */
	private void actionX(Slider sliderRotation, Label lblTournerX, Button X, Button Y, Button Z) {
		X.setDisable(true);
		Y.setDisable(false);
		Z.setDisable(false);
		lblTournerX.setText("Rotation X");
		sliderRotation.setValue(controleur.getStrategyX().getValeurRotation());
		controleur.updateX();
	}

	/**
	 * Importe le fichier dans le logiciel, permettant ensuite son interprétation et l'affichage de la figure qu'il définit.
	 * @param primaryStage
	 * @param importer
	 * @param sliderZoom
	 */
	private void importFichier(Stage primaryStage, FileChooser importer, Slider sliderZoom, Slider sliderRotation) {
		controleur.updateFichier(importer.showOpenDialog(primaryStage));
		if(controleur.getInit() != null) {
			if(controleur.getInit().isGood()) {
				sliderZoom.setDisable(false);
				sliderZoom.setMin(0);
				sliderZoom.setMax(controleur.getDefaultZoom()*2);
				sliderZoom.setValue(controleur.getDefaultZoom());
				sliderZoom.setMajorTickUnit(controleur.getDefaultZoom()/2.5);
				sliderZoom.setBlockIncrement(controleur.getDefaultZoom()/12.5);
				sliderZoom.setShowTickLabels(true);
			}
		}
	}

	/**
	 * Affiche la fenêtre d'aide, expliquant à l'utilisateur les fonctionnalités du logiciel.
	 * @param primaryStage
	 */
	private void aide(Stage primaryStage) {
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
	}

	/**
	 * Créer la figure en interprétant les différentes coordonnées de points et en les reliant entre eux, puis en colorant la figure 
	 * selon l'angle dans lequel arrive la lumière.
	 * @param gc
	 * @param faces
	 * @param c
	 */
	public void creerFigure(Face[] faces) {
		double[] px;
		double[] pz;
		QuickSort.getInstance().setTab(faces);
		QuickSort.getInstance().sort();
		for (int i = 0; i < faces.length; i++) {
			//System.out.println(i+" "+faces[i]); //Problème avec les coordonnées Y des points de la face, toutes à 0. C'est surement ça qui cause les problèmes d'affichage.
			px = new double[] {faces[i].getPoints()[0].getX() + (gc.getCanvas().getWidth()/2), faces[i].getPoints()[1].getX() + (gc.getCanvas().getWidth()/2), faces[i].getPoints()[2].getX() + (gc.getCanvas().getWidth()/2)};
			pz = new double[] {faces[i].getPoints()[0].getZ() + (gc.getCanvas().getHeight()/2), faces[i].getPoints()[1].getZ() + (gc.getCanvas().getHeight()/2), faces[i].getPoints()[2].getZ() + (gc.getCanvas().getHeight()/2)};
			double cosinus = controleur.calculVecteurNormal(faces[i]);
//			System.out.println(cosinus);
			if(modeDessin.equals(ModeDessin.FACES_ARRETES)) {
				gc.fillPolygon(px, pz, 3);
				gc.strokePolygon(px, pz, 3);
				gc.setFill(couleur.deriveColor(couleur.getHue(), couleur.getSaturation(), couleur.getBrightness()*cosinus, couleur.getOpacity()));
			}else if(modeDessin.equals(ModeDessin.FACES)) {
				gc.fillPolygon(px, pz, 3);
				gc.setFill(couleur.deriveColor(couleur.getHue(), couleur.getSaturation(), couleur.getBrightness()*cosinus, couleur.getOpacity()));
			}else
				gc.strokePolygon(px, pz, 3);
		}
	}

	/**
	 * Méthode de l'interface Observer, permettant la mise à jour de la figure avec les nouvelles valeurs du modèle.
	 */
	public void update(Observable o, Object arg) {
		gc.clearRect(0, 0, 1280, 800);
		creerFigure(((src.modele.Modele)o).miseAJourVue());
	}

	/**
	 * Effectue automatiquement une rotation de 360° de la figure autour de l'axe actif.
	 */
	private void rotationAuto() {
		boolean rotat = true;
		controleur.rotationAuto(rotat);
	}
}
