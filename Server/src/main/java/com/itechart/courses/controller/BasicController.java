package com.itechart.courses.controller;

import com.itechart.courses.dto.*;
import com.itechart.courses.enums.RoleEnum;
import com.itechart.courses.service.authorization.AuthorizationService;
import com.itechart.courses.service.contact.ContactService;
import com.itechart.courses.service.email.EmailService;
import com.itechart.courses.service.role.RoleService;
import com.itechart.courses.service.user.UserService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(BasicController.class);

    @Autowired
    private AuthorizationService authorization;

    @Autowired
    private ContactService  contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RoleService roleService;

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
        return roleService.getRoles();
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
    public @ResponseBody void userDelete(@RequestBody CheckDTO userId) throws IOException{
        for(int i: userId.getCheckId()){
            userService.deleteUser(i);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contactDelete")
    public @ResponseBody boolean contactDelete(@RequestBody CheckDTO contactId) throws IOException{
        boolean delete = true;
        for(int i: contactId.getCheckId()){
            if(!contactService.deleteContact(i)){
                delete = false;
            }
        }
        return delete;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/showEmail")
    public @ResponseBody List<String> showEmail(@RequestParam("checkId") ArrayList<Integer> contactId) throws IOException{
        List<String> listEmail = new ArrayList<String>();
        ContactDTO contact;
        for(int i: contactId){
            contact = contactService.readContact(i);
            if (contact.getEmail() != null)
                listEmail.add(contact.getEmail());
        }
        return listEmail;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/sendEmail")
    public @ResponseBody void sendEmail(@RequestBody EmailDTO emailDTO) throws IOException{
        emailService.sendEmail(emailDTO);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getLogin")
    public @ResponseBody List getLogin() throws IOException{
        return userService.readLogin();
    }
}
