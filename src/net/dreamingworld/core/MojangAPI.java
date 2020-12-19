package net.dreamingworld.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;

public class MojangAPI {

	public static UUID getPlayerUUID(String nickname) {
		String json = makeRequest("users/profiles/minecraft/" + nickname);

		if (json == null) {
			return null;
		}

		Map<String, String> m = new Gson().fromJson(json, new TypeToken<HashMap<String, String>>() {}.getType());

		return UUID.fromString(getUUIDWithHyphens(m.get("id")));
	}

	
	public static String getUUIDWithHyphens(String uuid) {
		if (uuid.length() != 32) {
			return null;
		}
		
		return uuid.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"); // Mahija reptylij
	}

	public static String makeRequest(String req, String subdomain) {
		URL url = null;

		try {
			url = new URL("https://" + subdomain + ".mojang.com/" + req);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if (url != null) {
			String json = "";

			try {
				json = IOUtils.toString(url);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (!json.isEmpty()) {
				return json;
			} else {
				return null;
			}
		}

		return null;
	}

	public static String makeRequest(String req) {
		return makeRequest(req, "api");
	}
}
