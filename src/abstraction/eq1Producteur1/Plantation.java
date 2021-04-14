package abstraction.eq1Producteur1;

import java.util.LinkedList;

/**
 * @author lebra
 */

public class Plantation extends Producteur1Acteur {
	
	private LinkedList<Arbre> arbresmq;
	private LinkedList<Arbre> arbresmqe;
	private LinkedList<Arbre> arbresbq;
	
	
	/**
	 * On crée au départ une plantation qui correspond à notre production
	 */
	public Plantation() {
		this.arbresmq = new LinkedList<Arbre>();
		for (int i=0; i<=40*24; i++) {
			arbresmq.add(new Arbre(i,360600));
		}
		this.arbresmqe = new LinkedList<Arbre>();
		for (int i=0; i<=40*24; i++) {
			arbresmq.add(new Arbre(i,120200));
		}
		this.arbresbq = new LinkedList<Arbre>();
		for (int i=0; i<=40*24; i++) {
			arbresmq.add(new Arbre(i,320600));
		}
			}
	
	public void maj_plantation(double new_mq, double new_mqe, double new_bq) {
		if (new_mq!=0) {
			this.arbresmq.add(new Arbre(0,new_mq));
			this.getJournal(5).ajouter("On a planté "+new_mq+" arbres de moyenne qualité");
			
		}
		if (new_mqe!=0) {
			this.arbresmqe.add(new Arbre(0,new_mqe));
			this.getJournal(5).ajouter("On a planté "+new_mqe+" arbres de moyenne qualité équitables");
		}
		if (new_bq!=0) {
			this.arbresbq.add(new Arbre(0,new_bq));
			this.getJournal(5).ajouter("On a planté "+new_bq+" arbres de basse qualité");
		}
		
		for (Arbre arbre : this.arbresmq) {
			if (arbre.getAge() > 40*24) {
				this.arbresmq.remove(arbre);
				this.getJournal(5).ajouter(arbre.getNombre_arbre()+" arbres de moyenne qualité sont morts");
			}
			arbre.augmenter_age();
		}
		for (Arbre arbre : this.arbresmqe) {
			if (arbre.getAge() > 40*24) {
				this.arbresmqe.remove(arbre);
				this.getJournal(5).ajouter(arbre.getNombre_arbre()+" arbres de moyenne qualité équitables sont morts");
			}
			arbre.augmenter_age();
		}
		for (Arbre arbre : this.arbresbq) {
			if (arbre.getAge() > 40*24) {
				this.arbresbq.remove(arbre);
				this.getJournal(5).ajouter(arbre.getNombre_arbre()+" arbres de basse qualité sont morts");
			}
			arbre.augmenter_age();
		}
	}
	
	public double production_mq () {
		double prod = 0;
		for (Arbre arbre : this.arbresmq) {
			prod += arbre.getNombre_arbre() * arbre.getRendement();
		}
		return prod;
	}
	
	public double production_mqe () {
		double prod = 0;
		for (Arbre arbre : this.arbresmqe) {
			prod += arbre.getNombre_arbre() * arbre.getRendement();
		}
		return prod;
	}
	
	public double production_bq () {
		double prod = 0;
		for (Arbre arbre : this.arbresbq) {
			prod += arbre.getNombre_arbre() * arbre.getRendement();
		}
		return prod;
	}

}
