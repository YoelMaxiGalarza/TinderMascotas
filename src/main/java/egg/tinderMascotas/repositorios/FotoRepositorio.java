package egg.tinderMascotas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import egg.tinderMascotas.Entidades.Foto;

public interface FotoRepositorio extends JpaRepository<Foto, String>{
	
}
