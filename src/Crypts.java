import java.io.File;

import acm.io.IOConsole;
import acm.program.ConsoleProgram;

public class Crypts extends ConsoleProgram {

	private static final long serialVersionUID = -5728780240021982027L;
	private boolean back = false;

	public void run() {
		setSize(600, 600);
		do {
			mainMenu(this);
		} while (true);
	}
	/**
	 * 
	 * @param filename nazwa pliku wraz z rozszerzeniem
	 * @return nazwa pliku zmieniona o _new wraz z rozszerzeniem
	 */
	public static String getNewFilename(String filename) {
		return new String(cutExtension(filename) + "_new."
				+ getExtension(filename));
	}
	/**
	 * 
	 * @param filename nazwa pliku wraz z rozszerzeniem
	 * @return nazwa pliku ze zmienionym rozszerzeniem na .key(klucz)
	 */
	public static String getKeyName(String filename) {
		return new String(cutExtension(filename) + ".key");
	}
	/**
	 * 
	 * @param console interface programu-konsola
	 * @param file plik ktory ma byc odpisany
	 * @return 
	 * <code>true</code> gdy chcemy nadpisac plik
	 * <code>false</code> gdy chcemy, by plik nie zostal nadpisany
	 */
	public static boolean askIfOverwrite(IOConsole console,File file) {
		String option;
		do {
			console.clear();
			console.println("Nadpisać Plik? " + file.getName() + "[T/N]");
			option = readNotEmptyLine(console).trim();
			if (option.equalsIgnoreCase("t")) {
				file.delete();
				return true;
			} else if (option.equalsIgnoreCase("n")) {
				console.println("Zapis anulowano");
				Crypts.waits(1000);
				return false;
			}
		} while (true);
	}

	/**
	 * 
	 * @param text nazwa pliku wraz z rozszerzeniem
	 * @return nazwa pliku bez rozszerzenia
	 */
	public static String cutExtension(String text) {
		String filename = "";
		String temp[] = text.split("\\.");
		if (temp.length > 1) {
			for (int i = 0; i < temp.length - 1; i++) {
				filename += temp[i];
				if (i < temp.length - 2)
					filename += "\\.";
			}
			return filename;
		}
		return text;
	}

	/**
	 * 
	 * @param text nazwa pliku wraz z rozszerzeniem
	 * @return <code>String</code> rozszerzenie lub
	 *  <code>null</code> gdy jego brak
	 */
	public static String getExtension(String text) {
		String ext = "";
		String temp[] = text.split("\\.");
		if (temp.length > 1) {
			ext = temp[temp.length - 1];
			return ext;
		}
		return null;
	}

	/**
	 * 
	 * Program rysuje progressbar na konsoli 
	 * <code>MAX_BAR</code> liczba "blokow" do wypelnienia
	 * 
	 * @param console interface programu-konsola
	 * @param max liczba elementow
	 * @param now aktualny element
	 * @param statement informacja ktora jest wyswietlana wraz z progressbarem
	 */
	public static void progressBar(IOConsole console, int max, int now,
			String statement) {
		final int MAX_BAR = 20;
		float x = now * MAX_BAR / max;
		float y = MAX_BAR-x;
		if (x != (now - 1) * MAX_BAR / max) {
			console.clear();
			console.println(statement);
			console.print(" ");
			for (int i = 0; i <= MAX_BAR; i++) {
				console.print("_");
			}
			console.println(" ");
			console.print("|");
			for (int i = 0; i < x+1; i++) {
				console.print("»");
			}
			for (int i = 0; (i < y ); i++) {
				console.print(" ");
			}			
			console.println("|");
			console.print(" ");
			for (int i = 0; i <= MAX_BAR; i++) {
				console.print("¯");
			}
			console.println(" ");
			Crypts.waits(1);
		}
	}

	/**
	 * Funkcja pobiera z klawiatury String, 
	 * i sprawdza czy nie jest rowny Esc,esc,ESC
	 * 
	 * @param statement wiadomosc do wyswietlenia
	 * @param console interface programu-konsola
	 * @return <code>wartosc pobrana</code> z klawiatury lub
	 * <code>null</code> gdy zostanie wpisane <code>"Esc","esc","ESC"</code>
	 */
	public static String readFilename(String statement, IOConsole console) {
		console.println("wpisz esc by anulowac");
		String filename = readNotEmptyLine(console);
		if (filename.equals("esc") || filename.equals("Esc")
				|| filename.equals("ESC")) {
			console.println(statement);
			Crypts.waits(1000);
			return null;
		}
		return filename;
	}

	/**
	 * metoda probuje parsowac Stringa do Integera  
	 * 
	 * @return <code>-5</code> gdy nie powiedzie sie, lub <code>Integer</code>
	 */
	public static Integer tryParse(String text) {
		try {
			return new Integer(text);
		} catch (NumberFormatException e) {
			return -5;
		}
	}

	/**
	 * metoda czyta z klawiatury linie, tak dluga az nie bedzie ona pusta(same biale znaki)
	 * 
	 * @param console interface programu-konsola
	 * @return <code>String</code> 
	 */

	public static String readNotEmptyLine(IOConsole console) {
		String text;
		do {
			text = new String(console.readLine());
		} while (text.trim().equals(""));
		return text;
	}
	
	/**
	 * Program zamraza watek na ustawiony czas
	 * @param time czas w milisekundach
	 */
	public static void waits(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Glowne menu programu. Tutaj wybierany jest modul do wlaczenia.
	 * 
	 * @param console interface programu-konsola
	 */
	private static void mainMenu(Crypts console) {
		int choice;
		do {
			console.getConsole().clear();
			console.println("Program kodujacy i dekodujacy ");
			console.println("Co chcesz zrobi?");
			console.println("1. Wlaczyc modul kodujacy i dekodujacy liste slow");
			console.println("2. Wlaczyc modul kodujacy i dekodujacy pliki");
			console.println("3. Wyjscie");
			choice = tryParse(console.readLine());
			if (choice <= 3 && choice >= 1)
				break;
		} while (true);
		do
			menu1(choice,console);
		while (!console.back);
		if (choice == 3)
			System.exit(0);
	}

	/**
	 * Menu ktore wybiera algorytm kodowania
	 * 
	 * @param module numer modulu
	 * @param console interface programu-konsola
	 */
	private static void menu1(int module, Crypts console) {
		int choice;
		do {
			console.getConsole().clear();
			console.println("Program kodujacy i dekodujacy ");
			console.println("Z jakigo algorytmu chcesz korzystac");
			console.println("1. Kod Cezara");
			console.println("2. RSA");
			console.println("3. Elgamal");
			console.println("4. Cofnij");
			console.println("5. Wyjscie");
			choice = tryParse(console.readLine());
			if (choice <= 5 && choice >= 1)
				break;
		} while (true);
		console.back = false;
		Code coder = null;
		switch (choice) {
		case 1:
			coder = new Cesar();
			break;
		case 2:
			coder = new Rsa();
			break;
		case 3:
			coder = new Elgamal();
			break;
		case 4:
			console.back = true;
			break;
		}
		if (choice == 5)
			System.exit(0);
		if (!console.back)
			switch (module) {
			case 1:
				new Module1().run(console.getConsole(), coder);
				break;
			case 2:
				new Module2().run(console.getConsole(), coder);
				break;
			}
	}

}
