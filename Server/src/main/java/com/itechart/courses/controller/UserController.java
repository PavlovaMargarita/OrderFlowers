package com.itechart.courses.controller;

import com.itechart.courses.dto.*;
import com.itechart.courses.enums.RoleEnum;
import com.itechart.courses.exception.DatabaseException;
import com.itechart.courses.exception.IllegalInputDataException;
import com.itechart.courses.exception.NotAuthorizedException;
import com.itechart.courses.service.role.RoleService;
import com.itechart.courses.service.user.UserService;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;



@Controller
@RequestMapping("/OrderFlowers/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RoleService roleService;

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

    @RequestMapping(method = RequestMethod.GET, value = "/userList")
    public @ResponseBody
    PageableUserDTO getUserList(@RequestParam("currentPage") int currentPage, @RequestParam("pageRecords") int pageRecords){
        logger.info("User viewed all users");
        PageableUserDTO result = null;
        try {
            int firstRecordNumber = firstRecordNumber(currentPage, pageRecords);
            result = userService.readUser(firstRecordNumber, pageRecords);
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (IllegalArgumentException e){
            throw new IllegalInputDataException();
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/userCorrect")
    public @ResponseBody
    UserDTO getUser(@RequestParam("id") String id ){
        logger.info("User viewed the user");
        UserDTO result = null;
        try {
            result = userService.readUser(Integer.parseInt(id));
        }
        catch (NumberFormatException e){
            throw new IllegalInputDataException();
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveUserCorrect")
    public @ResponseBody void saveUserCorrect(@RequestBody UserDTO userDTO){
        logger.info("User updated the user");
        try {
            userService.updateUser(userDTO);
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (IllegalArgumentException e){
            throw new IllegalInputDataException();
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveUserCreate")
    public @ResponseBody void saveUserCreate(@RequestBody UserDTO userDTO) {
        logger.info("User created new user");
        try {
            userService.createUser(userDTO);
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (IllegalArgumentException e){
            throw new IllegalInputDataException();
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/userDelete")
    public @ResponseBody void userDelete(@RequestBody CheckDTO userId) throws IOException {
        logger.info("User deleted users");
        try {
            for(int i: userId.getCheckId()){
                userService.deleteUser(i);
            }
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUsersByRole")
    public @ResponseBody
    HashMap<String, List<PersonDTO>> getUsersByRole(@RequestParam("role")  List<String> role){
        HashMap<String, List<PersonDTO>> map = null;
        try {
            map = new HashMap<String, List<PersonDTO>>(role.size());
            for (String temp : role){
                map.put(temp, userService.getUsersByRole(RoleEnum.valueOf(temp)));
            }
        }
        catch (IllegalArgumentException e){
            throw new IllegalInputDataException();
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
        return map;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/roleEnum")
    public @ResponseBody List getRoleEnum(){
        List result = null;
        try {
            result = roleService.getRoles();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getLogin")
    public @ResponseBody List getLogin() {
        List result = null;
        try {
            result = userService.readLogin();
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
        return result;
    }

    private int firstRecordNumber(int currentPage, int count){
        int firstRecordNumber = (currentPage - 1) * count;
        return firstRecordNumber >= 0 ? firstRecordNumber : 0;
    }
}
