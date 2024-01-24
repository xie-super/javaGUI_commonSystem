package com.achieve.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//This class is used to store information about a sport
public class Sport {
    //The ID of the sport
    String sportId;
    //The name of the sport
    String sportName;
    //The start time of the sport
    String startTime;
    //The end time of the sport
    String endTime;
    //The type of the sport
    String type;

}
