package com.shuanghan.community.controller;

import com.shuanghan.community.dto.PaginationDTO;
import com.shuanghan.community.dto.QuestionDTO;
import com.shuanghan.community.mapper.QuestionMapper;
import com.shuanghan.community.mapper.UserMapper;
import com.shuanghan.community.model.Question;
import com.shuanghan.community.model.User;
import com.shuanghan.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String hello(Model model,
                        @RequestParam(name = "page" ,defaultValue = "1") Integer page,
                        @RequestParam(name = "size" ,defaultValue = "5") Integer size
                        ){

        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";

    }
}
