package com.shay.aipets.mapper;

import com.shay.aipets.dto.DailyRecord;
import com.shay.aipets.dto.DataTablePost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DaillyRecordMapper {
    int insert(DailyRecord dailyRecord);
    int queryNum(DailyRecord dailyRecord);
    List<DailyRecord> query(DailyRecord dailyRecord);


}
