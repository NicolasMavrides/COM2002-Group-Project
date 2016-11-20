
public class Treatment {
	private String name;
	private int cost;
	private TreatmentType type;
	
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
