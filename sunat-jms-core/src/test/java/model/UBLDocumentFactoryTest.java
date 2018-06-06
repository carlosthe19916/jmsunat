package model;

import io.github.carlosthe19916.model.UBLDocument;
import io.github.carlosthe19916.model.UBLDocumentFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class UBLDocumentFactoryTest {

    private Document document;

    @Before
    public void before() throws ParserConfigurationException, IOException, SAXException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("ubl/Document.xml").getFile());

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        document = dBuilder.parse(file);
    }

    @Test
    public void getUBLDocument() {
        UBLDocument ublDocument = UBLDocumentFactory.getUBLDocument(document);

        Assert.assertNull(ublDocument);
    }
}