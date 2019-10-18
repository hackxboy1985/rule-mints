package com.bstek.urule.springboot.actions;

import com.bstek.urule.model.ExposeAction;
import org.springframework.stereotype.Component;

@Component("methodTest")
public class ActionAddBlack {

    @ExposeAction("打印内容")
    public void printContent(String username){
        System.out.println("输出: "+username);
    }
}
