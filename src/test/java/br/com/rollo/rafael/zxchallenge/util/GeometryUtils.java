package br.com.rollo.rafael.zxchallenge.util;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class GeometryUtils {

	public static Geometry wktToGeometry(String wellKnownText) {
		try {
			return new WKTReader().read(wellKnownText);
		} catch (ParseException e) {
			throw new RuntimeException("Não foi possível converter WKT para o tipo geométrico", e);
		}
	}
}
