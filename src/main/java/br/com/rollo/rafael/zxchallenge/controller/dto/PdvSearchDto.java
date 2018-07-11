package br.com.rollo.rafael.zxchallenge.controller.dto;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class PdvSearchDto {

	private double longitude;
	private double latitude;

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public Point toPoint() {
		Coordinate coordinate = new Coordinate(longitude, latitude);
		return new GeometryFactory().createPoint(coordinate);
	}
}
