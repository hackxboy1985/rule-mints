package com.bstek.urule.springboot;

import com.alibaba.fastjson.JSONObject;
import com.bstek.urule.runtime.KnowledgePackage;
import com.bstek.urule.runtime.KnowledgeSession;
import com.bstek.urule.runtime.KnowledgeSessionFactory;
import com.bstek.urule.runtime.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jacky.gao
 * @since 2019年5月16日
 */
@RestController
public class RuleTest {
	@Autowired
	private KnowledgeService service;

	@RequestMapping("/test")
	@ResponseBody
	public String test() throws Exception{
		//KnowledgePackage knowledgePackage=service.getKnowledge("3dxt-CXB/test");
		KnowledgePackage knowledgePackage=service.getKnowledge("test/com.test");
        KnowledgeSession session= KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);

		HashMap<String,Object> user = new HashMap<>();
		user.put("name","zs");
		user.put("age", 17);
		user.put("auditResult","");
		//user.put("level","");
		session.insert(user);

		session.fireRules();
        //如果是规则流
        //session.startProcess("test2/channel_02");

		session.writeLogFile();
//		List<Object> allFacts = session.getAllFacts();
		Map<String, Object> parameters = session.getParameters();

		return JSONObject.toJSONString(parameters);

//		String s = JSONObject.toJSONString(ruleExecutionResponse.getMatchedRules(),true);
//		return JSONFormat.formatJsonhtml(s);
	}

    @RequestMapping("/testflow")
    @ResponseBody
    public String testflow() throws Exception{
        KnowledgePackage knowledgePackage=service.getKnowledge("test2/channel_02");
        KnowledgeSession session= KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);

        HashMap<String,Object> applyInfo = new HashMap<>();
        applyInfo.put("name","zs");
        applyInfo.put("age", 17);
        applyInfo.put("edu","D");
        applyInfo.put("income","D");
        session.insert(applyInfo);

        HashMap<String,Object> dt = new HashMap<>();
        dt.put("query_cnt_m1", 6);
        dt.put("query_cnt_m3", 17);
        dt.put("query_cnt_m6", 22);
        session.insert(dt);

        //如果是规则流
        session.startProcess("channel_02");

        session.writeLogFile();
//		List<Object> allFacts = session.getAllFacts();
        Map<String, Object> parameters = session.getParameters();

        return JSONObject.toJSONString(parameters);

//		String s = JSONObject.toJSONString(ruleExecutionResponse.getMatchedRules(),true);
//		return JSONFormat.formatJsonhtml(s);
    }
}
