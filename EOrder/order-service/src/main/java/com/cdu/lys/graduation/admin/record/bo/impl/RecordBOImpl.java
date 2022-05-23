package com.cdu.lys.graduation.admin.record.bo.impl;

import com.cdu.lys.graduation.admin.record.bo.RecordBO;
import com.cdu.lys.graduation.repository.dao.SystemRecordDOMapper;
import com.cdu.lys.graduation.repository.entity.SystemRecordDO;
import com.cdu.lys.graduation.repository.entity.SystemRecordDOExample;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author liyinsong
 * @date 2022/4/13 19:53
 */
@Service
public class RecordBOImpl implements RecordBO {

    @Autowired
    private SystemRecordDOMapper systemRecordDOMapper;

    @Cacheable(value = "YesterdayRecord")
    @Override
    public SystemRecordDO getYesterdayRecord() {
        SystemRecordDOExample example = new SystemRecordDOExample();
        example.setOrderByClause("record_date desc");
        PageHelper.startPage(1, 1);
        return systemRecordDOMapper.selectByExample(example).get(0);
    }
}
