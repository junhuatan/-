package com.liu.org.vo.params;

import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

@Data
public class SeatParam {

    private Integer fid;

    private String seatType;

    private Integer oid;

    private String seatNumber;

}
