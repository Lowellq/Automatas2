package CompiladorSematicoFinal;

import java.io.PrintStream;
import java.util.Hashtable;
import java.lang.String;
import java.util.ArrayList;

class TokenAsignaciones
{
	  //public static TablaSimbolos Datos[] =  new TablaSimbolos[100]; 
	
	  //Variable para validar asignaciones a caracteres(chr)
	  public static int segunda = 0;
	  //Tabla que almacenara los tokens declarados
	  private static Hashtable tabla = new Hashtable();
	  
	  //Listas que guardaran los tipos compatibles de las variables
	  private static ArrayList<Integer> intComp = new ArrayList();
	  private static ArrayList<Integer> decComp = new ArrayList();
	  private static ArrayList<Integer> strComp = new ArrayList();
	  private static ArrayList<Integer> chrComp = new ArrayList();
	  
	  //static ArrayList<TablaSimbolos> Datos = new ArrayList<TablaSimbolos>();
	  static TablaSimbolos Datos[] = new TablaSimbolos[100];
	  static int ContadorGlobal=0;
	  static int NoInsertar = 1;
	  
		public static void TablaSimbolos()
		{
			
			System.out.println("Ident \t Tipo \t  Valor \t Linea");
			for (int i =0; i<ContadorGlobal; i++) 
			{
				
				System.out.println(" "+Datos[i].getNombre()+"\t"+Datos[i].getTipo()+"\t"+Datos[i].getValor()+"\t\t"+Datos[i].getposicion());
			}
	
		}
		
		
												//variable		//tipoDato
	public static void InsertarSimbolo(Token identificador, int tipo)
	{
		NoInsertar = 1;
		boolean Validacion = false;
		int Linea = identificador.beginLine;
		String Nombre = identificador.image;
		
		String TipoValor=" ";
		if(tipo==44 || tipo==48 )
			TipoValor="Int";
		else if (tipo==45 || tipo==50)
			TipoValor="Double";
		else if (tipo==46)
			TipoValor="Char";
		else if (tipo==47 || tipo==51)
			TipoValor="String";		
		else 
			TipoValor="No estipulado";
		
		for(int i = 0; i<ContadorGlobal; i++)
		{
			if(Nombre.equals(Datos[i].getNombre()))
					{
					 if(TipoValor.equals(Datos[i].getTipo()))
					 {
						 
						 Validacion=true;
						 System.out.println("\t Ocurrio un error Semantico \n\t  -> El identificador = " +Nombre+" ya fue declarado en la linea \n\t  -> Linea: \" "+Datos[i].getposicion());
						 NoInsertar = 0;
					 }
					}
		}
		
		if(Validacion == false) 
		{	
		//System.out.println("Valor de : "+L);
		Datos[ContadorGlobal] = new TablaSimbolos(Nombre, TipoValor, "null", Linea);
		
		//System.out.println(L+" "+TipoValor +" 0 "+Linea);
		//Datos.add(new TablaSimbolos(L, TipoValor, "null",Linea));
		//En este metodo se agrega a la tabla de tokens el identificador que esta siendo declarado junto con su tipo de dato
		tabla.put(identificador.image, tipo);
		ContadorGlobal++;}
	 }


	
	public static void SetTables()
	{
		/*En este metodo se inicializan las tablas, las cuales almacenaran los tipo de datos compatibles con:		
		 entero = intComp
		 decimal = decComp
		 cadena = strComp
		 caracter = chrComp
		*/
		intComp.add(44); //int
		intComp.add(48); //numeros 0-9
		
		decComp.add(44); //int
		decComp.add(45); //dec
		decComp.add(48); //numero 0-9
		decComp.add(50); //decimal
		
		chrComp.add(46); //char
		chrComp.add(52); //caracteres
		
		strComp.add(47); //string 
		strComp.add(51); //cadena
	}
	
	
 
	public static String checkAsing(Token TokenIzq, Token TokenAsig, int tipo)
	{
		//System.out.println("Valor que se debe de asignar = "+TokenAsig+" ,a "+ TokenIzq);
		//variables en las cuales se almacenara el tipo de dato del identificador y de las asignaciones (ejemplo: n1(tipoIdent1) = 2(tipoIdent2) + 3(tipoIdent2))
		int tipoIdent1;
		int tipoIdent2;
		
		String Nombre = TokenIzq.image;
		String Valor = TokenAsig.image;
	
		/* De la tabla obtenemos el tipo de dato del identificador  
		asi como, si el token enviado es diferente a algun tipo que no se declara como los numeros(48), los decimales(50),
		caracteres(52) y cadenas(51)
		entonces tipoIdent1 = tipo_de_dato, ya que TokenAsig es un dato*/
		if(TokenIzq.kind != 48 && TokenIzq.kind != 50)		
		{
			try 
			{
//Si el TokenIzq.image existe dentro de la tabla de tokens, entonces tipoIdent1 toma el tipo de dato con el que TokenIzq.image fue declarado
				tipoIdent1 = (Integer)tabla.get(TokenIzq.image);
				
				
				//aqui va a ir la validacion se se quiere volver a declarar
			}
			catch(Exception e)
			{
//Si TokenIzq.image no se encuentra en la tabla en la cual se agregan los tokens, el token no ha sido declarado, y se manda un error
				
				return "\t Ocurrio un error Semantico \n\t  -> El identificador = " + TokenIzq.image + " No ha sido declarado \n\t  -> Linea: " + TokenIzq.beginLine;
			}
		}
		
		else 		
			tipoIdent1 = 0;
			
		//TokenAsig.kind != 48 && TokenAsig.kind != 50 && TokenAsig.kind != 51 && TokenAsig.kind != 52
		if(TokenAsig.kind == 49)	
		{
			/*Si el tipo de dato que se esta asignando, es algun identificador(kind == 49) 
			se obtiene su tipo de la tabla de tokens para poder hacer las comparaciones*/
			try
			{
				tipoIdent2 = (Integer)tabla.get(TokenAsig.image);
			}
			catch(Exception e)
			{
				//si el identificador no existe manda el error
				return "\t Ocurrio un error Semantico \n\t  -> El identificador = " + TokenIzq.image + " No ha sido declarado \n\t  -> Linea: " + TokenIzq.beginLine;
			}
		}
				//Si el dato es entero(48) o decimal(50) o caracter(51) o cadena(52)
				//tipoIdent2 = tipo_del_dato
		else if(TokenAsig.kind == 48 || TokenAsig.kind == 50 || TokenAsig.kind == 51 || TokenAsig.kind == 52)
			tipoIdent2 = TokenAsig.kind;
		else //Si no, se inicializa en algun valor "sin significado(con respecto a los tokens)", para que la variable este inicializada y no marque error al comparar
			tipoIdent2 = 0; 

			
	  
		
		if(tipoIdent1 == 44) //Int
		{
			boolean Validacion = false;
			//Si la lista de enteros(intComp) contiene el valor de tipoIdent2, entonces es compatible y se puede hacer la asignacion
			if(intComp.contains(tipoIdent2))
			{
				if(NoInsertar==1) 
				{	
					for(int i = 0 ; i<ContadorGlobal; i++)
					{
						if(Nombre.equals(Datos[i].getNombre()))
						{
							Datos[i].setValor(Valor);
						}
					}
				}
			
				return " ";
			}	
			else //Si el tipo de dato no es compatible manda el error
				return "\t Ocurrio un error Semantico \n\t  -> No se puede asignar el valor : " + TokenAsig.image + " a Entero \n\t  -> Linea: " + TokenIzq.beginLine;
		}
		else if(tipoIdent1 == 45) //double
		{
			if(decComp.contains(tipoIdent2))
			{
				
				for(int i = 0 ; i<ContadorGlobal; i++)
				{
					if(Nombre.equals(Datos[i].getNombre()))
					{
						Datos[i].setValor(Valor);
					}
				}

				return " ";
			}
			else
				return "\t Ocurrio un error Semantico \n\t  -> No se puede asignar el valor : " + TokenAsig.image + " a double \n\t  -> Linea: " + TokenIzq.beginLine;
		}
		else if(tipoIdent1 == 46) //char
		{
			/*variable segunda: cuenta cuantos datos se van a asignar al caracter: 
				si a el caracter se le asigna mas de un dato (ej: 'a' + 'b') marca error 
				NOTA: no se utiliza un booleano ya que entraria en asignaciones pares o impares*/
			segunda++;
			if(segunda < 2)
			{
				if(chrComp.contains(tipoIdent2))
				{
					for(int i = 0 ; i<ContadorGlobal; i++)
					{
						if(Nombre.equals(Datos[i].getNombre()))
						{
							Datos[i].setValor(Valor);
						}
					}
					return " ";	
				}	
				else
					return "\t Ocurrio un error Semantico \n\t  -> No se puede el valor : " + TokenAsig.image + " a Char \n\t  -> Linea: " + TokenIzq.beginLine;
			}
			else //Si se esta asignando mas de un caracter manda el error 			
				return "\t Ocurrio un error Semantico \n\t  -> No se puede declarar mas de un caracter a un Char : " + TokenAsig.image + "  \n\t  -> Linea: " + TokenIzq.beginLine;
			
		}
		else if(tipoIdent1 == 47) //string
		{
			if(strComp.contains(tipoIdent2))
			{
				
				for(int i = 0 ; i<ContadorGlobal; i++)
				{
					if(Nombre.equals(Datos[i].getNombre()))
					{
						Datos[i].setValor(Valor);
					}
				}
				
				return " ";
			}
			else
				return "\t Ocurrio un error Semantico \n\t  -> No se puede Asignar el valor : " + TokenAsig.image + " a cadena  \n\t  -> Linea: " + TokenIzq.beginLine;
		}
		else
		{
			return "El Identificador " + TokenIzq.image + " no ha sido declarado" + " Linea: " + TokenIzq.beginLine;
		}
	}	  
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	/*Metodo que verifica si un identificador ha sido declarado, 
		ej cuando se declaran las asignaciones: i++, i--)*/ 
	public static String checkVariable(Token checkTok)
	{
		try
		{
			//Intenta obtener el token a verificar(checkTok) de la tabla de los tokens
			int tipoIdent1 = (Integer)tabla.get(checkTok.image);
			return " ";
		}
		catch(Exception e)
		{
			//Si no lo puede obtener, manda el error
			return "Error: El identificador " + checkTok.image + " No ha sido declarado \r\nLinea: " + checkTok.beginLine;
		}
	}

 }
  
  
  
  
  
  
  