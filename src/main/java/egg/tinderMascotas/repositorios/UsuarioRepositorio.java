package egg.tinderMascotas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import egg.tinderMascotas.Entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{
	@Query("SELECT c FROM Usuario c WHERE c.mail = :mail")
	public Usuario buscarPorMail(String mail);
}
