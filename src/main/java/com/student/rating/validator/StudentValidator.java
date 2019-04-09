package com.student.rating.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.student.rating.dto.UserDTO;
import com.student.rating.entity.Student;

import java.util.regex.Pattern;

@Component
public class StudentValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Student.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        UserDTO student = (UserDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "course", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");

        if(!Pattern.compile("[1-5]").matcher(student.getCourse()).find()){
            errors.rejectValue("course","InvalidCourse");
        }

        if (!student.getPhone().isEmpty() && !Pattern.compile("^3?8?(0\\d{9})$").matcher(student.getPhone()).find()) {
            errors.rejectValue("phone", "InvalidPhoneNumber");
        }

        if (student.getPassword().length() <= 5 || student.getPassword().length() > 32) {
            errors.rejectValue("password", "InvalidPasswordSize");
            return;
        }

        if (!student.getPassword().equals(student.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "DifferentPassword");
        }

    }
}