package ihm;
import java.io.IOException;

import chargement.Face;
import chargement.Launcher;
import chargement.LoadFile;
import exception.NotAnAxisException;
/**
 * @author bascopa & clarissa
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Polygon;
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
		
		Canvas canv = new Canvas(1100,600);
		GraphicsContext gc = canv.getGraphicsContext2D();
        dessin(gc);
		corps.getChildren().add(canv);
		
		Scene scene = new Scene(corps, 1280, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("i3D");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public void dessin(GraphicsContext gc) throws NotAnAxisException {
		  gc.setFill(Color.GREEN); //remplissage
	      gc.setStroke(Color.BLACK); //contours
	      gc.setLineWidth(1); //epaisseur des lignes
	      LoadFile file;
		try {
			file = new LoadFile();
			file.CreerPoints();
			file.CreerFaces();
			Face[] faces = file.getFaces();
			Launcher l = new Launcher();
			for(int i=0;i<faces.length;++i) {
				faces[i].setCentre_gravite(faces[i].calculCentreGravite());
			}
			l.trierFaces(faces, 1); 
			for (int i = 0; i < faces.length; i++) {
				gc.strokePolygon(faces[i].recupX(), faces[i].recupY(), 3);
				/*gc.transform(faces[i].getPoints()[0].getX(), faces[i].getPoints()[1].getX(), 
						faces[i].getPoints()[0].getY(), faces[i].getPoints()[1].getY(), , );
			*/}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	}
}
