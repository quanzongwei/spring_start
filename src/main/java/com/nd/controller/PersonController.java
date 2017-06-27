package com.nd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nd.dao.PersonDao;
import com.nd.service.PersonService;

/**
 * Created by quanzongwei(207127) on 2017/6/27 0027.
 */
@RestController
@RequestMapping(value = "/students", headers = "Accept=application/json", produces = "application/json; Charset=UTF-8")
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    PersonDao personDao;

    /**
     * 控制层的事务没有生效,就是因为扫描包的问题,建议事务放到非控制层扫描
     * created by quanzongwei
     */
    @Transactional
    @RequestMapping(value = "/say_hi", method = RequestMethod.GET)
    public void sayhi() {
        personService.sayHi();
        // personDao.createPersonTable();
        personDao.add();
        if (true) {
            throw new RuntimeException();
        }
        personDao.add();
        // personService.fun();
    }
}
