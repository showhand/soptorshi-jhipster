package org.soptorshi.service.extended;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.LeaveApplication;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.dto.LeaveBalanceDTO;
import org.soptorshi.utils.SoptorshiUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LeaveReportService {

    private final Logger log = LoggerFactory.getLogger(LeaveReportService.class);

    private final LeaveApplicationExtendedService leaveApplicationExtendedService;

    private final EmployeeExtendedRepository employeeExtendedRepository;

    private final LeaveBalanceService leaveBalanceService;

    public static Font mBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD, BaseColor.BLACK);

    static Font TIME_ROMAN_11 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    static Font TIMES_BOLD_11 = FontFactory.getFont(FontFactory.TIMES_BOLD, 11);
    static Font TIME_ROMAN_12 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
    static Font TIME_BOLD_12 = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
    static Font TIMES_BOLD_14 = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);

    public LeaveReportService(LeaveApplicationExtendedService leaveApplicationExtendedService, EmployeeExtendedRepository employeeExtendedRepository, LeaveBalanceService leaveBalanceService) {
        this.leaveApplicationExtendedService = leaveApplicationExtendedService;
        this.employeeExtendedRepository = employeeExtendedRepository;
        this.leaveBalanceService = leaveBalanceService;
    }

    public ByteArrayInputStream getAllLeaveApplications(LocalDate fromDate, LocalDate toDate, String employeeId) throws DocumentException {
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(20, 20, 40, 40);
        document.addTitle("Leave Applications");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.setPageEvent(new headerAndFooter());
        document.open();

        Chunk chunk = null;
        Paragraph paragraph = null;
        PdfPTable pdfPTable = null;
        PdfPCell pdfPCell = null;

        paragraph = new Paragraph(new Chunk("Seven Oceans Fish Processing Ltd", SoptorshiUtils.mBigBoldFont));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        document.add(Chunk.NEWLINE);

        paragraph = new Paragraph(new Chunk("Leave History", TIMES_BOLD_11));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph();
        lineBreak(paragraph, 1);
        document.add(paragraph);

        Optional<Employee> employee = employeeExtendedRepository.findByEmployeeId(employeeId);
        if (employee.isPresent()) {
            createLeaveApplicationTable(document, employee.get(), fromDate, toDate);
        } else {
            paragraph = new Paragraph(new Chunk("No employee found.", TIME_ROMAN_11));
            document.add(paragraph);
        }

        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    private void createLeaveApplicationTable(Document document, Employee employee, LocalDate fromDate, LocalDate toDate) throws DocumentException {
        Paragraph paragraph = new Paragraph(new Chunk("Employee: " + employee.getFullName(), TIMES_BOLD_11));
        document.add(paragraph);

        paragraph = new Paragraph();
        lineBreak(paragraph, 1);
        document.add(paragraph);

        PdfPTable pdfPTable;
        PdfPCell pdfPCell;

        List<LeaveApplication> leaveApplications = leaveApplicationExtendedService.getAll(fromDate, toDate, employee);

        if (leaveApplications.size() > 0) {
            pdfPTable = new PdfPTable(6);
            pdfPTable.setWidthPercentage(100);
            pdfPTable.setTotalWidth(new float[]{5, 25, 20, 20, 15, 15});

            pdfPCell = new PdfPCell(new Paragraph("#", TIMES_BOLD_11));
            pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("Leave Type", TIMES_BOLD_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("From", TIMES_BOLD_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("To", TIMES_BOLD_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("Day(s)", TIMES_BOLD_11));
            pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("Status", TIMES_BOLD_11));
            pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            int counter = 0;

            for (LeaveApplication leaveApplication : leaveApplications) {
                counter = counter + 1;
                pdfPCell = new PdfPCell(new Paragraph(counter + "", TIME_ROMAN_11));
                pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Paragraph(leaveApplication.getLeaveTypes().getName(), TIME_ROMAN_11));
                pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Paragraph(leaveApplication.getFromDate().format(DateTimeFormatter.ofPattern("dd MMMM, yyyy")), TIME_ROMAN_11));
                pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Paragraph(leaveApplication.getToDate().format(DateTimeFormatter.ofPattern("dd MMMM, yyyy")), TIME_ROMAN_11));
                pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Paragraph(leaveApplication.getNumberOfDays().toString(), TIME_ROMAN_11));
                pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Paragraph(leaveApplication.getStatus().name(), TIME_ROMAN_11));
                pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
            }
            document.add(pdfPTable);
        } else {
            paragraph = new Paragraph(new Chunk("No records found.", TIME_ROMAN_11));
            document.add(paragraph);
        }
    }

    public ByteArrayInputStream getAllLeaveApplications(LocalDate fromDate, LocalDate toDate) throws DocumentException {
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(20, 20, 40, 40);
        document.addTitle("Leave Applications");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.setPageEvent(new headerAndFooter());
        document.open();

        Chunk chunk = null;
        Paragraph paragraph = null;
        PdfPTable pdfPTable = null;
        PdfPCell pdfPCell = null;

        paragraph = new Paragraph(new Chunk("Seven Oceans Fish Processing Ltd", SoptorshiUtils.mBigBoldFont));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        document.add(Chunk.NEWLINE);

        paragraph = new Paragraph(new Chunk("Leave History", TIMES_BOLD_11));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph();
        lineBreak(paragraph, 1);
        document.add(paragraph);

        List<Employee> employees = employeeExtendedRepository.findAll();
        for(int i = 0; i < employees.size(); i++) {
            createLeaveApplicationTable(document, employees.get(i), fromDate, toDate);
            if(i != employees.size() - 1) {
                document.newPage();
            };
        }
        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public ByteArrayInputStream getAllLeaveBalance(int year, String employeeId) throws DocumentException {
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(20, 20, 40, 40);
        document.addTitle("Leave Application");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.setPageEvent(new headerAndFooter());
        document.open();

        Chunk chunk = null;
        Paragraph paragraph = null;
        PdfPTable pdfPTable = null;
        PdfPCell pdfPCell = null;

        paragraph = new Paragraph(new Chunk("Seven Oceans Fish Processing Ltd", SoptorshiUtils.mBigBoldFont));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        document.add(Chunk.NEWLINE);

        paragraph = new Paragraph(new Chunk("Leave Balance", TIMES_BOLD_11));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph();
        lineBreak(paragraph, 1);
        document.add(paragraph);

        Optional<Employee> employee = employeeExtendedRepository.findByEmployeeId(employeeId);
        if (employee.isPresent()) {
            createLeaveBalanceTable(document, employee.get(), year);
        } else {
            paragraph = new Paragraph(new Chunk("No employee found.", TIME_ROMAN_11));
            document.add(paragraph);
        }

        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    private void createLeaveBalanceTable(Document document, Employee employee, int year) throws DocumentException {
        Paragraph paragraph = new Paragraph(new Chunk("Employee: " + employee.getFullName(), TIMES_BOLD_11));
        document.add(paragraph);

        paragraph = new Paragraph();
        lineBreak(paragraph, 1);
        document.add(paragraph);

        PdfPTable pdfPTable;
        PdfPCell pdfPCell;

        List<LeaveBalanceDTO> leaveBalances = leaveBalanceService.calculateLeaveBalance(employee.getEmployeeId(), year);

        if (leaveBalances.size() > 0) {
            pdfPTable = new PdfPTable(4);
            pdfPTable.setWidthPercentage(100);
            pdfPTable.setTotalWidth(new float[]{10, 30, 30, 30});

            pdfPCell = new PdfPCell(new Paragraph("#", TIMES_BOLD_11));
            pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("Leave Type", TIMES_BOLD_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("Total Day(s)", TIMES_BOLD_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("Remaining Day(s)", TIMES_BOLD_11));
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            int counter = 0;

            for (LeaveBalanceDTO leaveBalanceDTO : leaveBalances) {
                counter = counter + 1;
                pdfPCell = new PdfPCell(new Paragraph(counter + "", TIME_ROMAN_11));
                pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Paragraph(leaveBalanceDTO.getLeaveTypeName(), TIME_ROMAN_11));
                pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Paragraph(leaveBalanceDTO.getTotalLeaveApplicableDays() + "", TIME_ROMAN_11));
                pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Paragraph(leaveBalanceDTO.getRemainingDays() + "", TIME_ROMAN_11));
                pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);
            }
            document.add(pdfPTable);
        } else {
            paragraph = new Paragraph(new Chunk("No records found.", TIME_ROMAN_11));
            document.add(paragraph);
        }
    }

    public ByteArrayInputStream getAllLeaveBalance(int year) throws DocumentException {
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(20, 20, 40, 40);
        document.addTitle("Leave Balance");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.setPageEvent(new headerAndFooter());
        document.open();

        Chunk chunk = null;
        Paragraph paragraph = null;
        PdfPTable pdfPTable = null;
        PdfPCell pdfPCell = null;

        paragraph = new Paragraph(new Chunk("Seven Oceans Fish Processing Ltd", SoptorshiUtils.mBigBoldFont));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        document.add(Chunk.NEWLINE);

        paragraph = new Paragraph(new Chunk("Leave Balance", TIMES_BOLD_11));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph();
        lineBreak(paragraph, 1);
        document.add(paragraph);

        List<Employee> employees = employeeExtendedRepository.findAll();
        for(int i = 0; i < employees.size(); i++) {
            createLeaveBalanceTable(document, employees.get(i), year);
            if(i != employees.size() - 1) {
                document.newPage();
            };
        }
        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    static class headerAndFooter extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f, Font.BOLDITALIC, BaseColor.GRAY);
            DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
            String loggedInUserId = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "Anonymous";
            Paragraph header =
                new Paragraph("Generated by " + loggedInUserId + " on " + new SimpleDateFormat("dd MMMM, yyyy").format(new Date()) + " at "
                    + dateFormat.format(new Date()), headerFont);
            header.setAlignment(Element.ALIGN_RIGHT);
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase(header),
                document.right() + 10, document.top() + 10, 0);
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            String text = String.format("Page %s", writer.getCurrentPageNumber());
            Paragraph paragraph = new Paragraph(text, mBoldFont);
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase(paragraph), (document.right() - document.left())
                / 2 + document.leftMargin(), document.bottom() - 10, 0);
        }

    }

    void lineBreak(Paragraph p, int number) {
        for (int i = 0; i < number; i++) {
            p.add(new Paragraph(" "));
        }
    }
}
