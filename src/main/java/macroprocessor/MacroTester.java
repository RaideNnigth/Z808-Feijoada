package macroprocessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MacroTester {
    public static void main(String[] args) {
        MacroProcessor macroProcessor = MacroProcessor.getInstance();
        try {
            var outpath = macroProcessor.parseMacros("src\\programs\\macrotest.asm");
            System.out.println("Output file: " + outpath);
        } catch (Exception e) {
            System.out.printf("Error: %s\n", e.getMessage());
            e.printStackTrace();
        }

        // Matcher tests
        /*
        final int[] testArr = {726, 235};
        final String REGEX = "#[\\d]";
        String INPUT = "ADD 12, #0 #0\nSUB AX, #0";

        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(INPUT);

        while (m.find()) {
            System.out.println(m.start() + " " + m.end());
            System.out.println(INPUT.substring(m.start() + 1, m.end()));
            System.out.println(m.group());
            int index = Integer.parseInt(INPUT.substring(m.start() + 1, m.end()));
            INPUT = INPUT.replaceFirst(REGEX, String.valueOf(testArr[index]));
            System.out.println(INPUT + "\n");
            m = p.matcher(INPUT);
        }
         */

    }
}
