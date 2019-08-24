package ca.arsenii.plan4me.web;

import ca.arsenii.plan4me.service.PlanService;
import ca.arsenii.plan4me.service.UserService;
import ca.arsenii.plan4me.util.PlansUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {
    @Autowired
    private UserService userService;

    @Autowired
    private PlanService planService;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @PostMapping("/users")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        SecurityUtil.setAuthUserId(userId);
        return "redirect:plans";
    }

    @GetMapping("/plans")
    public String getPlans(Model model) {
        model.addAttribute("plans",
                PlansUtil.getPlans(planService.getAll(SecurityUtil.authUserId())));
        return "plans";
    }
}
