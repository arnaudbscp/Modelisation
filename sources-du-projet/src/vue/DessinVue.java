package src.vue;


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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.controleur.Controleur;
import src.mecanique.ModeDessin;

public class DessinVue extends Application implements Observer{

	

	/**
	 * Boolean true si le pas de translation est valide, c'est à dire que c'est un bien un nombre réel.
	 */
	private boolean pasValide = true;

	Controleur controleur;
	
	public DessinVue(Controleur controleur) {
		this.controleur = controleur;
	}


	public void start(Stage primaryStage){
		//ELEMENTS GRAPHIQUES
		HBox corps = new HBox();
		Scene scene = new Scene(corps, 1280, 700);
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
		Canvas canv = new Canvas(1100, 700);
		GraphicsContext gc = canv.getGraphicsContext2D();
		controleur.setGc(gc);
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
		cp.setValue(controleur.getCouleur());
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
		boutonArretes.setPrefWidth(150);
		boutonFacesEtArretes.setPrefWidth(150);
		boutonFaces.setPrefWidth(150);
		vbBoutonsFacesArretes.getChildren().addAll(lblMode, boutonFacesEtArretes, boutonFaces, boutonArretes);
		vbBoutonsFacesArretes.setSpacing(2);
		menu.getChildren().add(vbBoutonsFacesArretes);

		//---------------------GESTION DES EVENEMENTS------------------

		boutonAide.setOnAction(e->{
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

		boutonImport.setOnAction(e -> {
			controleur.updateFichier(importer.showOpenDialog(primaryStage));
			sliderZoom.setDisable(false);
			sliderZoom.setMin(0);
			sliderZoom.setMax(controleur.getDefaultzoom()*2);
			sliderZoom.setValue(controleur.getDefaultzoom());
			sliderZoom.setMajorTickUnit(controleur.getDefaultzoom()/2.5);
			sliderZoom.setBlockIncrement(controleur.getDefaultzoom()/12.5);
			sliderZoom.setShowTickLabels(true);
		});

		cp.setOnAction(e ->{
			controleur.updateCouleur(cp.getValue());
		});

		X.setOnAction(e -> {
			X.setDisable(true);
			Y.setDisable(false);
			Z.setDisable(false);
			lblTournerX.setText("Rotation X");
			sliderRotation.setValue(controleur.getStrategyX().getValeurRotation());
			controleur.updateX();
		});

		Y.setOnAction(e -> {
			Y.setDisable(true);
			X.setDisable(false);
			Z.setDisable(false);
			lblTournerX.setText("Rotation Y");
			sliderRotation.setValue(controleur.getStrategyY().getValeurRotation());
			controleur.updateY();
		});

		Z.setOnAction(e -> {
			Z.setDisable(true);
			Y.setDisable(false);
			X.setDisable(false);
			lblTournerX.setText("Rotation Z");
			sliderRotation.setValue(controleur.getStrategyZ().getValeurRotation());
			controleur.updateZ();
		});

		sliderRotation.setOnMouseDragged(e-> {
			controleur.updateModele(sliderRotation.getValue(), sliderZoom.getValue(), controleur.getcptTranslateGD(), controleur.getcptTranslateHB());
		});

		sliderRotation.setOnMouseClicked(e-> {
			controleur.updateModele(sliderRotation.getValue(), sliderZoom.getValue(), controleur.getcptTranslateGD(), controleur.getcptTranslateHB());
		});

		sliderZoom.setOnMouseDragged(e -> {
			controleur.updateModele(sliderRotation.getValue(), sliderZoom.getValue(), controleur.getcptTranslateGD(), controleur.getcptTranslateHB());
		});

		sliderZoom.setOnMouseClicked(e -> {
			controleur.updateModele(sliderRotation.getValue(), sliderZoom.getValue(), controleur.getcptTranslateGD(), controleur.getcptTranslateHB());
		});

		gauche.setOnAction(e->{
			if(pasValide) {
				controleur.setcptTranslateGD(controleur.getcptTranslateGD() + Float.parseFloat("0"+tfPas.getText()));
				controleur.updateModele(sliderRotation.getValue(), sliderZoom.getValue(), controleur.getcptTranslateGD(), controleur.getcptTranslateHB());
			}
		});

		gauche.setOnMouseDragged(e->{
			if(pasValide) {
				controleur.setcptTranslateGD(controleur.getcptTranslateGD() + Float.parseFloat("0"+tfPas.getText()));
				controleur.updateModele(sliderRotation.getValue(), sliderZoom.getValue(), controleur.getcptTranslateGD(), controleur.getcptTranslateHB());
			}
		});

		droite.setOnAction(e->{
			if(pasValide) {
				controleur.setcptTranslateGD(controleur.getcptTranslateGD() - Float.parseFloat("0"+tfPas.getText()));
				controleur.updateModele(sliderRotation.getValue(), sliderZoom.getValue(), controleur.getcptTranslateGD(), controleur.getcptTranslateHB());
			}
		});

		droite.setOnMouseDragged(e->{
			if(pasValide) {
				controleur.setcptTranslateGD(controleur.getcptTranslateGD() - Float.parseFloat("0"+tfPas.getText()));
				controleur.updateModele(sliderRotation.getValue(), sliderZoom.getValue(), controleur.getcptTranslateGD(), controleur.getcptTranslateHB());
			}
		});

		haut.setOnAction(e->{
			if(pasValide) {
				controleur.setcptTranslateHB(controleur.getcptTranslateHB() + Float.parseFloat("0"+tfPas.getText()));
				controleur.updateModele(sliderRotation.getValue(), sliderZoom.getValue(), controleur.getcptTranslateGD(), controleur.getcptTranslateHB());
			}
		});

		haut.setOnMouseDragged(e->{
			if(pasValide) {
				controleur.setcptTranslateHB(controleur.getcptTranslateHB() + Float.parseFloat("0"+tfPas.getText()));
				controleur.updateModele(sliderRotation.getValue(), sliderZoom.getValue(), controleur.getcptTranslateGD(), controleur.getcptTranslateHB());
			}
		});

		bas.setOnAction(e->{
			if(pasValide) {
				controleur.setcptTranslateHB(controleur.getcptTranslateHB() - Float.parseFloat("0"+tfPas.getText()));
				controleur.updateModele(sliderRotation.getValue(), sliderZoom.getValue(), controleur.getcptTranslateGD(), controleur.getcptTranslateHB());
			}
		});

		bas.setOnMouseDragged(e->{
			if(pasValide) {
				controleur.setcptTranslateHB(controleur.getcptTranslateHB() - Float.parseFloat("0"+tfPas.getText()));
				controleur.updateModele(sliderRotation.getValue(), sliderZoom.getValue(), controleur.getcptTranslateGD(), controleur.getcptTranslateHB());
			}
		});

		canv.setOnScroll(e->{
			if(e.getDeltaY() > 0 && sliderZoom.getValue() < controleur.getDefaultzoom()*2) { //scroll up
				controleur.setDefaultzoom(sliderZoom.getValue() + controleur.getDefaultzoom()/12.5);
				controleur.updateModele(sliderRotation.getValue(), sliderZoom.getValue(), controleur.getcptTranslateGD(), controleur.getcptTranslateHB());
				sliderZoom.setValue(sliderZoom.getValue() + controleur.getDefaultzoom()/12.5);
			} else if(e.getDeltaY() < 0 && sliderZoom.getValue() > 0){ //scroll down
				controleur.setDefaultzoom(sliderZoom.getValue() - controleur.getDefaultzoom()/12.5);
				controleur.updateModele(sliderRotation.getValue(), sliderZoom.getValue(), controleur.getcptTranslateGD(), controleur.getcptTranslateHB());
				sliderZoom.setValue(sliderZoom.getValue() - controleur.getDefaultzoom()/12.5);
			}
		});

		boutonFacesEtArretes.setOnAction(e->{
			boutonFacesEtArretes.setDisable(true);
			boutonArretes.setDisable(false);
			boutonFaces.setDisable(false);
			controleur.setModeDessin(ModeDessin.FACES_ARRETES);
			controleur.updateModele(sliderRotation.getValue(), sliderZoom.getValue(), controleur.getcptTranslateGD(), controleur.getcptTranslateHB());
		});

		boutonFaces.setOnAction(e->{
			boutonFacesEtArretes.setDisable(false);
			boutonArretes.setDisable(false);
			boutonFaces.setDisable(true);
			controleur.setModeDessin(ModeDessin.FACES);
			controleur.updateModele(sliderRotation.getValue(), sliderZoom.getValue(), controleur.getcptTranslateGD(), controleur.getcptTranslateHB());
		});

		boutonArretes.setOnAction(e->{
			boutonFacesEtArretes.setDisable(false);
			boutonArretes.setDisable(true);
			boutonFaces.setDisable(false);
			controleur.setModeDessin(ModeDessin.ARRETES);
			controleur.updateModele(sliderRotation.getValue(), sliderZoom.getValue(), controleur.getcptTranslateGD(), controleur.getcptTranslateHB());
		});


		tfPas.setOnKeyReleased(e -> {
			hbPas.getChildren().remove(textErreur);
			pasValide = true;
			if(!tfPas.getText().matches("^[0-9]+\\.?[0-9]*$")) {
				hbPas.getChildren().add(textErreur);
				pasValide = false;
			}
		});

		//----AFFICHAGE FENETRE------
		primaryStage.setScene(scene);
		primaryStage.setTitle("i3D"); 
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public void update(Observable o, Object arg) {
		controleur.miseAJourVue(((src.modele.Modele)o).getRotationValue(), ((src.modele.Modele)o).getZoomValue(), ((src.modele.Modele)o).getCptTranslateGD(), ((src.modele.Modele)o).getCptTranslateHB());
	}
}
