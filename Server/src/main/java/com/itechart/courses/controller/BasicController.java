package com.itechart.courses.controller;

import com.itechart.courses.dto.ContactDTO;
import com.itechart.courses.dto.ContactSearchDTO;
import com.itechart.courses.dto.DeleteDTO;
import com.itechart.courses.dto.UserDTO;
import com.itechart.courses.dto.LoginDTO;
import com.itechart.courses.enums.RoleEnum;
import com.itechart.courses.service.authorization.AuthorizationService;
import com.itechart.courses.service.contact.ContactService;
import com.itechart.courses.service.user.UserService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/OrderFlowers")
public class BasicController {

    @Autowired
    private AuthorizationService authorization;

    @Autowired
    private ContactService  contactService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/userInfo")
    @ResponseBody
    public LoginDTO currentUserInfo(Authentication authentication){
        List<GrantedAuthority> authority = (List<GrantedAuthority>) authentication.getAuthorities();
        String stringRole = authority.get(0).getAuthority();
        String username = authentication.getName();
        LoginDTO loginDTO = new LoginDTO(RoleEnum.valueOf(stringRole), username);
        return loginDTO;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/authorize")
    public @ResponseBody UserDTO authorization(@RequestBody UserDTO user) throws IOException, JSONException{
        UserDTO userDTO = authorization.execute(user.getLogin(), user.getPassword());
        return userDTO;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/contactList")
    public @ResponseBody List<ContactDTO> getContactList(){
        return contactService.readContact();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contactSearch")
    public @ResponseBody List<ContactDTO> contactSearch(@RequestBody ContactSearchDTO contactSearchDTO){
        return contactService.searchContact(contactSearchDTO);
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
        for(int i = 0; i < RoleEnum.values().length; i++) {
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

    @RequestMapping(method = RequestMethod.POST, value = "/userDelete")
    public @ResponseBody void userDelete(@RequestBody DeleteDTO userId) throws IOException{
        for(int i: userId.getDeleteId()){
            userService.deleteUser(i);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contactDelete")
    public @ResponseBody boolean contactDelete(@RequestBody DeleteDTO contactId) throws IOException{
        boolean delete = true;
        for(int i: contactId.getDeleteId()){
            if(!contactService.deleteContact(i)){
                delete = false;
            }
        }
        return delete;
    }



}
