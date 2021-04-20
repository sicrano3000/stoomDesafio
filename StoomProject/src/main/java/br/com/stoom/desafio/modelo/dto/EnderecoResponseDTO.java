package br.com.stoom.desafio.modelo.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.stoom.desafio.modelo.EnderecoEntity;

public class EnderecoResponseDTO {

	private Long id;
	private String rua;
	private Integer numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String pais;
	private String cep;
	private String latitude;
	private String longitude;

	public Long getId() {
		return id;
	}

	public String getRua() {
		return rua;
	}

	public Integer getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public String getEstado() {
		return estado;
	}

	public String getPais() {
		return pais;
	}

	public String getCep() {
		return cep;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}
	
	public EnderecoResponseDTO() {
	}

	public EnderecoResponseDTO(EnderecoEntity endereco) {
		this.id = endereco.getId();
		this.rua = endereco.getRua();
		this.numero = endereco.getNumero();
		this.complemento = endereco.getComplemento();
		this.bairro = endereco.getBairro();
		this.cidade = endereco.getCidade();
		this.estado = endereco.getEstado();
		this.pais = endereco.getPais();
		this.cep = endereco.getCep();
		this.latitude = endereco.getLatitude();
		this.longitude = endereco.getLongitude();
	}
	
	public static List<EnderecoResponseDTO> toListDTO(List<EnderecoEntity> endereco) {
		return endereco.stream().map(EnderecoResponseDTO::new).collect(Collectors.toList());
	}

}
