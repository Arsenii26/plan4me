package ca.arsenii.plan4me.web;

import ca.arsenii.plan4me.service.PlanService;
import ca.arsenii.plan4me.service.UserService;
import ca.arsenii.plan4me.util.PlansUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {
    @GetMapping("/")
    public String root() {
        return "redirect:plans";
    }

    //    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String getUsers() {
        return "users";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping("/plans")
    public String getPlans() {
        return "plans";
    }
}
