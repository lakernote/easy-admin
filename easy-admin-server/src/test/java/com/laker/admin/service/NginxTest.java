package com.laker.admin.service;

import com.github.odiszapc.nginxparser.NgxBlock;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxEntry;
import com.github.odiszapc.nginxparser.NgxParam;

import java.io.IOException;
import java.util.List;

public class NginxTest {

    public static void main(String[] args) throws IOException {
        NgxConfig conf = NgxConfig.read("/etc/nginx/nginx.conf");
        NgxParam workers = conf.findParam("worker_processes");       // Ex.1
        workers.getValue(); // "1"
        NgxParam listen = conf.findParam("http", "server", "listen"); // Ex.2
        listen.getValue(); // "8889"
        List<NgxEntry> rtmpServers = conf.findAll(NgxConfig.BLOCK, "rtmp", "server"); // Ex.3
        for (NgxEntry entry : rtmpServers) {
            ((NgxBlock) entry).getName(); // "server"
            ((NgxBlock) entry).findParam("application", "live"); // "on" for the first iter, "off" for the second one
        }
    }
}
