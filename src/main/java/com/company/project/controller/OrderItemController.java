package com.company.project.controller;
import com.company.project.common.core.Result;
import com.company.project.common.core.ResultGenerator;
import com.company.project.model.OrderItem;
import com.company.project.service.OrderItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: YUEXIN
 * @Date: 2020-04-07 17:04:40
 */
@RestController
@RequestMapping("/order/item")
public class OrderItemController {
    @Resource
    private OrderItemService orderItemService;

    @PostMapping("/add")
    public Result add(@RequestBody OrderItem orderItem) {
        orderItemService.insert(orderItem);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        orderItemService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody OrderItem orderItem) {
        orderItemService.update(orderItem);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        OrderItem orderItem = orderItemService.findById(id);
        return ResultGenerator.genSuccessResult(orderItem);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<OrderItem> list = orderItemService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
