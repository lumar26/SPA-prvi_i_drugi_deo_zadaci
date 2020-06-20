package stabla;

public interface Stablo {
	public CvorBinarnogStabla getKoren();

	public int brojElemenata();

	public int visinaStabla();

	public int minimalniElement() throws Exception;

	public int maksimalniElement() throws Exception;

	public void prolazPoNivoima();

	public void prefiks();

	public void infiks();

	public void postfiks();

	public void dodajElement(int podatak);

	public boolean jesteBSP();
	
	public void izbaci(int podatak);
	
	public CvorBinarnogStabla nadjiCvor(int podatak);
}
