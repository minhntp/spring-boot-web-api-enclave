package com.nqminh.util;

import org.springframework.stereotype.Component;

@Component
public class Utils {

	public String generateSlug(String name) {

		// System.out.println("1. text = " + name);

		// replace all non-words with space
		String nonWordToSpace = name.replaceAll("\\W+", " ");
		// System.out.println("2. nonWordToSpace = " + nonWordToSpace);

		// trim
		String trim = nonWordToSpace.trim();
		// System.out.println("3. trim = " + trim);

		// replace all spaces with single dash
		String spaceToSingleDash = trim.replaceAll("\\s+", "-");
		// System.out.println("4. spaceToSingleDash = " + spaceToSingleDash);

		// lower case (done)
		String lowerCase = spaceToSingleDash.toLowerCase();
		// System.out.println("lowerCase (done) = " + lowerCase);

		return lowerCase;
	}

}
