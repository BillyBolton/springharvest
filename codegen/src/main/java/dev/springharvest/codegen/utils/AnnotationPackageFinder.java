package dev.springharvest.codegen.utils;

import dev.springharvest.errors.constants.ExceptionMessages;
import java.lang.annotation.Annotation;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class AnnotationPackageFinder {

  private AnnotationPackageFinder() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }

  public static String findAnnotationPackage(ProcessingEnvironment processingEnv, Class<? extends Annotation> annotationClass, Element annotatedElement) {
    // Ensure the annotated element is a class
    if (!(annotatedElement instanceof TypeElement)) {
      throw new IllegalArgumentException("Annotated element must be a class");
    }

    // Get the package of the annotated class
    String packageName = null;
    try {
      TypeElement annotatedClass = (TypeElement) annotatedElement;
      processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Entity package name TYPE_ELEMENT: " + annotatedClass);
      PackageElement packageElement = processingEnv.getElementUtils().getPackageOf(annotatedClass);
      processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Entity package name PACKAGE_ELEMENT: " + packageElement);
      packageName = packageElement.getQualifiedName().toString();
      processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                                               "Entity package name PACKAGE_ELEMENT_QUALIFIED_NAME: " + packageElement.getQualifiedName());
    } catch (Exception e) {
      processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to find package of entity using Harvest annotation", annotatedElement);
      e.printStackTrace();
    }
    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Entity package name: " + packageName);
    return packageName;
  }
}
