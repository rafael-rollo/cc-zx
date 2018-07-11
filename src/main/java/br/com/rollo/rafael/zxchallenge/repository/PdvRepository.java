package br.com.rollo.rafael.zxchallenge.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.vividsolutions.jts.geom.Point;

import br.com.rollo.rafael.zxchallenge.model.Pdv;

@org.springframework.stereotype.Repository
public interface PdvRepository extends Repository<Pdv, Integer>{

	Pdv save(Pdv pdv);
	
	Optional<Pdv> findByDocument(String cnpj);

	Optional<Pdv> findById(Integer id);

	@Query("select p from Pdv p "
			+ "where within(:point, p.coverageArea) = true or touches(:point, p.coverageArea) = true "
			+ "order by distance(:point, p.address)")
	List<Pdv> findAvailablePdvsSortedByDistance(@Param("point") Point point);
}
