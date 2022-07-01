package br.com.letscode.matheus.authenticate.controler;

import br.com.letscode.matheus.authenticate.repositories.UserRepository;
import br.com.letscode.matheus.authenticate.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private static final String SECRET_KEY = "eyJhbGciOiJIUzUxMiJ9.ew0KICAic3ViIjogIjEyMzQ1Njc4OTAiLA0KICAibmFtZSI6ICJBbmlzaCBOYXRoIiwNCiAgImlhdCI6IDE1MTYyMzkwMjINCn0.CRBkfm1no-OCnHcNGTOBP4nf6CyvzuLyOwCeS0gpJlppZXpAugPbaZTtmyBegL0SfSJSk_ZyheBjib7Hol-VYw";

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String login(@RequestParam("user") String email, @RequestParam("password") String pwd) {

        userService.verifyUser(email,pwd);

        return getJWTToken(email);
    }

    @RequestMapping(value = "/check-token", method = RequestMethod.GET)
    public Boolean checkToken(){
        return true;
    }

    private String getJWTToken(String username) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes()).compact();

        return "Bearer " + token;
    }
}
