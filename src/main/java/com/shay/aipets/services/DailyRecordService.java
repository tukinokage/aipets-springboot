package com.shay.aipets.services;

import com.shay.aipets.dto.DailyRecord;
import com.shay.aipets.entity.UserDailyRecordItem;
import com.shay.aipets.mapper.DaillyRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DailyRecordService {
    boolean insertDailyRecord(DailyRecord dailyRecord) throws Exception;
    List<UserDailyRecordItem> query(String userId, int perPaperNum, int currentPaperNum) throws Exception;

}
