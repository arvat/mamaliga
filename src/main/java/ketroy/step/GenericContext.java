package ketroy.step;

import ketroy.annotation.*;

public class GenericContext extends ContextStep {

	@Given("^today is ([^\"]*)$")
	public void todayIs(String day) throws Exception {
		System.out.println("*" + day);
	}
	
	@Then("^what day is today$")
	public void whatDay() throws Exception {
		System.out.println("***");
	}
}
