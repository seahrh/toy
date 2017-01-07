package toy.rm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;
import java.util.Set;
import java.util.HashSet;

public final class Spreadsheet {
	private static final Pattern EXPRESSION_PATTERN = Pattern.compile("[ a-zA-Z]");
	private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Set<String> OPERATORS_ON_TWO_OPERANDS = new HashSet<>();
	private static Set<String> OPERATORS_ON_ONE_OPERAND = new HashSet<>();

	static {
		OPERATORS_ON_TWO_OPERANDS.add("+");
		OPERATORS_ON_TWO_OPERANDS.add("-");
		OPERATORS_ON_TWO_OPERANDS.add("*");
		OPERATORS_ON_TWO_OPERANDS.add("/");
		OPERATORS_ON_ONE_OPERAND.add("++");
		OPERATORS_ON_ONE_OPERAND.add("--");
	}

	public static void main(String[] args) throws IOException {
		String[][] sheet = input();
		compute(sheet);
		print(sheet);
	}

	// Idea: process the spreadsheet in one pass, row by row, then from left to
	// right.
	// Expressions, once evaluated, are written into the spreadsheet.
	// The expression is tokenized, then the tokens are pushed into a stack,
	// TAIL first.
	// If the token is an operator, push it into the stack.
	// If the token is a value and the top of stack is NOT an operator on two
	// operands,
	// then pop tokens off the stack for computation.

	// Bug that I did not manage to fix in time, infinite loop.
	// I update the spreadsheet once an expression is evaluated,
	// not sure if this is causing the problem.

	private static double compute(String[][] sheet, int targetRow,
			int targetCol, int originRow, int originCol) {
		String expr = sheet[targetRow][targetCol];
		String[] tokens = expr.split(" ");
		int i = tokens.length - 1;
		Stack<String> stack = new Stack<>();
		String token;
		String firstOperand;
		String secondOperand;
		String operator;
		double d;
		while (i >= 0) {
			token = tokens[i];
			if (!isOperator(token)) {
				firstOperand = token;
				if (!stack.isEmpty()) {
					token = stack.peek();
					if (isOperatorOnOneOperand(token)) {
						operator = stack.pop();
						d = toDouble(firstOperand, sheet, originRow, originCol);
						if (operator.equals("++")) {
							d++;
						} else if (operator.equals("--")) {
							d--;
						}
						token = String.valueOf(d);
					} else if (isOperatorOnTwoOperands(token)) {
						token = firstOperand;
					} else {
						secondOperand = stack.pop();
						operator = stack.pop();
						d = compute(firstOperand, secondOperand, operator,
								sheet, originRow, originCol);
						token = String.valueOf(d);
					}
				}
			}
			stack.push(token);
			i--;
		}
		if (stack.size() == 1) {
			d = toDouble(stack.pop(), sheet, originRow, originCol);
			sheet[targetRow][targetCol] = String.valueOf(d);
			return d;
		}
		firstOperand = stack.pop();
		secondOperand = stack.pop();
		operator = stack.pop();
		d = compute(firstOperand, secondOperand, operator, sheet, originRow,
				originCol);
		sheet[targetRow][targetCol] = String.valueOf(d);
		return d;
	}

	private static double compute(String firstOperand, String secondOperand,
			String operator, String[][] sheet, int originRow, int originCol) {
		double d1 = toDouble(firstOperand, sheet, originRow, originCol);
		double d2 = toDouble(secondOperand, sheet, originRow, originCol);
		if (operator.equals("+")) {
			return d1 + d2;
		}
		if (operator.equals("-")) {
			return d1 - d2;
		}
		if (operator.equals("*")) {
			return d1 * d2;
		}
		if (operator.equals("/")) {
			return d1 / d2;
		}
		throw new IllegalStateException("Illegal operator");
	}

	private static double toDouble(String operand, String[][] sheet,
			int originRow, int originCol) {
		if (isCellReference(operand)) {
			int[] indices = toIndices(operand);
			if (indices[0] == originRow && indices[1] == originCol) {
				System.err.println("Detected cyclic dependencies at row "
						+ originRow + " col " + originCol);
				System.exit(1);
			}
			operand = sheet[indices[0]][indices[1]];
			if (isExpression(operand)) {
				return compute(sheet, indices[0], indices[1], originRow,
						originCol);
			}
		}
		return Double.parseDouble(operand);
	}

	private static void compute(String[][] sheet) {
		String cell;
		for (int i = 0; i < sheet.length; i++) {
			for (int j = 0; j < sheet[0].length; j++) {
				cell = sheet[i][j];
				if (!isExpression(cell)) {
					sheet[i][j] = String.format("%.5f",
							Double.parseDouble(cell));
					continue;
				}
				sheet[i][j] = String.format("%.5f", compute(sheet, i, j, i, j));
			}
		}
	}

	private static boolean isOperatorOnOneOperand(String token) {
		return OPERATORS_ON_ONE_OPERAND.contains(token);
	}

	private static boolean isOperatorOnTwoOperands(String token) {
		return OPERATORS_ON_TWO_OPERANDS.contains(token);
	}

	private static boolean isOperator(String token) {
		return isOperatorOnTwoOperands(token) || isOperatorOnOneOperand(token);
	}

	private static boolean isCellReference(String operand) {
		char first = operand.charAt(0);
		return LETTERS.indexOf(first) != -1;
	}

	private static String toCellReference(int row, int col) {
		StringBuilder sb = new StringBuilder();
		sb.append(LETTERS.charAt(row));
		sb.append(col);
		return sb.toString();
	}

	private static int[] toIndices(String cellReference) {
		char first = cellReference.charAt(0);
		int row = LETTERS.indexOf(first);
		int col = Integer.parseInt(cellReference.substring(1));
		return new int[] { row, col };
	}

	private static boolean isExpression(String cell) {
		Matcher m = EXPRESSION_PATTERN.matcher(cell);
		return m.find();
	}

	private static String[][] input() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		int width = 0;
		int height = 0;
		int n = 0;
		int m = 0;
		String[] tokens;
		String[][] ret = null;
		try {
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
			br.close();
		}
		return ret;
	}

	private static void print(String[][] sheet) {
		int m = sheet.length;
		int n = sheet[0].length;
		System.out.println(String.format("%d %d", n, m));
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				System.out.println(sheet[i][j]);
				// System.out.println(isExpression(sheet[i][j]));
			}
		}
	}

}
