package chargement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoadFile {
	File fichier;
	BufferedReader br;
	public LoadFile(){
		try {
			fichier = new File("ressources/dolphin.ply");
			br = new BufferedReader(new FileReader(fichier));
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier n'a pas été trouvé...");
			e.printStackTrace();
		}
	}
	
	public void CreerPoints() throws IOException {
		br.readLine();
		br.readLine();
		float[] points = new float[RecupNbPoints(br.readLine())];
		for(int i=0;i<6;i++) {
			br.readLine();
		}
		for(int j=0;j<points.length;j++) {
		//	points[j]=new Point(x, y, z)    EN COURS
		}
	}
	
	public int RecupNbPoints(String ligne) {
		return Integer.parseInt(ligne.substring(15, ligne.length()));
	}
	
//	public static void main(String[] args) throws IOException {
//		LoadFile file = new LoadFile();
//		file.CreerPoints();
//	}
}
