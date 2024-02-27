package dev.springharvest.codegen.generators;

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

  public static void generate(ProcessingEnvironment processingEnv) {
    // Define package
    String packageName = "dev.springharvest.library.domains.authors.author.rest";

    // Define imports
    ClassName iAuthorMapper = ClassName.get("dev.springharvest.library.domains.authors.author.mappers", "IAuthorMapper_");
    ClassName authorDTO = ClassName.get("dev.springharvest.library.domains.authors.author.models.dtos", "AuthorDTO");
    ClassName authorEntity = ClassName.get("dev.springharvest.library.domains.authors.author.models.entities", "AuthorEntity");
    ClassName authorConstants = ClassName.get("dev.springharvest.library.domains.authors.author.rest.constants", "AuthorConstants_");
    ClassName authorCrudService = ClassName.get("dev.springharvest.library.domains.authors.author.services", "AuthorCrudService");
    ClassName abstractCrudController = ClassName.get("dev.springharvest.crud.domains.base.rest", "AbstractCrudController");
    ClassName tag = ClassName.get("io.swagger.v3.oas.annotations.tags", "Tag");
    ClassName requestMapping = ClassName.get("org.springframework.web.bind.annotation", "RequestMapping");
    ClassName restController = ClassName.get("org.springframework.web.bind.annotation", "RestController");
    ClassName slf4j = ClassName.get("lombok.extern.slf4j", "Slf4j");
    ClassName uuid = ClassName.get("java.util", "UUID");

    // Define class
    TypeSpec authorCrudControllerImpl = TypeSpec.classBuilder("AuthorCrudControllerImpl_")
        .addModifiers(Modifier.PUBLIC)
        .addAnnotation(slf4j)
        .addAnnotation(restController)
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