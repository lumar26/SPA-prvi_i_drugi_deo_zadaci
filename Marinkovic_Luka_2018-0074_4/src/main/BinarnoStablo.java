package main;

import java.util.LinkedList;
import java.util.Queue;

import labis.cvorovi.CvorStabla;
import labis.exception.LabisException;
import labis.stabla.ABinarnoStablo;

public class BinarnoStablo extends ABinarnoStablo {
	@Override
	public CvorStabla vratiMaksimalanPolulist(CvorStabla k) throws LabisException {
		if (k == null)
			return null;
		CvorStabla max = new CvorStabla(Integer.MIN_VALUE);
		Queue<CvorStabla> red = new LinkedList<CvorStabla>();
		red.add(k);
		while (!red.isEmpty()) {
			CvorStabla pom = red.poll();
			if ((pom.levo == null ^ pom.desno == null) && pom.podatak > max.podatak)
				max = pom;
			if (pom.levo != null)
				red.add(pom.levo);
			if (pom.desno != null)
				red.add(pom.desno);
		}
		return max;
	}

	@Override
	public boolean daLiJeAVL(CvorStabla k) throws LabisException {
		return homogenaVisina(k) && jesteBST(k, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	private int visinaStabla(CvorStabla koren) {
		if (koren == null)
			return 0;
		int levaVisina = visinaStabla(koren.levo), desnaVisina = visinaStabla(koren.desno);

		if (desnaVisina > levaVisina) {
			return 1 + desnaVisina;
		}
		return 1 + levaVisina;
	}

	// ako ima podstablo u kom se nivoi listova razlikuju za vise od 1 onda vraca
	// false
	private boolean homogenaVisina(CvorStabla koren) {
		if (koren == null)
			return true;
		if (Math.abs(visinaStabla(koren.desno) - visinaStabla(koren.levo)) > 1)
			return false;
		return homogenaVisina(koren.desno) && homogenaVisina(koren.levo);
	}

	private boolean jesteBST(CvorStabla koren, int min, int max) {
		if (koren == null)
			return true;
		return koren == null || (koren.podatak < max && koren.podatak > min && jesteBST(koren.desno, koren.podatak, max)
				&& jesteBST(koren.levo, min, koren.podatak));

	}

	// odstampaj putanju od korena do nekog cvora koja ima zadatu sumu
	public void putanjaDateSume(int suma) {
		if (koren == null || koren.podatak > suma)
			return;
		int tekucaSuma = 0;
		// nadji cvor do kog je suma jednaka zadatoj
		CvorStabla cilj = ciljniCvor(koren, tekucaSuma, suma);
		if (cilj != null)
			istampajPutanju(koren, cilj);
	}

	private void istampajPutanju(CvorStabla koren, CvorStabla cilj) {
		if (koren == null)
			return;
		System.out.print(koren.podatak + "\t");
		if (sadrziCvor(koren.levo, cilj))
			istampajPutanju(koren.levo, cilj);
		if (sadrziCvor(koren.desno, cilj))
			istampajPutanju(koren.desno, cilj);
	}

	private boolean sadrziCvor(CvorStabla koren, CvorStabla cilj) {
		if (koren == null)
			return false;
		if (koren == cilj)
			return true;
		return sadrziCvor(koren.levo, cilj) || sadrziCvor(koren.desno, cilj);
	}

	// nadjemo cvor do kog je suma putanje jednaka zadatoj sumi
	private CvorStabla ciljniCvor(CvorStabla koren, int tekucaSuma, int suma) {
		if (koren == null)
			return null;
		tekucaSuma += koren.podatak;
		if (tekucaSuma == suma)
			return koren;
		if (tekucaSuma > suma)
			return null;
		CvorStabla desni = ciljniCvor(koren.desno, tekucaSuma, suma), levi = ciljniCvor(koren.levo, tekucaSuma, suma);
		// tekuca suma < suma
		if (desni != null)
			return desni;
		return levi;
	}

	// metoda koja porverava da li su zadata dva cvora rodjaci, dva cvora su rodjaci
	// ukoliko su na istom nivou i ako su im roditelji braca
	public boolean daLiSuRodjaci(CvorStabla prvi, CvorStabla drugi) {
		if (koren == null || prvi == null || drugi == null)
			return false;
		CvorStabla prviRoditelj = nadjiRoditelja(koren, prvi), drugiRoditelj = nadjiRoditelja(koren, drugi);
		return (prviRoditelj != null && drugiRoditelj != null && nivo(koren, prvi) == nivo(koren, drugi)
				&& jesuBraca(koren, prviRoditelj, drugiRoditelj));
	}

	private boolean jesuBraca(CvorStabla koren, CvorStabla prviRoditelj, CvorStabla drugiRoditelj) {
		if (koren == null)
			return false;
		if ((koren.levo == prviRoditelj && koren.desno == drugiRoditelj)
				|| (koren.levo == drugiRoditelj && koren.desno == prviRoditelj))
			return true;
		return jesuBraca(koren.levo, prviRoditelj, drugiRoditelj)
				|| jesuBraca(koren.desno, prviRoditelj, drugiRoditelj);
	}

	private int nivo(CvorStabla koren, CvorStabla cvor) {
		if (koren == null)
			return 0;
		if (koren == cvor)
			return 1;
		if (sadrziCvor(koren.levo, cvor))
			return 1 + nivo(koren.levo, cvor);
		return 1 + nivo(koren.desno, cvor);
	}

	private CvorStabla nadjiRoditelja(CvorStabla koren, CvorStabla trazeni) {
		if (koren == null || trazeni == null)
			return null;
		if (koren.levo == trazeni || koren.desno == trazeni)
			return koren;
		CvorStabla levi = nadjiRoditelja(koren.levo, trazeni), desni = nadjiRoditelja(koren.desno, trazeni);
		if (levi != null)
			return levi;
		return desni;
	}

	public int zbirZajednickihPredaka(CvorStabla jedan, CvorStabla dva) throws LabisException {
		if (koren == null || !sadrziCvor(koren, jedan) || !sadrziCvor(koren, dva))
			throw new LabisException("ne postoji stablo ili ne sardzi ova dva cvora");
		return zbirZajednickihPredaka(koren, jedan, dva);
	}

	private int zbirZajednickihPredaka(CvorStabla koren, CvorStabla jedan, CvorStabla dva) {
		if (koren == null)
			return 0;
		if (sadrziCvor(koren.desno, jedan) && sadrziCvor(koren.desno, dva))
			return koren.podatak + zbirZajednickihPredaka(koren.desno, jedan, dva);
		if (sadrziCvor(koren.levo, jedan) && sadrziCvor(koren.levo, dva))
			return koren.podatak + zbirZajednickihPredaka(koren.levo, jedan, dva);
		return 0;
	}

}
