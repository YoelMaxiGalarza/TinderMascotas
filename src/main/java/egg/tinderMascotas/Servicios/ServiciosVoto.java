package egg.tinderMascotas.Servicios;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egg.tinderMascotas.Entidades.Mascota;
import egg.tinderMascotas.Entidades.Voto;
import egg.tinderMascotas.Errores.ErrorServicio;
import egg.tinderMascotas.repositorios.MascotaRepositorio;
import egg.tinderMascotas.repositorios.UsuarioRepositorio;
import egg.tinderMascotas.repositorios.VotoRepositorio;

@Service
public class ServiciosVoto {

	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@Autowired
	MascotaRepositorio mascotaRepositorio;
	
//	@Autowired
//	private ServicioNotificacion servicioNotificacion;

	@Autowired
	VotoRepositorio votoRepositorio;

	/* METODO PARA AÑADIR VOTOS DE UNA MASCOTA A OTRA */
	public void votar(String idUser, String idMascotaQueVota, String idMascotaQueRecibe) throws ErrorServicio {

		/* CREAMOS UN NUEVO VOTO Y LE SETEAMOS LA FECHA */
		Voto voto = new Voto();
		voto.setFecha(new Date());

		/*
		 * CORROBORAMOS QUE NO SEA LA MISMA MASCOTA PARA QUE NO SE PUEDA VOTAR A SI
		 * MISMA
		 */
		if (idMascotaQueVota.equals(idMascotaQueRecibe)) {
			throw new ErrorServicio("No se puede votar a si mismo");
		}
		/*----------------------------------------------------------------------------------*/

		/* BUSCAMOS LA MASCOTA QUE VA A VOTAR */
		Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascotaQueVota);

		/*
		 * EL ID DEL USUARIO DE LA MASCOTA QUE VOTA ES IGUAL AL ID DEL USUARIO QUE SE
		 * PROVEE ENTONCES SE GUARDA EN EL VOTO LA MASCOTA QUE VOTA
		 */
		if (respuesta.isPresent()) {

			Mascota mascotaQueVota = respuesta.get();

			if (mascotaQueVota.getUsuario().getId().equals(idUser)) {
				voto.setMascotaOriginaVoto(mascotaQueVota);
			} else {
				throw new ErrorServicio("No tiene permisos para realizar la operacion solicitada");
			}

		} else {
			/*
			 * SI NO SE ENCUENTRA LA MASCOTA SE GENERA UNA EXCEPCION
			 */
			throw new ErrorServicio("No existe una mascota vinculada a este ID");
		}
		/*---------------------------------------------------------------------------------------*/

		/* BUSCAMOS LA MASCOTA QUE VA A RECIBIR EL VOTO */
		Optional<Mascota> respuesta2 = mascotaRepositorio.findById(idMascotaQueRecibe);

		/*
		 * SI SE ENCUENTRA LA MASCOTA QUE RECIBE, SE MATERIALIZA Y SE SETEA AL VOTO LA
		 * MASCOTA QUE RECIBE EL VOTO SINO SE GENERA UNA EXCEPCION
		 */
		if (respuesta2.isPresent()) {

			Mascota mascotaQueRecibe = respuesta2.get();

			voto.setMascotaRecibeVoto(mascotaQueRecibe);
			
//			servicioNotificacion.enviar("Tu mascota ha sido votada", "Tinder De Mascotas", mascotaQueRecibe.getUsuario().getMail());

		} else {
			throw new ErrorServicio("No existe una mascota vinculada a este ID");
		}
		/* AL FINAL DE TODO GUARDAMOS EL VOTO EN LA BASE DE DATOS */
		votoRepositorio.save(voto);
	}
	/*-----------------------------------------------------------------------------------------------*/

	/* METODO PARA RESPONDER UN VOTO */
	public void responder(String idUser, String idVoto) throws ErrorServicio {

		/* BUSCAMOS EL VOTO POR EL ID PROVEIDO */
		Optional<Voto> respuesta = votoRepositorio.findById(idVoto);
		/*
		 * SI SE ENCUENTRA SE MATERIALIZA EL VOTO Y SE LE SETEA LA FECHA DE LA RESPUESTA
		 */
		if (respuesta.isPresent()) {

			Voto voto = respuesta.get();

			voto.setRespuesta(new Date());
			/*
			 * SI EL ID DEL USUARIO DE LA MASCOTA QUE RECIBE EL VOTO ES IGUAL AL PARAMETRO
			 * INGRESADO EN EL METODO SE GUARDA EL VOTO
			 */
			if (voto.getMascotaRecibeVoto().getUsuario().getId().equals(idUser)) {
				
//				servicioNotificacion.enviar("Tu voto fue devuelto", "Tinder De Mascotas", voto.getMascotaOriginaVoto().getUsuario().getMail());
				
				votoRepositorio.save(voto);

			} else {
				/* SINO SE GENERA UNA EXCEPCION */
				throw new ErrorServicio("No tiene permisos para realizar la operacion solicitada");
			}

		} else {
			/* SI NO SE ENCUENTRA EL VOTO SE GENERA UNA EXCEPCION */
			throw new ErrorServicio("No existe el voto solicitado.");
		}

	}

}
