package egg.tinderMascotas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import egg.tinderMascotas.Entidades.Zona;

@Repository
public interface ZonaRepositorio extends JpaRepository<Zona, String>{
	
}
