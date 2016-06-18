package com.khushbu.imageimport;

import Jama.Matrix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Image {

	private int xSize;
	private int ySize;
	private short[][] data;
	private short maximumGreyScale;
	private String name;
	
	public Image(int xSize, int ySize) {

		this.xSize = xSize;
		this.ySize = ySize;
		data = new short[ySize][xSize];
	}
	
	public short[][] getData() {
		return data;
	}
	
	public Matrix getDataAsSingleDimensionalVector() {
		Matrix vector = new Matrix(xSize * ySize, 1);

		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				vector.set(x * ySize + y, 0, data[y][x]);
			}
		}
		
		return vector;
	}
	
	public void setPoint(int x, int y, short value) {
		data[y][x] = value;
	}
	public short getMaximumGreyScale() {
		return maximumGreyScale;
	}
	public void setMaximumGreyScale(short maximumGreyScale) {
		this.maximumGreyScale = maximumGreyScale;
	}
	
	public void exportToPgm(String fileName) throws IOException {

		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();			
		}
		
		FileWriter writer = new FileWriter(file);
				
		writer.append("P2\n");
		writer.append("" + xSize + " " + ySize + "\n");
		writer.append("" + maximumGreyScale + "\n");
		
		for (int y = 0; y < data.length; y++) {
			short[] currentLine = data[y];
			for (int x = 0; x < currentLine.length; x++) {
				short value = currentLine[x];
				writer.append("" + value + " ");
			}
			writer.append("\n");			
		}
		writer.close();
	}

	public int getXSize() {
		return xSize;
	}

	public int getYSize() {
		return ySize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
