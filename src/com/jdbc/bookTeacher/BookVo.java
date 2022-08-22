package com.jdbc.bookTeacher;

public class BookVo {
	private long book_id;
	private String title;
	private String pubs;
	private String pub_date;
	private String author_name;

	/**
	 * @return the book_id
	 */
	public long getBook_id() {
		return book_id;
	}

	/**
	 * @param book_id the book_id to set
	 */
	public void setBook_id(long book_id) {
		this.book_id = book_id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the pubs
	 */
	public String getPubs() {
		return pubs;
	}

	/**
	 * @param pubs the pubs to set
	 */
	public void setPubs(String pubs) {
		this.pubs = pubs;
	}

	/**
	 * @return the pub_date
	 */
	public String getPub_date() {
		return pub_date;
	}

	/**
	 * @param pub_date the pub_date to set
	 */
	public void setPub_date(String pub_date) {
		this.pub_date = pub_date;
	}

	/**
	 * @return the author_name
	 */
	public String getAuthor_name() {
		return author_name;
	}

	/**
	 * @param author_name the author_name to set
	 */
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	/**
	 * @param book_id
	 * @param title
	 * @param pubs
	 * @param pub_date
	 * @param author_name
	 */
	public BookVo(long book_id, String title, String pubs, String pub_date, String author_name) {
		this.book_id = book_id;
		this.title = title;
		this.pubs = pubs;
		this.pub_date = pub_date;
		this.author_name = author_name;
	}

	@Override
	public String toString() {
		return "BookVo [book_id=" + book_id + ", title=" + title + ", pubs=" + pubs + ", pub_date=" + pub_date
				+ ", author_name=" + author_name + "]";
	}

}