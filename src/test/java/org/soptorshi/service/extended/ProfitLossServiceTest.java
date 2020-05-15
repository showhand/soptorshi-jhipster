package org.soptorshi.service.extended;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.MstAccount;
import org.soptorshi.domain.MstGroup;
import org.soptorshi.repository.extended.MstAccountExtendedRepository;
import org.soptorshi.repository.extended.MstGroupExtendedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
@Transactional
public class ProfitLossServiceTest {
    @Autowired
    private ProfitLossService profitLossService;
    @Autowired
    private MstGroupExtendedRepository mstGroupExtendedRepository;
    @Autowired
    private AccountsDataGeneratorService accountsDataGeneratorService;
    @Autowired
    private MstAccountExtendedRepository mstAccountExtendedRepository;

    @Before
    public void initialize(){
        accountsDataGeneratorService.createProfitLossTestData();
    }

    @Test
    public void init(){
        List<MstGroup> groups = mstGroupExtendedRepository.findAll();
        assertThat(groups.size()).isGreaterThan(0);
        List<MstAccount> accounts  = mstAccountExtendedRepository.findAll();
        assertThat(accounts.size()).isGreaterThan(0);
    }

    @Test
    public void generateExcelFile() throws Exception{
        LocalDate fromDate = LocalDate.of(2020, Month.JANUARY, 1);
        LocalDate toDate = LocalDate.of(2020, Month.MAY, 31);
        ByteArrayInputStream report = profitLossService.createReport(fromDate, toDate);

        assertThat(report.read()).isNotEqualTo(0);

        File outputFile = new File("D:/profit-and-loss.xls");
        FileOutputStream fos = new FileOutputStream(outputFile);
        int data;
        while((data=report.read())!= -1){
            char character= (char)data;
            fos.write(character);
        }
        fos.flush();
        fos.close();
    }
}
