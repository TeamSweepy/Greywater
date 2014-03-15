package com.teamsweepy.greywater.ui.gui.subgui.bbcode;

/**
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */
public class BBCodeNode {
    public String value;
    public BBCodeTag start, end;
    public int startPos = -1, endPos = -1;

    public BBCodeNode(String value) {
        this.value = value;
    }

    public BBCodeNode() {
        this.value = "";
    }

    @Override
    public String toString() {
        String text = "["+start.node+"]"+value+"[/"+end.node+"]";

        if(start.innerValue != null) {
            text = "["+start.node+"="+start.innerValue+"]"+value+"[/"+end.node+"]";
        }

        return text;
    }
}
