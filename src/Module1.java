import java.io.IOException;


public class Module1 extends Modules {
	/*
	 * Menu 2 block
	 */
	boolean back=false;
	String text;
	public Code coder;
	public FileHandler filehandler;
	
	public void run(Crypts console,Code coder)
	{
		this.coder=coder;
		filehandler=new TxtHandler();
		do{
		menu2(console);
		}while(!back);
	}
	
	private void menu2(Crypts console) {
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
			console.getConsole().clear();
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
		choices2(choice,console);
	}

	private void choices2(int choice,Crypts console) {
		switch (choice % 10) {
		case 1:
			encodeWords(console);
			break;
		case 2:
			decodeWords(console);
			break;
		case 3:
			addOneWord(console);
			break;
		case 4:
			readOneWord(console);
			break;
		case 5:
			console.words.clear();
			coder.deleteKey();
			break;
		case 6:
			saveListToFile(console);
			break;
		case 7:
			loadListFromFile(console);
			break;
		case 8:
			back = true;
			break;
		case 9:
			System.exit(0);
		default:
			break;
		}
	}
	
	/*
	 * block responsible for the console io actions
	 */
	private void readOneWord(Crypts console) {
		int j = 0;
		if (console.words.isEmpty()) {
			console.println("Nie ma zapisanych zadnych slow");
			Crypts.waits(1000);
			return;
		} else {
			do {
				console.println("Podaj ktore chcesz slowo poznac");
				console.println("Jest ich " + console.words.size());
				j = Crypts.tryParse(console.readLine());
				if (j <= console.words.size() && j >= 0)
					break;
			} while (true);
			console.println(coder.decode(console.words.get(j - 1)));
			Crypts.waits(2000);
		}
	}

	private void decodeWords(Crypts console) {
		if (console.words.isEmpty()) {
			console.println("Nie ma zapisanych zadnych slow");
			Crypts.waits(1000);
			return;
		} else {
			for (String word : console.words) {
				console.println(coder.decode(word));
			}
			Crypts.waits(2000);
		}
	}

	private void encodeWords(Crypts console) {
		int x = 0;
		do {
			console.println("Podaj ilosc slow(Max 10)");
			x = Crypts.tryParse(console.readLine());
			if (x <= 10 && x >= 1)
				break;
		} while (true);
		coder = makeKeyIfNeeded(console);
		for (; x >= 1; x--)
			addOneWord(x,console);
	}

	private Code makeKeyIfNeeded(Crypts console) {
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

	private void addOneWord(int x,Crypts console) {
		addOneWord(" " + x + " ",console);
	}

	private void addOneWord(String x,Crypts console) {
		makeKeyIfNeeded(console);
		console.println("Podaj" + x + "slowo");
		text = console.readNotEmptyLine();
		console.words.add(coder.encode(text));
	}

	private void addOneWord(Crypts console) {
		addOneWord(" ",console);
	}

	/*
	 * file block
	 */
	private void loadListFromFile(Crypts console) {
		console.words.clear();
		coder.deleteKey();
		String filename = "";
		do {
			console.getConsole().clear();
			console.println("Podaj nazwe pliku do wczytania o rozszerzeniu txt");
			filename=console.readFilename("Wczytywanie anulowano");
			if(Crypts.getExtension(filename)==null){
				filename+=".txt";
			}
			try {
				filehandler.readKeyFile(console, filename, coder);
				filehandler.readFile(console, filename);
			} catch (IOException e) {
				console.println("No file1");
				Crypts.waits(2000);
			}
		} while (console.words.isEmpty());
	}

	private void saveListToFile(Crypts console) {
		if (console.words.isEmpty()) {
			console.println("Talbica slow jest pusta nie ma czego zapisac");
		}
		String filename = "";
		while (!console.words.isEmpty()) {
			console.getConsole().clear();
			console.println("Podaj nazwe pliku do zapisania(esc by anulowac)");
			filename=console.readFilename("Zapis anulowano");
			try {
				filehandler.saveFile(console, filename);
				filehandler.saveKeyFile(console, filename,coder);
				console.words.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
