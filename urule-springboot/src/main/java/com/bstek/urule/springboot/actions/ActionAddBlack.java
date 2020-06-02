package com.bstek.urule.springboot.actions;

import com.bstek.urule.model.ExposeAction;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component("methodTest")
public class ActionAddBlack {
//    private Logger log=Logger.getLogger(ActionAddBlack.class.getName());
private static org.slf4j.Logger log= LoggerFactory.getLogger(ActionAddBlack.class);


    @ExposeAction("打印内容")
    public void printContent(String username){
        log.info("执行方法 输出: {}",username);
    }
}
