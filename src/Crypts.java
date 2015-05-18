import java.io.File;
import java.util.ArrayList;
import java.util.List;

import acm.program.ConsoleProgram;

public class Crypts extends ConsoleProgram {

	private static final long serialVersionUID = -5728780240021982027L;
	public List<String> words = new ArrayList<String>();
	private boolean back = false;

	public void run() {
		setSize(600, 600);
		do {
			mainMenu();
		} while (true);
	}
	
	public static String getNewFilename(String filename){
		return new String(cutExtension(filename)+"_new."+getExtension(filename));		
	}
	public static String getKeyName(String filename){
		return new String(cutExtension(filename)+".key");		
	}
	
	
	public static boolean askIfOverwrite(Crypts console, String filename, File file) {
		String option;
		do {
			console.getConsole().clear();
			console.println("Nadpisać Plik? " + filename
					+ "[T/N]");
			option = console.readNotEmptyLine().trim();
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

	public static String cutExtension(String text) {
		String filename="";
		String temp[] = text.split("\\.");
		if (temp.length > 1) {
			for (int i = 0; i < temp.length - 1; i++) {
				filename += temp[i];
				if(i<temp.length - 2)filename +="\\.";
			}
			return filename;
		}
		return text;
	}
	
	public static String getExtension(String text) {
		String ext="";
		String temp[] = text.split("\\.");
		if (temp.length > 1) {
			ext=temp[temp.length-1];
			return ext;
		}
		return null;
	}
	
	public static void progressBar(Crypts console, int max, int now, String statement) {
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
			for (int i = 0;(i < y+1); i += 1) {
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


	public String readFilename(String statement) {
		println("wpisz esc by anulowac");
		String filename = readNotEmptyLine();
		if (filename.equals("esc") || filename.equals("Esc") || filename.equals("ESC")) {
			println(statement);
			Crypts.waits(1000);
			return null;
		}
		return filename;
	}

	/**
	 * function tries parse String to Integer
	 * 
	 * @return -5 if fail or Integer if success
	 */
	public static Integer tryParse(String text) {
		try {
			return new Integer(text);
		} catch (NumberFormatException e) {
			return -5;
		}
	}

	/**
	 * reads from console as long as something else than white spaces will
	 * appear
	 * 
	 * @return String text from readLine
	 */
	public String readNotEmptyLine() {
		String text;
		do {
			text = new String(readLine());
		} while (text.trim().equals(""));
		return text;
	}

	public static void waits(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void mainMenu() {
		int choice;
		do {
			getConsole().clear();
			println("Program kodujacy i dekodujacy ");
			println("Co chcesz zrobi?");
			println("1. Wlaczyc modul kodujacy i dekodujacy liste slow");
			println("2. Wlaczyc modul kodujacy i dekodujacy pliki");
			println("3. Wyjscie");
			choice = tryParse(readLine());
			if (choice <= 3 && choice >= 1)
				break;
		} while (true);
		do
			menu1(choice);
		while (!back);
		if (choice == 3)
			System.exit(0);
	}

	/*
	 * Menu11 block
	 */
	private void menu1(int module) {
		int choice;
		words.clear();
		do {
			getConsole().clear();
			println("Program kodujacy i dekodujacy ");
			println("Z jakigo algorytmu chcesz korzystac");
			println("1. Kod Cezara");
			println("2. RSA");
			println("3. Elgamal");
			println("4. Cofnij");
			println("5. Wyjscie");
			choice = tryParse(readLine());
			if (choice <= 5 && choice >= 1)
				break;
		} while (true);
		back = false;
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
			back = true;
			break;
		}
		if (choice == 5)
			System.exit(0);
		if (!back)
			switch (module) {
			case 1:
				new Module1().run(this, coder);
				break;
			case 2:
				new Module2().run(this, coder);
				break;
			}
	}

}
