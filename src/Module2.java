import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Module2 extends Modules {
	boolean back = false;
	String text;
	public Code coder;
	public FileHandler filehandler;

	public void run(Crypts console, Code coder) {
		this.coder = coder;
		do {
			menu2(console);
		} while (!back);
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
			console.println("1. Kodowanie pliku txt");
			console.println("2. Dekodowanie pliku txt");
		//	console.println("3. Kodowanie pliku");
		//	console.println("4. Dekodowanie pliku");
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

	private void choices2(int choice, Crypts console) {
		switch (choice % 10) {
		case 1:
			filehandler=new TxtHandler();
			encodeFile(console);
			break;
		case 2:
			filehandler=new TxtHandler();
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

	private void encodeWordList(Crypts console, Code coder) {
		List<String> words2 = new ArrayList<String>();
		for (int i = 0; i < console.words.size(); i++) {
			words2.add(coder.encode(console.words.get(i)));
			System.out.println(coder.encode(console.words.get(i)) + " "
					+ console.words.get(i));
			Crypts.progressBar(console, console.words.size() - 1, i,
					"Trwa kodowanie");
		}
		console.words = words2;
	}

	private void decodeWordList(Crypts console, Code coder) {
		List<String> words2 = new ArrayList<String>();
		for (int i = 0; i < console.words.size(); i++) {
			words2.add(coder.decode(console.words.get(i)));
			Crypts.progressBar(console, console.words.size() - 1, i,
					"Trwa kodowanie");
		}
		console.words = words2;
	}


	private void encodeFile(Crypts console) {
		String filename = "";
		String ext = "";
		boolean keySaved;
		do {
			console.getConsole().clear();
			console.words.clear();
			keySaved = false;
			console.println("Podaj nazwe pliku do zakodowania(wpisz esc by anulowac)");
			if ((filename = console.readFilename("Kodowanie anulowano")) == null)
				break;
			ext= Crypts.getExtension(filename);
			filename = Crypts.cutExtension(filename);
			coder.generateKeys();
			try {
				filehandler.readFile(console, filename,coder);
			} catch (IOException e2) {
				console.println("Brak pliku");
				Crypts.waits(2000);
				e2.printStackTrace();
			}
			if(!console.words.isEmpty())
			try {
				File file = new File(filename + "_new.key");
				filehandler.saveKeyFile(console, file,coder);
				keySaved = true;
			} catch (IOException e1) {
				console.println("Blad zapisu klucza");
				Crypts.waits(2000);
				keySaved = false;
				e1.printStackTrace();
			}else{
				console.println("Plik jest pusty");
				Crypts.waits(2000);
			}
			if (keySaved)
			{
				encodeWordList(console,coder);
				File file = new File(filename + "_new."+ext);
				try {
					filehandler.saveFile(console, file,coder);
					coder.deleteKey();
					break;
				} catch (IOException e) {
					console.println("Blad zapisu pliku");
				}
			}
		} while (true);
	}


	private void decodeFile(Crypts console) {
		console.words.clear();
		coder.deleteKey();
		String filename = "";
		String ext = "";
		boolean readedKey;
		do {
			readedKey = true;
			console.getConsole().clear();
			console.println("Podaj nazwe pliku do odszyfrowania");
			if ((filename = console.readFilename("Wczytawanie anulowano")) == null)
				break;
			ext= Crypts.getExtension(filename);
			filename = Crypts.cutExtension(filename);
			try {
				filehandler.readKeyFile(console, filename,coder);
			} catch (IOException e1) {
				console.println("Brak pliku z kluczem");
				Crypts.waits(2000);
				readedKey = false;
				e1.printStackTrace();
			}
			console.words.clear();
			if (readedKey) {
				try {
					filehandler.readFile(console, filename,coder);
				} catch (IOException e) {
					console.println("Brak zaszyfrowanego pliku");
					Crypts.waits(2000);
				}
			}
		} while (console.words.isEmpty());
		decodeWordList(console, coder);
		try {
			File file = new File(filename + "_new."+ext);
			filehandler.saveFile(console, file,coder);
			coder.deleteKey();
		} catch (IOException e) {
			console.println("Blad, nie udalo sie zapisac pliku");
			e.printStackTrace();
		}
	}

}
