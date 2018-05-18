package io.github.carlosthe19916.model;

import oasis.names.specification.ubl.schema.xsd.invoice_2.SimpleInvoiceNamespaceContext;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

public class UBLInvoiceDocument extends UBLDocument {

    public UBLInvoiceDocument(Document document) {
        super(document, new SimpleInvoiceNamespaceContext());
    }

    @Override
    public String getIDAsignado() throws XPathExpressionException {
        XPathExpression xPathExpression = xPath.compile("//ns:Invoice/cbc:ID/text()");
        NodeList nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
        return nodeList.item(0).getTextContent();
    }

    @Override
    public String getNumeroRucEmisor() throws XPathExpressionException {
        XPathExpression xPathExpression = xPath.compile("//ns:Invoice/cac:AccountingSupplierParty/cbc:CustomerAssignedAccountID/text()");
        NodeList nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
        return nodeList.item(0).getTextContent();
    }

    @Override
    public String getCodigoTipoDocumento() throws XPathExpressionException {
        XPathExpression xPathExpression = xPath.compile("//ns:Invoice/cbc:InvoiceTypeCode/text()");
        NodeList nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
        return nodeList.item(0).getTextContent();
    }

}
