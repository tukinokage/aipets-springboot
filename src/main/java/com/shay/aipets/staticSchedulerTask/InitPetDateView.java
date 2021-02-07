package com.shay.aipets.staticSchedulerTask;

import com.shay.aipets.dto.Pet;
import com.shay.aipets.dto.PetDayViewNum;
import com.shay.aipets.mapper.PetMapper;
import com.shay.aipets.services.PetService;
import com.shay.aipets.utils.TimeUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class InitPetDateView {

    @Autowired
    PetService petService;
    @Autowired
    PetMapper petMapper;

        //3.添加每日定时任务
        @Scheduled(cron = "0 0 0 1/1 * ? ")
        //或直接指定时间间隔，例如：5秒
        // @Scheduled(cron = "0/5 * * * * ?")
        // @Scheduled(fixedRate=5000)
        //0时更新每日浏览量
        private void configureTasks() {
            System.err.println("执行静态定时每日浏览初始化任务: " + LocalDateTime.now());
            final int perNum = 200;


            int num = petMapper.queryAllPetNum();
            int i = num % perNum;
            int batch = num / perNum;
            if( i != 0){
                batch++;
            }

            int startNum = 0;
            int endNum = perNum;
            PetDayViewNum petDayViewNum = new PetDayViewNum();
            petDayViewNum.setStime(TimeUntil.getDate());
            petDayViewNum.setViewNum(0);
            for(int j = 0; j < batch; j++){
                List<String> petIds = petMapper.queryIdByLimit(startNum, endNum);
                for(String petId:petIds){
                    try {
                        petDayViewNum.setPetId(petId);
                        petMapper.insertDateViewNum(petDayViewNum);
                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.println("ID: " +petId + " 初始化失败");
                    }
                }

                startNum = endNum - 1;
                endNum += perNum;
            }

            System.out.println("每日浏览量初始化结束");

        }
    }
