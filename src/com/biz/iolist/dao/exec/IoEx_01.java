package com.biz.iolist.dao.exec;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.session.SqlSession;

import com.biz.iolist.dao.IolistDao;
import com.biz.iolist.dao.config.DBConnection;
import com.biz.iolist.model.IolistVO;

import oracle.net.aso.i;

public class IoEx_01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		SqlSession sqlSession = DBConnection.
								getSqlSessionFactory().
								openSession(true);
		IolistDao ioDao = (IolistDao) sqlSession.getMapper(IolistDao.class);
		
		IolistVO vo = new IolistVO();
		
		vo.setIo_date("2019-07-02");
		vo.setIo_inout("1");
		vo.setIo_pcode("P0001");
		vo.setIo_ccode("C0001");
		vo.setIo_price(500);
		vo.setIo_qty(100);
		vo.setIo_total(vo.getIo_price() * vo.getIo_qty());
		
		
		ioDao.insert(vo);
		
		
	}

}
