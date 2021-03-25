package com.shuanghan.community.controller;


import com.shuanghan.community.dto.AccessTokenDTO;
import com.shuanghan.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback (@RequestParam(name = "code") String code,
                            @RequestParam(name = "state") String state){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.getClient_id("1969adb2b21d63f47ad7");
        accessTokenDTO.getClient_secret("7215766bfea1b4bf1a7b8a83101de73208e24449");
        accessTokenDTO.getRedirect_uri("http://localhost:8080/callback");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        githubProvider.getAccessToken(accessTokenDTO);

        return "index";
    }
}
