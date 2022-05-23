package com.cdu.lys.graduation.test;


import com.cdu.lys.graduation.domain.user.wx.LoginUser;
import com.cdu.lys.graduation.mini.coupon.service.CouponService;
import com.cdu.lys.graduation.redis.RedisService;
import com.cdu.lys.graduation.repository.dao.UserDOMapper;
import com.cdu.lys.graduation.task.TaskService;
import com.cdu.lys.graduation.types.exception.BusinessException;
import com.cdu.lys.graduation.types.exception.ErrorType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author liyinsong
 * @date 2022/1/3 15:42
 */
@RestController()
@RequestMapping("/test")
@Api(tags = "系统测试接口")
@Slf4j
public class TestController {

    @Resource
    UserDOMapper userDOMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    CouponService couponService;

    @Autowired
    private TaskService taskService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @ApiOperation(value = "测试方法")
    @GetMapping("/hello")
    public String test(){

        System.out.println("mapper="+userDOMapper.selectByPrimaryKey(1));
        return "hello";
    }

    @GetMapping("/exception")
    public void testException(){
        throw new BusinessException(ErrorType.BIZ_ERROR, ErrorType.BIZ_ERROR.getMessage());
    }

    @GetMapping("/redis")
    public void testRedis(){
        Test test = new Test();
        test.setAge("12");
        test.setName("test");

        redisTemplate.opsForValue().set("test", test);
    }

    @GetMapping("/redisService")
    public void testRedisService(String token){
        LoginUser wxLoginValue = redisService.getLoginValue(token);
        System.out.println(wxLoginValue.getUsername());
    }

    @GetMapping("/testUserService")
    public void testUserService() {
        couponService.minusCouponNumber(2);
    }

    @GetMapping("/bill")
    public void testBillTask() {
        taskService.billTask();
    }

    @GetMapping("/test/record")
    public void testRecordTask() {
        taskService.recordDailyNumber();
    }

    @GetMapping("/reply")
    public void testAutoReply(){
        taskService.autoReply();
    }

    @GetMapping("/calculate/score")
    public void testCalculateScore() {
        taskService.calculateCommentScore();
    }

    @GetMapping("/goodsScore")
    public void testCalculateGoodsScore(){
        taskService.calculateGoodsScore();
    }

//    @Scheduled(cron = "*/1 * * * * ?")
    public void TestScheduleTask() {
        log.info("定时任务1");

    }

    @Data
    class Test implements Serializable {
        private String name;
        private String age;
    }

    public static void main(String[] args) {
        int a=28;
        String format = String.format("%03d", a);
        System.out.println(format);

    }
}