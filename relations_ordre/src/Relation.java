/** Classe Relation héritant de RelationDeBase
	 Fournit des outils de manipulation des relations entre sous-ensembles de l'Univers
 */

import java.util.*;

public class Relation extends RelationDeBase {

	/** Valeur numérique de MAXELT */
	private static final int MAX = Elt.MAXELT.val();

	/** Construit la Relation vide sur l'ensemble vide */
	public Relation() {
		super();
	}

	/** Construit la Relation vide de d vers a */
	public Relation(EnsembleAbstrait d, EnsembleAbstrait a) {
		super(d, a);
	}

	/** Clone */
	public Relation clone() {
		return (Relation) super.clone();
	}
	
	//Ex1
	//renvoie le domaine de la relation courante
	public EnsembleAbstrait domaine() {
		Ensemble domaine = new Ensemble();
		for(Couple c:this){
			domaine.ajouter(c.getX());
		}
		return domaine;
	}
	
	//renvoie l'image de la relation courante
	public EnsembleAbstrait image() {
		Ensemble image = new Ensemble();
		for(Couple c:this){
			image.ajouter(c.getY());
		}
		return image;
	}
	
	// EX 2
	// renvoie la complémentaire de la relation courante
	public Relation complementaire() {
		Relation comp=new Relation(this.depart(),this.arrivee());
		Ensemble depart= (Ensemble) comp.depart();
		Ensemble arrivee= (Ensemble) comp.arrivee();
		for (Elt x: this.depart()) {
			for (Elt y:this.arrivee()){
				Couple couple=new Couple(x,y);
				if(!this.contient(couple)){
					comp.ajouter(couple);
				}
			}

		}

		return comp;
	}

	// renvoie la réciproque de la relation courante
	public Relation reciproque() {
		Relation recip=new Relation(this.arrivee(),this.depart());
		for (Couple c : this) {
			recip.ajouter(c.getY(),c.getX());
		}
		return recip;
	}

	// si possible, remplace la relation courante par son union avec r
	//sinon, lance une IllegalArgumentException
	public void ajouter(RelationInterface r) {
		if(r == null || !this.depart().equals(r.depart()) || !this.arrivee().equals(r.arrivee())) throw new IllegalArgumentException();
		for(Couple c: r){
			this.ajouter(c);
		}
	}

	// si possible, remplace this par sa différence avec r
	//sinon, lance une IllegalArgumentException
	public void enlever(RelationInterface r) {
		if(r == null || !this.depart().equals(r.depart()) || !this.arrivee().equals(r.arrivee())) throw new IllegalArgumentException();
		if(r==this) r=this.clone();
		for(Couple c: r){
			this.enlever(c);
		}
	}

	// si possible, remplace this par son intersection avec r
	//sinon, lance une IllegalArgumentException
	public void intersecter(RelationInterface r) {
		if(r == null || !this.depart().equals(r.depart()) || !this.arrivee().equals(r.arrivee())) throw new IllegalArgumentException();
		if(r==this) r=this.clone();
		Relation rel=this.clone();
		for(Couple c : rel){
			if(this.contient(c) && !r.contient(c))
				this.enlever(c);
		}
	}

	// si possible, renvoie la composée : this après r
	//sinon, lance une IllegalArgumentException
	public Relation apres(RelationInterface r) {
		if(r == null || !r.arrivee().equals(this.depart())) throw new IllegalArgumentException();
		Relation apres=new Relation(r.depart(),this.arrivee());
		for(Couple c:r){
			for(Couple c2:this){
				if(c.getY().equals(c2.getX()))
					apres.ajouter(c.getX(),c2.getY());

			}
		}
		return apres;
	}


	
	/*Les exercices 4 et 5 ne concernent que les relations sur un ensemble.
	 * Les méthodes demandées génèreront donc une MathException lorsque l'ensemble de départ
	 * ne coïncide pas avec l'ensemble d'arrivée.
	 */
	
	/* Ex 4 */
		
	// Clôture la Relation courante pour la réflexivité
	public void cloReflex() {
		if(!this.depart().equals(this.arrivee()))throw new MathException();
		for(Elt e: this.arrivee())
			this.ajouter(e,e);

	}

	// Clôture la Relation courante pour la symétrie
	public void cloSym() {
		if(!this.depart().equals(this.arrivee()))throw new MathException();
		Relation clone=this.clone();
		for(Couple c:clone)
			this.ajouter(c.getY(),c.getX());

	}
	int iter = 0;
	// Clôture la Relation courante pour la transitivité (Warshall)
	public void cloTrans() {
		if(!this.depart().equals(this.arrivee()))throw new MathException();
		for (Elt k : arrivee()) {
			for (Elt i : arrivee()) {
				for (Elt j : arrivee()) {
					if (contient(i, k) && contient(k, j)) {
						ajouter(i, j);

					}
					iter++;
				}
			}
		}
		System.out.println(iter);

	}
	
	
	//Ex 5
	/*Les questions qui suivent ne concernent que les relations sur un ensemble.
	 * Les méthodes demandées génèreront donc une MathException lorsque l'ensemble de départ
	 * ne coïncide pas avec l'ensemble d'arrivée.
	 */
	// renvoie true ssi this est réflexive
	public boolean reflexive(){
		if(!this.depart().equals(this.arrivee()))throw new MathException();
		for(Elt e: this.arrivee())
			if(!this.contient(e,e))
				return false;
		return true;
	}

	// renvoie true ssi this est antiréflexive
	public boolean antireflexive(){
		if(!this.depart().equals(this.arrivee()))throw new MathException();
		for(Elt e: this.arrivee())
			if(this.contient(e,e))
				return false;
		return true;
	}

	// renvoie true ssi this est symétrique
	public boolean symetrique(){
		if(!this.depart().equals(this.arrivee()))throw new MathException();
		for(Couple e: this)
			if(!this.contient(e.getY(),e.getX()))
				return false;
		return true;
	}

	// renvoie true ssi this est antisymétrique
	public boolean antisymetrique(){
		if(!this.depart().equals(this.arrivee()))throw new MathException();
		for(Couple e: this)
			if(this.contient(e.getY(),e.getX()) && !e.getX().equals(e.getY()))
				return false;
		return true;
	}

	// renvoie true ssi  this est transitive
	public boolean transitive() {
		if (!this.depart().equals(this.arrivee())) throw new MathException();
		for (Elt k : arrivee()) {
			for (Elt i : arrivee()) {
				for (Elt j : arrivee()) {
					if (contient(i, k) && contient(k, j) && !contient(i, j)) return false;
				}
			}
		}
		return true;
	}
	
	// Ex 6
	//Construit une copie de la relation en paramètre
	//lance une IllegalArgumentException en cas de paramètre invalide
	public Relation(RelationInterface r) {
		if(r == null ) throw new IllegalArgumentException();
		for(Elt dep: r.depart()){
			this.ajouterDepart(dep);
		}
		for(Elt arr: r.arrivee()){
			this.ajouterArrivee(arr);
		}
		for(Couple c: r){
			this.ajouter(c);
		}
	}

	//renvoie l'identité sur e
	//lance une IllegalArgumentException en cas de paramètre invalide
	public static Relation identite(EnsembleAbstrait e) {
		if(e == null ) throw new IllegalArgumentException();
		Relation rel=new Relation(e,e);
		for (Elt elt:e){
			rel.ajouter(elt,elt);
		}
		return rel;
	}

	//renvoie le produit cartésien de a et b
	//lance une IllegalArgumentException en cas de paramètre invalide
	public static Relation produitCartesien(EnsembleAbstrait a, EnsembleAbstrait b) {
		Relation rel= new Relation(a,b);
		for(Elt e:a){
			for(Elt el:b){
				rel.ajouter(e,el);
			}
		}
		return rel;
	}

} // class Relatçion
