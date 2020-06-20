package test;

import stabla.BinarnoStablo;
import stabla.BinarnoStabloPretrage;
import stabla.CvorBinarnogStabla;
import stabla.Stablo;

public class Test {

	public static void main(String[] args) {
		BinarnoStabloPretrage tree = new BinarnoStabloPretrage(new CvorBinarnogStabla(12));
//		tree.getKoren().desni = new CvorBinarnogStabla(22);
//		tree.getKoren().levi = new CvorBinarnogStabla(5);
//		tree.getKoren().desni.desni = new CvorBinarnogStabla(24);
//		tree.getKoren().desni.levi = new CvorBinarnogStabla(18);
//		tree.getKoren().levi.desni = new CvorBinarnogStabla(7);
//		tree.getKoren().levi.levi = new CvorBinarnogStabla(2);
//		tree.getKoren().levi.desni.levi = new CvorBinarnogStabla(6);
//		tree.getKoren().levi.desni.desni = new CvorBinarnogStabla(10);
		tree.dodajElement(22);
		tree.dodajElement(5);
		tree.dodajElement(7);
		tree.dodajElement(2);
		tree.dodajElement(18);
		tree.dodajElement(24);
		tree.dodajElement(4);
		tree.dodajElement(10);
		tree.dodajElement(15);
		tree.dodajElement(23);
		tree.dodajElement(6);
		tree.dodajElement(13);
		
		
		BinarnoStablo stablo = new BinarnoStablo(new CvorBinarnogStabla(-111));
		stablo.koren.levi = new CvorBinarnogStabla(2);
		stablo.koren.levi.levi = new CvorBinarnogStabla(3);
		stablo.koren.levi.desni = new CvorBinarnogStabla(6);
		stablo.koren.levi.levi.levi = new CvorBinarnogStabla(4);
		stablo.koren.levi.levi.desni = new CvorBinarnogStabla(5);
		stablo.koren.levi.desni.levi = new CvorBinarnogStabla(7);
		stablo.koren.desni = new CvorBinarnogStabla(8);
		stablo.koren.desni.levi = new CvorBinarnogStabla(9);
		stablo.koren.desni.desni = new CvorBinarnogStabla(13);
		stablo.koren.desni.levi.levi = new CvorBinarnogStabla(-10);
		stablo.koren.desni.levi.desni = new CvorBinarnogStabla(11);
		
		
		CvorBinarnogStabla pom = new CvorBinarnogStabla(100);
		stablo.ispisiPutanju(stablo.koren.desni.levi);
	}

}
