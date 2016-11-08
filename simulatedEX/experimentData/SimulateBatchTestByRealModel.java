package experimentData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import realPackage.DataRecord;
import realPackage.RealExperiementData;
import realPackage.ExpriSetUp;

import com.fc.testObject.TestCase;
import com.fc.testObject.TestSuite;
import com.fc.testObject.TestSuiteImplement;
import com.fc.tuple.Tuple;

public class SimulateBatchTestByRealModel {
	// public static final int CHAIN = 0;
	public static final int AUGCHAIN = 0;
	// public static final int FEEDBACK = 2;
	public static final int AUGFEEDBACK = 1;
	public static final int FIC = 2;
	public static final int RI = 3;
	public static final int OFOT = 4;
	public static final int LG = 5;
	public static final int SP = 6;
	public static final int AIFL = 7;
	public static final int TRT = 8;
	public static final int AUGTRT = 9;
	public static final int CTA = 10;

	public static final int NUM = 11;

	ExpriSetUp setup;
	PrintStream out;
	public SimulateBatchTestByRealModel() {
		setup = new ExpriSetUp();
		out=System.out;
	}

	public List<double[]> test(int index) {
		System.setOut(out);
		System.out.println("the " + index + "th");

		DataRecord record = setup.getRecords().get(index);
		setup.set(record.param, record.wrongs, record.bugs, record.faults,
				record.priority);
		for(int i : record.param)
			System.out.print(i+ " ");
		System.out.println();
		RealExperiementData exData = new RealExperiementData();
		exData.setParam(setup.getParam());
		exData.setHigherPriority(setup.getPriorityList());
		exData.setBugs(setup.getBugsList());

		List<Tuple> bugs = new ArrayList<Tuple>();
		for (Integer key : setup.getBugsList().keySet())
			bugs.addAll(setup.getBugsList().get(key));

		TestEveryAlogrithm ta = new TestEveryAlogrithm();

		// data record
		List<List<double[]>> data = new ArrayList<List<double[]>>();
		for (int i = 0; i < NUM; i++)
			data.add(new ArrayList<double[]>());

		List<TestCase> wrongCasesSet = new ArrayList<TestCase>();
		for (Integer code : exData.getWrongCases().keySet()) {
			List<TestCase> wrongCases = exData.getWrongCases().get(code);
			wrongCasesSet.addAll(wrongCases);
		}

		List<TestCase> rightCases = exData.getRightSuite();
		return this.doProcess(wrongCasesSet, bugs, exData.getParameter(),
				rightCases, ta, data, index + "");

	}

	public void conductTest(int start, int end) {

		List<List<double[]>> temp = new ArrayList<List<double[]>>();
		SimulateBatchTestByRealModel ex = new SimulateBatchTestByRealModel();
		for (int i = start; i < end; i++) {
//			System.out.println();
			temp.add(ex.test(i));
		}
		List<List<double[]>> result = new ArrayList<List<double[]>>();
		for (int i = 0; i < NUM; i++) {
			List<double[]> temRAlg = new ArrayList<double[]>();
			for (List<double[]> tt : temp)
				temRAlg.add(tt.get(i));
			result.add(temRAlg);
		}

		// output result
		this.setOutPut("Avg" + "-Real-addtion.txt");
		for (List<double[]> da : result) {
			outputResult(da, 0);
		}
		this.setOutPut("Avg" + "-Real-recall.txt");
		for (List<double[]> da : result) {
			outputResult(da, 1);
		}
		this.setOutPut("Avg" + "-Real-precise.txt");
		for (List<double[]> da : result) {
			outputResult(da, 2);
		}
	}

	
	public void conductTest(int[] index) {

		List<List<double[]>> temp = new ArrayList<List<double[]>>();
		SimulateBatchTestByRealModel ex = new SimulateBatchTestByRealModel();
		for (int i : index) {
//			System.out.println();
			temp.add(ex.test(i));
		}
		List<List<double[]>> result = new ArrayList<List<double[]>>();
		for (int i = 0; i < NUM; i++) {
			List<double[]> temRAlg = new ArrayList<double[]>();
			for (List<double[]> tt : temp)
				temRAlg.add(tt.get(i));
			result.add(temRAlg);
		}

		// output result
		this.setOutPut("Avg" + "-Real-addtion.txt");
		for (List<double[]> da : result) {
			outputResult(da, 0);
		}
		this.setOutPut("Avg" + "-Real-recall.txt");
		for (List<double[]> da : result) {
			outputResult(da, 1);
		}
		this.setOutPut("Avg" + "-Real-precise.txt");
		for (List<double[]> da : result) {
			outputResult(da, 2);
		}
	}
	
	
	private List<double[]> doProcess(List<TestCase> wrongCases,
			List<Tuple> bugs, int[] param, List<TestCase> rightTestCases,
			TestEveryAlogrithm ta, List<List<double[]>> data, String fileId) {

		TestSuite rightTestSuite = new TestSuiteImplement();
		for (TestCase rightTestCase : rightTestCases)
			rightTestSuite.addTest(rightTestCase);
		for (TestCase wrongCase : wrongCases) {
			wrongCase.setTestState(TestCase.FAILED);
//			data.get(TRT)
//					.add(ta.expTRT(wrongCase, bugs, param, rightTestSuite));
			data.get(AUGCHAIN).add(
					ta.expAugChain(wrongCase, bugs, param, rightTestSuite));
//			data.get(AUGTRT).add(
//					ta.expAUGTRT(wrongCase, bugs, param, rightTestSuite));
			data.get(AUGFEEDBACK).add(
					ta.expChainAugFeedBack(wrongCase, bugs, param,
							rightTestSuite));
			data.get(FIC).add(ta.expFIC(wrongCase, bugs, param));
			data.get(RI).add(ta.expRI(wrongCase, bugs, param));
			data.get(OFOT).add(ta.expOFOT(wrongCase, bugs, param));
			data.get(LG).add(
					ta.expLocateGraph(wrongCase, bugs, param,
							rightTestSuite.getAt(0)));

			// sp set 4 degree
			data.get(SP).add(ta.expSpectrumBased(wrongCase, bugs, param, 2));
//			data.get(AIFL).add(ta.expIterAIFL(wrongCase, bugs, param));
			try {

				data.get(CTA).add(ta.expCTA(wrongCase, bugs, param));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// output result
		this.setOutPut("detail/" + fileId + "-Real-addtion.txt");
		for (List<double[]> da : data) {
			outputResult(da, 0);
		}
		this.setOutPut("detail/" +fileId + "-Real-recall.txt");
		for (List<double[]> da : data) {
			outputResult(da, 1);
		}
		this.setOutPut("detail/" +fileId + "-Real-precise.txt");
		for (List<double[]> da : data) {
			outputResult(da, 2);
		}

		List<double[]> result = new ArrayList<double[]>();

		for (List<double[]> da : data) {
			double[] a = new double[3];
			a[0] = 0;
			a[1] = 0;
			a[2] = 0;
			for (double[] b : da) {
				a = add(a, b);
			}
			a = getAvg(a, da.size());
			result.add(a);
		}

		return result;
	}

	public void outputResult(List<double[]> data, int id) {
		for (double[] da : data)
			System.out.print(da[id] + " ");
		System.out.println();
	}

	public double[] add(double[] a, double[] b) {
		a[0] += b[0];
		a[1] += b[1];
		a[2] += b[2];
		return a;
	}

	public double[] getAvg(double[] a, int num) {
		a[0] /= num;
		a[1] /= num;
		a[2] /= num;
		return a;
	}

	public void setOutPut(String name) {
		File test = new File(name);
		try {
			PrintStream out = new PrintStream(new FileOutputStream(test));
			System.setOut(out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SimulateBatchTestByRealModel fk = new SimulateBatchTestByRealModel();
		fk.conductTest(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8 });
	}
	
	//0 1 2  3 Âý
}
