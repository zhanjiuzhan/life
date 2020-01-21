package org.jcl.life.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PropertyConfigurer extends PropertyPlaceholderConfigurer {
    private static Logger logger =
            LoggerFactory.getLogger(PropertyConfigurer.class);

    public PropertyConfigurer() {
        // 忽略不存在的配置文件
        super.setIgnoreResourceNotFound(true);
        // 忽略无法解析的占位符
        super.setIgnoreUnresolvablePlaceholders(true);
        super.setOrder(999);
        // 默认就是2 properties找不到就带system中去寻找
        super.setSystemPropertiesMode(2);
		/*
		try {
			super.setLocations(this.getLocations());
		} catch (IOException var2) {
			throw new RuntimeException(var2.getMessage(), var2);
		}*/
    }

    private Resource[] getLocations() throws IOException {
        List<Resource> resourceList = new ArrayList();
        String appPropertiesFilename = getPath();
        File file = new File(appPropertiesFilename);
        if (!file.exists()) {
            throw new FileNotFoundException("文件[" + appPropertiesFilename + "]不存在.");
        } else {
            resourceList.add(new InputStreamResource(new FileInputStream(file)));
        }
        return resourceList.toArray(new Resource[resourceList.size()]);
    }

    /**
     * 根据当前的系统环境取得配置文件的路径
     *
     * @return
     */
    private String getPath() {
        String systemName = System.getProperty("os.name").toLowerCase();
        final String PREFIX = "/application.properties";
        if (systemName.contains("windows")) {
            return getWindowPath() + "/config/dev" + PREFIX;
        } else if (systemName.contains("linux")) {
            return getLinuxPath();
        } else {
            throw new RuntimeException("未知操作系统");
        }
    }

    private String getWindowPath() {
        String path = ClassLoader.getSystemResource(".").toString();
        path = path.replaceFirst("file:/", "/");
        int index = path.indexOf("/target/");
        if (index == -1) {
            throw new RuntimeException("非法classes目录[" + path + "].");
        } else {
            path = path.substring(0, index);
            String moduleName;
            if (path.endsWith("/")) {
                throw new IllegalArgumentException("非法路径格式[" + path + "].");
            } else {
                index = path.lastIndexOf("/");
                moduleName = path.substring(index + 1);
            }
            if (moduleName.indexOf("-") == -1) {
                return path;
            } else {
                index = path.lastIndexOf("/");
                if (index == -1) {
                    throw new IllegalArgumentException("非法路径格式[" + path + "].");
                } else {
                    return path.substring(0, index);
                }
            }
        }
    }

    private String getLinuxPath() {
        String dev = System.getenv("ENV");
        if (!"dev".equals(dev)) {
            dev = "test";
        }
        return "/data/webapp/config/" + dev;
    }
}
