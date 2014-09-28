package com.itechart.courses.validation;

import com.itechart.courses.dto.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static boolean validateContact(ContactDTO contactDTO){
        boolean ok = true;
        if(contactDTO.getSurname() == null || !validateName(contactDTO.getSurname()))
            ok = false;
        if(contactDTO.getName() == null || !validateName(contactDTO.getName()))
            ok = false;
        if(contactDTO.getPatronymic() != null && !validatePatronymic(contactDTO.getPatronymic()))
            ok = false;
        if(contactDTO.getEmail() != null && !validateEmail(contactDTO.getEmail()))
            ok = false;
        if(contactDTO.getCity() != null && !validateCity(contactDTO.getCity()))
            ok = false;
        if(contactDTO.getStreet() != null && !validateStreet(contactDTO.getStreet()))
            ok = false;
        return ok;
    }
    private static boolean validateName(String value){
        Pattern pattern = Pattern.compile("^[A-zА-яЁё -]+$");
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()){
            return true;
        } else{
            return false;
        }
    }
    private static boolean validatePatronymic(String value){
        if(value.equals(""))
            return true;
        Pattern pattern = Pattern.compile("^[A-zА-яЁё]+$");
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()){
            return true;
        } else{
            return false;
        }
    }
    private static boolean validateEmail(String value){
        if(value.equals(""))
            return true;
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()){
            return true;
        } else{
            return false;
        }
    }
    private static boolean validateCity(String value){
        if(value.equals(""))
            return true;
        Pattern pattern = Pattern.compile("^[A-zА-яЁё -]+$");
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()){
            return true;
        } else{
            return false;
        }
    }
    private static boolean validateStreet(String value){
        if(value.equals(""))
            return true;
        Pattern pattern = Pattern.compile("^[A-zА-яЁё0-9 -]+$");
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()){
            return true;
        } else{
            return false;
        }
    }
    private static boolean validateInt(String value){
        if(value.equals(""))
            return true;
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()){
            return true;
        } else{
            return false;
        }
    }
    public static boolean validateContactSearch(ContactSearchDTO contactSearchDTO){
        boolean ok = true;
        if(contactSearchDTO.getSurname() != null && !validateName(contactSearchDTO.getSurname()))
            ok = false;
        if(contactSearchDTO.getName() != null && !validateName(contactSearchDTO.getName()))
            ok = false;
        if(contactSearchDTO.getPatronymic() != null && !validatePatronymic(contactSearchDTO.getPatronymic()))
            ok = false;
        if(contactSearchDTO.getCity() != null && !validateCity(contactSearchDTO.getCity()))
            ok = false;
        if(contactSearchDTO.getStreet() != null && !validateStreet(contactSearchDTO.getStreet()))
            ok = false;
        return ok;
    }
    public static boolean validateUser(UserDTO user){
        boolean ok = true;
        if(user.getIdContact() == 0){
            ok = false;
        }
        if(user.getRole().equals("")){
            ok = false;
        }
        if(user.getLogin() == null || user.getLogin().trim().equals("")){
            ok = false;
        }
        if(user.getPassword() == null || user.getLogin().trim().equals("")){
            ok = false;
        }
        return ok;
    }
    public static boolean validateOrderSearch(OrderSearchDTO orderSearchDTO){
        boolean ok = true;
        if (!validatePatronymic(orderSearchDTO.getCustomerSurname()) && orderSearchDTO.getCustomerSurname() != null){
            ok = false;
        }
        if (!validatePatronymic(orderSearchDTO.getRecipientSurname()) && orderSearchDTO.getRecipientSurname() != null){
            ok = false;
        }
        return ok;
    }

    public static boolean validateOrder(OrderDTO orderDTO){
        boolean ok = true;
        if(orderDTO.getCustomer() == null || orderDTO.getCustomer().getId() == null){
            ok = false;
        }
        if(orderDTO.getRecipient() == null || orderDTO.getRecipient().getId() == null){
            ok = false;
        }
        if(orderDTO.getDeliveryManager() == null || orderDTO.getDeliveryManager().getId() == null){
            ok = false;
        }
        if(orderDTO.getHandlerManager() == null || orderDTO.getHandlerManager().getId() == null){
            ok = false;
        }
        if(orderDTO.getOrderDescription() == null || orderDTO.getOrderDescription().trim().equals("")){
            ok = false;
        }
        if(orderDTO.getSum() == null){
            ok = false;
        }
        return ok;
    }
}
