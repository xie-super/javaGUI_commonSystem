package com.achieve.entity;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.Vector;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MyComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return o2.compareTo(o1);
    }
}
public class Admin {

    private String username;
    private String password;

    public static void main(String[] args) {
    


    }

}
