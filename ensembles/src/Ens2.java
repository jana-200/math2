import com.sun.source.tree.DefaultCaseLabelTree;

import java.util.Arrays;

public class Ens2 extends EnsembleAbstrait {

	private Elt[] elements; // contient les elements de l'ensemble. Il ne peut pas y avoir de doublon.
	private int cardinal;

	public Ens2() {
		elements= new Elt[Elt.MAXELT.val()];
		this.cardinal=0;
		
	}
	public Ens2(EnsembleInterface ens) {
		if(ens== null) throw new IllegalArgumentException();
		elements= new Elt[Elt.MAXELT.val()];
		cardinal= ens.cardinal();

		int j=0;
		for (int i = 1; i < elements.length+1 ; i++) {
			if(ens.contient(new Elt(i))) {
				elements[j] = new Elt(i );
				j++;
			}
		}
	}
	public Ens2(Elt elt) {
		if(elt==null) throw new IllegalArgumentException();
		elements= new Elt[Elt.MAXELT.val()];
		elements[0]= elt;
		this.cardinal=1;
	}


	public boolean estVide() {
		return cardinal==0 ;
	}
	
	public Elt unElement() {
		if(estVide())
			throw new MathException();
		return elements[0] ;

	}

	public boolean contient(Elt e) {
		if(e== null) throw new IllegalArgumentException();
		if(this.estVide()) return false;
		for (int i = 0; i < cardinal ; i++) {
			if(elements[i].val()==e.val())
				return true;
		}
		return false ;
	}

	public void ajouter(Elt e) {
		if(!contient(e)){
			elements[cardinal]=e;
			cardinal++;
		}
		
	}

	public void enlever(Elt e) {
		if(contient(e)){
			for (int i = 0; i < cardinal ; i++) {
				if(elements[i].val()==e.val())
					elements[i]=elements[cardinal-1];
			}
			cardinal--;
		}
		
	}

	public int cardinal() {
		return cardinal;
	}

	public void complementer() {

		int[] count= new int[Elt.MAXELT.val()+1];

		if(!this.estVide()) {
			for (int i = 0; i < cardinal; i++) {
				count[elements[i].val()]++;
			}
		}

		int j=0;
		for (int i = 1; i < elements.length+1; i++) {
			if(count[i]==0){
				elements[j]=new Elt(i);
				j++;
			}
		}

		int card = this.cardinal;
		this.cardinal = elements.length - card;


	}

	public String toString() {
		if(this.estVide())return "{}";
		String texte= "{";
		int taille= cardinal;
		for (int i = 0; i < elements.length; i++) {
			if(elements[i]!= null){
				if(taille==1) {
					texte += elements[i];
				}else {
					taille--;
					texte += elements[i] + ",";
				}
			}
		}
		return texte+"}";
	}

}
