package dev.springharvest.codegen.annotations;

import com.google.auto.service.AutoService;
import dev.springharvest.codegen.factories.ControllerConstantsGenerator;
import dev.springharvest.codegen.factories.CrudControllerGenerator;
import dev.springharvest.codegen.factories.CrudRepositoryGenerator;
import dev.springharvest.codegen.factories.CrudServiceGenerator;
import dev.springharvest.codegen.factories.MapperGenerator;
import dev.springharvest.codegen.models.HarvestBO;
import dev.springharvest.codegen.utils.AnnotationPackageFinder;
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
import org.apache.commons.lang3.StringUtils;

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
    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing annotations: @Harvest");
    for (Element element : roundEnv.getElementsAnnotatedWith(Harvest.class)) {

      if (element.getKind() != ElementKind.CLASS) {
        error(element, "Only classes can be annotated with @Harvest");
        return false; // Exit processing
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
      final String PACKAGE_NAME = AnnotationPackageFinder.findAnnotationPackage(processingEnv, Harvest.class, typeElement);

      final String ROOT_PACKAGE_NAME = buildRootPackageName(element,
                                                            DOMAIN_NAME_SINGULAR,
                                                            DOMAIN_NAME_PLURAL,
                                                            PACKAGE_NAME);
      processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "ROOT_PACKAGE_NAME: " + ROOT_PACKAGE_NAME);
      HarvestBO harvestBO = new HarvestBO(ROOT_PACKAGE_NAME, DOMAIN_NAME_SINGULAR, DOMAIN_NAME_PLURAL, PARENT_DOMAIN_NAME, DOMAIN_CONTEXT_PATH);
      processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "DOMAIN_NAME_SINGULAR: " + harvestBO.getDomainNameSingular());
      processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "DOMAIN_NAME_PLURAL: " + harvestBO.getDomainNamePlural());
      processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "PARENT_DOMAIN_NAME: " + harvestBO.getParentDomainName());
      processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "DOMAIN_CONTEXT_PATH: " + harvestBO.getDomainContextPath());

      // Pass the annotated class name to the generator methods
      CrudRepositoryGenerator.generate(harvestBO, processingEnv);
      MapperGenerator.generate(harvestBO, ROOT_PACKAGE_NAME, DOMAIN_NAME_SINGULAR, DOMAIN_NAME_PLURAL, entityTypeMirror, processingEnv);
      CrudServiceGenerator.generate(ROOT_PACKAGE_NAME, DOMAIN_NAME_SINGULAR, DOMAIN_NAME_PLURAL, processingEnv);
      ControllerConstantsGenerator.generate(harvestBO, ROOT_PACKAGE_NAME, DOMAIN_NAME_SINGULAR, DOMAIN_NAME_PLURAL, entityTypeMirror, processingEnv);
      CrudControllerGenerator.generate(ROOT_PACKAGE_NAME, DOMAIN_NAME_SINGULAR, DOMAIN_NAME_PLURAL, processingEnv);

    }

    return true;
  }


  private void error(Element element, String message, Object... args) {
    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format(message, args), element);
  }

  private String buildRootPackageName(Element element,
                                      final String DOMAIN_NAME_SINGULAR,
                                      final String DOMAIN_NAME_PLURAL,
                                      final String PACKAGE_NAME) {
    if (PACKAGE_NAME != null && !PACKAGE_NAME.isEmpty()) {
      processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Package of entity using Harvest annotation: " + PACKAGE_NAME);
    } else {
      processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to find package of entity using Harvest annotation", element);
    }

    StringBuilder sb = new StringBuilder();
    String[] packageParts = PACKAGE_NAME.split("\\.");
    if (packageParts.length == 0) {
      processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to find package of entity using Harvest annotation", element);
    } else {
      String prev = packageParts[0];
      sb.append(prev);
      for (int i = 1; i < packageParts.length; i++) {
        String curr = packageParts[i];

        if (!StringUtils.equalsIgnoreCase(curr, DOMAIN_NAME_SINGULAR)
            && !StringUtils.equalsIgnoreCase(curr, DOMAIN_NAME_PLURAL)) {
          sb.append(".");
          prev = curr;
          sb.append(curr);
        }

        if (StringUtils.equalsIgnoreCase(curr, DOMAIN_NAME_SINGULAR)
            || StringUtils.equalsIgnoreCase(curr, DOMAIN_NAME_PLURAL) || StringUtils.equalsIgnoreCase(curr, "domains")) {
          break;
        }
      }
    }
    return sb.toString();
  }

}
