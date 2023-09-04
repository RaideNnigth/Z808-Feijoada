package z808_gui.utils;

import javax.swing.text.*;
import java.awt.*;
import java.util.HashMap;

public class SyntaxHighlightingProfile {
    private final HashMap<SyntaxHighlightingGroup, AttributeSet> attributeGroups = new HashMap<>();
    private final HashMap<SyntaxHighlightingGroup, Color> colorGroups = new HashMap<>();
    private final HashMap<SyntaxHighlightingGroup, String> regexGroups = new HashMap<>();

    private final StyleContext styleContext = StyleContext.getDefaultStyleContext();

    public SyntaxHighlightingProfile() {
        this.reset();
    }

    public void reset() {
        this.regexGroups.put(SyntaxHighlightingGroup.INSTRUCTIONS, "(\\W)*(mov|add|sub|mult|hlt|jmp)");
        this.regexGroups.put(SyntaxHighlightingGroup.REGISTERS, "(\\W)*(ax|dx)");
        this.regexGroups.put(SyntaxHighlightingGroup.SEGMENTS, "(\\s)*(\\.(code|data|stack)\\s+segment)(\\s)*");
        this.regexGroups.put(SyntaxHighlightingGroup.DIRECTIVES, "(\\W)*(macrodef|endm|callm|name|extrn|public)");
        this.regexGroups.put(SyntaxHighlightingGroup.NUMBERS, "(\\W)*([0-9])*(b|d|)");
        this.regexGroups.put(SyntaxHighlightingGroup.COMMENTS, ";(.*)");
        this.regexGroups.put(SyntaxHighlightingGroup.STRINGS, "");
        this.regexGroups.put(SyntaxHighlightingGroup.DEFAULT, "");
        this.regexGroups.put(SyntaxHighlightingGroup.LABELS, "");

        this.colorGroups.put(SyntaxHighlightingGroup.INSTRUCTIONS, new Color(23, 161, 165));
        this.colorGroups.put(SyntaxHighlightingGroup.REGISTERS, new Color(211, 84, 0));
        this.colorGroups.put(SyntaxHighlightingGroup.SEGMENTS, new Color(94, 109, 3));
        this.colorGroups.put(SyntaxHighlightingGroup.DIRECTIVES, Color.red);
        this.colorGroups.put(SyntaxHighlightingGroup.NUMBERS, Color.blue);
        this.colorGroups.put(SyntaxHighlightingGroup.COMMENTS, Color.yellow);
        this.colorGroups.put(SyntaxHighlightingGroup.STRINGS, Color.BLACK);
        this.colorGroups.put(SyntaxHighlightingGroup.DEFAULT, Color.BLACK);
        this.colorGroups.put(SyntaxHighlightingGroup.LABELS, Color.BLACK);

        this.attributeGroups.put(SyntaxHighlightingGroup.INSTRUCTIONS, styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, new Color(23, 161, 165)));
        this.attributeGroups.put(SyntaxHighlightingGroup.REGISTERS, styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, new Color(211, 84, 0)));
        this.attributeGroups.put(SyntaxHighlightingGroup.SEGMENTS, styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, new Color(94, 109, 3)));
        this.attributeGroups.put(SyntaxHighlightingGroup.DIRECTIVES, styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.red));
        this.attributeGroups.put(SyntaxHighlightingGroup.NUMBERS, styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.blue));
        this.attributeGroups.put(SyntaxHighlightingGroup.COMMENTS, styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.yellow));
        this.attributeGroups.put(SyntaxHighlightingGroup.STRINGS, styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.BLACK));
        this.attributeGroups.put(SyntaxHighlightingGroup.DEFAULT, styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.BLACK));
        this.attributeGroups.put(SyntaxHighlightingGroup.LABELS, styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.BLACK));
    }

    public AttributeSet getAttribute(SyntaxHighlightingGroup group, StyleContext st) {
        return st.addAttribute(st.getEmptySet(), StyleConstants.Foreground, this.colorGroups.get(group));
    }

    public Color getColor(SyntaxHighlightingGroup gp) {
        return this.colorGroups.get(gp);
    }

    public void setColor(SyntaxHighlightingGroup group, Color color) {
        //this.attributeGroups.put(group, this.styleContext.addAttribute(this.styleContext.getEmptySet(), StyleConstants.Foreground, color));
    }

    public String getRegex(SyntaxHighlightingGroup group) {
        return this.regexGroups.get(group);
    }

    public void setRegex(SyntaxHighlightingGroup group, String regex) {
        this.regexGroups.put(group, regex);
    }

    /*
    public DefaultStyledDocument getDefaultStyleDoc(StyleContext styleContext) {
        // Obrigado a https://github.com/stark9000/java-Syntax-Highlight



        return new DefaultStyledDocument() {
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

                        for (SyntaxHighlightingGroup gp : SyntaxHighlightingGroup.values()) {
                            if (lowerCase.matches(getRegex(gp))) {
                                switch (gp) {
                                    case COMMENTS ->
                                            setCharacterAttributes(wordL, after - wordL, getAttribute(gp), false);
                                    case SEGMENTS ->
                                            setCharacterAttributes(0, after, getAttribute(SyntaxHighlightingGroup.SEGMENTS), false);
                                    default -> setCharacterAttributes(wordL, wordR - wordL, getAttribute(gp), false);
                                }
                            } else {
                                setCharacterAttributes(wordL, wordR - wordL, getAttribute(SyntaxHighlightingGroup.DEFAULT), false);
                            }
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
                    setCharacterAttributes(before, after - before, getAttribute(SyntaxHighlightingGroup.DEFAULT), false);
                } catch (Exception e) {

                }
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
        };
    }
    */
}
