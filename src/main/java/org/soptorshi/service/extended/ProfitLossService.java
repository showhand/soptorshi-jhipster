package org.soptorshi.service.extended;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.soptorshi.config.JxlsGenerator;
import org.soptorshi.domain.DtTransaction;
import org.soptorshi.domain.MstAccount;
import org.soptorshi.domain.MstGroup;
import org.soptorshi.domain.SystemGroupMap;
import org.soptorshi.domain.enumeration.GroupType;
import org.soptorshi.repository.MstAccountRepository;
import org.soptorshi.repository.extended.DtTransactionExtendedRepository;
import org.soptorshi.repository.extended.MstAccountExtendedRepository;
import org.soptorshi.repository.extended.MstGroupExtendedRepository;
import org.soptorshi.repository.extended.SystemGroupMapExtendedRepository;
import org.soptorshi.service.dto.extended.AccountWithMonthlyBalances;
import org.soptorshi.service.dto.extended.ProfitAndLossGroupDTO;
import org.soptorshi.service.dto.extended.ProfitLossDto;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<ProfitAndLossGroupDTO> revenueGroups = generateGroupsAndSubgroups(GroupType.INCOME);
        List<ProfitAndLossGroupDTO> expenseGroups = generateGroupsAndSubgroups(GroupType.EXPENSES);


        List<ProfitLossDto> revenue = generateRevenues(fromDate, toDate);
        List<ProfitLossDto> expense = generateExpenses(fromDate, toDate);
        AccountWithMonthlyBalances comparingBalances = generateComparingBalances(revenue, expense);

        Resource resource = resourceLoader.getResource("classpath:/templates/jxls/ProfitAndLoss.xls");// templates/jxls/ChartsOfAccounts.xls
        HSSFWorkbook workbook = new HSSFWorkbook(resource.getInputStream());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        byte[] barray = bos.toByteArray();
        InputStream is = new ByteArrayInputStream(barray);
        InputStream template = resource.getInputStream();
        OutputStream outputStream = new ByteArrayOutputStream() ; // new FileOutputStream(outputResource.getFile());
        jxlsGenerator.profitAndLossBuilder( months, revenueGroups, expenseGroups, comparingBalances, outputStream, template);
        ByteArrayOutputStream baos =(ByteArrayOutputStream) outputStream; //(ByteArrayOutputStream) outputStream; //new ByteArrayOutputStream();
        byte[] data = baos.toByteArray();
        outputStream.write(data);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public List<ProfitAndLossGroupDTO> generateGroupsAndSubgroups(GroupType groupType){
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
