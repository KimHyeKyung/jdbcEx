package com.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookTestApp {

	public static void main(String[] args) {

		List<BookVo> list = new ArrayList<BookVo>();
		BookDao dao = new BookDao();
		
		int book_id;
		String title = "";
		String pubs = "";
		String pub_date = "";
		int author_id;
		String find="";
		
		Boolean run = true;
		Scanner sc = new Scanner(System.in);
		System.out.print(">>원하는 메뉴 번호를 입력하세요: ");
		
		while (run) {
			System.out.println();
			System.out.println("------------------------------------------------------------");
			System.out.println(" 1.SELECT | 2.INSERT | 3.UPDATE | 4.DELETE | 5.찾기 | 6.종료");
			System.out.println("------------------------------------------------------------");
			System.out.print("선택> ");
			int menuNo = sc.nextInt();
			switch (menuNo) {
			case 1:
				// select
				list = dao.getList();
				for (BookVo vo : list) {
					System.out.println(vo.toString());
				}
				break;
			case 2:
				// insert
				BookVo voInsert = new BookVo();

				System.out.print("책번호>> ");
				book_id = sc.nextInt();
				
				System.out.print("책제목, 출판사, 날짜 >> ");
				sc.nextLine(); //버퍼에 찌꺼기가 남아있기 때문에 강제로 한번 더 써준다.(따로 받지는 말자)
				String insertInfo = sc.nextLine();
				
				System.out.print("작가번호 >> ");
				author_id = sc.nextInt();
				
				String[] s = insertInfo.split(" ");
				title = s[0];
				pubs = s[1];
				pub_date = s[2];
				
				voInsert.setBook_id(book_id);
				voInsert.setTitle(title);
				voInsert.setPubs(pubs);
				voInsert.setPub_date(pub_date);
				voInsert.setAuthor_id(author_id);
				
				dao.insert(voInsert);
				break;
			case 3:
				//update
				BookVo voUpdate = new BookVo();
				System.out.print("책 번호 >> ");
				book_id = sc.nextInt();
				
				System.out.print("책 제목>> ");
				sc.nextLine(); //버퍼에 찌꺼기가 남아있기 때문에 강제로 한번 더 써준다.(따로 받지는 말자)
				title = sc.nextLine();
				
				voUpdate.setTitle(title);
				voUpdate.setBook_id(book_id);
				
				dao.update(voUpdate);
				break;
			case 4:
				System.out.print("삭제할 책 번호 >> ");
				book_id = sc.nextInt();
				dao.delete((long) book_id);
				break;
			case 5:
				System.out.print("검색할 책의 제목을 입력해주세요 >> ");
				find = sc.nextLine();
				list = dao.findList(find);
				for (BookVo vo : list) {
					System.out.println(vo.toString());
				}
				break;
			case 6:
				System.out.println("종료");
				sc.close();
				run = false;
				break;
			default:
				System.out.println("다시 입력해주세요.");
				break;
			}
		}
		
	}

}
