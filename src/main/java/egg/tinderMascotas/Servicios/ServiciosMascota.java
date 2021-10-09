package egg.tinderMascotas.Servicios;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egg.tinderMascotas.Entidades.Foto;
import egg.tinderMascotas.Entidades.Mascota;
import egg.tinderMascotas.Entidades.Usuario;
import egg.tinderMascotas.Enumeraciones.Sexo;
import egg.tinderMascotas.Errores.ErrorServicio;
import egg.tinderMascotas.repositorios.MascotaRepositorio;
import egg.tinderMascotas.repositorios.UsuarioRepositorio;

@Service
public class ServiciosMascota {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private MascotaRepositorio mascotaRepositorio;

	@Autowired
	private ServiciosFoto serviciosFoto;

	/*
	 * METODO PARA CREAR UNA MASCOTA Y AGREGARLA A LA BASE DE DATOS CON EL ID DEL
	 * USUARIO QUE LA CREA
	 */
	@Transactional
	public void agregarMascota(MultipartFile archivo, String idUser, String nombre, Sexo sexo) throws ErrorServicio {

		// BUSCAMOS AL USUARIO POR EL ID PROVEIDO
		Usuario usuario = usuarioRepositorio.findById(idUser).get();

		// VALIDAMOS NOMBRE Y SEXO DE LA MASCOTA
		validar(nombre, sexo);

		// CREAMOS UNA NUEVA MASCOTA
		Mascota mascota = new Mascota();

		// SETEAMOS TODOS LOS ATRIBUTOS CON LOS QUE VAMOS A CREARLA
		mascota.setNombre(nombre);
		mascota.setSexo(sexo);
		mascota.setUsuario(usuario);
		// Y LE DAMOS UNA FECHA DE ALTA
		mascota.setAlta(new Date());
		// CREAMOS UNA ENTIDAD FOTO PARA GUARDAR EL ARCHIVO MULTIPART FILE QUE SE VA A
		// INGRESAR
		Foto foto = serviciosFoto.guardar(archivo);
		// SETEAMOS LA FOTO A LA MASCOTA
		mascota.setFoto(foto);

		/* SE GUARDA LA MASCOTA YA MODIFICADA EN LA BASE DE DATOS */
		mascotaRepositorio.save(mascota);
	}

	/*
	 * METODO PARA MODIFICAR UNA MASCOTA SI EL USUARIO QUE LA QUIERE MODIFICAR TIENE
	 * EL MISMO ID QUE EL USUARIO DE LA MACOTA
	 */
	@Transactional
	public void modificar(MultipartFile archivo, String idUser, String idMascota, String nombre, Sexo sexo)
			throws ErrorServicio {

		// VALIDAMOS NOMBRE Y SEXO DE LA MASCOTA
		validar(nombre, sexo);

		// BUSCAMOS EN LA BASE DE DATOS CON EL ID DE MASCOTA PROVEIDO
		Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);

		// SI SE ENCUENTRA
		if (respuesta.isPresent()) {

			/* MATERIALIZAMOS LA MASCOTA ENCONTRADA CON EL ID */
			Mascota mascota = respuesta.get();

			/*
			 * SE VERIFICA SI EL ID DEL USUARIO QUE QUIERE MODIFICAR ES EL MISMO ID QUE EL
			 * USUARIO DE LA MASCOTA
			 */
			if (mascota.getUsuario().getId().equals(idUser)) {
				/* SI SE VERIFICA EL IF SE PROCEDE A MODIFICAR NOMBRE Y SEXO DE LA MASCOTA */
				mascota.setNombre(nombre);
				mascota.setSexo(sexo);

				// CREAMOS UNA VARIABLE idFoto Y LA INICIALIZAMOS PARA LUEGO SETEARLE EL ID DE
				// LA FOTO
				String idFoto = null;
				// VERIFICAMOS SI LA MASCOTA TIENE UNA FOTO Y SI LA TIENE ASIGNAMOS EL ID A LA
				// VARIABLE idFoto
				if (mascota.getFoto() != null) {
					idFoto = mascota.getFoto().getId();
				}
				// CREAMOS LA ENTIDAD FOTO Y ACTUALIZAMOS LA FOTO DE LA MASCOTA CON EL SERVICIO
				// DE FOTO Y LUEGO LA SETEAMOS A LA MASCOTA
				Foto foto = serviciosFoto.actualizar(idFoto, archivo);
				mascota.setFoto(foto);

				/* SE GUARDA LA MASCOTA YA MODIFICADA EN LA BASE DE DATOS */
				mascotaRepositorio.save(mascota);

			} else {
				// SI LOS ID DE USUARIOS NO SON LOS MISMOS DEVOLVEMOS UN ERROR
				throw new ErrorServicio("No tiene permisoss suficientes para realizar la operacion");
			}
		} else {
			// SI NO SE ENCUENTRA LA MASCOTA DEVOLVEMOS UN ERROR
			throw new ErrorServicio("No existe mascota con el id solicitado");
		}
	}

	/*
	 * METODO PARA ELIMINAR UNA MASCOTA SI EL USUARIO QUE LA QUIERE ELIMINAR TIENE
	 * EL MISMO ID QUE EL USUARIO DE LA MASCOTA
	 */
	@Transactional
	public void eliminar(String idUser, String idMascota) throws ErrorServicio {

		// BUSCAMOS EN LA BASE DE DATOS CON EL ID DE MASCOTA PROVEIDO
		Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);

		// SI SE ENCUENTRA
		if (respuesta.isPresent()) {

			/* MATERIALIZAMOS LA MASCOTA ENCONTRADA CON EL ID */
			Mascota mascota = respuesta.get();

			/* SI EL USUARIO TIENE EL MISMO ID QUE EL USUARIO DE LA MASCOTA */
			if (mascota.getUsuario().getId().equals(idUser)) {

				// SETEAMOS UNA FECHA DE BAJA
				mascota.setBaja(new Date());
				// GUARDAMOS LA MASCOTA EN LA BASE DE DATOS
				mascotaRepositorio.save(mascota);
			}
		}
	}

	/* METODO PARA VALIDAR NOMBRE Y SEXO DE LA MASCOTA */
	public void validar(String nombre, Sexo sexo) throws ErrorServicio {
		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("El nombre no puede ser nulo o vacio.");
		}
		if (sexo == null) {
			throw new ErrorServicio("El sexo no puede ser nulo.");
		}
	}

}
