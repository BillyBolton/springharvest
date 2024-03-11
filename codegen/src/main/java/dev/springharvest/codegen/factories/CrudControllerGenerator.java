package dev.springharvest.codegen.factories;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import dev.springharvest.codegen.utils.JavaPoetUtils;
import dev.springharvest.errors.constants.ExceptionMessages;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;

public class CrudControllerGenerator {

  private CrudControllerGenerator() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }

  public static void generate(final String ROOT_PACKAGE_NAME,
                              final String DOMAIN_NAME_SINGULAR,
                              final String DOMAIN_NAME_PLURAL,
                              ProcessingEnvironment processingEnv) {

    // Define package
    String DOMAIN_PATH = String.format("%s.%s", DOMAIN_NAME_PLURAL.toLowerCase(), DOMAIN_NAME_SINGULAR.toLowerCase());
    String packageName = String.format("%s.%s.rest", ROOT_PACKAGE_NAME, DOMAIN_PATH);

    // Define imports
    ClassName iAuthorMapper = ClassName.get(String.format("%s.%s.mappers", ROOT_PACKAGE_NAME, DOMAIN_PATH), String.format("I%sMapper_", DOMAIN_NAME_SINGULAR));
    ClassName authorDTO = ClassName.get(String.format("%s.%s.models.dtos", ROOT_PACKAGE_NAME, DOMAIN_PATH), String.format("%sDTO", DOMAIN_NAME_SINGULAR));
    ClassName authorEntity = ClassName.get(String.format("%s.%s.models.entities", ROOT_PACKAGE_NAME, DOMAIN_PATH),
                                           String.format("%sEntity", DOMAIN_NAME_SINGULAR));
    ClassName authorConstants = ClassName.get(String.format("%s.%s.rest.constants", ROOT_PACKAGE_NAME, DOMAIN_PATH),
                                              String.format("%sConstants_", DOMAIN_NAME_SINGULAR));
    ClassName authorCrudService = ClassName.get(String.format("%s.%s.services", ROOT_PACKAGE_NAME, DOMAIN_PATH),
                                                String.format("%sCrudService_", DOMAIN_NAME_SINGULAR));
    ClassName uuid = ClassName.get("java.util", "UUID");

    ClassName abstractCrudController = ClassName.get("dev.springharvest.crud.domains.base.rest", "AbstractCrudController");
    ClassName tag = ClassName.get("io.swagger.v3.oas.annotations.tags", "Tag");
    ClassName requestMapping = ClassName.get("org.springframework.web.bind.annotation", "RequestMapping");
    ClassName restController = ClassName.get("org.springframework.web.bind.annotation", "RestController");
    ClassName slf4j = ClassName.get("lombok.extern.slf4j", "Slf4j");

    // Define class
    TypeSpec authorCrudControllerImpl = TypeSpec.classBuilder(String.format("%sCrudControllerImpl_", DOMAIN_NAME_SINGULAR))
        .addModifiers(Modifier.PUBLIC)
        .addAnnotation(slf4j)
        .addAnnotation(restController)
        .addAnnotation(AnnotationSpec.builder(tag)
                           .addMember("name", "$T.Controller.TAG", authorConstants)
                           .build())
        .addAnnotation(AnnotationSpec.builder(requestMapping)
                           .addMember("value", "$T.Controller.DOMAIN_CONTEXT", authorConstants)
                           .build())
        .superclass(ParameterizedTypeName.get(abstractCrudController, authorDTO, authorEntity, uuid))
        .addMethod(MethodSpec.constructorBuilder()
                       .addModifiers(Modifier.PROTECTED)
                       .addParameter(iAuthorMapper, "baseModelMapper")
                       .addParameter(authorCrudService, "baseService")
                       .addStatement("super($N, $N)", "baseModelMapper", "baseService")
                       .build())
        .build();

    // Define Java file
    JavaFile javaFile = JavaFile.builder(packageName, authorCrudControllerImpl)
        .build();
    JavaPoetUtils.writeJavaFile(javaFile, processingEnv);
  }

}
