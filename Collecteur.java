import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Collecteur {
	HashMap<String, Integer> dictionnaire = new HashMap<String, Integer>();

	public Collecteur(String meta, String dico) {
		FileReader fr;
		String ligne = "";
		try {
			fr = new FileReader(dico);
			BufferedReader bf = new BufferedReader(fr);
			ligne = bf.readLine();
			while (ligne != null) {
				if (dictionnaire.get(ligne) == null)
					dictionnaire.put(ligne, -1);
				ligne = bf.readLine();
			}
		} catch (IOException e) {
			System.out.println("fichier non trouvé");
			e.printStackTrace();
		}
		lireMeta(meta);
	}

	public void lireMeta(String meta) {
		String[] mots;
		Integer id=null;
		FileReader fr;
		String ligne = "";
		try {
			fr = new FileReader(meta);
			BufferedReader bf = new BufferedReader(fr);
			ligne = bf.readLine();
			while (ligne != null) {
				ligne = ligne.replaceAll("^\\s+", "");
				mots = new String[ligne.split("\\s").length];
				mots = ligne.split("\\s");
				if(mots[0].equals("Id:")) {
					id=Integer.parseInt(mots[1]);
				}
				if (mots[0].equals("title:")) {
					for (int i = 1; i < mots.length; i++) {
						String[] result = mots[i].split("");
						if (result[result.length - 1].equals(":"))
							mots[i] = mots[i].substring(0, mots[i].length() - 1);
						if(dictionnaire.get(mots[i].toLowerCase())==-1)
							dictionnaire.put(mots[i].toLowerCase(), id);
					}
					
				}
				ligne = bf.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Entry<String, Integer> entry : dictionnaire.entrySet()) {
		    String cle = entry.getKey();
		    Integer valeur = entry.getValue();
		    // traitements
		    System.out.println(cle);
		    System.out.println("----------");
		    System.out.print(valeur);
		}

	}

	public static void main(String[] args) {
		String meta = args[0];
		String dico = args[1];
		Collecteur col = new Collecteur(meta, dico);
	}
}
