package com.itechart.courses.controller;

import com.google.gson.Gson;
import com.itechart.courses.dto.ContactDTO;
import com.itechart.courses.dto.UserDTO;
import com.itechart.courses.enums.RoleEnum;
import com.itechart.courses.service.authorization.AuthorizationService;
import com.itechart.courses.service.contact.ContactService;
import com.itechart.courses.service.user.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/OrderFlowers")
public class BasicController {
    //вроде надо файлик, чтобы указать, где лежат сервисы (лекция, архив java 2014)
    @Autowired
    private AuthorizationService authorization;

    @Autowired
    private ContactService  contactService;

    @Autowired
    private UserService userService;
//    @RequestMapping(method = RequestMethod.POST, value = "/authorize")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public @ResponseBody String authorization(HttpServletRequest request) throws IOException, JSONException{
//        JSONObject jsonObject = fromJson(request);
//        UserDTO userDTO = authorization.execute(jsonObject.getString("login"), jsonObject.getString("password"));
//        return toJson(userDTO);
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/authorize")
    public @ResponseBody UserDTO authorization(@RequestBody UserDTO user) throws IOException, JSONException{
        UserDTO userDTO = authorization.execute(user.getLogin(), user.getPassword());
        return userDTO;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/contactList")
    public @ResponseBody List<ContactDTO> getContactList(){
        return contactService.readContact();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/contactCorrect")
    public @ResponseBody ContactDTO getContact(@RequestParam("id") String id ){
        return contactService.readContact(Integer.parseInt(id));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveContactCorrect")
    public @ResponseBody void saveContactCorrect(@RequestBody ContactDTO contactDTO) throws IOException{
        contactService.updateContact(contactDTO);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveContactCreate")
    public @ResponseBody void saveContactCreate(@RequestBody ContactDTO contactDTO) throws IOException{
        contactService.createContact(contactDTO);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/userList")
    public @ResponseBody List<UserDTO> getUserList(){
        return userService.readUser();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/userCorrect")
    public @ResponseBody UserDTO getUser(@RequestParam("id") String id ){
        return userService.readUser(Integer.parseInt(id));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/roleEnum")
    public @ResponseBody List getRoleEnum(){
        List roleEnum = new ArrayList();
        for(int i = 0; i < RoleEnum.values().length; i++){
            roleEnum.add(RoleEnum.values()[i]);
        }

        return roleEnum;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/contactListForUser")
    public @ResponseBody List<ContactDTO> getContactListForUser(@RequestParam("id") String idUser ){
        return contactService.readContactForUser(Integer.parseInt(idUser));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveUserCorrect")
    public @ResponseBody void saveUserCorrect(@RequestBody UserDTO userDTO) throws IOException{
        userService.updateUser(userDTO);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveUserCreate")
    public @ResponseBody void saveUserCreate(@RequestBody UserDTO userDTO) throws IOException{
        userService.createUser(userDTO);
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
