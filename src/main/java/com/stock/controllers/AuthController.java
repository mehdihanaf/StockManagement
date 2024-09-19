package com.stock.controllers;

import com.stock.StockManagementConstants;
import com.stock.api.controller.UserApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(StockManagementConstants.API_VERSION)
public class AuthController implements UserApi {



}
