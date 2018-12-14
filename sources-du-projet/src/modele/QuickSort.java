package src.modele;

/**
 * Classe implémentant le design pattern "Singleton",
 * réalisant le tri "QuickSort".
 * @author Valentin
 *
 */
public final class QuickSort
{ 
	/**
	 * L'instance de la classe. Il ne peut y en avoir qu'une seule.
	 * On instancie directement l'attribut pour éviter d'utiliser la méthode
	 * getInstance avec la version synchronized plutôt contre-productive en Java.
	 */
	private final static QuickSort INSTANCE = new QuickSort();
	
	/**
	 * Le tableau à trier.
	 */
	private Face[] tab;
	
	/**
	 * L'index de départ de tri.
	 */
	private int low;
	
	/**
	 * L'index de fin de tri.
	 */
	private int high;
	
	/**
	 * Constructeur privé typique du design-pattern Singleton.
	 */
	private QuickSort() {
		super();
	}
	
	/** 
	 * Méthode permettant de renvoyer l'instance de la classe Singleton.
	 * @return
	 */
	public final static QuickSort getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Méthode spécifiant le tableau à trier.
	 * @param tab
	 */
	public void setTab(Face[] tab) {
		this.tab = tab;
		low = 0;
		high = tab.length - 1;
	};

	/**
	 * Cette méthode prend le dernier élément en tant que pivot, le place à sa bonne position dans le tableau trié, et place
	 * toutes les valeurs inférieures à lui à sa gauche et toutes les valeurs supérieures à lui à sa droite. Elle retourne l'indice de partition.
	 * @param tab : tableau à partitionner
	 * @param low : indice de départ
	 * @param high : indice de fin
	 * @return
	 */
	private int partition(Face tab[], int low, int high) 
	{ 
		Face pivot = tab[high];  
		int i = (low-1); 
		for (int j=low; j<high; j++) 
		{ 
			if (tab[j].compareTo(pivot)<=0)
				swap(tab,++i,j);
		}  
		swap(tab,i+1,high);
		return i+1; 
	} 

	/**
	 * Méthode échangeant les 2 éléments d'index i et j dans le tableau passé en paramètre.
	 * @param tab : tableau dans lequel échanger les deux éléments
	 * @param i : index du premier élément à échanger
	 * @param j : index du deuxième élément à échanger
	 */
	private void swap(Face[] tab, int i,int j) {
		Face tmp = tab[i]; 
		tab[i] = tab[j]; 
		tab[j] = tmp;
	}

	/**
	 * Méthode récursive implémentant le QuickSort, qui va partitionner le tableau et ranger les éléments 
	 * jusqu'à ce que le tableau soit entièrement trié.
	 * @param tab : tableau à trier
	 * @param low : index de départ
	 * @param high : index de fin
	 */
	public void sort(Face tab[], int low, int high) 
	{ 
		if (low < high) 
		{ 
			//Indice de partition, le pivot est désormais à sa place
			int pi = partition(tab, low, high); 

			// Trie les éléments avant le pivot et après le pivot récursivement
			sort(tab, low, pi-1); 
			sort(tab, pi+1, high); 
		} 
	} 
	
	/**
	 * Méthode de tri initiale appelant celle récursive avec les paramètres de base, à savoir low=0 et high=tab.length-1
	 */
	public void sort() {
		// TODO Auto-generated method stub
		sort(tab, low, high);
	}

	/**
	 * Méthode affichant le tableau trié (pour vérification du bon fonctionnement).
	 * @param arr
	 */
	public void printArray(Face tab[]) 
	{ 
		for (int i=0; i < tab.length; ++i) 
			System.out.print(tab[i]); 
	}
} 