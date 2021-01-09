import java.lang.reflect.Method;

import javax.lang.model.SourceVersion;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.springframework.cache.annotation.Cacheable;

@Cacheable
public class ReflexExample {

	public static void main (String [] args) {
		
		Method [] methods = String.class.getMethods();
		
		for(Method mm : methods) {
//			System.out.println(mm.getName());
		}
		
		JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
		for (SourceVersion version: jc.getSourceVersions()) {
			System.out.println(version);
		}
		
	}
}
