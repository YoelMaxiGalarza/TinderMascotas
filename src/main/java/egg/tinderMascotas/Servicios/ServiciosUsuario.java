package egg.tinderMascotas.Servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egg.tinderMascotas.Entidades.Foto;
import egg.tinderMascotas.Entidades.Usuario;
import egg.tinderMascotas.Errores.ErrorServicio;
import egg.tinderMascotas.repositorios.UsuarioRepositorio;

@Service
public class ServiciosUsuario implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
//	@Autowired
//	private ServicioNotificacion servicioNotificacion;

	@Autowired
	private ServiciosFoto serviciosFoto;
	
	@Transactional
	public void registrar(MultipartFile archivo, String nombre, String apellido, String mail, String clave)
			throws ErrorServicio {

		validar(nombre, apellido, mail, clave);

		Usuario user = new Usuario();
		user.setNombre(nombre);
		user.setApellido(apellido);
		user.setMail(mail);

		String encriptada = new BCryptPasswordEncoder().encode(clave);
		user.setClave(encriptada);

		user.setAlta(new Date());

		Foto foto = serviciosFoto.guardar(archivo);
		user.setFoto(foto);

		usuarioRepositorio.save(user);
		//Notificamos al usuario con el java mail sender enviandole el mensaje con el titulo tinder de mascotas al mail del usuario
//		servicioNotificacion.enviar("Bienvenidos al Tinder de Mascotas", "Tinder de Mascotas", user.getMail());

	}
	@Transactional
	public void modificar(MultipartFile archivo, String id, String nombre, String apellido, String mail, String clave)
			throws ErrorServicio {

		validar(nombre, apellido, mail, clave);

		Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
		if (respuesta.isPresent()) {
			Usuario user = usuarioRepositorio.findById(id).get();
			user.setApellido(apellido);
			user.setNombre(nombre);
			user.setMail(mail);

			String encriptada = new BCryptPasswordEncoder().encode(clave);
			user.setClave(encriptada);

			String idFoto = null;

			if (user.getFoto() != null) {
				idFoto = user.getFoto().getId();
			}

			Foto foto = serviciosFoto.actualizar(idFoto, archivo);
			user.setFoto(foto);

			usuarioRepositorio.save(user);
		} else {
			throw new ErrorServicio("No se encontro el usuario solicitado");
		}
	}
	@Transactional
	public void deshabilitar(String id) throws ErrorServicio {
		Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
		if (respuesta.isPresent()) {
			Usuario user = respuesta.get();
			user.setBaja(null);
			usuarioRepositorio.save(user);
		} else {
			throw new ErrorServicio("No se encontro el usuario solicitado");
		}
	}
	@Transactional
	public void habilitar(String id) throws ErrorServicio {
		Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
		if (respuesta.isPresent()) {
			Usuario user = respuesta.get();
			user.setBaja(new Date());
			usuarioRepositorio.save(user);
		} else {
			throw new ErrorServicio("No se encontro el usuario solicitado");
		}
	}

	private void validar(String nombre, String apellido, String mail, String clave) throws ErrorServicio {

		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("El nombre no puede ser nulo.");
		}

		if (apellido == null || apellido.isEmpty()) {
			throw new ErrorServicio("El apellido no puede ser nulo.");
		}

		if (mail == null || mail.isEmpty()) {
			throw new ErrorServicio("El mail no puede ser nulo.");
		}

		if (clave == null || clave.isEmpty() || clave.length() <= 6) {
			throw new ErrorServicio("La clave no puede ser nula y tiene que tener mas de 6 digitos.");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
		if (usuario != null) {
			List<GrantedAuthority> permisos = new ArrayList<GrantedAuthority>();
			GrantedAuthority p1 = new SimpleGrantedAuthority("MODULO FOTOS");
			permisos.add(p1);
			GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO MASCOTAS");
			permisos.add(p2);
			GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO VOTOS");
			permisos.add(p3);

			User user = new User(usuario.getMail(), usuario.getClave(), permisos);

			return user;
		} else {
			return null;
		}

	}
}
