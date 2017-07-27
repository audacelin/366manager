package com.sm.activity;

import java.util.HashMap;

public abstract class Action {
	public abstract void doIt();

	public static class Mapper extends HashMap<Integer, Action> {

		public Mapper add(int rid, Action action) {
			this.put(rid, action);
			return this;
		}

	}

	public static Mapper init() {
		return new Mapper();
	}

}
