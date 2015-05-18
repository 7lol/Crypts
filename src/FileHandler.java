import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import acm.io.IOConsole;

public abstract class FileHandler {
	public List<String> words = new ArrayList<String>();

	/**
	 * @param console Crypts, dzieki ktoremu bedzie mozna wypisac cos na konsoli 
	 * @param filename Nazwa pliku do odczytu
	 * @return zwraca liste wczytanych slow
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<String> readFile(IOConsole console, String filename) throws FileNotFoundException, IOException {
		return null;
	}
	
	/**
	 * 
	 * @param console Crypts, dzieki ktoremu bedzie mozna wypisac cos na konsoli 
	 * @param filename Nazwa pliku do odczytu
	 * @param words lista lini do zapisania
	 * @throws IOException
	 */
	public void saveFile(IOConsole console, String filename, List<String> words) throws IOException {
	}
	
	/**
	 * 
	 * @param console
	 * @param filename
	 * @param coder
	 * @throws IOException
	 */
	public void readKeyFile(IOConsole console, String filename,Code coder)
			throws IOException {
		List<String> words2 = new ArrayList<String>();
		BufferedReader br = new BufferedReader(
				new FileReader(Crypts.getKeyName(filename)));
		while (br.ready()) {
			words2.add((br.readLine()));
		}
		String lol = new String("");
		for (String word : words2) {
			lol += word + " ";
		}
		br.close();
		coder.setKeys(lol);
	}

	/**
	 * Zapisywanie kluczu szyfrowania do pliku
	 * 
	 * @param console interface programu-konsola
	 * @param filename nazwa pliku do zapisania
	 * @param coder klasa modelujaca szyfrowania
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public void saveKeyFile(IOConsole console, String filename,Code coder) throws IOException {
		File file = new File(Crypts.getKeyName(filename));
		if (file.exists()) {
			if(Crypts.askIfOverwrite(console,file)){
			file.delete();}
		}
		if (!file.exists()) {
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
}
