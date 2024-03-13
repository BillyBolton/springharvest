package dev.springharvest.codegen.factories;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import dev.springharvest.codegen.models.HarvestBO;
import dev.springharvest.codegen.utils.JavaPoetUtils;
import dev.springharvest.errors.constants.ExceptionMessages;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;

public class CrudRepositoryGenerator {

  private CrudRepositoryGenerator() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }


  public static void generate(HarvestBO harvestBO,
                              ProcessingEnvironment processingEnv) {

    // Create interface specification
    TypeSpec interfaceSpec = TypeSpec.interfaceBuilder(harvestBO.getCrudRepositoryName())
        .addModifiers(Modifier.PUBLIC)
        .addSuperinterface(ParameterizedTypeName.get(
            ClassName.get("dev.springharvest.crud.domains.base.persistence", "ICrudRepository"),
            ClassName.get(harvestBO.getEntityPackageName(), harvestBO.getEntityName()),
            ClassName.get("java.util", "UUID")))
        .addAnnotation(AnnotationSpec.builder(ClassName.get("org.springframework.stereotype", "Repository")).build())
        .build();

    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "CRUD REPOSITORY PACKAGE NAME: " + harvestBO.getCrudRepositoryPackageName());
    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "CRUD REPOSITORY NAME: " + harvestBO.getCrudRepositoryName());
    // Create Java file
    JavaFile javaFile = JavaFile.builder(harvestBO.getCrudRepositoryPackageName(), interfaceSpec)
        .build();
    JavaPoetUtils.writeJavaFile(javaFile, processingEnv);
  }

}
