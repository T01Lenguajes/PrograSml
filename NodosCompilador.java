package com.app.jsps.servlet;

//Clase NodosCompilador

class NodosCompilador {
	
	Object Identificador,Tipo,Valor;	
  NodosCompilador siguiente;  //siguiente con valor de nulo
  //anterior=null;
	    

	//Construtor  Crea un nodo del tipo Object
	 NodosCompilador (Object identificador, Object tipo, Object valor)
	  { 
		 Identificador=identificador;
		 Tipo=tipo;
		 Valor=valor;
		 siguiente = null; //siguiente se refiere al siguiente nodo
	  }


	}//Final de la Clase NodosLi
