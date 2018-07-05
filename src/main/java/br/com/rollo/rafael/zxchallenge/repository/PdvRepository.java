package br.com.rollo.rafael.zxchallenge.repository;


import java.util.Optional;

import org.springframework.data.repository.Repository;

import br.com.rollo.rafael.zxchallenge.model.Pdv;

@org.springframework.stereotype.Repository
public interface PdvRepository extends Repository<Pdv, Integer>{

	Pdv save(Pdv pdv);
	
	Optional<Pdv> findByDocument(String cnpj);
}
