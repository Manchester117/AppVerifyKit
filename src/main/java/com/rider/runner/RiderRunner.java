package com.rider.runner;

import com.rider.generator.core.ClassDecompiler;
import com.rider.generator.core.ConvertCodeFile;
import com.rider.generator.xml.XMLFileOperator;
import com.rider.tools.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peng.Zhao on 2016/3/20.
 */
public class RiderRunner {
    private XMLFileOperator xfo = null;
    private ClassDecompiler cd = null;
    private ConvertCodeFile rcf = null;
    public static Logger logger = LogManager.getLogger(RiderRunner.class.getName());

    public void createTestJavaClass() throws Exception {
        this.cd = new ClassDecompiler();
        this.cd.writeClassToPackage();
        this.cd.getTestClassFileName();
        this.cd.decompilerClass();
    }

    public void createTestDriverXML() throws Exception{
        this.xfo = new XMLFileOperator();
        this.xfo.createMultiXML();
    }

    public void convertCode() {
        rcf = new ConvertCodeFile();
        rcf.convertUnicodeFile();
    }

    public void testRunner() {
        // 设定TestNG.xml的列表
        List<String> suitesStrList = Utility.searchTestNGXML();

        // 将TestNG.xml文件装载到XmlSuite里
        XmlSuite suite = new XmlSuite();
        suite.setSuiteFiles(suitesStrList);

        // 创建一个Suite的List
        List<XmlSuite> suitesXmlList = new ArrayList<>();
        suitesXmlList.add(suite);

        // 执行用例
        TestNG testng = new TestNG();
        testng.setXmlSuites(suitesXmlList);
        logger.info("**************************测试开始执行**************************");
        testng.run();
        logger.info("**************************测试执行结束**************************");
    }

    public void replaceReport() {
        Utility.replaceReportJS();
    }

    public void cleanCodeFile() {
        Utility.cleanCodeFile();
    }

    // 测试--main方法
    public static void main(String[] args) throws Exception {
        RiderRunner riderRunner = new RiderRunner();
        riderRunner.createTestJavaClass();
        riderRunner.createTestDriverXML();
        riderRunner.convertCode();
        riderRunner.testRunner();
        riderRunner.replaceReport();
        riderRunner.cleanCodeFile();
    }
}
