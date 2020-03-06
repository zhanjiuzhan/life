package org.jcl.life.pretreat;

import org.jcl.life.auth.Permission;
import org.jcl.life.auth.PermissionService;
import org.jcl.life.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenglei
 */
@Component
public class RequestMappingPretreat implements ApplicationContextAware, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(RequestMappingPretreat.class);

    private ApplicationContext applicationContext;

    private List<Permission> oldPermissions = new ArrayList<>();

    @Autowired
    PermissionService permissionService;

    @Override
    public void afterPropertiesSet() throws Exception {
        oldPermissions = permissionService.getPermissions();
        dealRequestMapping();
    }

    private void dealRequestMapping() {
        String[] beanNames = this.applicationContext.getBeanNamesForType(WithPermissionController.class);
        List<String> permissionUrls = new ArrayList<>();
        for (String beanName : beanNames) {
            Object obj = this.applicationContext.getBean(beanName);
            Method[] methods = obj.getClass().getMethods();
            for (Method method : methods) {
                List<String> retUrls = getRequestMappingMethodUrl(method);
                if (retUrls.size() > 0) {
                    permissionUrls.addAll(retUrls);
                }
            }
        }

        if (permissionUrls.size() > 0) {
            addPermission(permissionUrls);
        }
    }

    private List<String> getRequestMappingMethodUrl(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        List<String> retUrls =  new ArrayList<>();
        if (null != requestMapping) {
            String[] urls = requestMapping.value();
            for (String url : urls) {
                if (StringUtils.isNotEmpty(url)) {
                    retUrls.add(url);
                }
            }
        }
        return retUrls;
    }

    private void addPermission(List<String> urls) {
        List<Permission> tmpOldPermisstion = new ArrayList<>(oldPermissions);
        List<String> tmpUrls = new ArrayList<>(urls);
        for (Permission permission : oldPermissions) {
            String url = permission.getUrl();
            if (urls.contains(url)) {
                logger.info("[ " + url + " ] 该权限已经存在无需添加了。");
                tmpOldPermisstion.remove(permission);
                tmpUrls.remove(url);
            }
        }
        for (String url : tmpUrls) {
            logger.info("[ " + url + " ] 添加为新权限。");
            Permission permission = new Permission();
            permission.setUrl(url);
            permissionService.addPermission(permission);
        }

        for (Permission permission : tmpOldPermisstion) {
            logger.info("[ " + permission.getUrl() + " ] 该权限已废弃。");
            permissionService.deletePermission(permission.getId());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
