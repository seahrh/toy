package toy._default;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

import toy._default.CircusTower.Person;

public class CircusTowerTest {

	@Test(dataProvider = "maxSequenceData")
	public void maxSequenceTest(List<Person> input, List<Person> expected,
			String description) {
		List<Person> actual = CircusTower.maxSequence(input);
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] maxSequenceData() {
		List<Person> i1 = Lists.newArrayList(new CircusTower.Person(65, 100));
		List<Person> e1 = i1;
		List<Person> i2 = Lists.newArrayList(new CircusTower.Person(65, 100),
				new CircusTower.Person(70, 150),
				new CircusTower.Person(56, 90),
				new CircusTower.Person(75, 190),
				new CircusTower.Person(60, 95), new CircusTower.Person(68, 110));
		List<Person> e2 = Lists.newArrayList(new CircusTower.Person(56, 90),
				new CircusTower.Person(60, 95),
				new CircusTower.Person(65, 100),
				new CircusTower.Person(68, 110),
				new CircusTower.Person(70, 150),
				new CircusTower.Person(75, 190));
		List<Person> i3 = Lists.newArrayList(new CircusTower.Person(65, 100),
				new CircusTower.Person(70, 109),
				new CircusTower.Person(56, 90),
				new CircusTower.Person(75, 190),
				new CircusTower.Person(60, 89), new CircusTower.Person(68, 110));
		List<Person> e3 = Lists.newArrayList(new CircusTower.Person(60, 89),
				new CircusTower.Person(65, 100),
				new CircusTower.Person(68, 110));
		return new Object[][] { { i1, e1, "size 1" }, { i2, e2, "size 6" },
				{ i3, e3, "size 6, unfits at index 1 and 4, max len 3" } };
	}

}
