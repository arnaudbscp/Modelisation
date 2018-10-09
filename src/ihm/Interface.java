package ihm;

import java.io.IOException;

import chargement.Face;
import chargement.Initialisation;
import chargement.LoadFile;
import exception.WrongFaceLineFormatException;
import exception.WrongPointLineFormatException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author bascopa & clarissa
 */
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
		importer.setTitle("Selectionner un fichier 3D");
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
		
		Slider tournerX = new Slider();
		Label lTournerX = new Label();
		lTournerX.setText("Tourner X");
		lTournerX.setStyle("-fx-padding : 20 0 0 50;");
		tournerX.setMin(0);
		tournerX.setMax(360);
		tournerX.setMajorTickUnit(90);
		tournerX.setValue(0);
		tournerX.setShowTickLabels(true);
		menu.getChildren().addAll(lTournerX, tournerX);
		
		Slider tournerY = new Slider();
		Label lTournerY = new Label();
		lTournerY.setText("Tourner Y");
		lTournerY.setStyle("-fx-padding : 20 0 0 50;");
		tournerY.setMin(0);
		tournerY.setMax(360);
		tournerY.setMajorTickUnit(90);
		tournerY.setValue(0);
		tournerY.setShowTickLabels(true);
		menu.getChildren().addAll(lTournerY, tournerY);
		
		Slider tournerZ = new Slider();
		Label lTournerZ = new Label();
		lTournerZ.setText("Tourner Z");
		lTournerZ.setStyle("-fx-padding : 20 0 0 50;");
		tournerZ.setMin(-180);
		tournerZ.setMax(180);
		tournerZ.setMajorTickUnit(90);
		tournerZ.setValue(0);
		tournerZ.setShowTickLabels(true);
		menu.getChildren().addAll(lTournerZ, tournerZ);
		
		
		VBox dessin = new VBox();
		
		Separator sep = new Separator();
		sep.setOrientation(Orientation.VERTICAL);
		sep.setValignment(VPos.CENTER);
		//A modifier
		sep.setMinHeight(300);
		sep.setStyle("-fx-padding : 0 0 0 30;");
		dessin.getChildren().add(sep);
		
		corps.getChildren().add(menu);
		corps.getChildren().add(dessin);
		
		Canvas canv = new Canvas(1100,600);
		GraphicsContext gc = canv.getGraphicsContext2D();
		
		Group objetComplet = dessin(gc);
		
		tournerX.setOnMouseDragged(e-> {
			objetComplet.setRotate(tournerX.getValue());
		});
		tournerX.setOnMouseClicked(e-> {
			objetComplet.setRotate(tournerX.getValue());
		});
		
		tournerY.setOnMouseDragged(e-> {
			// on créé un objet rotation et on parcours l'objet pour recuperer chaque triangle
			// t.executer retourne trois nouveaux points avec de nouvelles coordonnées pour chaque triangle
			/**
			RotationObjet t = new RotationObjet(objetComplet, tournerY.getValue());
			for(int index = 0; index < objetComplet.getChildren().size(); index++) {
				Polygon tmp = (Polygon) objetComplet.getChildren().get(index);
				for(int i = 0; i < 3; i++) {
					tmp.getPoints().setAll(t.executer(index));
				}
			} REFAIRE L'AFFICHAGE, problème au niveau de la translation Y*/
		});
		
		
		corps.getChildren().add(objetComplet);
		
		Scene scene = new Scene(corps, 1280, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("i3D");
		primaryStage.setResizable(true);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public Group dessin(GraphicsContext gc){
	      gc.setLineWidth(1); //epaisseur des lignes
	      LoadFile file;
	      Group g = new Group();
		try {
			file = new LoadFile();
			try {
				file.CreerPoints();
				file.CreerFaces();
			} catch (WrongPointLineFormatException | WrongFaceLineFormatException e) {
				// TODO Auto-generated catch block
				System.exit(1);
			}
			Initialisation l = new Initialisation();
			Face[] faces = l.getFaces();
			for (int i = 0; i < faces.length; i++) {
				Polygon triangle = new Polygon();
				triangle.getPoints().setAll(
						(double)faces[i].getPoints()[0].getX()*-1, (double)faces[i].getPoints()[0].getZ()*-1,
						(double)faces[i].getPoints()[1].getX()*-1, (double)faces[i].getPoints()[1].getZ()*-1,
						(double)faces[i].getPoints()[2].getX()*-1, (double)faces[i].getPoints()[2].getZ()*-1
				);
				triangle.setStroke(Color.BLACK);
				triangle.setFill(Color.GRAY);
				g.getChildren().add(triangle);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.setTranslateX(100);
		g.setTranslateY(150);
		return g;
	}
}
