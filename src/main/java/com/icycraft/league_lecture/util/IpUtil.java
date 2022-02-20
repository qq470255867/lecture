package com.icycraft.league_lecture.util;

import com.alibaba.fastjson.JSONObject;
import com.baidubce.http.ApiExplorerClient;
import com.baidubce.http.AppSigner;
import com.baidubce.http.HttpMethodName;
import com.baidubce.model.ApiExplorerRequest;
import com.baidubce.model.ApiExplorerResponse;
import com.baidubce.services.cdn.model.JsonObject;
import com.icycraft.league_lecture.entity.Location;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;

@Slf4j
public class IpUtil {


    public static String KEY = "e9ef5ec615134e938b306a8d650b2070";

    public static String SECRET = "54abe595072f443aa5d3d2df7252fd93";


    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST = "127.0.0.1";
    private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";//客户端与服务器同为一台机器获取的ip有时候是ipv6格式表示的本地地址

    private static final String SEPARATOR = ",";

    public static String getIpAddr(HttpServletRequest request) {

        String ipAddress;
        try {

            String Forwarded = request.getHeader("X-Forwarded-For");
            ipAddress = Forwarded.split(", ")[0];
            //ipAddress = request.getHeader("X-Forwarded-For");//有时候是大写，在于nginx.conf中的proxy_set_header如何配置了
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("X-Forwarded-For");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
                System.out.println("Proxy-Client-IP:"+ipAddress);
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
                System.out.println("WL-Proxy-Client-IP:"+ipAddress);
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (LOCALHOST.equals(ipAddress)||LOCALHOST_IPV6.equals(ipAddress)) {
                    InetAddress inet = null;
                    try {
                        // 根据网卡取本机配置的IP
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(SEPARATOR) > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

    public static Location getLocationByIp(String ip) {
        String path = "http://gwgp-dd3tdfvrfcw.n.bdcloudapi.com/";
        ApiExplorerRequest request = new ApiExplorerRequest(HttpMethodName.GET, path);
        request.setCredentials("e9ef5ec615134e938b306a8d650b2070", "54abe595072f443aa5d3d2df7252fd93");

        // 设置header参数
        request.addHeaderParameter("Content-Type", "application/json; charset=utf-8");

        // 设置query参数
        request.addQueryParameter("ip", ip);

        ApiExplorerClient client = new ApiExplorerClient(new AppSigner());

        Location result = new Location();
        try {
            ApiExplorerResponse response = client.sendRequest(request);
            // 返回结果格式为Json字符串
            result.setIp(ip);
            JSONObject locationJson = JSONObject.parseObject(response.getResult());
            JSONObject data = locationJson.getJSONObject("data");
            String nation = unicodeToCn(data.getString("nation"));
            result.setAddress(nation);
            if (!"局域网".equals(nation)){
                JSONObject detail = data.getJSONObject("details");
                String region = unicodeToCn(detail.getString("region"));
                String city = unicodeToCn(detail.getString("city"));

                String lng = detail.getString("lng");
                String lat = detail.getString("lat");
                result.setAddress(nation+region+city);
                result.setLon(lng);
                result.setLat(lat);
            }

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        return result;


    }

    private static String unicodeToCn(String unicode) {

        if (!StringUtils.isEmpty(unicode)&&unicode.startsWith("\\\\u")){
            /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
            String[] strs = unicode.split("\\\\u");
            String returnStr = "";
            // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
            for (int i = 1; i < strs.length; i++) {
                returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
            }
            return returnStr;
        }
        else return unicode;
    }


}
