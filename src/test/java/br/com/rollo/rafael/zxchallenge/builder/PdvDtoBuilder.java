package br.com.rollo.rafael.zxchallenge.builder;

import java.io.InputStream;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.rollo.rafael.zxchallenge.controller.dto.PdvDto;
import br.com.rollo.rafael.zxchallenge.util.JsonUtils;

public class PdvDtoBuilder {
	
	private PdvDto pdvDto;
	
	public PdvDtoBuilder(PdvDto pdvDto) {
		this.pdvDto = pdvDto;
	}

	public static PdvDtoBuilder newPdv() {
		PdvDto pdvDto = new PdvDto();
		return new PdvDtoBuilder(pdvDto);
	}
	
	public PdvDtoBuilder withTradingName(String tradingName) {
		this.pdvDto.setTradingName(tradingName);
		return this;
	}
	
	public PdvDto build() {
		return this.pdvDto;
	}

	public PdvDto fromValidJson() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JtsModule());
		
		InputStream jsonInputStream = this.getClass().getResourceAsStream("/valid-pdv.json");
		return mapper.readValue(jsonInputStream, PdvDto.class);
	}

	public static PdvDto buildFromValidJson() {
		return new JsonUtils<>(PdvDto.class).readFrom("valid-pdv");
	}

	public static PdvDto buildFromInvalidJson() {
		return new JsonUtils<>(PdvDto.class).readFrom("invalid-pdv");
	}

}
