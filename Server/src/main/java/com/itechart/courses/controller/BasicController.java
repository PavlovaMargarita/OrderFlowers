package com.itechart.courses.controller;

import com.google.gson.Gson;
import com.itechart.courses.bl.dto.UserDTO;
import com.itechart.courses.bl.service.authorization.Authorization;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;

@Controller
@RequestMapping("/OrderFlowers")
public class BasicController {
    //вроде надо файлик, чтобы указать, где лежат сервисы (лекция, архив java 2014)
    @Autowired
    private Authorization authorization;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String indexPage() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/authorize")
    @Consumes(MediaType.APPLICATION_JSON)
    public @ResponseBody String authorization(HttpServletRequest request) throws IOException, JSONException{
        JSONObject jsonObject = fromJson(request);
        UserDTO userDTO = authorization.execute(jsonObject.getString("login"), jsonObject.getString("password"));
        return toJson(userDTO);
    }
    //преобразование json-строки в объект, который хранит пришедшие параметры
    private JSONObject fromJson(HttpServletRequest request) throws IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line = null;
        while ((line = reader.readLine()) != null){
            builder.append(line);
        }
        reader.close();
        return new JSONObject(builder.toString());
    }
    //преобразование объекта в json-строку
    private String toJson(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }


}