package com.eebbk.internal.question.convertor.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.eebbk.edu.common.util.json.JsonTool;

public class HtmlUtil {

//	// 匹配url地址
//    private static final String patternSrcStr = "http(\'?|\"?)(.*?)(\'|\"|>|\\))";
//    // 匹配css url地址
//    private static final String patternUrlStr = "\\s+([^>]*)url\\((.*?)\\)";
    
    // 匹配url地址
    private static final String patternSrcStr = "(\'?|\"?)(.*?)(\'|\"|>|\\))";
    // 匹配css url地址
//    private static final String patternUrlStr = "\\s+([^>]*)\\((.*?)\\)";
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
//		Pattern patternUrl = Pattern.compile(patternUrlStr,Pattern.CASE_INSENSITIVE);
        Matcher matcherSrc = patternSrc.matcher(content);
//        Matcher matcherUrl = patternUrl.matcher(content);
        while(matcherSrc.find()) 
        {
        	if(StringUtils.isNotEmpty(matcherSrc.group(1)))
        	{        		
        		datas.add( matcherSrc.group(1));
        	}
        	if(StringUtils.isNotEmpty(matcherSrc.group(2)))
        	{        		
        		datas.add(matcherSrc.group(2));
        	}
        }
//        while(matcherUrl.find()) 
//        {
//        	String url = matcherUrl.group(2);
//        	// 小猿地址有些没域名，加上默认，如<div style="width:6px;background: url(149178ebff461c4.png) repeat-y; height: 1px;overflow: hidden" muststretch="v"></div>
//        	if(! url.startsWith("http"))
//        	{
//        		url = DEFAULT_XIAOYUAN_DOMAIN + url;
//        	}
//			datas.add(url);
//        }
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
		String a="<p>Life used to be fun for teenagers in Britain. They used to have<img data-cke-saved-src=\"/zximages/zhixue/wordToHtml/bjcssp_0eb43c53-e464-4f8b-951a-98801226b9df.png\" src=\"/zximages/img/question/addQuestion/642/2083a301-a41e-4458-bf14-8ec193215f7a.png\">to spend, and free time to while away (消磨) and meet in teenage coffee bars. But for many young people, life is <u></u><img data-cke-saved-src=\"/zximages/zhixue/wordToHtml/bjcssp_272c4735-3629-4720-aae8-7a3e7897e917.png\" src=\"/zximages/img/question/addQuestion/355/94d9fb35-3774-4f09-ba92-3d3da381fa55.png\"><u></u>  now. <u></u><img data-cke-saved-src=\"/zximages/zhixue/wordToHtml/bjcssp_da1e7cf7-1e12-48be-8d05-161e1b16aee0.png\" src=\"/zximages/img/question/addQuestion/19/25dbc50b-9d69-4510-984b-20c5978885f6.png\"><u></u> are difficult to find. There’s not so much money around. Things are more<img data-cke-saved-src=\"/zximages/zhixue/wordToHtml/bjcssp_a9cb8d1f-0a07-4720-8e34-bf2bf3480b8b.png\" src=\"/zximages/img/question/addQuestion/148/a83a2fac-0489-4f88-8edc-12ae0058873c.png\"><u></u>, and it’s hard to find a place to live in. Teachers say that students work harder than they used to. They are more interested in <u></u><img data-cke-saved-src=\"/zximages/zhixue/wordToHtml/bjcssp_732a96a9-047f-475f-a3af-168261281526.png\" src=\"/zximages/img/question/addQuestion/114/6e2b9486-c483-4111-be63-9e5142e9e873.png\"><u> </u> exams. They know that good exam<img data-cke-saved-src=\"/zximages/zhixue/wordToHtml/bjcssp_e4106604-0932-4755-beef-d8510312d3ea.png\" src=\"/zximages/img/question/addQuestion/958/fb94f4c1-83cf-49ee-a2d7-60e164d100ce.png\"> may bring them better jobs.</p>"; 
		String b="<div style=\"background: url('/upimages/images/part/8652L.png') no-repeat; width:8px; height:14px;float:left;overflow:hidden\"></div>";
		String out="<p class=\"question_option\">A.<img alt=\"\" src=\"/zximages/question/2018/6/11/9/31/42114eaf-7bff-4522-8e48-ec015ffacc0c.png\" style=\"vertical-align:middle\" w=\"107px\" h=\"182px\" />&nbsp;&nbsp;B.<img alt=\"\" src=\"/zximages/question/2018/6/11/9/31/d3d3a4f8-371d-43e9-bb26-b80af4f9ffbd.png\" style=\"vertical-align:middle\" w=\"122px\" h=\"184px\" />&nbsp;&nbsp;C.<img alt=\"\" src=\"/zximages/question/2018/6/11/9/31/44ff98aa-097b-4996-aa01-8cac7e9b5bc3.png\" style=\"vertical-align:middle\" w=\"115px\" h=\"181px\" />&nbsp;&nbsp;D.<img alt=\"\" src=\"/zximages/question/2018/6/11/9/31/02b281fd-97df-4572-84f2-259a669405fd.png\" style=\"vertical-align:middle/\" w=\"107px\" h=\"175px\" />&nbsp;&nbsp;</p>";
//		String out2="<div> 实验室中用如图所示的装置进行甲烷与氯气在光照下反应的实验。 <br /><img alt=\"\" src=\"/zximages/question/2018/6/11/9/31/0e13d9e4-28a0-463f-abf2-0317e6269301.png\" style=\"vertical-align:middle" w="172px" h="157px" /> <br />光照下反应一段时间后，下列装置示意图中能正确反映实验现象的是\(\rm{(}\)　　\(\rm{)}\) </div> ";
		
		String imggg="<img align=\"middle\" class=\"Wirisformula\" "
		+"src=\"/zximages/question/addQuestion/787/f02135e4-e766-4c72-b850-fdf494a84129.png\" "
		+"style=\"vertical-align:middle\"  name=\"lpf\"/>";
		System.out.println(JsonTool.toJson(extralImg(imggg)));
//		System.out.println(JsonTool.toJson(extralImg(b)));
		
	}
    
}
