package com.eebbk.internal.question.convertor.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;

public class RegexUtil {
	/**
	 * @author lipf
	 * 判断图片路径属于那种类型
	 * @method getUrlFlags
	 * @param content
	 * @return
	 * @return Map<String,Integer>
	 * @date 2019年5月31日 上午8:34:50
	 */
	public static Map<String,Integer> getUrlFlags(String msg){
		List<String> srcImgList = HtmlUtil.extralImg(msg);
		Map<String, Integer> mapUrlFlag = new HashMap<String,Integer>();
		if (CollectionUtils.isNotEmpty(srcImgList)) {
			for (String content:srcImgList) {
				String patternImg0 = "^/img/.*";
				String patternImg1 = "^img/.*";
				
				String patternTikupic0 = "^/tikupic/.*";
				String patternTikupic1 = "^tikupic/.*";
				
				String patternUpimages0 = "^/upimages/.*";
				String patternUpimages1 = "^upimages/.*";
				
				String patternUpimg0 = "^/upimg/.*";
				String patternUpimg1 = "^upimg/.*";
				
				String patternUpload0 = "^/Upload/.*";
				String patternUpload1 = "^Upload/.*";
				
				String patternUser0 = "^/user/.*";
				String patternUser1 = "^user/.*";
				
				String patternZximages0 = "^/zximages/.*";
				String patternZximages1 = "^zximages/.*";
				
				boolean isMatchImg0 = Pattern.matches(patternImg0, content);
				boolean isMatchImg1 = Pattern.matches(patternImg1, content);
				
				boolean isMatchTikupic0 = Pattern.matches(patternTikupic0, content);
				boolean isMatchTikupic1 = Pattern.matches(patternTikupic1, content);
				
				boolean isMatchUpimages0 = Pattern.matches(patternUpimages0, content);
				boolean isMatchUpimages1 = Pattern.matches(patternUpimages1, content);
				
				boolean isMatchUpimg0 = Pattern.matches(patternUpimg0, content);
				boolean isMatchUpimg1 = Pattern.matches(patternUpimg1, content);
				
				boolean isMatchUpload0 = Pattern.matches(patternUpload0, content);
				boolean isMatchUpload1 = Pattern.matches(patternUpload1, content);
				
				boolean isMatchUser0 = Pattern.matches(patternUser0, content);
				boolean isMatchUser1 = Pattern.matches(patternUser1, content);
				
				boolean isMatchZximages0 = Pattern.matches(patternZximages0, content);
				boolean isMatchZximages1 = Pattern.matches(patternZximages1, content);

				if (isMatchImg0||isMatchImg1) {
					mapUrlFlag.put("urlImgFlag", 1);
				}
				
				if (isMatchTikupic0||isMatchTikupic1) {
					mapUrlFlag.put("urlTikupicFlag", 1);
				}
				if (isMatchUpimages0||isMatchUpimages1) {
					mapUrlFlag.put("urlUpimagesFlag", 1);
				}
				if (isMatchUpimg0||isMatchUpimg1) {
					mapUrlFlag.put("urlUpimgFlag", 1);
				}
				if (isMatchUpload0||isMatchUpload1) {
					mapUrlFlag.put("urlUploadFlag", 1);
				}
				if (isMatchUser0||isMatchUser1) {
					mapUrlFlag.put("urlUserFlag", 1);
				}
				if (isMatchZximages0||isMatchZximages1) {
					mapUrlFlag.put("urlZximagesFlag", 1);
				}
			}
		}
		return mapUrlFlag;
	}
}
