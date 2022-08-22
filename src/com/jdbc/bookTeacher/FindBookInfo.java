package com.jdbc.bookTeacher;

import java.util.List;
import java.util.Scanner;

public class FindBookInfo {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    BookDao dao = new BookDaoImpl();
//    List<BookVo> list = dao.getList();
//    
//    for(BookVo vo : list) {
//      System.out.println(vo);
//    }
    
//    List<BookVo> list2 = dao.getSearchList("문");
//    
//    for(BookVo vo : list2) {
//      System.out.println(vo);
//    }
    
    System.out.print("책정보 검색을 위한 검색어를 입력하세요 >>");
    String keyWord = sc.nextLine();
    List<BookVo> list3 = dao.getSearchList(keyWord);
    
    for(BookVo vo : list3) {
      System.out.println(vo);
    }
  }

}
