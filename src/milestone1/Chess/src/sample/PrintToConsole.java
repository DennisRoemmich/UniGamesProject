package sample;

/**
 * Printing to the console.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public final class PrintToConsole {
	
	private PrintToConsole() {
		
	}
	
	public static void print(String input) {
		System.out.print(input);
	}
	
	public static void print(char input) {
		System.out.print(input);
	}
	
	public static void println(String input) {
		System.out.println(input);
	}
}
