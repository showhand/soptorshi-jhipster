package org.soptorshi.service.extended;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.soptorshi.config.JxlsGenerator;
import org.soptorshi.service.dto.extended.AccountWithMonthlyBalances;
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

@Service
public class ProfitLossService {

    private ResourceLoader resourceLoader;
    private JxlsGenerator jxlsGenerator;

    public ProfitLossService(ResourceLoader resourceLoader, JxlsGenerator jxlsGenerator) {
        this.resourceLoader = resourceLoader;
        this.jxlsGenerator = jxlsGenerator;
    }

    public ByteArrayInputStream createReport(LocalDate fromDate, LocalDate toDate) throws Exception{
        List<ProfitLossDto> revenue = generateRevenues(fromDate, toDate);
        List<ProfitLossDto> expense = generateExpenses(fromDate, toDate);
        AccountWithMonthlyBalances comparingBalances = generateComparingBalances(revenue, expense);

        Resource resource = resourceLoader.getResource("classpath:/templates/jxls/ChartsOfAccounts.xls");// templates/jxls/ChartsOfAccounts.xls
        HSSFWorkbook workbook = new HSSFWorkbook(resource.getInputStream());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        byte[] barray = bos.toByteArray();
        InputStream is = new ByteArrayInputStream(barray);
        InputStream template = resource.getInputStream();
        OutputStream outputStream = new ByteArrayOutputStream() ; // new FileOutputStream(outputResource.getFile());
        jxlsGenerator.profitAndLossBuilder( revenue, expense, comparingBalances, outputStream, template);
        ByteArrayOutputStream baos =(ByteArrayOutputStream) outputStream; //(ByteArrayOutputStream) outputStream; //new ByteArrayOutputStream();
        byte[] data = baos.toByteArray();
        outputStream.write(data);
        return new ByteArrayInputStream(baos.toByteArray());
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
}
