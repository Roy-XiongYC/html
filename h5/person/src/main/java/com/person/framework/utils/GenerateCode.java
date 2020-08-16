package com.person.framework.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class GenerateCode {   

	//定义一个静态Map来保存已有的数据，以防冲突
	static Map<String,List<Integer>> map=new HashMap<String,List<Integer>>();
	
	/**
	 * 随机生成20位用户ID
	 * @return
	 */
	public static String GenerateId()
	{		
		//按“年月日时分秒毫秒”来生成生符串
		java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyMMddHHmmssSSS");
	    String dateStr = format2.format(new Date());
	    int num1= (int)((Math.random()*9+1)*10000); //生成5位随机数
	  
	    List<Integer> rand_list=new ArrayList<Integer>();
	    
	    //先查找同一毫秒内有没有重复的键,如果有，则检查随机数是不是相同
	    if (map.containsKey(dateStr))
	    {
	    	rand_list=map.get(dateStr);
	    	//随机数只保存5000位，保存超多，越影响程序性能，保存太少，产生重复的机率增加
		    if (rand_list.size()>5000)
		    {
		    	rand_list.remove(0);
		    }
		    //while循环直到生成出不同的随机数
			while(rand_list.contains(num1))
			 {
			    	num1= (int)((Math.random()*9+1)*10000);
			  }	
			rand_list.add(num1);
			map.put(dateStr, rand_list);
	    }
	    else
	    {
	    	map.clear();
	    	rand_list.add(num1);
	    	map.put(dateStr, rand_list);
	    }
		return dateStr+num1;
	}

	/**
	 * 生成6位短信验证码
	 * @return
	 */
    public static String getSMSCode()
    {
		int[] array = {0,1,2,3,4,5,6,7,8,9};
		Random rand = new Random();
		String result="";
		for (int i = 10; i > 1; i--) 
		{		 	   
		    result += array[rand.nextInt(10)];
		}
    	return result.substring(0,6); //生成6位随机数
    }
    
    /**
     * 生成指定长度的随机数
     * @param length
     * @return
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度  
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
        Random random = new Random();     
        StringBuffer sb = new StringBuffer();     
        for (int i = 0; i < length; i++) {     
            int number = random.nextInt(base.length());     
            sb.append(base.charAt(number));     
        }     
        return sb.toString();     
     }    
}
