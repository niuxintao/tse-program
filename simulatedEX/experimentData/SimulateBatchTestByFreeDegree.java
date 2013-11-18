package experimentData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.fc.tuple.Tuple;

import experimentData.DataBaseOfTestCase;
import experimentData.ExperimentData;

public class SimulateBatchTestByFreeDegree {
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

	public SimulateBatchTestByFreeDegree() {

	}

	public void batchTest(int caseLenth, int value, int[] degrees) {
		List<List<double[]>> data = new ArrayList<List<double[]>>();
		for (int i = 0; i < NUM; i++)
			data.add(new ArrayList<double[]>());
		for (Integer degree : degrees) {
			List<double[]> da = this.testByDegree(caseLenth, value, degree);
			for (int i = 0; i < NUM; i++) {
				data.get(i).add(da.get(i));
			}
		}

		outputResult("single-8-freeDegree", data);

	}

	public void outputResult(String fileId, List<List<double[]>> data) {
		// output result
		this.setOutPut(fileId + "-freeD-addtion.txt");
		for (List<double[]> da : data) {
			outputResult(da, 0);
		}
		this.setOutPut(fileId + "-freeD-recall.txt");
		for (List<double[]> da : data) {
			outputResult(da, 1);
		}
		this.setOutPut(fileId + "-freeD-precise.txt");
		for (List<double[]> da : data) {
			outputResult(da, 2);
		}
	}

	public List<double[]> testByDegree(int caseLenth, int value, int degree) {
		DataBaseOfTestCase casedata = new DataBaseOfTestCase(caseLenth, value);
		ExperimentData experimentData = new ExperimentData(casedata);
		List<Tuple> bugs = experimentData.generateBugByDegree(degree);
		TestEveryAlogrithm ta = new TestEveryAlogrithm();

		// data record
		List<double[]> data = new ArrayList<double[]>();
		for (int i = 0; i < NUM; i++)
			data.add(new double[3]);

		// create bug paris
		List<List<Tuple>> buPairs = new ArrayList<List<Tuple>>();
		for (Tuple tuple : bugs) {
			List<Tuple> bgPair = new ArrayList<Tuple>();
			bgPair.add(tuple);
			buPairs.add(bgPair);
		}

		doProcess(experimentData, ta, data, buPairs);
		return data;
	}

	private void doProcess(ExperimentData experimentData,
			TestEveryAlogrithm ta, List<double[]> data,
			List<List<Tuple>> buPairs) {
		// for each bug pairs, inject and test and record
		int[] param = new int[experimentData.getParam().length];
		for (int i = 0; i < param.length; i++)
			param[i] = 3;

		for (List<Tuple> bgPair : buPairs) {
			add(data.get(TRT), ta.expTRT(experimentData.getWrongCase(), bgPair,
					experimentData.getParam(), experimentData.getRightSuite()));
			add(data.get(AUGCHAIN), ta.expAugChain(
					experimentData.getWrongCase(), bgPair,
					experimentData.getParam(), experimentData.getRightSuite()));
			add(data.get(AUGTRT), ta.expAUGTRT(experimentData.getWrongCase(),
					bgPair, experimentData.getParam(),
					experimentData.getRightSuite()));
			add(data.get(AUGFEEDBACK), ta.expChainAugFeedBack(
					experimentData.getWrongCase(), bgPair,
					experimentData.getParam(), experimentData.getRightSuite()));
			add(data.get(FIC), ta.expFIC(experimentData.getWrongCase(), bgPair,
					experimentData.getParam()));
			add(data.get(RI), ta.expRI(experimentData.getWrongCase(), bgPair,
					experimentData.getParam()));
			add(data.get(OFOT), ta.expOFOT(experimentData.getWrongCase(),
					bgPair, experimentData.getParam()));
			add(data.get(LG), ta.expLocateGraph(experimentData.getWrongCase(),
					bgPair, experimentData.getParam(), experimentData
							.getRightSuite().getAt(0)));

			// sp set 4 degree
			add(data.get(SP), ta.expSpectrumBased(
					experimentData.getWrongCase(), bgPair, param,  4));
			add(data.get(AIFL), ta.expIterAIFL(experimentData.getWrongCase(),
					bgPair, experimentData.getParam()));
			try {

				add(data.get(CTA),
						ta.expCTA(experimentData.getWrongCase(), bgPair, param));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (double[] da : data) {
			getAvg(da, buPairs.size());
		}
	}

	public void outputResult(List<double[]> data, int id) {
		for (double[] da : data)
			System.out.print(da[id] + " ");
		System.out.println();
	}

	public void add(double[] a, double[] b) {
		a[0] += b[0];
		a[1] += b[1];
		a[2] += b[2];
	}

	public void getAvg(double[] a, int num) {
		a[0] /= num;
		a[1] /= num;
		a[2] /= num;
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
		SimulateBatchTestByFreeDegree fk = new SimulateBatchTestByFreeDegree();
		fk.batchTest(8, 10, new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
	}
}
