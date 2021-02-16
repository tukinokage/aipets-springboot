package com.shay.aipets.services;

import com.shay.aipets.dto.DailyRecord;
import com.shay.aipets.entity.UserDailyRecordItem;
import com.shay.aipets.mapper.DaillyRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("dailyRecordService")
public class DailyRecordServiceImpl implements DailyRecordService{
    @Autowired
    DaillyRecordMapper daillyRecordMapper;

    @Override
    public boolean insertDailyRecord(DailyRecord dailyRecord) {
        int insert = daillyRecordMapper.insert(dailyRecord);
        if(insert > 0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public List<UserDailyRecordItem> query(String userId, int perPaperNum, int currentPaperNum) throws Exception {
        int start = currentPaperNum * perPaperNum - 1;
        int end = start + perPaperNum ;
        return daillyRecordMapper.query(userId, start, end);
    }

}
