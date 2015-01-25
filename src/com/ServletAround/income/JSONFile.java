package com.ServletAround.income;

import java.util.Set;

public class JSONFile {
private String login, pass, email, status, photo, friend, x, y;
private int activity;

private boolean valid;


public JSONFile(String login, String pass){
	this.login = login;
	this.pass = pass;
}

public void setEmail(String email) {
	this.email = email;
}

public String getEmail(){
	return email;	
}

	public String getLogin(){
		return login;
	}
	
public String getPass(){
		return pass;
	}

public String getX(){
	return x;
}

public String getY(){
	return y;
}

public String getStatus(){
	return status;
}

public int getActivity(){
	return activity;
}

public String getPhoto(){
	return photo;
}
public String getFriend(){
	return friend;
}

public boolean isValid(){
	return valid;
}

public void setLogin(String login){
	this.login=login;
}

public void setPass(String pass){
	this.pass=pass;
}

public void setX(String x){
this.x=x;
}

public void setY(String y){
this.y=y;
}

public void setStatus(String status){
this.status=status;
}

public void setActivity(int activity){
this.activity=activity;
}

public void setPhoto(String photo){
this.photo=photo;
}
public void setFriend(String friend){
	this.friend = friend;
}
public void setValid(boolean valid){
	this.valid=valid;
}
}
