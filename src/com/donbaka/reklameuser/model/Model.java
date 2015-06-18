package com.donbaka.reklameuser.model;

public abstract class Model {
	protected long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	abstract public String toString();
	
}
