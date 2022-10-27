//Dalveer Dosanjh
package GUIDalveerDosanjh;

public enum Menu2022 {
	SABLEFISH("Sablefish",10,250),
	DUCKBREAST("Duck breast",0,340),
	SALMON("Salmon sushi roll",5,250),
	VEAL("Veal",5,170),
	LOBSTER("Lobster tail",15,170),
	LAMB("Lamb chop",12,300),
	HAMACHI("Hamachi sashimi",0,230),
	SCALLOP("Scallop roll",15,230),
	OCTOPUS("Octopus cake",7,150),
	LINGCOD("Lingcod steak",10,100),
	MUSSEL("Mussel",0,170);
	
	private String name;
	private double discount;
	private double calories;
	
	private Menu2022(String name,double discount,int calories)
	{
		this.name = name;
		this.discount = discount;
		this.calories = calories;
	}
		
	public String getName() 
	{
		return name;
	}
	
	public double getCalories() {
		return calories;
	}
	
	public double getDiscount() {
		return discount;
	}


}
