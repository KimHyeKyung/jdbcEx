package com.jdbc.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthorTestApp {

	public static void main(String[] args) {

		List<AuthorVo> list = new ArrayList<AuthorVo>();
		AuthorDao dao = new AuthorDao();
		
		int sc_author_id;
		String sc_author_name = "";
		String sc_author_desc = "";
		Boolean run = true;
		Scanner sc = new Scanner(System.in);
		System.out.print(">>원하는 메뉴 번호를 입력하세요: ");
		
		while (run) {
			System.out.println();
			System.out.println("------------------------------------------------------");
			System.out.println(" 1.SELECT | 2.INSERT | 3.UPDATE | 4.DELETE | 5.종료");
			System.out.println("------------------------------------------------------");
			System.out.print("선택> ");
			int menuNo = sc.nextInt();
			switch (menuNo) {
			case 1:
				// select
				list = dao.getList();
				for (AuthorVo vo : list) {
					System.out.println(vo.toString());
				}
				break;
			case 2:
				// insert
				AuthorVo voInsert = new AuthorVo();

				System.out.print("작가 번호 >> ");
				sc_author_id = sc.nextInt();
				System.out.print("작가이름, 작가설명 >> ");
				sc.nextLine(); //버퍼에 찌꺼기가 남아있기 때문에 강제로 한번 더 써준다.(따로 받지는 말자)
				String insertInfo = sc.nextLine();

				String[] s = insertInfo.split(" ");
				sc_author_name = s[0];
				sc_author_desc = s[1];
				
				voInsert.setAuthor_id(sc_author_id);
				voInsert.setAuthor_name(sc_author_name);
				voInsert.setAuthor_desc(sc_author_desc);
				
				dao.insert(voInsert);
				break;
			case 3:
				//update
				AuthorVo voUpdate = new AuthorVo();
				System.out.print("작가 번호 >> ");
				sc_author_id = sc.nextInt();
				System.out.print("작가이름, 작가설명 >> ");
				sc.nextLine(); //버퍼에 찌꺼기가 남아있기 때문에 강제로 한번 더 써준다.(따로 받지는 말자)
				String updateInfo = sc.nextLine();
				
				String[] u = updateInfo.split(" ");
				sc_author_name = u[0];
				sc_author_desc = u[1];
				
				voUpdate.setAuthor_name(sc_author_name);
				voUpdate.setAuthor_desc(sc_author_desc);
				voUpdate.setAuthor_id(sc_author_id);
				
				dao.update(voUpdate);
				break;
			case 4:
				System.out.print("삭제할 작가 번호 >> ");
				sc_author_id = sc.nextInt();
				dao.delete((long) sc_author_id);
				break;
			case 5:
				System.out.println("종료");
				sc.close();
				run = false;
				break;
			default:
				System.out.println("다시 입력해주세요.");
				break;
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		



//		// update

//
//
//		// delete
//		dao.delete((long) 7);
		
	}

}
