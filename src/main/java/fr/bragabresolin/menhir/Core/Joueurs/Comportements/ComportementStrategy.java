package fr.bragabresolin.menhir.Core.Joueurs.Comportements;

import java.util.*;
import fr.bragabresolin.menhir.Core.Joueurs.Joueur;
import fr.bragabresolin.menhir.Core.Saison;
import fr.bragabresolin.menhir.Core.Cartes.CarteIngredient;
import fr.bragabresolin.menhir.Core.Cartes.CarteAllie;

public interface ComportementStrategy {

	public abstract CarteAllie choisirCarteAllie(Joueur joueur, Saison saisonActuelle, ArrayList<Joueur> contexte,
			ArrayList<CarteAllie> cartes);

	public abstract CarteIngredient choisirCarteIngredient(Joueur joueur, Saison saisonActuelle,
			ArrayList<Joueur> contexte, ArrayList<CarteIngredient> cartes);

}
