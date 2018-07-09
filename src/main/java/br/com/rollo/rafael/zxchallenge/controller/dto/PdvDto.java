package br.com.rollo.rafael.zxchallenge.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;

import br.com.rollo.rafael.zxchallenge.model.Pdv;
import br.com.rollo.rafael.zxchallenge.validation.UniqueCNPJ;

public class PdvDto {

	private int id;
	@NotBlank
	private String tradingName;
	@NotBlank
	private String ownerName;
	@UniqueCNPJ
	private String document;
	@NotNull
	private MultiPolygon coverageArea;
	@NotNull
	private Point address;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
		return "PdvDto [id=" + id + ", tradingName=" + tradingName + ", ownerName=" + ownerName + ", document="
				+ document + ", coverageArea=" + coverageArea + ", address=" + address + "]";
	}

	public Pdv toPdv() {
		return new Pdv(this.tradingName, this.ownerName, this.document, this.coverageArea, this.address);
	}
}