import java.io.IOException;

public class Matrice {
	int n;
	Float mat[][];
	Graphe g;

	public Matrice(Graphe g) {
		this.n = g.nbrSommet;
		this.mat = new Float[n][n];
		this.g = g;
	}

	public void creerMatrice() {
		for (Graphe.Sommet s : this.g.sommets) {
			for (Graphe.Sommet t : s.al) {
				System.out.println("degré sortant du sommet "+s.key+" : "+s.al.size());
				mat[s.key][t.key] =  ((float)1 / (s.al.size()));// on met à l'emplacement de la matrice 1/degre Sortant
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (mat[i][j] == null) {
					mat[i][j] = (float) 0;
				}
			}
		}
	}

	public static void main(String[] args) {
		String fichier = args[0];
		Graphe g;
		try {
			g = new Graphe(fichier);
			Matrice m = new Matrice(g);
			m.creerMatrice();
			for(int i=0; i<g.nbrSommet; i++) {
				for(int j=0; j<g.nbrSommet; j++) {
					System.out.print(m.mat[i][j]+" ");
				}
				System.out.println();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
