package com.teamsweepy.greywater.entity.component.events;

import com.teamsweepy.greywater.entity.item.Item;


public class FetchEvent extends GameEvent {

	public Item acquired;
	
	public FetchEvent(Object source, Item item) {
		super(source);
		acquired = item;
		// TODO Auto-generated constructor stub
	}
	
	

}
