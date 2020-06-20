package main;

import java.util.concurrent.ForkJoinWorkerThread;

import labis.cvorovi.CvorDSListe;
import labis.cvorovi.CvorJSListe;
import labis.exception.LabisException;
import labis.liste.ADSLista;

public class DSLista extends ADSLista {

	/*
	 * Ovaj zadatak moze da se uradi samo sa jednom petljom Da se ne pravi for
	 * petlja ve' kad ponovo udje u petlju pom je ovaj novi pa svakako uacuje sve
	 * uredno Ali mozda bi trebalo 51
	 */

	@Override
	public void popuniListu() throws LabisException {
		if (this.prvi == null || this.prvi.sledeci == null)
			return;
		CvorDSListe pom = this.prvi;
		while (pom.sledeci != null) {
			if (pom.sledeci.podatak - pom.podatak > 1)
				pom.sledeci = new CvorDSListe(pom.podatak + 1, pom, pom.sledeci);
			pom = pom.sledeci;
		}
	}

	@Override
	public CvorDSListe elementUSredini() throws LabisException {
		if (this.prvi == null)
			throw new LabisException();
		CvorDSListe pom = prvi, dupliPom = prvi;
		// probaj da odradis sa uslovima duplipom!= null i .sledeci != null
		while (dupliPom.sledeci != null && dupliPom.sledeci.sledeci != null) {
			pom = pom.sledeci;
			dupliPom = dupliPom.sledeci.sledeci;
		}
		return pom;
	}

	// cirkularna lista
	public double prosekParnihElemenata() {
		int zbir = 0, brojac = 0;
		CvorDSListe pom = this.prvi;
		while (pom.sledeci != prvi) {
			if (pom.podatak % 2 == 0) {
				brojac++;
				zbir += pom.podatak;
			}
			pom = pom.sledeci;
		}
		zbir += pom.podatak;

		return zbir / brojac;
	}

	public void rotirajSvakihKElemenata(int k) {
		if (this.prvi == null || this.prvi.sledeci == null)
			return;
		CvorDSListe pom = this.prvi;
		int brojac = 0;
		while (pom != null && pom.sledeci != null && brojac < k - 1) {
			brojac++;
			pom = pom.sledeci;
		}
		// ovde bi trebao da bude base case
		if (brojac < k - 1) {
			CvorDSListe desni = null, levi = pom;

			// ovde se sad prevezuje
			for (int i = 0; i <= brojac; i++) {
				levi.sledeci = levi.prethodni;
				levi.prethodni = desni;
				desni = levi;
				levi = levi.sledeci;
			}
			desni.sledeci = null; // jer je to sad kraj liste
			levi.sledeci = pom;
			pom.prethodni = levi;
			prvi = pom;
			return;
		}
		// ovde mora da se promeni prvi da bi islo daljekroz listu
		this.prvi = pom.sledeci;
		rotirajSvakihKElemenata(k);

		prevezivanje(pom, k);

	}

	private void prevezivanje(CvorDSListe pom, int k) {
		CvorDSListe desni = null, levi = pom;

		// ovde se sad prevezuje
		for (int i = 0; i < k; i++) {
			levi.sledeci = levi.prethodni;
			levi.prethodni = desni;
			desni = levi;
			levi = levi.sledeci;
		}

		desni.sledeci = prvi;
		if (prvi != null) // ako je prvi null znaci da rotiramo paran br elem u listi
			prvi.prethodni = desni;
		// ovim ovde smo spojili desni kraj novonastgale liste, tj kad smo istumbali k
		// elemenata

		pom.prethodni = levi;
		if (levi != null) // znaci da ako smo dosli do kraja, tj do pocetka liste nista ne treba da se
							// dodeljuje onom koji je pre, jer taj i ne postoji
			levi.sledeci = pom;
		// ovim smo spojili levi kraj novonastale liste

		prvi = pom;
	}

	/*
	 * dat novi broj kao parametar Ubaci taj broj izmedju dva susedna negativna
	 * broja, ako takva dva broja ne postoje ubaci na kraj
	 */
	public void ubaciIzmedjuDvaNegativna(int k) {
		if (this.prvi == null || prvi.sledeci == null)
			return;
		CvorDSListe spori = prvi, brzi = prvi.sledeci;
		while (brzi != null) {
			if (spori.podatak < 0 && brzi.podatak < 0) {
				// ubacivanje izmedju sporog i brzog
				CvorDSListe novi = new CvorDSListe(k, spori, spori.sledeci);
				brzi.prethodni = novi;
				spori.sledeci = novi;
				return;
			}
			spori = brzi;
			brzi = brzi.sledeci;
		}
		brzi.sledeci = new CvorDSListe(k, brzi, null);
	}

	/* Krece se od drugog el i izbacuje se svaki treci (znaci 5. pa 8. itd) */
	public void izbaciSvakiTreciPosleDrugog() throws LabisException {
		if (this.prvi == null)
			throw new LabisException();
		try {
			CvorDSListe pom = prvi.sledeci; // drugi cvor
			while (true) {
				pom = pom.sledeci.sledeci.sledeci; // prebacujemo za 3
				pom.prethodni.sledeci = pom.sledeci;
				pom.sledeci.prethodni = pom.prethodni;
			}
		} catch (NullPointerException e) {
			// izbacili smo prakticno sve sto nam treba
			return;
		}
	}

	/*
	 * 5. Data je dvostruko spregnuta lista celih brojeva sortirana u rastućem
	 * redosledu i pokazivač p koji pokazuje na prvi element u listi. Napisati
	 * metodu koja prebrojava koliko ima elemenata koji su manji od proseka cele
	 * liste
	 */

	public int brojElemenataManjihOdProseka() {
		int zbir = 0, brojac = -1;
		CvorDSListe pom = this.prvi;
		while (pom != null) {
			zbir += pom.podatak;
			pom = pom.sledeci;
			brojac++;
		}
		// da bi nam dole vratilo minus jedan ako je lista prazna
		brojac++;
		pom = this.prvi;
		int avg = (int) Math.ceil(zbir / brojac);
		brojac = 0;
		while (pom != null) {
			if (pom.podatak < avg)
				brojac++;
		}
		return brojac;
	}

	/*
	 * 1. Dat je pokazivač na neki čvor dvostruko spregnute liste. Napisati funkciju
	 * za ubacivanje novog elementa pre datog pokazivača.
	 */

	public void ubaciPreZadatog(CvorDSListe zadati, int novi) {
		if (prvi == null || zadati == null) {
			// throw new Exception();
		}
		CvorDSListe cvor = new CvorDSListe(novi, null, null);
		if (zadati.prethodni == null) {
			cvor.sledeci = zadati;
			zadati.prethodni = cvor;
			return;
		}
		zadati.prethodni.sledeci = cvor;
		cvor.sledeci = zadati;
		cvor.prethodni = zadati.prethodni;
		zadati.prethodni = cvor;
	}

	// metoda koja prebacuje cvor sa najvecom vrdnoscu kljuca na pocetak

	public void prebaciNajveciNaPocetak() {
		if (prvi == null)
			return;
		CvorDSListe pom = prvi, max = prvi;
		while (pom != null) {
			if (pom.podatak > max.podatak)
				max = pom;
		}
		if (pom.prethodni == null)
			return;
		// odvezujemo
		max.prethodni.sledeci = max.sledeci;
		if (max.sledeci != null) // ako je max krajni ne radimo ovu liniju dole
			max.sledeci.prethodni = max.prethodni;
		// sad prebacujemo na pocetak
		prvi.prethodni = max;
		max.sledeci = prvi;
		max.prethodni = null;
		prvi = max;

	}

	// metoda koja stampa onu polovinu liste ciji je zbir veci, dat je sredisnji
	// elemennt

	public void polovinaSaVecimZbirom(CvorDSListe p) {
		if (p == null || p.prethodni == null || p.sledeci == null)
			return;

		int zbirLevo = p.prethodni.podatak, zbirDesno = p.sledeci.podatak;
		CvorDSListe pomLevo = p.prethodni, pomDesno = p.sledeci;

		while (pomLevo.prethodni != null || pomDesno != null) {
			if (pomLevo.prethodni != null) {
				zbirLevo += pomLevo.prethodni.podatak;
				pomLevo = pomLevo.prethodni;
			}
			if (pomDesno.sledeci != null) {
				zbirDesno += pomDesno.sledeci.podatak;
				pomDesno = pomDesno.prethodni;
			}
		}
		if (zbirLevo > zbirDesno) {
			while (pomLevo != p) {
				System.out.print(pomLevo.podatak + "\t");
				pomLevo = pomLevo.sledeci;
			}
		} else {
			while (pomDesno != p) {
				System.out.print(pomDesno.podatak + "\t");
				pomDesno = pomDesno.prethodni;
			}
		}

	}

	/*
	 * Dat je pokazivač na prvi element u dvostruko-spregnutoj listi celih brojeva.
	 * Napisati metodu „Ubaci“ koja ubacuje novi element nakon prvog elementa koji
	 * je veći od vrednosti koja se ubacuje. Ako takav element ne postoji, novi
	 * element se ubacuje na kraj
	 */
	public void ubacinakonPrvogVeceg(CvorDSListe prvi, int novi) {
		if (this.prvi == null)
			this.prvi = new CvorDSListe(novi, null, null);
		while (prvi.sledeci != null) {
			if (prvi.podatak > novi) {
				CvorDSListe pom = new CvorDSListe(novi, prvi, prvi.sledeci);
				prvi.sledeci.prethodni = pom;
				prvi.sledeci = pom;
				return;
			}
			prvi = prvi.sledeci;
		}
		prvi.sledeci = new CvorDSListe(novi, prvi, null);
	}

	// invertovanje liste bez pomocnih promenljivih

	public void invertuj() {
		if (this.prvi == null || this.prvi.sledeci == null)
			return;
		CvorDSListe spori = null, tekuci = prvi, brzi = prvi.sledeci;
		while (tekuci != null) {
			tekuci.sledeci = spori;
			tekuci.prethodni = brzi;
			spori = tekuci;
			tekuci = brzi;
			if (brzi != null)
				brzi = brzi.sledeci;
		}
		this.prvi = spori;
	}
}
