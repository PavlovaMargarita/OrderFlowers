package com.itechart.courses.validation;

import com.itechart.courses.dto.ContactDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Margarita on 25.09.2014.
 */
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
}
