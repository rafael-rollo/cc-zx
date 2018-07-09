package br.com.rollo.rafael.zxchallenge.util;

import java.io.IOException;
import java.io.InputStream;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils<T> {
	
	private Class<T> genericType;

	public JsonUtils(Class<T> genericType) {
		this.genericType = genericType;
	}
	
	public T readFrom(String fileBaseName) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JtsModule());
	
		String filePath = "/" + fileBaseName + ".json";
		InputStream jsonInputStream = this.getClass().getResourceAsStream(filePath);		

		try {
			return mapper.readValue(jsonInputStream, this.genericType);
		} catch (IOException e) {
			throw new RuntimeException("Não foi possível ler json", e);
		}
	}
	
	public String parseToJson(T t) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JtsModule());
		
		try {
			return mapper.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Não foi possível converter para json", e);
		}
	}
}
