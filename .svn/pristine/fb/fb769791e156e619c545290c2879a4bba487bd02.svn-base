package com.eebbk.internal.question.convertor.util;

public class CountLimitById {
	public static void main(String[] args) {
			/**
			 0-100   0-20 20-40 40-60 
			 */
			Integer serviceNum=10;//节点数
			Integer ThreadNum=50;//节点数
			Integer sumCounts=15000000;//处理数据总量
			Integer handerStartIndex=null;
			Integer handerDataMaxIndex=null;
			Integer handerCountTread=sumCounts/serviceNum/ThreadNum;
			if (handerCountTread==null) {
				System.out.println("error:handerCountTread====");
				return;
			}else{
				System.out.println("sucess:handerCount===="+handerCountTread);
			}
			Integer handerStartIndexPre=0;
			Integer handerDataMaxIndexPre=0;
			System.out.println(handerCountTread);
			//10000
			StringBuffer resultBf = new StringBuffer("");
			for (int i = 1; i <=serviceNum; i++) {
				resultBf.append("#service-").append(i).append("每个线程跑5W的数据，跑30个线程").append("\n");
				for (int j = 1; j <= ThreadNum; j++) {
					resultBf.append("	Thread--").append(j).append("\n");
					handerStartIndex=handerStartIndexPre;
					handerDataMaxIndex=handerDataMaxIndexPre+handerCountTread;
					
					
					handerStartIndexPre=handerStartIndex+handerCountTread;
					handerDataMaxIndexPre=handerDataMaxIndexPre+handerCountTread;
				}
			}
	}
}
