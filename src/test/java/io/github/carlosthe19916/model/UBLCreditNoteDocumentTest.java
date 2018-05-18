package io.github.carlosthe19916.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;

public class UBLCreditNoteDocumentTest {

    private Document document;

    @Before
    public void before() throws ParserConfigurationException, IOException, SAXException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("ubl/F001-00000007.xml").getFile());

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        document = dBuilder.parse(file);
    }

    @Test
    public void test() throws XPathExpressionException {
        UBLDocument ublDocument = UBLDocumentFactory.getUBLDocument(document);

        Assert.assertNotNull(ublDocument);
        Assert.assertEquals("F001-00000007", ublDocument.getIDAsignado());
        Assert.assertEquals("20494637074", ublDocument.getNumeroRucEmisor());
        Assert.assertEquals("07", ublDocument.getCodigoTipoDocumento());
    }
}