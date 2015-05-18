public abstract class Code {

	/**
	 * 
	 * @return <code>true</code>jezeli klucz istnieje <code>false</code>jezeli
	 *         klucz nie istnieje
	 * 
	 */
	public boolean keyExist() {
		return false;
	}

	/**
	 * usuwa klucz(przypisuje wartosc null do zmiennych)
	 */
	public void deleteKey() {
	}

	/**
	 * 
	 * @return zwraca wszystkie klucze oddzielone spacja
	 */
	public String getAllKeys() {
		return null;
	}
	
	/**
	 * 
	 * @param lol ustawia klucz(podklucze oddzielone spacja)
	 */
	public void setKeys(String lol) {
	}
	/**
	 * 
	 * @param text
	 * @return zaszyfrowany tekst
	 */
	public String encode(String text) {
		return null;

	}
	/**
	 * 
	 * @param text
	 * @return odszyfrowany tekst
	 */
	public String decode(String text) {
		return null;

	}

	/**
	 * losowo generuje klucz
	 */
	public void generateKeys() {
	}
}
