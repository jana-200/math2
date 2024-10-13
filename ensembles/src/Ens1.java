public class Ens1 extends EnsembleAbstrait {

	private boolean[] tabB; // e appartient à l'ensemble courant ssi tabE[e.val()] est à true.
	private int cardinal;

	public Ens1() {
		tabB= new boolean[Elt.MAXELT.val()+1];
		this.cardinal=0;
	}
	public Ens1(Elt e) {
		if(e==null) throw new IllegalArgumentException();
		tabB= new boolean[Elt.MAXELT.val()+1];
		this.cardinal=1;
		tabB[e.val()]= true;
	}
	public Ens1(EnsembleInterface ens) {
		if(ens== null) throw new IllegalArgumentException();
		tabB= new boolean[Elt.MAXELT.val()+1];
		cardinal= ens.cardinal();

		for (int i = 1; i < tabB.length ; i++) {
			if(ens.contient(new Elt(i)))
				tabB[i]= true;
		}
	}
	
	public boolean estVide() {
		return cardinal==0 ;
	}
	
	public Elt unElement() {
		if(estVide())
			throw new MathException();
		for (int i = 0; i < tabB.length; i++) {
			if(tabB[i]) return new Elt(i);
		}
		return null ;
	}

	public boolean contient(Elt e) {
		if(e == null ) throw new IllegalArgumentException() ;
		int o= e.val();
		if(tabB[o])
			return true;
		return false ;
	}


	public void ajouter(Elt e) {
		if(!contient(e)){
			cardinal++;
			tabB[e.val()]= true;
		}
	}

	public void enlever(Elt e) {
		if(contient(e)){
			cardinal--;
			tabB[e.val()]= false;
		}
	}

	public int cardinal() {
		return cardinal ;
	}

	public void complementer() {
		int card= this.cardinal;
		this.cardinal= tabB.length-1-card;

		for (int i = 0; i < tabB.length; i++) {
			if(tabB[i]){
				tabB[i]= false;
			}
			else{
				tabB[i]= true;
			}
		}
	}

	public String toString() {
		if(this.estVide())return "{}";
		String texte= "{";
		int taille= cardinal;
		for (int i = 1; i < tabB.length; i++) {
			if(tabB[i]){
				if(taille==1) {
					texte += i;
				}else {
					taille--;
					texte += i + ",";
				}
			}
		}
		return texte+"}";
	}
	
}
