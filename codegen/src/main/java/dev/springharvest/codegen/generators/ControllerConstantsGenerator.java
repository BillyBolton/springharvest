package dev.springharvest.codegen.generators;


import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import dev.springharvest.codegen.utils.JavaPoetUtils;
import dev.springharvest.errors.constants.ExceptionMessages;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import org.apache.commons.lang3.StringUtils;

public class ControllerConstantsGenerator {

  private ControllerConstantsGenerator() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }

  public static void generate(final String DOMAIN_NAME_SINGULAR,
                              final String DOMAIN_NAME_PLURAL,
                              final String PARENT_DOMAIN_NAME_PLURAL,
                              final String DOMAIN_CONTEXT_PATH,
                              ProcessingEnvironment processingEnv) {

    final String ROOT_PACKAGE_NAME = "dev.springharvest.library.domains";

    boolean hasParentDomainConstants =
        StringUtils.isNotBlank(DOMAIN_NAME_PLURAL) && StringUtils.isNotBlank(PARENT_DOMAIN_NAME_PLURAL) && DOMAIN_NAME_PLURAL.equalsIgnoreCase(
            PARENT_DOMAIN_NAME_PLURAL);
    String parentPackageName = null;
    String parentClassName = null;
    String tagValue = null;
    String domainContextValue = null;

    if (hasParentDomainConstants) {
      parentPackageName = ROOT_PACKAGE_NAME + "." + PARENT_DOMAIN_NAME_PLURAL.toLowerCase() + ".constants";
      parentClassName = PARENT_DOMAIN_NAME_PLURAL + "Constants_";
      tagValue = PARENT_DOMAIN_NAME_PLURAL;
      domainContextValue = DOMAIN_CONTEXT_PATH + "/" + PARENT_DOMAIN_NAME_PLURAL.toLowerCase();

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
        .initializer("$S", DOMAIN_CONTEXT_PATH + "/" + DOMAIN_NAME_PLURAL.toLowerCase())
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
