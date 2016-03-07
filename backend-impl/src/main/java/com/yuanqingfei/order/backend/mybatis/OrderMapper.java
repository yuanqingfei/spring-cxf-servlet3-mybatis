package com.yuanqingfei.order.backend.mybatis;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.yuanqingfei.order.backend.soap.order.domain.BackendOrder;

@Repository
public interface OrderMapper {
	
	@Select("SELECT * FROM orders WHERE seqNo = #{seqNo}")
	BackendOrder getOrder(@Param("seqNo") String seqNo);

}
