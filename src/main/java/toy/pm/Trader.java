package toy.pm;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Trader implements Comparable<Trader>, Serializable {
	private static final Logger log = LoggerFactory.getLogger(Trader.class);
	private static final long serialVersionUID = 1L;
	private static final Gson gson = new Gson();

	private String name = null;
	private String city = null;
	private String id = null;

	public Trader(String name, String city, String id) {
		name(name);
		city(city);
		id(id);
	}

	/**
	 * Converts the json response to a list of Trader objects.
	 * 
	 * @param json
	 *            string representing a json array
	 * @return List of Trader objects
	 */
	public static List<Trader> fromJsonToList(String json) {
		if (json == null || json.isEmpty()) {
			log.error("json must not be null or empty string");
			throw new IllegalArgumentException();
		}
		final Type listType = new TypeToken<ArrayList<Trader>>() {
		}.getType();
		return gson.fromJson(json, listType);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(", ");
		sb.append(city);
		sb.append(", ");
		sb.append(id);
		return sb.toString();
	}

	/*
	 * Instances are ordered by trader id. An object ordering is required for
	 * use in a TreeMultimap.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Trader other) {
		return id.compareTo(other.id());
	}

	public String name() {
		return name;
	}

	public void name(String name) {
		if (name == null || name.isEmpty()) {
			log.error("name must not be null or empty string");
			throw new IllegalArgumentException();
		}
		this.name = name;
	}

	public String city() {
		return city;
	}

	public void city(String city) {
		if (city == null || city.isEmpty()) {
			log.error("city must not be null or empty string");
			throw new IllegalArgumentException();
		}
		this.city = city;
	}

	public String id() {
		return id;
	}

	public void id(String id) {
		if (id == null || id.isEmpty()) {
			log.error("id must not be null or empty string");
			throw new IllegalArgumentException();
		}
		this.id = id.toLowerCase();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Trader)) {
			return false;
		}
		Trader other = (Trader) obj;
		if (city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!city.equals(other.city)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
