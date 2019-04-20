//package com.busbooking.validator;
//
//import java.text.Normalizer;
//import java.util.Collection;
//import java.util.regex.Pattern;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//
//public class WebUtils {
//
//	public static String toString(User user) {
//		StringBuilder sb = new StringBuilder();
//
//		sb.append("Username:").append(user.getUsername());
//
//		Collection<GrantedAuthority> authorities = user.getAuthorities();
//		if (authorities != null && !authorities.isEmpty()) {
//			sb.append(" (");
//			boolean first = true;
//			for (GrantedAuthority a : authorities) {
//				if (first) {
//					sb.append(a.getAuthority());
//					first = false;
//				} else {
//					sb.append(", ").append(a.getAuthority());
//				}
//			}
//			sb.append(")");
//		}
//		return sb.toString();
//	}
//
//	public static String removeAccent(String s) {
//		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
//		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
//		return pattern.matcher(temp).replaceAll("");
//	}
//}