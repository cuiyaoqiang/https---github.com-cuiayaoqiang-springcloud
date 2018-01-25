package com.bh.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author bh
 *使用@FeignClient("compute-service")注解来绑定该接口对应compute-service服务
 */
@FeignClient("service-A")
public interface ServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/instance-info")
    String getInstanceInfo();

}