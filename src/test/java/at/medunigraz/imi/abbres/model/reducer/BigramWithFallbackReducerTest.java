package at.medunigraz.imi.abbres.model.reducer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import at.medunigraz.imi.abbres.model.Abbreviation;
import at.medunigraz.imi.abbres.model.NGramMap;
import at.medunigraz.imi.abbres.model.NGramMapFactory;
import at.medunigraz.imi.abbres.model.matcher.LeftBigramMatcher;
import at.medunigraz.imi.abbres.model.matcher.UnigramMatcher;
import at.medunigraz.imi.abbres.model.policy.Mapper;
import at.medunigraz.imi.abbres.model.policy.StrictMapper;
import junit.framework.TestCase;

public class BigramWithFallbackReducerTest extends TestCase {
	public void testReduce() {
		Map<String, Integer> unigramMap = new TreeMap<>();
		unigramMap.put("To", 10);
		unigramMap.put("A", 1000);
		unigramMap.put("Kill", 1);
		unigramMap.put("Pride", 1000);
		unigramMap.put("Prejudice", 100);
		unigramMap.put("Pr", 10);
		unigramMap.put("Prid.", 10);
		NGramMapFactory.setUnigramMap(new NGramMap(unigramMap));
		
		Abbreviation a = new Abbreviation("Pr.").withLeftContext("and");
		List<Mapper> mappers = Arrays.asList(new StrictMapper(new UnigramMatcher(a)));
		BigramWithFallbackReducer reducer = new BigramWithFallbackReducer();
		assertEquals("Pride", reducer.reduce(mappers));
		
		
		Map<String, Integer> bigramMap = new TreeMap<>();
		bigramMap.put("Pride and", 10);
		bigramMap.put("and Prejudice", 1000);
		bigramMap.put("Prejudice is", 1);
		bigramMap.put("is a", 100);
		bigramMap.put("a novel", 100);
		bigramMap.put("and Pr", 10);
		bigramMap.put("and Prej.", 10);
		NGramMapFactory.setBigramMap(new NGramMap(bigramMap));
		
		mappers = Arrays.asList(new StrictMapper(new UnigramMatcher(a)), new StrictMapper(new LeftBigramMatcher(a)));
		assertEquals("Prejudice", reducer.reduce(mappers));	
	}
}
