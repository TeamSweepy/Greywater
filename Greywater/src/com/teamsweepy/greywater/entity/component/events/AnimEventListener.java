/**
 * Interface for any class (mostly sprites) that need to know about the state of animations.
 * 
 * @author Barnes
 */

package com.teamsweepy.greywater.entity.component.events;


public interface AnimEventListener {

	public void handleEvent(AnimEvent event);

}
