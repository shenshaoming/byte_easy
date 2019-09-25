package com.mbyte.easy.generator.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.generator.CodeGenerator;
import com.mbyte.easy.generator.entity.SysGenerator;
import com.mbyte.easy.generator.service.ISysGeneratorService;
import com.mbyte.easy.common.controller.BaseController;
import com.mbyte.easy.common.web.AjaxResult;
import com.mbyte.easy.util.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


/**
* <p>
* 前端控制器
* </p>
* @author 申劭明
* @since 2019-03-11
*/
@Controller
@RequestMapping("/generator/sysGenerator")
public class SysGeneratorController extends BaseController  {
    private static final Logger logger = LoggerFactory.getLogger(SysGeneratorController.class);
    private String prefix = "generator/sysGenerator/";

    @Autowired
    private ISysGeneratorService sysGeneratorService;

    @ModelAttribute("databases")
    public List<String> resourceList() {

        return sysGeneratorService.getTables();
    }

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param sysGenerator
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize, String updateTimeSpace, String createTimeSpace, SysGenerator sysGenerator) {
        Page<SysGenerator> page = new Page<SysGenerator>(pageNo, pageSize);
        QueryWrapper<SysGenerator> queryWrapper = new QueryWrapper<SysGenerator>();
        if(!ObjectUtils.isEmpty(sysGenerator.getModuleName())) {
            queryWrapper = queryWrapper.like("moduleName",sysGenerator.getModuleName());
         }
        if(!ObjectUtils.isEmpty(sysGenerator.getTableName())) {
            queryWrapper = queryWrapper.like("tableName",sysGenerator.getTableName());
         }
        IPage<SysGenerator> pageInfo = sysGeneratorService.page(page, queryWrapper);
        model.addAttribute("searchInfo", sysGenerator);
        model.addAttribute("pageInfo", new PageInfo(pageInfo));
        return prefix+"list";
    }

    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("addBefore")
    public String addBefore(){
        return prefix+"add";
    }
    /**
    * 添加
    * @param sysGenerator
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(SysGenerator sysGenerator){
        int ignoreFlag = sysGenerator.getIgnoreFlag();
        if(ignoreFlag != 1){
            sysGenerator.setIgnorePrefix("");
        }
        LocalDateTime now = LocalDateTime.now();
        sysGenerator.setUpdateTime(now);
        sysGenerator.setCreateTime(now);
        return toAjax(sysGeneratorService.save(sysGenerator));
    }

    /**
     * @Author 申劭明
     * @Description 批量生成代码
     * @Date 10:53 2019/4/16
     * @Param [ids]
     * @return com.mbyte.easy.common.web.AjaxResult
     **/
    @PostMapping("createCode")
    @ResponseBody
    public AjaxResult createCode(@RequestBody List<Long> ids){
        for(long id : ids){
            //通过ID在生成表中查询得到用户填入的与生成相关的信息
            SysGenerator generator = sysGeneratorService.getById(id);
            try {
                CodeGenerator.createCode(generator.getModuleName(),
                        generator.getTableName(), generator.getIgnoreFlag() +"",generator.getIgnorePrefix(),generator.getCreateRest() + "");
            }catch (IOException e) {
                logger.error(e.getMessage());
                return this.error("生成失败");
            }
        }

        return this.success("创建成功");
    }


    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("sysGenerator",sysGeneratorService.getById(id));
        return prefix+"edit";
    }
    /**
    * 修改
    * @param sysGenerator
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(SysGenerator sysGenerator){
        int ignoreFlag = sysGenerator.getIgnoreFlag();
        if(ignoreFlag != 1){
            sysGenerator.setIgnorePrefix("");
        }
        sysGenerator.setUpdateTime(LocalDateTime.now());
        return toAjax(sysGeneratorService.updateById(sysGenerator));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(sysGeneratorService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(sysGeneratorService.removeByIds(ids));
    }

}

