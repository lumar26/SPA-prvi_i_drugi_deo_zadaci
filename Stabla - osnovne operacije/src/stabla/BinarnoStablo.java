package stabla;

import java.util.Stack;

import queue.LinkedQueue;
import queue.Queue;

public class BinarnoStablo {
	public CvorBinarnogStabla koren;

	public BinarnoStablo(CvorBinarnogStabla koren) {
		super();
		this.koren = koren;
	}

	// Breadth first traversal ali u cikcaku
	public void cikcak() {
		if (this.koren == null) {
			// baci exception
			return;
		}
		// trenutni oznacava onaj nivo u kome smo i koji stampamo, a sledeci oynacava
		// nivo u koji dodajemo i koji stampamo u sledecem prolazu
		Stack<CvorBinarnogStabla> trenutni = new Stack<CvorBinarnogStabla>(), sledeci = new Stack<CvorBinarnogStabla>();
		trenutni.push(koren);
		boolean udesno = false;
		while (!trenutni.isEmpty()) {
			CvorBinarnogStabla pom = trenutni.pop();
			System.out.print(pom.podatak + "\t");
			if (udesno) {
				if (pom.levi != null)
					sledeci.push(pom.levi);
				if (pom.desni != null)
					sledeci.push(pom.desni);
			} else {
				if (pom.desni != null)
					sledeci.push(pom.desni);
				if (pom.levi != null)
					sledeci.push(pom.levi);
			}
			// kad se ispraznio trenutni nivo
			if (trenutni.isEmpty()) {
				udesno = !udesno;
				// zamena stekova
				Stack<CvorBinarnogStabla> temp = trenutni;
				trenutni = sledeci;
				sledeci = temp;
			}
		}
	}

	/*
	 * dato je stablo, vrati najveci zbir od zbirova svih podstabala
	 */

	public int najveciZbirPodstabla() {
		if (koren == null) {
			// throw new Exception("Stablo je prazno");
		}
		Maks m = new Maks();
		najveciZbirPodstabla(koren, m);
		return m.max;
	}

	private class Maks {
		public int max = Integer.MIN_VALUE, razlika = Integer.MIN_VALUE;
		public CvorBinarnogStabla cvor;
	}

	private int najveciZbirPodstabla(CvorBinarnogStabla pomKoren, Maks m) {
		if (pomKoren == null)
			return 0;
		m.max = Math.max(m.max,
				pomKoren.podatak + najveciZbirPodstabla(pomKoren.levi, m) + najveciZbirPodstabla(pomKoren.desni, m));
		return pomKoren.podatak + najveciZbirPodstabla(pomKoren.levi, m) + najveciZbirPodstabla(pomKoren.desni, m);
	}

	private int zbirStabla(CvorBinarnogStabla pomKoren) {
		if (pomKoren == null)
			return 0;
		if (pomKoren.levi == null && pomKoren.desni == null)
			return pomKoren.podatak;
		if (pomKoren.levi == null)
			return pomKoren.podatak + zbirStabla(pomKoren.desni);
		if (pomKoren.desni == null)
			return pomKoren.podatak + zbirStabla(pomKoren.levi);
		return pomKoren.podatak + zbirStabla(pomKoren.levi) + zbirStabla(pomKoren.desni);
	}

	/*
	 * dato je stablo vrati cvor koji ima najvecu RAZLIKU zbira levog i zbira desnog
	 * podstabla, od svih podstabala
	 */
	public CvorBinarnogStabla cvorSaNajvecomRazlikomMedjuDecom() {
		Maks m = new Maks();
		cvorSaNajvecomRazlikomMedjuDecom(koren, m);
		return m.cvor;
	}

	private void cvorSaNajvecomRazlikomMedjuDecom(CvorBinarnogStabla koren, Maks m) {
		if (koren == null)
			return;
		cvorSaNajvecomRazlikomMedjuDecom(koren.levi, m);
		cvorSaNajvecomRazlikomMedjuDecom(koren.desni, m);
		if (m.razlika < Math.abs(zbirStabla(koren.levi) - zbirStabla(koren.desni))) {
			m.razlika = Math.abs(zbirStabla(koren.levi) - zbirStabla(koren.desni));
			m.cvor = koren;
		}

	}

	/*
	 * Data su dva cvora, vratiti proizvod svih zajednickih predaka od ova dva cvora
	 */

	public Integer vratiProizvodPredaka(CvorBinarnogStabla c1, CvorBinarnogStabla c2) {
		if (koren == null) {
			return null;
			// baci exception
		}
		int proizvod = 1;
		Queue<CvorBinarnogStabla> red = new LinkedQueue<CvorBinarnogStabla>();
		red.enqueue(koren);
		while (!red.isEmpty()) {
			CvorBinarnogStabla pom = red.dequeue();
			if (BinarnoStablo.jestePredak(pom, c1) && BinarnoStablo.jestePredak(pom, c2))
				proizvod *= pom.podatak;
			if (pom.levi != null)
				red.enqueue(pom.levi);
			if (pom.desni != null)
				red.enqueue(pom.desni);
		}
		return proizvod;
	}

	public static boolean jestePredak(CvorBinarnogStabla predak, CvorBinarnogStabla naslednik) {
		if (predak.equals(naslednik))
			return false;
		Queue<CvorBinarnogStabla> red = new LinkedQueue<CvorBinarnogStabla>();
		red.enqueue(predak);
		while (!red.isEmpty()) {
			CvorBinarnogStabla pom = red.dequeue();
			if (pom.equals(naslednik))
				return true;
			if (pom.levi != null)
				red.enqueue(pom.levi);
			if (pom.desni != null)
				red.enqueue(pom.desni);
		}
		return false;
	}

	/*
	 * Dat je pokazivač na koren binarnog stabla čiji čvorovi sadrže cele brojeve i
	 * drugi pokazivač na neki čvor u stablu. Napisati funkciju koja će odštampati
	 * sve čvorove koji su na putanji od korena do datog čvora, uključujući i ta dva
	 * čvora.
	 */

	public void ispisiPutanju(CvorBinarnogStabla kraj) {
		prefiks(koren, kraj);
	}

	private void prefiks(CvorBinarnogStabla pomKoren, CvorBinarnogStabla kraj) {
		if (pomKoren == null)
			return;
		if(pomKoren.equals(kraj)) {
			System.out.println(kraj.podatak);
			return;
		}
		if (sadrziCvor(pomKoren, kraj))
			System.out.println(pomKoren.podatak);
		prefiks(pomKoren.levi, kraj);
		prefiks(pomKoren.desni, kraj);
	}

	public boolean sadrziCvor(CvorBinarnogStabla pomKoren, CvorBinarnogStabla cvor) {
		if (pomKoren == null)
			return false;
		if (pomKoren.equals(cvor))
			return true;
		return sadrziCvor(pomKoren.levi, cvor) || sadrziCvor(pomKoren.desni, cvor);
	}

}
