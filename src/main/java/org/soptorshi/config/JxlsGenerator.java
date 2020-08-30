package org.soptorshi.config;

import org.elasticsearch.search.aggregations.pipeline.movavg.models.MovAvgModel;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.soptorshi.service.dto.ChartsOfAccountsDTO;
import org.soptorshi.service.dto.extended.AccountWithMonthlyBalances;
import org.soptorshi.service.dto.extended.MonthWithProfitAndLossAmountDTO;
import org.soptorshi.service.dto.extended.ProfitAndLossGroupDTO;
import org.soptorshi.service.dto.extended.ProfitLossDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
public class JxlsGenerator {

    public void build(List<ChartsOfAccountsDTO> assetGroup,
                      List<ChartsOfAccountsDTO> liabilitiesGroup,
                      List<ChartsOfAccountsDTO> incomeGroup,
                      List<ChartsOfAccountsDTO> expenditureGroup,
                      OutputStream out,
                      InputStream templateLocation) throws IOException{
        Context context = new Context();
        context.putVar("assets", assetGroup);
        context.putVar("liabilities", liabilitiesGroup);
        context.putVar("incomes", incomeGroup);
        context.putVar("expenditures", expenditureGroup);
        processTemplate(context, templateLocation, out);
    }

    public void buildProfitAndLoss(
        ProfitLossDto income,
        ProfitLossDto expenditure,
        OutputStream out,
        InputStream templateLocation) throws Exception{

        Context context = new Context();
        context.putVar("income", income);
    }


    public void cashFlowBuilder(
        final List<String> months,
        final List<ProfitAndLossGroupDTO> asset,
        final List<ProfitAndLossGroupDTO> liability,
        final List<ProfitAndLossGroupDTO> equity,
        final List<ProfitAndLossGroupDTO> revenue,
        final List<ProfitAndLossGroupDTO> expense,
        final List<ProfitAndLossGroupDTO> depreciation,
        final List<ProfitAndLossGroupDTO> currentAsset,
        final List<ProfitAndLossGroupDTO> fixedAsset,
        final List<ProfitAndLossGroupDTO> currentLiability,
        final List<ProfitAndLossGroupDTO> loan,
        final List<ProfitAndLossGroupDTO> shareCapital,
        final List<MonthWithProfitAndLossAmountDTO> assetGroupAmount,
        final List<MonthWithProfitAndLossAmountDTO> equityGroupAmount,
        final List<MonthWithProfitAndLossAmountDTO> liabilityGroupAmount,
        final List<MonthWithProfitAndLossAmountDTO> revenueGroupAmount,
        final List<MonthWithProfitAndLossAmountDTO> expenseGroupAmount,
        final List<MonthWithProfitAndLossAmountDTO> depreciationGroupAmount,
        final List<MonthWithProfitAndLossAmountDTO> currentAssetGroupAmount,
        final List<MonthWithProfitAndLossAmountDTO> fixedAssetGroupAmount,
        final List<MonthWithProfitAndLossAmountDTO> currentLiabilityGroupAmount,
        final List<MonthWithProfitAndLossAmountDTO> loanGroupAmount,
        final List<MonthWithProfitAndLossAmountDTO> shareCapitalGroupAmount,
        final List<BigDecimal> differences,
        final List<BigDecimal> cashMovements,
        final List<BigDecimal> openBalances,
        final List<BigDecimal> closingBalances,
        OutputStream out,
        InputStream templateLocation) throws IOException{
        Context context = new Context();
        context.putVar("months", months);
        context.putVar("asset", asset);
        context.putVar("liability", liability);
        context.putVar("equity", equity);
        context.putVar("revenue", revenue);
        context.putVar("expenditure", expense);
        context.putVar("depreciation", depreciation);
        context.putVar("currentAsset", currentAsset);
        context.putVar("fixedAsset", fixedAsset);
        context.putVar("currentLiability", currentLiability);
        context.putVar("loan", loan);
        context.putVar("shareCapital", shareCapital);
        context.putVar("assetGroupAmount", assetGroupAmount);
        context.putVar("equityGroupAmount", equityGroupAmount);
        context.putVar("liabilityGroupAmount", liabilityGroupAmount);
        context.putVar("revenueGroupAmount", revenueGroupAmount);
        context.putVar("expenseGroupAmount", expenseGroupAmount);
        context.putVar("depreciationGroupAmount", depreciationGroupAmount);
        context.putVar("currentAssetGroupAmount", currentAssetGroupAmount);
        context.putVar("fixedAssetGroupAmount", fixedAssetGroupAmount);
        context.putVar("currentLiabilityGroupAmount", currentLiabilityGroupAmount);
        context.putVar("loanGroupAmount", loanGroupAmount);
        context.putVar("shareCapitalGroupAmount", shareCapitalGroupAmount);
        context.putVar("differences", differences);
        List<BigDecimal> movementsTmp = new ArrayList<>();
        for(BigDecimal m: cashMovements){
            movementsTmp.add(m);
        }
        context.putVar("cashMovements", movementsTmp);
        context.putVar("openBalances", openBalances);
        context.putVar("closingBalances", closingBalances);
        processTemplate(context, templateLocation, out);
    }

    public void balanceSheetBuilder(
        List<String> months,
        List<ProfitAndLossGroupDTO> asset,
        List<ProfitAndLossGroupDTO> liability,
        List<ProfitAndLossGroupDTO> equity,
        List<ProfitAndLossGroupDTO> revenue,
        List<ProfitAndLossGroupDTO> expense,
        List<MonthWithProfitAndLossAmountDTO> assetGroupAmount,
        List<MonthWithProfitAndLossAmountDTO> equityGroupAmount,
        List<MonthWithProfitAndLossAmountDTO> liabilityGroupAmount,
        List<MonthWithProfitAndLossAmountDTO> revenueGroupAmount,
        List<MonthWithProfitAndLossAmountDTO> expenseGroupAmount,
        List<BigDecimal> differences,
        OutputStream out,
        InputStream templateLocation) throws IOException{
        Context context = new Context();
        context.putVar("months", months);
        context.putVar("asset", asset);
        context.putVar("liability", liability);
        context.putVar("equity", equity);
        context.putVar("revenue", revenue);
        context.putVar("expenditure", expense);
        context.putVar("assetGroupAmount", assetGroupAmount);
        context.putVar("equityGroupAmount", equityGroupAmount);
        context.putVar("liabilityGroupAmount", liabilityGroupAmount);
        context.putVar("revenueGroupAmount", revenueGroupAmount);
        context.putVar("expenseGroupAmount", expenseGroupAmount);
        context.putVar("differences", differences);
        processTemplate(context, templateLocation, out);
    }


    public void profitAndLossBuilder(
        List<String> months,
        List<ProfitAndLossGroupDTO> revenue,
        List<ProfitAndLossGroupDTO> expense,
        List<MonthWithProfitAndLossAmountDTO> revenueGroupAmount,
        List<MonthWithProfitAndLossAmountDTO> expenseGroupAmount,
        List<BigDecimal> differences,
        OutputStream out,
        InputStream templateLocation) throws IOException{
        Context context = new Context();
        context.putVar("months", months);
        context.putVar("revenue", revenue);
        context.putVar("expenditure", expense);
        context.putVar("revenueGroupAmount", revenueGroupAmount);
        context.putVar("expenseGroupAmount", expenseGroupAmount);
        context.putVar("differences", differences);
        processTemplate(context, templateLocation, out);
    }

    private void processTemplate(Context context, InputStream pTemplateLocation, OutputStream pOutputStream) throws IOException {
        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        /*Transformer transformer = jxlsHelper.createTransformer(pTemplateLocation, pOutputStream);
        setSilent(transformer);
        jxlsHelper.processTemplate(context, transformer);*/
        jxlsHelper.processTemplate(pTemplateLocation, pOutputStream, context);
    }

    private void setSilent(Transformer transformer) {
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
       // evaluator.getJexlEngine().setSilent(true);
    }
}
