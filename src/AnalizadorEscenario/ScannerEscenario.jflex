package AnalizadorEscenario;
import java.util.ArrayList;
import Clases.*;
import Almacenamiento.*;
import java_cup.runtime.Symbol;

%%
    entero = [0-9]+
	letra = [a-zA-ZñÑ]+
    identificador = {letra}({letra}|{entero}|"_")*
	//comentarioLinea = "//"[^\n]*
	//comentarioMultilinea = "/*"~"*/"

%cupsym Simbolos
%cup
%class LexicoEscenario
%unicode
%public
%line
%ignorecase
%column
//%type TokenEscenario

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
/*Expresiones Regulares*/
	{entero} 		{System.out.println("Entero: "+yytext()+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 			return new Symbol(Simbolos.entero, yycolumn,yyline+1,new String(yytext()));}
    {identificador} {System.out.println("Identificador: "+yytext()+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 	return new Symbol(Simbolos.identificador, yycolumn,yyline+1,new String(yytext()));}
	
/*Operadores*/
    "->"	{System.out.println("->"+" Linea: "+(yyline+1)+" Columna: "+yycolumn);		return new Symbol(Simbolos.asignacion, yycolumn,yyline+1,new String(yytext()));}
	";" 	{System.out.println(";"+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 		return new Symbol(Simbolos.puntoComa, yycolumn,yyline+1,new String(yytext()));}
	".." 	{System.out.println(".."+" Linea: "+(yyline+1)+" Columna: "+yycolumn); 		return new Symbol(Simbolos.rango, yycolumn,yyline+1,new String(yytext()));}
	"," 	{System.out.println(","+" Linea: "+(yyline+1)+" Columna: "+yycolumn);		return new Symbol(Simbolos.coma, yycolumn,yyline+1,new String(yytext()));}
	"(" 	{System.out.println("("+" Linea: "+(yyline+1)+" Columna: "+yycolumn);		return new Symbol(Simbolos.parentesisAbre, yycolumn,yyline+1,new String(yytext()));}
	")" 	{System.out.println(")"+" Linea: "+(yyline+1)+" Columna: "+yycolumn);		return new Symbol(Simbolos.parentesisCierre, yycolumn,yyline+1,new String(yytext()));}
	
/*Espacios en Blanco*/
    [ \t\r\f\n]+ { /* Se ignoran */;}
	//{comentarioLinea} { /* Se ignoran System.out.println("comentario:  "+yytext()+" line "+(yyline+1))*/;}
	//{comentarioMultilinea} { /* Se ignoran System.out.println("comentario multilinea:  "+yytext()+" line "+(yyline+1))*/;}
	
/* Cualquier Otro */
    . 		{System.out.println("Error lexico: "+ yytext()+", linea: "+(yyline+1)+", columna:"+yycolumn); 	ingresarErrorLexico(yytext(),yyline+1,yycolumn,"Error Lexico, archivo Escenario.maze"); 	return new Symbol(Simbolos.errorLexico, yycolumn,yyline+1,new String(yytext()));}