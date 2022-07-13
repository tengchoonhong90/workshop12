package com.visa.workshop12.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.visa.workshop12.exception.RandomNumberException;
import com.visa.workshop12.model.Generate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class GenerateController {
    private Logger logger = LoggerFactory.getLogger(GenerateController.class);

    //root path
    //define our main page that forward to the generatePage(form)
    @GetMapping("/")
    public String showGenerateNumForm(Model model) {
        logger.info("-- showGenerateNumForm --");

        //Init an empty generate object that carries an int - x number to be generated
        Generate genObj = new Generate();
        //default number shown to 1
        genObj.setNumberVal(0);
        //pass it to the view as th:obj
        model.addAttribute("generateObj", genObj);
        return "generatePage";
    }

    @PostMapping("/generate")
    public String generateNumbers(@ModelAttribute Generate generate, Model model){
        int genNo = 31;
        String[] imgNumbers = new String[genNo];
        int numberRandomNum = generate.getNumberVal();
        logger.info("from the text field: " + numberRandomNum);

        if (numberRandomNum > 30 || numberRandomNum < 0)
            throw new RandomNumberException();

        for(int i=0; i < genNo; i++){
            imgNumbers[i] = "number" + i + ".jpg";
        }

        // logger.info("arr > " + imgNumbers);

        List<String> selectedImg = new ArrayList<String>();
        Random random = new Random();
        Set<Integer> uniqueGenResult= new LinkedHashSet<Integer>();
        while (uniqueGenResult.size() < numberRandomNum) {
            Integer resultOfRandNumber = random.nextInt(genNo);
            uniqueGenResult.add(resultOfRandNumber);
        }

        Iterator<Integer> it = uniqueGenResult.iterator();
        Integer currElem = null;
        while(it.hasNext()) {
            currElem = it.next();
            selectedImg.add(imgNumbers[currElem.intValue()]);
        }

        model.addAttribute("randNoResult", selectedImg.toArray());
        model.addAttribute("numberRandomNum", numberRandomNum);

        return "result";
    }
}
