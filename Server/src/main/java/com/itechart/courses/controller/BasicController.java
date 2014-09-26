package com.itechart.courses.controller;

import com.itechart.courses.dto.*;
import com.itechart.courses.entity.Contact;
import com.itechart.courses.enums.OrderStatusEnum;
import com.itechart.courses.enums.RoleEnum;
import com.itechart.courses.service.authorization.AuthorizationService;
import com.itechart.courses.service.contact.ContactService;
import com.itechart.courses.service.contact.ContactServiceImpl;
import com.itechart.courses.service.email.EmailService;
import com.itechart.courses.service.order.OrderService;
import com.itechart.courses.service.role.RoleService;
import com.itechart.courses.service.template.MessageTemplateService;
import com.itechart.courses.service.user.UserService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map;

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

    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageTemplateService messageTemplateService;

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
    public @ResponseBody PageableContactDTO getContactList(@RequestParam("currentPage") int currentPage, @RequestParam("pageRecords") int pageRecords){
        logger.info("User viewed all contacts");
        int firstRecordNumber = firstRecordNumber(currentPage, pageRecords);
        return contactService.readContact(firstRecordNumber, pageRecords);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/contactSearch")
    public @ResponseBody PageableContactDTO contactSearch(@RequestBody ContactSearchDTO contactSearchDTO,
                                                          @RequestParam("currentPage") int currentPage,
                                                          @RequestParam("pageRecords") int pageRecords){
        logger.info("User searched contacts");
        int firstRecordNumber = firstRecordNumber(currentPage, pageRecords);
        PageableContactDTO searchResult = contactService.searchContact(contactSearchDTO, firstRecordNumber, pageRecords);
        return searchResult;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/contactCorrect")
    public @ResponseBody ContactDTO getContact(@RequestParam("id") String id ){
        logger.info("User viewed the contact");
        return contactService.readContact(Integer.parseInt(id));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveContactCorrect")
    public @ResponseBody void saveContactCorrect(@RequestBody ContactDTO contactDTO) throws IOException{
        logger.info("User updated the contact");
        contactService.updateContact(contactDTO);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveContactCreate")
    public @ResponseBody void saveContactCreate(@RequestBody ContactDTO contactDTO) throws IOException{
        logger.info("User created new contact");
        contactService.createContact(contactDTO);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/userList")
    public @ResponseBody PageableUserDTO getUserList(@RequestParam("currentPage") int currentPage, @RequestParam("pageRecords") int pageRecords){
        logger.info("User viewed all users");
        int firstRecordNumber = firstRecordNumber(currentPage, pageRecords);
        return userService.readUser(firstRecordNumber, pageRecords);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/userCorrect")
    public @ResponseBody UserDTO getUser(@RequestParam("id") String id ){
        logger.info("User viewed the user");
        return userService.readUser(Integer.parseInt(id));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/roleEnum")
    public @ResponseBody List getRoleEnum(){
        return roleService.getRoles();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/contactListForUser")
    public @ResponseBody List<ContactDTO> getContactListForUser(@RequestParam("id") String idUser ){
        logger.info("User viewed all contacts");
        return contactService.readContactForUser(Integer.parseInt(idUser));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveUserCorrect")
    public @ResponseBody void saveUserCorrect(@RequestBody UserDTO userDTO) throws IOException{
        logger.info("User updated the user");
        userService.updateUser(userDTO);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveUserCreate")
    public @ResponseBody void saveUserCreate(@RequestBody UserDTO userDTO) throws IOException{
        logger.info("User created new user");
        userService.createUser(userDTO);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/userDelete")
    public @ResponseBody void userDelete(@RequestBody CheckDTO userId) throws IOException{
        logger.info("User deleted users");
        for(int i: userId.getCheckId()){
            userService.deleteUser(i);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contactDelete")
    public @ResponseBody boolean contactDelete(@RequestBody CheckDTO contactId) throws IOException{
        boolean delete = true;
        logger.info("User deleted contacts");
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

    @RequestMapping(method = RequestMethod.GET, value = "/showTemplate")
    public @ResponseBody List<MessageTemplateDTO> showTemplate() throws IOException{
        return messageTemplateService.showTemplate();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/sendEmail")
    public @ResponseBody void sendEmail(@RequestBody EmailDTO emailDTO) throws IOException{
        logger.info("User sent email");
        emailService.sendEmail(emailDTO);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getLogin")
    public @ResponseBody List getLogin() throws IOException{
        return userService.readLogin();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/orderList")
    public @ResponseBody PageableOrderDTO getOrderList(@RequestParam("currentPage") int currentPage,
                                                       @RequestParam("pageRecords") int pageRecords,
                                                       Authentication authentication){
        LoginDTO dto = currentUserInfo(authentication);
        logger.info("User viewed all orders");
        com.itechart.courses.entity.User user = null;
        List<OrderStatusEnum> statusEnums = null;
        PageableOrderDTO orders = null;
        int firstRecordNumber = firstRecordNumber(currentPage, pageRecords);
        switch (dto.getRole()){
            case ROLE_PROCESSING_ORDERS_SPECIALIST:
                user = userService.readUser(dto.getLogin());
                statusEnums = new ArrayList<OrderStatusEnum>(2);
                statusEnums.add(OrderStatusEnum.ADOPTED);
                statusEnums.add(OrderStatusEnum.IN_PROCESSING);
                orders = orderService.getAllOrders(user.getId(), statusEnums, firstRecordNumber, pageRecords);
                break;
            case ROLE_SERVICE_DELIVERY_MANAGER:
                user = userService.readUser(dto.getLogin());
                statusEnums = new ArrayList<OrderStatusEnum>(2);
                statusEnums.add(OrderStatusEnum.READY_FOR_SHIPPING);
                statusEnums.add(OrderStatusEnum.SHIPPING);
                orders = orderService.getAllOrders(user.getId(), statusEnums, firstRecordNumber, pageRecords);
                break;
            case ROLE_SUPERVISOR:
                orders = orderService.getAllOrders(firstRecordNumber, pageRecords);
                break;
            case ROLE_ADMIN:
                orders = orderService.getAllOrders(firstRecordNumber, pageRecords);
                break;
        }
        return orders;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/orderSearch")
    public @ResponseBody List<TableOrderDTO> searchOrder(@RequestBody OrderSearchDTO parameters){
        logger.info("User searched orders");
        return orderService.searchOrders(parameters);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getResolvedOrderState")
    public @ResponseBody Map<OrderStatusEnum, String> getResolvedOrderStatus(@RequestParam("currentState") OrderStatusEnum currentState){
        return orderService.getResolvedOrderStatus(currentState);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/showOrder")
    public @ResponseBody OrderDTO getOrder(@RequestParam("id") String id) {
        logger.info("User viewed the order");
        return orderService.readOrder(Integer.parseInt(id));
    }


    private int firstRecordNumber(int currentPage, int count){
        int firstRecordNumber = (currentPage - 1) * count;
        return firstRecordNumber >= 0 ? firstRecordNumber : 0;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUsersByRole")
    public @ResponseBody HashMap<String, List<PersonDTO>> getUsersByRole(@RequestParam("role")  List<String> role){
        HashMap<String, List<PersonDTO>> map = new HashMap<String, List<PersonDTO>>(role.size());
        for (String temp : role){
            map.put(temp, userService.getUsersByRole(RoleEnum.valueOf(temp)));
        }
        return map;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/getPerson")
    public @ResponseBody List<PersonDTO> getPerson(@RequestParam("term") String term) {
        List<PersonDTO> listPersonDTO = new ArrayList<PersonDTO>();
        ContactSearchDTO contactSearchDTO = new ContactSearchDTO();
        contactSearchDTO.setSurname(term);
        if (!term.trim().isEmpty())
            listPersonDTO = contactService.searchContact(contactSearchDTO);
        return listPersonDTO;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/correctOrder")
    public @ResponseBody void correctOrder(@RequestBody OrderDTO orderDTO, Authentication authentication) throws ParseException {
        logger.info("User updated the order");
        LoginDTO loginDTO = currentUserInfo(authentication);
        com.itechart.courses.entity.User user = userService.readUser(loginDTO.getLogin());
        orderService.updateOrder(orderDTO, user);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createOrder")
    public @ResponseBody void createOrder(@RequestBody OrderDTO orderDTO, Authentication authentication) throws ParseException {
        logger.info("User created new order");
        LoginDTO loginDTO = currentUserInfo(authentication);
        com.itechart.courses.entity.User user = userService.readUser(loginDTO.getLogin());
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(user.getId());
        orderDTO.setReceiveManager(personDTO);
        orderService.createOrder(orderDTO);
    }
}
