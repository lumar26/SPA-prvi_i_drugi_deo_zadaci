package Main;

import labis.exception.LabisException;
import labis.generator.StabloGenerator;

public class Test {

	public static void main(String[] args) {
		BinarnoStablo stablo = new BinarnoStablo(), stablo2 = new BinarnoStablo();
		StabloGenerator.izgenerisiStablo(stablo);
		StabloGenerator.izgenerisiStablo(stablo2);
		
		System.out.println(stablo.visinaStabla(stablo.koren));
	}

}
