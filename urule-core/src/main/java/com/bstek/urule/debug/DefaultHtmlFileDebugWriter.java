package com.bstek.urule.debug;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jacky.gao
 * @since 2017年11月27日
 */
public class DefaultHtmlFileDebugWriter implements DebugWriter{
	private String path;

	public String getConsoleKey(){
//		return debugMessageHolder.generateKey();
		return null;
	}

	/**
	 * 可扩展增加个接口，根据
	 * @param items
	 * @throws IOException
	 */
	@Override
	public void write(String key,List<MessageItem> items) throws IOException{
		if(StringUtils.isBlank(path)){
			return;
		}
		StringBuilder msg=new StringBuilder();
		for(MessageItem item:items){
			msg.append(item.toHtml());
		}
		String fullPath=path+"/"+key+".html";
		StringBuilder sb=new StringBuilder();
		sb.append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>URule调试日志信息</title><body style='font-size:12px'>");
		sb.append(msg.toString());
		sb.append("</body></html>");
		FileOutputStream out=new FileOutputStream(new File(fullPath));
		IOUtils.write(sb.toString(), out);
		out.flush();
		out.close();
	}
	public void setPath(String path) {
		this.path = path;
	}
}
