package ihm;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Interface extends Application {

	public void start(Stage primaryStage) throws Exception {
		HBox corps = new HBox();
		
		VBox menu = new VBox();
		
		HBox.setMargin(menu, new Insets(50, 0, 0, 20));
		menu.setMinWidth(150);
		Button b1 = new Button("Importer");
		b1.setMinWidth(150);
		menu.getChildren().add(b1);
		
		FileChooser importer = new FileChooser();
		importer.setTitle("Sélectionner un fichier 3D");
		b1.setOnAction(e -> {
			importer.showOpenDialog(primaryStage);
		});
		
		Slider zoom = new Slider();
		Label lZoom = new Label();
		lZoom.setText("Zoomer");
		lZoom.setStyle("-fx-padding : 20 0 0 50;");
		zoom.setMin(0);
		zoom.setMax(100);
		zoom.setValue(50);
		zoom.setShowTickLabels(true);
		menu.getChildren().addAll(lZoom, zoom);
		
		Slider tourner = new Slider();
		Label lTourner = new Label();
		lTourner.setText("Tourner");
		lTourner.setStyle("-fx-padding : 20 0 0 50;");
		tourner.setMin(-180);
		tourner.setMax(180);
		tourner.setMajorTickUnit(90);
		tourner.setValue(0);
		tourner.setShowTickLabels(true);
		menu.getChildren().addAll(lTourner, tourner);
		
		
		VBox dessin = new VBox();
		Canvas c = new Canvas();
		dessin.getChildren().add(c);
		
		Separator sep = new Separator();
		sep.setOrientation(Orientation.VERTICAL);
		sep.setValignment(VPos.CENTER);
		//A modifier
		sep.setMinHeight(300);
		sep.setStyle("-fx-padding : 0 0 0 30;");
		dessin.getChildren().add(sep);
		
		corps.getChildren().add(menu);
		corps.getChildren().add(dessin);
		
		
		
		Scene scene = new Scene(corps, 758, 280);
		primaryStage.setScene(scene);
		primaryStage.setTitle("i3D");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
