package com.hby.community.controller;


import com.hby.community.dto.AccessTokenDTO;
import com.hby.community.dto.GithubUser;
import com.hby.community.provider.GithubProvider;
import org.apache.coyote.http11.filters.SavedRequestInputFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientid;

    @Value("${github.client.secret}")
    private String clientse;

    @Value("${github.redirect.uri}")
    private String reuri;


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(clientid);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret(clientse);
        accessTokenDTO.setRedirect_uri(reuri);
        System.out.println(accessTokenDTO);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println(accessToken);
        GithubUser user= githubProvider.getUser(accessToken);
        System.out.println(user);
        System.out.println(user.getName());
        return "index";
    }


}
