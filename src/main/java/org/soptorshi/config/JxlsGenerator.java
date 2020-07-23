package org.soptorshi.config;

import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.soptorshi.service.dto.ChartsOfAccountsDTO;
import org.soptorshi.service.dto.extended.AccountWithMonthlyBalances;
import org.soptorshi.service.dto.extended.ProfitLossDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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


    public void profitAndLossBuilder(
        List<ProfitLossDto> revenue,
        List<ProfitLossDto> expense,
        AccountWithMonthlyBalances comparingBalance,
        OutputStream out,
        InputStream templateLocation) throws IOException{
        Context context = new Context();
//        context.putVar("revenue", revenue);
//        context.putVar("expense", expense);
//        context.putVar("comparingBalance", comparingBalance);
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
