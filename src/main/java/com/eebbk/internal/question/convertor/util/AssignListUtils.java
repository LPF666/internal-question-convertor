package com.eebbk.internal.question.convertor.util;

import java.util.ArrayList;
import java.util.List;

public class AssignListUtils {
	/**
	 * 将一组数据平均分成n组
	 *
	 * @param source 要分组的数据源
	 * @param n      平均分成n组
	 * @param <T>
	 * @return
	 */
	public static <T> List<List<T>> averageAssign(List<T> source, int n) {
	    List<List<T>> result = new ArrayList<List<T>>();
	    int remainder = source.size() % n;  //(先计算出余数)
	    int number = source.size() / n;  //然后是商
	    int offset = 0;//偏移量
	    for (int i = 0; i < n; i++) {
	        List<T> value = null;
	        if (remainder > 0) {
	            value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
	            remainder--;
	            offset++;
	        } else {
	            value = source.subList(i * number + offset, (i + 1) * number + offset);
	        }
	        result.add(value);
	    }
	    return result;
	}
	
	/**
	 * 将一组数据固定分组，每组n个元素
	 * @param source 要分组的数据源
	 * @param n      每组n个元素
	 * @param <T>
	 * @return
	 */
	public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {

	    if (null == source || source.size() == 0 || n <= 0)
	        return null;
	    List<List<T>> result = new ArrayList<List<T>>();

	    int sourceSize = source.size();
	    int size = (source.size() / n) + 1;
	    for (int i = 0; i < size; i++) {
	        List<T> subset = new ArrayList<T>();
	        for (int j = i * n; j < (i + 1) * n; j++) {
	            if (j < sourceSize) {
	                subset.add(source.get(j));
	            }
	        }
	        result.add(subset);
	    }
	    return result;
	}
	
	public static void main(String[] args) {
		List<String> listString =new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			listString.add(i, "数据"+i);
		}
		List<List<String>> fixedGrouping = fixedGrouping(listString, 100);
		System.out.println("共分为了"+fixedGrouping.size()+"组");
	}
	
}
