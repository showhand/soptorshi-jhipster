package org.soptorshi.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.soptorshi.domain.MstAccount;
import org.soptorshi.domain.MstGroup;
import org.soptorshi.domain.SystemAccountMap;
import org.soptorshi.domain.SystemGroupMap;
import org.soptorshi.domain.enumeration.GroupType;
import org.soptorshi.repository.MstAccountRepository;
import org.soptorshi.repository.SystemGroupMapRepository;
import org.soptorshi.repository.extended.MstGroupExtendedRepository;
import org.soptorshi.security.report.SoptorshiPdfCell;
import org.soptorshi.utils.SoptorshiUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ChartsOfAccountReportService {

    private MstGroupExtendedRepository mstGroupExtendedRepository;
    private MstAccountRepository mstAccountRepository;
    private SystemGroupMapRepository systemGroupMapRepository;

    public ChartsOfAccountReportService(MstGroupExtendedRepository mstGroupExtendedRepository, MstAccountRepository mstAccountRepository, SystemGroupMapRepository systemGroupMapRepository) {
        this.mstGroupExtendedRepository = mstGroupExtendedRepository;
        this.mstAccountRepository = mstAccountRepository;
        this.systemGroupMapRepository = systemGroupMapRepository;
    }

    public ByteArrayInputStream createChartsOrAccountReport() throws Exception, DocumentException{

        List<SystemGroupMap> systemGroupMaps = systemGroupMapRepository.findAll();
        Map<GroupType, SystemGroupMap> groupTypeMapWithSystemGroup = systemGroupMaps.stream().collect(Collectors.toMap(s->s.getGroupType(), s->s));
        List<MstAccount> accounts = mstAccountRepository.findAll();
        Map<Long, List<MstAccount>> groupMapWithAccounts = accounts.stream().collect(Collectors.groupingBy(a->a.getGroup().getId()));

        Document document = new Document();
        document.addTitle("Purchase Order");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();

        Paragraph paragraph = new Paragraph("Seven Oceans Fish Processing Ltd.", SoptorshiUtils.mBigBoldFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        paragraph = new Paragraph("Chart of Accounts", SoptorshiUtils.mBigLiteFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        float[] tableColumnWidth = new float[]{1,2};
        PdfPTable table = new PdfPTable(tableColumnWidth);

        table.setWidthPercentage(100);

        SoptorshiPdfCell cell = new SoptorshiPdfCell();
        cell.addElement(new Paragraph("Account Category", SoptorshiUtils.mBoldFont));
        table.addCell(cell);

        cell = new SoptorshiPdfCell();
        cell.addElement(new Paragraph("Account Title", SoptorshiUtils.mBoldFont));
        table.addCell(cell);

        Long assetGroupId = groupTypeMapWithSystemGroup.get(GroupType.ASSETS).getGroup().getId();
        List<MstGroup> assetGroups = mstGroupExtendedRepository.findByMainGroup(assetGroupId);
        Long liabilitiesGroupId = groupTypeMapWithSystemGroup.get(GroupType.LIABILITIES).getGroup().getId();
        List<MstGroup> liabilities = mstGroupExtendedRepository.findByMainGroup(liabilitiesGroupId);
        Long incomeGroupId = groupTypeMapWithSystemGroup.get(GroupType.INCOME).getGroup().getId();
        List<MstGroup> income = mstGroupExtendedRepository.findByMainGroup(incomeGroupId);
        Long expenditureGroupId = groupTypeMapWithSystemGroup.get(GroupType.EXPENSES).getGroup().getId();
        List<MstGroup> expenditure = mstGroupExtendedRepository.findByMainGroup(expenditureGroupId);

        table =  createGroupStructure(table, "ASSET", assetGroups, groupMapWithAccounts);
        table  = createGroupStructure(table, "LIABILITIES",liabilities, groupMapWithAccounts);
        table = createGroupStructure(table, "INCOME", income, groupMapWithAccounts);
        table = createGroupStructure(table, "EXPENDITURE", expenditure, groupMapWithAccounts);


        table.setHeaderRows(1);
        document.add(Chunk.NEWLINE);
        document.add(table);
        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public PdfPTable createGroupStructure(PdfPTable table, String groupName, List<MstGroup> groups, Map<Long, List<MstAccount>> groupMapAccounts){
        SoptorshiPdfCell cell = new SoptorshiPdfCell();
        Paragraph paragraph = new Paragraph(groupName, SoptorshiUtils.mBoldFontItalic);
        cell.addElement(paragraph);
        table.addCell(cell);
        cell = new SoptorshiPdfCell();
        cell.addElement(new Paragraph(""));
        table.addCell(cell);

        for(MstGroup group: groups){
            cell = new SoptorshiPdfCell();
            paragraph = new Paragraph(group.getName(), SoptorshiUtils.mLiteFontItalic);
            cell.addElement(paragraph);
            table.addCell(cell);
            cell = new SoptorshiPdfCell();
            cell.addElement(new Paragraph(""));
            table.addCell(cell);


            if(groupMapAccounts.containsKey(group.getId())){
                for(MstAccount account: groupMapAccounts.get(group.getId())){
                    cell = new SoptorshiPdfCell();
                    cell.addElement(new Paragraph(""));
                    table.addCell(cell);

                    cell = new SoptorshiPdfCell();
                    paragraph = new Paragraph(account.getName(), SoptorshiUtils.mLiteFont);
                    cell.addElement(paragraph);
                    table.addCell(cell);
                }
            }

        }

        cell = new SoptorshiPdfCell();
        paragraph = new Paragraph("");
        cell.addElement(paragraph);

        table.addCell(cell);
        cell = new SoptorshiPdfCell();
        cell.addElement(new Paragraph(""));
        table.addCell(cell);

        return table;
    }
}
