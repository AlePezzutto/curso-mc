package com.nelioalves.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class ResourceUtils {

	public static List<Integer> decodeIntList(String s){
		
		List<Integer> lstRet = new ArrayList<>();
		
		for(String n : s.split(",")) {
			lstRet.add(Integer.parseInt(n));
		}
		
		return lstRet;
	}

	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		}
		catch(UnsupportedEncodingException e) { 
			return "";
		}
	}
}
