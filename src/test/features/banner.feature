Feature: Banner

  Scenario: Should add banner JHipster v7
    When I apply legacy module "/api/servers/spring-boot/banners/jhipster-v7" to default project
    Then I should have files in "src/main/resources"
      | banner.txt |

  Scenario: Should add banner JHipster v7 for React
    When I apply legacy module "/api/servers/spring-boot/banners/jhipster-v7-react" to default project
    Then I should have files in "src/main/resources"
      | banner.txt |

  Scenario: Should add banner JHipster v7 for Vue
    When I apply legacy module "/api/servers/spring-boot/banners/jhipster-v7-vue" to default project
    Then I should have files in "src/main/resources"
      | banner.txt |

  Scenario: Should add banner JHipster v2
    When I apply legacy module "/api/servers/spring-boot/banners/jhipster-v2" to default project
    Then I should have files in "src/main/resources"
      | banner.txt |

  Scenario: Should add banner JHipster v3
    When I apply legacy module "/api/servers/spring-boot/banners/jhipster-v3" to default project
    Then I should have files in "src/main/resources"
      | banner.txt |

  Scenario: Should add banner Ippon
    When I apply legacy module "/api/servers/spring-boot/banners/ippon" to default project
    Then I should have files in "src/main/resources"
      | banner.txt |
