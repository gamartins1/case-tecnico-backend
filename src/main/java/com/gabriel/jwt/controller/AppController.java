package com.gabriel.jwt.controller;

import java.util.List;

import com.gabriel.jwt.model.CallRecord;
import com.gabriel.jwt.repository.CallRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

    @Autowired
    private CallRecordRepository callRecordRepository;

    @RequestMapping(value = "/viewValidationRecords", method = RequestMethod.GET)
    public ModelAndView redirectConsultaClientesForm() {
        ModelAndView modelAndView = new ModelAndView("validationRecords");
        List <CallRecord> callRecords = this.callRecordRepository.findAll();

        modelAndView.addObject("callRecords", callRecords);
        return modelAndView;
    }
}
