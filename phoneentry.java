package com.example.demo.entity;

public class phoneentry {

        String firstname;
		String midint;
		String lastname;
        String phonum;
        String address;
		String email;
		String id;

		public phoneentry(String firstname, String midint, String lastname, String phonum, String address, String email, String id) {
			this.firstname = firstname;
			this.midint = midint;
			this.lastname = lastname;
			this.phonum = phonum;
			this.address = address;
			this.email = email;
			this.id = id;
		}

    public String getfirstname() {
		return firstname;
	}
	public void setfirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getmidint() {
		return midint;
	}
	public void setmidint(String midint) {
		this.midint = midint;
	}
	public String getlastname() {
		return lastname;
	}
	public void setlastname(String lastname) {
		this.lastname = lastname;
	}
    public String getphonenumber() {
		return phonum;
	}
	public void setphonenumber(String phonum) {
		this.phonum = phonum;
	}
    public String getaddress() {
		return address;
	}
	public void setaddress(String address) {
		this.address = address;
	}
	public String getemail() {
		return email;
	}
	public void setemail(String email) {
		this.email = email;
	}
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
	}
}
