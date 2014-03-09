
package com.teamsweepy.greywater.entities.components;

import java.util.EventObject;


@SuppressWarnings("serial")
public class AnimEvent extends EventObject {

	public Object Source;
	public String action;
	public boolean ending;
	public boolean beginning;


	public AnimEvent(Object source, String action, boolean ending, boolean beginning) {
		super(source);
		this.action = action;
		this.source = source;
		this.ending = ending;
		this.beginning = beginning;
	}
}
