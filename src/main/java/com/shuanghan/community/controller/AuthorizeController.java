package com.shuanghan.community.controller;


import com.shuanghan.community.dto.AccessTokenDTO;
import com.shuanghan.community.dto.GithubUser;
import com.shuanghan.community.mapper.UserMapper;
import com.shuanghan.community.model.User;
import com.shuanghan.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clienId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback (@RequestParam(name = "code") String code,
                            @RequestParam(name = "state") String state,
                            HttpServletRequest request
                            ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clienId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser.getName());
        if(githubUser!=null){
            //登录成功,写cookie和session
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.Insert(user);
            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }
        else {
            //登录失败重新登录
            return "redirect:/";
        }
    }
}
