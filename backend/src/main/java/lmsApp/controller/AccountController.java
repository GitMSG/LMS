package lmsApp.controller;

import javax.validation.Valid;

import lmsApp.authentication.AuthProvider;
import lmsApp.authentication.JwtTokenHandler;
import lmsApp.authentication.UnauthorizedException;
import lmsApp.authentication.UserCreationException;
import lmsApp.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * AccountController
 */
@RestController
public class AccountController {
    @Autowired
    private AuthProvider auth;

    @Autowired
    private JwtTokenHandler tokenHandler;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestBody User user, RedirectAttributes flash) throws UnauthorizedException {
    	System.out.println("in login endpoint");
        if (auth.signIn(user.getEmail(), user.getPassword())) {
            User currentUser = auth.getCurrentUser();
            return tokenHandler.createToken(user.getEmail(), currentUser.getPermission());
        } else {
            throw new UnauthorizedException();
        }
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@Valid @RequestBody User user, BindingResult result) throws UserCreationException {
        if (result.hasErrors()) {
            String errorMessages = "";
            for (ObjectError error : result.getAllErrors()) {
                errorMessages += error.getDefaultMessage() + "\n";
            }
            throw new UserCreationException(errorMessages);
        }
        auth.register(user.getEmail(), user.getPassword(), user.getPermission());
        return "{\"success\":true}";
    }

}