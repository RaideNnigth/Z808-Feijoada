package z808_gui.components;

import z808_gui.utils.TextLineNumber;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.util.HashMap;

public class AssemblyTextPane extends JScrollPane {
    private JTextPane assemblyTextEditor;
    private static AssemblyTextPane instance = null;
    private final HashMap<String, Color> colorMap = new HashMap<>();
    private final HashMap<String, Color> colorGroups = new HashMap<>();
    private final String instructionsRegex = "(\\W)*(mov|add|sub|mult|hlt)";
    private final String registersRegex = "(\\W)*(ax|dx)";
    private final String segmentsRegex = "(\\s)*(\\.(code|data|stack)\\s+segment)(\\s)*";
    private final String directivesRegex = "(\\W)*(macrodef|endm)";
    private final String numbersRegex = "(\\W)*([0-9])*(b|d|)";
    private final String commentsRegex = ";(.*)";
    private final String stringRegex = "";

    private final StyleContext styleContext = StyleContext.getDefaultStyleContext();
    private final AttributeSet instructionsStyle = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, new Color(23, 161, 165));
    private final AttributeSet registersStyle = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, new Color(211, 84, 0));
    private final AttributeSet labelsStyle = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, new Color(94, 109, 3));
    private final AttributeSet directivesStyle = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.red);//new Color(0, 92, 95)
    private final AttributeSet commentsStyle = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.blue);
    private final AttributeSet segmentsStyle = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.gray);
    private final AttributeSet stringStyle = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.yellow);
    private final AttributeSet defaultStyle = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.BLACK);

    public AssemblyTextPane() {
        assemblyTextEditor = new JTextPane();

        TextLineNumber tln = new TextLineNumber(assemblyTextEditor);
        tln.setUpdateFont(true);

        assemblyTextEditor.setFont(new Font("Consolas", Font.PLAIN, 22));

        setRowHeaderView(tln);
        setViewportView(assemblyTextEditor);


        // Obrigado a https://github.com/stark9000/java-Syntax-Highlight
        DefaultStyledDocument doc = new DefaultStyledDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offset);
                if (before < 0) {
                    before = 0;
                }
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                        String lowerCase = text.substring(wordL, wordR).toLowerCase();

                        if (lowerCase.matches(instructionsRegex)) {
                            setCharacterAttributes(wordL, wordR - wordL, instructionsStyle, false);
                        } else if (lowerCase.matches(registersRegex)) {
                            setCharacterAttributes(wordL, wordR - wordL, registersStyle, false);
                        } else if (lowerCase.matches(directivesRegex)) {
                            setCharacterAttributes(wordL, wordR - wordL, directivesStyle, false);
                        } else if (lowerCase.matches("\"\"")) {
                            setCharacterAttributes(wordL, wordR - wordL, labelsStyle, false);
                        } else if (lowerCase.matches(commentsRegex)) {
                            setCharacterAttributes(wordL, after - wordL, commentsStyle, false);
                        } else if (text.toLowerCase().matches(segmentsRegex)) {
                            setCharacterAttributes(0, after, segmentsStyle, false);
                        } else if (text.toLowerCase().matches(stringRegex)) {
                            //setCharacterAttributes(wordL, wordR - wordL, commentsStyle, false);
                        } else {
                            setCharacterAttributes(wordL, wordR - wordL, defaultStyle, false);
                        }
                        wordL = wordR;
                    }
                    wordR++;
                }
            }

            @Override
            public void remove(int offs, int len) throws BadLocationException {
                super.remove(offs, len);
                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offs);
                if (before < 0) {
                    before = 0;
                }
                int after = findFirstNonWordChar(text, offs);
                try {
                    if (text.substring(before, after - before).matches("(\\W)*(int|array|bool|boolean|byte|char|double|float|long|short|String|unsigned| char|unsigned| int|unsigned| long|void|INPUT|LOW|HIGH|OUTPUT|INPUT_PULLUP|LED_BUILTIN|true|false)")) {
                        setCharacterAttributes(before, after - before, instructionsStyle, false);
                    } else if (text.substring(before, after - before).matches("(\\W)*(Serial|begin|WiFi|print|delay|println|ready|size_t)")) {
                        setCharacterAttributes(before, after - before, registersStyle, false);
                    } else if (text.substring(before, after - before).matches("(\\W)*(#|define|include|setup|else|loop)")) {
                        setCharacterAttributes(before, after - before, labelsStyle, false);
                    } else if (text.substring(before, after - before).matches("(\\W)* (\".*\")")) {
                        setCharacterAttributes(before, after - before, directivesStyle, false);
                    } else {
                        setCharacterAttributes(before, after - before, defaultStyle, false);
                    }
                } catch (Exception e) {
                }
            }
        };

        assemblyTextEditor.setDocument(doc);
    }

    private int findLastNonWordChar(String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar(String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }

    public static AssemblyTextPane getInstance() {
        if (instance == null)
            instance = new AssemblyTextPane();

        return instance;
    }

    public String getText() {
        return assemblyTextEditor.getText();
    }

    public void setText(String s) {
        assemblyTextEditor.setText(s);
    }
}
