package dev.springharvest.codegen.generators;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import dev.springharvest.codegen.utils.JavaPoetUtils;
import dev.springharvest.errors.constants.ExceptionMessages;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

public class MapperGenerator {

  private MapperGenerator() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }

  public static void generate(final String DOMAIN_NAME_SINGULAR,
                              final String DOMAIN_NAME_PLURAL,
                              TypeMirror typeMirror,
                              ProcessingEnvironment processingEnv) {

    Types typeUtils = processingEnv.getTypeUtils();

    String basePackage = String.format("dev.springharvest.library.domains.%s.%s", DOMAIN_NAME_PLURAL.toLowerCase(), DOMAIN_NAME_SINGULAR.toLowerCase());
    String packageName = basePackage + ".mappers";
    String className = String.format("I%sMapper_", DOMAIN_NAME_SINGULAR);
    String dtoPackage = basePackage + ".models.dtos";
    String entityPackage = basePackage + ".models.entities";
    String dtoClassName = String.format("%sDTO", DOMAIN_NAME_SINGULAR);
    String entityClassName = String.format("%sEntity", DOMAIN_NAME_SINGULAR);

    TypeName authorDtoType = ClassName.get(dtoPackage, dtoClassName);
    TypeName authorEntityType = ClassName.get(entityPackage, entityClassName);
    TypeName uuidType = ClassName.get("java.util", "UUID");
    TypeName mapType = ParameterizedTypeName.get(ClassName.get("java.util", "Map"), ClassName.get(String.class), ClassName.get(String.class));
    ClassName cyclicMappingHandlerType = ClassName.get("dev.springharvest.shared.domains.base.mappers", "CyclicMappingHandler");
    ClassName iBaseModelMapperType = ClassName.get("dev.springharvest.shared.domains.base.mappers", "IBaseModelMapper");
    ClassName mapStructBuilderType = ClassName.get("org.mapstruct", "Builder");
    ClassName nullValuePropertyMappingStrategyType = ClassName.get("org.mapstruct", "NullValuePropertyMappingStrategy");

    List<Element> enclosedElements = new ArrayList<>();
    TypeElement typeElement = (TypeElement) typeUtils.asElement(typeMirror);

    // Traverse up the hierarchy of superclasses to gather fields
    while (typeElement != null) {
      enclosedElements.addAll(typeElement.getEnclosedElements());
      TypeMirror superclass = typeElement.getSuperclass();
      if (superclass instanceof DeclaredType) {
        typeElement = (TypeElement) ((DeclaredType) superclass).asElement();
      } else {
        break;
      }
    }

    // Create method toDto
    MethodSpec.Builder toDtoMethodBuilder = MethodSpec.methodBuilder("toDto")
        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
        .returns(authorDtoType)
        .addParameter(mapType, "source")
        .addParameter(ParameterSpec.builder(cyclicMappingHandlerType, "context").addAnnotation(ClassName.get("org.mapstruct", "Context")).build())
        .addAnnotation(Override.class);

    // Create method toEntity
    MethodSpec.Builder toEntityMethodBuilder = MethodSpec.methodBuilder("toEntity")
        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
        .returns(authorEntityType)
        .addParameter(mapType, "source")
        .addParameter(ParameterSpec.builder(cyclicMappingHandlerType, "context").addAnnotation(ClassName.get("org.mapstruct", "Context")).build())
        .addAnnotation(Override.class);

    // Create method setDirtyFields
    MethodSpec.Builder setDirtyFieldsMethodBuilder = MethodSpec.methodBuilder("setDirtyFields")
        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
        .returns(authorDtoType)
        .addParameter(ParameterSpec.builder(authorDtoType, "source").build())
        .addParameter(ParameterSpec.builder(authorDtoType, "target").addAnnotation(ClassName.get("org.mapstruct", "MappingTarget")).build())
        .addParameter(ParameterSpec.builder(cyclicMappingHandlerType, "context").addAnnotation(ClassName.get("org.mapstruct", "Context")).build())
        .addAnnotation(Override.class);

    for (Element enclosedElement : enclosedElements) {
      if (enclosedElement.getKind() == ElementKind.FIELD) {
        String fieldName = enclosedElement.getSimpleName().toString();
        TypeName fieldType = TypeName.get(enclosedElement.asType());

        // Check if the field type is non-primitive
        if (!fieldType.isBoxedPrimitive() && !fieldType.equals(TypeName.get(String.class))) {
          toDtoMethodBuilder.addAnnotation(AnnotationSpec.builder(ClassName.get("org.mapstruct", "Mapping"))
                                               .addMember("target", "$S", fieldName)
                                               .addMember("source", "$S", ".")
                                               .build());

          toEntityMethodBuilder.addAnnotation(AnnotationSpec.builder(ClassName.get("org.mapstruct", "Mapping"))
                                                  .addMember("target", "$S", fieldName)
                                                  .addMember("source", "$S", ".")
                                                  .build());
        }

        setDirtyFieldsMethodBuilder.addAnnotation(AnnotationSpec.builder(ClassName.get("org.mapstruct", "Mapping"))
                                                      .addMember("target", "$S", fieldName)
                                                      .addMember("nullValuePropertyMappingStrategy", "$T.IGNORE", nullValuePropertyMappingStrategyType)
                                                      .build());
      }
    }

    MethodSpec toDtoMethod = toDtoMethodBuilder.build();
    MethodSpec toEntityMethod = toEntityMethodBuilder.build();
    MethodSpec setDirtyFieldsMethod = setDirtyFieldsMethodBuilder.build();

    // Create IAuthorMapper interface
    TypeSpec mapperInterface = TypeSpec.interfaceBuilder(className)
        .addModifiers(Modifier.PUBLIC)
        .addSuperinterface(ParameterizedTypeName.get(iBaseModelMapperType, authorDtoType, authorEntityType, uuidType))
        .addAnnotation(AnnotationSpec.builder(ClassName.get("org.mapstruct", "Mapper"))
                           .addMember("componentModel", "$S", "spring")
                           .addMember("builder", "@$T(disableBuilder = true)", mapStructBuilderType)
                           .addMember("uses",
                                      "{$T.class}",
                                      ClassName.get("dev.springharvest.shared.domains.embeddables.traces.trace.mappers", "UUIDTraceDataMapper"))
                           .addMember("implementationName", "$S", String.format("I%sMapperImpl_", DOMAIN_NAME_SINGULAR))
                           .build())
        .addMethod(toDtoMethod)
        .addMethod(toEntityMethod)
        .addMethod(setDirtyFieldsMethod)
        .build();

    // Create Java file
    JavaFile javaFile = JavaFile.builder(packageName, mapperInterface)
        .addStaticImport(Map.class, "*")
        .addStaticImport(UUID.class, "*")
        .addStaticImport(org.mapstruct.Mapping.class, "*")
        .addStaticImport(org.mapstruct.MappingTarget.class, "*")
        .addStaticImport(org.mapstruct.Context.class, "*")
        .addStaticImport(org.mapstruct.NullValuePropertyMappingStrategy.class, "*")
        .build();
    JavaPoetUtils.writeJavaFile(javaFile, processingEnv);
  }

}
