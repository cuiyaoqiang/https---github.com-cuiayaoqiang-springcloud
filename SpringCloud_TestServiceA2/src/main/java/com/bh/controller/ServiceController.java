package com.bh.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
public class ServiceController {

	//Eureka客户端
	@Autowired
	private EurekaClient eurekaClient;

	//服务发现客户端
	@Autowired
	private DiscoveryClient discoveryClient;

	/**
	 * 获得Eureka instance的信息，传入Application NAME
	 * 
	 * */
	@GetMapping("eureka-instance")
	public String serviceUrl(){
		System.out.println("2222222222");
		InstanceInfo instance = this.eurekaClient.getNextServerFromEureka("SERVICE-A", false);
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
	/**
	 * 获取所有服务实例信息
	 * */
	@GetMapping("discovery")
	public List<ServiceInstance> showServicesInfo(){
		System.out.println("discovery");
		List<String> services = this.discoveryClient.getServices();
		List<ServiceInstance> instances=new ArrayList<>();
		for(String service:services){
			List<ServiceInstance> serviceInstance = this.discoveryClient.getInstances(service);
			instances.add(serviceInstance.get(0));
		}
		return instances;
	}
}