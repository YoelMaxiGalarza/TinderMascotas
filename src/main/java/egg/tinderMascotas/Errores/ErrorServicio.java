package egg.tinderMascotas.Errores;

public class ErrorServicio extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6095588236869773717L;

	public ErrorServicio(String msn) {
		super(msn);
	}
}
