package student_rating.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.joda.time.*;
import student_rating.blocks.Block_1_1;
import student_rating.blocks.Paragraphs;
import student_rating.entity.Block;
import student_rating.entity.Paragraph;
import student_rating.entity.Rating;
import student_rating.entity.Subblock;
import student_rating.service.RatingService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Тарас on 19.02.2018.
 */
@Controller
@ComponentScan("student_rating")
public class UserController {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private RatingService ratingService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Locale locale,Model model, String error,String logout,String message) {

        System.out.println(locale.getDisplayLanguage());
        System.out.println(messageSource.getMessage("locale", new String[]{locale.getDisplayName(locale)}, locale));

        if (error != null) {
            model.addAttribute("error", messageSource.getMessage("error",new String[]{},locale));
        }

        if (logout != null) {
            model.addAttribute("message", "Успешный выход");
        }

        return "login";
    }



    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/getData",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody public Block getData(Block response){
        System.out.println("I ve got "+ response.getName());
/*
        paragraph1.setName(messageSource.getMessage("paraghaph_1_1_1",new String[]{},locale));
        paragraph1.setScore(block_1_1.getParaghaph_1_1_1());
        paragraph1.setName(messageSource.getMessage("paraghaph_1_1_2",new String[]{},locale));
        paragraph1.setScore(block_1_1.getParaghaph_1_1_2());

        paragraphList.add(paragraph1);
        paragraphList.add(paragraph2);

        subblock.setName(messageSource.getMessage("subblock_1.1",new String[]{},locale));
        subblock.setParagraph(paragraphList);
        subblockList.add(subblock);

        block.setName(messageSource.getMessage("block_1",new String[]{},locale));
        block.setSubblock(subblockList);

        try {
                ratingService.add(block);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

        for(String s:paragraphs.getParagrhaps()){
            System.out.println("i`ve got this shit="+s);
        }
        System.out.println(block_1_1.getParaghaph_1_1_1());
        System.out.println(block_1_1.getParaghaph_1_1_2());
        System.out.println(block_1_1.getParaghaph_1_1_3());
        System.out.println(block_1_1.getParaghaph_1_1_4());
        System.out.println(block_1_1.getParaghaph_1_1_5());*/
/*
        for(String s :create){
            System.out.println("ive got"+s);
        }*/


        return response;
    }


    @Secured("ADMIN")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }

}