
public class Treatment {
	private String name;
	private int cost;
	private TreatmentType type;
	
<<<<<<< HEAD
	public Treatment(String name, int cost) {
		this.name = name;
		this.cost = cost;
		
		switch (name) {
			case "check-up": type = TreatmentType.CHECKUP; break;
			case "hygiene": type = TreatmentType.HYGIENE; break;
			default: type = TreatmentType.REPAIR; break;
		}
=======
	public Treatment(String name, int cost,boolean cosmetic) {
		this.name = name;
		this.cost = cost;
		if (cosmetic) {
			type = TreatmentType.COSMETIC;
		}
		else {
			switch (name) {
				case "check-up": type = TreatmentType.CHECKUP; break;
				case "hygiene": type = TreatmentType.HYGIENE; break;
				default: type = TreatmentType.REPAIR; break;
			}
		}
		
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
	}

	public String getName() {
		return name;
	}

	public int getCost() {
		return cost;
	}

	public TreatmentType getType() {
		return type;
	}

}
