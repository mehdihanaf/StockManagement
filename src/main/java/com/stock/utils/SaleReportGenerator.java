package com.stock.utils;


import com.stock.models.Sale;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.core.io.ByteArrayResource;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaleReportGenerator {


    /*String templatePath = "C:/Users/melhanaf/OneDrive - NTT DATA EMEAL/Documentos/saleReport.jrxml";

    public Resource exportToPdf(List<Sale> list) throws JRException, FileNotFoundException {

        JasperReport jasperReport = JasperCompileManager.compileReport(templatePath);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("salesData", "Sales Report");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());



        return resource;
    }*/

    public Resource exportToPdf(List<Sale> list) throws JRException, FileNotFoundException {
        byte[] pdfBytes = JasperExportManager.exportReportToPdf(getReport(list));
        return new ByteArrayResource(pdfBytes);
    }


    private JasperPrint getReport(List<Sale> list) throws FileNotFoundException, JRException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("salesData", new JRBeanCollectionDataSource(list));

        JasperPrint report = JasperFillManager.fillReport(JasperCompileManager.compileReport(
                ResourceUtils.getFile("classpath:saleReport.jrxml")
                        .getAbsolutePath()), params, new JREmptyDataSource());

        return report;
    }
}
