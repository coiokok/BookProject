package com.jinzhi.bean;

// 数据库中的book表的实体类

public class Book {
	
	private int id;
	private String name;
	private float price;
	private String author;
	private String context;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	
	public Book(int id, String name, float price, String author, String context) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.author = author;
		this.context = context;
	}

	public Book() {
		super();
	}

	public Book(String name, float price, String author, String context) {
		super();
		this.name = name;
		this.price = price;
		this.author = author;
		this.context = context;
	}

	public Book(String name, float price, String author) {
		super();
		this.name = name;
		this.price = price;
		this.author = author;
	}

}