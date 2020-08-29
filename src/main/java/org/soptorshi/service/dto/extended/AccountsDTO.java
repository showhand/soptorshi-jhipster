package org.soptorshi.service.dto.extended;

import org.soptorshi.domain.enumeration.GroupType;

import java.util.List;

public class AccountsDTO {
    private  List<String> months;
    private  List<ProfitAndLossGroupDTO> assetGroups;
    private  List<ProfitAndLossGroupDTO> liabilityGroups;
    private  List<ProfitAndLossGroupDTO> equitiesGroups;
    private  List<ProfitAndLossGroupDTO> incomeGroups;
    private  List<ProfitAndLossGroupDTO> expenseGroups;
    private  List<ProfitAndLossGroupDTO> depreciationGroups;
    private  List<ProfitAndLossGroupDTO> currentAssetGroups;
    private  List<ProfitAndLossGroupDTO> fixedAssetGroups;
    private  List<ProfitAndLossGroupDTO> currentLiabilityGroups;
    private  List<ProfitAndLossGroupDTO> loanGroups;
    private  List<ProfitAndLossGroupDTO> shareCapitalGroups;
    private  List<MonthWithProfitAndLossAmountDTO> assetGroupAmount ;
    private  List<MonthWithProfitAndLossAmountDTO> liabilityGroupAmount;
    private  List<MonthWithProfitAndLossAmountDTO> equitiesGroupAmount ;
    private  List<MonthWithProfitAndLossAmountDTO> incomeGroupAmount ;
    private  List<MonthWithProfitAndLossAmountDTO> expenditureGroupAmount;
    private  List<MonthWithProfitAndLossAmountDTO> depreciationGroupAmount;
    private  List<MonthWithProfitAndLossAmountDTO> currentAssetGroupAmount;
    private  List<MonthWithProfitAndLossAmountDTO> fixedAssetGroupAmount;
    private  List<MonthWithProfitAndLossAmountDTO> currentLiabilityGroupAmount;
    private  List<MonthWithProfitAndLossAmountDTO> loanGroupAmount;
    private  List<MonthWithProfitAndLossAmountDTO> shareCapitalGroupAmount;

    public AccountsDTO(AccountsBuilder accountsBuilder) {
        months = accountsBuilder.months;
        assetGroups = accountsBuilder.assetGroups;
        liabilityGroups = accountsBuilder.liabilityGroups;
        equitiesGroups = accountsBuilder.equitiesGroups;
        incomeGroups = accountsBuilder.incomeGroups;
        expenseGroups = accountsBuilder.expenseGroups;
        depreciationGroups = accountsBuilder.depreciationGroups;
        currentAssetGroups = accountsBuilder.currentAssetGroups;
        fixedAssetGroups = accountsBuilder.fixedAssetGroups;
        currentLiabilityGroups = accountsBuilder.currentLiabilityGroups;
        loanGroups = accountsBuilder.loanGroups;
        shareCapitalGroups = accountsBuilder.shareCapitalGroups;
        assetGroupAmount = accountsBuilder.assetGroupAmount;
        liabilityGroupAmount = accountsBuilder.liabilityGroupAmount;
        equitiesGroupAmount = accountsBuilder.equitiesGroupAmount;
        incomeGroupAmount = accountsBuilder.incomeGroupAmount;
        expenditureGroupAmount = accountsBuilder.expenditureGroupAmount;
        depreciationGroupAmount = accountsBuilder.depreciationGroupAmount;
        currentAssetGroupAmount = accountsBuilder.currentAssetGroupAmount;
        fixedAssetGroupAmount = accountsBuilder.fixedAssetGroupAmount;
        currentLiabilityGroupAmount = accountsBuilder.currentLiabilityGroupAmount;
        loanGroupAmount = accountsBuilder.loanGroupAmount;
        shareCapitalGroupAmount = accountsBuilder.shareCapitalGroupAmount;
    }

    public List<String> getMonths() {
        return months;
    }

    public List<ProfitAndLossGroupDTO> getAssetGroups() {
        return assetGroups;
    }

    public List<ProfitAndLossGroupDTO> getLiabilityGroups() {
        return liabilityGroups;
    }

    public List<ProfitAndLossGroupDTO> getEquitiesGroups() {
        return equitiesGroups;
    }

    public List<ProfitAndLossGroupDTO> getIncomeGroups() {
        return incomeGroups;
    }

    public List<ProfitAndLossGroupDTO> getExpenseGroups() {
        return expenseGroups;
    }

    public List<ProfitAndLossGroupDTO> getDepreciationGroups() {
        return depreciationGroups;
    }

    public List<ProfitAndLossGroupDTO> getCurrentAssetGroups() {
        return currentAssetGroups;
    }

    public List<ProfitAndLossGroupDTO> getFixedAssetGroups() {
        return fixedAssetGroups;
    }

    public List<ProfitAndLossGroupDTO> getCurrentLiabilityGroups() {
        return currentLiabilityGroups;
    }

    public List<ProfitAndLossGroupDTO> getLoanGroups() {
        return loanGroups;
    }

    public List<ProfitAndLossGroupDTO> getShareCapitalGroups() {
        return shareCapitalGroups;
    }

    public List<MonthWithProfitAndLossAmountDTO> getAssetGroupAmount() {
        return assetGroupAmount;
    }

    public List<MonthWithProfitAndLossAmountDTO> getLiabilityGroupAmount() {
        return liabilityGroupAmount;
    }

    public List<MonthWithProfitAndLossAmountDTO> getEquitiesGroupAmount() {
        return equitiesGroupAmount;
    }

    public List<MonthWithProfitAndLossAmountDTO> getIncomeGroupAmount() {
        return incomeGroupAmount;
    }

    public List<MonthWithProfitAndLossAmountDTO> getExpenditureGroupAmount() {
        return expenditureGroupAmount;
    }

    public List<MonthWithProfitAndLossAmountDTO> getDepreciationGroupAmount() {
        return depreciationGroupAmount;
    }

    public List<MonthWithProfitAndLossAmountDTO> getCurrentAssetGroupAmount() {
        return currentAssetGroupAmount;
    }

    public List<MonthWithProfitAndLossAmountDTO> getFixedAssetGroupAmount() {
        return fixedAssetGroupAmount;
    }

    public List<MonthWithProfitAndLossAmountDTO> getCurrentLiabilityGroupAmount() {
        return currentLiabilityGroupAmount;
    }

    public List<MonthWithProfitAndLossAmountDTO> getLoanGroupAmount() {
        return loanGroupAmount;
    }

    public List<MonthWithProfitAndLossAmountDTO> getShareCapitalGroupAmount() {
        return shareCapitalGroupAmount;
    }

    public static class AccountsBuilder{
        private  List<String> months;
        private  List<ProfitAndLossGroupDTO> assetGroups;
        private  List<ProfitAndLossGroupDTO> liabilityGroups;
        private  List<ProfitAndLossGroupDTO> equitiesGroups;
        private  List<ProfitAndLossGroupDTO> incomeGroups;
        private  List<ProfitAndLossGroupDTO> expenseGroups;
        private  List<ProfitAndLossGroupDTO> depreciationGroups;
        private  List<ProfitAndLossGroupDTO> currentAssetGroups;
        private  List<ProfitAndLossGroupDTO> fixedAssetGroups;
        private  List<ProfitAndLossGroupDTO> currentLiabilityGroups;
        private  List<ProfitAndLossGroupDTO> loanGroups;
        private  List<ProfitAndLossGroupDTO> shareCapitalGroups;
        private  List<MonthWithProfitAndLossAmountDTO> assetGroupAmount ;
        private  List<MonthWithProfitAndLossAmountDTO> liabilityGroupAmount;
        private  List<MonthWithProfitAndLossAmountDTO> equitiesGroupAmount ;
        private  List<MonthWithProfitAndLossAmountDTO> incomeGroupAmount ;
        private  List<MonthWithProfitAndLossAmountDTO> expenditureGroupAmount;
        private  List<MonthWithProfitAndLossAmountDTO> depreciationGroupAmount;
        private  List<MonthWithProfitAndLossAmountDTO> currentAssetGroupAmount;
        private  List<MonthWithProfitAndLossAmountDTO> fixedAssetGroupAmount;
        private  List<MonthWithProfitAndLossAmountDTO> currentLiabilityGroupAmount;
        private  List<MonthWithProfitAndLossAmountDTO> loanGroupAmount;
        private  List<MonthWithProfitAndLossAmountDTO> shareCapitalGroupAmount;

        public AccountsBuilder() {
        }

        public AccountsBuilder months(List<String> months){
            this.months = months;
            return this;
        }


        public AccountsBuilder assetGroups(List<ProfitAndLossGroupDTO> assetGroups){
            this.assetGroups = assetGroups;
            return this;
        }

        public AccountsBuilder liabilityGroups(List<ProfitAndLossGroupDTO> liabilityGroups){
            this.liabilityGroups = liabilityGroups;
            return this;
        }

        public AccountsBuilder equitiesGroups(List<ProfitAndLossGroupDTO> equitiesGroups){
            this.equitiesGroups = equitiesGroups;
            return this;
        }

        public AccountsBuilder incomeGroups(List<ProfitAndLossGroupDTO> incomeGroups){
            this.incomeGroups = incomeGroups;
            return this;
        }

        public AccountsBuilder expenseGroups(List<ProfitAndLossGroupDTO> expenseGroups){
            this.expenseGroups = expenseGroups;
            return this;
        }

        public AccountsBuilder depreciationGroups(List<ProfitAndLossGroupDTO> depreciationGroups){
            this.depreciationGroups = depreciationGroups;
            return this;
        }

        public AccountsBuilder currentAssetGroups(List<ProfitAndLossGroupDTO> currentAssetGroups){
            this.currentAssetGroups = currentAssetGroups;
            return this;
        }

        public AccountsBuilder fixedAssetGroups(List<ProfitAndLossGroupDTO> fixedAssetGroups){
            this.fixedAssetGroups = fixedAssetGroups;
            return this;
        }

        public AccountsBuilder currentLiabilityGroups(List<ProfitAndLossGroupDTO> currentAssetGroups){
            this.currentLiabilityGroups = currentAssetGroups;
            return this;
        }

        public AccountsBuilder loanGroups(List<ProfitAndLossGroupDTO> loanGroups){
            this.loanGroups = loanGroups;
            return this;
        }

        public AccountsBuilder shareCapitalGroups(List<ProfitAndLossGroupDTO> shareCapitalGroups){
            this.shareCapitalGroups = shareCapitalGroups;
            return this;
        }

        public AccountsBuilder assetGroupAmount(List<MonthWithProfitAndLossAmountDTO> assetGroupAmount){
            this.assetGroupAmount = assetGroupAmount;
            return this;
        }

        public AccountsBuilder liabilityGroupAmount(List<MonthWithProfitAndLossAmountDTO> liabilityGroupAmount){
            this.liabilityGroupAmount = liabilityGroupAmount;
            return this;
        }

        public AccountsBuilder equitiesGroupAmount(List<MonthWithProfitAndLossAmountDTO> equitiesGroupAmount){
            this.equitiesGroupAmount = equitiesGroupAmount;
            return this;
        }

        public AccountsBuilder incomeGroupAmount(List<MonthWithProfitAndLossAmountDTO> incomeGroupAmount){
            this.incomeGroupAmount = incomeGroupAmount;
            return this;
        }

        public AccountsBuilder expenditureGroupAmount(List<MonthWithProfitAndLossAmountDTO> expenditureGroupAmount){
            this.expenditureGroupAmount = expenditureGroupAmount;
            return this;
        }

        public AccountsBuilder depreciationGroupAmount(List<MonthWithProfitAndLossAmountDTO> depreciationGroupAmount){
            this.depreciationGroupAmount = depreciationGroupAmount;
            return this;
        }

        public AccountsBuilder currentAssetGroupAmount(List<MonthWithProfitAndLossAmountDTO> currentAssetGroupAmount){
            this.currentAssetGroupAmount = currentAssetGroupAmount;
            return this;
        }

        public AccountsBuilder fixedAssetGroupAmount(List<MonthWithProfitAndLossAmountDTO> fixedAssetGroupAmount){
            this.fixedAssetGroupAmount = fixedAssetGroupAmount;
            return this;
        }

        public AccountsBuilder currentLiabilityGroupAmount(List<MonthWithProfitAndLossAmountDTO> currentAssetGroupAmount){
            this.currentLiabilityGroupAmount = currentAssetGroupAmount;
            return this;
        }

        public AccountsBuilder loanGroupAmount(List<MonthWithProfitAndLossAmountDTO> loanGroupAmount){
            this.loanGroupAmount = loanGroupAmount;
            return this;
        }

        public AccountsBuilder shareCapitalGroupAmount(List<MonthWithProfitAndLossAmountDTO> shareCapitalGroupAmount){
            this.shareCapitalGroupAmount = shareCapitalGroupAmount;
            return this;
        }
    }
}
