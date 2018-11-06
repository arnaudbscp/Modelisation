package Vue;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import src.ihm.Strategy;
import src.ihm.StrategyRotationX;
import src.ihm.StrategyRotationY;
import src.ihm.StrategyRotationZ;

public class DessinVue extends Application {

	private Strategy stratX = new StrategyRotationX(0);

	/**
	 * A DOCUMENTER
	 */
	private Strategy stratY = new StrategyRotationY(0);

	/**
	 * A DOCUMENTER
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
		menu.getChildren().addAll(lcolor,cp);
	}
}
