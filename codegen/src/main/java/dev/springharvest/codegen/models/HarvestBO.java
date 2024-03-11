package dev.springharvest.codegen.models;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class HarvestBO {

  private final String rootPackageName;
  private final String domainNameSingular;
  private final String domainNamePlural;
  private final String parentDomainName;
  private final String domainContextPath;
  private final String crudRepositoryPackageName;
  private final TypeName dtoTypeName;
  private final String dtoPackageName;
  private final String dtoName;
  private final TypeName entityTypeName;
  private final String entityName;
  private final String entityPackageName;
  private final String mapperPackageName;
  private final String crudRepositoryName;

  public HarvestBO(String rootPackageName, String domainNameSingular, String domainNamePlural, String parentDomainName, String domainContextPath) {
    if (rootPackageName == null) {
      throw new IllegalArgumentException("Root package name cannot be null");
    }
    if (domainNameSingular == null) {
      throw new IllegalArgumentException("Domain name singular cannot be null");
    }
    if (domainNamePlural == null) {
      throw new IllegalArgumentException("Domain name plural cannot be null");
    }
    if (parentDomainName == null) {
      throw new IllegalArgumentException("Parent domain name cannot be null");
    }
    if (domainContextPath == null) {
      throw new IllegalArgumentException("Domain context path cannot be null");
    }
    this.rootPackageName = rootPackageName;
    this.domainNameSingular = domainNameSingular;
    this.domainNamePlural = domainNamePlural;
    this.parentDomainName = parentDomainName;
    this.domainContextPath = domainContextPath;

    // DTO
    this.dtoPackageName = getPackageName("models.dtos");
    this.dtoName = String.format("%sDTO", domainNameSingular);
    this.dtoTypeName = ClassName.get(getDtoPackageName(), getDtoName());

    // Entity
    this.entityName = String.format("%sEntity", domainNameSingular);
    this.entityPackageName = getPackageName("models.entities");
    this.entityTypeName = ClassName.get(getEntityPackageName(), getEntityName());

    // Mapper
    this.mapperPackageName = getPackageName("mappers");

    // Crud Repository
    this.crudRepositoryPackageName = getPackageName("persistence");
    this.crudRepositoryName = String.format("I%sCrudRepository_", domainNameSingular);


  }

  private String getPackageName(String subPackage) {
    return String.format("%s.%s.%s.%s", rootPackageName, domainNamePlural.toLowerCase(), domainNameSingular.toLowerCase(), subPackage);
  }

}
