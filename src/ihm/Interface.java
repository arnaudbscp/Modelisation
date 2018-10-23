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
import exception.WrongFormatFileException;
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
 * Classe représentant la fenêtre graphique et tous les éléments graphiques contenus dedans.
 * @author bascopa & clarissa
 */
public class Interface extends Application {

	/**
	 * Permet de charger le fichier, de le lire séquentiellement afin de ranger les points et les faces dans des tableaux.
	 */
	private LoadFile file;
	/**
	 * le fichier .ply contenant les points et les faces à dessiner.
	 */
	private File filePly;
	/**
	 * Interprète le LoadFile pour créer les points et les faces, trier les faces et ainsi créer la figure.
	 */
	private Initialisation l;
	/**
	 * Compteur de translation gauche-droite. On l'incrémente lors de l'appui sur le bouton droite et on le décrémente lors de l'appui sur le bouton gauche pour déplacer la figure horizontalement.
	 */
	@SuppressWarnings("unused")
	private int cpt_translate_gd=0;
	/**
	 * Compteur de translation haut-bas. On l'incrémente lors de l'appui sur le bouton haut et on le décrémente lors de l'appui sur le bouton bas pour déplacer la figure verticalement.
	 */
	@SuppressWarnings("unused")
	private int cpt_translate_hb=0;
	/**
	 * Couleur de la figure initialisée à blanche. Elle sera modifiée grâce au colorpicker par l'utilisateur.
	 */
	private Color c = Color.WHITE;

	/**
	 * Méthode d'affichage de l'interface graphique.
	 */
	
	Face[] tabf;
	Point[] tabp;
	public void start(Stage primaryStage) throws Exception {
		//ELEMENTS GRAPHIQUES
		HBox corps = new HBox();
		Scene scene = new Scene(corps, 1280, 600);
		VBox menu = new VBox();
		menu.setMinWidth(150);
		Button b1 = new Button("Importer");
		b1.setMinWidth(150);
		menu.getChildren().add(b1);
		Canvas canv = new Canvas(1100,600);
		GraphicsContext gc = canv.getGraphicsContext2D();
		HBox.setMargin(menu, new Insets(50, 0, 0, 20));
		FileChooser importer = new FileChooser();
		VBox dessin = new VBox();
		Separator sep = new Separator();
		dessin.getChildren().add(sep);
		corps.getChildren().add(menu);
		corps.getChildren().add(dessin);
		sep.setOrientation(Orientation.VERTICAL);
		sep.setValignment(VPos.CENTER);
		//A modifier
		sep.setMinHeight(300);
		sep.setStyle("-fx-padding : 0 0 0 30;");
		corps.getChildren().add(canv);

		//SLIDER ZOOM
		Slider zoom = new Slider();
		Label lZoom = new Label();
		lZoom.setText("Zoomer");
		lZoom.setStyle("-fx-padding : 20 0 0 50;");
		zoom.setMin(0);
		zoom.setMax(100);
		zoom.setValue(50);
		zoom.setShowTickLabels(true);
		menu.getChildren().addAll(lZoom, zoom);

		//SLIDER TRANSLATION X
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

		//SLIDER TRANSLATION Y
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

		//SLIDER TRANSLATION Z
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

		//CROIX DIRECTIONNELLE TRANSLATION
		Label ltranslation = new Label("Translation");
		HBox hb_haut = new HBox();
		HBox hb_gauche_droite = new HBox();
		HBox hb_bas = new HBox();
		Button haut = new Button("↑");
		Button gauche = new Button("←");
		Button droite = new Button("→");
		Button bas = new Button("↓");
		hb_haut.setPadding(new Insets(0,0,0,28));
		hb_bas.setPadding(new Insets(0,0,0,28));
		hb_gauche_droite.setSpacing(28);
		haut.setPrefWidth(25);
		bas.setPrefWidth(25);
		droite.setPrefWidth(25);
		gauche.setPrefWidth(25);
		hb_haut.getChildren().add(haut);
		hb_gauche_droite.getChildren().addAll(gauche,droite);
		hb_bas.getChildren().add(bas);
		menu.getChildren().addAll(ltranslation,hb_haut,hb_gauche_droite,hb_bas);

		//CHOIX COULEURS
		Label lcolor = new Label("Couleur");
		ColorPicker cp = new ColorPicker();
		menu.getChildren().addAll(lcolor,cp);


		//---------------------GESTION DES EVENEMENTS------------------


		importer.setTitle("Selectionner un fichier 3D");
		b1.setOnAction(e -> {
			filePly = importer.showOpenDialog(primaryStage);
			String extension = "";
			int i=0;
			while (filePly.getPath().charAt(i) != '.')
				i++;
			extension = filePly.getPath().substring(i, filePly.getPath().length());	

			try {
				if (!extension.equals(".ply"))
					throw new WrongFormatFileException();
				else {
					gc.clearRect(0, 0, 1600, 800);
					file = new LoadFile(filePly);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (WrongFormatFileException e2) {
				JOptionPane.showMessageDialog(null, e2.toString(), "Erreur Format Fichier", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			l = null;
			try {
				l = new Initialisation(filePly);
				tabp = file.getPoints();
				tabf = file.getFaces();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				dessin(gc, file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
		});

		cp.setOnAction(e ->{
			if(filePly!=null) {
				c = cp.getValue();
				l.creerFigure(gc, file.getFaces(), c);
			}
		});

		//Déplacement dans l'action du bouton importer
		//dessin(gc, file, g, l);
		Rotation r = new Rotation();
		Translation t = new Translation();
		tournerX.setOnMouseDragged(e-> {
			if(filePly!=null) {
				try {
					tabp = r.creerPointrotate(tournerX.getValue(), file.getPoints(), 0);
				} catch (MatriceNullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (MatriceFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				r.recopiePoint(tabf, tabp);
				gc.clearRect(0, 0, 1280, 600);
				l.creerFigure(gc, tabf,c);
			}
				
		});
		
		tournerY.setOnMouseDragged(e ->{
			if(filePly!=null) {
				try {
					tabp = r.creerPointrotate(tournerY.getValue(), file.getPoints(), 1);
				} catch (MatriceNullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (MatriceFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				r.recopiePoint(tabf, tabp);
				gc.clearRect(0, 0, 1280, 600);
				l.creerFigure(gc, tabf,c);
			}
		});
		
		tournerZ.setOnMouseDragged(e -> {
			if(filePly!=null) {
				try {
					tabp = r.creerPointrotate(tournerZ.getValue(), file.getPoints(), 2);
				} catch (MatriceNullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (MatriceFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				r.recopiePoint(tabf, tabp);
				gc.clearRect(0, 0, 1280, 600);
				l.creerFigure(gc, tabf,c);
			}
		});

		zoom.setOnMouseDragged(e -> {
			if(filePly!=null) {
				for (int i=0; i<file.getPoints().length; i++) {
					Point p = new Point((float)(file.getPoints()[i].getX()*(zoom.getValue()/file.getCoordMax(0))*10), (float)(file.getPoints()[i].getY()*(zoom.getValue()/file.getCoordMax(1))*10), (float)(file.getPoints()[i].getZ()*(zoom.getValue()/file.getCoordMax(2))*10));
					tabp[i] = p;
				}
				r.recopiePoint(tabf, tabp);
				gc.clearRect(0, 0, 1280, 600);
				l.creerFigure(gc, tabf,c);
			}
				
		});

		gauche.setOnAction(e->{
			if(filePly!=null)
				try {
					tabp = t.creerPointsTranslate(cpt_translate_gd+=10,0, tabp);
				} catch (MatriceNullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (MatriceFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				r.recopiePoint(tabf, tabp);
				gc.clearRect(0, 0, 1280, 600);
				l.creerFigure(gc, tabf,c);
		});

		droite.setOnAction(e->{
			if(filePly!=null)
				try {
					tabp = t.creerPointsTranslate(cpt_translate_gd-=10, 0, tabp);
				} catch (MatriceNullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (MatriceFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				r.recopiePoint(tabf, tabp);
				gc.clearRect(0, 0, 1280, 600);
				l.creerFigure(gc, tabf,c);
		});

		//----AFFICHAGE FENETRE------
		primaryStage.setScene(scene);
		primaryStage.setTitle("i3D"); 
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	/**
	 * Retourne le fichier .ply
	 * @return
	 */
	public File getFichier() {
		return filePly;
	}

	/**
	 * Définit le fichier .ply à utiliser
	 * @param fichier
	 */
	public void setFichier(File fichier) {
		this.filePly = fichier;
	}

	/**
	 * Méthode lançant le programme.
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

	/**
	 * Dessine la figure à partir de la création dans Initialisation.
	 * @param gc
	 * @param file
	 * @throws IOException
	 */
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