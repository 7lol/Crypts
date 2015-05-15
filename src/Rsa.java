import java.math.BigInteger;
import java.util.Random;

import acm.util.RandomGenerator;

public class Rsa extends Code {
	private BigInteger e = null; // czesc klucza publicznego (e,n)
	private BigInteger n = null; // wspolna czesc kluczy
	private BigInteger d = null; // czesc klucza prywatnego (d,n)
	private int keysBitLength = 128;

	Rsa() {
		deleteKey();
	}

	Rsa(boolean needKeys) {
		if (needKeys)
			generateKeys();
	}

	Rsa(String Keys) {

	}

	Rsa(BigInteger e, BigInteger n, BigInteger d) {
		this.n = n;
		this.d = d;
		this.e = e;
	}

	public boolean keyExist() {
		if ((n == null) || (d == null) || (e == null))
			return false;
		return true;
	}

	public void deleteKey() {
		if (keyExist()) {
			n = null;
			d = null;
			e = null;
		}
	}

	public void setKeys(String lol) {
		n = new BigInteger(lol.split("\\s+")[0]);
		d = new BigInteger(lol.split("\\s+")[1]);
		e = new BigInteger(lol.split("\\s+")[2]);
	}

	public String getAllKeys() {
		return new String(e + " " + n + " " + d);
	}

	public void generateKeys() {
		Random r = new RandomGenerator();
		r.setSeed(System.currentTimeMillis());
		do {
			BigInteger p = new BigInteger(keysBitLength, 100, r);
			BigInteger q = new BigInteger(keysBitLength, 100, r);
			n = p.multiply(q);
			BigInteger phi = (p.subtract(BigInteger.valueOf(1)).multiply(q
					.subtract(BigInteger.valueOf(1))));
			e = phi.divide(BigInteger.valueOf(15));
			while (phi.gcd(e).intValue() != 1) {
				e = e.add(new BigInteger("1"));
			}
			d = e.modInverse(phi);
		} while (d == e);
	}

	public String decode(String text) {
		return new String((new BigInteger(text)).modPow(e, n).toByteArray());

	}

	public String encode(String text) {
		return (new BigInteger(text.getBytes())).modPow(d, n).toString();
	}

}
