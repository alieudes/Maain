
public class Search {
	Collecteur col;
	RankPage rp;
public Search(String input, String meta, String dico, String fichierRank) {
	col = new Collecteur(meta, dico);
	rp = new RankPage(fichierRank);
	
	String[] splitArray = null; //tableau de chaînes
	  
	 
	  // On découpe la chaîne "str" à traiter et on récupère le résultat dans le tableau "splitArray"
	  splitArray = input.split(" ");
}
public static void main(String[] args) {
	
	
}
}
