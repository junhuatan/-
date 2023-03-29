package com.liu.org.vo;

import lombok.Data;

@Data
public class SeatNumberVo {

    private int [][] seatYNumbers;

    private int [][] seatFNumbers;

    public SeatNumberVo(int [][] seatYNumbers, int [][] seatFNumbers){
        for (int i = 0; i <seatFNumbers.length; i++) {
            for (int j = 0; j < seatFNumbers[i].length; j++) {
                System.out.print(seatFNumbers[i][j]+",");
            }
            System.out.println();
        }
        for (int i = 0; i <seatYNumbers.length; i++) {
            for (int j = 0; j < seatYNumbers[i].length; j++) {
                System.out.print(seatYNumbers[i][j]+",");
            }
            System.out.println();
        }
        this.seatYNumbers = seatYNumbers;
        this.seatFNumbers = seatFNumbers;
    }
}
