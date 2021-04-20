package br.com.stoom.desafio.controller;

import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.stoom.desafio.DesafioStoomApplication;
import br.com.stoom.desafio.modelo.EnderecoEntity;
import br.com.stoom.desafio.modelo.dto.EnderecoRequestDTO;
import br.com.stoom.desafio.modelo.dto.EnderecoResponseDTO;
import br.com.stoom.desafio.modelo.maps.GeocodeResult;
import br.com.stoom.desafio.repository.EnderecoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DesafioStoomApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
public class EnderecoControllerTest {
	
	@InjectMocks
	private EnderecoController controller;
	
	@Mock
	private EnderecoRepository repository;
	
	@Mock
	private HttpHeaders httpHeaders;
	
	@Mock
	private UriComponentsBuilder uriBuilder;
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	EnderecoEntity endereco;
	
	EnderecoRequestDTO dto;
	
	List<EnderecoEntity> enderecos;
	
	public static String getJsonCadastroEndereco(EnderecoEntity endereco) {
        String jsonEndereco = 
        		"{  \n" +
        		"   \"rua\":\" " + endereco.getRua() + " \",\n"+
        		"   \"numero\":\" " + endereco.getNumero() + " \",\n"+
        		"   \"complemento\": \" " + endereco.getComplemento() + " \",\n"+
        		"   \"bairro\": \" " + endereco.getBairro() + " \",\n"+
        		"   \"cidade\": \" " + endereco.getCidade() + " \",\n"+
        		"   \"estado\": \" " + endereco.getEstado() + " \",\n"+
        		"   \"pais\": \" " + endereco.getEstado() + " \",\n"+
        		"   \"cep\": \" " + endereco.getCep() + " \",\n"+
        		"   \"latitude\": \" " + endereco.getLatitude() + " \",\n"+
        		"   \"longitude\": \" " + endereco.getLongitude() + " \"\n"+
                "}	";

        return jsonEndereco;
    }
	
	@Before
	public void getterAndSetterTest()  throws Exception {
		enderecos = new ArrayList<EnderecoEntity>();
		endereco = new EnderecoEntity();
		
		endereco.setId(1l);
		endereco.setRua("Rua Manoel Serrão");
		endereco.setNumero(561);
		endereco.setComplemento("casa 2");
		endereco.setBairro("Antonina");
		endereco.setCidade("São Gonçalo");
		endereco.setEstado("Rio de Janeiro");
		endereco.setPais("Brasil");
		endereco.setCep("24455-025");
		endereco.setLatitude("");
		endereco.setLongitude("");
		
		enderecos.add(endereco);
		
		dto = new EnderecoRequestDTO(endereco);
	}
	
	@Test
	public void listarTest() {
		when(repository.findAll()).thenReturn(enderecos);
		List<EnderecoResponseDTO> dto = controller.listar();
		
		Assert.assertNotNull(dto);
	}
	
	@Test
	public void listarPorIdTest() throws URISyntaxException {
		when(repository.findById(1l)).thenReturn(Optional.of(endereco));
		
		ResponseEntity<EnderecoResponseDTO> exchange = controller.listarPorId(1l);
	     
	    Assert.assertNotNull(exchange);
	    Assert.assertEquals(HttpStatus.OK, exchange.getStatusCode());
	}
	
	@Test
	public void cadastrarTest() throws URISyntaxException {
		repository.save(endereco);
		
		String planetaJson = getJsonCadastroEndereco(endereco);
		
		RequestEntity<String> entity = RequestEntity
		        .post(new URI("/endereco"))
		        .contentType(MediaType.APPLICATION_JSON)
		        .accept(MediaType.APPLICATION_JSON)
		        .body(planetaJson);
		
		ResponseEntity<EnderecoResponseDTO> exchange = restTemplate.exchange(entity, EnderecoResponseDTO.class);
		
		Assert.assertEquals(HttpStatus.CREATED, exchange.getStatusCode());
		Assert.assertNotNull(exchange);
	}
	
	@Test
	public void atualizarTest() throws URISyntaxException {
		when(repository.findById(1l)).thenReturn(Optional.of(endereco));
		
		endereco.setRua("Novo endereço");
		
		repository.save(endereco);
		
		String planetaJson = getJsonCadastroEndereco(endereco);
		
		RequestEntity<String> entity = RequestEntity
		        .post(new URI("/endereco"))
		        .contentType(MediaType.APPLICATION_JSON)
		        .accept(MediaType.APPLICATION_JSON)
		        .body(planetaJson);
		
		ResponseEntity<EnderecoResponseDTO> exchange = restTemplate.exchange(entity, EnderecoResponseDTO.class);
		
		Assert.assertEquals(HttpStatus.CREATED, exchange.getStatusCode());
		Assert.assertEquals(endereco.getRua(), "Novo endereço");		
		Assert.assertNotNull(exchange);
	}
	
	@Test
	public void deletarTest() {
		when(repository.findById(1l)).thenReturn(Optional.of(endereco));
		
		Assert.assertEquals(HttpStatus.OK, controller.deletar(endereco.getId()).getStatusCode());
	}
	
	@Test
	public void getLatLonTest() {
		GeocodeResult result = controller.getLatLon("Rua manoel serrão+561+antonina+são+gonçalo");
		
		Assert.assertEquals(HttpStatus.OK.name(), result.getStatus());
		Assert.assertNotNull(result);
		Assert.assertEquals(endereco.getCep(), result.getResults().get(0).getAddressComponents().get(6).getLongName());
	}

}
