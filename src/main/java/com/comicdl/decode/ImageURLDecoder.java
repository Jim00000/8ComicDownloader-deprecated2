package com.comicdl.decode;

import org.apache.commons.lang3.StringUtils;

import com.comicdl.parser.IDecodable;

public class ImageURLDecoder implements IDecodable {

	private String ch,chs, ti, cs;
	private final int f = 50;
	private int p;

	public ImageURLDecoder(String ch,String chs, String ti, String cs,int p) {
		this.ch = ch;
		this.chs = chs;
		this.ti = ti;
		this.cs = cs;
		this.p = p;
	}

	@Override
	public String decode() {
		return sp();
	}

	private String nn(Integer n) {
		return n < 10 ? "00" + n : n < 100 ? "0" + n : n.toString();
	}

	private Integer mm(Integer p) {
		return ((int)((p-1)/10)%10)+(((p-1)%10)*3);
	}

	private String ss(String a, Integer b, Integer c, Integer d) {
		String e = a.substring(b, b + c);
		return (d == null) ? e.replaceAll("[a-z]*", "") : e;
	}

	private String si(String c) {
		return ("http://img" + ss(c, 4, 2, null) + ".6comic.com:99/" + ss(c, 6, 1, null) + '/' + ti + '/'
				+ ss(c, 0, 4, null) + '/' + nn(p) + '_' + ss(c, mm(p) + 10, 3, f) + ".jpg");
	}

	private String sp() {
		int cc = cs.length();
		String c = "";
		for (int i = 0; i < cc / f; i++) {
			if (StringUtils.equals(ss(cs, i * f, 4, null), ch)) {
				c = ss(cs, i * f, f, f);
				break;
			}
		}

		if (StringUtils.equals(c, "")) {
			c = ss(cs, cc - f, f, null);
			ch=chs;
		}

		return si(c);
	}

}
