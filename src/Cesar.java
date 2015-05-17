import acm.util.RandomGenerator;

public class Cesar extends Code {
	int i2;
	Cesar(){
		i2=0;
	}
	Cesar(int i){
		i2=i;
	}
	public boolean keyExist()
	{
		if (i2==0) return false;
		return true;
	}
	
	public void deleteKey()
	{
		if(keyExist()){
			i2=0;
		}
	}
	
	public void setKeys(String lol) {
		i2=Crypts.tryParse(lol.trim());
	}
	
	public void generateKeys() {
	i2 = new RandomGenerator().nextInt(10);
	}
	
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
