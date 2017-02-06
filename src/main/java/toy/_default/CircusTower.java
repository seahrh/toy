package toy._default;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A circus is designing a tower routine consisting of people standing atop one
 * another’s shoulders. For practical and aesthetic reasons, each person must be
 * both shorter and lighter than the person below him or her. Given the heights
 * and weights of each person in the circus, write a method to compute the
 * largest possible number of people in such a tower. [CTCI Q9.7]
 * <p>
 * EXAMPLE: Input (ht, wt): (65, 100) (70, 150) (56, 90) (75, 190) (60, 95) (68,
 * 110)
 * <p>
 * Output: The longest tower is length 6 and includes from top to bottom: (56,
 * 90) (60,95) (65,100) (68,110) (70,150) (75,190)
 * 
 */
public final class CircusTower {
	private static final Logger log = LoggerFactory.getLogger(CircusTower.class);

	private CircusTower() {
		// Not meant to be instantiated
	}

	/**
	 * Find the longest non-decreasing subsequence.
	 * <p>
	 * First, sort the persons by height, then weight.
	 * <p>
	 * a) Start at the beginning of the sequence. Currently, max_sequence is
	 * empty. b) If, for the next item, the height and the weight is not greater
	 * than those of the previous item, we mark this item as “unfit” . (60,95)
	 * (65,100) (75,80) (80, 100) (unfit item) c) If the sequence found has more
	 * items than “max sequence”, it becomes “max sequence”. d) After that the
	 * search is repeated from the “unfit item”, until we reach the end of the
	 * original sequence.
	 * <p>
	 * Solution takes O(n^2) time and O(1) space.
	 * 
	 * @param persons
	 * @return
	 */
	public static List<Person> maxSequence(List<Person> persons) {
		if (persons == null || persons.isEmpty()) {
			log.error("Collection of persons must not be null or empty");
			throw new IllegalArgumentException();
		}
		Collections.sort(persons);
		log.debug("sorted persons={}", persons);
		List<Person> max = new ArrayList<>();
		List<Person> next;
		int unfit = 0;
		int size;
		while (unfit < persons.size()) {
			next = nextSequence(persons, unfit);
			size = next.size();
			if (size > max.size()) {
				max = next;
			}
			unfit += size;
			log.debug("unfit={}, next={}", unfit, next);
		}
		return max;
	}

	private static List<Person> nextSequence(List<Person> persons, int begin) {
		int size = persons.size();
		if (begin < 0 || begin >= size) {
			log.error("index out of bounds, begin={}", begin);
			throw new IllegalArgumentException();
		}
		List<Person> ret = new ArrayList<>();
		ret.add(persons.get(begin));
		Person curr;
		Person prev;
		for (int i = begin + 1; i < size; i++) {
			curr = persons.get(i);
			prev = persons.get(i - 1);
			// Cannot use Person#compareTo method here
			// because curr must have non-decreasing height _and_weight
			// compared to prev
			if (curr.height < prev.height || curr.weight < prev.weight) {
				break;
			}
			ret.add(curr);
		}
		return ret;
	}

	public static class Person implements Comparable<Person> {
		private int height = 0;
		private int weight = 0;

		public Person(int height, int weight) {
			this.height = height;
			this.weight = weight;
		}

		public int compareTo(Person other) {
			int ret = Integer.compare(height, other.height);
			if (ret != 0) {
				return ret;
			}
			return Integer.compare(weight, other.weight);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("h=");
			sb.append(height);
			sb.append(", w=");
			sb.append(weight);
			return sb.toString();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + height;
			result = prime * result + weight;
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
			if (!(obj instanceof Person)) {
				return false;
			}
			Person other = (Person) obj;
			if (height != other.height) {
				return false;
			}
			if (weight != other.weight) {
				return false;
			}
			return true;
		}
	}
}
