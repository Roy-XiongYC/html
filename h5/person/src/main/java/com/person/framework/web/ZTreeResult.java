package com.person.framework.web;

import java.io.Serializable;

public class ZTreeResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 子id
	 */
	private String id;
	/**
	 * 父id
	 */
	private String pId;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 是否选中
	 */
 	private boolean checked = true;
 	/**
	 * 是否打开
	 */
 	private boolean open = true;
 	/**
 	 * 是否允许拖拽
 	 */
 	private boolean drag = true;
 	/**
 	 * 是否允许子节点拖拽
 	 */
 	private boolean childOuter = true;
 	/**
 	 * 是否允许子节点排序
 	 */
 	private boolean childOrder = true;
 	/**
 	 * 是否可以成为父节点
 	 */
 	private boolean dropInner = true;
 	/**
 	 * 是否可以成为根节点
 	 */
 	private boolean dropRoot = true;

	public boolean isDrag() {
		return drag;
	}

	public void setDrag(boolean drag) {
		this.drag = drag;
	}

	public boolean isChildOuter() {
		return childOuter;
	}

	public void setChildOuter(boolean childOuter) {
		this.childOuter = childOuter;
	}

	public boolean isChildOrder() {
		return childOrder;
	}

	public void setChildOrder(boolean childOrder) {
		this.childOrder = childOrder;
	}

	public boolean isDropInner() {
		return dropInner;
	}

	public void setDropInner(boolean dropInner) {
		this.dropInner = dropInner;
	}

	public boolean isDropRoot() {
		return dropRoot;
	}

	public void setDropRoot(boolean dropRoot) {
		this.dropRoot = dropRoot;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
 	
}
