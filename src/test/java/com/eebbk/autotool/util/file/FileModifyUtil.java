package com.eebbk.autotool.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileModifyUtil {
	/**写文件*/
	public static void writeFile(File file, String content)
	{
		if (null == file || !file.exists())
	    {
	        return;
	    }
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			if (null != bw)
			{
				try {
					bw.close();
				} catch (Exception e2) {
					bw = null;
					e2.printStackTrace();
				}
			}
		}
	}
	
	public static void readReplaceFile(File file, ModifyFileInterface modifyFileDo)
	{
		if (null == file || !file.exists())
	    {
	        return;
	    }
		BufferedReader br = null;
		String line = null;
		StringBuilder sb = new StringBuilder();
		
		try {
            // 根据文件路径创建缓冲输入流
            br = new BufferedReader(new FileReader(file));
            // 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
            while ((line = br.readLine()) != null) {
            	modifyFileDo.modifyLine(sb, line);
            	sb.append("\r\n");
                /*// 此处根据实际需要修改某些行的内容
                if (line.startsWith("a")) {
                    buf.append(line).append(" start with a");
                }
                else if (line.startsWith("b")) {
                    buf.append(line).append(" start with b");
                }
                // 如果不用修改, 则按原来的内容回写
                else {
                    buf.append(line);
                }
                buf.append(System.getProperty("line.separator"));*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                }
            }
        }
        
		if (sb.length() > 0)
		{
			writeFile(file, sb.toString());
		}
	}
	
	/**
	 * 
	 * @param file
	 * @param modifyFileDo
	 * @return 将每条sql语句作为一个数组元素返回
	 */
	public static List<String> readSqlFile(File file)
	{
		BufferedReader br = null;
		String line = null;
		StringBuilder sb = new StringBuilder();
		List<String> sqls = new ArrayList<String>();
		try {
            // 根据文件路径创建缓冲输入流
            br = new BufferedReader(new FileReader(file));
            // 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
            while ((line = br.readLine()) != null) {
            	//忽略注释
            	if (line.contains("--"))
            	{
            		continue;
            	}
            	sb.append(line);
            	sb.append("\r\n");
            	//说明条单独的sql语句结束了
            	if (line.contains(";"))
            	{
            		sqls.add(sb.toString());
            		sb = new StringBuilder();
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                }
            }
        }
        
		return sqls;
	}
}
