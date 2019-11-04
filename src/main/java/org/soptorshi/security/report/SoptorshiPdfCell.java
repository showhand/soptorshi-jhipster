package org.soptorshi.security.report;

import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class SoptorshiPdfCell extends PdfPCell {
    public SoptorshiPdfCell() {
        super();
//    super.setPaddingTop(-1.0f);
        super.setPaddingLeft(4.0f);
        super.setPaddingBottom(3.0f);
    }

    public SoptorshiPdfCell(Phrase phrase) {
        super(phrase);
        super.setPaddingLeft(4.0f);
        super.setPaddingBottom(3.0f);
    }

    public SoptorshiPdfCell(Image image) {
        super(image);
    }

    public SoptorshiPdfCell(Image image, boolean fit) {
        super(image, fit);
    }

    public SoptorshiPdfCell(PdfPTable table) {
        super(table);
    }

    @Override
    public void addElement(Element element) {
//    super.setPaddingTop(-1.0f);
        super.setVerticalAlignment(Element.ALIGN_MIDDLE);
        super.setUseAscender(true);
        super.setPaddingTop(4f);
        super.setPaddingBottom(4f);
        super.addElement(element);
    }
}
