import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Bingo {

	public static void main(String[] args) {
		Random r = new Random();
		Scanner sc = new Scanner(System.in);
		int extracted = 0;
		int[] pallottoliere = new int[90];
		HashSet<Integer> nEstratti = new HashSet<>();

		for (int i = 0; i < 90; i++) {
			pallottoliere[i] = i + 1;
		}

		System.out.println("Benvenut3 al Bingo");
		String continua;
		do {
			System.out.print("Quante schede vuoi? (1 - 6) ");
			int nSchedePrese = sc.nextInt();
			sc.nextLine();  // Consuma la newline
			if (nSchedePrese < 1 || nSchedePrese > 6) {
				System.out.println("Numero di schede non valido. Il numero di schede è stato impostato a 1.");
				nSchedePrese = 1;
			}

			int[][][] schede = new int[nSchedePrese][3][5];
			// Generazione delle schede
			nEstratti.clear();
			for (int s = 0; s < nSchedePrese; s++) {
				HashSet<Integer> numSet = new HashSet<>();
				while (numSet.size() < 15) {
					numSet.add(pallottoliere[r.nextInt(90)]);
				}
				Integer[] numArray = numSet.toArray(new Integer[0]);
				for (int i = 0; i < 15; i++) {
					schede[s][i / 5][i % 5] = numArray[i];
				}
			}

			// stampaggio schede iniziali
			System.out.println("Ecco le tue schede iniziali:");
			int schedePerRiga = 3;  // Numero di schede per riga
			for (int s = 0; s < nSchedePrese; s += schedePerRiga) {
				for (int i = 0; i < 3; i++) {  // Riga all'interno della scheda
					for (int k = s; k < s + schedePerRiga && k < nSchedePrese; k++) {
						for (int j = 0; j < 5; j++) {
							if (schede[k][i][j] < 0) {
								System.out.printf("%4s", "*" + (-schede[k][i][j])); // Stampa con asterisco
							} else {
								System.out.printf("%4d", schede[k][i][j]);
							}
						}
						System.out.print("   "); // Spazio tra le schede
					}
					System.out.println();
				}
				System.out.println("----- ----- ----- ----- ----- ----- ----- ----- ----- -----");
			}

			boolean bingo = false;
			boolean[] cinquina = new boolean[nSchedePrese];

			// inizio estrazione numeri
			while (!bingo) {
				extracted = pallottoliere[r.nextInt(90)];
				while (nEstratti.contains(extracted)) {
					extracted = pallottoliere[r.nextInt(90)];
				}
				nEstratti.add(extracted);
				System.out.println("Numero estratto: " + extracted);

				// Controlla se il numero estratto è presente nelle schede
				for (int s = 0; s < nSchedePrese; s++) {
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 5; j++) {
							if (schede[s][i][j] == extracted) {
								schede[s][i][j] = -extracted; // Segna il numero come estratto con segno negativo
								System.out.println("È presente nella scheda " + (s + 1) + "!");
							}
						}
					}

					// Controlla cinquina
					for (int i = 0; i < 3; i++) {
						int count = 0;
						for (int j = 0; j < 5; j++) {
							if (schede[s][i][j] < 0) {
								count++;
							}
						}
						if (count == 5 && !cinquina[s]) {
							System.out.println("Cinquina! Hai completato una riga nella scheda " + (s + 1) + ".");
							cinquina[s] = true;
						}
					}

					// Controlla bingo
					int remainingNumbers = 0;
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 5; j++) {
							if (schede[s][i][j] > 0) {
								remainingNumbers++;
							}
						}
					}
					if (remainingNumbers == 0) {
						bingo = true;
						System.out.println("Bingo! Hai completato la scheda " + (s + 1) + ".");
					}
				}

				// stampa le schede aggiornate
				System.out.println("Schede aggiornate:");
				for (int s = 0; s < nSchedePrese; s += schedePerRiga) {
					for (int i = 0; i < 3; i++) {  // Riga all'interno della scheda
						for (int k = s; k < s + schedePerRiga && k < nSchedePrese; k++) {
							for (int j = 0; j < 5; j++) {
								if (schede[k][i][j] < 0) {
									System.out.printf("%4s", "*" + (-schede[k][i][j])); // Stampa con asterisco
								} else {
									System.out.printf("%4d", schede[k][i][j]);
								}
							}
							System.out.print("   "); // Spazio tra le schede
						}
						System.out.println();
					}
					System.out.println("----- ----- ----- ----- ----- ----- ----- ----- ----- -----");
				}
			}

			System.out.print("Vuoi giocare un'altra partita? (si/no): ");
			continua = sc.nextLine();
		} while (continua.equalsIgnoreCase("si"));

		sc.close();
	}
}
