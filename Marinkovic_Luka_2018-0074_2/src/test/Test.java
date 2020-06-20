package test;

import labis.exception.LabisException;
import labis.liste.ADSLista;
import labis.test.ListGenerator;
import main.DSLista;
import main.Niz;
import main.Stek;

public class Test {

	public static void main(String[] args) {
		DSLista lista = new DSLista();
		int[] niz = new int[] {4,5,6};
		Niz n = new Niz();
		n.niz = niz;
			System.out.println(n.leviPomeraj());
	}

}
