package io.github.carlosthe19916.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class UBLDocumentFactory {

    private UBLDocumentFactory() {
        // Use static methods only
    }

    public static UBLDocument getUBLDocument(Document document) {
        Element documentElement = document.getDocumentElement();
        String documentType = documentElement.getTagName();

        switch (documentType) {
            case "Invoice":
                return new UBLInvoiceDocument(document);
            case "CreditNote":
                return new UBLCreditNoteDocument(document);
            case "DebitNote":
                return new UBLDebitNoteDocument(document);
            default:
                return null;
        }
    }

}