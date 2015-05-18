import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class FileHandler {

	/**
	 * 
	 * @param console Crypts, dzieki ktoremu bedzie mozna wypisac cos na konsoli 
	 * @param filename Nazwa pliku do odczytu
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void readFile(Crypts console, String filename) throws FileNotFoundException, IOException {
	}
	/**
	 * 
	 * @param console Crypts, dzieki ktoremu bedzie mozna wypisac cos na konsoli 
	 * @param filename Nazwa pliku do odczytu
	 * @throws IOException
	 */
	public void saveFile(Crypts console, String filename) throws IOException {
	}

	public void readKeyFile(Crypts console, String filename,Code coder)
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

	@SuppressWarnings("resource")
	public void saveKeyFile(Crypts console, String filename,Code coder) throws IOException {
		File file = new File(Crypts.getKeyName(filename));
		if (file.exists()) {
			if(Crypts.askIfOverwrite(console, file.getName(), file)){
			file.delete();}
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
}
