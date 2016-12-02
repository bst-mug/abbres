package at.medunigraz.imi.abbres.model.mapper;

import java.util.Map;
import java.util.TreeMap;

import at.medunigraz.imi.abbres.TextUtils;
import at.medunigraz.imi.abbres.model.matcher.Matcher;

public class StrictMapper extends AbstractMapper {

	public StrictMapper(Matcher matcher) {
		super(matcher);
	}
	
	@Override
	public Map<String, Integer> map() {
		String prefix = prefix();
		String suffix = matcher.suffix();

		Map<String, Integer> subMap = matcher.submap(prefix, suffix);
		Map<String, Integer> ret = new TreeMap<>();

		for (Map.Entry<String, Integer> entry : subMap.entrySet()) {
			String expansion = matcher.expansion(entry.getKey());
			if (matcher.isValidExpansion(expansion)) {
				ret.put(expansion, entry.getValue());
			}
		}

		return ret;
	}
	
	public String prefix() {
		String trimmedAbbrev = matcher.getAbbreviation().getTrimmedToken();
		return matcher.prefix().concat(trimmedAbbrev);
	}

}
