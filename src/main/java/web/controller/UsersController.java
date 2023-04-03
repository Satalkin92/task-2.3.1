package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import javax.validation.Valid;

@Controller
public class UsersController {

    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String printUsers(ModelMap model) {
        model.addAttribute("users", userService.getUsers());
        System.out.println(userService.getUsers());
        return "usersFindAll";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult br) {
        if (br.hasErrors()) {
            return "newUserForm";
        }
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/new")
    public String newUser(ModelMap model) {
        model.addAttribute("user", new User());
        return "newUserForm";
    }

    @GetMapping("/{id}/get")
    public String getUser(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUser(id));
        return "updateUserForm";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult br, @PathVariable("id") Long id) {
        if (br.hasErrors()) {
            return "updateUserForm";
        }
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String dropUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

}
