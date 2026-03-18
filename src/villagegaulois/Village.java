package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		
		if(chef == null) {
			throw new VillageSansChefException("Ce village n'a pas de chef !");
		}
		
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder history = new StringBuilder();
		history.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " "+ produit+".\n");
		
		int indice = marche.trouverEtalLibre();
		if(indice >= 0) {
			marche.utiliserEtal(indice, vendeur, produit, nbProduit);
			history.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°"+ (indice+1)+".\n");
		}else {
			history.append("Le vendeur "  + vendeur.getNom() + "n'a pas de place pour s'installer.");
		}
		
		
		return history.toString();
	}	
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder history = new StringBuilder();
		Etal[] vendeurs = marche.trouverEtals(produit);
		
		if(vendeurs == null) {
			history.append("Il n'y a aucun vendeur qui propose des " + produit + " au marché.\n");
		}else if(vendeurs.length == 1){
			history.append("Seul le vendeur " + vendeurs[0].getVendeur().getNom() + " propose des " + produit + " au marché.\n");
		}else {
			history.append("Les vendeurs qui proposent des "+ produit+ " sont :\n");
			for(int i=0; i<vendeurs.length; ++i) {
				history.append("- "+vendeurs[i].getVendeur().getNom()+"\n");
			}
		}
		
		return history.toString();
	}
	
	
	
	
	
	
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	
	public String partirVendeur(Gaulois vendeur) {
		return rechercherEtal(vendeur).libererEtal();
		
	}
	public String afficherMarche() {
		return marche.afficherMarche();
	}
	
	
	// Internal Class
	private static class Marche {
		private Etal[] etals;
		public Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for(int i=0; i<nbEtals; ++i) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			//TODO : vérfier si indiceEtal est bien dans le tableau
			if(!(etals[indiceEtal].isEtalOccupe()) && indiceEtal >= 0 && indiceEtal < etals.length ) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}
		}
	
		private int trouverEtalLibre() {
			for(int i=0; i<etals.length; ++i) {
				if(!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			Etal[] tab;
			int nbEtals = 0;
			// Find exact number of Etal
			for(int i=0; i < etals.length; ++i) {
				if(etals[i].contientProduit(produit)) {
					++nbEtals;
				}
			}

			// Fill the table
			if(nbEtals == 0) {
				return null;
			}
			
			tab = new Etal[nbEtals];
			int indice = 0;
			for(int i=0; i < etals.length; ++i) {
				if(etals[i].contientProduit(produit)) {
					tab[indice] = etals[i];
					++indice;
				}
			}
			
			return tab;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0; i<etals.length; ++i) {
				// First check to avoid comparing a null string
				if(etals[i].isEtalOccupe() && etals[i].getVendeur().getNom() == gaulois.getNom()) {
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			StringBuilder result = new StringBuilder();
			int nbEtalsVides = 0;
			for(int i=0; i<etals.length; ++i) {
				if(etals[i].isEtalOccupe()) {
					result.append(etals[i].afficherEtal());
				}else {
					++nbEtalsVides;
				}
			}
			result.append("Il reste "+ nbEtalsVides + " étals non utilisés dans le marché.");
			return result.toString();
			
		}
		
		
	}
}

















