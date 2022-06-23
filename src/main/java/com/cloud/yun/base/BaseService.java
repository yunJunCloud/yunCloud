package com.cloud.yun.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName BaseService
 * @Description 定义基础的业务接口
 * @Author jack
 * @Date 6/23/2022 6:20 PM
 * @Version 1.0
 **/
public interface BaseService<T extends BaseModel>{
	int insert(T entity);

	int deleteById(Serializable id);

	int deleteById(T entity);

	int deleteByMap(Map<String, Object> columnMap);

	int delete(Wrapper<T> queryWrapper);

	int deleteBatchIds(Collection<?> idList);

	int updateById(T entity);

	int update(T entity, Wrapper<T> updateWrapper);

	T selectById(Serializable id);

	List<T> selectBatchIds(Collection<? extends Serializable> idList);

	List<T> selectByMap(Map<String, Object> columnMap);

	default T selectOne(Wrapper<T> queryWrapper) {
		List<T> ts = this.selectList(queryWrapper);
		if (CollectionUtils.isNotEmpty(ts)) {
			if (ts.size() != 1) {
				throw ExceptionUtils.mpe("One record is expected, but the query result is multiple records", new Object[0]);
			} else {
				return ts.get(0);
			}
		} else {
			return null;
		}
	}

	default boolean exists(Wrapper<T> queryWrapper) {
		Long count = this.selectCount(queryWrapper);
		return null != count && count > 0L;
	}

	Long selectCount(Wrapper<T> queryWrapper);

	List<T> selectList(Wrapper<T> queryWrapper);

	List<Map<String, Object>> selectMaps(Wrapper<T> queryWrapper);

	List<Object> selectObjs(Wrapper<T> queryWrapper);

	<P extends IPage<T>> P selectPage(P page, Wrapper<T> queryWrapper);

	<P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<T> queryWrapper);

}
