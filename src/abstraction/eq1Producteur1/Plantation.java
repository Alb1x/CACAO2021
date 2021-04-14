package abstraction.eq1Producteur1;

import java.util.LinkedList;

/**
 * @author lebra
 */

public class Plantation {
	
	private LinkedList<Arbre> arbresmq;
	private LinkedList<Arbre> arbresmqe;
	private LinkedList<Arbre> arbresbq;
	
	public Plantation() {
		this.arbresmq = new LinkedList<Arbre>();
		this.arbresmqe = new LinkedList<Arbre>();
		this.arbresbq = new LinkedList<Arbre>();
			}
	
	public void maj_plantation(double new_mq, double new_mqe, double new_bq) {
		this.arbresmq.add(new Arbre(0,new_mq));
		this.arbresmqe.add(new Arbre(0,new_mqe));
		this.arbresbq.add(new Arbre(0,new_bq));
		for (Arbre arbre : this.arbresmq) {
			if (arbre.getAge() > 40*24) {
				this.arbresmq.remove(arbre);
			}
			arbre.augmenter_age();
		}
		for (Arbre arbre : this.arbresmqe) {
			if (arbre.getAge() > 40*24) {
				this.arbresmqe.remove(arbre);
			}
			arbre.augmenter_age();
		}
		for (Arbre arbre : this.arbresbq) {
			if (arbre.getAge() > 40*24) {
				this.arbresbq.remove(arbre);
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
