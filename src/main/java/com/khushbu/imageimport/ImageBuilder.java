package com.khushbu.imageimport;

public class ImageBuilder {

	private Image image;
	
	public ImageBuilder newImage(int xSize, int ySize) {
		image = new Image(xSize, ySize);
		return this;
	}
	
	public ImageBuilder setPoint(int x, int y, short value) {
		if (image == null) {
			throw new RuntimeException("Image not initialized yet.");
		}
		image.setPoint(x, y, value);
		if (value > image.getMaximumGreyScale()) {
			image.setMaximumGreyScale(value);
		}
		return this;
	}
	
	public ImageBuilder setMaximumGreyScaleValue(short value) {
		if (image == null) {
			throw new RuntimeException("Image not initialized yet.");
		}
		image.setMaximumGreyScale(value);
		return this;
	}
	
	public ImageBuilder setName(String name) {
		if (image == null) {
			throw new RuntimeException("Image not initialized yet.");
		}
		image.setName(name);
		return this;
	}
	
	public Image buildImage() {
		return image;
	}
}
