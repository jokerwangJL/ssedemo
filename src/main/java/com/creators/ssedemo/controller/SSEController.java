package com.creators.ssedemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

/**
 * @author wangjialong
 * @date 2019年04月29日 10:31
 * @description SSE 服务端推送数据给http 客户端
 */
@Controller
@RequestMapping("/sse")
@Slf4j
public class SSEController {


    @RequestMapping(value = "/push",produces = "text/event-stream;charset=utf-8")
    @ResponseBody
    public String getStreamData(HttpServletResponse httpServletResponse) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String s= "retry:1000\n";
        s += "data:Testing 1,2,3------- "+new Date() +"\n\n";
        return  s;

    }

    @RequestMapping(value = "/pushImprove")
    @ResponseBody
    public void getStreamDataImprove(HttpServletResponse httpServletResponse) {
        httpServletResponse.setContentType("text/event-stream");
        httpServletResponse.setCharacterEncoding("utf-8");
        PrintWriter pw = null;
        int i = 0;
        while (true) {
            try {
                pw=httpServletResponse.getWriter();
                Thread.sleep(1000L);
                String s = "data:Testing 1,2,3------- "+new Date() +"\n\n";
                System.out.println("执行了while循环"+(++i)+"次");
                pw.write(s);
                if(pw.checkError()) {
                    System.out.println("客户端断开连接");
                    pw.close();
                    return ;
                }
            } catch (IOException | InterruptedException e) {
                if(null != pw) {
                    pw.close();
                }
                e.printStackTrace();

            }
        }

    }




    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String login(){
        System.out.println("1");
        return"sse";
    }
}
