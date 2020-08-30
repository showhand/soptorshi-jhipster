package org.soptorshi.service.extended;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.checkerframework.checker.units.qual.A;
import org.soptorshi.config.JxlsGenerator;
import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.FinancialYearStatus;
import org.soptorshi.domain.enumeration.GroupType;
import org.soptorshi.repository.extended.*;
import org.soptorshi.service.dto.extended.AccountsDTO;
import org.soptorshi.service.dto.extended.CashFlowBalanceDTO;
import org.soptorshi.service.dto.extended.MonthWithProfitAndLossAmountDTO;
import org.soptorshi.service.dto.extended.ProfitAndLossGroupDTO;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CashFlowService {
    private final ResourceLoader resourceLoader;
    private final JxlsGenerator jxlsGenerator;
    private final MstGroupExtendedRepository mstGroupExtendedRepository;
    private final SystemGroupMapExtendedRepository systemGroupMapExtendedRepository;
    private final MstAccountExtendedRepository mstAccountExtendedRepository;
    private final DtTransactionExtendedRepository dtTransactionExtendedRepository;
    private final ProfitLossService profitLossService;
    private final AccountBalanceExtendedRepository accountBalanceExtendedRepository;
    private final FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository;


    List<SystemGroupMap> systemGroupMaps;
    Map<GroupType, Long> groupTypeSystemAccountMapMap;
    List<MstAccount> accounts;
    Map<Long, List<MstAccount>> groupMapWithAccounts;
    Map<Long, AccountBalance> accountMapWithAccountBalance;

    public CashFlowService(ResourceLoader resourceLoader, JxlsGenerator jxlsGenerator, MstGroupExtendedRepository mstGroupExtendedRepository, SystemGroupMapExtendedRepository systemGroupMapExtendedRepository, MstAccountExtendedRepository mstAccountExtendedRepository, DtTransactionExtendedRepository dtTransactionExtendedRepository, ProfitLossService profitLossService, AccountBalanceExtendedRepository accountBalanceExtendedRepository, FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository) {
        this.resourceLoader = resourceLoader;
        this.jxlsGenerator = jxlsGenerator;
        this.mstGroupExtendedRepository = mstGroupExtendedRepository;
        this.systemGroupMapExtendedRepository = systemGroupMapExtendedRepository;
        this.mstAccountExtendedRepository = mstAccountExtendedRepository;
        this.dtTransactionExtendedRepository = dtTransactionExtendedRepository;
        this.profitLossService = profitLossService;
        this.accountBalanceExtendedRepository = accountBalanceExtendedRepository;
        this.financialAccountYearExtendedRepository = financialAccountYearExtendedRepository;
    }

    public ByteArrayInputStream createReport(LocalDate fromDate, LocalDate toDate) throws Exception{
        accounts = mstAccountExtendedRepository.findAll();
        systemGroupMaps = systemGroupMapExtendedRepository.findAll();
        groupTypeSystemAccountMapMap = systemGroupMaps.stream().collect(Collectors.toMap(s->s.getGroupType(), s->s.getGroup().getId()));
        groupMapWithAccounts = accounts.stream().collect(Collectors.groupingBy(a->a.getGroup().getId()));
        accountMapWithAccountBalance = accountBalanceExtendedRepository.findByFinancialAccountYear_Status(FinancialYearStatus.ACTIVE)
            .stream()
            .collect(Collectors.toMap(a->a.getAccount().getId(), a-> a));

        List<String> months = profitLossService.generateMonths(fromDate, toDate);
        List<ProfitAndLossGroupDTO> assetGroups = profitLossService.generateGroupsAndSubgroups(GroupType.ASSETS, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> liabilityGroups = profitLossService.generateGroupsAndSubgroups(GroupType.LIABILITIES, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> equitiesGroups = profitLossService.generateGroupsAndSubgroups(GroupType.EQUITIES, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> incomeGroups = profitLossService.generateGroupsAndSubgroups(GroupType.INCOME, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> expenseGroups = profitLossService.generateGroupsAndSubgroups(GroupType.EXPENSES, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> depreciationGroups = profitLossService.generateGroupsAndSubgroups(GroupType.DEPRECIATION, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> currentAssetGroups = profitLossService.generateGroupsAndSubgroups(GroupType.CURRENT_ASSETS, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> fixedAssetGroups = profitLossService.generateGroupsAndSubgroups(GroupType.FIXED_ASSETS, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> currentLiabilityGroups = profitLossService.generateGroupsAndSubgroups(GroupType.CURRENT_LIABILITIES, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> loanGroups = profitLossService.generateGroupsAndSubgroups(GroupType.LOAN, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> shareCapitalGroups = profitLossService.generateGroupsAndSubgroups(GroupType.SHARE_CAPITAL, groupTypeSystemAccountMapMap, groupMapWithAccounts);




        List<MonthWithProfitAndLossAmountDTO> assetGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.ASSETS, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> liabilityGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.LIABILITIES, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> equitiesGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.EQUITIES, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> incomeGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.INCOME, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> expenditureGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.EXPENSES, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> depreciationGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.DEPRECIATION, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> currentAssetGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.CURRENT_ASSETS, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> fixedAssetGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.FIXED_ASSETS, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> currentLiabilityGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.CURRENT_LIABILITIES, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> loanGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.LOAN, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<MonthWithProfitAndLossAmountDTO> shareCapitalGroupAmount = profitLossService.generateProfitAndLossAmount(GroupType.SHARE_CAPITAL, fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);


        AccountsDTO.AccountsBuilder accountsBuilder = new AccountsDTO.AccountsBuilder()
            .months(months)
            .assetGroups(assetGroups)
            .liabilityGroups(liabilityGroups)
            .equitiesGroups(equitiesGroups)
            .incomeGroups(incomeGroups)
            .expenseGroups(expenseGroups)
            .depreciationGroups(depreciationGroups)
            .currentAssetGroups(currentAssetGroups)
            .fixedAssetGroups(fixedAssetGroups)
            .currentLiabilityGroups(currentLiabilityGroups)
            .loanGroups(loanGroups)
            .shareCapitalGroups(shareCapitalGroups)
            .assetGroupAmount(assetGroupAmount)
            .liabilityGroupAmount(liabilityGroupAmount)
            .equitiesGroupAmount(equitiesGroupAmount)
            .incomeGroupAmount(incomeGroupAmount)
            .expenditureGroupAmount(expenditureGroupAmount)
            .depreciationGroupAmount(depreciationGroupAmount)
            .currentAssetGroupAmount(currentAssetGroupAmount)
            .fixedAssetGroupAmount(fixedAssetGroupAmount)
            .currentLiabilityGroupAmount(currentLiabilityGroupAmount)
            .loanGroupAmount(loanGroupAmount)
            .shareCapitalGroupAmount(shareCapitalGroupAmount);

        AccountsDTO accountsDTO = new AccountsDTO(accountsBuilder);

        List<BigDecimal> cashMovements = calculateCashMovement(accountsDTO);
        List<BigDecimal> differences = profitLossService.calculateDifference(incomeGroupAmount, expenditureGroupAmount);


        CashFlowBalanceDTO cashFlowBalanceDTO = calculateOpenAndClosingBalances(accountsDTO, cashMovements);
        List<BigDecimal> openBalances = cashFlowBalanceDTO.getOpeningBalances();
        List<BigDecimal> closingBalances = cashFlowBalanceDTO.getClosingBalances();

        Resource resource = resourceLoader.getResource("classpath:/templates/jxls/CashFlow.xls");
        HSSFWorkbook workbook = new HSSFWorkbook(resource.getInputStream());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        byte[] barray = bos.toByteArray();
        InputStream is = new ByteArrayInputStream(barray);
        InputStream template = resource.getInputStream();
        OutputStream outputStream = new ByteArrayOutputStream() ; // new FileOutputStream(outputResource.getFile());
        jxlsGenerator.cashFlowBuilder( months, assetGroups, liabilityGroups, equitiesGroups, incomeGroups, expenseGroups,depreciationGroups, currentAssetGroups, fixedAssetGroups, currentLiabilityGroups, loanGroups,
            shareCapitalGroups, assetGroupAmount, equitiesGroupAmount, liabilityGroupAmount, incomeGroupAmount, expenditureGroupAmount, depreciationGroupAmount, currentAssetGroupAmount, fixedAssetGroupAmount, currentLiabilityGroupAmount, loanGroupAmount, shareCapitalGroupAmount, differences, cashMovements, openBalances, closingBalances, outputStream, template);
        ByteArrayOutputStream baos =(ByteArrayOutputStream) outputStream; //(ByteArrayOutputStream) outputStream; //new ByteArrayOutputStream();
        byte[] data = baos.toByteArray();
        outputStream.write(data);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public CashFlowBalanceDTO calculateOpenAndClosingBalances(final AccountsDTO accountsDTO,final List<BigDecimal> cashMovements){
        List<BigDecimal> openBalances = new ArrayList<>();
        List<BigDecimal> closingBalances = new ArrayList<>();
        openBalances.add(calculateFirstOpenBalance(accountsDTO));

        for(int i=0; i<cashMovements.size(); i++){
            BigDecimal closingBalance = openBalances.get(i).subtract(cashMovements.get(i));
            closingBalances.add(closingBalance);
            if(i!=(cashMovements.size()-1))
                openBalances.add(closingBalance);
        }
        CashFlowBalanceDTO cashFlowBalanceDTO = new CashFlowBalanceDTO(openBalances, closingBalances);
        return cashFlowBalanceDTO;
    }

    public List<BigDecimal> calculateDifference(List<MonthWithProfitAndLossAmountDTO> liabilityGroupAmount, List<MonthWithProfitAndLossAmountDTO> equitiesGroiupAmount){
        List<BigDecimal> differences = new ArrayList<>();
        for(int i=0; i<equitiesGroiupAmount.size();i++){
            BigDecimal difference = ( liabilityGroupAmount.get(i).getGroupTypeTotal().add(equitiesGroiupAmount.get(i).getGroupTypeTotal()));
            differences.add(difference);
        }
        return differences;
    }

    public List<BigDecimal> calculateCashMovement(AccountsDTO accountsDTO){
        List<BigDecimal> cashMovement = new ArrayList<>();

        for(int i=0; i<accountsDTO.getMonths().size(); i++){
            BigDecimal movement = BigDecimal.ZERO;
            movement = movement.add(accountsDTO.getIncomeGroupAmount().get(i).getGroupTypeTotal())
                .subtract(accountsDTO.getExpenditureGroupAmount().get(i).getGroupTypeTotal())
                .add(accountsDTO.getDepreciationGroupAmount().get(i).getGroupTypeTotal())
                .add(accountsDTO.getCurrentAssetGroupAmount().get(i).getGroupTypeTotal())
                .add(accountsDTO.getFixedAssetGroupAmount().get(i).getGroupTypeTotal())
                .subtract(accountsDTO.getCurrentLiabilityGroupAmount().get(i).getGroupTypeTotal())
                .subtract(accountsDTO.getLoanGroupAmount().get(i).getGroupTypeTotal())
                .subtract(accountsDTO.getShareCapitalGroupAmount().get(i).getGroupTypeTotal());
            cashMovement.add(movement);
        }
        return cashMovement;
    }

    public BigDecimal calculateFirstOpenBalance(AccountsDTO accountsDTO){
        BigDecimal totalOpenBalance = BigDecimal.ZERO;
        BigDecimal incomeTotalOpenBalance = calculateOpenBalance(GroupType.INCOME);
        BigDecimal expenseTotalOpenBalance = calculateOpenBalance(GroupType.EXPENSES);
        BigDecimal depreciationTotalOpenBalance = calculateOpenBalance(GroupType.DEPRECIATION);
        BigDecimal currentAssetTotalOpenBalance = calculateOpenBalance(GroupType.CURRENT_ASSETS);
        BigDecimal fixedAssetTotalOpenBalance = calculateOpenBalance(GroupType.FIXED_ASSETS);
        BigDecimal currentLiabilitiesOpenBalance = calculateOpenBalance(GroupType.CURRENT_LIABILITIES);
        BigDecimal loanTotalBalance = calculateOpenBalance(GroupType.LOAN);
        BigDecimal shareCapitalTotalBalance = calculateOpenBalance(GroupType.SHARE_CAPITAL);

        totalOpenBalance = incomeTotalOpenBalance
            .subtract(expenseTotalOpenBalance)
            .add(depreciationTotalOpenBalance)
            .add(currentAssetTotalOpenBalance)
            .add(fixedAssetTotalOpenBalance)
            .subtract(currentLiabilitiesOpenBalance)
            .subtract(loanTotalBalance)
            .subtract(shareCapitalTotalBalance);
        return totalOpenBalance;
    }

    public BigDecimal calculateOpenBalance(GroupType groupType){
        BigDecimal totalOpenBalance = BigDecimal.ZERO;
        Long groupId = groupTypeSystemAccountMapMap.get(groupType);
        List<MstGroup> groups = mstGroupExtendedRepository.findByMainGroup(groupId);

        for(MstGroup group: groups){
            if(groupMapWithAccounts.containsKey(group.getId())){
                List<MstAccount> accounts = groupMapWithAccounts.get(group.getId());
                for(MstAccount account: accounts){
                    if(accountMapWithAccountBalance.containsKey(account.getId())){
                        AccountBalance accountBalance = accountMapWithAccountBalance.get(account.getId());
                        if(groupType.equals(GroupType.EXPENSES) || groupType.equals(GroupType.CURRENT_ASSETS) || groupType.equals(GroupType.FIXED_ASSETS)){
                            totalOpenBalance = accountBalance.getYearOpenBalanceType().equals(BalanceType.DEBIT)? totalOpenBalance.add(accountBalance.getYearOpenBalance()):  totalOpenBalance.subtract(accountBalance.getYearOpenBalance());
                        }else{
                            totalOpenBalance = accountBalance.getYearOpenBalanceType().equals(BalanceType.CREDIT)? totalOpenBalance.add(accountBalance.getYearOpenBalance()):  totalOpenBalance.subtract(accountBalance.getYearOpenBalance());
                        }
                    }
                }
            }

        }

        return totalOpenBalance;
    }
}
