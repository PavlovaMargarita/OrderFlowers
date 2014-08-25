package com.itechart.cources.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

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

    /*@RequestMapping(method = RequestMethod.POST, value = "/autorize")
    public @ResponseBody String autorization(HttpServletRequest request) {
        String login = (String)request.getAttribute("login");
        String password = request.getParameter("password");
        return login + password;
    }*/
}
