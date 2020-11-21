package net.dreamingworld.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class MojangAPI { // Class from our (2018?) projects :+)

	public static UUID getPlayerUUID(String nickname) {
		URL url = null;
		try {
			url = new URL("https://api.mojang.com/users/profiles/minecraft/" + nickname);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (url != null) {
			String UUIDJson = "";
			try {
				UUIDJson = IOUtils.toString(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (!UUIDJson.isEmpty()) {
				try {
					return UUID.fromString(getUUIDWithHyphens((String)((JSONObject)JSONValue.parseWithException(UUIDJson)).get("id")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			else
				return null;
		}
		
		return null;
	}
	
	public static String getUUIDWithHyphens(String uuid) {
		if (uuid.length() != 32)
			return null;
		
		return uuid.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"); // Mahija reptylij
	}
}
