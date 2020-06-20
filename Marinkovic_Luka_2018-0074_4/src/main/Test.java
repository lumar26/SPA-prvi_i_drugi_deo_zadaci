package main;

import labis.exception.LabisException;
import labis.generator.StabloGenerator;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BSTStablo stablo = new BSTStablo();
		BinarnoStablo s = new BinarnoStablo();
		StabloGenerator.izgenerisiStablo(s);
		
		System.out.println(s.visina);
	}

}
