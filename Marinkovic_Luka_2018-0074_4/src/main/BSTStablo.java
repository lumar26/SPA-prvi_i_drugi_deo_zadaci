package main;

import labis.cvorovi.CvorStabla;
import labis.exception.LabisException;
import labis.stabla.ABSTStablo;

public class BSTStablo extends ABSTStablo {
	@Override
	public void ubaci(int podatak) throws LabisException {
		if (this.koren == null) {
			this.koren = new CvorStabla(podatak);
		}
		ubaci(koren, podatak);
	}

	private void ubaci(CvorStabla koren, int podatak) {
		if (koren == null) {
			koren = new CvorStabla(podatak);
			return;
		}
		if (koren.podatak > podatak && koren.levo == null) {
			koren.levo = new CvorStabla(podatak);
			return;
		}
		if (koren.podatak < podatak && koren.desno == null) {
			koren.desno = new CvorStabla(podatak);
			return;
		}
		if (koren.podatak < podatak)
			ubaci(koren.desno, podatak);
		if (koren.podatak > podatak)
			ubaci(koren.levo, podatak);
	}

	@Override
	public void izbaci(int podatak) throws LabisException {
		koren = izbaci(this.koren, podatak);
	}

	private CvorStabla izbaci(CvorStabla koren, int podatak) {
		if (koren == null)
			return null;
		if (koren.podatak > podatak)
			koren.levo = izbaci(koren.levo, podatak);
		if (koren.podatak < podatak)
			koren.desno = izbaci(koren.desno, podatak);
		// base case, kad smo stigli do tog podatka, koren.podatak == podatak
		if (koren.podatak == podatak) {
			// list
			if (koren.levo == null && koren.desno == null)
				return null;
			// ima desno dete
			if (koren.levo == null)
				return koren.desno;
			// ima levo dete
			if (koren.desno == null)
				return koren.levo;

			if (koren.desno != null && koren.levo != null) {
				// ako ima oba deteta trazimo minimum desnog podstabla i menjamo sa korenom, i
				// posle izbacujemo taj minimum
				int minDesnogPodstabla = min(koren.desno);
				koren.podatak = minDesnogPodstabla;
				koren.desno = izbaci(koren.desno, minDesnogPodstabla);
			}
		}

		return koren;
	}

	private int min(CvorStabla koren) {
		while (koren.levo != null)
			koren = koren.levo;
		return koren.podatak;
	}

	@Override
	public void ispisiRastuce(CvorStabla k) throws LabisException {
		if (k == null)
			return;
		ispisiRastuce(k.levo);
		System.out.println(k.podatak);
		ispisiRastuce(k.desno);
	}

	public int brojElemenataUIntervalu(int a, int b) {
		if (koren == null)
			return 0;
		return brojElemenataUIntervalu(this.koren, a, b);
	}

	private int brojElemenataUIntervalu(CvorStabla koren, int a, int b) {
		if (koren == null)
			return 0;
		int kontrola = 0;
		if (koren.podatak >= a && koren.podatak <= b)
			kontrola = 1;
		return kontrola + brojElemenataUIntervalu(koren.levo, a, b) + brojElemenataUIntervalu(koren.desno, a, b);
	}

	/*
	 * Naoisati metodu koja ce ispisati na konzoli pozitivne elemente bst u
	 * opadajucem redosledu
	 */
	public void isipisiPozitivneOpadajuce() {
		if (this.koren == null)
			return;
		ispisiPozitivneOpadajuce(this.koren);
	}

	private void ispisiPozitivneOpadajuce(CvorStabla koren) {
		if (koren == null)
			return;
		ispisiPozitivneOpadajuce(koren.desno);
		if (koren.podatak >= 0)
			System.out.print(koren.podatak + "\t");
		ispisiPozitivneOpadajuce(koren.levo);
		
	}
}
