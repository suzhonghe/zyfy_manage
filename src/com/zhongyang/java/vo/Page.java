package com.zhongyang.java.vo;

public class Page {
		private int start;
		private int length;
		public int getStart() {
			return start;
		}
		public void setStart(int start) {
			this.start = start;
		}
		public int getLength() {
			return length;
		}
		public void setLength(int length) {
			this.length = length;
		}
		public Page(int start, int length) {
			this.start = start;
			this.length = length;
		}
		public Page() {
		}
		@Override
		public String toString() {
			return "Page [start=" + start + ", length=" + length + "]";
		}
		
		
}
