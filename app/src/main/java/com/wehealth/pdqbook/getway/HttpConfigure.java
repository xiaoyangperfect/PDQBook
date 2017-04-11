package com.wehealth.pdqbook.getway;

/**
 * Created by xiaoyang on 2016/12/7.
 */

public class HttpConfigure {
    private static final String URL_HOST = "http://ps.wehealth.mobi/API/NCC";
    //风险检测
    private static final String URL_RISK_INSPECTION = "http://pt.wehealth.mobi/ncc/index.html#/page5/lung_show";
    //癌症页面
    //http://pt.wehealth.mobi/ncc/index.html#/summarylist/1/Prevention
    private static final String URL_CANCER_WEB = "http://pt.wehealth.mobi/ncc/index.html#/summarylist/%s/%s";
    //文章页面
    private static final String URL_ARTICLE_WEB = "http://pt.wehealth.mobi/ncc/index.html#/page2/%s";
    //文章详情页面
    private static final String URL_ARTICLE_DETAIL_WEB = "http://pt.wehealth.mobi/ncc/index.html#/page2/%s/%s";
    //搜索接口
    private static final String URL_SEARCH = "http://ps.wehealth.mobi/API//NCC/Search?keyword=%s";

    private static final String URL_APPOINTMENT = "http://ty.ddiaos.cn/PDQtijian/hospital.html";

    //cancerWebSection = @{@"0":@"Prevention", @"1":@"Screening", @"2":@"Treatment"};
//    tabBarNames = @[@"预防", @"筛查", @"治疗"];
    public static final String param_cancerpage_index_prevention = "Prevention";
    public static final String param_cancerpage_index_screening = "Screening";
    public static final String param_cancerpage_index_treatment = "Treatment";

    public static String getUrlRiskInspection() {
        return URL_RISK_INSPECTION;
    }

    public static String getUrlCancerWeb(String cancerId) {
        return String.format(URL_CANCER_WEB, cancerId, "%s");
    }

    public static String getUrlArticleWeb(String cdrId) {
        return String.format(URL_ARTICLE_WEB, cdrId);
    }

    public static String getUrlArticleDetailWeb(String cdrId, String paraId) {
        return String.format(URL_ARTICLE_DETAIL_WEB, cdrId, paraId);
    }

    public static String getSearchUrl(String content) {
        return String.format(URL_SEARCH, content);
    }

    public static String getAppointmentUrl() {
        return URL_APPOINTMENT;
    }
}
