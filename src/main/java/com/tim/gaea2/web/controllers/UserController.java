package com.tim.gaea2.web.controllers;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.tim.gaea2.core.utils.GuavaCacheUtils;
import com.tim.gaea2.core.utils.SecretUtils;
import com.tim.gaea2.core.utils.SpringUtil;
import com.tim.gaea2.domain.model.SysUser;
import com.tim.gaea2.domain.service.UserInfoService;
import com.tim.gaea2.web.models.UserModel;
import com.tim.gaea2.web.models.UserQueryModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianzhonghai on 2017/3/13.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private DozerBeanMapperFactoryBean dozerBean;

    @RequestMapping("/index")
    @RequiresPermissions("user:index")
    public String index(){
        return "users/index";
    }

    @RequestMapping(value = "/getUserList",method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("user:index")
    public List<UserQueryModel> getUserList(){
        List<SysUser> userVOs = userInfoService.getAllUserVOs();

        List<UserQueryModel> models = new ArrayList<>();
        for (SysUser user : userVOs) {
            UserQueryModel model = new UserQueryModel();
            model.setId(user.getId());
            model.setUserName(user.getUserName());
            model.setCreateTime(user.getCreateTime());
            models.add(model);
        }
        return models;
    }

    @RequiresPermissions("user:view")
    @RequestMapping("/view")
    public String view(int id,Model model){
        SysUser userVO = userInfoService.getUserVoByUserId(id);
        model.addAttribute("User",userVO);
        return "users/view";
    }

    @RequiresPermissions("user:detail")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable int id, Model model) throws Exception{

        Cache<String,Object> cache = GuavaCacheUtils.getCache();

        SysUser userVO = (SysUser)cache.get(Integer.toString(id),()->{
            return userInfoService.getUserVoByUserId(id);
        });
        model.addAttribute("User",userVO);
        return "users/view";
    }

    @RequiresPermissions("user:add")
    @RequestMapping("/add")
    public String add(){
        return "users/add";
    }

    @RequiresPermissions("user:add")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(UserModel userModel, Model model){
        String pwd = "111111";
        try{
            pwd = SecretUtils.MD5(pwd);
        }catch (RuntimeException ex){
            throw new RuntimeException(ex);
        }

        org.dozer.Mapper mapper = null;
        try {
            mapper = dozerBean.getObject();
        }catch (Exception ex){}

        SysUser userVO = mapper.map(userModel,SysUser.class);
        userVO.setPassword(pwd);
        userInfoService.addUserVO(userVO);

        addToIndex(userVO);
        return "redirect:/user/index";
    }

    @RequestMapping(value = "/queryUserList",method = RequestMethod.GET)
    @ResponseBody
    public List<UserQueryModel> queryUserList(@RequestParam String k) {

        List<UserQueryModel> result =  new ArrayList<>();
        TransportClient client = SpringUtil.getBean(TransportClient.class);

        SearchRequestBuilder searchReq = client.prepareSearch("sys").setTypes("user").setFrom(0).setSize(20).setExplain(true);

        if(StringUtils.isNotEmpty(k)){
//            BoolQueryBuilder boolenFilter = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("userName",k));
//            BoolQueryBuilder filteredQueryBuilder = QueryBuilders.boolQuery().filter(boolenFilter);
//            searchReq.setQuery(filteredQueryBuilder);
            searchReq.setQuery(QueryBuilders.matchQuery("state", k));
        } else {
            searchReq.setQuery(QueryBuilders.matchAllQuery());
        }
        //searchReq.addSort(SORT_FIELD, SortOrder.valueOf(ORDER_TYPE));
        SearchResponse searchResponse = searchReq.execute().actionGet(TimeValue.timeValueMillis(1500));

        SearchHits searchHits = searchResponse.getHits();

        for (SearchHit sh :searchHits.getHits()) {
            String source = sh.getSourceAsString();
            UserQueryModel user = JSON.parseObject(source, UserQueryModel.class);
            user.setId(user.getId());
            user.setUserName(user.getUserName());
            user.setState(user.getState());
            user.setCreateTime(user.getCreateTime());
            result.add(user);
        }
        return result;
    }

    @RequestMapping(value = "/rebuild",method = RequestMethod.GET)
    @ResponseBody
    public String rebuild(){
        List<SysUser> userList = userInfoService.getAllUserVOs();
        if(userList == null || userList.size() == 0){
            return "user empty";
        }

        TransportClient client = SpringUtil.getBean(TransportClient.class);
        BulkRequest bulkRequest = new BulkRequest();

        for (SysUser user: userList) {
            UserQueryModel model = this.toUserQueryModel(user);
            String userJson = JSON.toJSONString(model);

            IndexRequest indexRequest =new IndexRequest();
            indexRequest.index("sys").type("user").id(user.getId().toString()).source(userJson);

            bulkRequest.add(indexRequest);
        }

        ActionFuture<BulkResponse> af = client.bulk(bulkRequest);
        return "ok";
    }

    @RequestMapping(value = "/mapping",method = RequestMethod.GET)
    @ResponseBody
    public String mapping() {
        String index = "sys";
        String type = "user";
        TransportClient client = SpringUtil.getBean(TransportClient.class);
        //CreateIndexResponse createIndexResponse = client.admin().indices().prepareCreate(index).execute().actionGet();
        try {
            XContentBuilder mapping  = createMapping(type);
            PutMappingRequest mappingRequest = Requests.putMappingRequest(index).type(type).source(mapping);
            PutMappingResponse putMappingResponse = client.admin().indices().putMapping(mappingRequest).actionGet();
        }
        catch (IOException ex){
            logger.error(ex.getMessage(),ex);
            return ex.getMessage();
        }catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            return ex.getMessage();
        }
        return "ok";
    }

    private XContentBuilder createMapping(String type) throws Exception {
//        XContentBuilder mapping = XContentFactory.jsonBuilder().startObject()
//                .startObject("fulltext")
//                .startObject("_all")
//                .field("indexAnalyzer", "ik")
//                .field("searchAnalyzer", "ik")
//                .field("term_vector","no")
//                .field("store","false")
//                .endObject()
//                .startObject("properties")
//                .startObject("state").field("type", "string").field("store","no")
//                .field("term_vector","with_positions_offsets").field("indexAnalyzer", "ik")
//                .field("searchAnalyzer", "ik").field("include_in_all","true").field("boost", 8)
//                .endObject()
//                .endObject()
//                .endObject()
//                .endObject();
//        return mapping;

        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject() //start boot
                .startObject(type)
                .startObject("properties")
                .startObject("id").field("type", "long").endObject()
                .startObject("username").field("type", "text").field("analyzer", "ik_smart").field("tokenizer", "ik_max_word").endObject()
                .startObject("password").field("type", "text")
                //.field("analyzer", "ik_smart").field("tokenizer", "ik_max_word")
                .endObject()
                .startObject("state").field("type", "text").field("analyzer", "ik_smart").field("tokenizer", "ik_max_word").endObject()
                .startObject("createtime").field("type", "date")
                //.field("analyzer", "ik_smart").field("tokenizer", "ik_max_word")
                .endObject()
                .endObject()
                .endObject()
                .endObject();

        return mapping;
    }

    private UserQueryModel toUserQueryModel(SysUser sysUser){
        UserQueryModel model = new UserQueryModel();
        model.setId(sysUser.getId());
        model.setUserName(sysUser.getUserName());
        model.setState(sysUser.getState());
        model.setCreateTime(sysUser.getCreateTime());
        return model;
    }

    private void addToIndex(SysUser sysUser){
        TransportClient client = SpringUtil.getBean(TransportClient.class);
        UserQueryModel model = this.toUserQueryModel(sysUser);
        String userJson = JSON.toJSONString(model);

        IndexResponse indexResponse = client.prepareIndex("sys","user").setSource(userJson).execute().actionGet();

    }
}
