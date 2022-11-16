package demo;

public class Evaluation {
	private String dateEval;
	private String hEval;
	private String avis;
	private int note;

	public int getNote() {
		return note;
	}

	public Evaluation dateEval(String dateEval) {
		this.dateEval = dateEval;
		return this;
	}

	public Evaluation hEval(String hEval) {
		this.hEval = hEval;
		return this;
	}

	public Evaluation avis(String avis) {
		this.avis = avis;
		return this;
	}

	public Evaluation note(int note) {
		this.note = (note > 5) ? 5 : note;
		return this;
	}

}
