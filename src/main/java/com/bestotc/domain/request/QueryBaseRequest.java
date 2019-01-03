package com.bestotc.domain.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页查询基类
 *
 * @author qxx
 */
@Getter
@Setter
public class QueryBaseRequest{
    private int pageNum = 1;
    private int pageSize = 10;
}
