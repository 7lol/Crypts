import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import acm.io.IOConsole;


public class Module1 extends Modules {

	boolean back=false;
	String text;
	public Code coder;
	public FileHandler filehandler;
	List<String> words=new ArrayList<String>();
	
	/**
	 * Start modulu
	 * @param console interface programu-konsola
	 * @param coder algorytm szyfrowania
	 */
	public void run(IOConsole console,Code coder)
	{
		this.coder=coder;
		filehandler=new TxtHandler();
		do{
		menu2(console,words);
		}while(!back);
	}
	
	private List<String> menu2(IOConsole  console,List<String> words) {
		int choice;
		String text = "";
		if (coder.getClass().equals(Cesar.class)) {
			text = "kod Cezara";
		}
		if (coder.getClass().equals(Rsa.class)) {
			text = "kod RSA";
		}
		if (coder.getClass().equals(Elgamal.class)) {
			text = "kod Elgamal";
		}
		do {
			console.clear();
			console.println("Program kodujacy i dekodujacy " + text);
			console.println("Co chcesz zrobic?");
			console.println("1. Kodowanie slow");
			console.println("2. Dekodowanie slow");
			console.println("3. Kodowanie jednego slowa");
			console.println("4. Dekodowanie jednego slowa");
			console.println("5. Czyszczenie listy slow");
			console.println("6. Zapisywanie listy do pliku");
			console.println("7. Wczytanie listy z pliku");
			console.println("8. Cofnij");
			console.println("9. Wyjscie");
			choice = Crypts.tryParse(console.readLine());
			if (choice <= 9 && choice >= 1)
				break;
		} while (true);
		words=choices2(choice,console,words);
		return words;
	}

	private List<String> choices2(int choice,IOConsole console,List<String> words) {
		switch (choice) {
		case 1:
			encodeWords(console,words);
			break;
		case 2:
			decodeWords(console,words);
			break;
		case 3:
			words.add(addOneWord(console));
			break;
		case 4:
			readOneWord(console,words);
			break;
		case 5:
			words.clear();
			coder.deleteKey();
			break;
		case 6:
			saveListToFile(console,words);
			break;
		case 7:
			words.clear();
			words.addAll(loadListFromFile(console));
			break;
		case 8:
			back = true;
			break;
		case 9:
			System.exit(0);
		default:
			break;
		}
		return words;
	}
	
	/*
	 * block responsible for the console io actions
	 */
	private void readOneWord(IOConsole console,List<String> words) {
		int j = 0;
		if (words.isEmpty()) {
			console.println("Nie ma zapisanych zadnych slow");
			Crypts.waits(1000);
			return;
		} else {
			do {
				console.println("Podaj ktore chcesz slowo poznac");
				console.println("Jest ich " + words.size());
				j = Crypts.tryParse(console.readLine());
				if (j <= words.size() && j >= 0)
					break;
			} while (true);
			console.println(coder.decode(words.get(j - 1)));
			Crypts.waits(2000);
		}
	}

	private void decodeWords(IOConsole console,List<String> words) {
		if (words.isEmpty()) {
			console.println("Nie ma zapisanych zadnych slow");
			Crypts.waits(1000);
			return;
		} else {
			for (String word : words) {
				console.println(coder.decode(word));
			}
			Crypts.waits(2000);
		}
	}

	private void encodeWords(IOConsole console,List<String> words) {
		int x = 0;
		do {
			console.println("Podaj ilosc slow(Max 10)");
			x = Crypts.tryParse(console.readLine());
			if (x <= 10 && x >= 1)
				break;
		} while (true);
		coder = makeKeyIfNeeded(console);
		for (; x >= 1; x--)
			words.add(addOneWord(x,console));
	}

	private Code makeKeyIfNeeded(IOConsole console) {
		if (coder.getClass().equals(Cesar.class)) {
			int i = 0;
			if (!coder.keyExist())
				do {
					console.println("Podaj przesuniecie");
					i = Crypts.tryParse(console.readLine());
					if (i <= Integer.MAX_VALUE && i >= 0) {
						coder = new Cesar(i);
						break;
					}
				} while (true);
		}
		if (coder.getClass().equals(Rsa.class)) {
			if (!coder.keyExist())
				coder = new Rsa(true);
		}
		if (coder.getClass().equals(Elgamal.class)) {
			if (!coder.keyExist())
				coder = new Elgamal(true);
		}
		return coder;
	}

	private String addOneWord(int x,IOConsole console) {
		return addOneWord(" " + x + " ",console);
	}

	private String addOneWord(String x,IOConsole console) {
		makeKeyIfNeeded(console);
		console.println("Podaj" + x + "slowo");
		text = Crypts.readNotEmptyLine(console);
		return coder.encode(text);
	}

	private String addOneWord(IOConsole console) {
		return addOneWord(" ",console);
	}

	/*
	 * file block
	 */
	private List<String> loadListFromFile(IOConsole console) {
		List<String> words= new ArrayList<String>();
		words.clear();
		coder.deleteKey();
		String filename = "";
		do {
			console.clear();
			console.println("Podaj nazwe pliku do wczytania o rozszerzeniu txt");
			filename=Crypts.readFilename("Wczytywanie anulowano",console);
			if (filename==null) return null;
			if(Crypts.getExtension(filename)==null){
				filename+=".txt";
			}
			try {
				filehandler.readKeyFile(console, filename, coder);
				words=filehandler.readFile(console, filename);
			} catch (IOException e) {
				console.println("No file1");
				Crypts.waits(2000);
			}
		} while (words.isEmpty());
		return words;
	}

	private void saveListToFile(IOConsole console, List<String> words) {
		if (words.isEmpty()) {
			console.println("Talbica slow jest pusta nie ma czego zapisac");
		}
		String filename = "";
		while (!words.isEmpty()) {
			console.clear();
			console.println("Podaj nazwe pliku do zapisania(esc by anulowac)");
			filename=Crypts.readFilename("Zapis anulowano",console);
			if (Crypts.getExtension(filename)==null){
				filename+=".txt";
			}
			try {
				filehandler.saveFile(console, filename,words);
				filehandler.saveKeyFile(console, filename,coder);
				words.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
