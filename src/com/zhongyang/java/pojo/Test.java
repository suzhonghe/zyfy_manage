package com.zhongyang.java.pojo;

public class Test {
	private int id;
	private String name;
	private int age;
	private String email;
	private int telephone;
	
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getTelephone() {
		return telephone;
	}
	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}
	public Test(int id, String name, int age, String email, int telephone) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.email = email;
		this.telephone = telephone;
	}
	public Test() {
	}
	@Override
	public String toString() {
		return "Test [id=" + id + ", name=" + name + ", age=" + age + ", email=" + email + ", telephone=" + telephone
				+ "]";
	}
	
	
	
	
}
