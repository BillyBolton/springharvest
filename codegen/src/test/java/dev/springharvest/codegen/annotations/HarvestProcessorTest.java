package dev.springharvest.codegen.annotations;

import static com.google.common.truth.Truth.assert_;

import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;
import java.util.List;
import javax.tools.JavaFileObject;
import org.junit.jupiter.api.Test;

class HarvestProcessorTest {

  @Test
  void testHarvestProcessor() {

    JavaFileObject userEntityFile = JavaFileObjects.forSourceString("test.users.user.models.entities.UserEntity",
                                                                    "package test.users.user.models.entities;\n" +
                                                                    "\n" +
                                                                    "import java.util.UUID;\n" +
                                                                    "import dev.springharvest.shared.domains.base.models.entities.BaseEntity;\n" +
                                                                    "import dev.springharvest.codegen.annotations.Harvest;\n" +
                                                                    "\n" +
                                                                    "@Harvest(domainNameSingular = \"User\", domainNamePlural = \"Users\", parentDomainName " +
                                                                    "= " +
                                                                    "\"Users\", domainContextPath = \"/users\")\n" +
                                                                    "public class UserEntity extends BaseEntity<UUID> {\n" +
                                                                    "}");

    JavaFileObject userDtoFile = JavaFileObjects.forSourceString("test.users.user.models.dtos.UserDTO",
                                                                 "package test.users.user.models.dtos;\n" +
                                                                 "import java.util.UUID;\n" +
                                                                 "import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;\n" +
                                                                 "\n" +
                                                                 "public class UserDTO extends BaseDTO<UUID> {\n" +
                                                                 "}");

    assert_().about(JavaSourcesSubjectFactory.javaSources())
        .that(List.of(userEntityFile, userDtoFile))
        .processedWith(new HarvestProcessor())
        .compilesWithoutError();
  }
}