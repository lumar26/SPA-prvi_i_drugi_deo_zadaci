package main;

import labis.exception.LabisException;
import labis.stek.AStek;

public class Stek extends AStek {

	public Stek(int kapacitet) {
		super(kapacitet);
	}

	@Override
	public void ispisiStekObrnuto() throws LabisException {
		ispisiStekObrnuto(this);
	}
	//moze i odmah u ovoj metodi da se radi rekurzivno

	private void ispisiStekObrnuto(Stek stek) throws LabisException {
		if (stek.prazanStek())
			return;
		int pom = stek.pop();
		ispisiStekObrnuto(stek);
		System.out.println(pom);
		this.push(pom);
	}
}
