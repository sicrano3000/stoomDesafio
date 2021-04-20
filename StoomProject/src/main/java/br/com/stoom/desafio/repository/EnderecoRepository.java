package br.com.stoom.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.stoom.desafio.modelo.EnderecoEntity;

public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long> {

}
