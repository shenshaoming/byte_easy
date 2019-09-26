package com.mbyte.easy.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.admin.entity.Cart;
import com.mbyte.easy.admin.service.ICartService;
import com.mbyte.easy.common.controller.BaseController;
import com.mbyte.easy.common.web.AjaxResult;
import com.mbyte.easy.util.PageInfo;
import com.mbyte.easy.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.ObjectUtils;
import java.util.List;
import java.time.LocalDateTime;

/**
* <p>
* 前端控制器
* </p>
* @author 申劭明
* @since 2019-9-26
*/
@Controller
@RequestMapping("/admin/cart")
public class CartController extends BaseController  {

    private String prefix = "admin/cart/";

    @Autowired
    private ICartService cartService;

    /**
    * 查询列表
    *
    * @param model
    * @param pageNo
    * @param pageSize
    * @param cart
    * @return
    */
    @RequestMapping
    public String index(Model model,@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                        @ModelAttribute("createTimeSpace")String createTimeSpace,
                        @ModelAttribute("updateTimeSpace")String updateTimeSpace, Cart cart) {
        Page<Cart> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
            if(!ObjectUtils.isEmpty(cart.getUserId())) {
                queryWrapper = queryWrapper.like("userId",cart.getUserId());
            }
            if(!ObjectUtils.isEmpty(cart.getGoodsId())) {
                queryWrapper = queryWrapper.like("goodsId",cart.getGoodsId());
            }
            if(!ObjectUtils.isEmpty(cart.getGoodsName())) {
                queryWrapper = queryWrapper.like("goodsName",cart.getGoodsName());
            }
            if(!ObjectUtils.isEmpty(cart.getGoodsDec())) {
                queryWrapper = queryWrapper.like("goodsDec",cart.getGoodsDec());
            }
            if(!ObjectUtils.isEmpty(cart.getGoodsPrice())) {
                queryWrapper = queryWrapper.like("goodsPrice",cart.getGoodsPrice());
            }
            if(!ObjectUtils.isEmpty(cart.getGoodsPic())) {
                queryWrapper = queryWrapper.like("goodsPic",cart.getGoodsPic());
            }
            if(!ObjectUtils.isEmpty(cart.getStoreId())) {
                queryWrapper = queryWrapper.like("storeId",cart.getStoreId());
            }
            if(!ObjectUtils.isEmpty(cart.getStoreName())) {
                queryWrapper = queryWrapper.like("storeName",cart.getStoreName());
            }
            if(!ObjectUtils.isEmpty(cart.getNum())) {
                queryWrapper = queryWrapper.like("num",cart.getNum());
            }
            if(!ObjectUtils.isEmpty(cart.getStatus())) {
                queryWrapper = queryWrapper.like("status",cart.getStatus());
            }
            if (!ObjectUtils.isEmpty(createTimeSpace)){
                try {
                    LocalDateTime[] localDateTimes = DateUtil.stringToLocals(createTimeSpace);
                    queryWrapper = queryWrapper.between("create_time",localDateTimes[0],localDateTimes[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!ObjectUtils.isEmpty(updateTimeSpace)){
                try {
                    LocalDateTime[] localDateTimes = DateUtil.stringToLocals(updateTimeSpace);
                    queryWrapper = queryWrapper.between("update_time",localDateTimes[0],localDateTimes[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        IPage<Cart> pageInfo = cartService.page(page, queryWrapper);
        model.addAttribute("searchInfo", cart);
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
    * @param cart
    * @return
    */
    @PostMapping("add")
    @ResponseBody
    public AjaxResult add(Cart cart){
        return toAjax(cartService.save(cart));
    }
    /**
    * 添加跳转页面
    * @return
    */
    @GetMapping("editBefore/{id}")
    public String editBefore(Model model,@PathVariable("id")Long id){
        model.addAttribute("cart",cartService.getById(id));
        return prefix+"edit";
    }
    /**
    * 添加
    * @param cart
    * @return
    */
    @PostMapping("edit")
    @ResponseBody
    public AjaxResult edit(Cart cart){
        return toAjax(cartService.updateById(cart));
    }
    /**
    * 删除
    * @param id
    * @return
    */
    @GetMapping("delete/{id}")
    @ResponseBody
    public AjaxResult delete(@PathVariable("id") Long id){
        return toAjax(cartService.removeById(id));
    }
    /**
    * 批量删除
    * @param ids
    * @return
    */
    @PostMapping("deleteAll")
    @ResponseBody
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        return toAjax(cartService.removeByIds(ids));
    }

}

