package com.example.demo.mapper;

import com.example.demo.data.Api;
import com.example.demo.data.Rule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 获取接口
 * 1. 通过接口id
 * 2. 通过接口名称
 * 3. 通过接口类型
 */
@Mapper
public interface RuleInfoMapper {
       Rule selectRuleById(@Param("id") int  ruleid);


//       DELETE   FROM `zgzxjg_five_five_0_10`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_five_five_0_11`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_five_five_0_12`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_five_five_0_13`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_five_five_0_20`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_five_five_0_21`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_five_five_0_22`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_five_five_0_23`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_five_five_1_10`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_five_five_1_11`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_five_five_1_12`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_five_five_1_13`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_five_five_2_20`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_five_five_2_21`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_five_five_2_22`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_five_five_2_23`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_five_five_1_20`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_five_five_1_21`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_five_five_1_22`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_five_five_1_23`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_five_five_2_10`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_five_five_2_11`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_five_five_2_12`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_five_five_2_13`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_six_two_3_20`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_six_two_3_21`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_six_two_3_22`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_six_two_3_23`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_five_three_1_10`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_five_three_1_11`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_five_three_1_12`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_five_three_1_13`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_five_two_4_20`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
//               DELETE   FROM ``zgzxjg_five_two_4_21`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM ``zgzxjg_five_two_4_22`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_five_two_4_23`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_five_three_4_10`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_five_three_4_11`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//       DELETE   FROM `zgzxjg_five_three_4_12`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
//               DELETE   FROM `zgzxjg_five_three_4_13`   WHERE  issue  >=20190714084  and  issue <=20190714154 ;
// 
       
}
