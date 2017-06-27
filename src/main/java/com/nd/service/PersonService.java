package com.nd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nd.dao.PersonDao;

/**
 * Created by quanzongwei(207127) on 2017/6/27 0027.
 */
@Service
public class PersonService {
    @Autowired
    private PersonDao personDao;

    public void sayHi() {
        System.out.println("hi");
    }

    @Transactional
    public void fun() {
        personDao.add();
        if (true) {
            throw new RuntimeException();
        }
        personDao.add();
    }
}
