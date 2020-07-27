package org.soptorshi.service.extended;

import io.undertow.security.idm.Account;
import org.soptorshi.domain.AccountBalance;
import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.domain.MstAccount;
import org.soptorshi.domain.MstGroup;
import org.soptorshi.domain.enumeration.AccountType;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.FinancialYearStatus;
import org.soptorshi.domain.enumeration.GroupType;
import org.soptorshi.repository.AccountBalanceRepository;
import org.soptorshi.repository.FinancialAccountYearRepository;
import org.soptorshi.repository.extended.*;
import org.soptorshi.repository.search.FinancialAccountYearSearchRepository;
import org.soptorshi.service.FinancialAccountYearService;
import org.soptorshi.service.dto.FinancialAccountYearDTO;
import org.soptorshi.service.mapper.FinancialAccountYearMapper;
import org.soptorshi.utils.SoptorshiUtils;
import org.soptorshi.web.rest.errors.CustomParameterizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class FinancialAccountYearExtendedService extends FinancialAccountYearService {
    private FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository;
    private final AccountBalanceRepository accountBalanceRepository;
    private final SystemGroupMapExtendedRepository systemGroupMapExtendedRepository;
    private final MstGroupExtendedRepository mstGroupExtendedRepository;
    private final MstAccountExtendedRepository mstAccountExtendedRepository;
    private final AccountBalanceExtendedRepository accountBalanceExtendedRepository;
    private final SystemAccountMapExtendedRepository systemAccountMapExtendedRepository;

    public FinancialAccountYearExtendedService(FinancialAccountYearRepository financialAccountYearRepository,
                                               FinancialAccountYearMapper financialAccountYearMapper,
                                               FinancialAccountYearSearchRepository financialAccountYearSearchRepository,
                                               FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository,
                                               AccountBalanceRepository accountBalanceRepository,
                                               SystemGroupMapExtendedRepository systemGroupMapExtendedRepository,
                                               MstGroupExtendedRepository mstGroupExtendedRepository,
                                               MstAccountExtendedRepository mstAccountExtendedRepository,
                                               AccountBalanceExtendedRepository accountBalanceExtendedRepository,
                                               SystemAccountMapExtendedRepository systemAccountMapExtendedRepository) {
        super(financialAccountYearRepository, financialAccountYearMapper, financialAccountYearSearchRepository);
        this.financialAccountYearExtendedRepository = financialAccountYearExtendedRepository;
        this.accountBalanceRepository = accountBalanceRepository;
        this.systemGroupMapExtendedRepository = systemGroupMapExtendedRepository;
        this.mstGroupExtendedRepository = mstGroupExtendedRepository;
        this.mstAccountExtendedRepository = mstAccountExtendedRepository;
        this.accountBalanceExtendedRepository = accountBalanceExtendedRepository;
        this.systemAccountMapExtendedRepository = systemAccountMapExtendedRepository;
    }

    @Override
    public FinancialAccountYearDTO save(FinancialAccountYearDTO financialAccountYearDTO) {
        financialAccountYearDTO.setDurationStr(SoptorshiUtils.formatDate(financialAccountYearDTO.getStartDate(),"dd-MM-yyyy")+" to "+ SoptorshiUtils.formatDate(financialAccountYearDTO.getEndDate(),"dd-MM-yyyy"));
        if(financialAccountYearDTO.getId()!=null){
            FinancialAccountYear existingFinancialAccountYear = financialAccountYearExtendedRepository.getOne(financialAccountYearDTO.getId());
            if(existingFinancialAccountYear.getStatus().equals(FinancialYearStatus.ACTIVE) && financialAccountYearDTO.getStatus().equals(FinancialYearStatus.NOT_ACTIVE)){
                //close financial account year
                FinancialAccountYear openedFinancialAccountYear = financialAccountYearExtendedRepository.getByStatus(FinancialYearStatus.ACTIVE);
                if(openedFinancialAccountYear==null)
                    return super.findOne(existingFinancialAccountYear.getId()).get();


            }
        }
        return super.save(financialAccountYearDTO);
    }

    public void transferAccountBalanceToNewFinancialAccountYear(FinancialAccountYear pNewFinancialAccountYear){
        MstGroup incomeGroup = systemGroupMapExtendedRepository.getByGroupType(GroupType.INCOME).getGroup();
        MstGroup expenditureGroup = systemGroupMapExtendedRepository.getByGroupType(GroupType.EXPENSES).getGroup();
        MstGroup assetGroup = systemGroupMapExtendedRepository.getByGroupType(GroupType.ASSETS).getGroup();
        MstGroup liabilitiesGroup = systemGroupMapExtendedRepository.getByGroupType(GroupType.LIABILITIES).getGroup();

        List<MstGroup> incomeGroupList = mstGroupExtendedRepository.findByMainGroup(incomeGroup.getId());
        List<MstGroup> expenditureGroupList = mstGroupExtendedRepository.findByMainGroup(expenditureGroup.getId());
        List<MstGroup> assetGroupList = mstGroupExtendedRepository.findByMainGroup(assetGroup.getId());
        List<MstGroup> liabilitiesGroupList = mstGroupExtendedRepository.findByMainGroup(liabilitiesGroup.getId());

        List<MstAccount> incomeRelatedAccountList = mstAccountExtendedRepository.getAllByGroup(incomeGroup);
        List<MstAccount> expenditureRelatedAccountList = mstAccountExtendedRepository.getAllByGroup(expenditureGroup);
        List<MstAccount> assetRelatedAccountList = mstAccountExtendedRepository.getAllByGroup(assetGroup);
        List<MstAccount> liabilitiesRelatedAccountList = mstAccountExtendedRepository.getAllByGroup(liabilitiesGroup);

        List<MstAccount> combinedAccountList = new ArrayList<>();
        combinedAccountList.addAll(incomeRelatedAccountList);
        combinedAccountList.addAll(expenditureRelatedAccountList);
        combinedAccountList.addAll(assetRelatedAccountList);
        combinedAccountList.addAll(liabilitiesRelatedAccountList);

        List<AccountBalance> accountBalanceList = accountBalanceExtendedRepository.findByFinancialAccountYear_Status(FinancialYearStatus.ACTIVE);
        Map<Long, AccountBalance> accountBalanceMapWithAccountId
            = accountBalanceList
            .parallelStream()
            .collect(Collectors.toMap(a->a.getAccount().getId(), a->a));


        List<AccountBalance> transferredAssetAndLiabilitiesAccountBalanceList = transferAssetRelatedAccountsToNewYearAccountBalance(assetRelatedAccountList, liabilitiesRelatedAccountList, accountBalanceMapWithAccountId, pNewFinancialAccountYear);
        List<AccountBalance> transferredIncomeAndExpenditureAccountBalanceList = transferIncomeAndExpenditureAccountBalance(incomeRelatedAccountList, expenditureRelatedAccountList, accountBalanceMapWithAccountId, pNewFinancialAccountYear);
        transferredAssetAndLiabilitiesAccountBalanceList.addAll(transferredIncomeAndExpenditureAccountBalanceList);

        accountBalanceExtendedRepository.saveAll(transferredAssetAndLiabilitiesAccountBalanceList);
    }

    private List<AccountBalance> transferAssetRelatedAccountsToNewYearAccountBalance(
        List<MstAccount> assetRelatedAccountList,
        List<MstAccount> liabilitiesRelatedAccountList,
        Map<Long, AccountBalance> accountBalanceMapWithAccountId,
        FinancialAccountYear financialAccountYear
    ){
        assetRelatedAccountList.addAll(liabilitiesRelatedAccountList);
        List<AccountBalance> newTransferredAssetAndLiabilitiesAccountBalanceList = new ArrayList<>();

        for(MstAccount account: assetRelatedAccountList){
            AccountBalance previousAccountBalance = accountBalanceMapWithAccountId.get(account.getId());
            BigDecimal previousTotalDrBalance = previousAccountBalance.getTotDebitTrans();
            BigDecimal previousTotalCrBalance = previousAccountBalance.getTotCreditTrans();

            AccountBalance newAccountBalance = new AccountBalance();
            newAccountBalance.setAccount(account);
            newAccountBalance.setFinancialAccountYear(financialAccountYear);
            newAccountBalance.setTotDebitTrans(BigDecimal.ZERO);
            newAccountBalance.setTotCreditTrans(BigDecimal.ZERO);
            BigDecimal openingBalance = previousTotalDrBalance.subtract(previousTotalCrBalance);
            newAccountBalance.setYearOpenBalance(openingBalance.abs());
            newAccountBalance.setYearOpenBalanceType(openingBalance.compareTo(BigDecimal.ONE)==1? BalanceType.DEBIT: BalanceType.CREDIT);
            newTransferredAssetAndLiabilitiesAccountBalanceList.add(newAccountBalance);
        }
        return newTransferredAssetAndLiabilitiesAccountBalanceList;
    }

    private List<AccountBalance> transferIncomeAndExpenditureAccountBalance(
        List<MstAccount> incomeRelatedAccountList,
        List<MstAccount> expenditureRelatedAccountList,
        Map<Long, AccountBalance> accountBalanceMapWithId,
        FinancialAccountYear financialAccountYear
    ){
        List<AccountBalance> newTransferredIncomeAccountBalance = new ArrayList<>();
        List<AccountBalance> newTransferredExpenditureAccountBalance = new ArrayList<>();

        BigDecimal incomeTotalDebit = new BigDecimal(0);
        BigDecimal incomeTotalCredit = new BigDecimal(0);
        BigDecimal expenditureTotalDebit = new BigDecimal(0);
        BigDecimal expenditureTotalCredit = new BigDecimal(0);


        for(MstAccount account : incomeRelatedAccountList) {
            AccountBalance previousIncomeAccountBalance = accountBalanceMapWithId.get(account.getId());
            incomeTotalDebit = incomeTotalDebit.add(previousIncomeAccountBalance.getTotDebitTrans());
            incomeTotalCredit = incomeTotalCredit.add(previousIncomeAccountBalance.getTotCreditTrans());
            AccountBalance newAccountBalance = new AccountBalance();
            newAccountBalance.setAccount(account);
            newAccountBalance.setFinancialAccountYear(financialAccountYear);
            newAccountBalance.setTotDebitTrans(BigDecimal.ZERO);
            newAccountBalance.setTotCreditTrans(BigDecimal.ZERO);
            newTransferredIncomeAccountBalance.add(newAccountBalance);
        }

        for(MstAccount account : expenditureRelatedAccountList) {
            AccountBalance previousExpenditureAccountBalance = accountBalanceMapWithId.get(account.getId());
            expenditureTotalDebit = expenditureTotalDebit.add(previousExpenditureAccountBalance.getTotDebitTrans());
            expenditureTotalCredit = expenditureTotalCredit.add(previousExpenditureAccountBalance.getTotCreditTrans());
            AccountBalance newAccountBalance = new AccountBalance();
            newAccountBalance.setAccount(account);
            newAccountBalance.setFinancialAccountYear(financialAccountYear);
            newAccountBalance.setTotDebitTrans(BigDecimal.ZERO);
            newAccountBalance.setTotCreditTrans(BigDecimal.ZERO);
            newTransferredExpenditureAccountBalance.add(newAccountBalance);
        }


        BigDecimal totalIncome = incomeTotalDebit.subtract(incomeTotalCredit);
        BigDecimal totalExpense = expenditureTotalDebit.subtract(expenditureTotalCredit);

        AccountBalance retailEarningsAccountBalance =
            configureRetailEarningForNewFinancialAccountYear(totalIncome, totalExpense, financialAccountYear);

        newTransferredExpenditureAccountBalance.add(retailEarningsAccountBalance);
        newTransferredExpenditureAccountBalance.addAll(newTransferredIncomeAccountBalance);
        return newTransferredExpenditureAccountBalance;
    }

    private AccountBalance configureRetailEarningForNewFinancialAccountYear(BigDecimal totalIncome,
                                                                                   BigDecimal totalExpense, FinancialAccountYear financialAccountYear) {
        MstAccount retailEarningsAccount = systemAccountMapExtendedRepository.findByAccountType(AccountType.RETAILED_EARNING).getAccount(); //= mstAccountExtendedRepository.getRetailedAccount();
        AccountBalance accountBalance = accountBalanceExtendedRepository.findByFinancialAccountYear_StatusAndAccount_Id(FinancialYearStatus.ACTIVE, retailEarningsAccount.getId());
        accountBalance.setId(null);
        accountBalance.setFinancialAccountYear(financialAccountYear);
        BigDecimal yearOpenBalance = totalIncome.subtract(totalExpense);
        accountBalance.setYearOpenBalance(yearOpenBalance.abs());
        accountBalance.setYearOpenBalanceType(yearOpenBalance.compareTo(new BigDecimal(0)) < 0 ? BalanceType.CREDIT
            : BalanceType.DEBIT);
        if(accountBalance.getYearOpenBalanceType().equals(BalanceType.DEBIT))
            accountBalance.setTotDebitTrans(accountBalance.getTotDebitTrans().add(yearOpenBalance));
        else
            accountBalance.setTotCreditTrans(accountBalance.getTotCreditTrans().add(yearOpenBalance));
        return accountBalance;
    }

}
