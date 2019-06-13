package com.eebbk.autotool.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.eebbk.autotool.util.constant.ConstantUtil;
import com.eebbk.edu.common.log.LogUtil;
import com.eebbk.edu.common.log.Logger;
import com.eebbk.edu.common.util.exception.BBKException;

/**
 * @项目名称：plug-eebbk-version
 * @类名称：XmlUtil
 * @类描述：pom Xml文件的读取类
 * @创建人：杨一中
 * @创建时间：2016年12月27日 下午9:51:02
 * @company:步步高教育电子有限公司
 */
public class XmlUtil
{
    // private static final String WEB_ROOT = "webapp.root";
    private static final String WEB_ROOT_SUBFIX = ".root";

    private static final Logger LOGGER = LogUtil.getLogger(XmlUtil.class);

    /**
     * @description 读取pom文件
     * @author 杨一中
     * @date 2016年12月27日 下午9:11:09
     * @param filePath
     * @return
     */
    public static Document readXML(String filePath)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        InputSource is = null;
        try
        {
            DocumentBuilder builder = factory.newDocumentBuilder();
            fis = new FileInputStream(filePath);
            isr = new InputStreamReader(fis, ConstantUtil.VER_ENCODE);
            br = new BufferedReader(isr);
            is = new InputSource(br);
            Document document = builder.parse(is);
            return document;
        }
        catch (Exception e)
        {
            LOGGER.error("创建pom解析失败." + e.getMessage() + ".path:" + filePath);
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (Exception e)
                {
                    LOGGER.error("创建pom解析失败." + e.getMessage() + ".path:" + filePath);
                }
            }
            if (null != isr)
            {
                try
                {
                    isr.close();
                }
                catch (Exception e)
                {
                    LOGGER.error("创建pom解析失败." + e.getMessage() + ".path:" + filePath);
                }
            }
            if (null != fis)
            {
                try
                {
                    fis.close();
                }
                catch (Exception e)
                {
                    LOGGER.error("创建pom解析失败." + e.getMessage() + ".path:" + filePath);
                }
            }
        }
        return null;
    }

    /**
     * @description 写入pom文件
     * @author 杨一中
     * @date 2016年12月27日 下午9:11:29
     * @param document
     * @param pomFile
     * @return
     */
    public static boolean writePomFile(Document document, String pomFile)
    {
        try
        {
            document.normalize();

            /** 将document中的内容写入文件中 */
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, ConstantUtil.VER_ENCODE);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // 编码
            DOMSource source = new DOMSource(document);
            PrintWriter pw = new PrintWriter(pomFile, ConstantUtil.VER_ENCODE);
            StreamResult result = new StreamResult(pw);
            transformer.transform(source, result);
            return true;
        }
        catch (Exception e)
        {
            LOGGER.error("创建pom解析失败." + e.getMessage() + ".path:" + pomFile);
        }
        return false;
    }

    /**
     * @description 根据版本规范中的版本信息，更新pom的doc文件内容
     * @author 杨一中
     * @date 2016年12月27日 下午9:49:59
     * @param doc
     * @param versionInfo
     */
    public static void updatePom(Document doc, String projectName)
    {
        Node root = doc.getDocumentElement();
        /** 如果root有子元素 */
        if (root.hasChildNodes())
        {
            NodeList ftpnodes = root.getChildNodes();
            /** 循环取得ftpnodes所有节点 */
            for (int i = 0; i < ftpnodes.getLength(); i++)
            {
                Node node = ftpnodes.item(i);
                if ("artifactId".equals(node.getNodeName()))
                {
                    node.setTextContent(projectName);
                }
                else if ("name".equals(node.getNodeName()))
                {
                    node.setTextContent(projectName);
                }
                else if ("description".equals(node.getNodeName()))
                {
                    node.setTextContent(projectName);
                }

                if (node.hasChildNodes())
                {
                    Node childeNode = null;
                    for (int j = 0; j < node.getChildNodes().getLength(); j++)
                    {
                        childeNode = node.getChildNodes().item(j);
                        if ("finalName".equals(childeNode.getNodeName()))
                        {
                            childeNode.setTextContent(projectName);
                        }
                    }
                }
            }
        }
    }

    /**
     * @description 根据版本规范中的版本信息，更新web.xml文件内容
     * @author 杨一中
     * @date 2017年2月20日 上午11:48:12
     * @param doc
     * @param projectName
     */
    public static void updateWebXml(Document doc, String projectName)
    {
        Node root = doc.getDocumentElement();

        updateServletName(root, projectName);

        updateWebAppRootKey(root, projectName);
    }

    /**
     * 更新webAppRootKey
     * @param root 根节点
     * @param projectName 项目名
     * @exception/throws [异常类型] [异常说明](可选)
     * @author panjianlin
     * @date 2017年10月11日 上午10:08:49
     * @since 1.0.0
     */
    private static void updateWebAppRootKey(Node root, String projectName)
    {
        setWebAppRootKey(root, projectName);
    }

    private static void setWebAppRootKey(Node root, String projectName)
    {
        Node WebAppRootValueNode = getWebAppRootValueNode(root);
        WebAppRootValueNode.setTextContent(projectName + WEB_ROOT_SUBFIX);
    }

    public static String getWebAppRootValue(String devPath)
    {
        String webXmlFile = devPath + File.separator + "src" + File.separator + "main" + File.separator + "webapp"
                + File.separator + "WEB-INF" + File.separator + "web.xml";

        Document webXmlDoc = readXML(webXmlFile);

        Node root = webXmlDoc.getDocumentElement();

        return getWebAppRootValueNode(root).getTextContent();
    }

    private static Node getWebAppRootValueNode(Node root)
    {
        /** 如果root有子元素 */
        if (root.hasChildNodes())
        {
            NodeList ftpnodes = root.getChildNodes();
            /** 循环取得ftpnodes所有节点 */
            for (int i = 0; i < ftpnodes.getLength(); i++)
            {
                Node node = ftpnodes.item(i);
                if ("context-param".equals(node.getNodeName()))
                {
                    Node contextParam = node;
                    if (contextParam.hasChildNodes())
                    {
                        Node childeNode = null;
                        for (int j = 0; j < contextParam.getChildNodes().getLength(); j++)
                        {
                            childeNode = contextParam.getChildNodes().item(j);
                            if ("param-name".equals(childeNode.getNodeName())
                                    && "webAppRootKey".equals(childeNode.getTextContent()))
                            {
                                if (contextParam.hasChildNodes())
                                {
                                    Node contextParamChildeNode = null;
                                    for (int k = 0; k < contextParam.getChildNodes().getLength(); k++)
                                    {
                                        contextParamChildeNode = contextParam.getChildNodes().item(k);
                                        if ("param-value".equals(contextParamChildeNode.getNodeName()))
                                        {
                                            return contextParamChildeNode;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        throw new BBKException("web.xml下没有找到webAppRootKey或value节点");
    }

    /**
     * 更新servletName
     * @param root 根节点
     * @param projectName 项目名
     * @exception/throws [异常类型] [异常说明](可选)
     * @author panjianlin
     * @date 2017年10月11日 上午10:07:37
     * @since 1.0.0
     */
    private static void updateServletName(Node root, String projectName)
    {
        /** 如果root有子元素 */
        if (root.hasChildNodes())
        {
            NodeList ftpnodes = root.getChildNodes();
            /** 循环取得ftpnodes所有节点 */
            for (int i = 0; i < ftpnodes.getLength(); i++)
            {
                Node node = ftpnodes.item(i);
                if (node.hasChildNodes())
                {
                    Node childeNode = null;
                    for (int j = 0; j < node.getChildNodes().getLength(); j++)
                    {
                        childeNode = node.getChildNodes().item(j);
                        if ("servlet-name".equals(childeNode.getNodeName()))
                        {
                            childeNode.setTextContent(projectName);
                        }
                    }
                }
            }
        }
    }

    public static void readReplaceFile(String devPath, String projectName)
    {
        // 更新pom文件
        String pomFile = devPath + File.separator + "pom.xml";
        Document pomDoc = readXML(pomFile);
        updatePom(pomDoc, projectName);
        writePomFile(pomDoc, pomFile);

        // 更新properties文件
        PropertyUtil.updateProperties(devPath, projectName);

        // 更新web.xml文件
        String webXmlFile = devPath + File.separator + "src" + File.separator + "main" + File.separator + "webapp"
                + File.separator + "WEB-INF" + File.separator + "web.xml";
        Document webXmlDoc = readXML(webXmlFile);
        updateWebXml(webXmlDoc, projectName);
        writePomFile(webXmlDoc, webXmlFile);
    }

}
