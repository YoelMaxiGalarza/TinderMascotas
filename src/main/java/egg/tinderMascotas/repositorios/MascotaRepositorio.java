package egg.tinderMascotas.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import egg.tinderMascotas.Entidades.Mascota;

@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota, String>{
	@Query("SELECT c FROM Mascota c WHERE c.usuario.id = :id")
	public List<Mascota> buscarMascotasPorUusarui(@Param("id") String id);
}
