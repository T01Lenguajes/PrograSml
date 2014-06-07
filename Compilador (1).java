package com.app.jsps.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 * Servlet implementation class Compilador
 */
public class Compilador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 public NodosCompilador PrimerNodo;
	  public NodosCompilador UltimoNodo;
	  Object[] arreglo_lineas;
	 
	  Object Nombre;
    /**
     * Default constructor. 
     */
    public Compilador() {
        // TODO Auto-generated constructor stub
    }
    
    
    
	 public boolean VaciaLista () {
       return PrimerNodo == null;
    }
	
        
     
	// Imprime el contenido de la lista
	 public void Imprimir()
	 { 
   if (VaciaLista())
	   {
	     System.out.println( "vacia" +Nombre);
	   }
	   //fin del primer if
	  else
	  {
		 NodosCompilador Actual = PrimerNodo;
		
	     while (Actual!= null)
		 {
	      System.out.print("Indentificador: "+ Actual.Identificador +" *** "+"Tipo de la variable: " +Actual.Tipo +" *** " + "Valor: "+ Actual.Valor + "\n");
	      Actual=Actual.siguiente;
	     }
	
	  }
	}
		
	 public Compilador (Object s)
	{ 
    Nombre = s;
	    PrimerNodo = UltimoNodo =null;
	}
	
	

	//Inserta al Final de la Lista
	//Si la lista se encuentra vac�a, el PrimerNodo y el UltimoNodo se refieren al nuevo nodo. 
//Si no, la variable de siguiente de UltimoNodo se refiere al nuevo nodo.
	
public void InsertaFinal(Object Identificador, Object tipo, Object valor)
	{ 
 if ( VaciaLista())
	   PrimerNodo = UltimoNodo = new NodosCompilador(Identificador, tipo, valor);
	 else
	   UltimoNodo=UltimoNodo.siguiente = new NodosCompilador(Identificador, tipo, valor);
	}
 
 //***************************************************************************************************************************
//este metodo verifica si corresponde a un val, let, etc.
     
 public void evaluar_expresion(Object [] Arreglo, int i) 
{
		
		int contador = 0;
		
		if (Arreglo[0].toString().startsWith("(")){ //si el arreglo inicia con "(" es porque corresponde a una tupla
			
			evaluar_tupla("Expresion no asociada",Arreglo, i,0);
		}
		
		if(i==1){
			
		   determinar_valor("Expresion no asociada", Arreglo[contador].toString()); //si el i es 1 es porque es una expresion como 5, true, [], etc.
		}
		
		
		if (i==3){
		
			determinar_valor(Arreglo[contador].toString(), Arreglo[2].toString()); //si el contador es 3, es porque la expresion es como x = 2		
			
		}
		
		if (!(Arreglo[contador].toString().equals("let")) && (!Arreglo[contador].toString().equals("val"))&& (!Arreglo[contador].toString().equals("if")) && (!Arreglo[contador].toString().equals("("))&& i>3) {
			
			ListaSimple lista=new ListaSimple();
			double resultado;
			
			for(int x=2;x<i;x++)
				lista.InsertaFinal(Arreglo[x].toString());
	         
			resultado=lista.ListaPostFijo(lista); //este metodo lo que hace es evaluar una expresion matematica, convirtiendola primero a una expresion postfijo
			Arreglo[2]=(int)resultado;
			Integer.toString( (Integer) Arreglo[2]);			
			determinar_valor(Arreglo[contador].toString(), Arreglo[2].toString());	
                     	
		}
	
		if (Arreglo[contador].toString().equals("let")) { //si es un let entonces llama al método Let_it_be
                 
                   Let_it_be(Arreglo,i);              
		}
		
		if (Arreglo[contador].toString().equals("if")) { //si inicia con un if entonces se manda a evaluar_if
			
         evaluar_if(Arreglo,i,"Expresión no asociada");
     }
			
		if (Arreglo[contador].toString().equals("val") && i==4) { //si es un val y el arreglo tiene 4 elementos es porque es una expresion como val x = 4
			
			determinar_valor(Arreglo[1].toString(), Arreglo[3].toString());	
                     
		}
		
		if (Arreglo[contador].toString().equals("val") && i>4) { //de lo contrario, es porque es una expresion mas compleja, por ejemplo val x = 2 + 5
			
			if (Arreglo[3].toString().startsWith("(") && Arreglo[i-1].toString().endsWith(")")){
				
				evaluar_tupla(Arreglo[1].toString(),Arreglo, i,3); //se evalua una tupla
			}
			
			else{ //se evalua una expresion matematica
			
				ListaSimple lista=new ListaSimple();
				double resultado;
				
				for(int x=3;x<i;x++)
					lista.InsertaFinal(Arreglo[x].toString());
		         
				resultado=lista.ListaPostFijo(lista); //este metodo lo que hace es evaluar una expresion matematica, convirtiendola primero a una expresion postfijo
				Arreglo[3]=(int)resultado;
				determinar_valor(Arreglo[1].toString(), Arreglo[3].toString());
			}
		}
		
		else{
			//System.out.println("");
		}	
	}
//***************************************************************************************************************************
//Este metodo es el que se encarga de resolver las expresiones let
 
 public void Let_it_be(Object[] Arreglo, int i) {
   
   int contador = 1 ; //se inicia en 1 puesto que ahi empieza la expresion que se va a evaluar
                     
   while(contador!=i){
   	
   	if((Arreglo[contador].equals("end"))){
   		
   		break;
   	}
   	
   	else{
   		
       int cero=0;
 	  Object nuevo []=new Object[100]; //arreglo donde se almacenara temporalmente las expresiones entre let e in, e in y end
    
       if (Arreglo[contador].toString().equals("val") ){ //
                               
				while(!(Arreglo[contador].toString().equals("in"))){  //se agrega la expresion que esta entre let e in en el arreglo
					
					if ((Arreglo[contador].toString().equals("end"))){ //si encuentra un end se detiene
						
						break;}
					
					else{
					 
						nuevo[cero] = Arreglo[contador]; 
						contador++;
						cero++;
                }
				}
                    
           }
				contador=contador+1;
           evaluar_expresion(nuevo, cero); //al finalizar el ciclo se manda a evaluar la expresion
       }
   }
      
   }
//Fin de let it be

//*********************************************************************************************************************************	
//este metodo determina el tipo de la expresion, es decir, si es un boolean, un int, un string...
 
 public void determinar_valor(String identificador, String valor){
	    	
	    	String tipo;
	    	
	    	if(valor.equals("true")|| valor.equals("false")){
				
				tipo="bool";
				this.InsertaFinal(identificador, tipo, valor.toString());
				
				
			}
			//si esta funcion retorna un true es porque corresponde a un numero, de lo contrario pasa a las siguientes instrucciones
			if (Es_Numero(valor)==true){
				
				tipo="int";
				this.InsertaFinal(identificador, tipo, valor.toString());
			}
			
			//esta instruccion es para saber si es un string
			
			if(valor.toString().startsWith("'") && valor.toString().endsWith("'")){ //se verifica si es un string
				
				tipo="string";
				this.InsertaFinal(identificador, tipo, valor.toString());
			}
			
			//esta instruccion es para saber si es una lista
			
			if (valor.startsWith("[") && valor.endsWith("]")){
				
				if(!(valor.contains(","))){
					
					tipo="bool list";
					this.InsertaFinal(identificador, tipo, valor.toString());
				}
				else{
					tipos_lista(valor,identificador);}
			}	
			if((valor.startsWith("(")) && (valor.endsWith(")"))){ //cuando encuentra esto en el nodo, pasa al siguiente
				
				if(!(valor.contains(","))){
					
					tipo="unit";
					this.InsertaFinal(identificador, tipo, valor.toString());}
					
			}
				
	    }
	    
	//*************************************************************************************************************************
	//esta funcion ingresa los datos de una tupla en una lista para evaluar el tipo de cada dato en la lista
		      
	public String evaluar_tupla(String identificador,Object Arreglo[], int contador, int i){
			
		ListaSimple lista=new ListaSimple(); //se crea la lista que almacenara los datos de la tupla
		String tipo="";
		String valor=""; //tendrá todos los elementos de la tupla en un solo string
		NodosLista aux; //recorre la lista
		String resultado="";
		
		while (i!=contador){
			
			lista.InsertaFinal(Arreglo[i].toString()); //ingresa cada elemento del arreglo en la lista
			valor+=Arreglo[i].toString();
			i++;
		}
		
		aux=lista.PrimerNodo; //se ubica en el primer nodo de la lista para despues irla recorriendo
				
		while(aux.siguiente!=null){
			
			if((aux.datos.equals("(")) && (aux.siguiente.datos.equals(")"))){ //cuando encuentra esto en el nodo, pasa al siguiente
				
				resultado="unit";
				valor="tupla vacia";
				this.InsertaFinal(identificador, resultado, valor);
				break;
				
			}
			
			if(aux.datos.equals("(")||aux.datos.equals(")")){ //cuando encuentra esto en el nodo, pasa al siguiente
				
				aux=aux.siguiente;
				
			}
			
			if(aux.datos.equals(",")){ //reemplaza la coma por el "*", esto para mostrar los tipos de la tupla
				
				aux.datos="*";
				aux=aux.siguiente;
			}
			
			else{
				
				if(!(aux.datos.equals("("))||(!(aux.datos.equals(")")))){ //se determina si es una tupla
					
				
				tipo=determinar_valor_tupla(aux.datos);
				
				if(tipo.equals("")){
					break;
				}
				
				else{
					aux.datos=tipo;
					aux=aux.siguiente;}
			
			    }
			}
		}
		if(!(tipo.equals(""))){
			aux=lista.PrimerNodo;
			
			while (aux!=null){
				
				resultado+=aux.datos;
				aux=aux.siguiente;
			
			}
			this.InsertaFinal(identificador, resultado, valor);} // se inserta en la lista principal, la que mostrara ambos ambientes
		else{
			
		}
		return resultado;
		}
		
	
	//*******************************************************************************************************************
	//este metodo indica el tipo de dato que hay en la tupla
	
	public String determinar_valor_tupla(String valor){
		
		String tipo="";
			
		if(valor.equals("true")|| valor.equals("false")){
					
			tipo="bool";
							
		}
		
		if (Es_Numero(valor)==true){
					
			tipo="int";
		}
				
		if(valor.startsWith("'") && valor.endsWith("'")){
					
			tipo="string";			
		}
		
		if (valor.startsWith("[") && valor.endsWith("]")){
			
			tipo=tipos_lista_para_tupla(valor,"");
		}
				
		return tipo;
		}
	
		
	//***********************************************************************************************************************
	//almacena en una lista el tipo de dato que almacena una lista recibida
	
	public Compilador Determinar_Valor_Lista(Object Arreglo[], int contador, String identificador,String valor){
		
		String tipo; //variable que almacenara el tipo de la expresion
		Compilador  lista=new Compilador();
			
		if(Arreglo[contador].toString().toLowerCase().equals("true")|| Arreglo[contador].toString().toLowerCase().equals("false")){
			
			tipo="bool list";
			lista.InsertaFinal(identificador, tipo, "bool");	
		}
		
		if (Es_Numero(Arreglo[contador])==true){
			
			tipo="int list";
			lista.InsertaFinal(identificador, tipo, "int");
		}
		
		if (Arreglo[contador].toString().startsWith("[") && Arreglo[contador].toString().endsWith("]")){
			
			tipo="bool list";
			lista.InsertaFinal(identificador, tipo, "bool");
			
		}
		
		if(Arreglo[contador].toString().startsWith("'") && Arreglo[contador].toString().endsWith("'")){
			
			tipo="string list";
			lista.InsertaFinal(identificador, tipo, "string");
		}
		
		return lista;
			
}
	
	//*****************************************************************************************************************
	//metodo que valida si la expresion es un numero
	
	public boolean Es_Numero(Object valor){
		
		boolean resultado = true;
		try { //trata de convertir el string a un numero, si se puede, entonces el resultado es true
			
			Integer.parseInt(valor.toString());
			resultado=true;
		}
		catch (NumberFormatException nfe){
			
			resultado = false; //de lo contrario, el resultado es false
		}
		return resultado;
		
		
	}
	
	//*****************************************************************************************************************
	//este metodo verifica el tipo de los elementos que hay en una lista
	
	public void tipos_lista(String valor, String identificador){
		
			Compilador lista=new Compilador(); //esta lista se crea para almacenar los elementos de una lista recibida, esto para ver tipo de esos elementos
			String resultado=null;
			boolean respuesta=true; //este boolean es para validar si eltipo de todos los elementos de la lista son iguales
			StringTokenizer tokens= new StringTokenizer(valor.toString(), "[,] "); //separa la linea cada vez que encuentre esos elementos
			int i=0;
			String arreglo_elementos[] = new String[100];
			
			while (tokens.hasMoreTokens()){
				
				
				valor=tokens.nextToken();
				arreglo_elementos[i]=valor.toString(); //ingresa cada token en un arreglo
				i++;
			}
			
			int x=0;
			
			while(x!=i){
				
			
				if (x==0){
					
					
					lista=Determinar_Valor_Lista(arreglo_elementos, x, "",valor); //retorna una lista con los elementos de la lista que se habia ingresado
					resultado=lista.UltimoNodo.Tipo.toString(); //se guarda el tipo de dato con el proposito de comparalo con los demas para ver si son iguales
					x++;
				}
				
				else{
					
					lista=Determinar_Valor_Lista(arreglo_elementos, x, "",valor);
					
					if (lista.UltimoNodo.Tipo.equals(resultado)){ //este bloque valida que el tipo de los elementos de la lista sean iguales
						
						
						respuesta=true;
						lista=Determinar_Valor_Lista(arreglo_elementos, x, "",valor);
						x++;	
						
					}
					else{
						
						respuesta=false; //si no son iguales, retorna un false y para
						break;
					}
						
					
					}
				}
			
				if(respuesta==true){ //si fue verdadero entonces se ingresa el tipo de la lista en la lista principal
					this.InsertaFinal(identificador, lista.UltimoNodo.Tipo, lista.UltimoNodo.Valor);
				}
				
				else{
					System.out.println("Los elementos de la lista deben tener el mismo tipo"+"\n");
				}
					
				
		
		
	}
	
	//*********************************************************************************************************************
	//se verifica el tipo que tiene la lista dentro de una tupla
	
	public String tipos_lista_para_tupla(String valor, String identificador){
		
		Compilador lista=new Compilador(); //esta lista se crea para almacenar los elementos de una lista recibida, esto para ver tipo de esos elementos
		String resultado=null;
		boolean respuesta=true; //este boolean es para validar si eltipo de todos los elementos de la lista son iguales
		StringTokenizer tokens= new StringTokenizer(valor.toString(), "[,] "); //separa la linea cada vez que encuentre esos elementos
		int i=0;
		String arreglo_elementos[] = new String[100];
		String tipo="";
		
		while (tokens.hasMoreTokens()){
			
			
			valor=tokens.nextToken();
			arreglo_elementos[i]=valor.toString(); //ingresa cada token en un arreglo
			i++;
		}
		
		int x=0;
		
		while(x!=i){
			
		
			if (x==0){
				
				
				lista=Determinar_Valor_Lista(arreglo_elementos, x, "",valor); //retorna una lista con los elementos de la lista que se habia ingresado
				resultado=lista.UltimoNodo.Tipo.toString();
				
				x++;
				//System.out.println(this.UltimoNodo.Tipo);
			}
			
			else{
				
				lista=Determinar_Valor_Lista(arreglo_elementos, x, "",valor);
				
				if (lista.UltimoNodo.Tipo.equals(resultado)){ //este bloque valida que el tipo de los elementos de la lista sean iguales
					
					
					respuesta=true;
					lista=Determinar_Valor_Lista(arreglo_elementos, x, "",valor);
					x++;	
					
				}
				else{
					
					respuesta=false; //si no son iguales, retorna un false y para
					break;
				}
					
				
				}
			}
		
			if(respuesta==true){ //si fue verdadero entonces se ingresa el tipo de la lista en la lista principal
				tipo=lista.UltimoNodo.Tipo.toString();
			}
			
			else{
				tipo="";
				System.out.println("Los elementos de la lista deben tener el mismo tipo"+"\n");
			}
				
			return tipo;
	
	
}
	
	//*************************************************************************************************************
    
	//este metodo es el encargado de evaluar la expresion if
	
	public void evaluar_if(Object [] Arreglo, int i, String identificador){
		
		 int contador = 1 ;
		 ListaSimple lista_temporal=new ListaSimple();
		 NodosLista aux=lista_temporal.PrimerNodo;
		 String resultado="";
		 String valor="";
		 boolean comparaciones=true;
		 boolean valor1=true;
		 boolean valor2=true;
      	        		  	  
				while(!(Arreglo[contador].toString().equals("then"))){
				  					 
					lista_temporal.InsertaFinal(Arreglo[contador].toString()); //almacena en una lista temporal la expresion que esta entre el if y el then
					contador++;
                   
				}
				 				
				aux=lista_temporal.PrimerNodo;
				
				while (aux!=null){
					
					resultado+=aux.datos;
					aux=aux.siguiente;
				
				}
				
				aux=lista_temporal.PrimerNodo; //se ubica en el primer nodo para recorrer la lista
				
				if ((resultado.contains(">")) || (resultado.contains("=")) || (resultado.contains("<")) || (resultado.contains("!="))){
					 					
					 valor1=Es_Numero(aux.datos); 
					 valor2=Es_Numero(aux.siguiente.datos);
					 
					 if((valor1==true) && (valor2==true)){ //verifica si se estan comparando dos numeros
						 
						 comparaciones=comparaciones(lista_temporal.PrimerNodo.siguiente.datos.toString(),Integer.parseInt(lista_temporal.PrimerNodo.datos.toString()),Integer.parseInt(lista_temporal.PrimerNodo.siguiente.siguiente.datos.toString()));
						 this.InsertaFinal(resultado, "bool", comparaciones);
					 }
					 
					 else{
						 
						 if(valor1==false){ //se cumple esta condicion cuando no es un numero el valor si no una letra
							 
							 valor=estaElemento(aux.datos.toString());
							 
							 if(!valor.equals("")){
								 
								comparaciones=comparaciones(lista_temporal.PrimerNodo.siguiente.datos.toString(),Integer.parseInt(valor),Integer.parseInt(lista_temporal.PrimerNodo.siguiente.siguiente.datos.toString()));
		  						this.InsertaFinal(resultado, "bool", comparaciones);
			  					  								 
								 
							 }
						 }
						 if(valor2==false){
							 
							 valor=estaElemento(aux.siguiente.datos.toString()); //busca si la variable fue asociada anteriormente
							 
							 if(!valor.equals("")){ //esto lo retornaria en caso de no encontrarse la variable, por lo tanto se usa el ! para comprobar que si se encuentra
								 
								comparaciones=comparaciones(lista_temporal.PrimerNodo.siguiente.datos.toString(),Integer.parseInt(valor),Integer.parseInt(lista_temporal.PrimerNodo.siguiente.siguiente.datos.toString()));
		  						this.InsertaFinal(resultado, "bool", comparaciones);
			  					  								 
								 
							 }
						 }

						 
						 
					 }
					 
				}
		
				contador=contador+1;
				
				int cero=0;
       	    Object nuevo []=new Object[100];
				
				while(!(Arreglo[contador].toString().equals("else"))){
	  					 
					nuevo[cero] = Arreglo[contador]; //se agrega la expresion que esta entre then y else
					contador++;
					cero++;
                   
				}
								
				evaluar_expresion(nuevo, cero);
				contador=contador+1;
				Object nuevo2 []=new Object[100];
				cero=0;
				
				while(contador!=i){
					
					nuevo2[cero] = Arreglo[contador]; //aqui se almacena el resto de la expresion, es decir, la que va despues de else
					contador++;
					cero++;
					
				}
				evaluar_expresion(nuevo2, cero); //esa expresion almacenada en el arreglo, se manda a evaluar
	            
      }
	
	
	//**************************************************************************************************************
	//este método busca si un elemento está en la lista principal
	
	public String estaElemento(String Elem)
	{
	
		NodosCompilador aux=this.PrimerNodo; //se ubica en la primera posición de la lista
		String resultado="";
		
		while(aux!=null){
			
			if(aux.Identificador.toString().equals(Elem)){
				
				resultado=aux.Valor.toString(); //si lo encuentra, almacena el valor aqui
				break;
			}
			aux=aux.siguiente; // si no continua
		}
		return resultado; 
	}

//****************************************************************************************************************
	//Este metodo busca un string determinado
	
	public boolean busqueda(String Elem)
	{
	
		NodosCompilador aux=this.PrimerNodo; //se ubica en la primera posición de la lista
		boolean resultado=false;
		
		while(aux!=null){
			
			if(aux.Identificador.toString().equals(Elem)){
				
				resultado=true; //si lo encuentra, almacena el valor aqui
				break;
			}
			aux=aux.siguiente; // si no continua
		}
		return resultado; 
	}
	
//*************************************************************************************************************************
	//este metodo realiza la comparacion entre dos numeros, si se cumple entonces el resultado es true,
	//de lo contrario sera false
	
	public boolean comparaciones(String elemento,int x, int y){
		
		boolean resultado=true;
		
		if (elemento.equals(">")){
			
			if(x>y){
				
				resultado=true;}
			
			else{
				resultado=false;
			}
		}
		
		if (elemento.equals("<")){
					
			if(x<y){
						
			 resultado=true;}
					
			else{
				resultado=false;
			}
		}
		
		if(elemento.equals("=")){
			
			if(x==y){
				
			  resultado=true;}
						
			else{
	     		resultado=false;
			}
			
		} 
			
		if(elemento.equals("!=")){
			
			if(x!=y){
				
			 resultado=true;
			}
			else{
				resultado=false;
			}
		}
		return resultado;
	}
	
//*************************************************************************************************************
//Metodo que escribe en el archivo el identificador y el tipo de la variable

public void ambiente_estatico(String nombre, Compilador lista) throws FileNotFoundException

{
	String fileName= nombre;
	 FileOutputStream leer=   new FileOutputStream(fileName,false);
	
	 

try{

	OutputStreamWriter writer= new OutputStreamWriter(leer);
	BufferedWriter salida = new java.io.BufferedWriter(writer);
	NodosCompilador aux=lista.PrimerNodo;
	
	while(aux!=null){
		salida.write( aux.Identificador.toString()+ " ; "+  aux.Tipo.toString());//escribimos en el archivo
		salida.write(" ");
		salida.newLine();
		//salida.println();
		aux=aux.siguiente;
	}
	
	salida.close();
	
//	bw.close();
	
	}
catch(IOException e){};
	
	}


public void ambiente_dinamico(String nombre, Compilador lista) throws FileNotFoundException

{
	 String fileName= nombre;
	 FileOutputStream leer=   new FileOutputStream(fileName,false);
	
	 

try{

	OutputStreamWriter writer= new OutputStreamWriter(leer);
	BufferedWriter salida = new java.io.BufferedWriter(writer);
	
	NodosCompilador aux=lista.PrimerNodo;
	
	while(aux!=null){
		
		salida.write (aux.Identificador.toString()+ ";" +  aux.Valor.toString() +  ";"+ "\n");//escribimos en el archivo
		salida.newLine();
		aux=aux.siguiente;
	}
	
	salida.close();
	

	
	}
catch(IOException e){};
	
	}     
 
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
protected void CargarArchivo(HttpServletRequest request, HttpServletResponse response) throws Exception
{
	//Ruta donde se guardara el fichero
	File destino=new File("C:\\TEMP\\");
//Convertimos el HTTPRequest en un ContextRequest,este paso es necesario en la ultima version, ya que los metodos de las versiones anteriores se han quedado desfasados.
	ServletRequestContext src=new ServletRequestContext(request);
 
	String fileName = "" ;
	 
	if(ServletFileUpload.isMultipartContent(src))  //Si el formulario es enviado con Multipart
			{
		
		DiskFileItemFactory factory = new DiskFileItemFactory((1024*1024),destino); //Necesario para evitar errores de NullPointerException
		ServletFileUpload upload=new  ServletFileUpload(factory); //Creamos un FileUpload
		List lista = null; //Procesamos el request para que nos devuelva una lista con los parametros y ficheros.
		ArrayList<String> listaFinal  = new ArrayList<String>(); //Procesamos el request para que nos devuelva una lista con los parametros y ficheros.
		//Archivo lista1=new Archivo();
		try {
			lista = upload.parseRequest(src);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File file= null;
		//Recorremos la lista.
		Iterator it = lista.iterator();
		while(it.hasNext()){
			//Rescatamos el fileItem
			FileItem item=(FileItem)it.next();
			//Comprobamos si es un campo de formulario
			if(item.isFormField()){
				//Hacemos lo que queramos con el.
				System.out.println(item.getFieldName()+"<br>");
				System.out.println("Tonta");
			}
		else
		{
			
			//Si no, es un fichero y lo subimos al servidor.
			//Primero creamos un objeto file a partir del nombre del fichero.
		
			file=new File(item.getName());
			
			//Lo escribimos en el disco usando la ruta donde se guardara el fichero y cogiendo el nombre del file
			
				System.out.println("Hola");
				 fileName = item.getName();
                 String contentType = item.getContentType();
                 String String  = item.getString(); 
                 
                 
                 long size = item.getSize();
                 request.setAttribute("fileName", fileName);
                 System.out.println("Tonta" + fileName);
                 request.setAttribute("contentType", contentType);
                 request.setAttribute("size", size);
                 System.out.println("size" +  size);
				 
				 
				 item.write(new File(destino,file.getName()));
				 
				 String docBase = getServletConfig().getServletContext().getRealPath ("/");
		    	 System.out.println(docBase);
				 // Parte para leer el archivo
		    	
		    	try{
		    		File Archivo = new File ( destino + "/" + fileName );
					FileReader lectorarchivo= new FileReader(Archivo);
					BufferedReader br= new BufferedReader(lectorarchivo);
					String linea="";
					//Object linea2;
					//Object[] arreglo_lineas= new Object[100];
					int contador=0;
							
					while(true){
						
						linea= br.readLine(); //Corresponde a cada lï¿½nea del archivo	
						//System.out.println(linea);
						arreglo_lineas= new Object[100];
					//	lista1.
						
						if (linea!=null){
							
							StringTokenizer tokens= new StringTokenizer(linea, " "); //separa la linea cada vez que encuentre un espacio
							while (tokens.hasMoreTokens()){
								
								linea=tokens.nextToken();
								arreglo_lineas[contador]=linea; //ingresa cada token en un arreglo
								listaFinal.add(linea);
								System.out.println("La linea es" + linea);
								request.setAttribute("linea", listaFinal);
								contador++;
							}	
							
							evaluar_expresion(arreglo_lineas,contador); //funcion que determina si el parametro es un val, let, etc.
							contador=0;


							}
								
						else
							break; 
						
							
						}	
				
					
			           System.out.println("*************************************************************************************"); 
			           this.Imprimir(); //imprime la lista una vez que finaliza de leer el archivo
						
						
						ambiente_estatico("C:/TEMP/Estatico.txt", this);
						ambiente_dinamico("C:/TEMP/dinamico.txt",this);
						request.getRequestDispatcher("Resultado.jsp").forward(request, response); 
						br.close();
						lectorarchivo.close();
			         		
						
					}
				
				catch (IOException e){
					System.out.println("Error:" +e.getMessage()); //Imprime un mensaje de error en el caso de que haya problemas con el archivo
				}
		    	
		
	}

		}
		
			}
}


    
    
    

    
    
    
    
    
    
    
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			CargarArchivo(request, response);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		//LeerArchivo(request, response);
		
	}

}
