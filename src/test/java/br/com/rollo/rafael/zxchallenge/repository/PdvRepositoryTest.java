package br.com.rollo.rafael.zxchallenge.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.vividsolutions.jts.geom.Point;

import br.com.rollo.rafael.zxchallenge.builder.PdvDtoBuilder;
import br.com.rollo.rafael.zxchallenge.model.Pdv;
import br.com.rollo.rafael.zxchallenge.util.GeometryUtils;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@ActiveProfiles("test")
public class PdvRepositoryTest {

	private static final String PERIMETER = "MULTIPOLYGON ((("
			+ "10 20, 30 0, 70 10, 100 10, 110 30, 100 50, "
			+ "110 70, 60 80, 30 90, 10 60, 30 40, 10 20)))";

	private Pdv pdv1 = PdvDtoBuilder.newPdv()
			.withTradingName("PDV 1")
			.withDocument("11.111.111/0001-11")
			.withCoverageArea(PERIMETER)
			.withAddress("POINT (70 40)")
			.buildAsPdv();
		
	private Pdv pdv2 = PdvDtoBuilder.newPdv()
			.withTradingName("PDV 2")
			.withDocument("22.222.222/0001-22")
			.withCoverageArea(PERIMETER)
			.withAddress("POINT (50 50)")
			.buildAsPdv();
	
	@Autowired
	private TestEntityManager manager;
	
	@Autowired
	private PdvRepository repository;
	
	@Test
	public void shouldReturnAvailablePDVsWithPdv1AsNearest() throws Exception {
	
		manager.persist(pdv1);
		manager.persist(pdv2);
		
		Point point = (Point) GeometryUtils.wktToGeometry("POINT (70 20)");
		List<Pdv> availablePDVs = repository.findAvailablePdvsSortedByDistance(point);
		
		assertThat(availablePDVs.size()).isEqualTo(2);
		assertThat(availablePDVs).containsSequence(pdv1, pdv2);
	}
	
	@Test
	public void shouldReturnAvailablePDVsWithPdv2AsNearest() throws Exception {
	
		manager.persist(pdv1);
		manager.persist(pdv2);
		
		Point point = (Point) GeometryUtils.wktToGeometry("POINT (30 20)");
		List<Pdv> availablePDVs = repository.findAvailablePdvsSortedByDistance(point);
		
		assertThat(availablePDVs.size()).isEqualTo(2);
		assertThat(availablePDVs).containsSequence(pdv2, pdv1);
	}
	
	@Test
	public void shouldReturnEmptyList() throws Exception {
	
		manager.persist(pdv1);
		manager.persist(pdv2);
		
		Point point = (Point) GeometryUtils.wktToGeometry("POINT (150 150)");
		List<Pdv> availablePDVs = repository.findAvailablePdvsSortedByDistance(point);
		
		assertThat(availablePDVs).isEmpty();
	}
	
	@Test
	public void shouldReturnPdv1ByDoument() {
		manager.persist(pdv1);
		manager.persist(pdv2);
		
		Optional<Pdv> possiblePDV = repository.findByDocument("11.111.111/0001-11");
		assertThat(possiblePDV).isNotEmpty();
		assertThat(possiblePDV).contains(pdv1);
	}
	
	@Test
	public void shouldReturnPdv2ById() {
		manager.persist(pdv1);
		Pdv persistedPdv = manager.persist(pdv2);
		
		Optional<Pdv> possiblePDV = repository.findById(persistedPdv.getId());
		assertThat(possiblePDV).isNotEmpty();
		assertThat(possiblePDV).contains(pdv2);
	}
	
	@Test
	public void shouldntReturnAnyPdvByDocument() {
		manager.persist(pdv1);
		manager.persist(pdv2);
		
		Optional<Pdv> possiblePDV = repository.findByDocument("00.000.000/0001-00");
		assertThat(possiblePDV).isEmpty();
	}
}
