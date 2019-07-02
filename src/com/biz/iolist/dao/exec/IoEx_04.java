package com.biz.iolist.dao.exec;

import com.biz.iolist.dao.service.IolistService;

public class IoEx_04 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IolistService ioService = new IolistService();
		
		while(true) {
			
			ioService.viewIolist();
			if(ioService.insertIO()) System.out.println("데이터추가성공");
			else System.out.println("데이터추가실패");
		}
	}

}
