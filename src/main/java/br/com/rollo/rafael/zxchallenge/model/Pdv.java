package br.com.rollo.rafael.zxchallenge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;

@Entity
public class Pdv {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String tradingName;
	private String ownerName;

	@Column(unique = true)
	private String document;
	private MultiPolygon coverageArea;
	private Point address;

	Pdv() {
	}

	public Pdv(String tradingName, String ownerName, String document, MultiPolygon coverageArea, Point address) {
		this.tradingName = tradingName;
		this.ownerName = ownerName;
		this.document = document;
		this.coverageArea = coverageArea;
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public MultiPolygon getCoverageArea() {
		return coverageArea;
	}

	public void setCoverageArea(MultiPolygon coverageArea) {
		this.coverageArea = coverageArea;
	}

	public Point getAddress() {
		return address;
	}

	public void setAddress(Point address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Pdv [id=" + id + ", tradingName=" + tradingName + ", ownerName=" + ownerName + ", document=" + document
				+ ", coverageArea=" + coverageArea + ", address=" + address + "]";
	}
}
