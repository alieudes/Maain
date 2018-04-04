import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
		boolean cate = false;
		int ligneCate = 0;
		String ligne = "";
		try {
			fr = new FileReader(meta);
			BufferedReader bf = new BufferedReader(fr);
			ligne = bf.readLine();
			while (ligne != null) {
				if (cate) {
					ligne = ligne.replaceAll("^\\s+", "");
					mots = new String[ligne.split("\\|").length];
					mots = ligne.split("\\|");
					for (int i = 3; i < mots.length; i++) {
						char[] c = new char[mots[i].split("|").length];
						c = mots[i].toCharArray();
						for (int j = 0; j < c.length; j++) {
							if (c[j] == '[') {
								mots[i] = mots[i].substring(0, j);
							}
							if (dictionnaire.get(mots[i].toLowerCase()) != null
									&& !dictionnaire.get(mots[i].toLowerCase()).contains(id)) {
								dictionnaire.get(mots[i].toLowerCase()).add(id);
							}
						}
						for (int j = 0; j < c.length; j++) {
							if (c[j] == '&') {
								String[] m = new String[mots[i].split(" ").length];
								m = mots[i].split(" ");
								for (int k = 0; k < m.length; k++) {
									if (dictionnaire.get(m[k].toLowerCase()) != null
											&& !dictionnaire.get(m[k].toLowerCase()).contains(id)) {
										dictionnaire.get(m[k].toLowerCase()).add(id);
									}
								}
							}
						}
					}

					ligneCate--;
					if (ligneCate == 0)
						cate = false;
				}
				ligne = ligne.replaceAll("^\\s+", "");
				mots = new String[ligne.split("\\s").length];
				mots = ligne.split("\\s");

				if (mots[0].equals("categories:")) {
					cate = true;
					ligneCate = Integer.parseInt(mots[1]);
				}
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
		File fichier = new File("Resultat.ASCII");
		try {
			FileWriter fw = new FileWriter(fichier);

			Iterator it = dictionnaire.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				arr = (ArrayList<Integer>) pair.getValue();

				if (arr.size() > 0) {
					
					fw.write((String)pair.getKey()+" : ");
					fw.flush();
					System.out.print(pair.getKey()+" : ");
					for (Integer i : arr) {
						System.out.print(i+",");
						fw.write(i.toString());
						fw.flush();
						fw.write(",");
						fw.flush();
					}
					System.out.println();
					fw.write("\n");
					fw.flush();
				}
				
				

				it.remove();
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		String meta = args[0];
		String dico = args[1];
		Collecteur col = new Collecteur(meta, dico);
	}
}
