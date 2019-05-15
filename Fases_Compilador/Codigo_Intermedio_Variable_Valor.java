package Fases_Compilador;

public class Codigo_Intermedio_Variable_Valor 
{
	String NombreaVariable;
	int valor;
	public Codigo_Intermedio_Variable_Valor(String nombreaVariable, int valor) {
		super();
		this.NombreaVariable = nombreaVariable;
		this.valor = valor;
	}
	public String getNombreaVariable() {
		return NombreaVariable;
	}
	public void setNombreaVariable(String nombreaVariable) {
		NombreaVariable = nombreaVariable;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	
	
	
	
}
