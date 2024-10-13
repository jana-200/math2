 public class Matrice {
    private final int nbLignes;              // nombre de lignes
    private final int nbColonnes;            // nombre de colonnes
    private final double[][] data;           // matrice (nbLignes,nbColonnes)

    // ce constructeur cree la matrice nulle de genre (a,b)
    public Matrice(int a, int b) throws IllegalArgumentException {
        if(a<=0 || b<=0) throw new IllegalArgumentException();
        nbLignes=a ;
        nbColonnes=b ;
        data =new double[a][b] ;
    }

    //  Ce constructeur permet de construire la matrice correspondant 
    //  au tableau en parametre. 
    public Matrice(double[][] tab)  throws IllegalArgumentException {
        if(tab==null || tab.length<1 || tab[0] == null ||tab[0].length<1) throw new IllegalArgumentException();
        for (int i = 1; i < tab.length; i++) {
            if(tab[i]==null ) throw new IllegalArgumentException();
            if(tab[i].length!=tab[i-1].length) throw new IllegalArgumentException();
        }
        double[][] table= new double[tab.length][tab[0].length];
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j <tab[0].length ; j++) {
                table[i][j]=tab[i][j];
            }
        }
        nbLignes= tab.length; ;
        nbColonnes=tab[0].length ;
        data =table;

    }

    // constructeur par recopie
    public Matrice(Matrice a)  throws IllegalArgumentException {
        if(a==null || a.data==null || a.nbLignes<1 || a.nbColonnes<1) throw new IllegalArgumentException();
        double[][] matrice=new double[a.nbLignes][a.nbColonnes];
        for (int i = 0; i < a.nbLignes; i++) {
            for (int j = 0; j <a.nbColonnes ; j++) {
                matrice[i][j]=a.data[i][j];

            }
        }
        data=matrice;
        nbColonnes=a.nbColonnes;
        nbLignes= a.nbLignes;
	}

    // cree la matrice identite d'ordre a
    public static Matrice identite(int a)  throws IllegalArgumentException {
    	if(a<1) throw new IllegalArgumentException();
        Matrice id= new Matrice(a,a);
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < a; j++) {
                if(i==j)
                    id.data[i][j]=1;
            }
        }
    	return id;
    }

    
    //Cette methode renvoie l'element de la ligne numLigne et de la 
    //colonne numColonne de la matrice. Si cet element n'existe pas, la 
    //methode lance une IllegalArgumentException 
	public double getElement(int numLigne, int numColonne) throws IllegalArgumentException {
		if(nbColonnes<numColonne || nbLignes<numLigne || numColonne<1 || numLigne<1) throw new IllegalArgumentException();
		return data[numLigne-1][numColonne-1];
	 }
    
    // ajoute b a la matrice courante si c'est possible
    public Matrice somme(Matrice b)  throws IllegalArgumentException {
    	if(b== null || nbColonnes!=b.nbColonnes || nbLignes!=b.nbLignes) throw new IllegalArgumentException();
        Matrice matrice = new Matrice(b);
        for (int i = 0; i < b.nbLignes; i++) {
            for (int j = 0; j < b.nbColonnes; j++) {
                matrice.data[i][j]+=this.data[i][j];
            }
        }
    	return matrice;
    }

    // calcule le produit scalaire.this de la matrice courante avec scalaire
    public Matrice produitParScalaire(double scalaire){
    	Matrice matrice= new Matrice(this);
        for (int i = 0; i <this.nbLignes ; i++) {
            for (int j = 0; j <this.nbColonnes ; j++) {
                matrice.data[i][j]= matrice.data[i][j]*scalaire;
            }
        }
    	return matrice;
    }

    // calcule le produit this*b de la matrice courante avec b si possible
    public Matrice produitAGauche(Matrice b)  throws IllegalArgumentException {
        if (b == null || this.nbColonnes != b.nbLignes)  throw new IllegalArgumentException();
        Matrice matrice= new Matrice(this.nbLignes, b.nbColonnes);
        for (int i = 0; i < this.nbLignes; i++) {
            for (int j = 0; j < b.nbColonnes; j++) {
                double sum = 0;
                for (int k = 0; k < this.nbColonnes; k++) {
                    sum += this.data[i][k] * b.data[k][j];
                }
                matrice.data[i][j] = sum;
            }
        }
    	return matrice;
    }
    
	// calcule le produit b*this de b avec la matrice courante si possible
    public Matrice produitADroite(Matrice b)  throws IllegalArgumentException {
        if (b == null || b.nbColonnes != this.nbLignes)  throw new IllegalArgumentException();
        Matrice matrice= new Matrice(b.nbLignes, this.nbColonnes);
        for (int i = 0; i < b.nbLignes; i++) {
            for (int j = 0; j < this.nbColonnes; j++) {
                double sum = 0;
                for (int k = 0; k < b.nbColonnes; k++) {
                    sum += b.data[i][k] * this.data[k][j];
                }
                matrice.data[i][j] = sum;
            }
        }
        return matrice;
    }
	 
   // renvoie true si la matrice courante est carrée
	 public boolean carree(){
		 return this.nbColonnes==this.nbLignes;
    }
    
    // Calcule this^n. Lance une Mathexception si this n'est pas carrée
    public Matrice puissance(int n) throws  IllegalArgumentException {

    	return null;
    }
    
	//Calcule this^T : la tranposée de this
	public Matrice transposee() {
        Matrice transposedMatrix = new Matrice(this.nbColonnes, this.nbLignes);

        for (int i = 0; i < this.nbLignes; i++) {
            for (int j = 0; j < this.nbColonnes; j++) {
                transposedMatrix.data[j][i] = this.data[i][j];
            }
        }

        return transposedMatrix;
	}
	 
    // affiche la matrice en format standard
    public String toString(){
    	//TODO
    	return null;
    }

    public Matrice pageRank() {
        // A FAIRE QUAND MARKOV AURA ÉTÉ VU
        return null ;
    }
  }   
