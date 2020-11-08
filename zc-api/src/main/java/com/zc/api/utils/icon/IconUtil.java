package com.zc.api.utils.icon;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zichen
 */
public class IconUtil {

    /**
     * 根据url获取icon网络路径
     * 主要应对路径会发生跳转的情况：例如12306官网
     *
     * @param url   普通网络路径
     * @return
     */
    public static Set<String> getIcon(String url) {
        // 1. 先判断访问路径是否存在
        Set<String> icons;
        HttpResponse response = HttpRequest.get(url).execute();
        if (response.isOk()) {
            icons = getIcon(url, false, response);
        } else {
            icons = getIcon(response);
        }
        // 2. 如果获取失败，使用纯域名访问试试
        if (CollUtil.isEmpty(icons)) {
            int endIndex = url.indexOf("/", 8);
            if (endIndex != -1) {
                url = url.substring(0, endIndex);
                icons = getIcon(url);
            }
        }
        return icons;
    }

    /**
     * 根据网址根路径+favicon.ico的方式尝试获取icon
     *
     * @param url           网络路径
     * @param isRootPath    是否是根路径
     * @param response      根路径的请求响应
     * @return
     */
    public static Set<String> getIcon(String url, boolean isRootPath, HttpResponse response) {
        // 1. 声明结果集
        Set<String> icons = new HashSet<>();
        // 2. 拼接网络路径
        String iconUrl = splicingUrl(url, isRootPath);
        iconUrl += "/favicon.ico";
        // 3.. 如果icon存在则添加到结果集中
        if (!isExistUrl(iconUrl)) {
            // 4. 根据根路径和请求响应体分析
            iconUrl = getIcon(url, response.body());
            // 5. 如果资源部存在则返回空结果集
            if (StrUtil.isEmpty(iconUrl)) {
                return icons;
            }
        }
        // 6. 将存在的资源添加到结果集并返回
        icons.add(iconUrl);
        return icons;
    }

    /**
     * 拼接网络路径
     *
     * @param url           网络路径
     * @param isRootPath    是否是根路径
     * @return
     */
    public static String splicingUrl(String url, boolean isRootPath) {
        // 1. 声明返回结果对象
        String iconUrl = null;
        // 2. 判断是否是根路径
        if (isRootPath) {
            // 3. 如果获取到的url最后一位是/，则移除最后一位字符
            char separator = '/';
            if (CharUtil.equals(url.charAt(url.length() - 1), separator, true)) {
                iconUrl = url.substring(0, url.length() - 1);
            }
        } else {
            // 4. 将网址中的根路径以外的部分移除掉
            int endIndex = url.indexOf("/", 8);
            if (endIndex != -1) {
                iconUrl = url.substring(0, endIndex);
            } else {
                iconUrl = url;
            }
        }
        return iconUrl;
    }

    /**
     * 以请求响应的headers的Location属性中的值为根路径,尝试获取icon访问路径
     *
     * @param response  根路径的请求响应
     * @return
     */
    public static Set<String> getIcon(HttpResponse response) {
        // 1. 以请求响应的headers的Location属性中的值为根路径
        List<String> urls = response.headers().get("Location");
        if (ObjectUtil.isNull(urls)) {
            urls = new ArrayList<>();
        }
        // 2. 遍历根路径值集合尝试获取icon访问路径
        Set<String> icons = urls.stream().map(url -> {
            // 3. 根据网址根路径+favicon.ico的方式尝试获取icon
            HttpResponse response2 = HttpRequest.get(url).execute();
            // 4. 根据网址根路径+favicon.ico的方式尝试获取icon
            Set<String> iconsA = getIcon(url, true, response2);
            // 5. 如果失败，则根据根路径和请求响应体分析
            if (iconsA.isEmpty()) {
                return getIcon(url, response.body());
            } else {
                // 6. 如果A方式成功，则返回结果集中的第一个元素
                return iconsA.stream().findFirst().orElse(null);
            }
        }).collect(Collectors.toSet());
        // 7. 移除掉在遍历根路径失败加入数组的null
        icons.remove(null);
        return icons;
    }

    /**
     * 根据根路径和请求响应体分析
     *
     * @param url   网络根路径
     * @param body  根路径的请求响应体
     * @return
     */
    public static String getIcon(String url, String body) {
        // 1. 将响应体转换为html节点
        Document doc = Jsoup.parse(body);
        // 2. 获取head节点
        Element head = doc.head();
        // 3. 声明所有link标签上rel属性的值
        String[] rels = { "shortcut icon", "shortcut", "icon" };
        // 4. 获取存放icon网络路径的link节点
        Elements iconLink = null;
        for (String rel : rels) {
            // 5. 根据节点选择器和link节点上rel属性值获取存放icon网络路径的link节点
            iconLink = head.select("head > link[rel='" + rel + "']");
            // 6. 如果当前节点存在则无需获取下一节点
            if (CollUtil.isNotEmpty(iconLink)) {
                break;
            }
        }
        // 7. 如果还是没有匹配到节点则直接返回
        if (CollUtil.isEmpty(iconLink)) {
            return null;
        }
        // 8. 将根路径与当前节点的href属性值拼接为icon访问路径
        String iconUrl = url + iconLink.first().attributes().get("href");
        // 9. 如果icon存在则直接返回
        if (isExistUrl(iconUrl)) {
            return iconUrl;
        } else {
            url = splicingUrl(url, false);
            iconUrl = url + iconLink.first().attributes().get("href");
            if (isExistUrl(iconUrl)) {
                return iconUrl;
            }
        }
        return null;
    }

    /**
     * 根据路径判断访问的资源是否存在
     *
     * @param url   完整资源网络路径
     * @return
     */
    public static boolean isExistUrl(String url) {
        return HttpRequest.get(url).execute().isOk();
    }

}
