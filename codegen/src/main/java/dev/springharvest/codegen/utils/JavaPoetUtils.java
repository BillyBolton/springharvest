package dev.springharvest.codegen.utils;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import dev.springharvest.errors.constants.ExceptionMessages;
import java.util.Arrays;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;

public class JavaPoetUtils {

  private JavaPoetUtils() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }

  public static void writeJavaFile(final JavaFile javaFile, ProcessingEnvironment processingEnv) {
    try {
      javaFile.writeTo(processingEnv.getFiler());
    } catch (Exception e) {
      // TODO
      processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to write Java file: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static MethodSpec buildPrivateConstructorMethod() {
    return MethodSpec.constructorBuilder()
        .addModifiers(Modifier.PRIVATE)
        .addStatement("throw new $T($T.PRIVATE_CONSTRUCTOR_MESSAGE)", UnsupportedOperationException.class, ExceptionMessages.class)
        .build();
  }

  public static TypeSpec generateClassDefinition(String className,
                                                 Modifier modifier,
                                                 TypeName superClass,
                                                 MethodSpec constructor,
                                                 AnnotationSpec... annotations) {

    TypeSpec.Builder classDef = TypeSpec.classBuilder(className)
        .addModifiers(modifier)
        .superclass(superClass)
        .addMethod(constructor);
    Arrays.stream(annotations).toList().forEach(classDef::addAnnotation);

    return classDef.build();
  }

}
