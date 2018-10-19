package ihm;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import chargement.Face;
import chargement.Initialisation;
import chargement.LoadFile;
import chargement.Point;
import exception.MatriceFormatException;
import exception.MatriceNullException;
import exception.WrongFaceLineFormatException;
import exception.WrongPointLineFormatException;

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
 * @author bascopa & clarissa
 */
public class Interface extends Application {
	
	LoadFile file;
	File filePly;
	Initialisation l;
	int cpt_translate=0;
	Color c = Color.WHITE;

	public void start(Stage primaryStage) throws Exception {
		//Déplacement dans l'action du bouton importer
		/*try {
			file = new LoadFile();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		HBox corps = new HBox();

		VBox menu = new VBox();

		Canvas canv = new Canvas(1100,600);
		GraphicsContext gc = canv.getGraphicsContext2D();

		HBox.setMargin(menu, new Insets(50, 0, 0, 20));
		menu.setMinWidth(150);
		Button b1 = new Button("Importer");
		b1.setMinWidth(150);
		menu.getChildren().add(b1);

		FileChooser importer = new FileChooser();
		importer.setTitle("Selectionner un fichier 3D");
		b1.setOnAction(e -> {
			filePly = importer.showOpenDialog(primaryStage);
			String extension = "";
			int i=0;
			while (filePly.getPath().charAt(i) != '.')
				i++;
			extension = filePly.getPath().substring(i, filePly.getPath().length());	

			try {
				if (extension.equals(".ply")) {
					gc.clearRect(0, 0, 1600, 800);
					file = new LoadFile(filePly);
				}
				else 
					JOptionPane.showMessageDialog(null,"/!\\ Veuillez choisir un fichier .ply ! \n","Erreur format fichier",JOptionPane.ERROR_MESSAGE);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			l = null;
			try {
				l = new Initialisation(filePly);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				dessin(gc, file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
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

		Label ltranslation = new Label("Translation");
		HBox hb_haut = new HBox();
		HBox hb_gauche_droite = new HBox();
		HBox hb_bas = new HBox();
		Button haut = new Button("^");
		Button gauche = new Button("<");
		Button droite = new Button(">");
		Button bas = new Button("v");
		hb_haut.getChildren().add(haut);
		hb_gauche_droite.getChildren().addAll(gauche,droite);
		hb_bas.getChildren().add(bas);
		menu.getChildren().addAll(ltranslation,hb_haut,hb_gauche_droite,hb_bas);

		Label lcolor = new Label("Couleur");
		ColorPicker cp = new ColorPicker();
		
		menu.getChildren().addAll(lcolor,cp);
		
		cp.setOnAction(e->{
			c=cp.getValue();
			l.creerFigure(gc, file.getFaces(), c);
		});

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


		//Déplacement dans l'action du bouton importer
		//dessin(gc, file, g, l);
		Rotation r = new Rotation();
		tournerX.setOnMouseDragged(e-> {
			try {
				Point[] tabp = r.creerPointrotate(tournerX.getValue(), file.getPoints());
				Face[] tabf = file.getFaces();

				r.recopiePoint(tabf, tabp);
				gc.clearRect(0, 0, 1280, 600);
			
				l.creerFigure(gc, tabf,c);
				r.setPoint(tabp);
				
			} catch (MatriceNullException | MatriceFormatException e1) {
				e1.printStackTrace();
			}
		
		});
		
		tournerX.setOnDragDone(e-> {
			file.setPoints(r.getPoint());
			r.recopiePoint(file.getFaces(), r.getPoint());
		});

		zoom.setOnMouseDragged(e -> {
			Point[] pZoom = new Point[file.getPoints().length];
			for (int i=0; i<file.getPoints().length; i++) {
				Point p = new Point((float)(file.getPoints()[i].getX()*(zoom.getValue()/100)), (float)(file.getPoints()[i].getY()*(zoom.getValue()/100)), (float)(file.getPoints()[i].getZ()*zoom.getValue()/100));
				pZoom[i] = p;
			}
			Face[] tabf = file.getFaces();
			r.recopiePoint(tabf, pZoom);
			gc.clearRect(0, 0, 1280, 600);
			l.creerFigure(gc, tabf,c);
		});

		Translation t = new Translation();
		gauche.setOnAction(e->{
			try {
				Point[] tabp = t.creerPointsTranslate(cpt_translate++,0, file.getPoints());
				Face[] tabf = file.getFaces();
				
				r.recopiePoint(tabf, tabp);
				gc.clearRect(0, 0, 1280, 600);
				
				l.creerFigure(gc, tabf,c);
			} catch (MatriceNullException | MatriceFormatException e1) {
				e1.printStackTrace();
			}
			
		});
		
		droite.setOnAction(e->{
			try {
				Point[] tabp = t.creerPointsTranslate(cpt_translate--, 0, file.getPoints());
				Face[] tabf = file.getFaces();
				r.recopiePoint(tabf, tabp);
				gc.clearRect(0, 0, 1280, 600);
				l.creerFigure(gc, tabf,c);
			} catch (MatriceNullException | MatriceFormatException e1) {
				e1.printStackTrace();
			}
		});

		corps.getChildren().add(canv);

		Scene scene = new Scene(corps, 1280, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("i3D"); 
		primaryStage.setResizable(true);
		primaryStage.show();
	}

	public File getFichier() {
		return filePly;
	}

	public void setFichier(File fichier) {
		this.filePly = fichier;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void dessin(GraphicsContext gc, LoadFile file) throws IOException{
		gc.setLineWidth(1); //epaisseur des lignes
		try {
			file.creerPoints();
			file.creerFaces();
		} catch (WrongPointLineFormatException | WrongFaceLineFormatException e) {
			// TODO Auto-generated catch block
			System.exit(1);
		}
		l.creerFigure(gc, file.getFaces(),c);
	}
}
