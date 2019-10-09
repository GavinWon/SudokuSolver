import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(new File("/Users/allen/eclipse-workspace/Suduko Solver/src/file"));
		int grid[][] = new int[9][9];
		for (int i = 0; i < 9; i++) {
			String s = scan.nextLine();
			s = s.replaceAll("\\s", "");
			for (int j = 0; j < 9; j++) {
				grid[i][j] = Integer.parseInt(s.charAt(j) + "");
			}
		}
		printBoard(grid);
		System.out.println(possibleEntries(grid, 1, 4));
		System.out.println(isFull(grid));
		// solve(grid);
	}

	static void printBoard(int[][] g) {
		for (int i = 0; i < 9; i++) {
			String s = "";
			for (int j = 0; j < 9; j++) {
				s += g[i][j] + " ";
			}
			System.out.println(s);
		}
	}

	/*
	 * static void solve(int[][] g) { int i = 0, j = 0; if (isFull(g)) {
	 * printBoard(g); return; } else { for (int x = 0; x < 9; x++) { for (int y = 0;
	 * y < 9; y++) { if (g[x][y] == 0) { i = x; j = y; break; } } } }
	 * ArrayList<Integer> p = new ArrayList<Integer>(); p = possibleEntries(g, i,
	 * j); for (int x = 1; x <= 10; x++) { if (p.get(x) != 0) { g[i][j] = p.get(x);
	 * solve(g); } } g[i][j] = 0; }
	 */
	static void solve(int[][] g) {
		if (isFull(g)) {
			printBoard(g);
			return;
		}
		//finds the next zero's location
		int zero_col = nextZero(g)[0], zero_row = nextZero(g)[1];
		
		ArrayList<Integer> possibleMoves = possibleEntries(g, zero_col, zero_row);
		for(int i =0 ; i < possibleMoves.size(); i ++)
			if(!contradicts(g, zero_row, zero_col, possibleMoves.get(i))){
				g[zero_col][zero_row] = possibleMoves.get(i);
			}

	}

	static int[] nextZero(int[][] g) {
		for (int i = 0; i < g.length; i++) {
			for (int j = 0; j < g.length; j++) {
				if (g[i][j] == 0) {
					int[] booga = { i, j };
					return booga;
				}
			}
		}
		int[] nevergonnabeusedlolxd = { 0, 0 };
		return nevergonnabeusedlolxd;
	}

	static ArrayList<Integer> possibleEntries(int[][] g, int row, int col) {
		ArrayList<Integer> possibilities = new ArrayList<Integer>();
		HashSet<Integer> usedNumbers = new HashSet<Integer>();
		if (g[row][col] != 0) {
			possibilities.add(0);
			return possibilities;
		}
		// find all used numbers in row of the 0
		for (int i = 0; i < 9; i++) {
			if (g[row][i] != 0) {
				usedNumbers.add(g[row][i]);
			}
		}
		System.out.println("these numbers are used in the row " + usedNumbers);
		// find all used numbers in the col of 0
		for (int i = 0; i < 9; i++) {
			if (g[i][col] != 0) {
				usedNumbers.add(g[i][col]);
			}
		}

		System.out.println(usedNumbers + " these are the current possible numbers from row and col");
		// after we get the used numbers from the row and col, we need to get the used
		// numbers in the 3 by 3 square
		int k = 0, l = 0;
		if (row >= 0 && row <= 2) {
			k = 0;
		} else if (row > 2 && row <= 5) {
			k = 3;
		} else {
			k = 6;
		}
		if (col >= 0 && col <= 2) {
			l = 0;
		} else if (col > 2 && col <= 5) {
			l = 3;
		} else {
			l = 6;
		}
		// debugging purposes
		ArrayList<Integer> numbersInside3by3 = new ArrayList<Integer>();
		ArrayList<Integer> bigga = new ArrayList<Integer>();
		for (int i = k; i < k + 3; i++) {
			for (int j = l; j < l + 3; j++) {
				numbersInside3by3.add(g[i][j]);
				if (g[i][j] != 0) {
					bigga.add(g[i][j]);
					usedNumbers.add(g[i][j]);
				}
			}
		}
		// System.out.println("numbers inside 3by3: "+numbersInside3by3);
		System.out.println("rounding up " + row + " " + col + " to starting points" + k + " " + l);
		System.out.println("used numbers in 3 by 3 matrix starting at " + bigga);
		System.out.println("These are all the used numbers " + usedNumbers);
		// usedNumbers.addAll(numbersInside3by3);
		for (int i = 1; i <= 9; i++) {
			if (!usedNumbers.contains(i)) {
				possibilities.add(i);
			}
		}
		System.out.println(possibilities + " these are the possible integers for this 0");
		return possibilities;
	}

	static boolean contradicts(int[][] g, int row, int col, int num) {

		g[row][col] = num;
		ArrayList<Integer> yesRepeats = new ArrayList<Integer>();
		HashSet<Integer> noRepeats = new HashSet<Integer>();
		for (int i = 0; i < 9; i++) {
			if (g[row][i] != 0) {
				yesRepeats.add(g[row][i]);
				noRepeats.add(g[row][i]);
			}
		}
		if (yesRepeats.size() != noRepeats.size()) {

			return false;

		}
		yesRepeats.clear();
		noRepeats.clear();

		for (int i = 0; i < 0; i++) {
			if (g[i][col] != 0) {
				yesRepeats.add(g[i][col]);
				noRepeats.add(g[i][col]);
			}
		}
		if (yesRepeats.size() != noRepeats.size()) {

			return false;

		}
		yesRepeats.clear();
		noRepeats.clear();
		int k = 0, l = 0;
		if (row >= 0 && row <= 2) {
			k = 0;
		} else if (row > 2 && row <= 5) {
			k = 3;
		} else {
			k = 6;
		}
		if (col >= 0 && col <= 2) {
			l = 0;
		} else if (col > 2 && col <= 5) {
			l = 3;
		} else {
			l = 6;
		}

		for (int i = k; i < k + 3; i++) {
			for (int j = l; j < l + 3; j++) {
				if (g[i][j] != 0) {
					yesRepeats.add(g[i][j]);
					noRepeats.add(g[i][j]);
				}
			}
		}
		return (yesRepeats.size() == noRepeats.size() ? true : false);

	}

	// works
	static boolean isFull(int[][] g) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (g[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}

}
