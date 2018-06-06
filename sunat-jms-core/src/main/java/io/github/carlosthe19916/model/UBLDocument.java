package io.github.carlosthe19916.model;

import org.w3c.dom.Document;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public abstract class UBLDocument {

    protected final XPath xPath;
    protected final Document document;

    public UBLDocument(Document document, NamespaceContext namespaceContext) {
        this.document = document;

        XPathFactory xPathFactory = XPathFactory.newInstance();
        xPath = xPathFactory.newXPath();
        xPath.setNamespaceContext(namespaceContext);
    }

    public abstract String getIDAsignado() throws XPathExpressionException;

    public abstract String getNumeroRucEmisor() throws XPathExpressionException;

    public abstract String getCodigoTipoDocumento() throws XPathExpressionException;

}
