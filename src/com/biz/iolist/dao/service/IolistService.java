package com.biz.iolist.dao.service;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;

import com.biz.iolist.dao.IolistDao;
import com.biz.iolist.dao.config.DBConnection;
import com.biz.iolist.model.IolistVO;

public class IolistService {

	
	SqlSession sqlSession = null;
	IolistDao ioDao = null;
	Scanner scan = null;
	
	public IolistService() {
		sqlSession = DBConnection.getSqlSessionFactory().openSession(true);
		ioDao = (IolistDao) sqlSession.getMapper(IolistDao.class);
		scan = new Scanner(System.in);
		
	}
	public void viewIolist() {
		System.out.println("==================================================================");
		System.out.println("우리동네 슈퍼마트 - 매입매출 리스트");
		System.out.println("------------------------------------------------------------------");
		System.out.println("거래일자\t거래처\t시퀀스\t거래구분\t상품코드\t단가\t수량\t금액");
		System.out.println("------------------------------------------------------------------");
		
		List<IolistVO> ioList = ioDao.selectAll();
		for(IolistVO vo : ioList) {
			System.out.print(vo.getIo_date() + "\t");
			System.out.print(vo.getIo_ccode() + "\t");
			
			System.out.print(vo.getIo_seq() + "\t");
			
			String inout = vo.getIo_inout();
			if(Integer.valueOf(inout) ==1) System.out.print("매입\t\t");
			else System.out.print("매출\t\t");
			System.out.print(vo.getIo_pcode() + "\t\t");
			System.out.printf("%3d\t",vo.getIo_price());
			System.out.printf("%3d\t",vo.getIo_qty());
			//숫자표현 시 3자리마다 컴마구분기호 넣기
			int intTotal = vo.getIo_total();
			DecimalFormat df = new DecimalFormat("#,###");
			
			String strTotal = df.format(intTotal);		
			System.out.printf("%s\t",strTotal);
			
			
			System.out.printf("%,d\n",intTotal);
		}
		System.out.println("-----------------------------------------------------------------");
	}
	
	public boolean insertIO() {
		System.out.println("===============================================");
		System.out.println("매입매출 등록");
		System.out.println("------------------------------------------------");
		
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		
		String strDate = sd.format(date); // 2019-07-02 형식으로 보냄
		System.out.println("거래일자 : " + strDate);
		System.out.print("거래처코드(C0001) >>" );
		String strCcode = scan.nextLine();
		System.out.print("거래방법(1. 매입, 2. 매출 >>");
		String strInout = scan.nextLine();
		
		System.out.print("상품코드(P0001) >>");
		String strPcode = scan.nextLine();
		int intPrice =0;
		int intQty =0;
		
		while(true) {
			System.out.print("상품단가 >>");
			String strPrice = scan.nextLine();
			try {
				intPrice = Integer.valueOf(strPrice);
			} catch (NumberFormatException e) {
				System.out.println("상품단가는 숫자로만 입력하세요");
				continue;
			}
				break;
				
		}
		while(true) {
			System.out.print("수량 >>");
			String strQty = scan.nextLine();
			try {
				intQty = Integer.valueOf(strQty);
			} catch (NumberFormatException e) {
				System.out.println("거래수량은 숫자로만 입력하세요");
				continue;
			}
				break;
				
		}
		int intTotal = intPrice * intQty;
		
		
		IolistVO vo = new IolistVO(0, strDate, strPcode, strCcode, strInout, intQty, intPrice, intTotal);
		System.out.println(vo);
		
		if(ioDao.insert(vo) > 0) return true;
		else return false;
	}
	
	public boolean updateIO() {

		System.out.println("===============================================");
		System.out.println("거래정보 수정");
		System.out.println("------------------------------------------------");
		System.out.println("수정할거래 no >>");
		
		String strId = scan.nextLine();
		
		long io_seq = Long.valueOf(strId);
		IolistVO vo = ioDao.findById(io_seq);
		if(vo==null) {
			System.out.println("거래정보가 없습니다.");
			return true;
		}
		
		/*
		 * 거래처 코드를 보여주고
		 * 거래처 코드를 변경하려면 코드를 입력 후 엔터
		 * 변경하지 않으려면 그냥 엔터
		 * 코드를 입력하지않고 엔터를 누르면
		 * strCcode에는 아무 값도 없다.
		 * 이때 strcode.isEmpty()는 트루를 리턴한다.
		 * 
		 * 따라서 코드를 입력 후 엔터를 누르면
		 * strCcode.isEmpty를 스킵하여 입력한 코드가 strCcode에 담겨있고
		 * 아무것도 입력하지않고 엔터를 누르면
		 * strCcode.isEmpty가 트루가되어 vo에 담겨있는 ioCcode값을 넣어준다.
		 */
		System.out.printf("거래처 (%s) >> ",vo.getIo_ccode());
		String strCcode = scan.nextLine();
		if(strCcode.isEmpty()) strCcode = vo.getIo_ccode();
		
		System.out.printf("거래구분 (%s) >> ",vo.getIo_inout());
		String strInout = scan.nextLine();
		if(strInout.isEmpty()) strInout = vo.getIo_inout();
		
		System.out.printf("상품코드 (%s) >> ",vo.getIo_pcode());
		String strPcode = scan.nextLine();
		if(strPcode.isEmpty()) strPcode = vo.getIo_pcode();

		System.out.printf("단가 (%d) >> ",vo.getIo_price());
		String strPrice = scan.nextLine();
		int intPrice =0;
		if(strInout.isEmpty()) intPrice = vo.getIo_price();
		else intPrice = Integer.valueOf(strPrice);

		System.out.printf("수량 (%d) >> ",vo.getIo_qty());
		String strQty = scan.nextLine();
		int intQty =0;
		if(strQty.isEmpty()) intQty = vo.getIo_qty();
		else intQty = Integer.valueOf(strQty);
		
		int intTotal = intPrice * intQty;
		
		vo.setIo_ccode(strCcode);
		vo.setIo_inout(strInout);
		vo.setIo_pcode(strPcode);
		vo.setIo_price(intPrice);
		vo.setIo_qty(intQty);
		vo.setIo_total(intTotal);
		
		if(ioDao.update(vo) > 0) return true;
		else return false;
		
	}
	
	public boolean deleteIO() {
		System.out.println("삭제할 거래내역 >>");
		String strId = scan.nextLine();
		long io_seq = Long.valueOf(strId);
		
		IolistVO vo = ioDao.findById(io_seq);
		System.out.println(vo);
		System.out.print("정말 삭제할까요 ? (YES)");
		String confirm = scan.nextLine();
		if(confirm.equals("YES")) {
			if(ioDao.delete(io_seq)>0) return true;
			else return false;
		}
		return false;
		
	}
}
