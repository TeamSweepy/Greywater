package com.teamsweepy.greywater.ui.gui.subgui.bbcode;

//import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */
public class BBCodeParser {
    private static final String DEFAULT_REGEX = "(?:\\[((?:[a-z]|\\*){1,16})(?:=([^\\x00-\\x1F\"'()<>\\[\\]]{1,2083}))?\\])|(?:\\[/([a-z]{1,16})\\])";
    private static final Pattern DEFAULT_PATERN = Pattern.compile(DEFAULT_REGEX, Pattern.CASE_INSENSITIVE);

    public static BBCodeNode[] parse(String text) {
        return parse(text, DEFAULT_PATERN);
    }

    public static BBCodeNode[] parse(String text, Pattern pattern) {
        List<BBCodeNode> nodes = new ArrayList<BBCodeNode>();

        Matcher matcher = pattern.matcher(text);
        boolean end = false;
        int start = 0;


        BBCodeNode node = null;

        while(matcher.find()) {
            if(end) {
                node.value = text.substring(start, matcher.start());
                node.end = new BBCodeTag(matcher.group(3), matcher.start(), matcher.end());
                nodes.add(node);

                end = false;
            } else {
                if(matcher.group(1) != null) {
                    start = matcher.end();

                    node = new BBCodeNode();
                    BBCodeTag tag;

                    if(matcher.group(2) != null) {
                        tag = new BBCodeTag(
                                matcher.group(1),
                                matcher.group(2),
                                matcher.start(),
                                matcher.end()
                        );
                    } else {
                        tag = new BBCodeTag(
                                matcher.group(1),
                                matcher.start(),
                                matcher.end()
                        );
                    }

                    node.start = tag;
                    end = true;
                }
            }
        }

        BBCodeNode[] temp = new BBCodeNode[nodes.size()];
        nodes.toArray(temp);
        return temp;
    }

    /**
     * Replaces the BBCode text with normal text, and gives the nodes a start and end position
     *
     * @param text The text to be replaced
     * @param nodes All the BBCodes that needs to be replaced
     * @return The new text without BBCode
     *
     * 20, 37
     * 39, 48
     * 50, 59
     * 61, 70
     * 72, 81
     * 83, 92
     * 94, 103
     * 105, 114
     */
    public static String replaceText(String text, BBCodeNode[] nodes) {
        List<BBCodeNode> toBeRemovedNodes = new ArrayList<BBCodeNode>(Arrays.asList(nodes));

        String newLine = System.getProperty("line.separator");

        String[] textArray = text.split(newLine);
        String temp = "";

        for(int i = 0; i < textArray.length; i ++) {
            String currentText = textArray[i] + newLine;
            for(BBCodeNode node : toBeRemovedNodes){
                if(textArray[i].equals(node.toString())) {
                    String tempText = temp + textArray[i];

                    int index = tempText.indexOf(node.toString()) - (i * 2);
                    int last = index + node.value.length();

                    node.startPos = index;
                    node.endPos = last;

                    currentText = node.value+newLine;
                    toBeRemovedNodes.remove(node);
                    break;
                }
            }
            temp += currentText;
        }

        return temp;
    }
}
