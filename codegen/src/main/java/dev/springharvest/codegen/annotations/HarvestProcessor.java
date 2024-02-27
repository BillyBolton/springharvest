package dev.springharvest.codegen.annotations;

import com.google.auto.service.AutoService;
import dev.springharvest.codegen.generators.ControllerConstantsGenerator;
import dev.springharvest.codegen.generators.CrudControllerGenerator;
import dev.springharvest.codegen.generators.CrudRepositoryGenerator;
import dev.springharvest.codegen.generators.MapperGenerator;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;

@AutoService(Processor.class)
public class HarvestProcessor extends AbstractProcessor {

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Set.of(Harvest.class.getCanonicalName());
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    processingEnv.getMessager().printMessage(Kind.NOTE, "Processing annotations");
    for (Element element : roundEnv.getElementsAnnotatedWith(Harvest.class)) {
      if (element.getKind() != ElementKind.CLASS) {
        error(element, "Only classes can be annotated with @GenerateRestController");
        return true; // Exit processing
      }

      // Extract the qualified name of the annotated class
      TypeElement typeElement = (TypeElement) element;
      TypeMirror entityTypeMirror = typeElement.asType();

      // Extract other annotation attributes
      Harvest annotation = typeElement.getAnnotation(Harvest.class);
      final String DOMAIN_NAME_SINGULAR = annotation.domainNameSingular();
      final String DOMAIN_NAME_PLURAL = annotation.domainNamePlural();
      final String PARENT_DOMAIN_NAME = annotation.parentDomainName();
      final String DOMAIN_CONTEXT_PATH = annotation.domainContextPath();

      // Pass the annotated class name to the generator methods
      CrudRepositoryGenerator.generate(DOMAIN_NAME_SINGULAR, DOMAIN_NAME_PLURAL, processingEnv);
      MapperGenerator.generate(DOMAIN_NAME_SINGULAR, DOMAIN_NAME_PLURAL, entityTypeMirror, processingEnv);
      ControllerConstantsGenerator.generate(DOMAIN_NAME_SINGULAR, DOMAIN_NAME_PLURAL, PARENT_DOMAIN_NAME, DOMAIN_CONTEXT_PATH, processingEnv);
      CrudControllerGenerator.generate(processingEnv);

    }

    return true;
  }


  private void error(Element element, String message, Object... args) {
    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format(message, args), element);
  }
}