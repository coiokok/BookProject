package com.qq.bean;

// friend���ݱ��ʵ����
// ֻ��д�� get��set��������

import java.io.Serializable;

public class Friend implements Serializable {        // ��Ϊ��Ҫ���紫�������Ϣ������Ҫʵ�����л�������Ҫ�̳�Serializable�ӿڣ���ֻ��һ����ʶ�ӿڣ����޷�����Ҫ��д

    private String userAccount;
 
    private String friendAccount;
    
    
	public String getFriendAccount() {
		return friendAccount;
	}
	public void setFriendAccount(String friendAccount) {
		this.friendAccount = friendAccount;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
}