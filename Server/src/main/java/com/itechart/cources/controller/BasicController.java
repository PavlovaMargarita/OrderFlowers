package com.itechart.cources.controller;

import com.google.gson.Gson;
import com.itechart.cources.entity.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Александр on 21.08.2014.
 */

@Controller
@RequestMapping("/OrderFlowers")
public class BasicController {

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public @ResponseBody String helloWorld() {
        return "HelloWorld";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String indexPage() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/authorize")
    @Consumes(MediaType.APPLICATION_JSON)
    public @ResponseBody String authorization(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
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
        return json;

    }
}
