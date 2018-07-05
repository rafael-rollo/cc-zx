package br.com.rollo.rafael.zxchallenge.controller.dto;

import java.util.Arrays;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CoverageAreaDto {

	@NotBlank
	private String type;
	@NotNull
	private double[][][][] coordinates;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double[][][][] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(double[][][][] coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public String toString() {
		return "CoverageAreaDto [type=" + type + ", coordinates=" + Arrays.toString(coordinates) + "]";
	}
	
}