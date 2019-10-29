package com.student.rating.controller;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * This is custom error handler instead of default spring boot while label page
 *
 * @since 1.0
 */
@Controller
public class CustomErrorController implements ErrorController {

	@GetMapping(value = "/error")
	public ModelAndView errors(HttpServletRequest request) {
		String pageURL;
		ModelAndView errorPage = new ModelAndView();

		int httpErrorCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

		if(Objects.equals(httpErrorCode, FORBIDDEN.value())) {
			pageURL = "error403";
			errorPage = new ModelAndView(pageURL);
		} else if(Objects.equals(httpErrorCode, NOT_FOUND.value())) {
			pageURL = "error404";
			errorPage = new ModelAndView(pageURL);
			errorPage.addObject("msg", "У вас недостатньо прав для перегляду цієї сторіки!");
		}

		return errorPage;
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
