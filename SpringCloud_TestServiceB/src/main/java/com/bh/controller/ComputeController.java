package com.bh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
public class ComputeController {

	//Eureka客户端
	@Autowired
	private EurekaClient eurekaClient;

	//服务发现客户端
	@Autowired
	private DiscoveryClient discoveryClient;

	/**
	 * 获得Eureka instance的信息，传入Application NAME
	 * */
	@GetMapping("eureka-instance")
	public String serviceUrl(){
		System.out.println("111111111111111111");
		InstanceInfo instance = this.eurekaClient.getNextServerFromEureka("SERVICE-B", false);
		return instance.getHomePageUrl();
	}

	/**
	 * 本地服务实例信息
	 * */
	@GetMapping("instance-info")
	public ServiceInstance showInfo(){
		ServiceInstance localServiceInstance = this.discoveryClient.getLocalServiceInstance();
		return localServiceInstance;
	}
}