package fr.bragabresolin.menhir;

import java.util.*;
import java.io.*;

public class CLIUtils {

	private static final String SIGNE_PROMPT = ">> ";
	private static final String SEPARATEUR = "┕━━━━━━━━━━━━━━━━━━━━━━━━━┙";
	private static final String HEADER = "┌───── JEU DU MENHIR ─────┐\n"
									   + "│ Braga & Bresolin, A2015 │\n"
									   + SEPARATEUR;

	private static Scanner reader = new Scanner(System.in);


	public static String demanderString(String prompt) {
		String input = "";
		afficherPrompt(prompt);
		input = reader.next();

		return input;
	}

	public static int demanderNombre(String prompt, int min, int max) {
		int nombre = 0;
		while (nombre < min || nombre > max) {
			afficherPrompt(prompt + " [" + min + "-" + max + "]");
			try {
				nombre = reader.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Veuillez saisir un nombre valide.");
				reader.nextLine();
			}
		}
		return nombre;
	}

	public static boolean demanderBool(String prompt) {
		String input = "";
		while (!input.equals("o") && !input.equals("oui")
				&& !input.equals("n") && !input.equals("non")
				&& !input.equals("y")) {
			input = demanderString(prompt + " [o/n]");
			input = input.toLowerCase();
		}
		if (input.equals("y") || input.equals("o") || input.equals("oui"))
			return true;
		return false;
	}

	private static void afficherPrompt(String prompt) {
		System.out.println("");
		System.out.println(prompt);
		System.out.print(SIGNE_PROMPT);
	}

	public static void afficherHeader() {
		System.out.println("");
		System.out.println(HEADER);
	}

	public static void infoBox(String message) {
		System.out.println("");
		System.out.println(message);
		System.out.println("");
	}
}
