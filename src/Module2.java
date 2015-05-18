import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import acm.io.IOConsole;

public class Module2 extends Modules {
	boolean back = false;
	String text;
	public Code coder;
	public FileHandler filehandler;

	/**
	 * Start modulu
	 * 
	 * @param console
	 *            interface programu-konsola
	 * @param coder
	 *            algorytm szyfrowania
	 */
	public void run(IOConsole console, Code coder) {
		this.coder = coder;
		do {
			menu(console);
		} while (!back);
	}

	/**
	 * menu modulu
	 * 
	 * @param console
	 *            interface programu-konsola
	 */
	private void menu(IOConsole console) {
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
			console.println("1. Kodowanie pliku txt");
			console.println("2. Dekodowanie pliku txt");
			// console.println("3. Kodowanie pliku");
			// console.println("4. Dekodowanie pliku");
			console.println("5. Cofnij");
			console.println("6. Wyjscie");
			choice = Crypts.tryParse(console.readLine());
			if (choice <= 6 && choice >= 1)
				break;
			if (choice == 3 || choice == 4)
				break;
		} while (true);
		choices2(choice, console);
	}

	/**
	 * Sprawdzanie opcji pobranych w menu
	 * 
	 * @param choice
	 *            opcja pobrana w menu
	 * @param console
	 *            interface programu-konsola
	 */
	private void choices2(int choice, IOConsole console) {
		switch (choice % 10) {
		case 1:
			filehandler = new TxtHandler();
			encodeFile(console);
			break;
		case 2:
			filehandler = new TxtHandler();
			decodeFile(console);
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			back = true;
			break;
		case 6:
			System.exit(0);
		default:
			break;
		}
	}

	/**
	 * kodowanie listy slow
	 * 
	 * @param console
	 *            interface programu-konsola
	 * @param coder
	 *            algorytm szyfrujacy
	 * @param words
	 *            lista slow do zakodowania
	 * @return zakodowana lista slow
	 */
	private List<String> encodeWordList(IOConsole console, Code coder,
			List<String> words) {
		List<String> words2 = new ArrayList<String>();
		for (int i = 0; i < words.size(); i++) {
			words2.add(coder.encode(words.get(i)));
			Crypts.progressBar(console, words.size() - 1, i, "Trwa kodowanie");
		}
		return words2;
	}

	/**
	 * odkodowywanie listy slow
	 * 
	 * @param console
	 *            interface programu- konsola
	 * @param coder
	 *            algorytm szyfrujacy
	 * @param words
	 *            lista slow do odkodowania
	 * @return odkodowana lista slow
	 */
	private List<String> decodeWordList(IOConsole console, Code coder,
			List<String> words) {
		List<String> words2 = new ArrayList<String>();
		for (int i = 0; i < words.size(); i++) {
			words2.add(coder.decode(words.get(i)));
			Crypts.progressBar(console, words.size() - 1, i, "Trwa Odkodowanie");
		}
		console.println("Dekodowanie zakonczone");
		Crypts.waits(2000);
		return words2;
	}

	/**
	 * Wczytywanie plikow Kodowanie pliku linia po lini Zapisanie klucza oraz
	 * zakodowanej listy lini.
	 * 
	 * @param console
	 *            interface programu-konsola
	 */
	private void encodeFile(IOConsole console) {
		List<String> words = new ArrayList<String>();
		String filename = "";
		boolean keySaved;
		do {
			console.clear();
			words.clear();
			keySaved = false;
			console.println("Podaj nazwe pliku do zakodowania(wpisz esc by anulowac)");
			if ((filename = Crypts.readFilename("Kodowanie anulowano", console)) == null)
				break;
			if (Crypts.getExtension(filename) == null) {
				filename += ".txt";
			}
			coder.generateKeys();
			Crypts.progressBar(console, 10, 0, "Trwa Wczytywanie plikow");
			try {
				words = filehandler.readFile(console, filename);
			} catch (IOException e2) {
				console.println("Brak pliku");
				Crypts.waits(2000);
				e2.printStackTrace();
			}
			filename = Crypts.getNewFilename(filename);
			if (!words.isEmpty())
				try {
					filehandler.saveKeyFile(console, filename, coder);
					keySaved = true;
				} catch (IOException e1) {
					console.println("Blad zapisu klucza");
					Crypts.waits(2000);
					keySaved = false;
				}
			else {
				console.println("Plik jest pusty");
				Crypts.waits(2000);
			}
			if (keySaved) {
				words = encodeWordList(console, coder, words);
				try {
					filehandler.saveFile(console, filename, words);
					coder.deleteKey();
					break;
				} catch (IOException e) {
					console.println("Blad zapisu pliku");
				}
			}
		} while (true);
	}

	/**
	 * Wczytywanie plikow oraz klucza Odkod owywanie pliku linia po lini oraz
	 * odkodowanej listy lini.
	 * 
	 * @param console
	 *            interface programu-konsola
	 */
	private void decodeFile(IOConsole console) {
		List<String> words = new ArrayList<String>();
		words.clear();
		coder.deleteKey();
		String filename = "";
		boolean readedKey;
		do {
			readedKey = true;
			console.clear();
			console.println("Podaj nazwe pliku do odszyfrowania");
			if ((filename = Crypts.readFilename("Wczytawanie anulowano",
					console)) == null)
				break;
			if (Crypts.getExtension(filename) == null) {
				filename += ".txt";
			}
			try {
				filehandler.readKeyFile(console, filename, coder);
			} catch (IOException e1) {
				console.println("Brak pliku z kluczem");
				Crypts.waits(2000);
				readedKey = false;
			}
			words.clear();
			Crypts.progressBar(console, 10, 0, "Trwa Wczytywanie plikow");
			if (readedKey) {
				try {
					words = filehandler.readFile(console, filename);
				} catch (IOException e) {
					console.println("Brak zaszyfrowanego pliku");
					Crypts.waits(2000);
				}
			}
		} while (words.isEmpty());
		words = decodeWordList(console, coder, words);
		filename = Crypts.getNewFilename(filename);
		try {
			filehandler.saveFile(console, filename, words);
			coder.deleteKey();
		} catch (IOException e) {
			console.println("Blad, nie udalo sie zapisac pliku");
		}
	}

}
