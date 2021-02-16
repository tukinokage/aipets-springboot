package com.shay.aipets.mapper;

import com.shay.aipets.dto.DailyRecord;
import com.shay.aipets.dto.DataTablePost;
import com.shay.aipets.entity.UserDailyRecordItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DaillyRecordMapper {
    int insert(DailyRecord dailyRecord);
    List<UserDailyRecordItem> query(@Param("userId") String userId, @Param("startNum") int start, @Param("endNum") int end));
}
