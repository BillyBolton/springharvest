package dev.springharvest.codegen.generators;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import dev.springharvest.codegen.utils.JavaPoetUtils;
import dev.springharvest.errors.constants.ExceptionMessages;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;

public class CrudRepositoryGenerator {

  private CrudRepositoryGenerator() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }


  public static void generate(final String DOMAIN_NAME_SINGULAR,
                              final String DOMAIN_NAME_PLURAL,
                              ProcessingEnvironment processingEnv) {

    // Define package and interface names
    String packageName = String.format("dev.springharvest.library.domains.%s.%s.persistence",
                                       DOMAIN_NAME_PLURAL.toLowerCase(),
                                       DOMAIN_NAME_SINGULAR.toLowerCase());
    String interfaceName = String.format("I%sCrudRepository_", DOMAIN_NAME_SINGULAR);

    // Create interface specification
    TypeSpec interfaceSpec = TypeSpec.interfaceBuilder(interfaceName)
        .addModifiers(Modifier.PUBLIC)
        .addSuperinterface(ParameterizedTypeName.get(
            ClassName.get("dev.springharvest.crud.domains.base.persistence", "ICrudRepository"),
            ClassName.get("dev.springharvest.library.domains.authors.author.models.entities", String.format("%sEntity", DOMAIN_NAME_SINGULAR)),
            ClassName.get("java.util", "UUID")))
        .addAnnotation(AnnotationSpec.builder(ClassName.get("org.springframework.stereotype", "Repository")).build())
        .build();

    // Create Java file
    JavaFile javaFile = JavaFile.builder(packageName, interfaceSpec)
        .build();
    JavaPoetUtils.writeJavaFile(javaFile, processingEnv);
  }

}

