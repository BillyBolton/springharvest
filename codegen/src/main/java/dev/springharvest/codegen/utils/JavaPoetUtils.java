package dev.springharvest.codegen.utils;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import dev.springharvest.errors.constants.ExceptionMessages;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;

public class JavaPoetUtils {

  private JavaPoetUtils() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }

  public static void writeJavaFile(final JavaFile javaFile, ProcessingEnvironment processingEnv) {
    try {
      javaFile.writeTo(processingEnv.getFiler());
    } catch (Exception e) {
      // TODO
      e.printStackTrace();
    }
  }

  public static MethodSpec buildPrivateConstructorMethod() {
    return MethodSpec.constructorBuilder()
        .addModifiers(Modifier.PRIVATE)
        .addStatement("throw new $T($T.PRIVATE_CONSTRUCTOR_MESSAGE)", UnsupportedOperationException.class, ExceptionMessages.class)
        .build();
  }

}