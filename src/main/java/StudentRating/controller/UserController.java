package StudentRating.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Тарас on 19.02.2018.
 */
@Controller
@ComponentScan("StudentRating")
public class UserController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout, String message) {

        if (error != null) {
            model.addAttribute("error", "Пароль или имя пользователя неправильные");
        }

        if (logout != null) {
            model.addAttribute("message", "Успешный выход");
        }

        return "login";
    }
}
