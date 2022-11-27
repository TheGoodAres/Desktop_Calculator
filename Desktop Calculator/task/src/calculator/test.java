package calculator;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static void main (String[] args) {
        Pattern pattern = Pattern.compile("((?:([+\\-])?|(?<![+\\-])([+\\-]))(?:(\\d+(?:\\.\\d*)?)\\*)?(?<![+\\-])([+\\-])?(\\d+(?:\\.\\d*)?)?(?:\\^(\\d+))?)*");
        Matcher matcher = pattern.matcher("1+1--");
        System.out.println(matcher.matches());

    }


}
