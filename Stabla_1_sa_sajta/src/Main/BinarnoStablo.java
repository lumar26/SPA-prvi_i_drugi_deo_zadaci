package Main;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import labis.cvorovi.CvorStabla;
import labis.exception.LabisException;
import labis.stabla.ABinarnoStablo;

public class BinarnoStablo extends ABinarnoStablo {
	@Override
	public boolean daLiPostojiIsti(CvorStabla k, CvorStabla cvor) throws LabisException {
		if (k.podatak == cvor.podatak)
			return true;
		if (daLiPostojiIsti(k.levo, cvor) || daLiPostojiIsti(k.desno, cvor))
			return true;
		return false;
	}

	@Override
	public CvorStabla vratiCvorNaNajvecojDubini(CvorStabla k) throws LabisException {
		Queue<CvorStabla> red = new LinkedList<CvorStabla>();
		red.add(k);
		CvorStabla pom = null;
		while (!red.isEmpty()) {
			pom = red.poll();
			if (pom.levo != null)
				red.add(pom.levo);
			if (pom.desno != null)
				red.add(pom.desno);
		}
		return pom;
	}

	@Override
	public CvorStabla vratiListNaNajmanjojDubini(CvorStabla k) throws LabisException {
		Queue<CvorStabla> red = new LinkedList<CvorStabla>();
		red.add(k);
		CvorStabla pom = null;
		while (!red.isEmpty()) {
			pom = red.poll();
			if (pom.levo == null && pom.desno == null)
				return pom;
			if (pom.levo != null)
				red.add(pom.levo);
			if (pom.desno != null)
				red.add(pom.desno);
		}
		return null;

	}

	@Override
	public int vratiBrojCvorovaVecihOdSvojihSledbenika(CvorStabla k) throws LabisException {
		if (k == null)
			return 0;
		int levo = 0, desno = 0;
		int jeVeci = 1;
		if (k.levo != null) {
			if (k.levo.podatak > k.podatak)
				jeVeci = 0;
			levo = vratiBrojCvorovaVecihOdSvojihSledbenika(k.levo);
		}
		if (k.desno != null) {
			if (k.desno.podatak > k.podatak)
				jeVeci = 0;
			desno = vratiBrojCvorovaVecihOdSvojihSledbenika(k.desno);
		}
		return jeVeci + levo + desno;
	}

	@Override
	public CvorStabla vratiNajveciNaPutanji(CvorStabla k, CvorStabla neki) throws LabisException {
		if (k == null || neki == null)
			return null;
		if (k == neki)
			return neki;
		CvorStabla maks = null, pom = k;
		if (sadrziCvor(k.desno, neki)) {
			pom = vratiNajveciNaPutanji(k.desno, neki);
		} else
			pom = vratiNajveciNaPutanji(k.levo, neki);
		if (pom.podatak > maks.podatak)
			maks = pom;
		return maks;
	}

	private boolean sadrziCvor(CvorStabla k, CvorStabla neki) {
		if (k == null)
			return false;
		if (k.equals(neki)) {
			return true;
		}
		return sadrziCvor(k.desno, neki) || sadrziCvor(k.levo, neki);

	}
	/*
	 * dato je stablo i dat je BROJ izracunaj zbir elemenata koji su na NIVOU ==
	 * BROJ ali samo zbir onih koji imaju oba deteta
	 */

	public int zbirElemenataNaNivouSaObaDeteta(int nivo) {
		if (this.koren == null) {
			// throw new Exception();
		}

		if (nivo == 0 && koren.levo != null && koren.desno != null)
			return koren.podatak;
		Stack<CvorStabla> trenutniNivo = new Stack<>(), sledeciNivo = new Stack<>();
		trenutniNivo.push(koren);
		int n = 1;
		while (!trenutniNivo.isEmpty()) {
			CvorStabla pom = trenutniNivo.pop();
			if (pom.levo != null)
				sledeciNivo.push(pom.levo);
			if (pom.desno != null)
				sledeciNivo.push(pom.desno);
			// kad je trenutni prazan sledeci je pun
			if (trenutniNivo.isEmpty()) {
				// kad dodjemo da je sledeciNivo onaj koji nam treba idemo break
				if (n == nivo)
					break;
				Stack<CvorStabla> temp = trenutniNivo;
				trenutniNivo = sledeciNivo;
				sledeciNivo = temp;
				n++;
			}
		}
		int zbir = 0;
		while (!sledeciNivo.isEmpty()) {
			CvorStabla pom = sledeciNivo.pop();
			if (pom.desno != null && pom.levo != null) {
				zbir += pom.podatak;
			}
		}
		return zbir;
	}

	/*
	 * dato je stablo i dat je BROJ nadji broj podstabala ciji je zbir veci od datog
	 * BROJA
	 */

	public int brojPodstabalaSaZbiromVecimOd(int zbir) {
		if (koren == null) {
			// throw new Exception();
		}
		int brojac = 0;
		Queue<CvorStabla> red = new LinkedList<>();
		red.add(koren);
		while (!red.isEmpty()) {
			CvorStabla pom = red.poll();
			if (zbir(pom) > zbir) {
				// System.out.println(zbir(pom));
				brojac++;
			}
			if (pom.levo != null)
				red.add(pom.levo);
			if (pom.desno != null)
				red.add(pom.desno);
		}
		return brojac;
	}

	private int zbir(CvorStabla koren) {
		if (koren == null)
			return 0;
		return koren.podatak + zbir(koren.levo) + zbir(koren.desno);
	}

	public CvorStabla nadjiRoditelja(CvorStabla koren, int podatak) {
		if (koren == null || koren.podatak == podatak)
			return null;
		if ((koren.levo != null && koren.levo.podatak == podatak)
				|| (koren.desno != null && koren.desno.podatak == podatak))
			return koren;
		CvorStabla desno = nadjiRoditelja(koren.desno, podatak), levo = nadjiRoditelja(koren.levo, podatak);
		if (desno != null)
			return desno;
		return levo;
	}

	public boolean daLiJePostablo(CvorStabla s1, CvorStabla s2) {
		if (s2 == null)
			return true;
		if (s1 == null)
			return false;
		if (istaStabla(s1, s2))
			return true;
		return daLiJePostablo(s1.levo, s2) || daLiJePostablo(s1.desno, s2);
	}

	private boolean istaStabla(CvorStabla s1, CvorStabla s2) {
		if ((s1 == null && s2 != null) || (s1 == null && s2 != null) || (s1.podatak != s2.podatak))
			return false;
		if (s1 == null && s2 == null)
			return true;
		return istaStabla(s1.levo, s2.levo) && istaStabla(s1.desno, s2.desno);

	}

	/*
	 * Dato je binarno stablo celih brojeva. Napisati funkciju koja će prebrojati
	 * koliko elemenata ima vrednost veću od proseka elemenata svog levog podstabla
	 */
	public int brojVecihOdProsekaSvogLevogPodstabla(CvorStabla koren) {
		int zbir = 0;
		Queue<CvorStabla> red = new LinkedList<CvorStabla>();
		red.add(koren);
		while (!red.isEmpty()) {
			CvorStabla pom = red.poll();
			if (pom.podatak > prosekLevogPodstabla(pom))
				zbir++;
			if (pom.levo != null)
				red.add(pom.levo);
			if (pom.desno != null)
				red.add(pom.desno);
		}
		return zbir;
	}

	private int prosekLevogPodstabla(CvorStabla koren) {
		return zbir(koren.levo) / brojElemenata(koren.levo);

	}

	private int brojElemenata(CvorStabla koren) {
		if (koren == null)
			return 0;
		return 1 + brojElemenata(koren.levo) + brojElemenata(koren.desno);
	}

	public int najveciZbirOdSvihPodstabala() throws LabisException {
		if (this.koren == null)
			throw new LabisException();
		int max = Integer.MIN_VALUE;
		return najveciZbirPodstabala(this.koren, max);
	}

	private int najveciZbirPodstabala(CvorStabla koren, int max) {
		if (koren == null)
			return max;
		if (zbirStabla(koren) > max)
			max = zbirStabla(koren);
		int leviZbir = najveciZbirPodstabala(koren.levo, max), desniZbir = najveciZbirPodstabala(koren.levo, max);
		if (leviZbir > max)
			max = leviZbir;
		if (desniZbir > max)
			max = desniZbir;
		return max;
	}

	private int zbirStabla(CvorStabla koren) {
		if (koren == null)
			return 0;
		return koren.podatak + zbirStabla(koren.levo) + zbirStabla(koren.desno);
	}

	public CvorStabla najvecaRazlikaLevogIDesnogPodstabla() {
		if (this.koren == null)
			return null;
		Queue<CvorStabla> red = new LinkedList<>();
		red.add(koren);
		int maxRazlika = 0;
		CvorStabla rezultat = koren;
		while (!red.isEmpty()) {
			CvorStabla pom = red.poll();
			int novaRazlika = Math.abs(zbir(pom.levo) - zbir(pom.desno));
			if (novaRazlika > maxRazlika) {
				maxRazlika = novaRazlika;
				rezultat = pom;
			}
			if (pom.levo != null)
				red.add(pom.levo);
			if (pom.desno != null)
				red.add(pom.desno);

		}
		return rezultat;
	}

	public int proizvodZajednickihPredaka(CvorStabla koren, CvorStabla prvi, CvorStabla drugi) {
		if (koren == null)
			return 0;
		int proizvod = 1;
		return proizvodZajednickihPredaka(koren, prvi, drugi, proizvod);
	}

	private int proizvodZajednickihPredaka(CvorStabla koren, CvorStabla prvi, CvorStabla drugi, int proizvod) {
		if (sadrziCvor(koren.levo, prvi) && sadrziCvor(koren.levo, drugi)) {
			proizvod *= koren.podatak;
			return proizvodZajednickihPredaka(koren.levo, prvi, drugi, proizvod);
		} else if (sadrziCvor(koren.desno, prvi) && sadrziCvor(koren.desno, drugi)) {
			proizvod *= koren.podatak;
			return proizvodZajednickihPredaka(koren.desno, prvi, drugi, proizvod);
		} else if (sadrziCvor(koren, prvi) && sadrziCvor(koren, drugi)) { // poslednji koji je roditelj dva zadata
			proizvod *= koren.podatak;
		}
		return proizvod;

	}

	/*
	 * Neka je dat koren i pokazivac na neki cvor u stablu, vrati cvor koji se
	 * nalazi na putanji od korena do datog cvora i koji ima najvecu vrednost kljuca
	 */
	public CvorStabla vratiMaxNaPutanji(CvorStabla zadati) {
		if (this.koren == null)
			return null;
		CvorStabla max = this.koren;
		return vratiMaxNaPutanji(koren, zadati, max);
	}

	private CvorStabla vratiMaxNaPutanji(CvorStabla koren, CvorStabla zadati, CvorStabla max) {
		if (koren.desno != null && sadrziCvor(koren.desno, zadati)) {
			if (max.podatak < koren.desno.podatak)
				max = koren.desno;
			return vratiMaxNaPutanji(koren.desno, zadati, max);
		}
		if (koren.levo != null && sadrziCvor(koren.levo, zadati)) {
			if (max.podatak < koren.levo.podatak)
				max = koren.levo;
			return vratiMaxNaPutanji(koren.levo, zadati, max);
		}
		return max;
	}

	// vrati najveci zbir od korena do nekog lista
	public int najveciZbirOdKorenaDoLista() {
		if (koren == null)
			return 0;
		int max = 0, trenutnaSuma = 0;
		return najveciZbirOdKOrenaDoLista(koren, max, trenutnaSuma);
	}

	private int najveciZbirOdKOrenaDoLista(CvorStabla koren, int max, int trenutnaSuma) {
		if (koren == null)
			return max;

		trenutnaSuma += koren.podatak;

		if (koren.desno == null && koren.levo == null && trenutnaSuma > max) {
			max = trenutnaSuma;
			// ako bi trebao da se pamti list do koga je najveca putanja samo dodamo ovde
			// list = koren i vratimo list
		}

		max = najveciZbirOdKOrenaDoLista(koren.levo, max, trenutnaSuma);
		max = najveciZbirOdKOrenaDoLista(koren.desno, max, trenutnaSuma);
		return max;

	}


	public int zbirSvihListovaNaNajdubljemNivou() throws LabisException {
		if (koren == null)
			throw new LabisException();
		int visina = visinaStabla(koren), trenutniNivo = 1, suma = 0;
		return zbirSvihCvorovaNaPosledenjemNivou(koren, visina, trenutniNivo, suma);
	}

	private int zbirSvihCvorovaNaPosledenjemNivou(CvorStabla koren, int visina, int trenutniNivo, int suma) {
		if (koren == null)
			return suma;
		if (trenutniNivo++ == visina)
			return suma += koren.podatak;
		suma = zbirSvihCvorovaNaPosledenjemNivou(koren.levo, visina, trenutniNivo, suma);
		suma = zbirSvihCvorovaNaPosledenjemNivou(koren.desno, visina, trenutniNivo, suma);
		return suma;
	}

	public int visinaStabla(CvorStabla koren) {
		if (koren == null)
			return 0;
		int levaVisina = 1 + visinaStabla(koren.levo), desnaVisina = 1 + visinaStabla(koren.desno);
		if (levaVisina > desnaVisina)
			return levaVisina;
		return desnaVisina;

	}

	// proveri da li su data dva binarna stabla identicna
	public static boolean jesuIdenticna(CvorStabla koren1, CvorStabla koren2) {
		if (koren1 == null && koren2 == null)
			return true;
		if ((koren1.levo == null ^ koren2.levo == null) || (koren1.desno == null ^ koren2.desno == null))
			return false;
		return jesuIdenticna(koren1.desno, koren2.desno) && jesuIdenticna(koren1.levo, koren2.levo);
	}

	// neka je dat neki cvor stabla kao parametar, istampaj sve njegove rodjake, tj
	// cvorove na istom nivou
	public void ispisiSveRodjake(CvorStabla zadati) {
		Stack<CvorStabla> trenutniStek = new Stack<>(), sledeciStek = new Stack<>();
		trenutniStek.push(koren);
		boolean nadjen = false;
		while (!trenutniStek.isEmpty()) {
			CvorStabla pom = trenutniStek.pop();
			if (pom.levo != null)
				sledeciStek.push(pom.levo);
			if (pom.desno != null)
				sledeciStek.push(pom.desno);
			if (pom.desno == zadati || pom.levo == zadati)
				nadjen = true;
			if (trenutniStek.isEmpty()) {
				Stack temp = trenutniStek;
				trenutniStek = sledeciStek;
				sledeciStek = temp;
				if (nadjen)
					break;
			}
		}
		if (nadjen) {
			while (!trenutniStek.isEmpty())
				System.out.print(trenutniStek.pop().podatak + "\t");
		}
	}

	// ispisi elemente stabla po nivoima pocevsi od poslednjeg nivoa
	public void ispisiPoNivoimaObrnuto() {
		if (this.koren == null)
			return;
		Queue<CvorStabla> red = new LinkedList<>();
		Stack<CvorStabla> stek = new Stack<>();
		red.add(koren);
		while (!red.isEmpty()) {
			CvorStabla pom = red.poll();
			stek.push(pom);
			if (pom.levo != null)
				red.add(pom.levo);
			if (pom.desno != null)
				red.add(pom.desno);
		}
		while (!stek.isEmpty())
			System.out.print(stek.pop().podatak + "\t");
	}

	// da li je dato stablo1 podstablo nekog drugog stabla2
	public boolean jestePodstabloOd(BinarnoStablo stablo) {
		if (koren == null)
			return true;
		if (stablo.koren == null)
			return false;
		Queue<CvorStabla> red = new LinkedList<>();
		red.add(stablo.koren);
		while (!red.isEmpty()) {
			CvorStabla pom = red.poll();
			if (BinarnoStablo.istoStablo(pom, this.koren))
				return true;
			if (pom.levo != null)
				red.add(pom.levo);
			if (pom.desno != null)
				red.add(pom.desno);
		}
		return false;
	}

	private static boolean istoStablo(CvorStabla pom, CvorStabla koren) {
		if (pom == null && koren == null)
			return true;
		if ((pom == null ^ koren == null) || pom.podatak != koren.podatak)
			return false;
		return istoStablo(pom.levo, koren.levo) && istoStablo(pom.desno, koren.desno);
	}

	// da li je dato stablo simetricno, da li su njegovo levo i desno podstablo kao
	// u ogledalu
	public boolean jesteSimetricno() {
		if (koren == null)
			return true;
		return BinarnoStablo.jesuSimetricna(koren.levo, koren.desno);
	}

	private static boolean jesuSimetricna(CvorStabla k1, CvorStabla k2) {
		if (k1 == null && k2 == null)
			return true;
		if ((k1.levo == null ^ k2.desno == null) || (k1.desno == null ^ k2.levo == null))
			return false;

		return jesuSimetricna(k1.levo, k2.desno) && jesuSimetricna(k1.desno, k2.levo);
	}

	/*
	 * Napisati proceduru koja štampa sadržaj svih čvorova binarnog stabla (nije
	 * BST) na putanji od korena do čvora koji ima najveću vrednost u stablu
	 */
	public void istampajPutanjuDoMaksimuma() {
		if (koren == null)
			return;
		CvorStabla najveci = this.max(koren, koren);
		if (najveci != null && sadrziCvor(koren, najveci))
			istampajPutanju(koren, najveci);
	}

	private void istampajPutanju(CvorStabla koren, CvorStabla najveci) {
		System.out.println(koren.podatak);
		if (sadrziCvor(koren.desno, najveci))
			istampajPutanju(koren.desno, najveci);
		if (sadrziCvor(koren.levo, najveci))
			istampajPutanju(koren.levo, najveci);
	}

	private CvorStabla max(CvorStabla koren, CvorStabla max) {
		if (koren == null)
			return max;
		if (koren.podatak > max.podatak)
			max = koren;
		CvorStabla maxLevi = max(koren.levo, max);
		CvorStabla maxDesni = max(koren.desno, max);
		if (maxDesni.podatak > max.podatak)
			max = maxDesni;
		if (maxLevi.podatak > max.podatak)
			max = maxLevi;
		return max;

	}

	/*
	 * Napisati funkciju koja prihvata pokazivač k na koren i vrednosti p i q nekih
	 * čvorova u binarnom stablu i vraća pokazivač na zajedničkog pretka čvorova p i
	 * q koji se nalazi najdublje u stablu.
	 */
	public CvorStabla prviZajednickiPredak(int p, int q) {
		CvorStabla prvi = nadjiCvor(koren, p), drugi = nadjiCvor(koren, q);
		if (koren == null || prvi == null || drugi == null)
			return null;
		return prviZajednickiPredak(koren, prvi, drugi);
	}

	private CvorStabla prviZajednickiPredak(CvorStabla koren, CvorStabla prvi, CvorStabla drugi) {

		boolean levoSadrziOba = (sadrziCvor(koren.levo, prvi) && sadrziCvor(koren.levo, drugi));
		boolean desnoSadrziOba = sadrziCvor(koren.desno, prvi) && sadrziCvor(koren.desno, drugi);
		boolean desnoSadrziSamoJedan = sadrziCvor(koren.desno, prvi) ^ sadrziCvor(koren.desno, drugi);
		boolean levoSadrziSamoJedan = sadrziCvor(koren.levo, prvi) ^ sadrziCvor(koren.levo, drugi);
		// ako naidjemo na cvor cije levo podstablo sadrzi samo jedan, tada onaj drugi
		// deo mora da sadrzi samo onaj drugi, onda je to taj
		if (levoSadrziSamoJedan && desnoSadrziSamoJedan) {
			return koren;
		}
		// ako su oba trazena cvora u levom podtablu onda rekurzijom idemo u levo, u
		// suprotnom u desno
		if (levoSadrziOba)
			return prviZajednickiPredak(koren.levo, prvi, drugi);
		if (desnoSadrziOba)
			return prviZajednickiPredak(koren.desno, prvi, drugi);
		//prostali slucaj je kad je prvi predak onom drugom, tako da tad vrcacamo roditelja tog cvora
		return roditelj(this.koren, koren);

	}

	private CvorStabla roditelj(CvorStabla koren, CvorStabla trazeni) {
		if (koren == null)
			return null;
		if (koren.levo == trazeni || koren.desno == trazeni)
			return koren;
		if (roditelj(koren.levo, trazeni) != null)
			return roditelj(koren.levo, trazeni);
		return roditelj(koren.desno, trazeni);
	}

	private CvorStabla nadjiCvor(CvorStabla koren, int p) {
		if (koren == null)
			return null;
		if (koren.podatak == p)
			return koren;
		if (nadjiCvor(koren.levo, p) != null)
			return nadjiCvor(koren.levo, p);
		if (nadjiCvor(koren.desno, p) != null)
			return nadjiCvor(koren.desno, p);
		return null;
	}
}
