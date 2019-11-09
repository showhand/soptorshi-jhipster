package org.soptorshi.service;

import com.itextpdf.text.DocumentException;
import org.soptorshi.config.JxlsGenerator;
import org.soptorshi.domain.MstAccount;
import org.soptorshi.domain.MstGroup;
import org.soptorshi.domain.SystemGroupMap;
import org.soptorshi.domain.enumeration.GroupType;
import org.soptorshi.repository.MstAccountRepository;
import org.soptorshi.repository.SystemGroupMapRepository;
import org.soptorshi.repository.extended.MstGroupExtendedRepository;
import org.soptorshi.service.dto.ChartsOfAccountsDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ChartOfAccountsExcelReportService {
    private MstGroupExtendedRepository mstGroupExtendedRepository;
    private MstAccountRepository mstAccountRepository;
    private SystemGroupMapRepository systemGroupMapRepository;
    private JxlsGenerator jxlsGenerator;

    public ChartOfAccountsExcelReportService(MstGroupExtendedRepository mstGroupExtendedRepository, MstAccountRepository mstAccountRepository, SystemGroupMapRepository systemGroupMapRepository, JxlsGenerator jxlsGenerator) {
        this.mstGroupExtendedRepository = mstGroupExtendedRepository;
        this.mstAccountRepository = mstAccountRepository;
        this.systemGroupMapRepository = systemGroupMapRepository;
        this.jxlsGenerator = jxlsGenerator;
    }

    public ByteArrayInputStream createChartsOrAccountReport() throws Exception, DocumentException {
        List<SystemGroupMap> systemGroupMaps = systemGroupMapRepository.findAll();
        Map<GroupType, SystemGroupMap> groupTypeMapWithSystemGroup = systemGroupMaps.stream().collect(Collectors.toMap(s->s.getGroupType(), s->s));
        List<MstAccount> accounts = mstAccountRepository.findAll();
        Map<Long, List<MstAccount>> groupMapWithAccounts = accounts.stream().collect(Collectors.groupingBy(a->a.getGroup().getId()));

        Long assetGroupId = groupTypeMapWithSystemGroup.get(GroupType.ASSETS).getGroup().getId();
        List<MstGroup> assetGroups = mstGroupExtendedRepository.findByMainGroup(assetGroupId);
        Long liabilitiesGroupId = groupTypeMapWithSystemGroup.get(GroupType.LIABILITIES).getGroup().getId();
        List<MstGroup> liabilities = mstGroupExtendedRepository.findByMainGroup(liabilitiesGroupId);
        Long incomeGroupId = groupTypeMapWithSystemGroup.get(GroupType.INCOME).getGroup().getId();
        List<MstGroup> income = mstGroupExtendedRepository.findByMainGroup(incomeGroupId);
        Long expenditureGroupId = groupTypeMapWithSystemGroup.get(GroupType.EXPENSES).getGroup().getId();
        List<MstGroup> expenditure = mstGroupExtendedRepository.findByMainGroup(expenditureGroupId);

        List<ChartsOfAccountsDTO> assetExcelGroup = createChartsOfAccountsExcelMap(assetGroups, groupMapWithAccounts);
        List<ChartsOfAccountsDTO> liabilitiesExcelGroup = createChartsOfAccountsExcelMap(liabilities, groupMapWithAccounts);
        List<ChartsOfAccountsDTO> incomeExcelGroup = createChartsOfAccountsExcelMap(income, groupMapWithAccounts);
        List<ChartsOfAccountsDTO> expenditureExcelGroup = createChartsOfAccountsExcelMap(expenditure, groupMapWithAccounts);

        InputStream template = new FileInputStream("ChartsOfAccounts.xls"); // ChartsOfAccountReportService.class.getResourceAsStream("/org/soptorshi/service/ChartsOfAccounts.xls");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStream outputStream = new FileOutputStream("ChartsOfAccounts.xls");
        outputStream.write(baos.toByteArray());
        jxlsGenerator.build(assetExcelGroup, liabilitiesExcelGroup, incomeExcelGroup, expenditureExcelGroup, outputStream, template);
        baos.writeTo(outputStream);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    List<ChartsOfAccountsDTO> createChartsOfAccountsExcelMap(List<MstGroup> groups, Map<Long, List<MstAccount>> groupAccountsMap){
        List<ChartsOfAccountsDTO> chartsOfAccountsDTOS = new ArrayList<>();
        for(MstGroup group: groups){
            if(groupAccountsMap.containsKey(group.getId())){
                for(MstAccount account: groupAccountsMap.get(group.getId())){
                    ChartsOfAccountsDTO chartsOfAccountsDTO = new ChartsOfAccountsDTO(group.getName(), account.getName());
                    chartsOfAccountsDTOS.add(chartsOfAccountsDTO);
                }
            }else{
                chartsOfAccountsDTOS.add(new ChartsOfAccountsDTO(group.getName(),""));
            }
        }

        return chartsOfAccountsDTOS;
    }
}
