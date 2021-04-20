package br.com.stoom.desafio.modelo.maps;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressComponent {
	
	@JsonProperty("long_name")
	String longName;
	
	@JsonProperty("short_name")
	String shortName;
	
	List<String> types;

	public AddressComponent() {
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

}
