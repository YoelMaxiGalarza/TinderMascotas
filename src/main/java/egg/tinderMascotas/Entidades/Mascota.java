package egg.tinderMascotas.Entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import egg.tinderMascotas.Enumeraciones.Sexo;

@Entity
public class Mascota {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String nombre;
	
	@ManyToOne
	private Usuario usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date alta;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date baja;
	
	@Enumerated(EnumType.STRING)
	private Sexo sexo;
	
	@OneToOne
	private Foto foto;
	
	public Mascota() {
		super();
	}
	public Mascota(String id, String nombre, Usuario usuario, Date alta, Date baja, Sexo sexo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.usuario = usuario;
		this.alta = alta;
		this.baja = baja;
		this.sexo = sexo;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getAlta() {
		return alta;
	}
	public void setAlta(Date alta) {
		this.alta = alta;
	}
	public Date getBaja() {
		return baja;
	}
	public void setBaja(Date baja) {
		this.baja = baja;
	}
	public Foto getFoto() {
		return foto;
	}
	public void setFoto(Foto foto) {
		this.foto = foto;
	}
	
	
}
