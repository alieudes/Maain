import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;


//pour l'executer : java tp2 etLeNomDuFicherDeStandford
public class Graphe {
	public ArrayList<Sommet> sommets;
	public int nbrSommet;
	public Graphe(String fich) throws IOException {

		remplissageMap(fich);
		nbrSommet=sommets.size();
	}

	public class Sommet {
		public int key;
		public HashSet<Sommet> al; // les voisins

		public Sommet(int key) {
			this.key = key;
			al = new HashSet<Sommet>();// faut instancier
		}

		public int getKey() {
			return this.key;
		}

		public HashSet<Sommet> getVoisins() {
			return this.al;
		}

		public void add(Sommet s) {
			this.al.add(s);
		}

		@Override
		public boolean equals(Object o) { // pour comparer les sommets
			return this.key == ((Sommet) o).key;
		}

		@Override
		public String toString() {
			return "id : " + this.getKey() + " nombre de voisins : " + this.getVoisins().size() + "\n";
		}
	}

	

	public String toString() {
		String res = "";
		for (Sommet s : sommets) {
			res += s.toString();
		}
		return res;
	}

	public void remplissageMap(String fich) throws IOException {
		sommets = new ArrayList<Sommet>();// instancier la liste de tous les sommets
		FileReader fr = new FileReader(fich);
		BufferedReader bf = new BufferedReader(fr);
		String ligne = "";
		String[] integers = new String[2];
		Integer a;
		Integer b;
		ligne = bf.readLine();
		while (ligne != null) {

			if (ligne.charAt(0) == '#') {
				System.out.println("a");
			} else {
				integers = ligne.split("\\s");
				a = Integer.parseInt(integers[0]);
				b = Integer.parseInt(integers[1]);
				
				Sommet s = new Sommet(a);
				if (!sommets.contains(s)) { // grace a override de equals pour Sommet ca passe meme si c'est pas la meme
											// adresse memoire
					sommets.add(s);
				} else {
					s = findSom(s.getKey());
				}
				Sommet t = new Sommet(b);
				if (!sommets.contains(t)) {
					sommets.add(t);
				} else {

					t = findSom(t.getKey());
				}
				if (!s.getVoisins().contains(t))
					s.add(t);
			}
			ligne = bf.readLine();

		}
		bf.close();

	}

	public Sommet findSom(int id) {
		for (Sommet s : this.sommets) {
			if (s.getKey() == id)
				return s;
		}
		return null;
	}

}
