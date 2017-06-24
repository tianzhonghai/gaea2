package com.tim.gaea2.web.controllers;

import com.tim.gaea2.core.utils.SpringUtil;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tianzhonghai on 2017/6/12.
 */
@Controller
@RequestMapping("/sort")
public class SortController {
    public String demo(){
        return "";
    }

}
