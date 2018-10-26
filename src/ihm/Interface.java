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
import exception.NoFileSelectedException;
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
 * Classe reprÃƒÂ©sentant la fenÃƒÂªtre graphique et tous les ÃƒÂ©lÃƒÂ©ments graphiques contenus dedans.
 * @author bascopa & clarissa
 */
public class Interface extends Application {

	/**
	 * Permet de charger le fichier, de le lire sÃƒÂ©quentiellement afin de ranger les points et les faces dans des tableaux.
	 */
	private LoadFile file;

	/**
	 * le fichier .ply contenant les points et les faces ÃƒÂ  dessiner.
	 */
	private File filePly;

	/**
	 * InterprÃƒÂ¨te le LoadFile pour crÃƒÂ©er les points et les faces, trier les faces et ainsi crÃƒÂ©er la figure.
	 */
	private Initialisation l;

	/**
	 * Compteur de translation gauche-droite. On l'incrÃƒÂ©mente lors de l'appui sur le bouton droite et on le dÃƒÂ©crÃƒÂ©mente lors de l'appui sur le bouton gauche pour dÃƒÂ©placer la figure horizontalement.
	 */
	private int cpt_translate_gd=0;

	/**
	 * Compteur de translation haut-bas. On l'incrÃƒÂ©mente lors de l'appui sur le bouton haut et on le dÃƒÂ©crÃƒÂ©mente lors de l'appui sur le bouton bas pour dÃƒÂ©placer la figure verticalement.
	 */
	@SuppressWarnings("unused")
	private int cpt_translate_hb=0;

	/**
	 * Couleur de la figure initialisÃƒÂ©e ÃƒÂ  blanche. Elle sera modifiÃƒÂ©e grÃƒÂ¢ce au colorpicker par l'utilisateur.
	 */
	private Color c = Color.WHITE;

	/**
	 * MÃƒÂ©thode d'affichage de l'interface graphique.
	 */

	Face[] tabf;
	Point[] tabp;
	public void start(Stage primaryStage){
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
		zoom.setMax(5);
		zoom.setValue(1);
		zoom.setMajorTickUnit(1);
		zoom.setBlockIncrement(0.2);
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
		Button haut = new Button("Ã¢â€ â€˜");
		Button gauche = new Button("Ã¢â€ ï¿½");
		Button droite = new Button("Ã¢â€ â€™");
		Button bas = new Button("Ã¢â€ â€œ");
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
			if(filePly==null) {
				try {
					throw new NoFileSelectedException();
				} catch (NoFileSelectedException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(),"Erreur Selection Fichier",JOptionPane.ERROR_MESSAGE);
				}	
			}
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


		tournerX.setOnMouseDragged(e-> {
			if(filePly!=null) {
				miseAJourVue(gc, tournerX.getValue(), tournerY.getValue(), tournerZ.getValue(), zoom.getValue());
			}
		});

		tournerY.setOnMouseDragged(e ->{
			if(filePly!=null) {
				miseAJourVue(gc, tournerX.getValue(), tournerY.getValue(), tournerZ.getValue(), zoom.getValue());
			}

		});

		tournerZ.setOnMouseDragged(e -> {
			if(filePly!=null) {
				miseAJourVue(gc, tournerX.getValue(), tournerY.getValue(), tournerZ.getValue(), zoom.getValue());
			}
		});

		zoom.setOnMouseDragged(e -> {
			if(filePly!=null) {
				miseAJourVue(gc, tournerX.getValue(), tournerY.getValue(), tournerZ.getValue(), zoom.getValue());
			}
		});

		gauche.setOnAction(e->{
			cpt_translate_gd -= 10;
			miseAJourVue(gc, tournerX.getValue(), tournerY.getValue(), tournerZ.getValue(), zoom.getValue());
		});

		droite.setOnAction(e->{
			cpt_translate_gd += 10;
			miseAJourVue(gc, tournerX.getValue(), tournerY.getValue(), tournerZ.getValue(), zoom.getValue());
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
	 * DÃƒÂ©finit le fichier .ply ÃƒÂ  utiliser
	 * @param fichier
	 */
	public void setFichier(File fichier) {
		this.filePly = fichier;
	}

	/**
	 * MÃƒÂ©thode lanÃƒÂ§ant le programme.
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

	/**
	 * Dessine la figure ÃƒÂ  partir de la crÃƒÂ©ation dans Initialisation.
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

	/**
	 * 
	 * @param gc
	 * @param xvalue
	 * @param zoomvalue
	 */
	public void miseAJourVue(GraphicsContext gc, double xvalue, double yvalue, double zvalue, double zoomvalue) {
		Translation t = new Translation();
		Rotation r = new Rotation();
		Face[] tabf = file.getFaces();
		Point[] tabp = file.getPoints();
		Zoom z = new Zoom();

		try {
			tabp = r.creerPointrotate(xvalue, tabp, 1);
		} catch (MatriceNullException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MatriceFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		r.recopiePoint(tabf, tabp);

		try {
			tabp = r.creerPointrotate(yvalue, tabp, 0);
		} catch (MatriceNullException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MatriceFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		r.recopiePoint(tabf, tabp);

		try {
			tabp = r.creerPointrotate(zvalue, tabp, 2);
		} catch (MatriceNullException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MatriceFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		r.recopiePoint(tabf, tabp);
		
		try {
			tabp = z.creerPointZoom(zoomvalue, tabp);
		} catch (MatriceNullException | MatriceFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		r.recopiePoint(tabf, tabp);
		
			try {
				tabp = t.creerPointsTranslate(cpt_translate_gd,0, tabp);
			} catch (MatriceNullException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MatriceFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			r.recopiePoint(tabf, tabp);	
		
		gc.clearRect(0, 0, 1280, 600);
		l.creerFigure(gc, tabf,c);
	}

}