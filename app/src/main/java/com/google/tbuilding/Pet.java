package com.google.tbuilding;

public class Pet
{
	private String image;
	private String name;
	private String breed;
	private String color;

	public Pet() {

	}

	public Pet(String name, String image, String breed, String color){
		this.name = name;
		this.image = image;
		this.breed = breed;
		this.color = color;
	}

	public String getName(){
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage(){
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
