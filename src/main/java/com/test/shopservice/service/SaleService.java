package com.test.shopservice.service;

import com.test.shopservice.dto.SaleDetailDto;
import com.test.shopservice.dto.SaleDto;
import com.test.shopservice.entity.Sale;
import com.test.shopservice.entity.SaleDetail;
import com.test.shopservice.repository.SaleDetailRepository;
import com.test.shopservice.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<SaleDto> listSale() {

        return saleRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

    }

    public SaleDto getSale(Integer id) {

        Optional<Sale> sale = this.saleRepository.findById(id);

        if(sale.isEmpty()){
            return null;
        }

        return  this.toDto(sale.get());
    }

    public List<SaleDetail> createSale(SaleDto salesDto) {

        Sale sale = modelMapper.map(salesDto, Sale.class);
        sale.setDate(LocalDateTime.now());

        this.saleRepository.save(sale);

        return sale.getDetailList()
                .stream()
                .map(element -> {
                    element.setSale(sale);
                    SaleDetail saleDetail = modelMapper.map(element, SaleDetail.class);
                    this.saleDetailRepository.save(saleDetail);
                    return saleDetail;
                })
                .collect(Collectors.toList());
    }

    private SaleDto toDto(Sale sale) {

        SaleDto saleDto = modelMapper.map(sale, SaleDto.class);
        List<SaleDetailDto> saleDetailDto = sale.getDetailList()
                .stream()
                .map(element ->{
                    SaleDetailDto detailDto = modelMapper.map(element, SaleDetailDto.class);
                    detailDto.setTotal(detailDto.getQuantity() * detailDto.getProduct().getPrice());
                    return detailDto;
                })
                .collect(Collectors.toList());

        Double total = saleDto.getDetailList()
                .stream()
                .mapToDouble(element -> {
                    SaleDetailDto detailDto = modelMapper.map(element, SaleDetailDto.class);
                    return detailDto.getQuantity() * detailDto.getProduct().getPrice();
                })
                .sum();

        saleDto.setDetailList(saleDetailDto);
        saleDto.setTotalSale(total);

        return saleDto;
    }

}


