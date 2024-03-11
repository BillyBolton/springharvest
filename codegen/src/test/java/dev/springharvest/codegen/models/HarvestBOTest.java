package dev.springharvest.codegen.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.squareup.javapoet.ClassName;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Sonarcloud debugging")
class HarvestBOTest {

  @Test
  void shouldThrowExceptionWhenRootPackageNameIsNull() {
    assertThrows(IllegalArgumentException.class, () -> new HarvestBO(null, "User", "Users", "ParentUser", "/users"));
  }

  @Test
  void shouldThrowExceptionWhenDomainNameSingularIsNull() {
    assertThrows(IllegalArgumentException.class, () -> new HarvestBO("com.example", null, "Users", "ParentUser", "/users"));
  }

  @Test
  void shouldThrowExceptionWhenDomainNamePluralIsNull() {
    assertThrows(IllegalArgumentException.class, () -> new HarvestBO("com.example", "User", null, "ParentUser", "/users"));
  }

  @Test
  void shouldThrowExceptionWhenParentDomainNameIsNull() {
    assertThrows(IllegalArgumentException.class, () -> new HarvestBO("com.example", "User", "Users", null, "/users"));
  }

  @Test
  void shouldThrowExceptionWhenDomainContextPathIsNull() {
    assertThrows(IllegalArgumentException.class, () -> new HarvestBO("com.example", "User", "Users", "ParentUser", null));
  }

  @Test
  void shouldGenerateCorrectDtoTypeName() {
    HarvestBO harvestBO = new HarvestBO("com.example", "User", "Users", "ParentUser", "/users");
    assertEquals(ClassName.get("com.example.users.user.models.dtos", "UserDTO"), harvestBO.getDtoTypeName());
  }

  @Test
  void shouldGenerateCorrectEntityTypeName() {
    HarvestBO harvestBO = new HarvestBO("com.example", "User", "Users", "ParentUser", "/users");
    assertEquals(ClassName.get("com.example.users.user.models.entities", "UserEntity"), harvestBO.getEntityTypeName());
  }

  @Test
  void shouldGenerateCorrectDtoPackageNameWhenRootPackageNameIsEmpty() {
    HarvestBO harvestBO = new HarvestBO("", "User", "Users", "ParentUser", "/users");
    assertEquals(".users.user.models.dtos", harvestBO.getDtoPackageName());
  }

  @Test
  void shouldGenerateCorrectGettersForConstructorParametersValidConstructorIsUsed() {
    HarvestBO harvestBO = new HarvestBO("com.example", "User", "Users", "Users", "/users");
    assertEquals("com.example", harvestBO.getRootPackageName());
    assertEquals("User", harvestBO.getDomainNameSingular());
    assertEquals("Users", harvestBO.getDomainNamePlural());
    assertEquals("Users", harvestBO.getParentDomainName());
    assertEquals("/users", harvestBO.getDomainContextPath());
  }

  @Test
  void shouldGenerateCorrectEntityPackageNameWhenRootPackageNameIsEmpty() {
    HarvestBO harvestBO = new HarvestBO("", "User", "Users", "ParentUser", "/users");
    assertEquals(".users.user.models.dtos", harvestBO.getDtoPackageName());
  }

  @Test
  void shouldGenerateCorrectDtoPackageNameWhenRootPackageNameIsNull() {
    assertThrows(IllegalArgumentException.class, () -> new HarvestBO(null, "User", "Users", "ParentUser", "/users"));
  }

  @Test
  void shouldGenerateCorrectEntityPackageNameWhenRootPackageNameIsNull() {
    assertThrows(IllegalArgumentException.class, () -> new HarvestBO(null, "User", "Users", "ParentUser", "/users"));
  }

  @Test
  void shouldCorrectlyCheckEqualityWithoutBuilders() {
    HarvestBO harvestBO1 = new HarvestBO("com.example", "User", "Users", "ParentUser", "/users");
    HarvestBO harvestBO2 = new HarvestBO("com.example", "User", "Users", "ParentUser", "/users");

    assertEquals(harvestBO1, harvestBO2);
  }

  @Test
  void shouldCorrectlyCheckInequalityWithoutBuilders() {
    HarvestBO harvestBO1 = new HarvestBO("com.example", "User", "Users", "ParentUser", "/users");
    HarvestBO harvestBO2 = new HarvestBO("com.example", "User", "Users", "ParentUser", "/users2");

    assertNotEquals(harvestBO1, harvestBO2);
  }

}