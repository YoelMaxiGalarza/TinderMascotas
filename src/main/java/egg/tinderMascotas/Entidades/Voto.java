package egg.tinderMascotas.Entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Voto {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@ManyToOne
	private Mascota mascotaOriginaVoto;

	@ManyToOne
	private Mascota mascotaRecibeVoto;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	@Temporal(TemporalType.TIMESTAMP)
	private Date respuesta;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Date respuesta) {
		this.respuesta = respuesta;
	}

	public Mascota getMascotaOriginaVoto() {
		return mascotaOriginaVoto;
	}

	public void setMascotaOriginaVoto(Mascota mascotaOriginaVoto) {
		this.mascotaOriginaVoto = mascotaOriginaVoto;
	}

	public Mascota getMascotaRecibeVoto() {
		return mascotaRecibeVoto;
	}

	public void setMascotaRecibeVoto(Mascota mascotaRecibeVoto) {
		this.mascotaRecibeVoto = mascotaRecibeVoto;
	}

}
