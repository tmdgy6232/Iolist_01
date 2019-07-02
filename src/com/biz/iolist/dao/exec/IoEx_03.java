package com.biz.iolist.dao.exec;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.biz.iolist.dao.IolistDao;
import com.biz.iolist.dao.config.DBConnection;
import com.biz.iolist.dao.service.IolistService;
import com.biz.iolist.model.IolistVO;

public class IoEx_03 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		IolistService ioService = new IolistService();
		
		ioService.viewIolist();
				
		
	
		
	}

}
