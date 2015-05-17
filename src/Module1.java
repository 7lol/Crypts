import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Module1 extends Modules {
	/*
	 * Menu 2 block
	 */
	boolean back=false;
	String text;
	public Code coder;
	
	public void run(Crypts console,Code coder)
	{
		this.coder=coder;
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
			filename = console.readNotEmptyLine();
			if (filename == "esc" || filename == "Esc" || filename == "ESC") {
				console.println("Wczytywanie anulowano");
				Crypts.waits(1000);
				break;
			}
			filename=Crypts.cutExtension(filename);
			try (BufferedReader br = new BufferedReader(
					new FileReader(filename+".txt"))) {
				if (br.ready()) {
					String lol = br.readLine();
					coder.setKeys(lol);
				}
				while (br.ready()) {
					console.words.add((br.readLine()));
				}
			} catch (IOException e) {
				console.println("No file");
				Crypts.waits(2000);
			}
		} while (console.words.isEmpty());
	}

	private void saveListToFile(Crypts console) {
		if (console.words.isEmpty()) {
			console.println("Talbica slow jest pusta nie ma czego zapisac");
		}
		String filename = "";
		String option = "";
		while (!console.words.isEmpty()) {
			console.getConsole().clear();
			console.println("Podaj nazwe pliku do zapisania(esc by anulowac)");
			filename = console.readNotEmptyLine();
			if (filename == "esc" || filename == "Esc" || filename == "ESC") {
				console.println("Zapis anulowano");
				Crypts.waits(1000);
				break;
			}
			filename=Crypts.cutExtension(filename);
			try {
				File file = new File(filename+".txt");
				if (file.exists()) {
					boolean exit = false;
					do {
						console.getConsole().clear();
						console.println("Nadpisaæ Plik? " + filename + "[T/N]");
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
				if (!file.exists()) {
					file.createNewFile();
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(coder.getAllKeys());
					bw.write("\n");
					for (String word : console.words) {
						bw.write(word);
						bw.write("\n");
					}
					console.words.clear();
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
