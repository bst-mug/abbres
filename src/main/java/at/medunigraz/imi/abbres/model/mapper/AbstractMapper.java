package at.medunigraz.imi.abbres.model.mapper;

import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;

import at.medunigraz.imi.abbres.model.matcher.LeftBigramMatcher;
import at.medunigraz.imi.abbres.model.matcher.Matcher;
import at.medunigraz.imi.abbres.model.policy.Policy;

public abstract class AbstractMapper implements Mapper {
	protected Map<String, Integer> candidates = null;

	protected Map.Entry<String, Integer> bestEntry = null;

	protected Matcher matcher;

	protected Policy policy;

	private static final int MIN_COUNT = 1;

	public AbstractMapper(Matcher matcher, Policy policy) {
		this.matcher = matcher;
		this.policy = policy;
	}

	public Map<String, Integer> map() {
		String prefix = prefix(matcher.getAbbreviation());
		String suffix = suffix(matcher.getAbbreviation());

		Map<String, Integer> subMap = matcher.submap(prefix, suffix);
		Map<String, Integer> ret = new TreeMap<>();

		for (Map.Entry<String, Integer> entry : subMap.entrySet()) {
			String tokenExpansion = matcher.expansion(entry.getKey());
			String token = matcher.getAbbreviation().getTrimmedToken();
			if (!policy.isValidExpansion(token, tokenExpansion)) {
				continue;
			}
			if (!matcher.getAbbreviation().isValidExpansion(tokenExpansion)) {
				continue;
			}

			String context = matcher.context(matcher.getAbbreviation().getTokenWithContext());
			String contextExpansion = matcher.context(entry.getKey());
			if (!isValidContext(context, contextExpansion)) {
				continue;
			}

			ret.put(tokenExpansion, entry.getValue());
		}

		return ret;
	}

	public Map<String, Integer> getCandidates() {
		if (candidates == null) {
			candidates = map();
		}
		return candidates;
	}

	public Matcher getMatcher() {
		return matcher;
	}

	public Policy getPolicy() {
		return policy;
	}

	public Map.Entry<String, Integer> getBestEntry() {
		if (bestEntry == null) {
			bestEntry = calculateBestEntry();
		}
		return bestEntry;
	}

	protected Map.Entry<String, Integer> calculateBestEntry() {
		Map.Entry<String, Integer> bestEntry = new AbstractMap.SimpleEntry<>("", 0);
		int bestCount = 0;
		for (Map.Entry<String, Integer> entry : getCandidates().entrySet()) {
			int count = entry.getValue();
			if (count > bestCount && count >= MIN_COUNT) {
				bestEntry = entry;
				bestCount = count;
			}
		}

		return bestEntry;
	}

	@Override
	public int compareTo(Mapper o) {
		int mapperDiff = this.getPriority() - o.getPriority();
		if (mapperDiff != 0) {
			return mapperDiff;
		}

		int matcherDiff = this.getMatcher().compareTo(o.getMatcher());
		if (matcherDiff != 0) {
			return matcherDiff;
		}

		int policyDiff = this.getPolicy().compareTo(o.getPolicy());
		if (policyDiff != 0) {
			return policyDiff;
		}

		int bestEntryDiff = this.getBestEntry().getValue() - o.getBestEntry().getValue();
		if (bestEntryDiff != 0) {
			return bestEntryDiff;
		}
		
		return this.getMatcher() instanceof LeftBigramMatcher ? +1 : -1;
	}

}
