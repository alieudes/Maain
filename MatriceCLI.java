import java.io.IOException;

public class MatriceCLI {
	Matrice mat;
	float [] C;
	int [] I;
	int [] L;
	
	public MatriceCLI(Matrice mat) {
		this.mat=mat;
		int lengthC=0;
		for (int i = 0; i < mat.g.sommets.size(); i++) {
			lengthC+=mat.g.sommets.get(i).al.size();
		}
		int longL = mat.n+1;
		this.C=new float[lengthC];
		this.I=new int[lengthC];
		this.L= new int[longL];
		L[mat.n]=lengthC;
		int tmp=0;
		int tmp2=0;
		boolean test=true;
		for (int i = 0; i < mat.n; i++) {
			for (int j = 0; j < mat.n; j++) {
				if(mat.mat[i][j]!=0) {
					C[(lengthC-lengthC)+tmp]=mat.mat[i][j];
					if(test) {
						L[(longL-longL)+tmp2]=(lengthC-lengthC)+tmp;
						tmp2++;
						test=false;
					}
						
					I[(lengthC-lengthC)+tmp]=j;
					tmp++;
				}
					
			}
			if(test) {
				tmp2++;
			}
			else
				test=true;
		}
		for (int i = 1; i < longL; i++) {
			if(L[i]==0)
				L[i]=L[i+1];
		}
		
		for (int i = 0; i < lengthC; i++) {
			System.out.println(C[i]);
		}
		System.out.println("-------------------------");
		for (int i = 0; i < longL; i++) {
			System.out.println(L[i]);
		}
		System.out.println("-------------------------");
		for (int i = 0; i < lengthC; i++) {
			System.out.println(I[i]);
		}
	}
	
	public static void main(String[] args) {
		String fichier = args[0];
		Graphe g;
		MatriceCLI matCLI;
		try {
			
			g = new Graphe(fichier);
			Matrice m = new Matrice(g);
			m.creerMatrice();
			/*
			for(int i=0; i<g.nbrSommet; i++) {
				for(int j=0; j<g.nbrSommet; j++) {
					System.out.print(m.mat[i][j]+" ");
				}
				System.out.println();
			}*/
			matCLI =new MatriceCLI(m);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
