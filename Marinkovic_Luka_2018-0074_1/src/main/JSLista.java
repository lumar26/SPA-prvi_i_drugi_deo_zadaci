package main;

import labis.cvorovi.CvorJSListe;
import labis.exception.LabisException;
import labis.liste.AJSLista;
import labis.test.ListGenerator;

public class JSLista extends AJSLista {

	@Override
	public int izbaciTrenutni(CvorJSListe t) throws LabisException {
		if (this.prvi == null)
			throw new LabisException();
		if (t == this.prvi) {
			this.prvi = this.prvi.sledeci;
			return t.podatak;
		}
		CvorJSListe pom = this.prvi;
		while (pom.sledeci != null && t != pom.sledeci) {
			pom = pom.sledeci;
		}
		pom.sledeci = pom.sledeci.sledeci;
		return t.podatak;
	}

	@Override
	public void invertovanjeBezPomocne() throws LabisException {
		// ne moraju ova dva ifa
		if (this.prvi == null)
			throw new LabisException();
		if (this.prvi.sledeci == null)
			return;
		CvorJSListe prethodni = null, trenutni = this.prvi, predstojeci = null;
		while (trenutni != null) {
			predstojeci = trenutni.sledeci;
			trenutni.sledeci = prethodni;
			prethodni = trenutni;
			trenutni = predstojeci;
		}
		this.prvi = prethodni;

	}

	@Override
	public int zbirElemenataUCiklicnoj() throws LabisException {
		if (this.prvi == null)
			throw new LabisException();
		// ovo smo odradili u onom sabiranju nakon while petlje
		// if (this.prvi.sledeci == this.prvi)
		// return this.prvi.podatak;
		CvorJSListe pom = this.prvi;
		int suma = 0;
		while (pom.sledeci != this.prvi) {
			suma += pom.podatak;
			pom = pom.sledeci;
		}
		suma += pom.podatak;
		return suma;
	}

	@Override
	public void ispisiObrnuto(CvorJSListe prvi) throws LabisException {
		// moze i preko invertovanja liste
		if (prvi == null)
			return;
		ispisiObrnuto(prvi.sledeci);
		System.out.print(prvi.podatak + "\t");
	}

	@Override
	public CvorJSListe klonirajRekurzivno(CvorJSListe pom) throws LabisException {
		if (pom == null)
			return null;
		return new CvorJSListe(pom.podatak, klonirajRekurzivno(pom.sledeci));

	}

	public CvorJSListe ukloniNtiOdKraja(int n) {
		if (prvi == null || n < 0)
			return null;

		CvorJSListe spori = prvi, brzi = prvi;
		for (int i = 0; i < n; i++) {
			if (brzi.sledeci == null) {
				// treba da se izbaci prvi
				return prvi = prvi.sledeci;
			}
			brzi = brzi.sledeci;
		}

		while (brzi.sledeci != null) {
			brzi = brzi.sledeci;
			spori = spori.sledeci;
		}
		spori.sledeci = spori.sledeci.sledeci;

		return prvi;
	}

	public static JSLista saberiDvaBroja(JSLista l1, JSLista l2) throws LabisException {
		if (l1 == null || l2 == null || l1.prvi == null || l2.prvi == null)
			throw new LabisException("Prosledjene su prazne liste");

		// pomocne promenljive
		JSLista rez = new JSLista();
		CvorJSListe pom1 = l1.prvi.sledeci, pom2 = l2.prvi.sledeci;
		int br1 = l1.prvi.podatak, stepen = 1, br2 = l2.prvi.podatak, zbir = 0;

		// dobijamo prvi int
		while (pom1 != null) {
			br1 += pom1.podatak * Math.pow(10, stepen);
			stepen++;
			pom1 = pom1.sledeci;
		}

		// dobijamo drugi broj
		stepen = 1;
		while (pom2 != null) {
			br2 += pom2.podatak * Math.pow(10, stepen);
			stepen++;
			pom2 = pom2.sledeci;
		}
		zbir = br1 + br2;
		if (zbir == 0)
			rez.ubaciElementNaPocetak(0);
		else {
			// sad da od dobijenog zbira napravimo listu
			while (zbir != 0) {
				rez.ubaciElementNaPocetak(zbir % 10);
				zbir = zbir / 10;
			}
		}

		return rez;
	}

	public void ubaciElementNaPocetak(int element) {
		this.prvi = new CvorJSListe(element, prvi);
	}

	// da se pregledaju ovi uslovi dal moze nesto jednostavnije
	public void izbaciNegativneIzJSCirkularne() {
		CvorJSListe pom = this.prvi;
		while (pom.sledeci != this.prvi) {
			if (pom.sledeci.podatak < 0) {
				pom.sledeci = pom.sledeci.sledeci;
			}
			pom = pom.sledeci;
		}
		if (this.prvi.sledeci == this.prvi && this.prvi.podatak < 0) {
			prvi = null;
			return;
		}
		if (this.prvi.podatak < 0) {
			pom.sledeci = prvi.sledeci;
			this.prvi = this.prvi.sledeci;

		}

	}

	// trebalo bi da je ovo u jednom prolazu
	public void preskociMizbaciN(int m, int n) {
		// mozda jedna optimizacija mala
		if (m == 0) {
			this.prvi = null;
			return;
		}
		CvorJSListe pom = this.prvi;
		while (pom != null) {
			// stanemo na poslednji od tih m, da bi sledeci bio onaj koji se izbacuje
			for (int i = 0; i < m - 1 && pom.sledeci != null; i++, pom = pom.sledeci)
				;
			for (int i = 0; i < n && pom.sledeci != null; i++)
				pom.sledeci = pom.sledeci.sledeci;
			pom = pom.sledeci;
		}
	}

	public static CvorJSListe reverseKGroup(CvorJSListe prvi, int k) {
		int brojac = 0;
		CvorJSListe pom = prvi;
		for (int i = 0; i < k && pom != null; i++, brojac++, pom = pom.sledeci)
			;
		// base case
		if (brojac < k) {
			return prvi;
		}

		pom = reverseKGroup(pom, k);
		// reverseKGroup(pom, k);

		CvorJSListe spori = null, trenutni = prvi, brzi = null;
		for (int i = 0; i < k - 1; i++) {
			brzi = trenutni.sledeci;
			trenutni.sledeci = spori;
			spori = trenutni;
			trenutni = brzi;
		}
		// u ovom trenutku su trenutni i brzi jednaki
		trenutni.sledeci = spori;
		// onaj koji je bio na pocetku sad je na kraju i pokazuje na ostatak liste
		prvi.sledeci = pom;
		// podesavamo da prvi u toj podlisti pokazuje na onaj koji je sad prvi posle
		// invertovanja
		prvi = trenutni;

		return prvi;
	}

	public int brojParovaSaZbiromVecimOdK(int k) throws LabisException {
		int brojac = 0;
		if (this.prvi.sledeci == null)
			throw new LabisException("Lista ima samo jedan elem pa nemamo ni jedan par");
		CvorJSListe pom1 = prvi, pom2 = null;
		while (pom1.sledeci != null) {
			pom2 = pom1.sledeci;
			while (pom2 != null) {
				if (pom2.podatak + pom1.podatak > k)
					brojac++;
				pom2 = pom2.sledeci;
			}
			pom1 = pom1.sledeci;
		}
		return brojac;
	}

	public void izbaciKvadratVeciOdZbirCifara() {
		CvorJSListe pom = this.prvi;
		int zbir = 0;
		while (pom != null) {
			zbir += pom.podatak;
			pom = pom.sledeci;
		}
		pom = this.prvi;
		while (pom.sledeci != null) {
			if (Math.pow(pom.sledeci.podatak, 2) > zbir)
				pom.sledeci = pom.sledeci.sledeci;
		}
	}

	/*
	 * dat broj kao parametar Izbaci iz liste broj pre tog broja
	 */
	public CvorJSListe izbaciPreZadatogBroja(int podatak) {
		if (this.prvi == null || prvi.sledeci == null)
			return null;
		if (prvi.podatak == podatak)
			return null;
		if (prvi.sledeci.podatak == podatak) {
			CvorJSListe pom = prvi;
			prvi = prvi.sledeci;
			return pom;
		}
		CvorJSListe brzi = prvi.sledeci, spori = prvi;
		while (brzi.sledeci != null) {
			if (brzi.sledeci.podatak == podatak) {
				CvorJSListe pom = spori.sledeci;
				spori.sledeci = brzi.sledeci;
				return pom;
			}
			spori = spori.sledeci;
			brzi = brzi.sledeci;
		}
		return null;
	}

	public static JSLista odDvaBrojaNapraviListu(JSLista a, JSLista b) {

		int brojA = 0, brojB = 0, brojac = 0;
		CvorJSListe pom = a.prvi;
		while (pom != null) {
			brojA = brojA * 10 + pom.podatak;
			pom = pom.sledeci;
		}
		pom = b.prvi;
		brojac = 0;
		while (pom != null) {
			brojB = brojB * 10 + pom.podatak;
			pom = pom.sledeci;
		}

		int zbir = brojA + brojB;

		JSLista novaLista = new JSLista();
		while (zbir != 0) {
			novaLista.prvi = new CvorJSListe(zbir % 10, novaLista.prvi);
			zbir /= 10;
		}
		return novaLista;
	}

	/*
	 * dat broj kao parametar Izbaci sve brojeve iz liste koji su manji od tog broja
	 */

	public void izbaciSveManjeBrojeve(int k) {
		// dok god je prvi manji izbacuj ga
		while (prvi != null && prvi.podatak < k) {
			prvi = prvi.sledeci;
		}
		if (prvi == null)
			return;
		CvorJSListe pom = prvi;
		while (pom != null && pom.sledeci != null) {
			if (pom.sledeci.podatak < k) {
				// izbacivanje prvog sledeceg
				pom.sledeci = pom.sledeci.sledeci;
			}
			pom = pom.sledeci;
		}
	}

	/*
	 * Date dve js liste kao parametri, elementi tih lista su temperature po danima.
	 * Metoda treba da vrati najveci zbir temperatura za neki dan
	 */

	public static int najveciZbirTemperatura(JSLista l1, JSLista l2) throws LabisException {
		if (l1.prvi == null || l2.prvi == null)
			throw new LabisException();
		CvorJSListe pom1 = l1.prvi, pom2 = l2.prvi;
		int max = 0;
		while (pom1 != null && pom2 != null) {
			if ((pom1.podatak + pom2.podatak) > max)
				max = pom1.podatak + pom2.podatak;
			pom1 = pom1.sledeci;
			pom2 = pom2.sledeci;
		}

		return max;
	}

	/*
	 * Od dve jednostruko spregnute liste celih brojeva koje su sortirane u rastućem
	 * redosledu (sa datim početnim pokazivačima) formirajte treću sortiranu u
	 * opadajućem redosledu. Date dve liste treba da ostanu kakve su bile (tj. ne
	 * menjaju se).
	 */

	public JSLista formirajTrecuUOpadajucemRedosledu(CvorJSListe a, CvorJSListe b) {

		JSLista rezultat = new JSLista();
		while (a != null && b != null) {
			if (a.podatak < b.podatak) {
				rezultat.ubaciElementNaPocetak(a.podatak);
				a = a.sledeci;
			} else {
				rezultat.ubaciElementNaPocetak(b.podatak);
				b = b.sledeci;
			}
		}
		// ubacujemo ostatke
		while (a != null)
			ubaciElementNaPocetak(a.podatak);
		while (b != null)
			ubaciElementNaPocetak(b.podatak);
		return rezultat;
	}

	/*
	 * data je JS lista i parametri BROJ i N ubaci broj na N-to mesto od kraja liste
	 */

	public void ubaciNaNtoMestoOdKraja(int podatak, int n) {
		if (prvi == null)
			return;

		CvorJSListe spori = prvi, brzi = prvi;
		// situacija ako ubacujemo na kraj
		if (n == 0) {
			while (brzi.sledeci != null)
				brzi = brzi.sledeci;
			brzi.sledeci = new CvorJSListe(podatak, null);
		}
		int i;
		for (i = 0; i < n && brzi != null; i++, brzi = brzi.sledeci)
			;
		// i ce da stane na n-1 ako se ubacuje na pocetak
		if (i == n - 1) {
			this.prvi = new CvorJSListe(podatak, prvi);
			return;
		}
		// ako damo n vece od duzine liste
		if (i < n - 1)
			return;
		// normalna situlacija
		while (brzi != null) {
			brzi = brzi.sledeci;
			spori = spori.sledeci;
		}
		spori.sledeci = new CvorJSListe(podatak, spori.sledeci);
	}

}
