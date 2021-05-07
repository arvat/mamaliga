package ketroy.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import ketroy.annotation.Given;
import ketroy.step.ContextStep;

public class BddService {

	public static final String CriteriaGiven="Given";
	public static final String CriteriaWhen="When";
	public static final String CriteriaThen="Then";
	
	public static final String StepPackage = "ketroy.step";
	
	public static void triggerRunnerQueue(List<String> lines) {
		
		for (String line : lines) {
			
		}
	}
	
	private Set<Class<? extends ContextStep>> getStepObjects() {
		Reflections reflections = new Reflections(StepPackage);
		return reflections.getSubTypesOf(ContextStep.class);
	}
	
	public List<String> getMethods() {
		List<String> methods = new ArrayList<>();

		
		for (Class<? extends ContextStep> selection : getStepObjects()) {
	        for (Method method : selection.getDeclaredMethods()) {
	        	if (method.isAnnotationPresent(Given.class))
	        		methods.add(CriteriaGiven + " " + getAnnotationValue(method.getAnnotation(Given.class).value()));
	        	
	        	if (method.isAnnotationPresent(When.class))
	        		methods.add(BddConstant.CriteriaWhen + " " + getAnnotationValue(method.getAnnotation(When.class).value()));
	        	
	        	if (method.isAnnotationPresent(Then.class))
	        		methods.add(BddConstant.CriteriaThen + " " + getAnnotationValue(method.getAnnotation(Then.class).value()));
	        	
	        }
		}
		
        return methods;
	}
	
	private static String getAnnotationValue(String value) {
		return value.replace("([^\"]*)", "property");
	}
}
