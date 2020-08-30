package org.soptorshi.service.extended;

import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.soptorshi.config.JxlsGenerator;
import org.soptorshi.domain.DtTransaction;
import org.soptorshi.domain.MstAccount;
import org.soptorshi.domain.MstGroup;
import org.soptorshi.domain.SystemGroupMap;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.GroupType;
import org.soptorshi.repository.MstAccountRepository;
import org.soptorshi.repository.extended.DtTransactionExtendedRepository;
import org.soptorshi.repository.extended.MstAccountExtendedRepository;
import org.soptorshi.repository.extended.MstGroupExtendedRepository;
import org.soptorshi.repository.extended.SystemGroupMapExtendedRepository;
import org.soptorshi.security.report.SoptorshiPdfCell;
import org.soptorshi.service.dto.extended.*;
import org.soptorshi.utils.SoptorshiUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.security.acl.Group;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.soptorshi.utils.SoptorshiUtils.mLiteFont;

@Service
public class ProfitLossService {

    private ResourceLoader resourceLoader;
    private JxlsGenerator jxlsGenerator;
    private MstGroupExtendedRepository mstGroupExtendedRepository;
    private SystemGroupMapExtendedRepository systemGroupMapExtendedRepository;
    private MstAccountExtendedRepository mstAccountExtendedRepository;
    private DtTransactionExtendedRepository dtTransactionExtendedRepository;


    List<SystemGroupMap> systemGroupMaps;
    Map<GroupType, Long> groupTypeSystemAccountMapMap;
    List<MstAccount> accounts;
    Map<Long, List<MstAccount>> groupMapWithAccounts;

    public ProfitLossService(ResourceLoader resourceLoader,
                             JxlsGenerator jxlsGenerator,
                             MstGroupExtendedRepository mstGroupExtendedRepository,
                             SystemGroupMapExtendedRepository systemGroupMapExtendedRepository,
                             MstAccountExtendedRepository mstAccountExtendedRepository,
                             DtTransactionExtendedRepository dtTransactionExtendedRepository) {
        this.resourceLoader = resourceLoader;
        this.jxlsGenerator = jxlsGenerator;
        this.mstGroupExtendedRepository = mstGroupExtendedRepository;
        this.systemGroupMapExtendedRepository = systemGroupMapExtendedRepository;
        this.mstAccountExtendedRepository = mstAccountExtendedRepository;
        this.dtTransactionExtendedRepository = dtTransactionExtendedRepository;
    }

    public ByteArrayInputStream createReport(LocalDate fromDate, LocalDate toDate) throws Exception{
        accounts = mstAccountExtendedRepository.findAll();
        systemGroupMaps = systemGroupMapExtendedRepository.findAll();
        groupTypeSystemAccountMapMap = systemGroupMaps.stream().collect(Collectors.toMap(s->s.getGroupType(), s->s.getGroup().getId()));
        groupMapWithAccounts = accounts.stream().collect(Collectors.groupingBy(a->a.getGroup().getId()));



        toDate = toDate.atTime(23,59).toLocalDate();
        List<String> months = generateMonths(fromDate, toDate);
        List<ProfitAndLossGroupDTO> revenueGroups = generateGroupsAndSubgroups(GroupType.INCOME, groupTypeSystemAccountMapMap, groupMapWithAccounts);
        List<ProfitAndLossGroupDTO> expenseGroups = generateGroupsAndSubgroups(GroupType.EXPENSES, groupTypeSystemAccountMapMap, groupMapWithAccounts);


        List<MonthWithProfitAndLossAmountDTO> revenueGroupAmount = generateProfitAndLossAmount(GroupType.INCOME , fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts );
        List<MonthWithProfitAndLossAmountDTO> expenseGroupAmount = generateProfitAndLossAmount(GroupType.EXPENSES , fromDate, toDate, groupTypeSystemAccountMapMap, groupMapWithAccounts);

        List<BigDecimal> differences = calculateDifference(revenueGroupAmount, expenseGroupAmount);

        Resource resource = resourceLoader.getResource("classpath:/templates/jxls/ProfitAndLoss.xls");// templates/jxls/ChartsOfAccounts.xls
        HSSFWorkbook workbook = new HSSFWorkbook(resource.getInputStream());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        byte[] barray = bos.toByteArray();
        InputStream is = new ByteArrayInputStream(barray);
        InputStream template = resource.getInputStream();
        OutputStream outputStream = new ByteArrayOutputStream() ; // new FileOutputStream(outputResource.getFile());
        jxlsGenerator.profitAndLossBuilder( months, revenueGroups, expenseGroups, revenueGroupAmount, expenseGroupAmount, differences, outputStream, template);
        ByteArrayOutputStream baos =(ByteArrayOutputStream) outputStream; //(ByteArrayOutputStream) outputStream; //new ByteArrayOutputStream();
        byte[] data = baos.toByteArray();
        outputStream.write(data);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public List<BigDecimal> calculateDifference(List<MonthWithProfitAndLossAmountDTO> revenueGroupAmount, List<MonthWithProfitAndLossAmountDTO> expenseGroupAmount){
        List<BigDecimal> differences = new ArrayList<>();
        for(int i=0; i<revenueGroupAmount.size();i++){
            BigDecimal difference = revenueGroupAmount.get(i).getGroupTypeTotal().subtract(expenseGroupAmount.get(i).getGroupTypeTotal());
            differences.add(difference);
        }
        return differences;
    }


    public List<MonthWithProfitAndLossAmountDTO> generateProfitAndLossAmount(GroupType groupType, LocalDate fromDate, LocalDate toDate,     Map<GroupType, Long> groupTypeSystemAccountMapMap, Map<Long, List<MstAccount>> groupMapWithAccounts){
        List<MonthWithProfitAndLossAmountDTO> monthWithProfitAndLossAmountDTOS = new ArrayList<>();

        LocalDate lastDate = LocalDate.now();
        LocalDate initialFromDate = fromDate;

        while(!fromDate.isAfter(toDate)){
            lastDate = LocalDate.of(fromDate.getYear(), fromDate.getMonthValue(), fromDate.lengthOfMonth());
            String month = lastDate.getMonth().name()+"-"+fromDate.getYear();
            fromDate = lastDate;
            MonthWithProfitAndLossAmountDTO monthWithProfitAndLossAmountDTO = new MonthWithProfitAndLossAmountDTO();
            monthWithProfitAndLossAmountDTO.setMonth(month);

            List<ProfitAndLossGroupAmountDTO> profitAndLossGroupAmountDTOS = new ArrayList<>();
            BigDecimal totalMonthGroupTypeAmount = BigDecimal.ZERO;
            List<MstGroup> groups = mstGroupExtendedRepository.findByMainGroup(groupTypeSystemAccountMapMap.get(groupType));
            LocalDate lastDateWithOneDateExtended  = fromDate.plusDays(1);
            List<DtTransaction> dtTransactions = dtTransactionExtendedRepository.findByVoucherDateBetween(initialFromDate, lastDateWithOneDateExtended);
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

            for(MstGroup group: groups){
                ProfitAndLossGroupAmountDTO profitAndLossGroupAmountDTO = new ProfitAndLossGroupAmountDTO();
                profitAndLossGroupAmountDTO.setGroup(null);

                BigDecimal totalDebit = BigDecimal.ZERO;
                BigDecimal totalCredit = BigDecimal.ZERO;
                List<BigDecimal> accountBalances = new ArrayList<>();
                if(groupMapWithAccounts.containsKey(group.getId())){
                    for(MstAccount account: groupMapWithAccounts.get(group.getId())){
                        BigDecimal totalAccDebit = BigDecimal.ZERO;
                        BigDecimal totalAccCredit = BigDecimal.ZERO;


                        if(accountMapTotalDebitBalance.containsKey(account.getId()) || accountMapTotalCreditBalance.containsKey(account.getId())){
                            if(accountMapTotalDebitBalance.containsKey(account.getId()))
                                totalAccDebit = accountMapTotalDebitBalance.get(account.getId())
                                    .stream()
                                    .map(a-> a.getAmount())
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                            if(accountMapTotalCreditBalance.containsKey(account.getId()))
                                totalAccCredit = accountMapTotalCreditBalance.get(account.getId())
                                    .stream()
                                    .map(a-> a.getAmount())
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                            totalDebit = totalDebit.add(totalAccDebit);
                            totalCredit = totalCredit.add(totalAccCredit);
                            if(groupType.equals(GroupType.ASSETS) || groupType.equals(GroupType.EXPENSES) ||  groupType.equals(GroupType.FIXED_ASSETS) ||  groupType.equals(GroupType.CURRENT_ASSETS))
                                accountBalances.add(totalAccDebit.subtract(totalAccCredit));

                            else
                                accountBalances.add(totalAccCredit.subtract(totalAccDebit));
                        }else{
                            accountBalances.add(BigDecimal.ZERO);
                        }
                    }
                }

                profitAndLossGroupAmountDTO.setAccountAmounts(accountBalances);
                if(groupType.equals(GroupType.ASSETS) || groupType.equals(GroupType.EXPENSES) || groupType.equals(GroupType.FIXED_ASSETS) || groupType.equals(GroupType.CURRENT_ASSETS))
                    profitAndLossGroupAmountDTO.setTotalAmount(totalDebit.subtract(totalCredit));

                else
                    profitAndLossGroupAmountDTO.setTotalAmount(totalCredit.subtract(totalDebit));
                totalMonthGroupTypeAmount = totalMonthGroupTypeAmount.add(profitAndLossGroupAmountDTO.getTotalAmount());
                profitAndLossGroupAmountDTOS.add(profitAndLossGroupAmountDTO);

            }


            fromDate = fromDate.plusDays(1);
//            BigDecimal monthTotalGroupAmount = monthWithProfitAndLossAmountDTO.getGroupTypeTotal()==null? BigDecimal.ZERO: monthWithProfitAndLossAmountDTO.getGroupTypeTotal();
//            monthTotalGroupAmount = monthTotalGroupAmount
            monthWithProfitAndLossAmountDTO.setGroupAmounts(profitAndLossGroupAmountDTOS);
            monthWithProfitAndLossAmountDTO.setGroupTypeTotal(totalMonthGroupTypeAmount);
            monthWithProfitAndLossAmountDTOS.add(monthWithProfitAndLossAmountDTO);
        }

        return monthWithProfitAndLossAmountDTOS;
    }

    public List<ProfitAndLossGroupDTO> generateGroupsAndSubgroups(GroupType groupType, Map<GroupType, Long> groupTypeSystemAccountMapMap,     Map<Long, List<MstAccount>> groupMapWithAccounts){
        List<ProfitAndLossGroupDTO> profitAndLossGroupsAndSubGroups = new ArrayList<>();
        List<MstGroup> groups = mstGroupExtendedRepository.findByMainGroup(groupTypeSystemAccountMapMap.get(groupType));

        for(MstGroup group: groups){
            ProfitAndLossGroupDTO  profitAndLossGroupAndSubGroup = new ProfitAndLossGroupDTO();
            profitAndLossGroupAndSubGroup.setGroupName(group.getName());

            List<String> accounts = new ArrayList<>();
            if(groupMapWithAccounts.containsKey(group.getId())){
                for(MstAccount account: groupMapWithAccounts.get(group.getId())){
                    accounts.add(account.getName());
                }
            }

            profitAndLossGroupAndSubGroup.setAccounts(accounts);
            profitAndLossGroupAndSubGroup.setTotalAmount(group.getName()+" Total");

            profitAndLossGroupsAndSubGroups.add(profitAndLossGroupAndSubGroup);
        }
        return profitAndLossGroupsAndSubGroups;
    }

    public List<String> generateMonths(LocalDate fromDate, LocalDate toDate){
        List<String> months = new ArrayList<>();
        LocalDate lastDate = LocalDate.now();

        while(!fromDate.isAfter(toDate)){
            lastDate = LocalDate.of(fromDate.getYear(), fromDate.getMonthValue(), fromDate.lengthOfMonth());
            String month = lastDate.getMonth().name()+"-"+fromDate.getYear();
            months.add(month);
            fromDate = lastDate;
            fromDate = fromDate.plusDays(1);
        }
        return months;
    }

    private AccountWithMonthlyBalances generateComparingBalances(List<ProfitLossDto> revenue, List<ProfitLossDto> expense) {
        AccountWithMonthlyBalances accountWithMonthlyBalances = new AccountWithMonthlyBalances();
        return accountWithMonthlyBalances;
    }

    private List<ProfitLossDto> generateExpenses(LocalDate fromDate, LocalDate toDate) {
        List<ProfitLossDto> expenses = new ArrayList<>();
        return expenses;
    }

    private List<ProfitLossDto> generateRevenues(LocalDate fromDate, LocalDate toDate) {
        List<ProfitLossDto> revenues = new ArrayList<>();
        return revenues;
    }

    private List<ProfitLossDto> generateBody(GroupType groupType, LocalDate fromDate, LocalDate toDate){
        List<ProfitLossDto> profitLossBody = new ArrayList<>();
        SystemGroupMap systemGroupMap = systemGroupMapExtendedRepository.getByGroupType(groupType);
        List<MstGroup> childGroups = mstGroupExtendedRepository.findByMainGroup(systemGroupMap.getGroup().getId());
        for(MstGroup group: childGroups){
            ProfitLossDto profitLossChildBody = new ProfitLossDto();
            profitLossChildBody.setGroupName(group.getName());

            List<MstAccount> groupAccounts = mstAccountExtendedRepository.getAllByGroup(group);
            for(MstAccount account: groupAccounts){
                AccountWithMonthlyBalances accountWithMonthlyBalances = new AccountWithMonthlyBalances();
                accountWithMonthlyBalances.setAccountName(account.getName());

                while(fromDate.getYear()<= toDate.getYear() && fromDate.getMonth().getValue()<=toDate.getMonth().getValue()){

                }
            }
        }
        return profitLossBody;
    }
}
