package com.cdu.lys.graduation.admin.record.service.impl;

import com.cdu.lys.graduation.admin.record.bo.RecordBO;
import com.cdu.lys.graduation.admin.record.service.RecordService;
import com.cdu.lys.graduation.commons.utils.DateUtils;
import com.cdu.lys.graduation.domain.admin.vo.RecordVO;
import com.cdu.lys.graduation.redis.RedisService;
import com.cdu.lys.graduation.repository.dao.SystemRecordDOMapper;
import com.cdu.lys.graduation.repository.entity.SystemRecordDO;
import com.cdu.lys.graduation.repository.entity.SystemRecordDOExample;
import com.cdu.lys.graduation.types.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/4/13 15:27
 */
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SystemRecordDOMapper systemRecordDOMapper;

    @Autowired
    private RecordBO recordBO;

    @Override
    public RecordVO getDailyVisit() {
        String visit = redisService.getKey(SystemConstant.DAILY_VISIT_NUM_KEY);

        SystemRecordDO recordDO = recordBO.getYesterdayRecord();
        if (!StringUtils.hasLength(visit)) {
            visit = "0";
        }
        String rate = this.calculateRate(recordDO.getVisitNum(), Integer.parseInt(visit));
        return new RecordVO(visit, rate);
    }

    @Override
    public RecordVO getDailySales() {
        String sales = redisService.getKey(SystemConstant.DAILY_SALES_KEY);

        SystemRecordDO recordDO = recordBO.getYesterdayRecord();
        if (!StringUtils.hasLength(sales)) {
            sales = "0";
        }
        String rate = this.calculateRate(recordDO.getSales(), Integer.parseInt(sales));
        return new RecordVO(sales, rate);
    }

    @Override
    public RecordVO getDailyIncome() {
        String income = redisService.getKey(SystemConstant.DAILY_INCOME_KEY);

        SystemRecordDO recordDO = recordBO.getYesterdayRecord();
        if (!StringUtils.hasLength(income)) {
            income = "0";
        }
        String rate = this.calculateRate(recordDO.getDailyIncome(), Double.parseDouble(income));
        return new RecordVO(income, rate);
    }

    @Override
    public RecordVO getDailyCommentNum() {
        String comment = redisService.getKey(SystemConstant.DAILY_COMMENT_NUM_KEY);

        SystemRecordDO recordDO = recordBO.getYesterdayRecord();
        if (!StringUtils.hasLength(comment)) {
            comment = "0";
        }
        String rate = this.calculateRate(recordDO.getDailyCommentNum(), Integer.parseInt(comment));
        return new RecordVO(comment, rate);
    }

    @Override
    public int recordTask() {
        String visit = redisService.getKey(SystemConstant.DAILY_VISIT_NUM_KEY);
        String sales = redisService.getKey(SystemConstant.DAILY_SALES_KEY);
        String income = redisService.getKey(SystemConstant.DAILY_INCOME_KEY);
        String comment = redisService.getKey(SystemConstant.DAILY_COMMENT_NUM_KEY);

        SystemRecordDO recordDO = new SystemRecordDO();
        Date today = DateUtils.getToday();
        recordDO.setRecordDate(today);
        if (StringUtils.hasLength(comment)) {
            recordDO.setDailyCommentNum(Integer.parseInt(comment));
        }
        if (StringUtils.hasLength(income)) {
            recordDO.setDailyIncome(Double.parseDouble(income));
        }
        if (StringUtils.hasLength(sales)) {
            recordDO.setSales(Integer.parseInt(sales));
        }
        if (StringUtils.hasLength(visit)) {
            recordDO.setVisitNum(Integer.parseInt(visit));
        }

        SystemRecordDOExample example = new SystemRecordDOExample();
        example.createCriteria()
                .andRecordDateEqualTo(today);
        List<SystemRecordDO> recordDOS = systemRecordDOMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(recordDOS)) {
            return systemRecordDOMapper.insertSelective(recordDO);
        }else {
            recordDO.setId(recordDOS.get(0).getId());
            return systemRecordDOMapper.updateByPrimaryKeySelective(recordDO);
        }

    }

    /**
     * 计算百分比
     * @param num1
     * @param num2
     * @return num2相对于num1的百分比
     */
    private String calculateRate(int num1, int num2) {

        DecimalFormat fmt = new DecimalFormat("#.##%");
        double rate = 1;

        if (num1 != 0) {
            rate = (num2 - num1) * 1.0 / num1;
        }

        return fmt.format(rate);
    }

    /**
     * 计算百分比
     * @param num1
     * @param num2
     * @return
     */
    private String calculateRate(double num1, double num2) {

        DecimalFormat fmt = new DecimalFormat("#.##%");
        double rate = 1;

        if (num1 != 0) {
            rate = (num2 - num1) / num1;
        }
        return fmt.format(rate);
    }
}
