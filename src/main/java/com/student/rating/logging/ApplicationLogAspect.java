package com.student.rating.logging;

import com.student.rating.dto.ResultRatingDTO;
import com.student.rating.entity.Student;
import com.student.rating.entity.log.ApplicationLog;
import com.student.rating.entity.log.Colour;
import com.student.rating.entity.log.LogType;
import com.student.rating.exception.DuplicateRatingException;
import com.student.rating.service.ApplicationLogService;
import com.student.rating.service.StudentService;
import com.student.rating.utils.IpAddressUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static java.util.Objects.nonNull;


/**
 * @since 1.0
 */

@Aspect
@Component
public class ApplicationLogAspect {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationLogAspect.class);

    private final StudentService studentService;
    private final IpAddressUtils ipAddressUtils;
    private final ApplicationLogService applicationLogService;

    @Autowired
    public ApplicationLogAspect(StudentService studentService, IpAddressUtils ipAddressUtils, ApplicationLogService applicationLogService) {
        this.studentService = studentService;
        this.ipAddressUtils = ipAddressUtils;
        this.applicationLogService = applicationLogService;
    }

    @Pointcut("@annotation(logged)")
    public void callLoggingAt(Logged logged) {
        // standard pointcut fo @Logged
    }

    @Around(value = "callLoggingAt(logged)", argNames = "joinPoint,logged")
    @SuppressWarnings("unchecked")
    public Object logSuccess(ProceedingJoinPoint joinPoint, Logged logged) throws Throwable {
        LOG.trace("In login success [{}]", logged.value());
        Object proceed = null;
        Colour colour = Colour.GREEN;
        boolean resultSuccess = true;
        ApplicationLog log;
        String description;
        if (logged.value() == LogType.LOGIN) {
            HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
            Object thirdArg = joinPoint.getArgs()[2];
            if (thirdArg instanceof Authentication) {
                Authentication authentication = (Authentication) thirdArg;
                proceed = joinPoint.proceed();
                description = String.format(logged.value().getMessage(), joinPoint, logged.value(), proceed);
                Student student = studentService.findByUsername(authentication.getName());
                if (nonNull(student)) {
                    log = new ApplicationLog(logged.value(), description, ipAddressUtils.getClientIpAddr(request), LocalDateTime.now(), authentication.getName(), colour, resultSuccess);
                    applicationLogService.saveSystemLog(log);
                }
            } else if (thirdArg instanceof AuthenticationException) {
                resultSuccess = false;
                colour = Colour.RED;
                String username = request.getParameter("username");
                proceed = joinPoint.proceed();
                description = String.format(logged.value().getFailMessage(), joinPoint, logged.value(), proceed);
                log = new ApplicationLog(logged.value(), description, ipAddressUtils.getClientIpAddr(request), LocalDateTime.now(), username, colour, resultSuccess);
                applicationLogService.saveSystemLog(log);
            }
        } else if (logged.value() == LogType.SIGN_UP) {
            //TODO implement it in ver 1.1
            proceed = joinPoint.proceed();
            LOG.debug("{}",proceed);
        } else if (logged.value() == LogType.LOGOUT) {
            HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
            Authentication authentication = (Authentication) joinPoint.getArgs()[2];
            proceed = joinPoint.proceed();
            description = String.format(logged.value().getMessage(), joinPoint, logged.value(), proceed);
            log = new ApplicationLog(logged.value(), description, ipAddressUtils.getClientIpAddr(request), LocalDateTime.now(), authentication.getName(), colour, resultSuccess);
            applicationLogService.saveSystemLog(log);
        } else if (logged.value() == LogType.RATING_FILL) {
            Student student = studentService.getCurrentUser();
            try {
                proceed = joinPoint.proceed();
                ResponseEntity<ResultRatingDTO> result = (ResponseEntity<ResultRatingDTO>) proceed;
                if (result.getStatusCode() == HttpStatus.OK) {
                    description = String.format(logged.value().getMessage(), joinPoint, logged.value(), proceed);
                    log = new ApplicationLog(logged.value(), description, ipAddressUtils.getClientIpAddr(), LocalDateTime.now(), student.getUsername(), colour, resultSuccess);
                    applicationLogService.saveSystemLog(log);
                }
            } catch (DuplicateRatingException ex) {
                colour = Colour.RED;
                resultSuccess = false;
                description = String.format(logged.value().getFailMessage(), joinPoint, logged.value(), proceed);
                log = new ApplicationLog(logged.value(), description, ipAddressUtils.getClientIpAddr(), LocalDateTime.now(), student.getUsername(), colour, resultSuccess);
                applicationLogService.saveSystemLog(log);
            }
        }
        return proceed;
    }
}
