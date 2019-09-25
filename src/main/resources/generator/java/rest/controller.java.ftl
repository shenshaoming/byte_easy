package ${cfg.restController}.${entity ?uncap_first};

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import ${cfg.superController};
import com.mbyte.easy.common.web.AjaxResult;
import com.mbyte.easy.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
* <p>
* 前端控制器
* </p>
* @author ${author}
* @since ${.now?date}
*/
@RestController
<#--@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if><#if controllerMappingHyphenStyle>/${controllerMappingHyphen}<#else>/${table.entityPath}</#if>")-->
@RequestMapping("rest/${entity ?uncap_first}")
public class Rest${table.controllerName} extends ${superControllerClass}  {

    @Autowired
    private ${table.serviceName} ${entity?uncap_first}Service;

    /**
    * 查询列表
    *
    * @param pageNo
    * @param pageSize
    * @param ${entity?uncap_first}
    * @return
    */
    @RequestMapping
    public AjaxResult index(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize<#list table.fields as field ><#if (field.propertyName != "id" && field.propertyName != "create_time" && field.propertyName != "update_time") ><#if (field.type == "datetime") >, String ${field.propertyName}Space</#if></#if></#list>, ${entity} ${entity?uncap_first}) {
        Page<${entity}> page = new Page<${entity}>(pageNo, pageSize);
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<${entity}>();
    <#list table.fields as field >
        <#if (field.propertyName != "id" && field.propertyName != "createTime" && field.propertyName != "updateTime") >

        if(${entity?uncap_first}.get${field.propertyName?cap_first}() != null  && !"".equals(${entity?uncap_first}.get${field.propertyName?cap_first}() + "")) {
            queryWrapper = queryWrapper.like("${field.name}",${entity?uncap_first}.get${field.propertyName?cap_first}());
         }

        </#if>
    </#list>
        IPage<${entity}> pageInfo = ${entity?uncap_first}Service.page(page, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("searchInfo",  ${entity?uncap_first});
        map.put("pageInfo", new PageInfo(pageInfo));

        return this.success(map);
    }


    /**
    * 添加
    * @param ${entity?uncap_first}
    * @return
    */
    @PostMapping("add")
    public AjaxResult add(${entity} ${entity?uncap_first}){
        return toAjax(${entity?uncap_first}Service.save(${entity?uncap_first}));
    }

    /**
    * 添加
    * @param ${entity?uncap_first}
    * @return
    */
    @PostMapping("edit")
    public AjaxResult edit(${entity} ${entity?uncap_first}){
        return toAjax(${entity?uncap_first}Service.updateById(${entity?uncap_first}));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(${entity?uncap_first}Service.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(${entity?uncap_first}Service.removeByIds(ids));
    }

}

