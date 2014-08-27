package com.itechart.cources.controller;

import com.google.gson.Gson;
import com.itechart.cources.bl.service.authorization.Authorization;
import com.itechart.cources.entity.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;




@Controller
@RequestMapping("/OrderFlowers")
public class BasicController {
    //вроде надо файлик, чтобы указать, где лежат сервисы (лекция, архив java 2014)
    @Autowired
    private Authorization authorizationService;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String indexPage() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/authorize")
    @Consumes(MediaType.APPLICATION_JSON)
    public @ResponseBody String authorization(HttpServletRequest request) throws IOException, JSONException{
        JSONObject jsonObject = fromJson(request);
        User user = authorizationService.execute(jsonObject.getString("login"), jsonObject.getString("password"));

        /*StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = request.getReader();
            String line = null;
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = null;
        String login = null;
        String password = null;
        try {
            jsonObject = new JSONObject(builder.toString());
            login = jsonObject.getString("login");
            password = jsonObject.getString("password");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setLogin(login);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        return json;*/
        return toJson(user);
    }



    //преобразование json-строки в объект, который хранит пришедшие параметры
    private JSONObject fromJson(HttpServletRequest request) throws IOException, JSONException{
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
