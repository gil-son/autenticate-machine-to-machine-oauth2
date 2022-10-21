package com.matrizero.dto;

import com.matrizero.entities.Role;

import java.io.Serializable;

public class RoleDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	

	private Long id;
	private String authority;
	
	public RoleDTO() {}
	
	public RoleDTO(Long id, String authority) {
		this.id = id;
		this.authority = authority;
	}
	
	public RoleDTO(Role entity) { // the FetchType.EAGER in the class User get the Role. Is used in UserDTO
		id = entity.getId();
		authority = entity.getAuthority();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
