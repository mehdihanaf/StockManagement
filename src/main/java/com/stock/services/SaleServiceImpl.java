package com.stock.services;

import com.stock.exceptions.CustomResponseException;
import com.stock.exceptions.TMNotFoundException;
import com.stock.model.ProductDTO;
import com.stock.model.SaleDTO;
import com.stock.models.Product;
import com.stock.models.Sale;
import com.stock.pages.ProductPage;
import com.stock.pages.SalePage;
import com.stock.repository.IProductRepository;
import com.stock.repository.ISaleRepository;
import com.stock.utils.SaleReportGenerator;
import com.stock.utils.TextUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SaleServiceImpl implements ISaleService{

    private final ISaleRepository saleRepository;
    private final IProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final TextUtil textUtil;

    private final SaleReportGenerator saleReportGenerator;

    public SaleServiceImpl(ISaleRepository saleRepository,
                           IProductRepository productRepository,
                           ModelMapper modelMapper,
                           TextUtil textUtil,
                           SaleReportGenerator saleReportGenerator) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.textUtil = textUtil;
        this.saleReportGenerator = saleReportGenerator;
    }
    @Override
    public SaleDTO getSaleById(Integer id) {

        log.info("Product  with id {}", id);
        var sale_txt = textUtil.getMessage("sale");
        return saleRepository.findById(id)
                .map(sale -> modelMapper.map(sale, SaleDTO.class))
                .orElseThrow(() -> new TMNotFoundException(textUtil.getMessage("error.notfound",sale_txt, id)));
    }
    @Override
    public List<SaleDTO> getAllSales() {

        log.info("Fetching all sales");
        List<SaleDTO> listSales =  saleRepository.findAll().stream()
                .map(sale -> modelMapper.map(sale, SaleDTO.class))
                .toList();
        log.info("Fetched {} categories", listSales.size());
        return listSales;

    }

    @Override
    public SaleDTO addSale(SaleDTO saleDTO) {

        var sale = modelMapper.map(saleDTO, Sale.class);
        var product = productRepository.findById(sale.getProduct().getId());
        if (product.get().getQuantity() < sale.getSaleQuantity()) {
            throw new CustomResponseException(textUtil.getMessage("error.empty"));
        }
        var newQuantity = product.get().getQuantity() - sale.getSaleQuantity();
        product.get().setQuantity(newQuantity);
        productRepository.save(product.get());
        var savedSale = saleRepository.save(sale);
        savedSale.setProduct(product.get());
        return modelMapper.map(savedSale, SaleDTO.class);
    }

    @Override
    public SaleDTO editSale(Integer id, SaleDTO saleDTO) {

        log.info("retrieve Sale to edit with id : {}", id);
        var oldSaleQuantity=  getSaleById(id).getSaleQuantity();
        var marge = saleDTO.getSaleQuantity() - oldSaleQuantity;
        log.info("Old Quantity = {} , New Quantity = {}",oldSaleQuantity,saleDTO.getSaleQuantity());
        saleDTO.setId(id);
        Optional<Product> product = productRepository.findById(saleDTO.getProduct().getId());
        var newProductQuantity = product.get().getQuantity() - marge;
        if (newProductQuantity < 0) {
            throw new CustomResponseException(textUtil.getMessage("error.empty"));
        }
        product.get().setQuantity(newProductQuantity);
        productRepository.save(product.get());
        Sale savedSale = saleRepository.save(modelMapper.map(saleDTO, Sale.class));
        savedSale.setProduct(product.get());
        return modelMapper.map(savedSale, SaleDTO.class);

    }

    @Override
    public void deleteSale(Integer id) {

        log.info("Sale to Delete with id {}", id);
        SaleDTO saleDTO = getSaleById(id);
        ProductDTO productDTO = saleDTO.getProduct();
        var updatedQuantity = productDTO.getQuantity() + saleDTO.getSaleQuantity();
        productDTO.setQuantity(updatedQuantity);
         modelMapper.map(productDTO, Product.class);
        productRepository.save(modelMapper.map(productDTO, Product.class));
        saleRepository.deleteById(id);
        log.info(" Sale Deleted with id {}", id);
    }

    @Override
    public void deleteSalesById(List<Integer> idList) {

        try {
            idList.forEach(this::deleteSale);
        }catch(DataIntegrityViolationException ex){
            log.info("category deletion exception ");
            throw new CustomResponseException(textUtil.getMessage("error.sale.assosciate"));
        }


    }

    @Override
    public List<SaleDTO> searchSale(String description) {

        log.info("Retrieve sale description to lookup with name {}",description );
        List<SaleDTO> listSalesByName = saleRepository.findByDescription(description).stream()
                .map(sale -> modelMapper.map(sale, SaleDTO.class))
                .toList();
        log.info("list of  Categories {} with name {}", listSalesByName, description);
        return listSalesByName;

    }
    @Override
    public SalePage searchForSalesByAnyColumn(String name, Pageable pageable) {
        Page<Sale> salePage = saleRepository.searchForSalesByAnyColumn( "%"+ name+"%", pageable);
        log.info("this is sale page");
        log.info(String.valueOf(salePage));
        Page<SaleDTO> saleDtoPage = salePage.map(sale -> modelMapper.map(sale, SaleDTO.class));
        SalePage sPage = new SalePage();
        sPage.setSales(saleDtoPage.getContent());
        sPage.setTotalCount(salePage.getTotalElements());

        return sPage;
    }

    @Override
    public Resource exportPdf() throws JRException, FileNotFoundException {
        return saleReportGenerator.exportToPdf(saleRepository.findAll());
    }

}
