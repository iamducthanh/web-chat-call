package com.webchat.webchat.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;

/**
 * Description:
 *
 * @author: GMO_ThanhND
 * @version: 1.0
 * @since 12/12/2021 10:30 AM
 * Project_name: GMO_QuanLyTaiSan
 */
public class HttpUtil {
 String value;
 public HttpUtil(String value){
  this.value = value;
 }

 public static HttpUtil of (BufferedReader reader){
  StringBuilder json = new StringBuilder();
  String line;
  try {
   while ((line = reader.readLine()) != null){
    json.append(line);
   }
   System.out.println(json.toString());
  } catch (Exception e){
   System.out.println(e.getMessage());
  }
  return new HttpUtil(json.toString());
 }

 public <T> T toModel(Class<T> tClass){
  try {
   return new ObjectMapper().readValue(value, tClass);
  }catch (Exception e){
   return null;
  }
 }
}
