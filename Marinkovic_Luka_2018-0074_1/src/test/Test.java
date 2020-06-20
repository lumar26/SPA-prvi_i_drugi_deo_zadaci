package test;

import javax.swing.ListCellRenderer;

import labis.exception.LabisException;
import labis.test.ListGenerator;
import main.JSLista;

public class Test {

	public static void main(String[] args) {
		//
		JSLista lista1 = new JSLista(), lista2 = new JSLista();
		int[] niz1 =  {1,3,2,6,12}, niz2 =  {10, 7, 8, 13, 16, 17};
		//
		ListGenerator.napraviJSListuCommon(lista1, niz1, false);
		ListGenerator.napraviJSListuCommon(lista2, niz2, false);
		
		System.out.println(lista1.izbaciPreZadatogBroja(1));
		ListGenerator.ispisiJSListu(lista1.prvi);
	}

}
