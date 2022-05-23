package com.test.shopservice.service;

import com.test.shopservice.dto.SaleDetailDto;
import com.test.shopservice.entity.Sale;
import com.test.shopservice.entity.SaleDetail;
import com.test.shopservice.repository.SaleDetailRepository;
import com.test.shopservice.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleDetailService {

    private final SaleRepository saleRepository;
    private final SaleDetailRepository detailRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Mono<Object[]> findBySaleId(Integer id) {

        List<SaleDetail> detailList = detailRepository.findBySaleId(id);
        if (detailList.isEmpty()) {
            return null;
        }

        Object[] detailDtoList = detailList.stream()
                .map(element -> {
                    SaleDetailDto detailDto = modelMapper.map(element, SaleDetailDto.class);
                    detailDto.setTotal(detailDto.getQuantity() * detailDto.getProduct().getPrice());
                    return detailDto;
                })
                .toArray();

        return Mono.just(detailDtoList);
    }

    public Mono<Object[]> findByClientId(Integer id) {

        List<Sale> saleList = saleRepository.findByClientId(id);

        if (saleList.isEmpty()) {
            return null;
        }

        Object[] saleDetail = saleList.stream()
                .map(element -> {
                    return detailRepository.findBySaleId(element.getId());
                })
                .toArray();

        return Mono.just(saleDetail);
    }

}
