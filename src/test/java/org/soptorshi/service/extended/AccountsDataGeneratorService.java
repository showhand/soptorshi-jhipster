package org.soptorshi.service.extended;

import org.soptorshi.domain.MstAccount;
import org.soptorshi.domain.MstGroup;
import org.soptorshi.domain.SystemGroupMap;
import org.soptorshi.domain.enumeration.GroupType;
import org.soptorshi.domain.enumeration.ReservedFlag;
import org.soptorshi.repository.MstGroupRepository;
import org.soptorshi.repository.extended.MstAccountExtendedRepository;
import org.soptorshi.repository.extended.SystemGroupMapExtendedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsDataGeneratorService {
    @Autowired
    private MstGroupRepository mstGroupRepository;
    @Autowired
    private MstAccountExtendedRepository mstAccountRepository;
    @Autowired
    private SystemGroupMapExtendedRepository systemGroupMapExtendedRepository;

    public void createProfitLossTestData(){
        createTestGroups();
    }

    private void createTestGroups(){
        MstGroup assetGroup = new MstGroup();
        assetGroup.setName("Asset");
        assetGroup.setReservedFlag(ReservedFlag.RESERVED);
        mstGroupRepository.save(assetGroup);
        SystemGroupMap systemGroupMap = new SystemGroupMap();
        systemGroupMap.setGroup(assetGroup);
        systemGroupMap.setGroupType(GroupType.ASSETS);
        systemGroupMapExtendedRepository.save(systemGroupMap);

        MstGroup incomeGroup = new MstGroup();
        incomeGroup.setName("Income (Revenue)");
        incomeGroup.setReservedFlag(ReservedFlag.RESERVED);
        incomeGroup = mstGroupRepository.save(incomeGroup);
        createIncomeAccounts(incomeGroup);
        systemGroupMap = new SystemGroupMap();
        systemGroupMap.setGroup(incomeGroup);
        systemGroupMap.setGroupType(GroupType.INCOME);
        systemGroupMapExtendedRepository.save(systemGroupMap);

        MstGroup expenseGroup = new MstGroup();
        expenseGroup.setName("Expenses");
        expenseGroup.setReservedFlag(ReservedFlag.RESERVED);
        expenseGroup = mstGroupRepository.save(expenseGroup);
        createExpenseAccounts(expenseGroup);
        systemGroupMap = new SystemGroupMap();
        systemGroupMap.setGroup(expenseGroup);
        systemGroupMap.setGroupType(GroupType.EXPENSES);
        systemGroupMapExtendedRepository.save(systemGroupMap);
    }

    private void createIncomeAccounts(MstGroup mstGroup){
        MstAccount incomeAccount = new MstAccount();
        incomeAccount.setGroup(mstGroup);
        incomeAccount.setName("A");
        incomeAccount.setReservedFlag(ReservedFlag.NOT_RESERVED);
        mstAccountRepository.save(incomeAccount);
    }

    private void createExpenseAccounts(MstGroup mstGroup){
        MstAccount expenseAccount = new MstAccount();
        expenseAccount.setGroup(mstGroup);
        expenseAccount.setName("B");
        expenseAccount.setReservedFlag(ReservedFlag.NOT_RESERVED);
        mstAccountRepository.save(expenseAccount);
    }


}
