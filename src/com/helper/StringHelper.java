package com.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {
	public static int n2i(String d) {
		int i = 0;
		if (d != null) {
			String dual = d.toString().trim();
			try {
				i = new Integer(dual).intValue();
			} catch (Exception e) {
				System.out.println("Unable to find integer value");
			}
		}
		return i;
	}


	public static boolean n2b(String d) {
		boolean i = false;
		if (d != null) {
			String dual = d.toString().trim();
			try {
				i = new Boolean(dual).booleanValue();
			} catch (Exception e) {
				System.out.println("Unable to find integer value");
			}
		}
		return i;
	}


	public static float n2f(Object d) {
		float i = 0;
		if (d != null) {
			String dual = d.toString().trim();
			try {
				i = new Float(dual).floatValue();
			} catch (Exception e) {
				System.out.println("Unable to find integer value");
			}
		}
		return i;
	}

	public static String n2s(String d) {
		String dual = "";
		if (d == null) {
			dual = "";
		} else
			dual = d.toString().trim();
		return dual;
	}
	public static String n2s(Object d) {
		String dual = "";
		if (d == null) {
			dual = "";
		} else {
			dual = d.toString().trim();
			if (dual.equalsIgnoreCase("null")) {
				dual = "";
			}

		}
		return dual;
	}

	public static double n2d(Object d) {
		double i = 0;
		if (d != null) {
			String dual = d.toString().trim();
			try {
				i = new Double(dual).doubleValue();
			} catch (Exception e) {
				System.out.println("Unable to find integer value");
			}
		}
		return i;
	}

	public static int n2i(Object d) {
		int i = 0;
		if (d != null) {
			String dual = d.toString().trim();
			try {
				i = new Integer(dual).intValue();
			} catch (Exception e) {

				System.out.println("Unable to find integer value For " + d
						+ " ");
			}
		}
		return i;
	}
	public static String removeWhiteSpace(String dlatlng) {
		if (dlatlng == null) {
			return "";
		}
		dlatlng = dlatlng.trim();
		dlatlng = dlatlng.replaceAll("\t", "");
		dlatlng = dlatlng.replaceAll("\n", "");
		dlatlng = dlatlng.replaceAll("\r", "");
		return dlatlng;
	}

public static boolean isDouble(String checkStr) {
		try {
			Double.parseDouble(checkStr);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}

	public static String leftPad(String s, int length) {
		if (s != null && s.length() >= length)
			return s;
		if (s == null)
			s = "";
		int make_up = length - s.length();
		for (int i = 0; i < make_up; i++)
			s = "0" + s;
		return s;
	}

	public static String rightPad(String s, int length) {
		if (s != null && s.length() >= length)
			return s;
		if (s == null)
			s = "";
		int make_up = length - s.length();
		for (int i = 0; i < make_up; i++)
			s = s + "0";
		return s;
	}



	public static HashMap sortByValues(HashMap map) {
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {

			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue())
						.compareTo(((Map.Entry) (o1)).getValue());
			}
		});
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}

	public static String getIpAddress(String ipString) {
		String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		Matcher matcher = pattern.matcher(ipString);
		if (matcher.find()) {
			return matcher.group();
		} else {
			return "0.0.0.0";
		}
	}

	public static int countOccurrences(String haystack, char needle) {
		int count = 0;
		for (int i = 0; i < haystack.length(); i++) {
			if (haystack.charAt(i) == needle) {
				count++;
			}
		}
		return count;
	}



	public static double round(double d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Double.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}




	public static ArrayList n2List(Object d) {
		ArrayList dual = null;
		if (d == null) {
			dual = new ArrayList();
		} else {
			dual = (ArrayList) d;
		}
		return dual;
	}



	public static boolean n2b(Object get) {
		boolean dual = false;
		if (get != null) {
			dual = new Boolean(get.toString()).booleanValue();
		}
		return dual;
	}



	public static String toSentenceCase(String s) {
		String s2 = "";
		s2 += Character.toUpperCase(s.charAt(0));
		for (int i = 1; i < s.length(); i++) {
			if (Character.isUpperCase(s.charAt(i))) {
				s2 += Character.toLowerCase(s.charAt(i));
			} else {
				s2 += s.charAt(i);
			}
		}
		return s2;
	}

	public static void print(Object array[]) {
		int i = 0;
		for (i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
	}

	public static void runCommand(String s) {
		try {
			Process p = Runtime.getRuntime().exec(s);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String args[]) {
		double s = round(12.256869,3);
		System.out.println(s);
	}



	public List n2list(Object d) {
		List dual = null;
		if (d == null) {
			dual = new ArrayList();
		} else if (d instanceof List)
			dual = (List) d;
		return dual;
	}

	public static int removeLeading0s(Object d) {
		int temp = 0;
		String dual = "";
		if (d == null) {
			dual = "";
		} else {
			dual = d.toString().trim();
			int i = 0;
			for (i = 0; i < dual.length(); i++) {
				if (dual.charAt(i) != '0') {
					break;
				}
			}
			if (i < dual.length()) {
				dual = dual.substring(i, dual.length());
				temp = Integer.parseInt(dual);
			} else
				dual = "";
		}
		return temp;
	}

	public static HashMap parseString2Map(String str) {
		HashMap hmp = new HashMap();
		StringTokenizer st1 = new StringTokenizer(str, "&");
		while (st1.hasMoreTokens()) {
			String token = st1.nextToken();
			StringTokenizer st2 = new StringTokenizer(token, "=");
			String key = st2.nextToken();
			String value = "";
			if (st2.hasMoreTokens())
				value = st2.nextToken();
			hmp.put(key, value);
		}
		return hmp;
	}

	public static ArrayList<Integer[]> generateGrid() {
		ArrayList<Integer[]> grids = new ArrayList<Integer[]>();
		for (int i = 0; i < 10; i++) {
			ArrayList<Integer> arr = new ArrayList<Integer>();
			System.out.println("Start=========================");
			for (int j = 0; j < 10; j++) {
				arr.add(j);
			}
			Collections.shuffle(arr);
			Integer[] a = new Integer[arr.size()];
			arr.toArray(a);
			grids.add(a);
			System.out.println("End=========================");
		}
		return grids;
	}


	public static String map2query(HashMap map) {
		Set s = map.keySet();
		String q = "";
		for (Iterator iterator = s.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String value = StringHelper.n2s(map.get(key));
			if (!key.equalsIgnoreCase("QUERY"))
				q += key + "=" + URLEncoder.encode(value) + "&";
		}
		map.put("QUERY", q);
		return q;
	}
	public static String CovertIdAry2String(int[] id_array) {
		if (id_array != null) {
			String inStr = "";
			for (int i = 0; i < id_array.length; i++) {
				inStr += id_array[i];
				if ((i + 1) < id_array.length)
					inStr += ", ";
			}
			return (inStr.equals("") ? null : inStr);
		}
		return null;
	}
}
