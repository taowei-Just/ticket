
package com.example.demo.crawl.iml;

import com.example.demo.data.PlanDetail;
import com.example.demo.data.PlanKind;

import java.util.List;

public interface Icrawl {

    List<PlanDetail> crawl(PlanKind planKind ) throws Exception;

}





