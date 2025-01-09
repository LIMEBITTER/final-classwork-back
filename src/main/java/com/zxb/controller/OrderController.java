package com.zxb.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxb.common.Result;
import com.zxb.entity.Order;
import com.zxb.entity.OrderHistory;
import com.zxb.entity.Role;
import com.zxb.entity.UserOrder;
import com.zxb.entity.dto.AuditFormDto;
import com.zxb.entity.dto.FormDto;
import com.zxb.entity.dto.OrderAHistoryDto;
import com.zxb.entity.dto.UserOrderDto;
import com.zxb.service.DetailService;
import com.zxb.service.OrderHistoryService;
import com.zxb.service.OrderService;
import com.zxb.service.UserOrderService;
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
    private final UserOrderService userOrderService;

    //构造器输入
    @Autowired
    public OrderController(OrderService orderService,DetailService detailService,IdGeneratorUtil idGeneratorUtil,OrderHistoryService orderHistoryService,UserOrderService userOrderService){
        this.orderService = orderService;
        this.detailService = detailService;
        this.idGeneratorUtil = idGeneratorUtil;
        this.orderHistoryService = orderHistoryService;
        this.userOrderService = userOrderService;
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
            return Result.success("审核成功!");
        }

        return Result.error("审核失败！");

    }

    //获取当前订单历史记录
    @GetMapping("/getHistoryOrder")
    public Result getHistoryOrder(@RequestParam("orderId") String orderId){
        LambdaQueryWrapper<OrderHistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderHistory::getOrderId,orderId).orderByDesc(OrderHistory::getCreateTime);
        List<OrderHistory> list = orderHistoryService.list(queryWrapper);
        return Result.success(list);
    }

    /**
     * 分配用户或者转交用户
     * @param dto
     * @return
     */
    @PostMapping("/allocServiceman")
    public Result allocServiceman(@RequestBody UserOrderDto dto){
        System.out.println(dto);
        //分配师傅或者转交工单
        LambdaQueryWrapper<UserOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserOrder::getOrderId,dto.getOrderId());
        UserOrder one = new UserOrder();


        OrderHistory history = new OrderHistory();
        history.setOrderId(dto.getOrderId());
        history.setOperatorName(dto.getOperatorName());

        UserOrder userOrder = new UserOrder();
        userOrder.setAllocUserId(dto.getAllocUserId());
        userOrder.setOrderId(dto.getOrderId());


        LambdaQueryWrapper<Order> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Order::getOrderId,dto.getOrderId());
        Order order = orderService.getOne(queryWrapper1);
        order.setState(3);


        //不等于null，即转交操作
        if (userOrderService.exists(queryWrapper)){
            userOrderService.update(userOrder,queryWrapper);
//            order.setRelatedPerson(dto.getAllocUserId());
            orderService.update(order,queryWrapper1);

            history.setCirculation(2);
            history.setCurrentNode("转交工单");
            history.setRemark(dto.getOperatorName()+"转交给"+dto.getAllocUserName());
            orderHistoryService.save(history);

            return Result.success("转交成功");

        }


        //等于null，插入操作
        one.setOrderId(dto.getOrderId());
        one.setAllocUserId(dto.getAllocUserId());
        userOrderService.save(one);
//        order.setRelatedPerson(dto.getAllocUserId());
        orderService.update(order,queryWrapper1);

        history.setCirculation(1);
        history.setCurrentNode("分配工单");
        history.setRemark(dto.getOperatorName()+"分配给"+dto.getAllocUserName());
        orderHistoryService.save(history);

        return Result.success("分配成功");
    }


    @GetMapping("/findAllocUser")
    public Result findAllocUser(@RequestParam("orderId") String orderId){
        LambdaQueryWrapper<UserOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserOrder::getOrderId,orderId);
        UserOrder one = userOrderService.getOne(queryWrapper);
        return Result.success(one);
    }





}
