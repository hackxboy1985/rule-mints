package com.bstek.urule.springboot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bstek.urule.model.GeneralInputEntity;
import com.bstek.urule.model.GeneralOutputEntity;
import com.bstek.urule.model.rule.RuleInfo;
import com.bstek.urule.runtime.KnowledgePackage;
import com.bstek.urule.runtime.KnowledgeSession;
import com.bstek.urule.runtime.KnowledgeSessionFactory;
import com.bstek.urule.runtime.response.FlowExecutionResponse;
import com.bstek.urule.runtime.response.RuleExecutionResponse;
import com.bstek.urule.runtime.service.KnowledgeService;
import com.bstek.urule.springboot.utils.JSONFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jacky.gao
 * @since 2019年5月16日
 */
@RestController
public class RuleTest {
	@Autowired
	private KnowledgeService service;

    /**
     * 测试单个规则
     * @return
     * @throws Exception
     */
	@RequestMapping("/test")
	@ResponseBody
	public String testrule() throws Exception{
		//KnowledgePackage knowledgePackage=service.getKnowledge("3dxt-CXB/test");
        //1、获取项目下的知识包
		KnowledgePackage knowledgePackage=service.getKnowledge("test/com.test");
        KnowledgeSession session= KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);

//		HashMap<String,Object> user = new HashMap<>();
        GeneralInputEntity user = new GeneralInputEntity();
		user.put("name","zs");
		user.put("age", 17);
//		user.put("auditResult","");
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

    /**
     * 测试规则流
     * @return
     * @throws Exception
     */
    @RequestMapping("/testflow")
    @ResponseBody
    public String testflow() throws Exception{
        KnowledgePackage knowledgePackage=service.getKnowledge("test2/channel_02");
        KnowledgeSession session= KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);

        GeneralInputEntity applyInfo = new GeneralInputEntity();
        applyInfo.put("name","zs");
        applyInfo.put("age", 17);
        applyInfo.put("edu","C");
        applyInfo.put("income","1000");
        applyInfo.put("query_cnt_m1", 6);
        applyInfo.put("query_cnt_m3", 17);
        applyInfo.put("query_cnt_m6", 22);
        session.insert(applyInfo);

        GeneralOutputEntity outputEntity = new GeneralOutputEntity();
        session.insert(outputEntity);

        //执行规则流
        FlowExecutionResponse flowExecutionResponse = session.startProcess("channel_02");

        //执行结果
        JSONObject result = new JSONObject();
        result.put("nodenames",flowExecutionResponse.getNodeNames());

        //命中规则
        //result.put("flowExecutionResponse",flowExecutionResponse.getFlowExecutionResponses());
        //result.put("ruleExecutionResponse",flowExecutionResponse.getRuleExecutionResponses());
        JSONArray matchedRules = new JSONArray();
        List<RuleExecutionResponse> ruleExecutionResponses = flowExecutionResponse.getRuleExecutionResponses();
        for (RuleExecutionResponse ruleExecutionResponse : ruleExecutionResponses){
            for(RuleInfo rule :ruleExecutionResponse.getMatchedRules()) {
                JSONObject ruleJson = new JSONObject();
                ruleJson.put("name", rule.getName());
                matchedRules.add(ruleJson);
            }
        }
        result.put("matchedRules", matchedRules);


        session.writeLogFile();
//        String logHtml = session.printLogHtml();
//        return logHtml;
//        Map<String, Object> parameters = session.getParameters();
//        return JSONObject.toJSONString(parameters);

        //List<Object> allFacts = session.getAllFacts();
        Map<String, Object> parameters = session.getParameters();
        result.put("result", parameters);
        return JSONFormat.formatJsonhtml(JSONObject.toJSONString(result,true));

//		String s = JSONObject.toJSONString(ruleExecutionResponse.getMatchedRules(),true);
//		return JSONFormat.formatJsonhtml(s);
    }
}
