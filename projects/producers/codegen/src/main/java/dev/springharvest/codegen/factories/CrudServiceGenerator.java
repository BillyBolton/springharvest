package dev.springharvest.codegen.factories;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import dev.springharvest.codegen.utils.JavaPoetUtils;
import dev.springharvest.errors.constants.ExceptionMessages;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;

public class CrudServiceGenerator {

  private CrudServiceGenerator() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }


  public static void generate(final String ROOT_PACKAGE_NAME,
                              final String DOMAIN_NAME_SINGULAR,
                              final String DOMAIN_NAME_PLURAL,
                              ProcessingEnvironment processingEnv) {

    final String DOMAIN_PATH = String.format("%s.%s", DOMAIN_NAME_PLURAL.toLowerCase(), DOMAIN_NAME_SINGULAR.toLowerCase());

    // Define package and class names
    String packageName = String.format("%s.%s.services", ROOT_PACKAGE_NAME, DOMAIN_PATH);
    String className = String.format("%sCrudService_", DOMAIN_NAME_SINGULAR);

    // Create base class parameterized type
    TypeName abstractCrudServiceType = ParameterizedTypeName.get(
        ClassName.get("dev.springharvest.crud.domains.base.services", "AbstractCrudService"),
        ClassName.get(String.format("%s.%s.models.entities", ROOT_PACKAGE_NAME, DOMAIN_PATH),
                      String.format("%sEntity", DOMAIN_NAME_SINGULAR)),
        ClassName.get("java.util", "UUID")
                                                                );

    MethodSpec constructor = MethodSpec.constructorBuilder()
        .addModifiers(Modifier.PROTECTED)
        .addParameter(ClassName.get(String.format("%s.%s.persistence", ROOT_PACKAGE_NAME, DOMAIN_PATH),
                                    String.format("I%sCrudRepository_", DOMAIN_NAME_SINGULAR)), "baseRepository")
        .addStatement("super(baseRepository)")
        .build();

    // Generate class definition
    TypeSpec service = TypeSpec.classBuilder(className)
        .addModifiers(Modifier.PUBLIC)
        .superclass(abstractCrudServiceType)
        .addAnnotation(AnnotationSpec.builder(ClassName.get("org.springframework.stereotype", "Service")).build())
        .addMethod(constructor)
        .build();

    // Generate Java file
    JavaFile javaFile = JavaFile.builder(packageName, service)
        .build();
    JavaPoetUtils.writeJavaFile(javaFile, processingEnv);
  }

}
