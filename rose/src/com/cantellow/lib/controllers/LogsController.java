package com.cantellow.lib.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cantellow.lib.dao.LogDAO;
import com.cantellow.lib.model.Log;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.rest.Get;

@LoginRequired
public class LogsController {

    @Autowired
    private LogDAO logDAO;

    @Get("")
    public String list(final Invocation inv) {
        final List<Log> logs = this.logDAO.find();
        inv.addModel("logs", logs);
        return "logs";
    }
}
