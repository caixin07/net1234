package com.toutiao.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class IkAnalyzer {
	public static void main(String[] args) {
		String text = "<p>近期，国家食品药品监督管理总局通报“3批次食品不合格”，<a class='f14_link' href='http://news.mydrivers.com/1/544/544522.htm' target='_blank'>天猫商城三只松鼠中枪。</a></p><p>具体来说，本次组织抽检了饼干、炒货食品及坚果制品、糕点、豆制品、蜂产品、食糖和糖果制品等7类食品449批次样品，抽样检验项目合格样品446批次，不合格样品3批次。</p><p align='center'><a href='http://img1.mydrivers.com/img/20170816/07a3765772354199a488a355b414d541.png' target='_blank'><img alt='三只松鼠回应霉菌超标1.8倍：已全部销毁' src='../img/20170818/s_07a3765772354199a488a355b414d541.png' style='border-top: black 1px solid; border-right: black 1px solid; border-bottom: black 1px solid; border-left: black 1px solid'></a></p><p>其中，天猫超市在天猫（网站）商城销售的标称三只松鼠股份有限公司生产的开心果，<strong>霉菌检出值为70 CFU/g，比国家标准规定（不超过25 CFU/g）高出1.8倍。检验机构为上海市食品药品检验所。</strong></p><p>对于这个问题，今天三只松鼠通过官网以及官方微博做出了回应，表示已经对出现问题的产品进行召回并做销毁处理。</p><p>此外，三只松鼠还表示，这批产品<strong>极可能是在产品出厂后因存储、运输条件下控制不当引起霉菌滋生，导致流通环节抽取样品不合格。</strong></p><p>截至6月份，该批次产品原因查明、内部整改等各项工作已在芜湖市食品药品监督管理局的监督下全部落实。</p><p align='center'><img alt='三只松鼠回应霉菌超标1.8倍：已全部销毁' src='../img/20170818/21505104094a4e26957c7d21a84f03ee.png' style='border-top: black 1px solid; border-right: black 1px solid; border-bottom: black 1px solid; border-left: black 1px solid'></p>";
		String title = "三只松鼠回应霉菌超标1.8倍：已全部销毁";
		System.out.println(getGjc(text, title));
	}

	public synchronized static String getGjc(String text, String title) {
		text = HTMLSpirit.delHTMLTag(text);
		String afterFcStr = ikAnalyzer(text); // 分词后的字符串
		System.out.println(afterFcStr);

		// 计算词频
		Map<String, Integer> words = new HashMap<String, Integer>();
		IKSegmenter seg = new IKSegmenter(new StringReader(afterFcStr), true);
		try {
			Lexeme l = null;
			while ((l = seg.next()) != null) {
				if (isChineseChar(l.getLexemeText())) {
					if (words.containsKey(l.getLexemeText()))
						words.put(l.getLexemeText(), words.get(l.getLexemeText()) + 1);
					else
						words.put(l.getLexemeText(), 1);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		String result = sortSegmentResult(words, 4);

		String titleFc = ikAnalyzer(title);
		System.out.println(titleFc);
		if (titleFc != null && !"".equals(titleFc)) {
			System.out.println(titleFc);
			String[] by = titleFc.split("\\|");
			for (String string : by) {
				if (result.indexOf(string) < 0) {
					result = string + "," + result;
				}
			}
		}

		return result;
	}

	public static boolean isChineseChar(String str) {
		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find() && str.length() >= 2) {
			temp = true;
		}
		return temp;
	}

	/**
	 * 字符在字符串中出现的次数
	 * 
	 * @param string
	 * @param a
	 * @return
	 */
	public static int occurTimes(String string, String a) {
		int pos = -2;
		int n = 0;

		while (pos != -1) {
			if (pos == -2) {
				pos = -1;
			}
			pos = string.indexOf(a, pos + 1);
			if (pos != -1) {
				n++;
			}
		}
		return n;
	}

	public synchronized static String ikAnalyzer(String str) {

		Reader input = new StringReader(str);
		// 智能分词关闭（对分词的精度影响很大）
		IKSegmenter iks = new IKSegmenter(input, true);
		Lexeme lexeme = null;
		StringBuilder sb = new StringBuilder();

		try {
			while ((lexeme = iks.next()) != null) {
				if (lexeme.getLexemeText().length() >= 2) {
					sb.append(lexeme.getLexemeText()).append("|");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	public static String sortSegmentResult(Map<String, Integer> wordsFrenMaps, int topWordsCount) {
		Iterator<Map.Entry<String, Integer>> wordsFrenMapsIterator = wordsFrenMaps.entrySet().iterator();
		while (wordsFrenMapsIterator.hasNext()) {
			Map.Entry<String, Integer> wordsFrenEntry = wordsFrenMapsIterator.next();
		}

		List<Map.Entry<String, Integer>> wordFrenList = new ArrayList<Map.Entry<String, Integer>>(
				wordsFrenMaps.entrySet());
		Collections.sort(wordFrenList, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2) {
				return obj2.getValue() - obj1.getValue();
			}
		});

		String keys = "";
		for (int i = 0; i < topWordsCount && i < wordFrenList.size(); i++) {
			Map.Entry<String, Integer> wordFrenEntry = wordFrenList.get(i);
			if (wordFrenEntry.getValue() > 1) {
				keys = keys + "," + wordFrenEntry.getKey();
			}
		}
		if (keys.startsWith(",")) {
			keys = keys.replaceFirst(",", "");
		}
		return keys;
	}
}