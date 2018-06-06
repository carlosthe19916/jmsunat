package io.github.carlosthe19916.model;

import oasis.names.specification.ubl.schema.xsd.creditnote_2.SimpleCreditNoteNamespaceContext;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

public class UBLCreditNoteDocument extends UBLDocument {

    public UBLCreditNoteDocument(Document document) {
        super(document, new SimpleCreditNoteNamespaceContext());
    }

    @Override
    public String getIDAsignado() throws XPathExpressionException {
        XPathExpression xPathExpression = xPath.compile("//ns:CreditNote/cbc:ID/text()");
        NodeList nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
        return nodeList.item(0).getTextContent();
    }

    @Override
    public String getNumeroRucEmisor() throws XPathExpressionException {
        XPathExpression xPathExpression = xPath.compile("//ns:CreditNote/cac:AccountingSupplierParty/cbc:CustomerAssignedAccountID/text()");
        NodeList nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
        return nodeList.item(0).getTextContent();
    }

    @Override
    public String getCodigoTipoDocumento() throws XPathExpressionException {
        return "07";
    }

}
