package io.github.carlosthe19916.ubl;

import oasis.names.specification.ubl.schema.xsd.debitnote_2.SimpleDebitNoteNamespaceContext;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

public class UBLDebitNoteDocument extends UBLDocument {

    public UBLDebitNoteDocument(Document document) {
        super(document, new SimpleDebitNoteNamespaceContext());
    }

    @Override
    public String getIDAsignado() throws XPathExpressionException {
        XPathExpression xPathExpression = xPath.compile("//ns:DebitNote/cbc:ID/text()");
        NodeList nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
        return nodeList.item(0).getTextContent();
    }

    @Override
    public String getNumeroRucEmisor() throws XPathExpressionException {
        XPathExpression xPathExpression = xPath.compile("//ns:DebitNote/cac:AccountingSupplierParty/cbc:CustomerAssignedAccountID/text()");
        NodeList nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
        return nodeList.item(0).getTextContent();
    }

    @Override
    public String getCodigoTipoDocumento() throws XPathExpressionException {
        return "08";
    }

}
