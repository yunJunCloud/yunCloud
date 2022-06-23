package com.cloud.yun.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName BaseServiceImpl
 * @Description 基本实现单表的增删改查
 * @Author jack
 * @Date 6/23/2022 6:24 PM
 * @Version 1.0
 **/
public class BaseServiceImpl<K extends BaseMapper, T extends BaseModel> implements BaseService<T> {

	@Autowired
	protected K baseMapper;

	@Override
	public int insert(T entity) {
		return this.baseMapper.insert(entity);
	}

	@Override
	public int deleteById(Serializable id) {
		return this.baseMapper.deleteById(id);
	}

	@Override
	public int deleteById(T entity) {
		return this.baseMapper.deleteById(entity);
	}

	@Override
	public int deleteByMap(Map<String, Object> columnMap) {
		return this.baseMapper.deleteByMap(columnMap);
	}

	@Override
	public int delete(Wrapper<T> queryWrapper) {
		return this.baseMapper.delete(queryWrapper);
	}
	@Override
	public int deleteBatchIds(Collection<?> idList) {
		return this.baseMapper.deleteBatchIds(idList);
	}

	@Override
	public int updateById(T entity) {
		return this.baseMapper.updateById(entity);
	}

	@Override
	public int update(T entity, Wrapper<T> updateWrapper) {
		return this.baseMapper.update(entity,updateWrapper);
	}

	@Override
	public T selectById(Serializable id) {
		return (T) this.baseMapper.selectById(id);
	}

	@Override
	public List<T> selectBatchIds(Collection<? extends Serializable> idList) {
		return this.baseMapper.selectBatchIds(idList);
	}

	@Override
	public List<T> selectByMap(Map<String, Object> columnMap) {
		return this.selectByMap(columnMap);
	}

	@Override
	public Long selectCount(Wrapper<T> queryWrapper) {
		return this.baseMapper.selectCount(queryWrapper);
	}

	@Override
	public List<T> selectList(Wrapper<T> queryWrapper) {
		return this.baseMapper.selectList(queryWrapper);
	}

	@Override
	public List<Map<String, Object>> selectMaps(Wrapper<T> queryWrapper) {
		return this.baseMapper.selectMaps(queryWrapper);
	}

	@Override
	public List<Object> selectObjs(Wrapper<T> queryWrapper) {
		return this.baseMapper.selectObjs(queryWrapper);
	}

	@Override
	public <P extends IPage<T>> P selectPage(P page, Wrapper<T> queryWrapper) {
		return (P) this.baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<T> queryWrapper) {
		return (P) this.baseMapper.selectMapsPage(page,queryWrapper);
	}
}
