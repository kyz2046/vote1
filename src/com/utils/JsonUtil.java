package com.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

	public static void toJson(HttpServletResponse response,Object object) {
		PrintWriter pw=null;
		try {
			pw=response.getWriter();
			pw.write(new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(object));
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null!=pw){
				pw.close();
			}
		}
	}
}
