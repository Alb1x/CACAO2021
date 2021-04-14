package abstraction.eq1Producteur1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;
import abstraction.fourni.Filiere;
import abstraction.fourni.IActeur;
import abstraction.fourni.Journal;
import abstraction.fourni.Variable;

public class Producteur1Acteur implements IActeur {
	private int cryptogramme;
	private Color couleur = new Color(26, 188, 156);
	private Stock stock_F_M_E;
	private Stock stock_F_M;
	private Stock stock_F_B;
	private Stock stock_P_M_E;
	private Stock stock_P_M;
	private double new_mqe;
	private double new_mq;
	private double new_bq;
	private Plantation plantation;
	private Transformation transformation;
	protected HashMap<Object, Stock> stocks; //dictionnaire qui contient tous nos stocks.
	protected int step_actuel;
	private List<VenteAO> historique_AO_F_M; //historique des appels d'offre pour les fèves de moyenne qualité non équitable.(0.0 : pas de vente, !=0 : vente à ce prix.)
	private List<VenteAO> historique_AO_F_B; //historique des appels d'offre pour les fèves de basse qualité non équitable. idem
	protected HashMap<Feve,List<VenteAO>> historiques; //dictionnaire qui contient les historiques de ventes par AO.
	protected JournauxEq1 journaux;
	
	public Producteur1Acteur() {
		this.init_stocks(this);
		this.init_historiques();
		this.step_actuel = 0;
		this.init_journaux();
		this.plantation= new Plantation();
	}
	
	public Journal getJournal(int i) {
		return journaux.getJournal(i);
	}

	public void initialiser() {
		transformation = new Transformation();

	}
	/**
	 * @author Alb1x
	 * On crée un stock pour chaque chose que l'on produit.
	 * On range ensuite les stock dans un dictionnaire stocks.
	 */
	private void init_stocks(IActeur a) {
		this.stock_F_M_E = new Stock("Stock F_M_E",0, a); //stock que l'on possède au départ
		this.stock_F_M = new Stock("Stock F_M",0, a);
		this.stock_F_B = new Stock("Stock F_B",0, a);
		this.stock_P_M_E = new Stock("Stock P_M_E",0, a);
		this.stock_P_M = new Stock("Stock P_M",0, a);
		this.stocks = new HashMap<Object, Stock>();
		this.stocks.put(Feve.FEVE_MOYENNE_EQUITABLE, stock_F_M_E);
		this.stocks.put(Feve.FEVE_MOYENNE, stock_F_M);
		this.stocks.put(Feve.FEVE_BASSE, stock_F_B);
		this.stocks.put(Chocolat.POUDRE_MOYENNE_EQUITABLE, stock_P_M_E);
		this.stocks.put(Chocolat.POUDRE_MOYENNE, stock_P_M);
	}
	/**
	 * @author Alb1x
	 * On crée un historique de vente par AO pour chaque fève que l'on vend par AO.
	 * On range ensuite les historiques dans un dictionnaire historiques.
	 */
	private void init_historiques() {
		this.historique_AO_F_M  = new ArrayList<VenteAO>();
		this.historique_AO_F_B  = new ArrayList<VenteAO>();
		this.historiques = new HashMap<Feve,List<VenteAO>>();
		this.historiques.put(Feve.FEVE_MOYENNE, this.historique_AO_F_M);
		this.historiques.put(Feve.FEVE_BASSE, historique_AO_F_B);
	}
	/**
	 * @author Alb1x
	 */
	private void stepSuivant() {
		this.step_actuel += 1;
	}

	/**
	 * @author Alb1x
	 * On rajoute une vente non conclue, cela sera changé si une vente est conclue au cours du step.
	 */
	private void majHist_AO() {
		this.historique_AO_F_M.add(new VenteAO());
		this.historique_AO_F_B.add(new VenteAO());
	}
	/**
	 * @author Alb1x
	 * @author lebra pour l'ajout dans le journal
	 */
	private void produireFeve() {
		double p_mqe = this.plantation.production_mqe();
		double p_mq = this.plantation.production_mq();
		double p_bq = this.plantation.production_bq();
		this.stocks.get(Feve.FEVE_MOYENNE_EQUITABLE).addQuantite(p_mqe);
		this.stocks.get(Feve.FEVE_MOYENNE).addQuantite(p_mq);
		this.stocks.get(Feve.FEVE_BASSE).addQuantite(p_bq);
		this.journaux.getJournal(0).ajouter("Ajout de "+p_mqe+" fèves de qualité moyenne équitable");
		this.journaux.getJournal(0).ajouter("Ajout de "+p_mq+ " fèves de qualité moyenne");
		this.journaux.getJournal(0).ajouter("Ajout de "+p_bq+ " fèves de qualité basse");
		
	}
	
	private void init_journaux() {
		this.journaux = new JournauxEq1();
		this.journaux.addJournal("Ghanao Production", this);
		this.journaux.getJournal(0).ajouter(couleur, Color.black, "==== Journal de la production ===");
		this.journaux.addJournal("Ghanao Transformation", this);
		this.journaux.getJournal(1).ajouter(couleur, Color.black,"==== Journal de la transformation ===");
		this.journaux.addJournal("Ghanao VenteAO", this);
		this.journaux.getJournal(2).ajouter(couleur, Color.black,"==== Journal des ventes par offre d'achat ===");
		this.journaux.addJournal("Ghanao VenteContratCadre", this);
		this.journaux.getJournal(3).ajouter(couleur, Color.black,"==== Journal des ventes par contrat cadre ===");
		this.journaux.addJournal("Ghanao coûts de stockage", this);
		this.journaux.getJournal(4).ajouter(couleur, Color.black,"==== Journal des coûts de stockage ===");
		this.journaux.addJournal("Ghanao Plantation", this);
		this.journaux.getJournal(5).ajouter(couleur, Color.black,"==== Journal de la plantation ===");
	}
	
	protected HashMap<Object, Stock> getStocks() {
		return stocks;
	}

	public String getNom() {
		return "EQ1";
	}

	public String getDescription() {
		return "Producteur Ghanao";
	}

	public Color getColor() {
		return couleur;
	}


	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}


	public void next() {
		this.stepSuivant();
		this.majHist_AO();
		this.produireFeve();
		Cout.cout(this); // coût proportionel à la qualité et à la quantité de fèves produites
		this.transformation.Transformation_Feve(this);
		this.plantation.maj_plantation(new_mq, new_mqe, new_bq);
	}

	public List<String> getNomsFilieresProposees() {
		return new ArrayList<String>();
	}

	public Filiere getFiliere(String nom) {
		return null;
	}

	public List<Variable> getIndicateurs() {
		List<Variable> res=new ArrayList<Variable>();
		for (Stock stck : stocks.values()) { //On ajoute les valeurs des stocks.
			res.add(stck.getVariable());
		}
		return res;
	}

	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res; 
	}

	public List<Journal> getJournaux() {
		return journaux.getJournaux();
	}

	public void notificationFaillite(IActeur acteur) {
		if (this==acteur) {
			System.out.println("I'll be back... or not... "+this.getNom());
		} else {
			System.out.println("Poor "+acteur.getNom()+"... We will miss you. "+this.getNom());
		}
	}

	public void notificationOperationBancaire(double montant) {
	}

	// Renvoie le solde actuel de l'acteur
	public double getSolde() {
		return Filiere.LA_FILIERE.getBanque().getSolde(this, this.cryptogramme);
	}
	
	/**
	 * @author arthurlemgit
	 * Pour les coûts fixes et de transformation
	 */
	protected void perteargent(double quantite) {
		if (quantite>0) {
			Filiere.LA_FILIERE.getBanque().virer(Filiere.LA_FILIERE.getActeur("EQ1"), this.cryptogramme, Filiere.LA_FILIERE.getBanque(),quantite );
		}
	}
} 