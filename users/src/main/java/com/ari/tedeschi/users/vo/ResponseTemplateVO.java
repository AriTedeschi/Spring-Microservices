package com.ari.tedeschi.users.vo;

import com.ari.tedeschi.users.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplateVO {
	private User user;
	private Department department;
	
	public static ResponseTemplateVO from(User user, Department department) {
		return new ResponseTemplateVO(user, department);
	}
}
