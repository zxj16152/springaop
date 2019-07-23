package com.example.springaop.User;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author zhouxinjian
 * @Date 2019-07-10 14:41
 **/
//@Data
@Accessors(chain =true)
//@RequiredArgsConstructor(staticName = "of")
//@NoArgsConstructor(staticName = "of")
@Builder
public class User {
    @NonNull
    private Long id;
    //    @NonNull
    private String name;

//    @Delegate
//    private List<String > list = new ArrayList<>();
//    public void testDemo() {
//        new User();
//    }
}
