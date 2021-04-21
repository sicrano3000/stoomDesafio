package br.com.stoom.desafio.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.stoom.desafio.modelo.EnderecoEntity;
import br.com.stoom.desafio.modelo.dto.EnderecoRequestDTO;
import br.com.stoom.desafio.modelo.dto.EnderecoResponseDTO;
import br.com.stoom.desafio.modelo.maps.GeocodeResult;
import br.com.stoom.desafio.repository.EnderecoRepository;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
	
	@Autowired
	private EnderecoRepository repository;
	
	@GetMapping
	public List<EnderecoResponseDTO> listar() {
		List<EnderecoEntity> endereco = repository.findAll();
		
		endereco.forEach(e -> {
			GeocodeResult geo = new GeocodeResult();
			
			if (e.getLatitude() == null && e.getLongitude() == null) {
				geo = getLatLon(e.toString());
				if (geo != null) {
					e.setLatitude(geo.getResults().get(0).getGeometry().getGeocodeLocation().getLatitude());
					e.setLongitude(geo.getResults().get(0).getGeometry().getGeocodeLocation().getLongitude());
				}
			}
		});
		
		return EnderecoResponseDTO.toListDTO(endereco);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EnderecoResponseDTO> listarPorId(@PathVariable Long id) {
		Optional<EnderecoEntity> optional = repository.findById(id);
		
		GeocodeResult geo = new GeocodeResult();
		
		if (optional.get().getLatitude() == null && optional.get().getLongitude() == null) {
			geo = getLatLon(optional.get().toString());
			if (geo != null) {
				optional.get().setLatitude(geo.getResults().get(0).getGeometry().getGeocodeLocation().getLatitude());
				optional.get().setLongitude(geo.getResults().get(0).getGeometry().getGeocodeLocation().getLongitude());
			}
		}
		
		if (optional.isPresent())
			return ResponseEntity.ok(new EnderecoResponseDTO(optional.get()));
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<EnderecoResponseDTO> cadastrar(@RequestBody @Valid EnderecoRequestDTO dto, UriComponentsBuilder uriBuilder) {
		EnderecoEntity endereco = dto.toEntity();
		
		repository.save(endereco);
		
		URI uri = uriBuilder.path("/endereco/{id}").buildAndExpand(endereco.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new EnderecoResponseDTO(endereco));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<EnderecoResponseDTO> Atualiar(@PathVariable Long id, @RequestBody EnderecoRequestDTO dto) {
		Optional<EnderecoEntity> optional = repository.findById(id);
		
		if (optional.isPresent()) {
			EnderecoEntity endereco = dto.toEntity(optional.get(), id);
			repository.save(endereco);
			return ResponseEntity.ok(new EnderecoResponseDTO(endereco));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		Optional<EnderecoEntity> optional = repository.findById(id);
		
		if (optional.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
    public GeocodeResult getLatLon(@PathVariable String endereco) {
		RestTemplate template = new RestTemplate();
		
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("maps.googleapis.com")
				.path("maps/api/geocode/json")
				.queryParam("address", endereco)
				.queryParam("key", "AIzaSyCj0cY2yEvVfYhAaTz3-P2MW-YRKmhz5Uw")
				.build();
		
		ResponseEntity<GeocodeResult> retorno = template.getForEntity(uri.toUriString(), GeocodeResult.class);
		
		return retorno.getBody();
    }

}
