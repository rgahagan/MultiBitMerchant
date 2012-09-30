package org.multibit.mbm.resources;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.multibit.mbm.api.hal.HalMediaType;
import org.multibit.mbm.db.dto.Role;
import org.multibit.mbm.db.dto.RoleBuilder;
import org.multibit.mbm.db.dto.User;
import org.multibit.mbm.test.BaseJerseyResourceTest;
import org.multibit.mbm.test.FixtureAsserts;

public class AdminUserResourceTest extends BaseJerseyResourceTest {

  private final AdminUserResource testObject=new AdminUserResource();

  @Override
  protected void setUpResources() {

    // Create the admin Role
    Role adminRole = RoleBuilder.newInstance()
      .withAdminAuthorities()
      .withName("Administrator")
      .withDescription("Administrator role")
      .build();

    // Create the User for authenticated access
    User adminUser = setUpAuthenticator(Lists.newArrayList(adminRole));
    adminUser.setId(1L);

    // Configure resources
    addResource(testObject);

  }

  @Test
  public void testGetAllByPage() throws Exception {

    String actualResponse = client()
      .resource("/admin/user")
      .accept(HalMediaType.APPLICATION_HAL_JSON)
      .get(String.class);

    FixtureAsserts.assertStringMatchesJsonFixture("User list can be retrieved as JSON", actualResponse, "fixtures/hal/user/expected-users-by-admin-page-1.json");

  }

}