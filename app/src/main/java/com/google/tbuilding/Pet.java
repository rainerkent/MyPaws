package com.google.tbuilding;

public class Pet
{
	private int image;
	private String name;
	
	public Pet(String name, int image){
		this.name = name;
		this.image = image;
	}
	
	public String getName(){
		return name;
	}
	
	public int getImage(){
		return image;
	}
}
