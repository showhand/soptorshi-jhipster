package org.soptorshi.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.TermsAndConditions;
import org.soptorshi.repository.PurchaseOrderRepository;
import org.soptorshi.repository.RequisitionRepository;
import org.soptorshi.repository.TermsAndConditionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Service
@Transactional
public class WorkOrderReportService {

    private final Logger log = LoggerFactory.getLogger(WorkOrderReportService.class);

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private RequisitionRepository requisitionRepository;
    @Autowired
    private TermsAndConditionsRepository termsAndConditionsRepository;


    public void createWorkOrderReport(Long purchaseOrderId, OutputStream outputStream) throws Exception, DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        document.addTitle("Work Order");
        document.setPageSize(PageSize.A4);
        document.open();

        document.add(new Paragraph("Hello Soptorshi"));
        document.close();
        baos.writeTo(outputStream);
    }

}
