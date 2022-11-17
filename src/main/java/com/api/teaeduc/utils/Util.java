package com.api.teaeduc.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class Util {
	
	public static final String PATH_SEPARADOR = "/";

	public static boolean equalOrBetween(LocalDate dataSerComparada, LocalDate dataInicial, LocalDate dataFinal){
		if((dataSerComparada.isAfter(dataInicial) || dataSerComparada.isEqual(dataInicial))
				&& (dataSerComparada.isBefore(dataFinal) || dataSerComparada.isEqual(dataFinal))){
			return true;
		}
		return false;
	}
	
	public static LocalDate convertDateForLocalDateTime(Date data){
        return  data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
