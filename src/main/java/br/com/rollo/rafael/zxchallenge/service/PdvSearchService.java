package br.com.rollo.rafael.zxchallenge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.Point;

import br.com.rollo.rafael.zxchallenge.model.Pdv;
import br.com.rollo.rafael.zxchallenge.repository.PdvRepository;

@Service
public class PdvSearchService {

	@Autowired
	private PdvRepository pdvRepository;
	
	public Optional<Pdv> findNearestPdvFrom(Point position) {
		
		List<Pdv> availablePdvs = pdvRepository.findAvailablePdvsSortedByDistance(position);
		
		if(availablePdvs.isEmpty())
			return Optional.empty();
		
		return Optional.of(availablePdvs.get(0));
	}

}
