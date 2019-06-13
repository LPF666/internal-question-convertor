package com.eebbk.internal.question.convertor.util;

public class HtmlTools {
	public static String convertHtml(String bodyInfo,Integer tableFlag){
		StringBuffer html = new StringBuffer("<html>");
		html=html.append("<head>").append("<meta charset=\"utf-8\">").append("<title></title>");
		Integer flag=1;
//		html=html.append("<style>");
//		html=html.append(".MathJye table{").append("border-collapse: collapse;").append("margin: 0; padding: 0;text-align: center;vertical-align: middle; line-height: normal;font-size: inherit; _font-size: 100%; font-style: normal;font-weight: normal;border: 0; float: none;display: inline-block;zoom: 0;");
//		html=html.append("}");
//		html=html.append(" table.edittable{border-collapse:collapse;text-align:center;margin:2px}table.edittable td,table.edittable th{line-height:30px;padding:5px;white-space:normal;word-break:break-all;border:1px solid #000;vertical-align:middle}table.composition{border-collapse:collapse;text-align:left;margin:2px;width:98%}table.composition td,table.composition th{line-height:30px;white-space:normal;word-break:break-all;border-width:0;vertical-align:middle}table.composition2{border-collapse:collapse;width:auto}table.composition2 td,table.composition2 th{text-align:left;line-height:30px;white-space:normal;word-break:break-all;border:none;border-width:0;vertical-align:middle}");
//		html=html.append("</style>");
		if (flag==tableFlag) {
			html=html.append("<link  href=\"/bbk/css/question.css\" rel=\"stylesheet\"/>");
		}else{
			html=html.append("<link  href=\"http://common-pic-dn.eebbk.net/common-pic/2019/05/14/214635666_52442c8cd8467cc6.css\" rel=\"stylesheet\"/>");
//			html=html.append("<script type=\"text/javascript\" src=\"http://common-pic-dn.eebbk.net/common-pic/2019/04/20/100812659_db8b88c76f9826e7.js\"></script>");
		}
		html=html.append("<script type=\"text/javascript\" async src=\"http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script>");
		html=html.append("</head>");
		html=html.append("<body>");
		html=html.append(bodyInfo);
		html=html.append("</body>");
		html=html.append("</html>");
		return html.toString();
	}
	/**
	 * @author admin
	 * 外包导出静态页面拼接
	 * @method convertHtmlTowaibao
	 * @param bodyInfo
	 * @return
	 * @return String
	 * @date 2019年5月18日 下午4:51:30
	 */
	public static String convertHtmlTowaibao(String bodyInfo){
		StringBuffer html = new StringBuffer("<html>");
		html=html.append("<head>").append("<meta charset=\"utf-8\">").append("<title></title>");
		//bootstrap.min.css
		html=html.append("<link  href=\"http://common-pic-dn.eebbk.net/common-pic/2019/05/18/170017485_2e391b3b2de2df8e.css\" rel=\"stylesheet\"/>");
		//question.css
		html=html.append("<link  href=\"http://common-pic-dn.eebbk.net/common-pic/2019/05/18/195300106_095b42527f4d491f.css\" rel=\"stylesheet\"/>");
		html=html.append("<link  href=\"http://common-pic-dn.eebbk.net/common-pic/2019/05/14/214635666_52442c8cd8467cc6.css\" rel=\"stylesheet\"/>");
		//jquery-1.9.1.min.js
		html=html.append("<script type=\"text/javascript\" src=\"http://common-pic-dn.eebbk.net/common-pic/2019/05/18/170146105_b960160bb0a1ed06.js\"></script>");
		//bootstrap.min.js
		html=html.append("<script type=\"text/javascript\" src=\"http://common-pic-dn.eebbk.net/common-pic/2019/05/18/170239554_265e18a6fd0792cc.js\"></script>");
		html=html.append("<script type=\"text/javascript\" src=\"http://common-pic-dn.eebbk.net/common-pic/2019/04/20/100812659_db8b88c76f9826e7.js\"></script>");
		html=html.append("<script type=\"text/javascript\" async src=\"http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script>");
		html=html.append("</head>");
		html=html.append("<body>");
		html=html.append(bodyInfo);
		html=html.append("</body>");
		html=html.append("</html>");
		return html.toString();
	}

}
