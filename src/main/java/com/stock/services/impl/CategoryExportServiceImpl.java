package com.stock.services.impl;

import com.opencsv.CSVWriter;
import com.stock.exceptions.TMNotFoundException;
import com.stock.model.CategoryDTO;
import com.stock.models.Category;
import com.stock.services.CategoryExportService;
import com.stock.utils.TextUtil;
import net.sf.jasperreports.engine.util.FileBufferedWriter;
import org.modelmapper.ModelMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class CategoryExportServiceImpl implements CategoryExportService {

    private final ModelMapper modelMapper;
    private final TextUtil textUtil;

    public CategoryExportServiceImpl(ModelMapper modelMapper, TextUtil textUtil) {
        this.modelMapper = modelMapper;
        this.textUtil = textUtil;
    }

    @Override
    public File export(List<Category> categories) throws IOException {

        CSVWriter writer = new CSVWriter(new FileWriter("category.csv"));
        List<String[]> rows = new ArrayList<>();
        String[] header = {"id", "name"};
        rows.add(header);
        rows.addAll(categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .map(dto -> new String[]{String.valueOf(dto.getId()), dto.getName()})
                .toList());
        writer.writeAll(rows);
        return null;
    }
}
