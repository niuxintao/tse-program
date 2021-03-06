package realPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;




import com.fc.testObject.TestCase;
import com.fc.testObject.TestCaseImplement;
import com.fc.tuple.Tuple;

public class RealExperiementData {

	private int[] parameter;
	// private List<TestCase> allCases;

	private HashMap<Integer, List<Integer>> higherPriority;

	// private List<TestCase> rightCases;

	private HashMap<Integer, List<TestCase>> wrongCases;

	private HashMap<Integer, List<Tuple>> bugsTable;
	
	
	private List<TestCase>  rightSuite;

//	private CaseRunner ignoreRuner;
//	private CaseRunner distinguishRunenr;
//	private CaseRunner maskRunenr;

	public RealExperiementData() {
		this.init();
	}

	public void init() {
		// this.allCases = new ArrayList<TestCase>();
		this.higherPriority = new HashMap<Integer, List<Integer>>();
		// this.rightCases = new ArrayList<TestCase>();
		this.wrongCases = new HashMap<Integer, List<TestCase>>();
		this.bugsTable = new HashMap<Integer, List<Tuple>>();
		rightSuite = new ArrayList<TestCase> ();
	}

	public void setParam(int[] param) {
		this.parameter = param;
	}

	public void setHigherPriority(HashMap<Integer, List<Integer>> higherPriority) {
		this.higherPriority = higherPriority;
	}

	public void addCodeAndPriority(Integer code, List<Integer> higher) {
		this.higherPriority.put(code, higher);
	}


	public void setBugs(HashMap<Integer, List<Tuple>> bugsTable) {
		this.bugsTable = bugsTable;
		List<Tuple> notContains = new ArrayList<Tuple>();
		
		for (Integer code : bugsTable.keySet()) {
			List<Tuple> mfs = bugsTable.get(code);
			List<Integer> higher = this.higherPriority.get(code);
			List<Tuple> higherBugs = new ArrayList<Tuple>();
			for (Integer high : higher) {
				higherBugs.addAll(this.bugsTable.get(high));
			}
			notContains.addAll(mfs);

			this.setWrongCases(code, mfs, higherBugs);
		}
		
		
		Tuple contains = new Tuple(0, this.getWrongCases().get(1).get(0));
		this.rightSuite = this.casesContainAndNotContain(contains, notContains);
	
	}

	public void setWrongCases(Integer code, List<Tuple> mfss,
			List<Tuple> higherBugs) {
		List<TestCase> wrongCases = new ArrayList<TestCase>();
		for (int i = 0; i < mfss.size(); i++) {
			Tuple contained = mfss.get(i);
			List<Tuple> notContained = new ArrayList<Tuple>();
			notContained.addAll(higherBugs);
			for (int j = 0; j < i; j++)
				notContained.add(mfss.get(j));
			wrongCases.addAll(this.casesContainAndNotContain(contained,
					notContained));
		}
		
//		for (TestCase t : wrongCases) {
//			System.out.println(t.getStringOfTest());
//		}

		this.wrongCases.put(code, wrongCases);

	}

	public List<TestCase> casesContainAndNotContain(Tuple contain,
			List<Tuple> notContain) {
//		for(Tuple t : notContain){
//			System.out.println(t.toString());
//		}
		List<TestCase> result = new ArrayList<TestCase>();
		int[] init = new int[this.parameter.length];
		for (int i = 0; i < contain.getDegree(); i++) {
			init[contain.getParamIndex()[i]] = contain.getParamValue()[i];
		}
		TestCase firstTestCase = new TestCaseImplement(init);
//		System.out.println(firstTestCase.getStringOfTest());
//		if (!containTuple(firstTestCase, notContain))
//			result.add(firstTestCase);
		GenMaskTestCaseWithOri gen = new GenMaskTestCaseWithOri(firstTestCase,
				this.parameter, contain);
		while (!gen.isStop()) {
			TestCase next = gen.generateTestCaseContainTuple(contain);
//			System.out.println(next.getStringOfTest());
			if (!containTuple(next, notContain))
				result.add(next);
		}
		
//		for(TestCase t : result){
//			System.out.println(t.getStringOfTest());
//		}

		return result;
	}

	boolean containTuple(TestCase testCase, List<Tuple> tuples) {
//		System.out.println(testCase.getStringOfTest());
		boolean result = false;
		for (Tuple tuple : tuples) {
//			System.out.println(tuple.toString());
			if (testCase.containsOf(tuple)) {
				result = true;
				break;
			}
		}
//		System.out.println(result);
		return result;
	}

	// public void addBug(Integer code, List<Tuple> bugs) {
	// this.bugsTable.put(code, bugs);
	//
	// }

	public List<Tuple> getMFS() {
		List<Tuple> result = new ArrayList<Tuple>();

		for (Entry<Integer, List<Tuple>> entry : bugsTable.entrySet())
			result.addAll(entry.getValue());

		return result;
	}

	public int[] getParameter() {
		return parameter;
	}

	// public List<TestCase> getAllCases() {
	// return allCases;
	// }

	// public List<Integer> getBugCode() {
	// return BugCode;
	// }

	public HashMap<Integer, List<Integer>> getHigherPriority() {
		return higherPriority;
	}

	// public List<TestCase> getRightCases() {
	// return rightCases;
	// }

	public HashMap<Integer, List<TestCase>> getWrongCases() {
		return wrongCases;
	}

	public HashMap<Integer, List<Tuple>> getBugsTable() {
		return bugsTable;
	}

	public List<TestCase> getRightSuite() {
		return rightSuite;
	}


}
