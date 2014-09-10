package com.cantellow.lib.dao;

import java.util.List;

import com.cantellow.lib.model.Log;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.core.Identity;

@DAO
public interface LogDAO {

    @SQL("select id, user_name, resource_pattern, resource_id, success, remarks, create_time from log")
    public List<Log> find();

    @SQL("insert into log (user_name, resource_pattern, resource_id, success, remarks) values (:1.userName, :1.resourcePattern, :1.resourceId, :1.success, :1.remarks)")
    public Identity save(Log log);
}
