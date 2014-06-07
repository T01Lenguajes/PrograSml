<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Muestra </title>
    

<link href='http://fonts.googleapis.com/css?family=Rancho' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Orbitron:400,500' rel='stylesheet' type='text/css'>
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
			color:rgba(148, 192, 242, 0.95);
            text-align: center;
		}

		b{
			
            font-size: 4cm;
			font-family: 'Orbitron', sans-serif;
			text-align: center;
            color: rgba(141, 70, 109, 0.88);
			margin:0cm;
		}

		h1{
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


		li{
			font-family: 'Rancho';
			display: inline-block;
			font-size: 1.7em;
			margin-right: 1em;
			cursor: pointer;
			padding: 0.3em;
        } 
    
    
    </style>
    
</head>
<body  align= "center">
 
<marquee width=200 direction= "down">   

 <h1>Mostrar el resultado del archivo</h1> 
 
</marquee>
    

<form action="Contenido" method="post"  >


        

</form>

<h1>Informacion del archivo </h1>
        <dl>
             <dt> fileName:${fileName}
            <dt>contentType:${contentType}
            <dt>size:${size/1024.0} KB
            <dt> String: ${linea}
          
        </dl>
      
 

<form action="Estatico"  method="post"    align= "center" >


<td> <input type="submit" value="Ambiente Estatico"  style="font-size:14px; color: black;" >  </td>


</form>

 <form action="Dinamico" method="post" >
<td> <input type="submit" value="Ambiente Dinamico"  style="font-size:14px; color: black;">  </td>
</form>




</body>
</html>