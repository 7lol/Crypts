import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Module2 extends Modules {
	boolean back = false;
	String text;
	public Code coder;

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
			console.println("3. Kodowanie pliku");
			console.println("4. Dekodowanie pliku");
			console.println("5. Cofnij");
			console.println("6. Wyjscie");
			choice = Crypts.tryParse(console.readLine());
			if (choice <= 6 && choice >= 1)
				break;
		} while (true);
		choices2(choice, console);
	}

	private void choices2(int choice, Crypts console) {
		switch (choice % 10) {
		case 1:
			encodeTxt(console);
			break;
		case 2:
			decodeTxt(console);
			break;
		case 3:
			encodeBinary(console);
			break;
		case 4:
			decodeBinary(console);
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

	static void progressBar(Crypts console, int max, int now, String statement) {
		final int MAX_BAR = 10;
		float x = now * MAX_BAR / max;
		float y = (max - now) * MAX_BAR / max;
		if (x != (now-1) * MAX_BAR / max) {
			console.getConsole().clear();
			console.println(statement);
			console.print(" ");
			for (int i = 0; i <= MAX_BAR; i += 1) {
				console.print("_");
			}
			console.println(" ");
			console.print("|");
			for (int i = 0; i <= x; i += 1) {
				console.print("»");
			}
			for (int i = 0; i < y; i += 1) {
				console.print(" ");
			}
			console.println("|");
			console.print(" ");
			for (int i = 0; i <= MAX_BAR; i += 1) {
				console.print("¯");
			}
			console.println(" ");
			Crypts.waits(1);
		}
	}

	private void encodeWordList(Crypts console, Code coder) {
		List<String> words2 = new ArrayList<String>();
		for (int i = 0; i < console.words.size(); i++) {
			words2.add(coder.encode(console.words.get(i)));
			System.out.println(coder.encode(console.words.get(i)) + " "
					+ console.words.get(i));
			Module2.progressBar(console, console.words.size() - 1, i,
					"Trwa kodowanie");
		}
		console.words = words2;
	}

	private void decodeWordList(Crypts console, Code coder) {
		List<String> words2 = new ArrayList<String>();
		for (int i = 0; i < console.words.size(); i++) {
			words2.add(coder.decode(console.words.get(i)));
			Module2.progressBar(console, console.words.size() - 1, i,
					"Trwa kodowanie");
		}
		console.words = words2;
	}

	private void decodeBinary(Crypts console) {
		// TODO Auto-generated method stub

	}

	private void encodeBinary(Crypts console) {
		// TODO Auto-generated method stub
	}

	private void readFile(Crypts console, String filename)
			throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(
				new FileReader(filename + ".txt"));
		while (br.ready()) {
			console.words.add((br.readLine()));
		}
		br.close();
	}

	private void saveFile(Crypts console, File file) throws IOException {
		if (file.exists()) {
			file.delete();
		}
		if (!file.exists()) {
			file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (String word : console.words) {
				bw.write(word);
				bw.write(System.getProperty("line.separator"));
			}
			bw.close();
		}
	}

	private void readKeyFile(Crypts console, String filename)
			throws IOException {
		List<String> words2 = new ArrayList<String>();
		System.out.println(filename + ".key");
		BufferedReader br = new BufferedReader(
				new FileReader(filename + ".key"));
		while (br.ready()) {
			words2.add((br.readLine()));
		}
		String lol = new String("");
		for (String word : words2) {
			lol += word + " ";
		}
		br.close();
		coder.setKeys(lol);
		System.out.println(coder.getAllKeys());
		System.out.println(lol);
	}

	@SuppressWarnings("resource")
	private void saveKeyFile(Crypts console, File file) throws IOException {
		if (file.exists()) {
			file.delete();
		}
		if (!file.exists()) {
			file.createNewFile();
			System.out.println(coder.getAllKeys());
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			String temp[] = coder.getAllKeys().split(" ");
			if (temp.length > 0) {
				for (int i = 0; i < temp.length; i++) {
					bw.write(temp[i]);
					bw.write(System.getProperty("line.separator"));
				}
			} else {
				throw new IOException("Missing file content");
			} 
			bw.close();
		}
	}

	private void encodeTxt(Crypts console) {
		// TODO Auto-generated method stub
		String filename = "";
		boolean keySaved;
		do {
			console.getConsole().clear();
			console.words.clear();
			keySaved = false;
			console.println("Podaj nazwe pliku do zakodowania(wpisz esc by anulowac)");
			if ((filename = console.readFilename("Kodowanie anulowano")) == null)
				break;
			filename = Crypts.cutExtension(filename);
			coder.generateKeys();
			try {
				readFile(console, filename);
			} catch (IOException e2) {
				console.println("Brak pliku");
				Crypts.waits(2000);
				e2.printStackTrace();
			}
			if(!console.words.isEmpty())
			try {
				File file = new File(filename + "_new.key");
				saveKeyFile(console, file);
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
				File file = new File(filename + "_new.txt");
				try {
					saveFile(console, file);
					break;
				} catch (IOException e) {
					console.println("Blad zapisu pliku");
				}
			}
		} while (true);
	}

	private void askIfOverwrite(Crypts console, String filename, File file) {
		String option;
		boolean exit = false;
		do {
			console.getConsole().clear();
			console.println("Nadpisać Plik? " + filename
					+ "[T/N]");
			option = console.readNotEmptyLine().trim();
			if (option.equalsIgnoreCase("t")) {
				file.delete();
				exit = true;
			} else if (option.equalsIgnoreCase("n")) {
				console.println("Zapis anulowano");
				Crypts.waits(1000);
				exit = true;
			}
		} while (!exit);
	}

	private void decodeTxt(Crypts console) {
		console.words.clear();
		coder.deleteKey();
		String filename = "";
		boolean readedKey;
		do {
			readedKey = true;
			console.getConsole().clear();
			console.println("Podaj nazwe pliku do odszyfrowania");
			if ((filename = console.readFilename("Wczytawanie anulowano")) == null)
				break;
			filename = Crypts.cutExtension(filename);
			try {
				readKeyFile(console, filename);
			} catch (IOException e1) {
				console.println("Brak pliku z kluczem");
				Crypts.waits(2000);
				readedKey = false;
				e1.printStackTrace();
			}
			console.words.clear();
			if (readedKey) {
				try {
					readFile(console, filename);
				} catch (IOException e) {
					console.println("Brak zaszyfrowanego pliku");
					Crypts.waits(2000);
				}
			}
		} while (console.words.isEmpty());
		decodeWordList(console, coder);
		try {
			File file = new File(filename + "_new.txt");
			saveFile(console, file);
		} catch (IOException e) {
			console.println("Blad, nie udalo sie zapisac pliku");
			e.printStackTrace();
		}

	}

}
