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
			new Module1().run(this,coder);
			break;
		case 2:
			while (!back) {
				//menu3();
			}
			break;
		}
	}



}
