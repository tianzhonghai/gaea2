package com.tim.gaea2.web.controllers;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.tim.gaea2.core.utils.GuavaCacheUtils;
import com.tim.gaea2.core.utils.SecretUtils;
import com.tim.gaea2.domain.model.SysUser;
import com.tim.gaea2.domain.service.UserInfoService;
import com.tim.gaea2.web.models.UserModel;
import com.tim.gaea2.web.models.UserQueryModel;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

        return "redirect:/user/index";
    }

    @RequestMapping(value = "/queryUserList",method = RequestMethod.GET)
    @ResponseBody
    public List<UserQueryModel> queryUserList() {
        List<InetAddress> addrs = new ArrayList<>();
        try {
            InetAddress addr = InetAddress.getByName("172.16.13.65");
            addrs.add(addr);

            InetAddress addr2 = InetAddress.getByName("172.16.13.73");
            addrs.add(addr2);

        }catch (UnknownHostException ex){}

        TransportClient client = null;
        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", "gaea")
                    //.put("name","node-1")
                    .put("client.transport.sniff", true)
                    //.put("action.bulk.compress", true)
                    .build();
            client = TransportClient.builder().settings(settings).build();
            for (InetAddress addr :addrs) {
                client.addTransportAddress(new InetSocketTransportAddress(addr, 9300));
            }

            SearchRequestBuilder searchReq = client.prepareSearch("gaea").setTypes("user").setFrom(0).setSize(20).setExplain(true);

            BoolQueryBuilder boolenFilter = QueryBuilders.boolQuery()
                    .must(QueryBuilders.termQuery("id",1));

            BoolQueryBuilder filteredQueryBuilder = QueryBuilders.boolQuery().filter(boolenFilter);
            searchReq.setQuery(filteredQueryBuilder);
            //searchReq.addSort(SORT_FIELD, SortOrder.valueOf(ORDER_TYPE));
            SearchResponse res = searchReq.execute().actionGet(TimeValue.timeValueMillis(1500));

            client.close();
        }
        catch (Exception ex){
            logger.error(ex.getMessage(),ex);
        }
        finally {
            if(client != null) client.close();
        }

        return null;
    }

    @RequestMapping(value = "/rebuild",method = RequestMethod.GET)
    @ResponseBody
    public String rebuild(){
        List<SysUser> userList = userInfoService.getAllUserVOs();
        if(userList == null || userList.size() == 0){
            return "user empty";
        }

        TransportClient client = null;
        try {
            Settings settings = Settings.builder().put("cluster.name", "gaea")
                    .put("name","node-1")
                    .put("client.transport.sniff", true).build();

            //client = new PreBuiltTransportClient(settings);

            client = TransportClient.builder().settings(settings).build();
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.16.13.65"), 9300));

            BulkRequest bulkRequest = new BulkRequest();

            for (SysUser user: userList) {
                String userJson = JSON.toJSONString(user);

                IndexRequest indexRequest =new IndexRequest();
                indexRequest.index("sys").type("user").id(user.getId().toString()).source(userJson);
                bulkRequest.add(indexRequest);
            }

            ActionFuture<BulkResponse> af = client.bulk(bulkRequest);

            client.close();
        }
        catch (UnknownHostException ex){
            logger.error(ex.getMessage(),ex);
        }
        catch (Exception ex){
            logger.error(ex.getMessage(),ex);
            throw ex;
        }
        catch(NoClassDefFoundError ex) {
            logger.error(ex.getMessage(),ex);
            throw ex;
        }
        finally {
            if(client != null) client.close();
        }
        return "ok";
    }
}
