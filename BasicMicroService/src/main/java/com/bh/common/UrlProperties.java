package com.bh.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "url")
public class UrlProperties {

    private String url; //请求人员的url

    private String port; //本机port

    public String getUrl() {
        return url;
    }
    public String getPort() {
        return port;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setPort(String port) {
        this.port = port;
    }
    @Override
    public String toString() {
        return "UrlProperties{" +
                "url='" + url + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
