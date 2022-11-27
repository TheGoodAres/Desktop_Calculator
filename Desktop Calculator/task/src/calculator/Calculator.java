package calculator;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator extends JFrame {
    private final String[] buttonNames = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Add",
            "Subtract", "Divide", "Multiply", "Equals", "Dot"};
    String regexPattern = "((?:([+\\-])?|(?<![+\\-])([+\\-]))(?:(\\d+(?:\\.\\d*)?)\\*)?(?<![+\\-])([+\\-])?(\\d+(?:\\.\\d*)?)?(?:\\^(\\d+))?)*";
    public Calculator () {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setTitle("Calculator");
        setLayout(null);
        //resultLabel
        JLabel resultLabel = new JLabel("Result");
        resultLabel.setName("ResultLabel");
        resultLabel.setText("0");
        resultLabel.setBackground(Color.ORANGE);
        resultLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        resultLabel.setBounds(30, 0, 250, 60);
        //textLabel
        JLabel EquationLabel = new JLabel();
        EquationLabel.setName("EquationLabel");
        EquationLabel.setBounds(30, 60, 250, 30);
        EquationLabel.setForeground(Color.BLACK);
        //clearButton
        JButton clear = new JButton("C");
        clear.setName("Clear");
        clear.addActionListener(ActionEvent -> EquationLabel.setText(""));
        clear.setBounds(150, 90, 40, 40);
        //del
        JButton delete = new JButton("Del");
        delete.setName("Delete");
        delete.addActionListener(ActionEvent -> {
            String currentValue = EquationLabel.getText();
            if (currentValue.length() > 0) {
                char[] characters = new char[currentValue.length() - 1];
                for (int i = 0; i < currentValue.length() - 1; i++) {
                    characters[i] = currentValue.charAt(i);
                }

                EquationLabel.setText(String.valueOf(characters));
            }
        });
        delete.setBounds(200, 90, 40, 40);
        //create buttons and add listeners
        JButton[] buttons = new JButton[buttonNames.length];
        for (int i = 0; i < buttons.length; i++) {
            if (i <= 9) {
                buttons[i] = new JButton(String.valueOf(i));
            } else {
                String operator = switch (buttonNames[i]) {
                    case "Multiply" -> "*";
                    case "Add" -> "+";
                    case "Subtract" -> "-";
                    case "Divide" -> "/";
                    case "Equals" -> "=";
                    case "Dot" -> ".";
                    default -> " ";
                };
                buttons[i] = new JButton(operator);
            }
            buttons[i].setName(buttonNames[i]);
            int finalI = i;
            buttons[i].addActionListener(ActionEvent -> {
                if (!buttons[finalI].getText().matches("[0-9]")) {
                    if (!EquationLabel.getText().contains("=")) {
                        String operator = switch (buttonNames[finalI]) {
                            case "Multiply" -> "×";
                            case "Add" -> "+";
                            case "Subtract" -> "-";
                            case "Divide" -> "÷";
                            case "Dot" -> ".";
                            default -> "";
                        };
                        EquationLabel.setText(EquationLabel.getText() + operator);
                    }
                } else {
                    EquationLabel.setText(EquationLabel.getText() + finalI);
                }

            });
        }
        buttons[14].addActionListener(ActionEvent -> {
                    String valueOfField = EquationLabel.getText();
                    String value = String.valueOf(calculate(valueOfField));
                    String[] valueSplit = value.split("\\.");
                    Pattern pattern = Pattern.compile("[1-9]");
                    Matcher matcher = pattern.matcher(valueSplit[1]);
                    if(matcher.matches()) {
                        resultLabel.setText(value);
                    } else {
                        resultLabel.setText(String.valueOf(Integer.parseInt(valueSplit[0])));
                    }


                }
        );

        buttons[1].setBounds(50, 140, 40, 40);
        buttons[2].setBounds(100, 140, 40, 40);
        buttons[3].setBounds(150, 140, 40, 40);
        buttons[4].setBounds(50, 190, 40, 40);
        buttons[5].setBounds(100, 190, 40, 40);
        buttons[6].setBounds(150, 190, 40, 40);
        buttons[7].setBounds(50, 240, 40, 40);
        buttons[8].setBounds(100, 240, 40, 40);
        buttons[9].setBounds(150, 240, 40, 40);
        buttons[10].setBounds(200, 290, 40, 40);
        buttons[11].setBounds(200, 140, 40, 40);
        buttons[12].setBounds(200, 190, 40, 40);
        buttons[13].setBounds(200, 240, 40, 40);
        buttons[0].setBounds(100, 290, 40, 40);
        buttons[14].setBounds(150, 290, 40, 40);
        buttons[15].setBounds(50, 290, 40, 40);
        for (JButton button : buttons) {
            add(button);
        }
        add(resultLabel);
        add(delete);
        add(clear);
        add(EquationLabel);
        setVisible(true);
    }

    private double calculate (String equation) {
        char[] tokens = equation.toCharArray();

        // Stack for numbers: 'values'
        Stack<Double> values = new
                Stack<Double>();

        // Stack for Operators: 'ops'
        Stack<Character> ops = new
                Stack<Character>();

        for (int i = 0; i < tokens.length; i++)
        {

            // Current token is a
            // whitespace, skip it
            if (tokens[i] == ' ')
                continue;

            // Current token is a number,
            // push it to stack for numbers
            if (tokens[i] >= '0' &&
                    tokens[i] <= '9')
            {

                StringBuffer sbuf = new
                        StringBuffer();

                // There may be more than one
                // digits in number
                while (i < tokens.length &&
                        ((tokens[i] >= '0' &&
                                tokens[i] <= '9') || (tokens[i] == '.'))) {

                    sbuf.append(tokens[i++]);

                }
                values.push(Double.parseDouble(sbuf.
                        toString()));

                // right now the i points to
                // the character next to the digit,
                // since the for loop also increases
                // the i, we would skip one
                //  token position; we need to
                // decrease the value of i by 1 to
                // correct the offset.
                i--;
            }

            // Current token is an opening brace,
            // push it to 'ops'
            /*else if (tokens[i] == '(')
                ops.push(tokens[i]);*/

            // Closing brace encountered,
            // solve entire brace
            /*else if (tokens[i] == ')')
            {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(),
                            values.pop(),
                            values.pop()));
                ops.pop();
            }*/

            // Current token is an operator.
            else if (tokens[i] == '+' ||
                    tokens[i] == '-' ||
                    tokens[i] == '×' ||
                    tokens[i] == '÷')
            {
                // While top of 'ops' has same
                // or greater precedence to current
                // token, which is an operator.
                // Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() &&
                        hasPrecedence(tokens[i],
                                ops.peek()))
                    values.push(applyOp(ops.pop(),
                            values.pop(),
                            values.pop()));

                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }

        // Entire expression has been
        // parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty())
            values.push(applyOp(ops.pop(),
                    values.pop(),
                    values.pop()));

        // Top of 'values' contains
        // result, return it
        return values.pop();
    }

    public static boolean hasPrecedence(
            char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '×' || op1 == '÷') &&
                (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    // A utility method to apply an
    // operator 'op' on operands 'a'
    // and 'b'. Return the result.
    public static double applyOp(char op,
                                 double b, double a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '×':
                return a * b;
            case '÷':
                if (b == 0)
                    throw new
                            UnsupportedOperationException(
                            "Cannot divide by zero");
                return a / b;
        }
        return 0;
    }

}

