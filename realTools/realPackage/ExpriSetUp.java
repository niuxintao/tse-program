package realPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fc.testObject.TestCase;
import com.fc.testObject.TestCaseImplement;
import com.fc.tuple.Tuple;

//��������experiment��model
public class ExpriSetUp {

	private HashMap<Integer, List<Tuple>> bugsList;
	private HashMap<Integer, List<Integer>> priorityList;
	private int[] param;

	private List<DataRecord> records;

	public ExpriSetUp() {
		records = new ArrayList<DataRecord>();
		DataRecord record = new DataRecord();

		// tomcat 7.0

		/** 1. [ 1 , - , - , 3 , - , - , - , - , - , - ] **/

		/** 2. [ 1 , - , - , 1 , - , - , - , - , - , - ] **/

		/** 3. [ - , - , - , - , - , - , - , - , - , 0 ] **/
		record = new DataRecord();
		this.param = new int[] { 2, 2, 2, 4, 2, 2, 2, 2, 2, 3 };// parameters
		int[][] wrongs = new int[][] { { 1, 0, 0, 3, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 }, };
		int[][] bugs = new int[][] { { 0, 3 }, { 0, 3 }, { 8 } };
		int[] faults = new int[] { 1, 1, 1 };
		int[][] priority = new int[][] { {} };
		record.set(param, wrongs, bugs, faults, priority);
		records.add(record);

		// hsqldb 2.0rc
		record = new DataRecord();
		param = new int[] { 3, 2, 2, 2, 2, 2, 2, 2, 4, 3, 2, 2 };
		wrongs = new int[][] { { 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1 } };
		bugs = new int[][] { { 5, 8, 9 }, { 5, 8, 9 }, };
		faults = new int[] { 1, 1 };
		// prioirty : non bigger than 1, 1 masked 2, 1 masked 3.
		priority = new int[][] { {} };
		record.set(param, wrongs, bugs, faults, priority);
		records.add(record);

		// gcc 4.7.2
		record = new DataRecord();
		param = new int[] { 6, 2, 2, 2, 2, 2, 2, 2, 2, 2 };
		wrongs = new int[][] { { 2, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
				{ 2, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
				{ 3, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
				{ 3, 0, 0, 1, 0, 0, 0, 0, 0, 0 } };
		bugs = new int[][] { { 0, 5, 9 }, { 0, 3, 9 }, { 0, 5, 9 }, { 0, 3, 9 } };
		faults = new int[] { 1, 1, 1, 1 };
		// prioirty : non bigger than 1, 1 masked 2, 1 masked 3.
		priority = new int[][] { {} };
		record.set(param, wrongs, bugs, faults, priority);
		records.add(record);

		// jflex 1.4.2
		record = new DataRecord();
		param = new int[] { 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 3 };
		wrongs = new int[][] { { 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, };
		bugs = new int[][] { { 1, 2 } };
		faults = new int[] { 1 };
		priority = new int[][] { {} };
		record.set(param, wrongs, bugs, faults, priority);
		records.add(record);

		// grep 2.1.3
		record = new DataRecord();
		param = new int[] { 3, 2, 2, 4, 2, 2, 2 };
		wrongs = new int[][] { { 0, 0, 0, 0, 1, 0, 0 },
				{ 0, 0, 0, 1, 1, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0 } };
		bugs = new int[][] { { 3, 4 }, { 3, 4 }, { 3, 4 } };
		faults = new int[] { 1, 1, 1 };
		priority = new int[][] { {} };
		record.set(param, wrongs, bugs, faults, priority);
		records.add(record);

		// Cli 1.3
		record = new DataRecord();
		param = new int[] { 2, 2, 2, 2, 2, 2, 2, 2 };
		wrongs = new int[][] { { 0, 0, 0, 0, 0, 0, 0, 1 } };
		bugs = new int[][] { { 5, 6, 7 } };
		faults = new int[] { 1 };
		priority = new int[][] { {} };
		record.set(param, wrongs, bugs, faults, priority);
		records.add(record);

		// Joda-time 2.8
		record = new DataRecord();
		param = new int[] { 3, 2, 2, 2, 2, 2, 5, 3 };
		wrongs = new int[][] { { 0, 0, 0, 0, 1, 1, 0 } };
		bugs = new int[][] { { 4, 5 } };
		faults = new int[] { 1 };
		priority = new int[][] { {} };
		record.set(param, wrongs, bugs, faults, priority);
		records.add(record);

		// lang 3.4
		record = new DataRecord();
		param = new int[] { 2, 2, 3, 3, 3, 2, 2 };
		wrongs = new int[][] { { 0, 0, 0, 0, 0, 1, 1 } };
		bugs = new int[][] { { 5, 6 } };
		faults = new int[] { 1 };
		priority = new int[][] { {} };
		record.set(param, wrongs, bugs, faults, priority);
		records.add(record);

		// jsoup 1.8
		record = new DataRecord();
		param = new int[] { 2, 2, 2, 2, 2, 3, 2, 2, 2 };
		wrongs = new int[][] { { 0, 0, 0, 0, 0, 1, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0, 1, 0, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, 0 }, { 0, 0, 0, 0, 0, 1, 0, 0, 1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 1 } };

		bugs = new int[][] { { 5, 6 }, { 5, 6 }, { 5, 7 }, { 5, 7 }, { 5, 8 },
				{ 5, 8 } };
		faults = new int[] { 1, 1, 1, 1, 1, 1 };
		priority = new int[][] { {} };
		record.set(param, wrongs, bugs, faults, priority);
		records.add(record);
	}

	public HashMap<Integer, List<Tuple>> getBugsList() {
		return bugsList;
	}

	public HashMap<Integer, List<Integer>> getPriorityList() {
		return priorityList;
	}

	public int[] getParam() {
		return param;
	}

	public List<DataRecord> getRecords() {
		return records;
	}

	public void set(int[] param, int[][] wrongs, int[][] bugs, int[] faults,
			int[][] higherPriority) {
		this.param = param;
		bugsList = new HashMap<Integer, List<Tuple>>();
		priorityList = new HashMap<Integer, List<Integer>>();

		// set the priority
		for (int i = 0; i < higherPriority.length; i++) {
			int key = i + 1;
			int[] higher = higherPriority[i];
			List<Integer> priority = new ArrayList<Integer>();
			for (int j : higher)
				priority.add(j);
			priorityList.put(key, priority);
		}

		// set the bugs as well as its fault

		for (int i = 0; i < wrongs.length; i++) {
			int[] wrong = wrongs[i];
			int[] bug = bugs[i];

			int key = faults[i];

			TestCase wrongCase = new TestCaseImplement();
			wrongCase.setTestState(TestCase.FAILED);
			((TestCaseImplement) wrongCase).setTestCase(wrong);

			Tuple bugTuple = new Tuple(bug.length, wrongCase);
			bugTuple.setParamIndex(bug);

			if (bugsList.get(key) == null) {
				List<Tuple> tuples = new ArrayList<Tuple>();
				bugsList.put(key, tuples);
			}

			bugsList.get(key).add(bugTuple);
		}

	}

	public void set1() {
		// int[] param = new int[] { 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 4 };

		int[] wrong = new int[] { 1, 1, 1, 1, 1, 1, 1 };

		TestCase wrongCase = new TestCaseImplement();
		wrongCase.setTestState(TestCase.FAILED);
		((TestCaseImplement) wrongCase).setTestCase(wrong);

		int[] wrong2 = new int[] { 2, 2, 2, 2, 2, 2, 2 };

		TestCase wrongCase2 = new TestCaseImplement();
		wrongCase2.setTestState(TestCase.FAILED);
		((TestCaseImplement) wrongCase2).setTestCase(wrong2);

		Tuple bug1 = new Tuple(2, wrongCase);
		bug1.set(0, 0);
		bug1.set(1, 1);

		List<Tuple> bugs1 = new ArrayList<Tuple>();
		bugs1.add(bug1);

		Tuple bug2 = new Tuple(2, wrongCase2);
		bug2.set(0, 3);
		bug2.set(1, 4);

		List<Tuple> bugs2 = new ArrayList<Tuple>();
		bugs2.add(bug2);

		List<Integer> priority1 = new ArrayList<Integer>();
		priority1.add(2);
		List<Integer> priority2 = new ArrayList<Integer>();

		HashMap<Integer, List<Tuple>> bugs = new HashMap<Integer, List<Tuple>>();
		bugs.put(1, bugs1);
		bugs.put(2, bugs2);

		HashMap<Integer, List<Integer>> priority = new HashMap<Integer, List<Integer>>();

		priority.put(1, priority1);
		priority.put(2, priority2);

	}

	public static void main(String[] args) {
		ExpriSetUp ex = new ExpriSetUp();
		DataRecord record = ex.getRecords().get(0);
		ex.set(record.param, record.wrongs, record.bugs, record.faults,
				record.priority);
		for (int i : ex.getParam())
			System.out.print(i + " ");
		System.out.println();

		for (Integer key : ex.bugsList.keySet()) {
			System.out.println(key);
			List<Tuple> tuples = ex.getBugsList().get(key);
			for (Tuple tuple : tuples)
				System.out.print(tuple.toString() + " ");
			System.out.println();
		}

		for (Integer key : ex.priorityList.keySet()) {
			System.out.println(key);
			List<Integer> higher = ex.priorityList.get(key);
			for (Integer high : higher)
				System.out.print(high + " ");
			System.out.println();
		}
	}

}
