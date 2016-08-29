package AnalizadorRecursos;
import java.util.ArrayList;
import Almacenamiento.*;
import Clases.*;
import java_cup.runtime.Symbol;

%%
%cupsym Simbolos
%cup
%class LexicoRecurso
%unicode
%public
%line
%ignorecase
%column
//%type TokenRecurso

    entero = [0-9]+
	letra = [a-zA-ZñÑ]+
    identificador = {letra}({letra}|{entero}|"_")*
	ruta = \"({identificador}|"/"|":")*"."("png"|"gif"|"jpg"|"jpeg")\"
	//comentarioLinea = "//"[^\n]*
	//comentarioMultilinea = "/*"~"*/"



	/*Metodos de la clase*/
%{
		private ArrayList<ErrorLexico> listaErrores = new ArrayList<ErrorLexico>();
		
		private void ingresarErrorLexico(String lexema,int fila,int columna,String descripcion){
			listaErrores.add(new ErrorLexico(lexema,fila,columna,descripcion));
		}
			
		public ArrayList<ErrorLexico> getListaErrores(){
			return listaErrores;
		}
%}

%%
	/*Palabras Reservadas*/
	"escenario"	{System.out.println("escenario"+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 		return new Symbol(Simbolos.escenario, yycolumn,yyline+1,new String(yytext()));}
	"heroe"		{System.out.println("heroe"+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 			return new Symbol(Simbolos.heroe, yycolumn,yyline+1,new String(yytext()));}
	"enemigo"	{System.out.println("enemigo"+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 		return new Symbol(Simbolos.enemigo, yycolumn,yyline+1,new String(yytext()));}
	"img"		{System.out.println("img"+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 			return new Symbol(Simbolos.img, yycolumn,yyline+1,new String(yytext()));}
	"corazones"	{System.out.println("corazones"+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 		return new Symbol(Simbolos.corazones, yycolumn,yyline+1,new String(yytext()));}
	"algoritmo"	{System.out.println("algoritmo"+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 		return new Symbol(Simbolos.algoritmo, yycolumn,yyline+1,new String(yytext()));}
	"obstaculo"	{System.out.println("obstaculo"+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 		return new Symbol(Simbolos.obstaculo, yycolumn,yyline+1,new String(yytext()));}
	"dest"		{System.out.println("dest"+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 			return new Symbol(Simbolos.dest, yycolumn,yyline+1,new String(yytext()));}
	"v"			{System.out.println("verdadero"+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 		return new Symbol(Simbolos.v, yycolumn,yyline+1,new String(yytext()));}
	"f"			{System.out.println("falso"+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 			return new Symbol(Simbolos.f, yycolumn,yyline+1,new String(yytext()));}
	"bomba-r"	{System.out.println("bomba-r"+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 		return new Symbol(Simbolos.bombar, yycolumn,yyline+1,new String(yytext()));}
	"fuego"		{System.out.println("fuego"+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 			return new Symbol(Simbolos.fuego, yycolumn,yyline+1,new String(yytext()));}
	"bomba"		{System.out.println("bomba"+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 			return new Symbol(Simbolos.bomba, yycolumn,yyline+1,new String(yytext()));}
	"corazon"	{System.out.println("corazon"+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 		return new Symbol(Simbolos.corazon, yycolumn,yyline+1,new String(yytext()));}
	
/*Expresiones Regulares*/
	{entero} 		{System.out.println("Entero: "+yytext()+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 				return new Symbol(Simbolos.entero, yycolumn,yyline+1,new String(yytext()));}
    {identificador} {System.out.println("Identificador: "+yytext()+" Linea: "+(yyline+1)+" Columna: "+yycolumn);		return new Symbol(Simbolos.identificador, yycolumn,yyline+1,new String(yytext()));}
	{ruta} 			{System.out.println("ruta: "+yytext()+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 				return new Symbol(Simbolos.ruta, yycolumn,yyline+1,new String(yytext()));}
	
/*Operadores*/
    ":" 	{System.out.println(":"+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 		return new Symbol(Simbolos.dosPuntos, yycolumn,yyline+1,new String(yytext()));}
	"{" 	{System.out.println("{"+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 		return new Symbol(Simbolos.llaveAbre, yycolumn,yyline+1,new String(yytext()));}
	"}" 	{System.out.println("}"+" Linea: "+(yyline+1)+" Columna: "+yycolumn);		return new Symbol(Simbolos.llaveCierre, yycolumn,yyline+1,new String(yytext()));}
	
/*Espacios en Blanco*/
    [ \t\r\f\n]+ { /* Se ignoran */;}
	//{comentarioLinea} { /* Se ignoran System.out.println("comentario:  "+yytext()+" line "+yyline)*/;}
	//{comentarioMultilinea} { /* Se ignoran System.out.println("comentario multilinea:  "+yytext()+" line "+yyline)*/;}
	
/* Cualquier Otro */
    . 		{System.out.println("Error lexico: "+ yytext()+", linea: "+(yyline+1)+", columna:"+yycolumn);	ingresarErrorLexico(yytext(),yyline+1,yycolumn,"Error Lexico, archivo Recursos.rr"); 	return new Symbol(Simbolos.errorLexico, yycolumn,yyline+1,new String(yytext()));}