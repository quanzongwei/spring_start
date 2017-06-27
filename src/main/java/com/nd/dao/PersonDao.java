package com.nd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by quanzongwei(207127) on 2017/6/27 0027.
 */
@Repository
public class PersonDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createPersonTable() {
        String sql = "create table if not exists person(`id` int(11), `name` varchar(64))engine=innodb";
        jdbcTemplate.execute(sql);
    }

    public void add() {
        String sql = "insert into person(`id`,name) values(1,'cat')";// success
        jdbcTemplate.execute(sql);
    }

}
