package ihm;

import java.io.IOException;

import chargement.Face;
import chargement.Initialisation;
import chargement.LoadFile;
import exception.MatriceFormatException;
import exception.MatriceNullException;
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
	LoadFile file;

	public void start(Stage primaryStage) throws Exception {
		try {
			file = new LoadFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Initialisation l = new Initialisation();
		Group g = new Group();
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

		dessin(gc, file, g, l);

		tournerX.setOnMouseDragged(e-> {
			Rotation r = new Rotation();
			try {
				file.setPoints(r.CreerPointrotate(tournerX.getValue(), file.getPoints()));
			} catch (MatriceNullException | MatriceFormatException e1) {
				e1.printStackTrace();
			}
			try {
				file.getBr().reset();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				file.CreerFaces();
			} catch (IOException | WrongFaceLineFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			l.CreerFigure(g);
		});

		tournerX.setOnMouseClicked(e ->{
			System.out.println(tournerX.getValue());
		});


		corps.getChildren().add(g);

		Scene scene = new Scene(corps, 1280, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("i3D");
		primaryStage.setResizable(true);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void dessin(GraphicsContext gc, LoadFile file, Group g, Initialisation l) throws IOException{
		gc.setLineWidth(1); //epaisseur des lignes
		try {
			file.CreerPoints();
			file.CreerFaces();
		} catch (WrongPointLineFormatException | WrongFaceLineFormatException e) {
			// TODO Auto-generated catch block
			System.exit(1);
		}
		l = new Initialisation();
		l.CreerFigure(g);
		g.setTranslateX(100);
		g.setTranslateY(150);
	}
}
