package toy.rm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Spreadsheet {
	private static final Logger log = LoggerFactory.getLogger(Spreadsheet.class);
	private static final String INPUT_FILE_PATH = System.getProperty("toy.input");
	private static final String OUTPUT_FILE_PATH = System.getProperty("toy.output");
	private static final Pattern EXPRESSION_PATTERN = Pattern.compile("[ a-zA-Z]");
	private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static void main(String[] args) throws IOException {
		String[][] sheet = input();
		debug(sheet);
		eval(sheet);
		output(sheet);
	}

	/**
	 * Evaluates the spreadsheet row by row, then from left to right.
	 * 
	 * @param sheet
	 */
	private static void eval(String[][] sheet) {
		String cell;
		for (int i = 0; i < sheet.length; i++) {
			for (int j = 0; j < sheet[0].length; j++) {
				cell = sheet[i][j];
				if (!isExpression(cell)) {
					continue;
				}
				eval(sheet, i, j);
			}
		}
	}

	/**
	 * Initiate the evaluation by adding the first cell to a Set of
	 * dependencies.
	 * 
	 * @param sheet
	 * @param rowIndex
	 * @param colIndex
	 */
	private static void eval(String[][] sheet, int rowIndex, int colIndex) {
		Set<String> dependencies = new HashSet<>();
		dependencies.add(toCellReference(rowIndex, colIndex));
		eval(sheet, rowIndex, colIndex, dependencies);
	}

	/**
	 * Recursively evaluate the expression.
	 * <p>
	 * Use only one stack to hold the operands (a variant of Djikstra's
	 * two-stack algorithm). When an operator is encountered, pop the required
	 * number of operands and perform the evaluation.
	 * <p>
	 * Detect cyclic dependencies by using a Set to hold visited cell
	 * references.
	 * 
	 * @param sheet
	 * @param rowIndex
	 * @param colIndex
	 * @param dependencies
	 */
	private static void eval(String[][] sheet, int rowIndex, int colIndex,
			Set<String> dependencies) {
		String expr = sheet[rowIndex][colIndex];
		String[] tokens = expr.split(" ");
		Stack<Double> operands = new Stack<>();
		double d1;
		double d2;
		int[] indices;
		for (String token : tokens) {
			if (token.equals("+")) {
				d2 = operands.pop();
				d1 = operands.pop();
				operands.push(d1 + d2);
			} else if (token.equals("-")) {
				d2 = operands.pop();
				d1 = operands.pop();
				operands.push(d1 - d2);
			} else if (token.equals("*")) {
				d2 = operands.pop();
				d1 = operands.pop();
				operands.push(d1 * d2);
			} else if (token.equals("/")) {
				d2 = operands.pop();
				d1 = operands.pop();
				operands.push(d1 / d2);
			} else if (token.equals("++")) {
				d1 = operands.pop();
				d1++;
				operands.push(d1);
			} else if (token.equals("--")) {
				d1 = operands.pop();
				d1--;
				operands.push(d1);
			} else if (isCellReference(token)) {
				if (dependencies.contains(token)) {
					log.error(
							"Cyclic dependencies detected\nDuplicate={}\nDependencies={}",
							token, dependencies);
					throw new IllegalStateException();
				}
				dependencies.add(token);
				indices = toIndices(token);
				eval(sheet, indices[0], indices[1], dependencies);
				// Push the value of the cell reference back onto the stack!
				operands.push(Double.parseDouble(sheet[indices[0]][indices[1]]));
			} else {
				// Push value onto the stack
				operands.push(Double.parseDouble(token));
			}
			log.info("sheet[{}][{}]={}, operands={}", rowIndex, colIndex,
					token, operands);
		}
		sheet[rowIndex][colIndex] = String.valueOf(operands.pop());
	}

	private static boolean isCellReference(String operand) {
		char first = operand.charAt(0);
		return LETTERS.indexOf(first) != -1;
	}

	private static String toCellReference(int row, int col) {
		StringBuilder sb = new StringBuilder();
		sb.append(LETTERS.charAt(row));
		sb.append(++col);
		return sb.toString();
	}

	private static int[] toIndices(String cellReference) {
		char first = cellReference.charAt(0);
		int row = LETTERS.indexOf(first);
		int col = Integer.parseInt(cellReference.substring(1));
		return new int[] { row, --col };
	}

	private static boolean isExpression(String cell) {
		Matcher m = EXPRESSION_PATTERN.matcher(cell);
		return m.find();
	}

	private static File output(String[][] sheet) throws IOException {
		BufferedWriter bw = null;
		File file = new File(OUTPUT_FILE_PATH);
		StringBuilder sb;
		int n;
		int m;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			n = sheet[0].length;
			m = sheet.length;
			sb = new StringBuilder();
			sb.append(n);
			sb.append(" ");
			sb.append(m);
			sb.append("\n");
			bw.write(sb.toString());
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					sb = new StringBuilder();
					sb.append(String.format("%.5f",
							Double.parseDouble(sheet[i][j])));
					sb.append("\n");
					bw.write(sb.toString());
				}
			}
		} finally {
			if (bw != null) {
				bw.close();
			}
		}
		return file;
	}

	private static String[][] input() throws IOException {
		BufferedReader br = null;
		File file = new File(INPUT_FILE_PATH);
		int width = 0;
		int height = 0;
		int n = 0;
		int m = 0;
		String line;
		String[] tokens;
		String[][] ret = null;
		try {
			br = new BufferedReader(new FileReader(file));
			line = br.readLine();
			tokens = line.split(" ");
			width = Integer.parseInt(tokens[0]);
			height = Integer.parseInt(tokens[1]);
			ret = new String[height][width];
			while ((line = br.readLine()) != null) {
				ret[m][n] = line;
				n++;
				if (n == width) {
					n = 0;
					m++;
				}
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return ret;
	}

	private static void debug(String[][] sheet) {
		int m = sheet.length;
		int n = sheet[0].length;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				log.info(sheet[i][j]);
			}
		}
	}

}
