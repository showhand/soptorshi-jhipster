package org.soptorshi.service;

import com.itextpdf.text.DocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.soptorshi.config.JxlsGenerator;
import org.soptorshi.domain.DtTransaction;
import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.domain.MstAccount;
import org.soptorshi.domain.SystemGroupMap;
import org.soptorshi.domain.enumeration.BalanceSheetFetchType;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.FinancialYearStatus;
import org.soptorshi.domain.enumeration.GroupType;
import org.soptorshi.repository.MstAccountRepository;
import org.soptorshi.repository.SystemGroupMapRepository;
import org.soptorshi.repository.extended.*;
import org.soptorshi.service.dto.extended.MonthWithProfitAndLossAmountDTO;
import org.soptorshi.service.dto.extended.ProfitAndLossGroupDTO;
import org.soptorshi.service.extended.ProfitLossService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BalanceSheetExcelReportService {
    private final  ResourceLoader resourceLoader;
    private final  JxlsGenerator jxlsGenerator;
    private final  MstGroupExtendedRepository mstGroupExtendedRepository;
    private final  SystemGroupMapExtendedRepository systemGroupMapExtendedRepository;
    private final  MstAccountExtendedRepository mstAccountExtendedRepository;
    private final  DtTransactionExtendedRepository dtTransactionExtendedRepository;
    private final  FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository;
    private final  ProfitLossService profitLossService;

    public BalanceSheetExcelReportService(ResourceLoader resourceLoader,
                                          JxlsGenerator jxlsGenerator,
                                          MstGroupExtendedRepository mstGroupExtendedRepository,
                                          SystemGroupMapExtendedRepository systemGroupMapExtendedRepository,
                                          MstAccountExtendedRepository mstAccountExtendedRepository,
                                          DtTransactionExtendedRepository dtTransactionExtendedRepository,
                                          FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository,
                                          ProfitLossService profitLossService) {
        this.resourceLoader = resourceLoader;
        this.jxlsGenerator = jxlsGenerator;
        this.mstGroupExtendedRepository = mstGroupExtendedRepository;
        this.systemGroupMapExtendedRepository = systemGroupMapExtendedRepository;
        this.mstAccountExtendedRepository = mstAccountExtendedRepository;
        this.dtTransactionExtendedRepository = dtTransactionExtendedRepository;
        this.financialAccountYearExtendedRepository = financialAccountYearExtendedRepository;
        this.profitLossService = profitLossService;
    }

    List<SystemGroupMap> systemGroupMaps;
    Map<GroupType, Long> groupTypeSystemAccountMapMap;
    List<MstAccount> accounts;
    Map<Long, List<MstAccount>> groupMapWithAccounts;



    public ByteArrayInputStream createBalanceSheetReport( LocalDate toDate) throws Exception {

        accounts = mstAccountExtendedRepository.findAll();
        systemGroupMaps = systemGroupMapExtendedRepository.findAll();
        groupTypeSystemAccountMapMap = systemGroupMaps.stream().collect(Collectors.toMap(s->s.getGroupType(), s->s.getGroup().getId()));
        groupMapWithAccounts = accounts.stream().collect(Collectors.groupingBy(a->a.getGroup().getId()));
        FinancialAccountYear openedFinancialAccountYear = financialAccountYearExtendedRepository.getByStatus(FinancialYearStatus.ACTIVE);
        toDate = toDate.atTime(23,59).toLocalDate();

        List<String> months = profitLossService.generateMonths(openedFinancialAccountYear.getStartDate(), toDate);
        List<ProfitAndLossGroupDTO> assetGroups = profitLossService.generateGroupsAndSubgroups(GroupType.ASSETS, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> liabilityGroups = profitLossService.generateGroupsAndSubgroups(GroupType.LIABILITIES, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> equitiesGroups = profitLossService.generateGroupsAndSubgroups(GroupType.EQUITIES, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> incomeGroups = profitLossService.generateGroupsAndSubgroups(GroupType.INCOME, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> expenseGroups = profitLossService.generateGroupsAndSubgroups(GroupType.EXPENSES, groupTypeSystemAccountMapMap, groupMapWithAccounts);


        List<MonthWithProfitAndLossAmountDTO> assetGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.ASSETS, openedFinancialAccountYear.getStartDate(), toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> liabilityGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.LIABILITIES, openedFinancialAccountYear.getStartDate(), toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> equitiesGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.EQUITIES, openedFinancialAccountYear.getStartDate(), toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> incomeGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.INCOME, openedFinancialAccountYear.getStartDate(), toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> expenditureGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.EXPENSES, openedFinancialAccountYear.getStartDate(), toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);

        List<BigDecimal> differences = calculateDifference(liabilityGroupAmount, equitiesGroupAmount);

        Resource resource = resourceLoader.getResource("classpath:/templates/jxls/BalanceSheet.xls");// templates/jxls/BalanceSheet.xls
        HSSFWorkbook workbook = new HSSFWorkbook(resource.getInputStream());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        byte[] barray = bos.toByteArray();
        InputStream is = new ByteArrayInputStream(barray);
        InputStream template = resource.getInputStream();
        OutputStream outputStream = new ByteArrayOutputStream() ; // new FileOutputStream(outputResource.getFile());
        jxlsGenerator.balanceSheetBuilder( months, assetGroups, liabilityGroups, equitiesGroups, incomeGroups, expenseGroups, assetGroupAmount, equitiesGroupAmount, liabilityGroupAmount, incomeGroupAmount, expenditureGroupAmount, differences, outputStream, template);
        ByteArrayOutputStream baos =(ByteArrayOutputStream) outputStream; //(ByteArrayOutputStream) outputStream; //new ByteArrayOutputStream();
        byte[] data = baos.toByteArray();
        outputStream.write(data);
        return new ByteArrayInputStream(baos.toByteArray());
    }


    public List<BigDecimal> calculateDifference(List<MonthWithProfitAndLossAmountDTO> liabilityGroupAmount, List<MonthWithProfitAndLossAmountDTO> equitiesGroiupAmount){
        List<BigDecimal> differences = new ArrayList<>();
        for(int i=0; i<equitiesGroiupAmount.size();i++){
            BigDecimal difference = ( liabilityGroupAmount.get(i).getGroupTypeTotal().add(equitiesGroiupAmount.get(i).getGroupTypeTotal()));
            differences.add(difference);
        }
        return differences;
    }
}
