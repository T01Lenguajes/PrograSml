<%@page import="java.io.DataInputStream"%>
<%@page import="java.io.DataInput"%>
<%@page import="java.io.*"%>
<%@page import="java.util.StringTokenizer;"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ambiente Estatico</title>
 <link href='http://fonts.googleapis.com/css?family=Lobster' rel='stylesheet' type='text/css'>
    
<style type="text/css">

		body,html{
			height: 100%;
			width: 100%;
			background-color: #eb9cbc;
		}


		#fondo{
			width: 100%;
			height: 10%;
			min-height: 50%;
			margin: 0 auto;
			text-align: center;
			font-family: monospace;
			background-color: #C0FF7D;
			border-radius: 0.15em;
			padding: 0.10em;
			box-shadow: 5em;

		}
    
		a{
		    
            text-decoration: underline;
			color:#e651e1;
            text-align: center;
		}

		b{
			
            font-size: 4cm;
			font-family: 'Orbitron', sans-serif;
			text-align: center;
            color: rgba(141, 70, 109, 0.88);
			
		}

		h1 {
			font-family: 'Lobster', cursive;
			font-size: 2em;
			color: #222121;
            text-align: center;
		}
	
		p{
			font-size: 1.5em;
			font-family: 'Comic Sans';
			color: #222121;
		}


		li {
			font-family: 'Rancho';
			display: inline-block;
			font-size: 1.7em;
			margin-right: 1em;
			cursor: pointer;
			padding: 0.3em;
		}


		</style>    
    
</head>
<body align= "center" >
<marquee width=200 direction=right>

<h1> Bienvenido al Ambiente Estatico </h1>

</marquee>

<form action="Contenido" method="post"  >


</form>

<%

//declarando

String linea=""; String nombre="";;

//creando un objeto de tipo archivo

DataInputStream archivo = null;

//construyendo una tabla html

out.println("<HTML><TABLE Border=3 align= center CellPadding=3 ><TR>");

out.println("<th bgcolor=#81f5b2>Identificador</th><th bgcolor=#a2dbff>Tipo</th></TR>");

try {

//abriendo archivo

archivo = new DataInputStream(new FileInputStream("C:/TEMP/Estatico.txt")); 

//ciclo de lectura del archivo

while(true){

	linea= archivo.readLine(); ; //Corresponde a cada l�nea del archivo
	
	//System.out.println(linea);
	//Object[] arreglo_lineas= new Object[100];
	
	if (linea!=null){
		
		StringTokenizer tokens= new StringTokenizer(linea , ";"); //separa la linea cada vez que encuentre un espacio
		while (tokens.hasMoreTokens()){
			
			out.println("<TR>");
		
			String Tipo= tokens.nextToken();
			
			String Valor = tokens.nextToken();
				
				
		
out.println("<TD>"+Tipo+"</TD>");

//nombre=archivo.readUTF();

out.println("<TD>"+Valor+"</TD>");

//estatura=archivo.readFloat();

//out.println("<TD>"+estatura+"</TD>");

out.println("</TR>");

} 
	}
	

	else
		break; 
	
		
	}
	
		
	
	
}


catch(FileNotFoundException fnfe) {}

catch (IOException ioe) {};

archivo.close();


//construyendo forma dinamica

out.println("<FORM ACTION=.AmbienteEstatico.jsp METHOD=post>");

//out.println("<INPUT TYPE=SUBMIT NAME=LECTURA VALUE=leer ><BR>");

out.println("</FORM>");

%>


       
     


</body>
</html>