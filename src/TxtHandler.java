import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class TxtHandler extends FileHandler {


	public void readFile(Crypts console, String filename,Code coder)
			throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(
				new FileReader(filename + ".txt"));
		while (br.ready()) {
			console.words.add((br.readLine()));
		}
		br.close();
	}

	public void saveFile(Crypts console, File file,Code coder) throws IOException {
		if (file.exists()) {
			if(Crypts.askIfOverwrite(console, file.getName(), file))
			file.delete();
		}
		if (!file.exists()) {
			file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (String word : console.words) {
				bw.write(word+System.getProperty("line.separator"));
			}
			bw.close();
		}
	}
}
