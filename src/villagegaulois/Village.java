package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
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
	
	// TP starts here
	private static class Marche {
		private Etal[] etals;
		public Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for(int i=0; i<nbEtals; ++i) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if(!(etals[indiceEtal].isEtalOccupe())) {
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
		
		private String afficherMarcher() {
			String result = "";
			int nbEtalsVides = 0;
			for(int i=0; i<etals.length; ++i) {
				if(etals[i].isEtalOccupe()) {
					result += etals[i].afficherEtal();
				}else {
					++nbEtalsVides;
				}
			}
			result += "Il reste "+ nbEtalsVides + " étals non utilisés dans le marché.";
			return result;
			
		}
		
		
	}
}

















