package org.soptorshi.service.extended;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.soptorshi.domain.MonthlySalary;
import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.repository.MonthlyBalanceRepository;
import org.soptorshi.repository.extended.MonthlySalaryExtendedRepository;
import org.soptorshi.utils.SoptorshiUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import static org.soptorshi.utils.SoptorshiUtils.mBoldFont;
import static org.soptorshi.utils.SoptorshiUtils.mLiteFont;

@Service
@Transactional
public class PayrollReportService {
    private final MonthlySalaryExtendedRepository monthlySalaryExtendedRepository;

    public PayrollReportService(MonthlySalaryExtendedRepository monthlySalaryExtendedRepository) {
        this.monthlySalaryExtendedRepository = monthlySalaryExtendedRepository;
    }

    public ByteArrayInputStream createReport(Long officeId, int year, MonthType monthType) throws Exception{
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.addTitle("Purchase Order");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        Paragraph paragraph = new Paragraph("Seven Oceans Fish Processing Ltd.", SoptorshiUtils.mBigBoldFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);


        paragraph = new Paragraph("Salary and Allowances for the month of "+ monthType+", "+ year, SoptorshiUtils.mBigLiteFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        document.add(Chunk.NEWLINE);

        List<MonthlySalary> monthlySalaryList = monthlySalaryExtendedRepository.getByEmployee_Office_IdAndYearAndMonth(officeId, year, monthType);
        float[] widths = {1f, 2f, 4f, 3f, 3f, 3f};
        PdfPTable salaryTable = new PdfPTable(widths);
        salaryTable.setWidthPercentage(105);

        PdfPCell cell = new PdfPCell();
        paragraph = new Paragraph("S/N", mBoldFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        salaryTable.addCell(cell);

        cell = new PdfPCell();
        paragraph = new Paragraph("Employee Id", mBoldFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        salaryTable.addCell(cell);

        cell = new PdfPCell();
        paragraph = new Paragraph("Name of the Employee", mBoldFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        salaryTable.addCell(cell);

        cell = new PdfPCell();
        paragraph = new Paragraph("Bank's Name", mBoldFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        salaryTable.addCell(cell);

        cell = new PdfPCell();
        paragraph = new Paragraph("A/C No.", mBoldFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        salaryTable.addCell(cell);

        cell = new PdfPCell();
        paragraph = new Paragraph("Total Amount", mBoldFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        salaryTable.addCell(cell);

        BigDecimal totalAmount = BigDecimal.ZERO;
        Locale locale = new Locale("BD","BD");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);


        for(int i=0; i<monthlySalaryList.size(); i++){

            MonthlySalary monthlySalary = monthlySalaryList.get(i);
            totalAmount = totalAmount.add(monthlySalary.getPayable());

            cell = new PdfPCell();
            paragraph = new Paragraph((i+1)+"", mLiteFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            salaryTable.addCell(cell);

            cell = new PdfPCell();
            paragraph = new Paragraph(monthlySalary.getEmployee().getEmployeeId(), mLiteFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            salaryTable.addCell(cell);

            cell = new PdfPCell();
            paragraph = new Paragraph(monthlySalary.getEmployee().getFullName(), mLiteFont);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(paragraph);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            salaryTable.addCell(cell);

            cell = new PdfPCell();
            paragraph = new Paragraph(monthlySalary.getEmployee().getBankName()==null?"-": monthlySalary.getEmployee().getBankName(), mLiteFont);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(paragraph);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            salaryTable.addCell(cell);

            cell = new PdfPCell();
            paragraph = new Paragraph(monthlySalary.getEmployee().getBankAccountNo()==null?"-": monthlySalary.getEmployee().getBankAccountNo(), mLiteFont);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(paragraph);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            salaryTable.addCell(cell);

            cell = new PdfPCell();
            paragraph = new Paragraph(formatter.format(monthlySalary.getPayable()), mLiteFont);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(paragraph);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            salaryTable.addCell(cell);
        }


        cell = new PdfPCell();
        paragraph = new Paragraph("Total", mBoldFont);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(paragraph);
        cell.setColspan(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        salaryTable.addCell(cell);

        cell = new PdfPCell();
        paragraph = new Paragraph(formatter.format(totalAmount), mBoldFont);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        salaryTable.addCell(cell);

        salaryTable.setPaddingTop(40.0f);
        salaryTable.setHeaderRows(1);
        document.add(salaryTable);

        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    class PayrollReportHeaderAndFooter extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document pDocument) {
            PdfContentByte cb = writer.getDirectContent();
            String text = String.format("Page %s", writer.getCurrentPageNumber());
            Paragraph paragraph = new Paragraph(text, mBoldFont);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase(paragraph),
                (pDocument.right() - pDocument.left()) / 2 + pDocument.leftMargin(), pDocument.bottom() - 10, 0);
        }
    }

}
