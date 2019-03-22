package Fases_Compilador;


public class TablaSimbolos {

	String nombre;
	String tipo;
	String valor;
	int posicion;
	
	
	public TablaSimbolos(String nombre, String tipo, String valor, int posicion) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.valor = valor;	
		this.posicion = posicion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public int getposicion() {
		return posicion;
	}
	public void setposicion(int posicion) {
		this.posicion = posicion;
	}


	
}