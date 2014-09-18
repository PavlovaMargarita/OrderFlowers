package com.itechart.courses.service.user;

import com.itechart.courses.dao.contact.ContactDAO;
import com.itechart.courses.dao.user.UserDAO;
import com.itechart.courses.dto.UserDTO;
import com.itechart.courses.entity.User;
import com.itechart.courses.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = true)
    UserDAO userDAO;

    @Autowired(required = true)
    ContactDAO contactDAO;

    @Override
    public UserDTO readUser(int id) {
        UserDTO userDTO = null;
        User user = userDAO.readUser(id);
        if(user != null){
            userDTO = userToUserDTO(user);
        }
        return userDTO;
    }

    @Override
    public void createUser(UserDTO userDTO) {
        User user = new User();
        userDTOToUser(userDTO, user);
        userDAO.createUser(user);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User user = userDAO.readUser(userDTO.getId());
        userDTOToUser(userDTO, user);
        userDAO.updateUser(user);
    }


    @Override
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    @Override
    public List readUser() {
        List userDTOList = new ArrayList<UserDTO>();
        List<User> userList = userDAO.readAllUsers();
        for(User user: userList){
            userDTOList.add(userToUserDTO(user));
        }
        return userDTOList;
    }

    @Override
    public List readLogin() {
        List<User> userList = userDAO.readAllUsers();
        List login = new ArrayList();
        for(User user: userList){
            login.add(user.getLogin());
        }
        return login;
    }

    public UserDTO userToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setPassword(user.getPassword());
        userDTO.setLogin(user.getLogin());
        userDTO.setRole(user.getRole());
        userDTO.setSurname(user.getContact().getSurname());
        userDTO.setName(user.getContact().getName());
        userDTO.setPatronymic(user.getContact().getPatronymic());
        userDTO.setIdContact(user.getContact().getId());
        if(user.getRole().equals(RoleEnum.ROLE_ADMIN)){
            userDTO.setRoleRussian("Администратор");
        }
        if(user.getRole().equals(RoleEnum.ROLE_PROCESSING_ORDERS_SPECIALIST)){
            userDTO.setRoleRussian("Специалист по обработке заказов");
        }
        if(user.getRole().equals(RoleEnum.ROLE_RECEIVING_ORDERS_MANAGER)){
            userDTO.setRoleRussian("Менеджер по приему заказа");
        }
        if(user.getRole().equals(RoleEnum.ROLE_SERVICE_DELIVERY_MANAGER)){
            userDTO.setRoleRussian("Менеджер по доставке заказа");
        }
        if(user.getRole().equals(RoleEnum.ROLE_SUPERVISOR)){
            userDTO.setRoleRussian("Супервизор");
        }
        return userDTO;
    }
    public void userDTOToUser(UserDTO userDTO, User user){
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setContact(contactDAO.readContact(userDTO.getIdContact()));
    }
}
