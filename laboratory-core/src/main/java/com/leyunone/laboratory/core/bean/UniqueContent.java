package com.leyunone.laboratory.core.bean;

public 	class UniqueContent{
	
	private String content;
	
	private String uuid;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UniqueContent that = (UniqueContent) o;

		if (content != null ? !content.equals(that.content) : that.content != null) return false;
		return uuid != null ? uuid.equals(that.uuid) : that.uuid == null;
	}

	@Override
	public int hashCode() {
		return uuid.hashCode() * content.hashCode();
	}
}
