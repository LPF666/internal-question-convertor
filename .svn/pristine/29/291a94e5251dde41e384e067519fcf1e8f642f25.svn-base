package com.eebbk.internal.question.convertor.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.eebbk.edu.common.util.json.JsonTool;

public class HtmlUtil_bak {

	// 匹配url地址
    private static final String patternSrcStr = "http(\'?|\"?)(.*?)(\'|\"|>|\\))";
    // 匹配css url地址
    private static final String patternUrlStr = "\\s+([^>]*)url\\((.*?)\\)";
    /** 默认小猿地址 */
    private static final String DEFAULT_XIAOYUAN_DOMAIN = "http://solar.fbcontent.cn/apolo-image/api/";
    
	/**
	 * @description 从html中过滤出图片地址
	 * @param content
	 * @return
	 */
	public static List<String> extralImg(String content) 
	{
		Set<String> datas = new HashSet<>();
		if("".equals(content) || content == null)
		{
			return null;
		}
		Pattern patternSrc = Pattern.compile(patternSrcStr,Pattern.CASE_INSENSITIVE);
		Pattern patternUrl = Pattern.compile(patternUrlStr,Pattern.CASE_INSENSITIVE);
        Matcher matcherSrc = patternSrc.matcher(content);
        Matcher matcherUrl = patternUrl.matcher(content);
        while(matcherSrc.find()) 
        {
        	if(StringUtils.isNotEmpty(matcherSrc.group(1)))
        	{        		
        		datas.add("http" + matcherSrc.group(1));
        	}
        	if(StringUtils.isNotEmpty(matcherSrc.group(2)))
        	{        		
        		datas.add("http" + matcherSrc.group(2));
        	}
        }
        while(matcherUrl.find()) 
        {
        	String url = matcherUrl.group(2);
        	// 小猿地址有些没域名，加上默认，如<div style="width:6px;background: url(149178ebff461c4.png) repeat-y; height: 1px;overflow: hidden" muststretch="v"></div>
        	if(! url.startsWith("http"))
        	{
        		url = DEFAULT_XIAOYUAN_DOMAIN + url;
        	}
			datas.add(url);
        }
        List<String> result = new ArrayList<>();
        for(String url : datas) {
        	if(isReplace(url)) {
        		url = url.replace("+", "%2B").replace("amp;", "");
        		result.add(url);
        	}
        }
        return result;
	}
	
	private static String[] suffixs = {"png", "jpg", "jpeg", "mp3", "gif", "svg", "css", "js", "GIF"};
	
	public static boolean isReplace(String url) {
		for(String suffix : suffixs) {
			if(url.endsWith("." + suffix) || url.contains("latext")) {
				return true;
			}
		}
		return false;
	}
	
    public static String delHTMLTag(String htmlStr)
    {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签
        // 去除过滤html标签后的空行
        htmlStr = htmlStr.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "").replaceAll("^((\r\n)|\n)", "").trim().replace("\\n", "").trim();
        htmlStr = htmlStr.replaceAll("\r|\n|\r", "");
        htmlStr = htmlStr.replace("&nbsp;", " ");
        Pattern p = Pattern.compile("\\s+");
        Matcher m = p.matcher(htmlStr);
        htmlStr = m.replaceAll(" ");
        return htmlStr;
    }
    
    public static void main(String[] args) 
    {
		String str = "<html></br> <head></head></br> <body></br>  <p>化简.<br></p> </br>  <p>（1）<img class=\"kfformula\" src=\"http://solar.fbcontent.cn/apolo-image/api/156937a5e3bbe0d.jpg\" style=\"width:280px\">；</p> </br>  <p>（2）<img class=\"kfformula\" src=\"http://solar.fbcontent.cn/apolo-image/api/156937a61014737.jpg\" style=\"width:221.5px\">.</p></br> </body></br></html>";
		System.out.println(JsonTool.toJson(extralImg(str)));
		
	}
    
}
