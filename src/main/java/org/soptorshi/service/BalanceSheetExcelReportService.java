package org.soptorshi.service;

import com.itextpdf.text.DocumentException;
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
import org.soptorshi.repository.extended.AccountBalanceExtendedRepository;
import org.soptorshi.repository.extended.DtTransactionExtendedRepository;
import org.soptorshi.repository.extended.FinancialAccountYearExtendedRepository;
import org.soptorshi.repository.extended.MstGroupExtendedRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BalanceSheetExcelReportService {
    private MstGroupExtendedRepository mstGroupExtendedRepository;
    private MstAccountRepository mstAccountRepository;
    private SystemGroupMapRepository systemGroupMapRepository;
    private AccountBalanceExtendedRepository accountBalanceExtendedRepository;
    private DtTransactionExtendedRepository dtTransactionExtendedRepository;
    private FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository;


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
            .filter(t->t.getBalanceType().equals(BalanceType.DEBIT))
            .collect(Collectors.groupingBy(t->t.getAccount().getId()));

        Map<Long, List<DtTransaction>> accountMapTotalCreditBalance = dtTransactions
            .stream()
            .filter(t->t.getBalanceType().equals(BalanceType.CREDIT))
            .collect(Collectors.groupingBy(t->t.getAccount().getId()));


        return null;
    }
}
