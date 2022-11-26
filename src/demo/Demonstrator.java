package demo;

public class Demonstrator {

	public static void main(String[] args) {
		Display.presentation();
		Database.connection();
		Display.helper();

		Console.sethook();
		Console.prompt();
	}
}
