package com.liu.org.service;

import com.liu.org.pojo.Flight;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.org.vo.Result;
import com.liu.org.vo.params.PageParams;

/**
* @author kiwi
* @description 针对表【flight】的数据库操作Service
* @createDate 2022-11-05 20:52:01
*/
public interface FlightService extends IService<Flight> {

//    Result getFlightByAll(String source , String destination ,String dateOfJourney);

    /**
     * 分页查询 航程列表
     * @param pageParams
     * @return
     */
    Result listFlight(PageParams pageParams);

    /**
     *添加airplane的字段链接
     */
    void addAid();

    void updateDB();

    Result listFlightByAirlineName(PageParams pageParams);
}
