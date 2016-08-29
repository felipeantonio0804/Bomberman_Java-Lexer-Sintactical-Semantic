package AnalizadorInteligencia;
import java.util.ArrayList;
import Clases.*;
import Almacenamiento.*;
import java_cup.runtime.Symbol;

%%
    entero = [0-9]+
	letra = [a-zA-ZñÑ]
	decimal = {entero}"."{entero}
    identificador = {letra}({letra}|{entero}|"_")*
	//comentarioLinea = "//"[^\n]*
	//comentarioMultilinea = "/*"~"*/"

%cupsym Simbolos
%cup
%class LexicoInteligencia
%unicode
%public
%line
%ignorecase
%column
//%type TokenInteligencia

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
	"bomba()"		{System.out.println("bomba"+" Linea: "+yyline+" Columna: "+yycolumn); 		return new Symbol(Simbolos.bomba, yycolumn,yyline+1,new String(yytext()));}
	"move"		{System.out.println("move"+" Linea: "+yyline+" Columna: "+yycolumn); 			return new Symbol(Simbolos.move, yycolumn,yyline+1,new String(yytext()));}
	"-x"		{System.out.println("-x"+" Linea: "+yyline+" Columna: "+yycolumn); 				return new Symbol(Simbolos.menosx, yycolumn,yyline+1,new String(yytext()));}
	"+x"		{System.out.println("+y"+" Linea: "+yyline+" Columna: "+yycolumn); 				return new Symbol(Simbolos.masx, yycolumn,yyline+1,new String(yytext()));}
	"-y"		{System.out.println("-y"+" Linea: "+yyline+" Columna: "+yycolumn); 				return new Symbol(Simbolos.menosy, yycolumn,yyline+1,new String(yytext()));}
	"+y"		{System.out.println("+y"+" Linea: "+yyline+" Columna: "+yycolumn); 				return new Symbol(Simbolos.masy, yycolumn,yyline+1,new String(yytext()));}
	"verificar"	{System.out.println("verificar"+" Linea: "+yyline+" Columna: "+yycolumn);		return new Symbol(Simbolos.verificar, yycolumn,yyline+1,new String(yytext()));}
	"espera"	{System.out.println("espera"+" Linea: "+yyline+" Columna: "+yycolumn); 			return new Symbol(Simbolos.espera, yycolumn,yyline+1,new String(yytext()));}
	"if"		{System.out.println("if"+" Linea: "+yyline+" Columna: "+yycolumn); 				return new Symbol(Simbolos.tokenif, yycolumn,yyline+1,new String(yytext()));}
	"elseif"	{System.out.println("elseif"+" Linea: "+yyline+" Columna: "+yycolumn); 			return new Symbol(Simbolos.elseif, yycolumn,yyline+1,new String(yytext()));}
	"else"		{System.out.println("else"+" Linea: "+yyline+" Columna: "+yycolumn); 			return new Symbol(Simbolos.tokenelse, yycolumn,yyline+1,new String(yytext()));}
	"while"		{System.out.println("while"+" Linea: "+yyline+" Columna: "+yycolumn); 			return new Symbol(Simbolos.tokenwhile, yycolumn,yyline+1,new String(yytext()));}
	"def"		{System.out.println("def"+" Linea: "+yyline+" Columna: "+yycolumn); 			return new Symbol(Simbolos.def, yycolumn,yyline+1,new String(yytext()));}
/*Expresiones Regulares*/
	{decimal} 		{System.out.println("decimal: "+yytext()+" Linea: "+yyline+" Columna: "+yycolumn); 			return new Symbol(Simbolos.decimal, yycolumn,yyline+1,new String(yytext()));}
    {identificador} {System.out.println("Identificador: "+yytext()+" Linea: "+yyline+" Columna: "+yycolumn);	return new Symbol(Simbolos.identificador, yycolumn,yyline+1,new String(yytext()));}
	
	
	
/*Operadores*/
    "(" 	{System.out.println("("+" Linea: "+yyline+" Columna: "+yycolumn); 		return new Symbol(Simbolos.parentesisAbre, yycolumn,yyline+1,new String(yytext()));}
	")" 	{System.out.println(")"+" Linea: "+yyline+" Columna: "+yycolumn); 		return new Symbol(Simbolos.parentesisCierre, yycolumn,yyline+1,new String(yytext()));}
	"{" 	{System.out.println("{"+" Linea: "+yyline+" Columna: "+yycolumn); 		return new Symbol(Simbolos.llavesAbre, yycolumn,yyline+1,new String(yytext())); }
	"}" 	{System.out.println("}"+" Linea: "+yyline+" Columna: "+yycolumn);		return new Symbol(Simbolos.llavesCierre, yycolumn,yyline+1,new String(yytext()));}
	";" 	{System.out.println(";"+" Linea: "+yyline+" Columna: "+yycolumn);		return new Symbol(Simbolos.puntoComa, yycolumn,yyline+1,new String(yytext()));}
	
/*Espacios en Blanco*/
    [ \t\r\f\n]+ { /* Se ignoran */;}
	//{comentarioLinea} { /* Se ignoran System.out.println("comentario:  "+yytext()+" line "+yyline)*/;}
	//{comentarioMultilinea} { /* Se ignoran System.out.println("comentario multilinea:  "+yytext()+" line "+yyline)*/;}
	
/* Cualquier Otro */
    . 		{System.out.println("Error lexico"+ yytext()+" linea: "+yyline+" columna:"+yycolumn); 	ingresarErrorLexico(yytext(),yyline+1,yycolumn,"Error Lexico, archivo Recursos.rr"); 	return new Symbol(Simbolos.errorLexico, yycolumn,yyline+1,new String(yytext()));}