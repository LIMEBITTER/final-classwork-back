package com.zxb.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxb.common.Result;
import com.zxb.entity.Order;
import com.zxb.entity.OrderHistory;
import com.zxb.entity.Role;
import com.zxb.entity.dto.AuditFormDto;
import com.zxb.entity.dto.FormDto;
import com.zxb.entity.dto.OrderAHistoryDto;
import com.zxb.service.DetailService;
import com.zxb.service.OrderHistoryService;
import com.zxb.service.OrderService;
import com.zxb.utils.IdGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.Jar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final IdGeneratorUtil idGeneratorUtil;
    private final DetailService detailService;
    private final OrderHistoryService orderHistoryService;

    //构造器输入
    @Autowired
    public OrderController(OrderService orderService,DetailService detailService,IdGeneratorUtil idGeneratorUtil,OrderHistoryService orderHistoryService){
        this.orderService = orderService;
        this.detailService = detailService;
        this.idGeneratorUtil = idGeneratorUtil;
        this.orderHistoryService = orderHistoryService;
    }


    /**
     * 工单申请
     * @param formDto 表单申请对象
     * @return Result
     */
    //todo 后期需要完成上传多个照片功能
    @PostMapping("/save")
    public Result save(@RequestBody FormDto formDto){

        orderService.saveOrder(formDto);
        detailService.saveDetail(formDto);
        orderHistoryService.saveHistory(formDto);

        return Result.success();
    }

    /**
     * 根据规则生成20位工单id
     * @return Result
     */
    @GetMapping("/getRandomId")
    public Result generateId(){
        return Result.success(idGeneratorUtil.generateId());
    }

    @GetMapping("/findOrderPage")
    public Result findPage(@RequestParam(name = "title",required = false) String title,
                           @RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize){

        Page<OrderAHistoryDto> page = new Page<>(pageNum,pageSize);
        IPage<OrderAHistoryDto> combines = orderService.getOrderAHistoryPage(page,title);

        return Result.success(combines);
    }

    @GetMapping("/findByOrderId")
    public Result findByOrderId (@RequestParam(name = "orderId",required = false) String orderId){

        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getOrderId, orderId);
        Order one = orderService.getOne(queryWrapper);
        if (one!=null){
            return Result.success(one);
        }

        return Result.error("该工单不存在！");
    }
    //审核

    @PostMapping("/audit")
    public Result auditOrder(@RequestBody AuditFormDto formDto){

        System.out.println("dfjddkljfdl"+formDto.getOrderId());

        Order one = orderService.getOne(new LambdaQueryWrapper<Order>()
                .eq(Order::getOrderId, formDto.getOrderId()));
        one.setState(formDto.getState());
        boolean update = orderService.updateById(one);

//        OrderHistory history = orderHistoryService.getOne(new LambdaQueryWrapper<OrderHistory>()
//                .eq(OrderHistory::getOrderId, formDto.getOrderId()));

        OrderHistory history = new OrderHistory();
        history.setCirculation(1);
        history.setOperatorName(formDto.getOperatorName());
        history.setCurrentNode("管理员审核");
        history.setOrderId(formDto.getOrderId());
        history.setRemark(formDto.getRemark());

        boolean save = orderHistoryService.save(history);

        if (save && update){
            return Result.success();
        }

        return Result.error("审核失败！");

    }

    @GetMapping("/getHistoryOrder")
    public Result getHistoryOrder(@RequestParam("orderId") String orderId){
        LambdaQueryWrapper<OrderHistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderHistory::getOrderId,orderId).orderByDesc(OrderHistory::getCreateTime);
        List<OrderHistory> list = orderHistoryService.list(queryWrapper);
        return Result.success(list);
    }







}
