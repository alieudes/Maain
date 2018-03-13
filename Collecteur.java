import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Collecteur {
	HashMap<String, ArrayList<Integer>> dictionnaire = new HashMap<String, ArrayList<Integer>>();
	ArrayList<Integer> arr = null;

	public Collecteur(String meta, String dico) {
		FileReader fr;
		String ligne = "";
		try {
			fr = new FileReader(dico);
			BufferedReader bf = new BufferedReader(fr);
			ligne = bf.readLine();
			while (ligne != null) {
				if (dictionnaire.get(ligne) == null) {
					arr = new ArrayList<Integer>();
					dictionnaire.put(ligne, arr);
				}

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
		Integer id = null;
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
				if (mots[0].equals("Id:")) {
					id = Integer.parseInt(mots[3]);
				}
				if (mots[0].equals("title:")) {
					for (int i = 1; i < mots.length; i++) {
						String[] result = mots[i].split("");
						if (result[result.length - 1].equals(":"))
							mots[i] = mots[i].substring(0, mots[i].length() - 1);
						if (dictionnaire.get(mots[i].toLowerCase()) != null) {
							dictionnaire.get(mots[i].toLowerCase()).add(id);
						}

					}

				}
				ligne = bf.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator it = dictionnaire.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			arr = (ArrayList<Integer>) pair.getValue();
			
			if (arr.size() > 0) {
				System.out.print(pair.getKey());
				System.out.print(" : ");
				for (Integer i : arr) {
					System.out.print(i + ",");
				}
				
				System.out.println("------------------");

				
			}

			it.remove(); // avoids a ConcurrentModificationException
		}

	}

	public static void main(String[] args) {
		String meta = args[0];
		String dico = args[1];
		Collecteur col = new Collecteur(meta, dico);
	}
}
