package dev.springharvest.codegen.factories;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import dev.springharvest.codegen.models.HarvestBO;
import dev.springharvest.codegen.utils.JavaPoetUtils;
import dev.springharvest.errors.constants.ExceptionMessages;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import org.apache.commons.lang3.StringUtils;

public class ControllerConstantsGenerator {

  private ControllerConstantsGenerator() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }

  public static void generate(HarvestBO harvestBO,
                              final String ROOT_PACKAGE_NAME,
                              final String DOMAIN_NAME_SINGULAR,
                              final String DOMAIN_NAME_PLURAL,
                              TypeMirror typeMirror,
                              ProcessingEnvironment processingEnv) {

//    final String ROOT_PACKAGE_NAME = "dev.springharvest.bookscatalogue.domains";

    boolean hasParentDomainConstants =
        StringUtils.isNotBlank(DOMAIN_NAME_PLURAL) && StringUtils.isNotBlank(DOMAIN_NAME_PLURAL) && DOMAIN_NAME_PLURAL.equalsIgnoreCase(
            DOMAIN_NAME_PLURAL);
    String parentPackageName = null;
    String parentClassName = null;
    String tagValue = null;
    String domainContextValue = null;

    if (hasParentDomainConstants) {
      parentPackageName = ROOT_PACKAGE_NAME + "." + DOMAIN_NAME_PLURAL.toLowerCase() + ".constants";
      parentClassName = DOMAIN_NAME_PLURAL + "Constants_";
      tagValue = DOMAIN_NAME_PLURAL;
      domainContextValue = harvestBO.getDomainContextPath() + "/" + DOMAIN_NAME_PLURAL.toLowerCase();

      FieldSpec tagField = FieldSpec.builder(String.class, "TAG", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
          .initializer("$S", tagValue)
          .build();

      FieldSpec domainContextField = FieldSpec.builder(String.class, "DOMAIN_CONTEXT", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
          .initializer("$S", domainContextValue)
          .build();

      TypeSpec constantsFile = TypeSpec.classBuilder(parentClassName)
          .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
          .addMethod(JavaPoetUtils.buildPrivateConstructorMethod())
          .addType(TypeSpec.classBuilder("Controller")
                       .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                       .addField(tagField)
                       .addField(domainContextField)
                       .addMethod(JavaPoetUtils.buildPrivateConstructorMethod())
                       .build())
          .build();

      JavaFile javaFile = JavaFile.builder(parentPackageName, constantsFile)
          .build();

      JavaPoetUtils.writeJavaFile(javaFile, processingEnv);
    }

    final String PACKAGE_NAME = ROOT_PACKAGE_NAME + "." + DOMAIN_NAME_PLURAL.toLowerCase() + "." + DOMAIN_NAME_SINGULAR.toLowerCase() + ".rest.constants";
    final String CLASS_NAME = DOMAIN_NAME_SINGULAR + "Constants_";

    FieldSpec tagField = FieldSpec.builder(String.class, "TAG", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
        .initializer("$S", DOMAIN_NAME_PLURAL)
        .build();

    FieldSpec domainContextField = FieldSpec.builder(String.class, "DOMAIN_CONTEXT", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
        .initializer("$S", harvestBO.getDomainContextPath() + "/" + DOMAIN_NAME_PLURAL.toLowerCase())
        .build();

    TypeSpec constantsFile = TypeSpec.classBuilder(CLASS_NAME)
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        .addMethod(JavaPoetUtils.buildPrivateConstructorMethod())
        .addType(TypeSpec.classBuilder("Controller")
                     .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                     .addField(tagField)
                     .addField(domainContextField)
                     .addMethod(JavaPoetUtils.buildPrivateConstructorMethod())
                     .build())
        .build();

    JavaFile javaFile = JavaFile.builder(PACKAGE_NAME, constantsFile).build();
    JavaPoetUtils.writeJavaFile(javaFile, processingEnv);
  }


}
