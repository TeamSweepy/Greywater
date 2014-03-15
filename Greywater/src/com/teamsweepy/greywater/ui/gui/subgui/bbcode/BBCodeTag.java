package com.teamsweepy.greywater.ui.gui.subgui.bbcode;

/**
 * Created with IntelliJ IDEA.
 * User: Robin de Jong
 * Date: 2:10 PM, 3/15/14
 */
public class BBCodeTag {
    public String node;
    public int start, end;
    public String innerValue;

    public BBCodeTag(String node, int start, int end) {
        this(node, null, start, end);
    }

    public BBCodeTag(String node, String innerValue, int start, int end) {
        this.node = node;
        this.innerValue = innerValue;
        this.start = start;
        this.end = end;
    }
}
