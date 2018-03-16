package me.lab.parser;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company:
 * <p/>
 *
 * @author darrenfu
 * @version 1.0.0
 * @date 2016/3/28
 */
public class MybatisXmlParser {


    private InputStream is;
    private Document document;

    public MybatisXmlParser(InputStream is) {

        try {
            this.is = is;
            SAXReader saxReader = new SAXReader();
            this.document = saxReader.read(is);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    public MybatisXmlParser(String path) {
        try {
            this.is = MybatisXmlParser.class.getClassLoader().getResourceAsStream(path);
            SAXReader saxReader = new SAXReader();
            this.document = saxReader.read(is);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String path = "me/lab/mybatis/xml/OrgMapper.xml";

        MybatisXmlParser parser = new MybatisXmlParser(path);
        Element root = parser.getRoot();
        System.out.println(root.getName());

        List<Element> selects = root.elements("select");

        if (selects == null || selects.size() < 1) {
            System.out.println("no select node");
        }


        for (Element select : selects) {
            Attribute idAttr = select.attribute("id");
            System.out.println("====================" + idAttr.getText() + "================================================================");
            if (select.isTextOnly()) {
                String txt = select.getText();
                System.out.println(txt);
            } else {
                for (Object o : select.content()) {
                    System.out.println(o.toString());

                }
            }


            System.out.println(select.isTextOnly());
            System.out.println(select.hasMixedContent());
            for (Object o : select.content()) {
                System.out.println(o.toString());

            }
            ;
//            List<Element> eles = select.elements();
//            for (Element e : eles) {
//                System.out.println(e.getName());
//            }


        }

    }


    public Element getRoot() {
        Element root = document.getRootElement();
        return root;
    }


    private void finishParser() {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
