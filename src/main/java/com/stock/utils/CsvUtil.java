package com.stock.utils;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public final class CsvUtil {
    private CsvUtil() {}

    public static  <T> Resource export(List<T> list) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        try {
            StatefulBeanToCsv<T> beanToCsv  = new StatefulBeanToCsvBuilder<T>(writer)
                    .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();

            beanToCsv.write(list);
            writer.flush();
            return new ByteArrayResource(outputStream.toByteArray());
        }catch (Exception e){
            throw new RuntimeException("Exception while Exporting csv file");
        }
    }
}
