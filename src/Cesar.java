import acm.util.RandomGenerator;

public class Cesar extends Code {
	int i2;
	Cesar(){
		i2=0;
	}
	/**
	 * zapisywanie i jako klucza szyfrowania
	 * @param i klucz
	 */
	Cesar(int i){
		i2=i;
	}
	
	/**
	 * 
	 * @return <code>true</code>jezeli klucz istnieje <code>false</code>jezeli
	 *         klucz nie istnieje lub jest zerowy
	 * 
	 */
	public boolean keyExist()
	{
		if (i2==0) return false;
		return true;
	}
	
	/**
	 * usuwa klucz(przypisuje wartosc null do zmiennych)
	 */
	public void deleteKey()
	{
		if(keyExist()){
			i2=0;
		}
	}

	/**
	 * ustawienie klucza na podstawie podanego Stringa
	 */
	public void setKeys(String lol) {
		i2=Crypts.tryParse(lol.trim());
	}
	
	/**
	 * generowanie klucza-liczba losowa od 0 do 10
	 */
	public void generateKeys() {
	i2 = new RandomGenerator().nextInt(10);
	}
	
	/**
	 * dekodowanie podanego stringa- przesuniecie znaku o klucz
	 */
	public String decode(String text2) {
		int k;
		char x[] = text2.toCharArray();
		for (int i = 0; i < x.length; i++) {
			k = x[i];
			k -= i2;
			x[i] = (char) k;
		}
		return new String(x);

	}

	/**
	 * kodowanie podanego stringa- przesuniecie znaku o klucz
	 */
	public  String encode(String text2) {
		int k;
		char x[] = text2.toCharArray();
		for (int i = 0; i < x.length; i++) {
			k = x[i];
			k += i2;
			x[i] = (char) k;
		}
		return new String(x);
	}
	
	public String getAllKeys()
	{
		return new String(Integer.toString(i2));
	}
}
