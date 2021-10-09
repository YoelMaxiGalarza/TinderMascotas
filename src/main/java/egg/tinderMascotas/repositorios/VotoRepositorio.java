package egg.tinderMascotas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import egg.tinderMascotas.Entidades.Voto;

@Repository
public interface VotoRepositorio extends JpaRepository<Voto, String>{
	
	@Query("SELECT c FROM Voto c WHERE c.mascotaOriginaVoto.id = :id ORDER BY c.fecha DESC")
	public Voto buscarVotosPropios(@Param("id") String id);
	
	@Query("SELECT c FROM Voto c WHERE c.mascotaRecibeVoto.id = :id ORDER BY c.fecha DESC")
	public Voto buscarVotosRecibidos(@Param("id") String id);
}
