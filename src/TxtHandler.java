import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import acm.io.IOConsole;


public class TxtHandler extends FileHandler {

	/**
	 * Wczytywanie listy slow z pliku txt funkcja readLine() klasy BufferedReader
	 * 
	 * @param console Crypts, dzieki ktoremu bedzie mozna wypisac cos na konsoli 
	 * @param filename Nazwa pliku do odczytu
	 * @return zwraca liste wczytanych slow
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<String> readFile(IOConsole console, String filename)
			throws FileNotFoundException, IOException {
		words.clear();
		BufferedReader br = new BufferedReader(
				new FileReader(filename));
		while (br.ready()) {
			words.add((br.readLine()));
		}
		br.close();
		return words;
	}

	/**
	 * Zapisywanie do pliku linia po lini oddzielone System.getProperty("line.separator")
	 * 
	 * @param console Crypts, dzieki ktoremu bedzie mozna wypisac cos na konsoli 
	 * @param filename Nazwa pliku do odczytu
	 * @param words lista lini do zapisania
	 * @throws IOException
	 */
	public void saveFile(IOConsole console, String filename,List<String> words) throws IOException {
		File file = new File(filename);
		if (file.exists()) {
			if(Crypts.askIfOverwrite(console,file))
			file.delete();
		}
		if (!file.exists()) {
			file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (String word : words) {
				bw.write(word+System.getProperty("line.separator"));
			}
			bw.close();
		}
	}
}
