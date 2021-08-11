package com.qq.pub;

// �����Ǳ����࣬����һ�ִ��ݹ��ߵ�����ŵĶ�����һ��������ݱ���ͷ�Ĳ�ͬ���ȥ�ò�ͬ�ķ���ȥ������
// ͷ��Ҫʹ�õķ��������Ǳ�ʹ�õĶ���
// implements Serializable ��ֻ��һ����ʶ�ӿڴ�������Ǳ����������޷�����Ҫʵ��

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// implements Serializable�ӿ� �ʹ�������һ����������
public class TcpMessage implements Serializable {
	private String head = null;             // ����ͷ
	private Map<String, Object> map = new HashMap<String, Object>();            // �����壬����Ҫ��key���ж���
	
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	
	public void setBody(String key, Object value) {
		this.map.put(key, value);
	}
	public Object getBody(String key) {
		return this.map.get(key);
	}
}