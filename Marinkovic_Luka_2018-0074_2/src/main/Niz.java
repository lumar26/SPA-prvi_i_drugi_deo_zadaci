package main;

import java.util.ArrayList;

import labis.exception.LabisException;
import labis.niz.ANiz;

public class Niz extends ANiz {

	@Override
	public int[] spojiDva(int[] a, int[] b) throws LabisException {
		if (a == null || b == null)
			throw new LabisException("Nisu prosledjena dva niza");
		// pomocne promenljive
		int[] c = new int[a.length + b.length];
		// ubacujem ceo jedan niz u novi
		for (int i = a.length - 1, j = 0; i >= 0; i--, j++) {
			c[j] = a[i];
		}
		// dodajem drugi niz tako što ubacujem sortirano
		for (int i = 0; i < b.length; i++) {
			c = ubaciSortirano(c, b[i]);
		}

		return c;
	}

	public int[] ubaciSortirano(int[] c, int x) {
		int j;
		for (j = 0; j < c.length && x < c[j]; j++)
			;
		// ovde sam dobio od kog treba da pomeram udesno da bih ubacio element
		for (int i = c.length - 1; i > j; i--) {
			c[i] = c[i - 1];
		}
		c[j] = x;
		return c;
	}

	public static int ravnotezniIndeks(int[] niz) {

		int[] leviProlaz = new int[niz.length];
		leviProlaz[0] = 0;
		for (int i = 1; i < niz.length; i++) {
			leviProlaz[i] = leviProlaz[i - 1] + niz[i - 1];
		}
		// dal ovde ovaj desni treba da bude nula ili poslednji element
		int desniProlaz = 0;
		for (int i = leviProlaz.length - 1; i > 0; i--) {
			if (desniProlaz == leviProlaz[i])
				return i;
			desniProlaz += niz[i];
		}

		return -1;
	}

	public static void elementiVeciOdSvihDesno(int[] niz) { // ne racuna se poslednji element
		ArrayList<Integer> rez = new ArrayList<Integer>();
		int max = niz[niz.length - 1];
		for (int i = niz.length - 2; i >= 0; i--) {
			if (niz[i] > max) {
				max = niz[i];
				rez.add(max);
			}
		}

		System.out.println("Elementi niza koji sa svoje desne strane imaju samo elemente koji su manji od njih su: ");
		for (Integer element : rez) {
			System.out.print(element + " ");
		}
	}

	public static int[] nizNastaoMnozenjem(int[] niz) {
		int prethodni, trenutni, sledeci;
		for (int i = 0, j = 1, k = 2; k < niz.length; i++, j++, k++) {
			prethodni = niz[i];
			trenutni = niz[j];
			sledeci = niz[k];
			niz[i] = prethodni * trenutni;
		}
		return niz;
	}

	// svaki element u nizu zameni sa proizvodom njegovih suseda, a prvi i poslednji
	// sa proiyvodom prvod i drugog
	// odnosno predposlednjeg i poslednjeg; u jednom prolazu
	public void zameniSvakiProizvodomSuseda() {
		if (this.niz.length < 2)
			return;
		int i = 0, k = niz[i], n = 0;
		while (i < niz.length - 1) {
			// cuvamo taj koji cemo da menjamo
			n = niz[i];
			// menjanje, gde je vrednost k ostalo iz prosle iteracije
			niz[i] = k * niz[++i];
			// idemo dalje sa k
			k = n;
		}
		// poslednji i predposlednji
		niz[i] *= k;
	}

	// vraca najmanji pozitivni intidzer koji nedostaje u nizu
	public int firstMissingPositive() {
		if (niz.length < 1)
			return Integer.MAX_VALUE;
		boolean gotovo = false;
		for (int i = 1; i < niz.length; i++) {
			if (gotovo)
				break;
			for (int j = 1; j < niz.length - i + 1; j++) {
				if (niz[j] < niz[j - 1]) {
					// zamena
					int pom = niz[j];
					niz[j] = niz[j - 1];
					niz[j - 1] = pom;
					gotovo = true;
				}
			}
			gotovo = false;
		}
		int i;
		for (i = 0; i < niz.length && niz[i] < 1; i++)
			;
		if (niz[i] != 1)
			return 1;
		for (int j = i + 1; j < niz.length; j++) {
			if (niz[j] - niz[j - 1] > 1) {
				return niz[j - 1] + 1;
			}
		}
		return niz[niz.length - 1] + 1;

	}

	public void premestiKElemenataSaKrajaNaPocetak(int k) {
		// if (k > niz.length)
		// throw new LabisException("Zadali ste parametar koji je veci od dimenzija
		// niza");
		int[] pomNiz = new int[k];

		// ubacivanje u pomocni niz
		for (int i = niz.length - k, j = 0; i < niz.length; i++, j++)
			pomNiz[j] = niz[i];

		// pomeranje prvobitnog niza
		for (int i = niz.length - 1; i >= k; i--)
			niz[i] = niz[i - k];

		// dodavanje pomNiza na pocetak
		for (int i = 0; i < k; i++)
			niz[i] = pomNiz[i];

	}

	/*
	 * Preraspodeli elemente niza tako da na pocetku budu svi negativni a zatim svi
	 * pozitivni, u jednom prolazu bez pomocnih i sve nule neka spadaju u pozitivne
	 */

	public void prebaciNegativneNaPocetak() {
		if (this.niz.length < 2)
			return;
		int pocetak = 0;
		int i = 0, j = niz.length - 1;
		while (i < j) {
			if (niz[j] >= 0 && niz[i] < 0) {
				i++;
				j++;
			}
			if (niz[j] < 0 && niz[i] >= 0) {
				int temp = niz[i];
				niz[i] = niz[j];
				niz[j] = temp;
				j--;
				i++;
			}
			if (niz[j] < 0 && niz[i] < 0)
				i++;
			if (niz[j] >= 0 && niz[i] >= 0)
				j--;
		}
	}

	/*
	 * nadji najvecu razliku izmedju dva elementa tako da desni element mora biti
	 * veci od drugog
	 */
	public int najvecaRazlikaLevogIDesnog() {
		if (niz.length < 2)
			return 0;
		int razlika = 0;
		for (int levi = 0, desni = 1; desni < niz.length - 1; levi++, desni++) {
			if (niz[levi] < niz[desni] && niz[desni] - niz[levi] > razlika)
				razlika = niz[desni] - niz[levi];
		}
		return razlika;
	}

	/*
	 * posmatramo da su vrednosti u nizu visine uzastopnih stubova izrazene u m, a
	 * sirina svakog stuba je 1m, odrediti koliko ce se kisnici sakupiti medju
	 * stubovima ako padne kisa
	 */
	public int skupljanjeKisnice() {

		// for (int i = 0; i < niz.length; i++) {
		// if (max < niz[i]) {
		// max = niz[i];
		// maxIndex = i;
		// continue;
		// }
		// if (niz[i] < niz[i + 1] && niz[i + 1] <= max) {
		// // dodajem ono iznad najnizeg, odnosno dodajem uvis
		// zbir += (i - maxIndex) * (niz[i + 1] - niz[i]);
		// }
		// if (niz[i] < niz[i + 1] && niz[i + 1] > max) {
		// zbir += (i - maxIndex) * (max - niz[i]);
		// }
		// if (niz[i] > niz[i + 1]) {
		// zbir += niz[i] - niz[i + 1];
		// }
		//
		// }
		boolean maxNadjen = false;
		int zbir = 0;
		int maxCount = 0, pomMax = 0, pomMaxIndex = 0;
		int max1 = 0, maxIndex1 = 0;
		int max2 = 0, maxIndex2 = 0;
		// glavna petlja
		for (int i = 0; i < niz.length; i++) {
			if (max1 < niz[i] && maxCount == 0) {
				max1 = niz[i];
				maxIndex1 = i;
				maxCount++;
				continue;
				// nastavljamo kad nadjemo prvi max da nam ne bi i drugi bio isti taj
			}

			// sta ccemo ako nema maximuma do kraja
			if (niz[i] > pomMax) {
				pomMax = niz[i];
				pomMaxIndex = i;
			}

			// veci od max1 jer nama treba prvi sledeci kkoji je veci od prvog maximuma
			if (max1 <= niz[i] && maxCount == 1) {
				max2 = niz[i];
				maxIndex2 = i;
				// sad imamo dva maximmuma
				zbir += max1 * (maxIndex2 - maxIndex1 - 1);
				// sad treba da oduzmemo vrednosti u udubljenju koje ne moze da ispuni voda
				for (int j = maxIndex1 + 1; j < maxIndex2; j++) {
					zbir -= niz[j];
				}
				max1 = niz[i];
				maxIndex1 = i;
				pomMax = 0;
			} else if (i == niz.length - 1 && maxIndex2 != i) {
				// znaci da poslednji nije maksimum, tj nije granica udubljenja u kojem je voda
				// tad sabiramo od max1 pa do pomocnog maximuma, i postavljamo da je max1 =
				// pomMax
				zbir += pomMax * (pomMaxIndex - maxIndex1 - 1);
				for (int j = maxIndex1 + 1; j < pomMaxIndex; j++) {
					zbir -= niz[j];
				}
				max1 = niz[pomMaxIndex];
				maxIndex1 = pomMaxIndex;
				i = pomMaxIndex + 1;
				// da bismo mogli opet da koristimo pomMax vratimo ga na 0
				pomMax = 0;
			}
		}
		return zbir;
	}

	/*
	 * Niz, dat broj kao parametar Metoda vraca duzinu najkraceg podniza ciji je
	 * zbir veci od datog parametra u jednom polazu
	 */
	public int najkraciPodnizSaZbiromVecim(int k) throws LabisException {
		if (niz == null)
			throw new LabisException();
		int kraj = 0, pocetak = 0, zbir = 0, min = niz.length + 1;
		while (kraj < niz.length) {
			while (zbir < k && kraj < niz.length)
				zbir += niz[kraj++];
			while (zbir >= k) {
				if (min > kraj - pocetak)
					min = kraj - pocetak;
				zbir -= niz[pocetak++];
			}
		}
		return min;
	}

	/*
	 * metoda koja nalazi najduzi niz uzastopnih brojeva koji se ne moraju nalaziti
	 * jedan pored drugog niti u bilo kakvom redolsedu, kompleksnost bi trebala biti
	 * O(n) Input: [100, 4, 200, 1, 3, 2] Output: 4 Explanation: The longest
	 * consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
	 */
	public int najduziNizUzastopnih() {
		int brojac = 0;
		int duzina = niz.length; // broj kolika maksimalna duzina tog niza koji trazimo moze da bude
		for (int i = 0; i < niz.length - 1; i++) {

		}
		return 0;
	}

	// quick sort
	public static void quickSort(int niz[]) {
		quickSort(niz, 0, niz.length - 1);
	}

	private static void quickSort(int[] niz, int levaG, int desnaG) {
		if (levaG < desnaG) {
			int pivotPoz = partition(niz, levaG, desnaG);
			quickSort(niz, levaG, pivotPoz - 1);
			quickSort(niz, pivotPoz + 1, desnaG);
		}
	}

	private static int partition(int[] niz, int levaG, int desnaG) {
		int pivot = niz[desnaG], i = levaG - 1, j = desnaG;
		while (true) {
			while (niz[++i] < pivot)
				;
			while (niz[--j] > pivot)
				if (i == j)
					break;
			if (i >= j)
				break;
			zameni(niz, i, j);
		}
		zameni(niz, i, desnaG);
		return i;
	}

	private static void zameni(int[] niz, int i, int j) {
		int pom = niz[i];
		niz[i] = niz[j];
		niz[j] = pom;
	}

	// insertion sort
	public void insertionSort() {
		if (this.niz == null)
			return;
		for (int i = 1; i < niz.length; i++) {
			int pom = niz[i];
			int j = i - 1;

			while (j >= 0 && pom < niz[j]) {
				int temp = niz[j];
				niz[j] = niz[j + 1];
				niz[j + 1] = temp;
				j--;
				// ovo moze mnogo krace ali jbg to sam se setila
			}
		}
	}

	// interpolaciono pretrazivanje
	public int InterpolacioniPretrazivanje(int podatak) {
		int min = niz[0], max = niz[niz.length - 1], minPoz = 0, maxPoz = niz.length - 1;

		while (minPoz <= maxPoz && podatak >= min && podatak <= max) {
			// ako je minPoz == maxPoz tad je svakako pozicija = min
			double pom3 = maxPoz - minPoz, pom1 = podatak - min, pom2 = max - min;
			double pom4 = pom1 / pom2;
			double pom5 = pom3 * pom4;
			int pozicija = minPoz + (int) pom5;
			if (niz[pozicija] == podatak)
				return pozicija;
			if (podatak < niz[pozicija]) {
				maxPoz = pozicija - 1;
				max = niz[pozicija - 1];
			} else {
				minPoz = pozicija + 1;
				min = niz[pozicija + 1];
			}
		}
		return -1;
	}

	// interpolaciono pretrazivanje rekurzivno
	public int interpolacionoPretrazivanjeRek(int podatak, int donjaGranica, int gornjaGranica) {
		if (gornjaGranica < donjaGranica)
			return -1;
		int indeks = (podatak - this.niz[donjaGranica]) / (niz[gornjaGranica] - niz[donjaGranica])
				* (gornjaGranica - donjaGranica);
		if (niz[indeks] == podatak)
			return indeks;
		if (niz[indeks] > podatak)
			return interpolacionoPretrazivanjeRek(podatak, donjaGranica, indeks - 1);
		else
			return interpolacionoPretrazivanjeRek(podatak, indeks + 1, gornjaGranica);
	}

	// binarno pretrazivanje rekurzivno
	public int binarnoPretrazivanjeRekurzivno(int podatak) {
		return binarnoPretrazivanjeRekPom(podatak, 0, niz.length - 1);
	}

	private int binarnoPretrazivanjeRekPom(int podatak, int minPoz, int maxPoz) {
		if (minPoz > maxPoz)
			return -1;
		int sredina = minPoz + (maxPoz - minPoz) / 2;

		if (podatak < niz[sredina])
			binarnoPretrazivanjeRekPom(podatak, minPoz, sredina - 1);
		if (podatak > niz[sredina])
			binarnoPretrazivanjeRekPom(podatak, sredina + 1, maxPoz);
		// base case ako je podatak jednak onom na sredini
		return niz[sredina];
	}

	// oderditi pomeraj ulevo sortiranog popunjenog niza ukoliko je dat niz nakon
	// pomeraja, efinaksnost je O(log n)
	public int leviPomeraj() {
		if (niz == null)
			return -1;
		int pocetak = 0, kraj = niz.length - 1, sredina = 0;
		while(pocetak <= kraj) {
			sredina = pocetak + (kraj - pocetak) / 2;
			if(niz[sredina] > niz[sredina + 1]) {
				return niz.length - 1 - sredina;
			}
			if(niz[sredina] > niz[niz.length - 1]) {
				pocetak = sredina + 1;
			}
			if(niz[sredina] < niz[niz.length - 1]) {
				kraj = sredina - 1;
			}
		}
		return 0;
	}
}
