package egg.tinderMascotas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import egg.tinderMascotas.Servicios.ServiciosUsuario;

@SpringBootApplication
public class TinderMascotasApplication {
	
	@Autowired
	private ServiciosUsuario serviciosUsuario;
	
	public static void main(String[] args) {
		SpringApplication.run(TinderMascotasApplication.class, args);
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(serviciosUsuario).passwordEncoder(new BCryptPasswordEncoder());
	}
}
