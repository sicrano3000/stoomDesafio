package br.com.stoom.desafio.modelo.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.stoom.desafio.modelo.EnderecoEntity;

@JsonInclude(Include.NON_NULL)
public class EnderecoRequestDTO {
	
	@NotEmpty
	private String rua;
	
	@DecimalMin(value = "1")
	private Integer numero;
	
	private String complemento;
	
	@NotEmpty
	private String bairro;
	
	@NotEmpty
	private String cidade;
	
	@NotEmpty
	private String estado;
	
	@NotEmpty
	private String pais;
	
	@NotEmpty
	private String cep;
	
	private String latitude;
	private String longitude;

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
	
	public EnderecoRequestDTO() {
	}
	
	public EnderecoRequestDTO(EnderecoEntity endereco) {
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
	
	public EnderecoEntity toEntity() {		
		return new EnderecoEntity(this.rua, this.numero, this.complemento, this.bairro, this.cidade, 
				this.estado, this.pais, this.cep, this.latitude, this.longitude);
	}
	
	public EnderecoEntity toEntity(EnderecoEntity endereco, Long id) {		
		EnderecoEntity novoEndereco = new EnderecoEntity();
		
		novoEndereco.setId(id);
		novoEndereco.setRua(this.rua == null ? endereco.getRua() : this.rua);
		novoEndereco.setNumero(this.numero == null ? endereco.getNumero() : this.numero);
		novoEndereco.setComplemento(this.complemento == null ? endereco.getComplemento() : this.complemento);
		novoEndereco.setBairro(this.bairro == null ? endereco.getBairro() : this.bairro);
		novoEndereco.setCidade(this.cidade == null ? endereco.getCidade() : this.cidade);
		novoEndereco.setEstado(this.estado == null ? endereco.getEstado() : this.estado);
		novoEndereco.setPais(this.pais == null ? endereco.getPais() : this.pais);
		novoEndereco.setCep(this.cep == null ? endereco.getCep() : this.cep);
		novoEndereco.setLatitude(this.latitude == null ? endereco.getLatitude() : this.latitude);
		novoEndereco.setLongitude(this.longitude == null ? endereco.getLongitude() : this.longitude);
		
		return novoEndereco;
	}
	
}
