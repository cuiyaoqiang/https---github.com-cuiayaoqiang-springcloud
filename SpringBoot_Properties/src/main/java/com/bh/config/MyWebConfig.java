package com.bh.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**自定义配置文件
 * 在spring boot（版本1.5.1.RELEASE）项目之后移除了原本的@ConfigurationProperties注解中的location属性
 * @author bh
 */
@ConfigurationProperties(prefix = "web")
@PropertySource("classpath:res/my-web.properties")
@Component
public class MyWebConfig {

    private String name;

    private String version;

    private String author;

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}