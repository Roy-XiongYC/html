package com.person.web.admin.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import com.person.framework.annotation.Dict;
import com.person.framework.constant.DefaultConstant;
import com.person.framework.constant.MessageConstant;
import com.person.framework.mybatis.Criteria;
import com.person.framework.redis.RedisServiceUtil;
import com.person.framework.service.IMessageService;
import com.person.framework.utils.StringUtil;
import com.person.web.admin.model.SysDict;
import com.person.web.admin.repository.ISysDictRepository;
import com.person.web.admin.service.ISysDictService;
import com.person.web.blog.service.ISequenceService;

public class SysDictServiceImpl implements ISysDictService {

	@Override
	public List<Map<String, Object>> queryPageList(Criteria<SysDict> param) {
		List<SysDict> list = sysDictRepository.queryPage(param);
		return translateToMapList(list);
	}
	
	public List<SysDict> queryPage(Criteria<SysDict> param) {
		List<SysDict> list = sysDictRepository.queryPage(param);
		return list;
	}

	public Integer queryPageCount(Criteria<SysDict> param) {
		return (Integer) sysDictRepository.queryPageCount(param);
	}

	public SysDict queryEntityById(String id) {
		return sysDictRepository.queryEntityById(id);
	}
	
	public String insertOrUpdate(SysDict record) {
		String ret = null;
		try {
			//验证是否存在记录
			Criteria<SysDict> param = new Criteria<SysDict>();
			param.addParam("dictCode", record.getDictCode());
			param.addParam("dictValue", record.getDictValue());
			List<SysDict> list = queryPage(param);
			
			if (StringUtil.isNullOrBlank(record.getId())) {
				if (list != null && list.size()>0) {
					return messageService.message(MessageConstant.C00001,record.getDictCode()+"_"+record.getDictValue());
				}
				record.setId(sequenceService.getUpdateQuerySeq(DefaultConstant.SEQ_COMMON));
				ret = insert(record);
			}else {
				if (list != null && list.size()>0 && !list.get(0).getId().equals(record.getId())) {
					return messageService.message(MessageConstant.C00001,record.getDictCode()+"_"+record.getDictValue());
				}
				param.clearParams().addParam("id", record.getId());
				param.setRecord(record);
				ret = updateByCriteria(param);
				if (StringUtil.isNullOrBlank(ret)) {
					RedisServiceUtil.delJedis(RedisServiceUtil.SERVICE_REDIS_COMMON,String.format("dictCode=[%s]dictValue=[%s]", record.getDictCode(), record.getDictValue()));
				}
			}
		} catch (Exception e) {
			ret = messageService.message(MessageConstant.C00000);
		}
		
		return ret;
	}

	public String insert(SysDict record) {
		int result = sysDictRepository.insert(record);
		if (result != 1) {
			return messageService.message(MessageConstant.C00010);
		}
		return null;
	}

	public String updateByCriteria(Criteria<SysDict> param) {
		int result = sysDictRepository.updateByCriteria(param);
		if (result != 1) {
			return messageService.message(MessageConstant.C00011);
		}
		return null;
	}

	public String deleteById(String id) {
		int result = sysDictRepository.deleteById(id);
		if (result != 1) {
			return messageService.message(MessageConstant.C00012);
		}
		return null;
	}

	public String deleteByCriteria(Criteria<SysDict> param) {
		sysDictRepository.deleteByCriteria(param);
		return null;
	}
	
	public <T> List<Map<String, Object>> translateToMapList(List<T> data)
	{

		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();

		if (data == null || data.isEmpty())
		{
			return ret;
		}

		Class<?> rowClazz = data.get(0).getClass();
		Field[] fields = FieldUtils.getAllFields(rowClazz);
		if (fields == null || fields.length < 1)
		{
			return ret;
		}

		for (Object rowData : data)
		{
			Map<String, Object> row = translateToMap(rowData);
			if (row != null)
			{
				ret.add(row);
			}
		}

		return ret;
	}

	
	public Map<String, Object> translateToMap(Object obj)
	{

		Field[] fields = FieldUtils.getAllFields(obj.getClass());
		if (fields == null || fields.length < 1)
		{
			return null;
		}

		Map<String, Object> row = new HashMap<String, Object>();

		for (int i = 0; i < fields.length; i++)
		{

			Field aField = fields[i];
			Dict config = aField.getAnnotation(Dict.class);

			String fieldName = aField.getName();
			String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
			try
			{
				Object value = MethodUtils.invokeMethod(obj, methodName);
				row.put(fieldName, value);

				if (config != null && !StringUtil.isNullOrBlank(config.dictCode()))
				{

					if (value == null)
					{
						row.put(fieldName + "Zh", "");
						continue;
					}

					row.put(fieldName + "Zh", cachedValue(config.dictCode(), value.toString()));
				}

			}
			catch (Exception e)
			{
			}
		}
		return row;
	}
	
	public String cachedValue(String key, String value)
	{

		String cachedKey = String.format("dictCode=[%s]dictValue=[%s]", key, value);

		// String cachedValue = memcachedService.get(cachedKey);
		String cachedValue = RedisServiceUtil.getJedis(RedisServiceUtil.SERVICE_REDIS_COMMON,cachedKey);

		if (null != cachedValue)
		{
			return cachedValue;
		}

		Criteria<SysDict> param = new Criteria<SysDict>();
		param.initParams().addParam("dictCode", key);
		param.addParam("dictValue", value);
		param.setPageSize(1);
		param.setStartIndex(0);

		List<SysDict> tmp = sysDictRepository.queryPage(param);
		if (tmp != null && !tmp.isEmpty())
		{
			cachedValue = tmp.get(0).getDictRemark() != null ? tmp.get(0).getDictRemark() : "";
		}
		else
		{
			cachedValue = "";
		}

		//如果字典翻译值为空则不存入redis
		if(!StringUtil.isNullOrBlank(cachedValue)){
			// memcachedService.add(cachedKey, cachedValue);
			RedisServiceUtil.setJedis(RedisServiceUtil.SERVICE_REDIS_COMMON,cachedKey, cachedValue);
		}
		// System.out.println("cachedKey -------------" + cachedKey +
		// "     cachedValue ----------------------" + cachedValue);
		return cachedValue;
	}

	public void setSysDictRepository(ISysDictRepository repository) {
		this.sysDictRepository = repository;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	private ISysDictRepository sysDictRepository;

	private IMessageService messageService;

	private ISequenceService sequenceService;
}
