public abstract class EnsembleAbstrait implements EnsembleInterface {

	// renvoie true ssi this est inclus dans a
	// lance une IllegalArgumentException en cas de paramètre invalide
	public boolean inclusDans(EnsembleAbstrait a) {
		if(a== null) throw new IllegalArgumentException();
		for (int i = 1; i < MAX+1; i++) {
			if(this.contient(new Elt(i)) && !a.contient(new Elt(i)))
				return false;
		}
		return true ;
	}

	// renvoie true ssi this est égal à a o
	public final boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof EnsembleAbstrait))
			return false ;
		if(this.estVide() && !((EnsembleAbstrait) o).estVide())
			return false;
		if(((EnsembleAbstrait) o).cardinal()!=this.cardinal() )
			return false;
		if(!((EnsembleAbstrait) o).inclusDans(this))
			return false;
		if(!this.inclusDans(((EnsembleAbstrait) o)))
			return false;

		return true;
	}

	@Override
	public final int hashCode() {
		int result = 1;
		int prime = 31;
		for (int i = 1; i <= MAX; i++) {
			Elt ei = new Elt(i);
			if (this.contient(ei))
				result = result * prime + ei.hashCode();
		}
		return result;
	}
	
}
