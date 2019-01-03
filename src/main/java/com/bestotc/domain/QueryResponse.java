package com.bestotc.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用的集合返回
 * @author qxx on 2018/7/12.
 */
@Getter
@Setter
public class QueryResponse<T> {

    /**
     * 订单列表项
     */
    private List<T> list;

    /**
     * 查询总数量
     */
    private Long total;

    public QueryResponse() {
    }

    public QueryResponse(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }
}
