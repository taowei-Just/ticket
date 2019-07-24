package com.example.demo.crawl.engine;

import com.example.demo.bean.JihuaInfo;
import com.example.demo.crawl.iml.Icrawl;
import com.example.demo.data.PlanDetail;
import com.example.demo.data.PlanKind;
import com.example.utlis.o;
import com.example.utlis.subweb.PattenUtil;
import com.example.utlis.subweb.SubWebInfo;
import com.example.utlis.subweb.SubwebUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ZgzxjgCrawl implements Icrawl {

    private int reCount = 3;

    @Override
    public List<PlanDetail> crawl(PlanKind planKind) {
//        o.e("ZgzxjgCrawl  crawl" + planKind.toString());
        return loadUrl(planKind);
    }

    boolean isLast = false;

    private String getHeadString(int issue) {

        int t = 0;
        if (issue >= 180)
            isLast = true;

        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis());

        int hour = instance.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour <= 6) {
            t = 1;
        } else if (hour >= 13 && hour <= 23) {
            t = 0;
        } else {
            if (issue == 1)
                t = 0;
            else
                t = 1;
        }

        if (isLast) {
            instance.add(Calendar.DAY_OF_YEAR, -1 - t);
        } else {
            instance.add(Calendar.DAY_OF_YEAR, 0 - t);
        }


        return new SimpleDateFormat("yyyyMMdd").format(instance.getTime());
    }

    public static void main(String[] args) {
        PlanKind planKind = new PlanKind();
        planKind.setHost("https://www.zgzxjg.com/m-xyft-4-12.html");
        List<PlanDetail> planDetails = new ZgzxjgCrawl().loadUrl(planKind);
        if (planDetails!=null)
        for (PlanDetail planDetail : planDetails) {
            o.e(planDetail.toString());
        }
    }

    private List<PlanDetail> loadUrl(PlanKind planKind) {
        try {
            Connection conn = Jsoup.connect(planKind.host);
            conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            conn.header("Accept-Encoding", "gzip, deflate, sdch");
            conn.header("Accept-Language", "zh-CN,zh;q=0.8");
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
            conn.userAgent("UA").validateTLSCertificates(false);
          
            Document document = conn.get();
            return prease(planKind, document);
        } catch (Exception e) {
            e.printStackTrace();
            o.e(planKind.host);
        }
        return null;
    }

    private List<PlanDetail> prease(PlanKind planKind, Document document) {
        Element slide = document.getElementById("slide");
        Element pj10 = document.getElementById("pj10");
        Elements pj4 = document.getElementsByClass("bgff am-text-center b-bottom am-g menu_index2 yc");
        Elements page_lottery = document.getElementsByClass("page-lottery");
        Elements bang_main = document.getElementsByClass("bang-main");

        String jihuaOrder = getJihuaOrder(planKind.host, pj4);
        List<JihuaInfo.Data> jihuaDataS = getJihuaData(bang_main);
        jihuaDataS = operateJihuaData(jihuaDataS);

//        for (JihuaInfo.Data jihuaData : jihuaDataS) {
//            o.e(jihuaData.toString());
//        }
        List<PlanDetail> planDetails = new ArrayList<>();
        for (int i = 0; i < jihuaDataS.size(); i++) {
            
            if (i>=179)
                return planDetails ;
            
            JihuaInfo.Data data = jihuaDataS.get(i);
            PlanDetail planDetail = new PlanDetail();
            planDetail.setName(jihuaOrder);
            planDetail.setHost(planKind.host);
            planDetail.setWiner(data.getWinNumber());
            planDetail.setPlanExact(data.getJihuaResult() == null ? 2 : data.getJihuaResult().contains("对") ? 1 : 0);
            planDetail.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
            planDetail.setTimepoke(System.currentTimeMillis());
            Integer issue = Integer.valueOf(data.getWinOrCurrentIssue());
            planDetail.setIssue(getHeadString(issue) + (issue < 10 ? "00" : issue < 100 ? "0" : "") + issue);
            planDetail.setDetail(data.getForecastJihua());
            planDetail.setPlayedId(planKind.getPlyedId());
            planDetail.setPlanKindId(planKind.getPlanId());
            planDetail.setPagerId(planKind.getPagerId());
            planDetail.setTicketKindId(planKind.getTicketKindId());
            planDetails.add(planDetail);
        }

        return planDetails;
    }

    private String getRanking(String url, Element pj10) {
        String html = pj10.html();
//        o.e(html.toString());
        List<List> subWebHarf = SubwebUtil.subWeb(html, new SubWebInfo[]{new SubWebInfo().setStartD("<a").setEndD("</a>").setConer(true)});

        for (Object list : subWebHarf.get(0)) {
//            o.e(list.toString());
            List<List> lists = SubwebUtil.subWeb((String) list, new SubWebInfo[]{new SubWebInfo().setStartD("href=\"").setEndD("\"").setConer(false)});
            if (url.contains((CharSequence) lists.get(0).get(0))) {
                return (String) SubwebUtil.subWeb((String) list, new SubWebInfo[]{new SubWebInfo().setStartD("<p>").setEndD("</p>").setConer(false)}).get(0).get(0);
            }
        }

        return "";
    }

    private String getRate(Elements page_lottery) {
        return (String) SubwebUtil.subWeb(page_lottery.html(), new SubWebInfo[]{new SubWebInfo().setStartD("color=\"red\">").setEndD("</font>").setConer(false)}).get(0).get(0);
    }

    private String getlaetNumber(Elements page_lottery) {
        List<List> subWebHarf = SubwebUtil.subWeb(page_lottery.html(), new SubWebInfo[]{new SubWebInfo().setStartD("cai-numopen-num\">").setEndD("</div>").setConer(false)});
        subWebHarf = SubwebUtil.subWeb((String) subWebHarf.get(0).get(0), new SubWebInfo[]{new SubWebInfo().setStartD("\">").setEndD("</span>").setConer(false)});
        StringBuilder sb = new StringBuilder();
        for (Object o : subWebHarf.get(0)) {
            sb.append(o);
        }

        return sb.toString();
    }

    private String getlaetIssue(Elements page_lottery) {
        List<List> subWebHarf = SubwebUtil.subWeb(page_lottery.html(), new SubWebInfo[]{new SubWebInfo().setStartD("class=\"current\">").setEndD("</span>").setConer(false)});
        return (String) subWebHarf.get(0).get(0);
    }

    private String getJihuaOrder(String url, Elements pj4) {

        for (Element element : pj4) {
            String html = element.html();
            if (html.contains("冠"))
                continue;
//        o.e(html.toString());
            List<List> subWebHarf = SubwebUtil.subWeb(html, new SubWebInfo[]{new SubWebInfo().setStartD("<a").setEndD("</a>").setConer(true)});
            for (Object list : subWebHarf.get(0)) {
//            o.e(list.toString());
                List<List> lists = SubwebUtil.subWeb((String) list, new SubWebInfo[]{new SubWebInfo().setStartD("href=\"").setEndD("\"").setConer(false)});
                if (url.contains((CharSequence) lists.get(0).get(0))) {
                    return (String) SubwebUtil.subWeb((String) list, new SubWebInfo[]{new SubWebInfo().setStartD("<p>").setEndD("</p>").setConer(false)}).get(0).get(0);
                }
            }
        }
        return "";

    }

    private List<JihuaInfo.Data> getJihuaData(Elements bang_main) {
        String html = bang_main.get(0).getElementsByClass("plan-table-content-index block").get(0).html();
        List<List> lists = SubwebUtil.subWeb(html, new SubWebInfo[]{new SubWebInfo().setStartD("<ul>").setEndD("</ul>").setConer(true)});
        List<JihuaInfo.Data> dataList = new ArrayList<>();
        for (Object list : lists.get(0)) {
            JihuaInfo.Data data = new JihuaInfo.Data();
//            o.e((String) list);
            if (((String) list).contains("fontcolor=\"red\""))
                data.setNextJihua(true);

            List<List> web = SubwebUtil.subWeb((String) list, new SubWebInfo[]{new SubWebInfo().setStartD("<ul><li>").setEndD("</li><li>").setConer(false)});
//            o.e(web.toString());
            data.setJihuaIssue((String) web.get(0).get(0));

            String reg = null;
            List<String> subUtil = null;
            try {
                reg = "<spanclass=.*?>(.*?)</span></li>";
                subUtil = PattenUtil.getSubUtil((String) list, reg, false);
                data.setWinNumber(subUtil.get(0));
            } catch (Exception e) {

            }
//            o.e(subUtil.toString());
            reg = "<fontcolor=.*?>(.*?)</font></li>";
            subUtil = PattenUtil.getSubUtil((String) list, reg, false);
            if (!data.isNextJihua()) {
                String winOrCurrentIssue = subUtil.get(0);
                if (winOrCurrentIssue.length() > 3)
                    winOrCurrentIssue = winOrCurrentIssue.substring(1);
                data.setWinOrCurrentIssue(winOrCurrentIssue);
            } else {
                String winOrCurrentIssue = subUtil.get(1);
                if (winOrCurrentIssue.length() > 3)
                    winOrCurrentIssue = winOrCurrentIssue.substring(1);
                data.setWinOrCurrentIssue(winOrCurrentIssue);
            }
            lists = SubwebUtil.subWeb((String) list, new SubWebInfo[]{new SubWebInfo().setStartD("</font></li><li>").setEndD("<").setConer(false)});

            if (data.isNextJihua())
                data.setForecastJihua((String) lists.get(0).get(1));
            else
                data.setForecastJihua((String) lists.get(0).get(0));
//            o.e(lists.toString());

            lists = SubwebUtil.subWeb((String) list, new SubWebInfo[]{new SubWebInfo().setStartD("</li><li><spanclass=\"").setEndD("\">").setConer(false)});
//  
            try {
                data.setJihuaResult((String) lists.get(0).get(1));
            } catch (Exception e) {
            }
            dataList.add(data);
        }
        return dataList;
    }

    private JihuaInfo.Typeinfo getTypeInfo(Element slide) {
        String type = "xyft";

        Elements slideElements = slide.getElementsByClass("index-nav bui-fluid-space");
        for (Element slideElement : slideElements) {
            Elements allElements = slideElement.getAllElements();
            for (Element allElement : allElements) {
                if (!allElement.html().contains("href"))
                    continue;
                Elements hrefElements = allElement.getAllElements();
                for (Element hrefElement : hrefElements) {
                    if (!hrefElement.html().contains("href"))
                        continue;
//                    o.e("hrefElement:"+hrefElement.html()+" \n ");
                    SubWebInfo[] webInfoS = new SubWebInfo[1];
                    webInfoS[0] = new SubWebInfo();
                    webInfoS[0].startD = "<a";
                    webInfoS[0].endD = "</a>";
                    List list1 = SubwebUtil.subWeb(hrefElement.html(), webInfoS).get(0);
                    for (Object list : list1) {
                        if (!((String) list).contains(type))
                            continue;
//                        o.e("list:" + list + " \n ");
                        JihuaInfo.Typeinfo typeinfo = new JihuaInfo.Typeinfo();
                        List<List> subWebHarf = SubwebUtil.subWeb((String) list, new SubWebInfo[]{new SubWebInfo().setStartD("<ahref=\"").setEndD("\"").setConer(false)});
                        List<List> subWebImg = SubwebUtil.subWeb((String) list, new SubWebInfo[]{new SubWebInfo().setStartD("<imgsrc=\"").setEndD("\"").setConer(false)});
                        List<List> subWebName = SubwebUtil.subWeb((String) list, new SubWebInfo[]{new SubWebInfo().setStartD("span1\">").setEndD("</div>").setConer(false)});
                        typeinfo.setTypeHref((String) subWebHarf.get(0).get(0));
                        typeinfo.setTypeimg((String) subWebImg.get(0).get(0));
                        List list2 = subWebName.get(0);
//                        o.e(list2.toString());
                        typeinfo.setTypeName((String) list2.get(0));

//                        o.e(typeinfo.getTypeName());
                        return typeinfo;
                    }

                }

            }
        }
        return null;
    }

    private List<JihuaInfo.Data> operateJihuaData(List<JihuaInfo.Data> jihuaDataS) {

//        o.e("============================ 1 ");
        for (int i = 0; i < jihuaDataS.size() - 1; i++) {
          
            JihuaInfo.Data data = jihuaDataS.get(i);
            String jihuaIssue = data.getJihuaIssue();
            String[] split = jihuaIssue.split("-");
            int cIssue = Integer.parseInt(data.getWinOrCurrentIssue());
            int startIssue = Integer.parseInt(split[0].substring(1));
            int endIssue = Integer.parseInt(split[1].substring(1));
            int num = cIssue - startIssue;
//            o.e(data.toString());

            if (num > 0) {
                mm(jihuaDataS, i, data, startIssue, num);
                i += num;
            } else if (num < 0) {
                num = 180 + num;
                mm(jihuaDataS, i, data, startIssue, num);
                i += num;
            }
        }
//        o.e("============================ 2 ");

        return jihuaDataS;
    }

    private void mm(List<JihuaInfo.Data> jihuaDataS, int i, JihuaInfo.Data data, int startIssue, int num) {
        int c = 0;
        for (int j = num - 1; j >= 0; j--) {
            c++;
            JihuaInfo.Data data1 = new JihuaInfo.Data();
            int is = startIssue + j;
            data1.setWinOrCurrentIssue((is > 180 ? is - 180 : is) + "");
            data1.setWinNumber("");
            data1.setJihuaResult("错");
            data1.setForecastJihua(data.getForecastJihua());
            jihuaDataS.add(i + c, data1);
//            o.e(data1.toString());
        }
    }

    
}
