package stabla;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.print.attribute.standard.QueuedJobCount;

import queue.ArrayQueue;
import queue.LinkedQueue;
import queue.Queue;

public class BinarnoStabloPretrage implements Stablo {
	private CvorBinarnogStabla koren;

	public BinarnoStabloPretrage(CvorBinarnogStabla koren) {
		this.koren = koren;
	}

	@Override
	public int brojElemenata() {
		return brojElemenata(koren);
	}

	private int brojElemenata(CvorBinarnogStabla pomKoren) {
		if (pomKoren == null)
			return 0;
		return 1 + brojElemenata(pomKoren.levi) + brojElemenata(pomKoren.desni);
	}

	@Override
	public int visinaStabla() {
		return visinaStabla(koren);
	}

	private int visinaStabla(CvorBinarnogStabla pomKoren) {
		if (pomKoren == null)
			return 0;
		if (visinaStabla(pomKoren.levi) > visinaStabla(pomKoren.desni))
			return 1 + visinaStabla(pomKoren.levi);
		else
			return 1 + visinaStabla(pomKoren.desni);
	}

	@Override
	public int minimalniElement() throws Exception {
		return minimalni(koren);
	}

	private int minimalni(CvorBinarnogStabla pomKoren) throws Exception {
		if (pomKoren == null)
			throw new Exception("Ne postoji minimalni element jer je stablo prazno");
		CvorBinarnogStabla pom = pomKoren;
		while (pom.levi != null) {
			pom = pom.levi;
		}
		return pom.podatak;
	}

	@Override
	public int maksimalniElement() throws Exception {
		if (this.koren == null)
			throw new Exception("Ne postoji maksimalni element jer je stablo prazno");
		CvorBinarnogStabla pom = koren;
		while (pom.desni != null) {
			pom = pom.desni;
		}
		return pom.podatak;
	}

	//breadth first traversal
	@Override
	public void prolazPoNivoima() {
		Queue<CvorBinarnogStabla> red = new LinkedQueue<CvorBinarnogStabla>();
		red.enqueue(koren);
		while (!red.isEmpty()) {
			CvorBinarnogStabla pom = red.peek();
			System.out.println(pom.podatak);
			if (pom.levi != null)
				red.enqueue(pom.levi);
			if (pom.desni != null)
				red.enqueue(pom.desni);
			red.dequeue();
		}
	}

	@Override
	public void prefiks() {
		// koren - levi - desni
		prefiks(koren);
	}

	private void prefiks(CvorBinarnogStabla pomKoren) {
		if (pomKoren == null)
			return;
		System.out.print(pomKoren.podatak + "\t");
		prefiks(pomKoren.levi);
		prefiks(pomKoren.desni);
	}

	@Override
	public void infiks() {
		//L-K-D
		infiks(koren);
	}

	private void infiks(CvorBinarnogStabla pomKoren) {
		if (pomKoren == null)
			return;

		infiks(pomKoren.levi);
		System.out.println(pomKoren.podatak);
		infiks(pomKoren.desni);
	}

	@Override
	public void postfiks() {
		postfiks(koren);

	}

	private void postfiks(CvorBinarnogStabla pomKoren) {
		if (pomKoren == null)
			return;

		postfiks(pomKoren.levi);
		postfiks(pomKoren.desni);
		System.out.println(pomKoren.podatak);
	}

	@Override
	public CvorBinarnogStabla getKoren() {
		return this.koren;
	}

	@Override
	public void dodajElement(int podatak) {
		dodajElement(koren, podatak);
	}

	private void dodajElement(CvorBinarnogStabla pomKoren, int podatak) {

		if (podatak <= pomKoren.podatak) { // dodajemo u levo podstablo
			if (pomKoren.levi == null) {// ako to podstablo ne postoji, tj novoprosledjeni koren je null
				pomKoren.levi = new CvorBinarnogStabla(podatak);
				return;
			} else
				dodajElement(pomKoren.levi, podatak);
		} else {// dodajemo u desno podstablo
			if (pomKoren.desni == null) {// ako to podstablo ne postoji, tj novoprosledjeni koren je null
				pomKoren.desni = new CvorBinarnogStabla(podatak);
				return;
			} else
				dodajElement(pomKoren.desni, podatak);
		}
	}

	/*
	 * Ako je stablo BSP to znaci da kada se ispise prefiks prolazom svi elementi su
	 * sortirano ispisani
	 */
	@Override
	public boolean jesteBSP() {
		return jesteBSP(koren, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	private boolean jesteBSP(CvorBinarnogStabla pomKoren, int minimum, int maximum) {
		if (pomKoren == null || (pomKoren.podatak < maximum && pomKoren.podatak >= minimum
				&& jesteBSP(pomKoren.levi, minimum, pomKoren.podatak)
				&& jesteBSP(pomKoren.desni, pomKoren.podatak, maximum)))
			return true;
		return false;

	}

	@Override
	public void izbaci(int podatak) {
		this.koren = izbaci(koren, podatak);
	}

	private CvorBinarnogStabla izbaci(CvorBinarnogStabla pomKoren, int podatak) {
		if (pomKoren == null)
			return null;
		if (podatak < pomKoren.podatak) {
			pomKoren.levi = izbaci(pomKoren.levi, podatak);
			return pomKoren;
		}

		if (podatak > pomKoren.podatak) {
			pomKoren.desni = izbaci(pomKoren.desni, podatak);
			return pomKoren;
		}

		// ako je list
		if (pomKoren.levi == null && pomKoren.desni == null)
//			return pomKoren = null;
			return null;
		// ako ima samo desno podstablo jednostavno prevezemo
		if (pomKoren.levi == null)
			return pomKoren.desni;
		// ako ima samo levo podstablo jednostavno prevezemo
		if (pomKoren.desni == null)
			return pomKoren.levi;
		// ako ima i levo i desno podstablo
		// biramo minimalni iz desnog podstabla, zamenjujemo ga sa trenutnim i onda

		try {
			// ne sme odma return jer prvo treba da se izbaci taj minimalni sa kojim smo zamenili
			pomKoren.podatak = minimalni(pomKoren.desni);
		} catch (Exception e) {
		}
		// iz desnog podstabla izbacujemo taj minimalni
		pomKoren.desni = izbaci(pomKoren.desni, pomKoren.podatak);
		return pomKoren;
	}

	@Override
	public CvorBinarnogStabla nadjiCvor(int podatak) {
		return nadjiCvor(koren, podatak);
	}

	private CvorBinarnogStabla nadjiCvor(CvorBinarnogStabla pomKoren, int podatak) {

		if (podatak < pomKoren.podatak)
			return nadjiCvor(pomKoren.levi, podatak);
		if (podatak > pomKoren.podatak)
			return nadjiCvor(pomKoren.desni, podatak);
		return pomKoren;
	}

	/*
	 * Naci broj koji je sledeci pri ispisivanju infiks prolazom, tj naci najmanji
	 * lement u stablu koji je veci od broja a
	 */
	public Integer nadjiInfiksSledbenika(int a) {
		if (koren == null)
			return null;

		CvorBinarnogStabla pom = nadjiCvor(a);
		if (pom.desni != null)
			try {
				return minimalni(pom.desni);
			} catch (Exception e) {
			}
		return prviVeciPrethodnik(null, a, koren);
	}

	private Integer prviVeciPrethodnik(CvorBinarnogStabla prethodni, int a, CvorBinarnogStabla trenutni) {
		//prethodni se menja samo ako naidjemo na neki koji je blizi vrednosno prosledjenom parametru, ali ako je i dalje veci od njega
		while (trenutni.podatak != a) {
			if (a < trenutni.podatak) {
				prethodni = trenutni;
				trenutni = trenutni.levi;

			} else
				trenutni = trenutni.desni;
		}

		return prethodni.podatak;
	}

	public int zbirElemenata() {
		if(koren == null) {
			//throw new Exception();
		}
		return zbirElemenata(this.koren);
	}

	private int zbirElemenata(CvorBinarnogStabla pomKoren) {
		if (pomKoren == null)
			return 0;
		return pomKoren.podatak + zbirElemenata(pomKoren.levi) + zbirElemenata(pomKoren.desni);
	}

	public double prosekElemenata() {
		if (koren == null) {
			// baci exception
		}
		Queue<CvorBinarnogStabla> red = new LinkedQueue<CvorBinarnogStabla>();
		red.enqueue(koren);
		double suma = 0, brojac = 0;
		while (!red.isEmpty()) {
			CvorBinarnogStabla pom = red.dequeue();
			suma += pom.podatak;
			brojac++;
			if (pom.levi != null)
				red.enqueue(pom.levi);
			if (pom.desni != null)
				red.enqueue(pom.desni);

		}
		return suma / brojac;
	}
}
