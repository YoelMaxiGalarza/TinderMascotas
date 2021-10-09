package egg.tinderMascotas.Controladores;

import java.util.logging.Level;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egg.tinderMascotas.Errores.ErrorServicio;
import egg.tinderMascotas.Servicios.ServiciosUsuario;

@Controller
@RequestMapping("/")
public class PortalControlador {

	@Autowired
	private ServiciosUsuario serviciosUsuario;

	@GetMapping("/")
	public String inicio() {
		return "index.html";
	}

	@GetMapping("/register")
	public String register() {
		return "registro.html";
	}

	@GetMapping("/login")
	public String login() {
		return "login.html";
	}

	@PostMapping("/register")
	public String registrar(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail,
			@RequestParam String clave1, @RequestParam String clave2) {

		try {
			serviciosUsuario.registrar(null, nombre, apellido, mail, clave1);
		} catch (ErrorServicio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "registro.html";
		}

		return "index.html";
	}

}
