package br.com.itcraft.book.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class NumberConverterUtils {

	public static double convertToBR(String valor) throws ParseException{
	    NumberFormat numFormat = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
	    double valorFormatado = numFormat.parse(valor).doubleValue();
	    return valorFormatado;
	}

}
