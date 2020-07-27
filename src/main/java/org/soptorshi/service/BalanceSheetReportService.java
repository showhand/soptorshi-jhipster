package org.soptorshi.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.BalanceSheetFetchType;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.FinancialYearStatus;
import org.soptorshi.domain.enumeration.GroupType;
import org.soptorshi.repository.AccountBalanceRepository;
import org.soptorshi.repository.MstAccountRepository;
import org.soptorshi.repository.SystemGroupMapRepository;
import org.soptorshi.repository.extended.AccountBalanceExtendedRepository;
import org.soptorshi.repository.extended.DtTransactionExtendedRepository;
import org.soptorshi.repository.extended.FinancialAccountYearExtendedRepository;
import org.soptorshi.repository.extended.MstGroupExtendedRepository;
import org.soptorshi.security.report.SoptorshiPdfCell;
import org.soptorshi.utils.SoptorshiUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.soptorshi.utils.SoptorshiUtils.mBoldFont;
import static org.soptorshi.utils.SoptorshiUtils.mLiteFont;

@Service
@Transactional(readOnly = true)
public class BalanceSheetReportService {
    private MstGroupExtendedRepository mstGroupExtendedRepository;
    private MstAccountRepository mstAccountRepository;
    private SystemGroupMapRepository systemGroupMapRepository;
    private AccountBalanceExtendedRepository accountBalanceExtendedRepository;
    private DtTransactionExtendedRepository dtTransactionExtendedRepository;
    private FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository;

    public BalanceSheetReportService(MstGroupExtendedRepository mstGroupExtendedRepository, MstAccountRepository mstAccountRepository, SystemGroupMapRepository systemGroupMapRepository, AccountBalanceExtendedRepository accountBalanceExtendedRepository, DtTransactionExtendedRepository dtTransactionExtendedRepository, FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository) {
        this.mstGroupExtendedRepository = mstGroupExtendedRepository;
        this.mstAccountRepository = mstAccountRepository;
        this.systemGroupMapRepository = systemGroupMapRepository;
        this.accountBalanceExtendedRepository = accountBalanceExtendedRepository;
        this.dtTransactionExtendedRepository = dtTransactionExtendedRepository;
        this.financialAccountYearExtendedRepository = financialAccountYearExtendedRepository;
    }

    public ByteArrayInputStream createBalanceSheetReport(BalanceSheetFetchType balanceSheetFetchType, LocalDate asOnDate) throws Exception, DocumentException {
        List<MstAccount> accounts = mstAccountRepository.findAll();
        List<SystemGroupMap> systemGroupMaps = systemGroupMapRepository.findAll();
        Map<GroupType, Long> groupTypeSystemAccountMapMap = systemGroupMaps.stream().collect(Collectors.toMap(s->s.getGroupType(), s->s.getGroup().getId()));
        Map<Long, List<MstAccount>> groupMapWithAccounts = accounts.stream().collect(Collectors.groupingBy(a->a.getGroup().getId()));
        FinancialAccountYear openedFinancialAccountYear = financialAccountYearExtendedRepository.getByStatus(FinancialYearStatus.ACTIVE);
        asOnDate.atTime(LocalTime.MAX);
        List<DtTransaction> dtTransactions = dtTransactionExtendedRepository.findByVoucherDateBetween(openedFinancialAccountYear.getStartDate(), asOnDate);
        Map<Long, List<DtTransaction>> accountMapTotalDebitBalance = dtTransactions
            .stream()
            .filter(t->t.getAccount()!=null)
            .filter(t->t.getBalanceType().equals(BalanceType.DEBIT))
            .collect(Collectors.groupingBy(t->t.getAccount().getId()));

        Map<Long, List<DtTransaction>> accountMapTotalCreditBalance = dtTransactions
            .stream()
            .filter(t->t.getAccount()!=null)
            .filter(t->t.getBalanceType().equals(BalanceType.CREDIT))
            .collect(Collectors.groupingBy(t->t.getAccount().getId()));


        Document document = new Document();
        document.setPageSize(PageSize.A4.rotate());
        document.addTitle("Purchase Order");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        generateInitialHeaderInfo(asOnDate, document);

        float[] columnLengths = new float[]{5, 3, 5, 3};
        PdfPTable table = new PdfPTable(columnLengths);
        table.setWidthPercentage(100);
        PdfPCell cell = new PdfPCell();
        Paragraph paragraph = new Paragraph("Particulars", mBoldFont);
        addTopAndBottomBorderedCell(document, table, cell, paragraph);


        cell = new PdfPCell();
        paragraph = new Paragraph("Amount (BDT) ", mBoldFont);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        addTopAndBottomBorderedCell(document, table, cell, paragraph);

        cell = new PdfPCell();
        paragraph = new Paragraph("Particulars", mBoldFont);
        addTopAndBottomBorderedCell(document, table, cell, paragraph);


        cell = new PdfPCell();
        paragraph = new Paragraph("Amount (BDT)", mBoldFont);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        addTopAndBottomBorderedCell(document, table, cell, paragraph);
        table.setHeaderRows(1);

        PdfPTable assetTable = new PdfPTable(1);
        cell = new PdfPCell(new Paragraph("Asset", mBoldFont));
        cell.setPaddingTop(20f);
        cell.setBorder(Rectangle.NO_BORDER);
        assetTable.addCell(cell);



        float[] innerTableCellWidth = new float[] {6, 6};
        PdfPTable assetGroupTable = new PdfPTable(innerTableCellWidth);
        BigDecimal assetTotalAmount = createGroupSection(GroupType.ASSETS, groupTypeSystemAccountMapMap, balanceSheetFetchType, groupMapWithAccounts, accountMapTotalDebitBalance, accountMapTotalCreditBalance, assetGroupTable);
        cell = new PdfPCell(new Paragraph("Total", mBoldFont));
        cell.setPaddingTop(20f);
        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        assetGroupTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(SoptorshiUtils.getFormattedBalance(assetTotalAmount), mBoldFont));
        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        assetGroupTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(assetGroupTable);
        cell.setBorder(Rectangle.NO_BORDER);
        assetTable.addCell(cell);


        cell = new PdfPCell();
        cell.addElement(assetTable);
        cell.setColspan(2);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        PdfPTable rightTable = new PdfPTable(1);
        cell = new PdfPCell(new Paragraph("Liabilities", mBoldFont));
        cell.setPaddingTop(20f);
        cell.setBorder(Rectangle.NO_BORDER);
        rightTable.addCell(cell);
        PdfPTable liabilitiesGroupTable = new PdfPTable(innerTableCellWidth);
        BigDecimal liabilitiesAmount = createGroupSection(GroupType.LIABILITIES, groupTypeSystemAccountMapMap, balanceSheetFetchType, groupMapWithAccounts, accountMapTotalDebitBalance, accountMapTotalCreditBalance, liabilitiesGroupTable);
        cell = new PdfPCell(new Paragraph("Total", mBoldFont));
        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        liabilitiesGroupTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(SoptorshiUtils.getFormattedBalance(liabilitiesAmount), mBoldFont));
        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        liabilitiesGroupTable.addCell(cell);
        cell = new PdfPCell();
        cell.addElement(liabilitiesGroupTable);
        cell.setBorder(Rectangle.NO_BORDER);
        rightTable.addCell(cell);



        BigDecimal incomeAmount = BigDecimal.ZERO;


        cell = new PdfPCell(new Paragraph("Income", mBoldFont));
        cell.setBorder(Rectangle.NO_BORDER);
        rightTable.addCell(cell);
        PdfPTable incomeGroupTable = new PdfPTable(innerTableCellWidth);
        incomeAmount = createGroupSection(GroupType.INCOME, groupTypeSystemAccountMapMap, balanceSheetFetchType, groupMapWithAccounts, accountMapTotalDebitBalance, accountMapTotalCreditBalance, incomeGroupTable);

        cell = new PdfPCell(new Paragraph("Total", mBoldFont));
        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        incomeGroupTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(SoptorshiUtils.getFormattedBalance(incomeAmount), mBoldFont));
        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        incomeGroupTable.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(incomeGroupTable);
        rightTable.addCell(cell);


        cell = new PdfPCell(new Paragraph("Expenditure", mBoldFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingTop(20f);
        rightTable.addCell(cell);
        PdfPTable expenseGroupTable = new PdfPTable(innerTableCellWidth);
        BigDecimal expenseAmount = createGroupSection(GroupType.EXPENSES, groupTypeSystemAccountMapMap, balanceSheetFetchType, groupMapWithAccounts, accountMapTotalDebitBalance, accountMapTotalCreditBalance, expenseGroupTable);
        cell = new PdfPCell(new Paragraph("Total", mBoldFont));
        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        expenseGroupTable.addCell(cell);
        cell = new PdfPCell(new Paragraph(SoptorshiUtils.getFormattedBalance(expenseAmount), mBoldFont));
        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        expenseGroupTable.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(expenseGroupTable);
        rightTable.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(rightTable);
        cell.setColspan(2);
        table.addCell(cell);


        float[] totalAmmountTableWidth = {4, 8};
        PdfPTable totalAmountTable = new PdfPTable(2);
        addTotalAmountOfTheSection(totalAmountTable, assetTotalAmount);
        cell = new PdfPCell();
        cell.addElement(totalAmountTable);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        table.addCell(cell);

        PdfPTable rightTotalAmountTable = new PdfPTable(totalAmmountTableWidth);
        addTotalAmountOfTheSection(rightTotalAmountTable, liabilitiesAmount.add(incomeAmount).add(expenseAmount));
        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(rightTotalAmountTable);
        cell.setColspan(2);
        table.addCell(cell);


        document.add(table);
        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }


    private void addTotalAmountOfTheSection(PdfPTable pAssetTable, BigDecimal pSectionTotalBalance) {
        PdfPCell cell;
        Paragraph paragraph;
        cell = new PdfPCell();
        cell.addElement(new Paragraph("Total ", mBoldFont));
        cell.setPaddingBottom(3);
        cell.setPaddingTop(-3);
        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        pAssetTable.addCell(cell);

        cell = new PdfPCell();
        paragraph = new Paragraph(SoptorshiUtils.getFormattedBalance(pSectionTotalBalance), mBoldFont);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(paragraph);
        cell.setPaddingBottom(3);
        cell.setPaddingTop(-3);
        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        pAssetTable.addCell(cell);
    }

    private BigDecimal createGroupSection(GroupType groupType,
                                         Map<GroupType, Long> systemGroupMap,
                                         BalanceSheetFetchType balanceSheetFetchType,
                                         Map<Long, List<MstAccount>> groupMapWithAccounts,
                                         Map<Long, List<DtTransaction>> accountMapWithDebits,
                                         Map<Long, List<DtTransaction>> accountMapWithCredits,
                                          PdfPTable groupTable) throws Exception, DocumentException{

        BigDecimal totalMainGroupDebit = BigDecimal.ZERO;
        BigDecimal totalMainGroupCredit = BigDecimal.ZERO;
        List<MstGroup> groups = mstGroupExtendedRepository.findByMainGroup(systemGroupMap.get(groupType));

        SoptorshiPdfCell cell;
        for(MstGroup group: groups){
            BigDecimal totalDebit = BigDecimal.ZERO;
            BigDecimal totalCredit = BigDecimal.ZERO;

            if(balanceSheetFetchType.equals(BalanceSheetFetchType.SUMMARIZED)){
                cell = new SoptorshiPdfCell(new Paragraph(group.getName(), mLiteFont));
                cell.setBorder(Rectangle.NO_BORDER);
                groupTable.addCell(cell);

                if(groupMapWithAccounts.containsKey(group.getId())){
                    List<MstAccount> groupAccounts = groupMapWithAccounts.get(group.getId());


                    for(MstAccount account: groupAccounts){
                        BigDecimal totalAccDebit = BigDecimal.ZERO;
                        BigDecimal totalAccCredit = BigDecimal.ZERO;
                        if(accountMapWithDebits.containsKey(account.getId())){
                            totalAccDebit = accountMapWithDebits.get(account.getId())
                                .stream()
                                .map(a-> a.getAmount())
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                        }
                        if(accountMapWithCredits.containsKey(account.getId())){
                            totalAccCredit = accountMapWithCredits.get(account.getId())
                                .stream()
                                .map(a-> a.getAmount())
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                        }

                        totalDebit = totalDebit.add(totalAccDebit);
                        totalCredit = totalCredit.add(totalAccCredit);


                    }

                    if(groupType.equals(GroupType.ASSETS)){
                        cell = new SoptorshiPdfCell(new Paragraph(SoptorshiUtils.getFormattedBalance(totalDebit.subtract(totalCredit)), mLiteFont));
                        cell.setBorder(Rectangle.NO_BORDER);
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        groupTable.addCell(cell);
                    }else{
                        cell = new SoptorshiPdfCell(new Paragraph(SoptorshiUtils.getFormattedBalance(totalCredit.subtract(totalDebit)), mLiteFont));
                        cell.setBorder(Rectangle.NO_BORDER);
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        groupTable.addCell(cell);
                    }
                }else{
                    cell = new SoptorshiPdfCell(new Paragraph(SoptorshiUtils.getFormattedBalance(BigDecimal.ZERO), mLiteFont));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    groupTable.addCell(cell);
                }
            }else{
                cell = new SoptorshiPdfCell(new Paragraph(group.getName(), mBoldFont));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setColspan(2);
                groupTable.addCell(cell);

                if(groupMapWithAccounts.containsKey(group.getId())){
                    List<MstAccount> groupAccounts = groupMapWithAccounts.get(group.getId());


                    for(MstAccount account: groupAccounts){
                        BigDecimal totalAccDebit = BigDecimal.ZERO;
                        BigDecimal totalAccCredit = BigDecimal.ZERO;
                        cell = new SoptorshiPdfCell(new Paragraph(account.getName(), mLiteFont));
                        cell.setBorder(Rectangle.NO_BORDER);
                        groupTable.addCell(cell);

                        if(accountMapWithDebits.containsKey(account.getId()) || accountMapWithCredits.containsKey(account.getId())){
                            if(accountMapWithDebits.containsKey(account.getId()))
                            totalAccDebit = accountMapWithDebits.get(account.getId())
                                .stream()
                                .map(a-> a.getAmount())
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                            if(accountMapWithCredits.containsKey(account.getId()))
                                totalAccCredit = accountMapWithCredits.get(account.getId())
                                    .stream()
                                    .map(a-> a.getAmount())
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                            totalDebit = totalDebit.add(totalAccDebit);
                            totalCredit = totalCredit.add(totalAccCredit);


                            if(groupType.equals(GroupType.ASSETS)){
                                cell = new SoptorshiPdfCell(new Paragraph(SoptorshiUtils.getFormattedBalance(totalAccDebit.subtract(totalAccCredit)), mLiteFont));
                                cell.setBorder(Rectangle.NO_BORDER);
                                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                groupTable.addCell(cell);
                            }else{
                                cell = new SoptorshiPdfCell(new Paragraph(SoptorshiUtils.getFormattedBalance(totalAccCredit.subtract(totalAccDebit)), mLiteFont));
                                cell.setBorder(Rectangle.NO_BORDER);
                                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                groupTable.addCell(cell);
                            }
                        }else{
                            cell = new SoptorshiPdfCell(new Paragraph(SoptorshiUtils.getFormattedBalance(BigDecimal.ZERO), mLiteFont));
                            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            cell.setBorder(Rectangle.NO_BORDER);
                            groupTable.addCell(cell);
                        }

                    }
                    if(groupType.equals(GroupType.ASSETS)){
                        cell = new SoptorshiPdfCell(new Paragraph(SoptorshiUtils.getFormattedBalance(totalDebit.subtract(totalCredit)), mBoldFont));
                        cell.setColspan(2);
                        cell.setBorder(Rectangle.NO_BORDER);
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        groupTable.addCell(cell);
                    }else{
                        cell = new SoptorshiPdfCell(new Paragraph(SoptorshiUtils.getFormattedBalance(totalCredit.subtract(totalDebit)), mBoldFont));
                        cell.setColspan(2);
                        cell.setBorder(Rectangle.NO_BORDER);
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        groupTable.addCell(cell);
                    }
                }else{

                    cell = new SoptorshiPdfCell(new Paragraph(SoptorshiUtils.getFormattedBalance(BigDecimal.ZERO), mBoldFont));
                    cell.setColspan(2);
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    groupTable.addCell(cell);
                }
            }

            totalMainGroupDebit = totalMainGroupDebit.add(totalDebit);
            totalMainGroupCredit = totalMainGroupCredit.add(totalCredit);
        }

        BigDecimal total = groupType.equals(GroupType.ASSETS)? totalMainGroupDebit.subtract(totalMainGroupCredit): totalMainGroupCredit.subtract(totalMainGroupDebit);

       /* cell = new SoptorshiPdfCell(new Paragraph("Total", mBoldFont));
        cell.setBorder(Rectangle.NO_BORDER);
        groupTable.addCell(cell);

        cell = new SoptorshiPdfCell(new Paragraph(SoptorshiUtils.getFormattedBalance(total), mBoldFont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        groupTable.addCell(cell);*/
        return total;
    }


    private void addTopAndBottomBorderedCell(Document pDocument, PdfPTable pTable, PdfPCell pCell, Paragraph pParagraph)
        throws DocumentException {
        pCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
        pCell.addElement(pParagraph);
        pCell.setPaddingTop(-2);
        pCell.setPaddingBottom(5);
        pTable.addCell(pCell);
    }

    private void generateInitialHeaderInfo(LocalDate pDate, Document pDocument) throws DocumentException {
        float[] columnSize = new float[] {1, 6, 2};
        PdfPTable table = new PdfPTable(columnSize);
        SoptorshiPdfCell cell = new SoptorshiPdfCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new SoptorshiPdfCell();
        Phrase innerPhrase = new Phrase();
        Paragraph paragraph = new Paragraph("Balance Sheet For ", mLiteFont);
        innerPhrase.add(paragraph);
        paragraph = new Paragraph("Seven Oceans Fish Processing Ltd.", mBoldFont);
        innerPhrase.add(paragraph);
        paragraph = new Paragraph(innerPhrase);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);

        innerPhrase = new Phrase();
        paragraph = new Paragraph("As on ", mLiteFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        innerPhrase.add(paragraph);
        paragraph = new Paragraph(SoptorshiUtils.formatDate(pDate, "dd-MM-yyyy"), mBoldFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        innerPhrase.add(paragraph);
        paragraph = new Paragraph(innerPhrase);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new SoptorshiPdfCell();
        innerPhrase = new Phrase();
        paragraph = new Paragraph("Date : ", mBoldFont);
        innerPhrase.add(paragraph);
        paragraph = new Paragraph(SoptorshiUtils.formatDate(LocalDate.now(), "dd-MM-yyyy"), mLiteFont);
        innerPhrase.add(paragraph);
        cell.addElement(innerPhrase);
        innerPhrase = new Phrase();
        paragraph = new Paragraph("Time : ", mBoldFont);
        paragraph.setFont(mBoldFont);
        innerPhrase.add(paragraph);
        LocalTime time = LocalTime.now();
        DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
        paragraph = new Paragraph(dateFormat.format(new Date()).toString(), mLiteFont);
        innerPhrase.add(paragraph);
        cell.addElement(innerPhrase);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        pDocument.add(table);
    }

    class BalanceSheetReportHeaderAndFooter extends PdfPageEventHelper {
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
