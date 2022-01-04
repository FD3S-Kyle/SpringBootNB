package com.example.newboston;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //tells springboot to make it a restcontroller
@RequestMapping("api/hello") //defines the path for the rest endpoint
public class HelloWorldController {
    
    @GetMapping //signifies a get endpoint, it just fetches data, adding ("springboot") appends to api/hello
    public String helloWorld()
    {
        return "Hello this is a rest endpoint";
    } 
}
