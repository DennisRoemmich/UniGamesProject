package frameworkchess;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Prints an error message to the gamelog.txt file.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public final class WriteError {	
	
	private WriteError() {	
		
	}	
	
	public static void writeErrorLog(String errorMessage) {
		if(errorMessage == "") {
			return;
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("gamelog.txt"));) {		
				writer.write(errorMessage);
		} catch (Exception e) {
			e.printStackTrace();			
		} 
	} 
}
