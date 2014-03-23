package com.teamsweepy.greywater.entity.item;


public interface Chargeable {
	
	public void removeCharge(int remove);
	
	public boolean addCharge(Item add);
	
	public int getCharge();
	
	public int maxCharge();
	
	public boolean isCharger(Item i);
	
	public Item getNoChargeItem();

}
