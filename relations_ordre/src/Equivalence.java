/** Classe Equivalence
    Chaque instance de cette classe est une relation d'équivalence sur un sous-ensemble de l'Univers
 **/

import java.util.*;

public class Equivalence extends RelationAbstraite {

	private EnsembleAbstrait sousJac; // ensemble sous-jacent
	private Elt[] tabRep; // tableau des représentants
	private int numVersion; // numéro de version


	// Construit l’identité sur e
	// Lance une IllegalArgumentException en cas de paramètre invalide 
	public Equivalence(EnsembleAbstrait e) {
		if(e==null) throw new IllegalArgumentException();
		numVersion=1;
		this.sousJac=new Ensemble(e);
		this.tabRep=new Elt[Elt.MAXELT.val()+1];
		for(Elt elt:e){
			tabRep[elt.val()]=elt;
		}
	}
	
	
	// ajoute (si nécessaire) l’arête x-y au diagramme de classes de
	// l’Equivalence courante ; autrement dit, fusionne les classes de
	// c.getx() et de c.gety()
	// Lance une IllegalArgumentException en cas de paramètre invalide 
	public void ajouter(Couple c) {
		if(c==null) throw new IllegalArgumentException();
		if(!sousJac.contient(c.getX()) ||!sousJac.contient(c.getY())) throw new IllegalArgumentException();
		if(contient(c)) return ;
		if(!tabRep[c.getY().val()].equals(tabRep[c.getX().val()])){
			Elt aRemplacer = tabRep[c.getX().val()];
			Elt remplacerPar = tabRep[c.getY().val()];
			for(Elt elt : sousJac){
				if(tabRep[elt.val()].equals(aRemplacer))
					tabRep[elt.val()] = remplacerPar;
			}
			numVersion++;
		}
	}

	// Construit la clôture équivalente de r, pour autant que celle-ci soit une relation sur un ensemble.
	// lance une IllegalArgumentException sinon
	public Equivalence(Relation r) {
		if(r==null || !r.depart().equals(r.arrivee())) throw new IllegalArgumentException();

		sousJac=new Ensemble(r.depart());
		numVersion=1;
		tabRep=new Elt[Elt.MAXELT.val()+1];

		for(Elt elt : r.depart()){
			tabRep[elt.val()] = elt;
		}
		for(Couple c : r){
			ajouter(c);
		}


	}

	
	// renvoie true si c.getx() et c.gety() sont dans la même classe et false sinon
	// Lance une IllegalArgumentException en cas de paramètre invalide 
	public boolean contient(Couple c) {
		if(c==null )throw new IllegalArgumentException();
		if(!sousJac.contient(c.getX()) ||!sousJac.contient(c.getY())) throw new IllegalArgumentException();
		return tabRep[c.getX().val()].equals(tabRep[c.getY().val()]);
	}

	// renvoie la classe d'équivalence de x, ou génère une IllegalArgumentException
	// si e est null ou si e n'appartient pas à l'ensemble sous-jacent
	public EnsembleAbstrait classe(Elt e) {
		EnsembleAbstrait ens= new Ensemble();
		if(e ==null )throw new IllegalArgumentException();
		if(!sousJac.contient(e) ) throw new IllegalArgumentException();
		for(Elt elt : sousJac){
			if(tabRep[elt.val()].equals(tabRep[e.val()])){
				ens.ajouter(elt);
			}
		}
		return ens;
	}

	// Si c.getx()et c.gety() sont distincts et si la classe commune
	// de c.getx() et c.gety() est {c.getx(),c.gety()}, alors cette classe
	// sera scindée en deux classes.
	// génère une IllegalArgumentException si le paramètre est invalide,
	// ou si c.getx(), c.gety() sont dans la même classe  mais qu'on n'est pas 
	// dans le cas où on peut scindée cette classe.
	public void enlever(Couple c) {
		if(c == null )
			throw new IllegalArgumentException();
		if(!contient(c)){return;}
		if(!sousJac.contient(c.getX()) || !sousJac.contient(c.getY()) || c.getX().equals(c.getY()) || !tabRep[c.getX().val()].equals(tabRep[c.getY().val()]))
			throw new IllegalArgumentException();
		if(classe(c.getX()).cardinal() != 2)
			throw new IllegalArgumentException();
		tabRep[c.getX().val()] = c.getX();
		tabRep[c.getY().val()] = c.getY();
		numVersion++;

	}

	// renvoie le nombre de classes de l'Equivalence courante
	public int nbreClasses() {
		Ensemble ens= new Ensemble();
		for (int i = 0; i < tabRep.length; i++) {
			if(tabRep[i]!=null){
				ens.ajouter(tabRep[i]);
			}
		}
		return ens.cardinal();
	}

	// renvoie le quotient de l’ensemble sous-jacent par l'Equivalence
	// courante
	public EnsembleAbstrait[] quotient() {
		EnsembleAbstrait[] quotient = new Ensemble[nbreClasses()];
		EnsembleAbstrait copie= sousJac.clone();

		int j=0;

		while (!copie.estVide()){
			EnsembleAbstrait ens= classe(copie.unElement());
			copie.enlever(ens);
			quotient[j]=ens;
			j++;
		}
		return quotient;
	}


	public boolean estVide() {
		return sousJac.estVide();
	}

	@Override
	public EnsembleAbstrait depart() {
		return sousJac.clone();
	}

	@Override
	public EnsembleAbstrait arrivee() {
		return sousJac.clone();
	}
	
	/** renvoie un itérateur sur l'Equivalence courante */
	public Iterator<Couple> iterator() {
		return new EquivalenceIterator();
	}

	private class EquivalenceIterator implements java.util.Iterator<Couple> {
		private Iterator<Couple> itC;
		private int version;

		public EquivalenceIterator() {
			version = numVersion;
			Relation r = new Relation(sousJac, sousJac);
			EnsembleInterface reste = sousJac.clone();
			while (!reste.estVide()) {
				Elt e = reste.unElement();
				EnsembleAbstrait classeE = classe(e);
				Iterator<Elt> itClasseE = classeE.iterator();
				while (itClasseE.hasNext()) {
					Elt next = itClasseE.next();
					r.ajouter(e, next);
					r.ajouter(next, e);
					r.ajouter(next, next);
				}
				reste.enlever(classeE);
			}
			r.cloTrans();
			itC = r.iterator();
		}

		@Override
		public boolean hasNext() {
			return itC.hasNext();
		}

		@Override
		public Couple next() {
			if (numVersion != this.version)
				throw new ConcurrentModificationException();
			if (!hasNext())
				throw new NoSuchElementException();
			return itC.next();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

} // Equivalence
