package com.stock.services;

import com.stock.models.Category;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface CategoryExportService {

    File export(List<Category> categories) throws IOException;

}
