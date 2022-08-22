package com.jdbc.book;

import java.util.ArrayList;
import java.util.List;

public class AuthorTest {

  public static void main(String[] args) {
    List<AuthorVo> list = new ArrayList<AuthorVo>();
    AuthorDao dao = new AuthorDao();
    list = dao.getList();
    
    for(AuthorVo vo : list) {
      //System.out.println("Author id :" + vo.getAuthor_id());
      System.out.println( vo.toString() );
    }
  }

}
