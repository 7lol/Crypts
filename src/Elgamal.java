import java.math.BigInteger;
import java.util.Random;

import acm.util.RandomGenerator;

public class Elgamal extends Code {
	private int keysBitLength = 128; //dlugosc klucza
	BigInteger p; // czesc klucza publicznego (p, e1, e2)
	BigInteger e1; // czesc klucza publicznego (p, e1, e2)
	BigInteger e2; // czesc klucza publicznego (p, e1, e2)
	BigInteger d; // klucz prywatny
	Random rand = new RandomGenerator();

	/**
	 * 
	 */
	Elgamal() {
		deleteKey();
	}

	/**
	 * 
	 * @param needKeys
	 *            <code>true</code> klucz bedzie wygenerowany <code>false</code>
	 *            nie ma potrzeby generowac klucza
	 */
	Elgamal(boolean needKeys) {
		if (needKeys)
			generateKeys();
	}

	/**
	 * 
	 */
	public boolean keyExist() {
		if ((p == null) || (e1 == null) || (e2 == null) || (d == null))
			return false;
		return true;
	}

	/**
	 * 
	 */
	public void deleteKey() {
		p = null;
		e1 = null;
		e2 = null;
		d = null;
	}

	/**
	 * 
	 */
	public void setKeys(String lol) {
		p = new BigInteger(lol.split("\\s+")[0]);
		e1 = new BigInteger(lol.split("\\s+")[1]);
		e2 = new BigInteger(lol.split("\\s+")[2]);
		d = new BigInteger(lol.split("\\s+")[3]);
	}

	/**
	 * 
	 */
	public String getAllKeys() {
		return new String(p + " " + e1 + " " + e2 + " " + d);
	}

	/**
	 * 
	 */
	public String encode(String text) {
		BigInteger X = new BigInteger(text.getBytes());
		BigInteger r = BigInteger.probablePrime(keysBitLength, rand);
		BigInteger EC = X.multiply(e2.modPow(r, p)).mod(p);
		BigInteger e1rmodp = e1.modPow(r, p);
		return new String(EC + " " + e1rmodp);

	}

	/**
	 * 
	 */
	public String decode(String text) {
		String texts[] = text.split(" ");
		BigInteger EC = new BigInteger(texts[0]);
		BigInteger e1rmodp = new BigInteger(texts[1]);
		BigInteger crmodp = e1rmodp.modPow(d, p);
		BigInteger ad = crmodp.modInverse(p).multiply(EC).mod(p);
		return new String(ad.toByteArray());

	}

	/**
	 * 
	 */
	public void generateKeys() {
		p = BigInteger.probablePrime(keysBitLength, rand);
		e1 = BigInteger.probablePrime(keysBitLength - 1, rand);
		while (true) {
			d = BigInteger.probablePrime(keysBitLength - 1, rand);
			if (d.gcd(p).equals(BigInteger.ONE))
				break;
			else
				continue;
		}
		e2 = e1.modPow(d, p);
	}
}
