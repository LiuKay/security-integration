package com.kay.service;

import org.springframework.stereotype.Service;

/**
 * @author LiuKay
 * @since 2019/11/23
 */
@Service
public class BusinessServiceImpl implements BusinessService {
    @Override
    public void mockCheckBusiness(Object object) {
        System.out.println("handle business, args: " + object);
    }
}
