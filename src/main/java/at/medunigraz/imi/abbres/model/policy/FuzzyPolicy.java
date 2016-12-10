package at.medunigraz.imi.abbres.model.policy;

import at.medunigraz.imi.abbres.model.Abbreviation;

public class FuzzyPolicy extends AbstractPolicy {

	@Override
	public boolean containChars(String abbreviation, String expansion) {
		for (int i = 0, j = 0; i < abbreviation.length(); i++, j++) {
			char a = abbreviation.charAt(i);
			for (; j < expansion.length(); j++) {
				char e = expansion.charAt(j);
				if (a == e) {
					break;
				}
			}
			if (j == expansion.length() && i != abbreviation.length()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String prefix(Abbreviation abbreviation) {
		String token = abbreviation.getToken();
		if (token.isEmpty()) {
			return "";
		}

		return token.substring(0, 1);
	}

	@Override
	public int getPriority() {
		return 10;
	}

}