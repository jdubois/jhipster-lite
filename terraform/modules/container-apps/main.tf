terraform {
  required_providers {
    azurecaf = {
      source  = "aztfmod/azurecaf"
      version = "1.2.26"
    }
  }
}

resource "azurecaf_name" "log_analytics_workspace" {
  name          = var.application_name
  resource_type = "azurerm_log_analytics_workspace"
  suffixes      = [var.environment]
}

resource "azurerm_log_analytics_workspace" "application" {
  name                = azurecaf_name.log_analytics_workspace.result
  resource_group_name = var.resource_group
  location            = var.location
  sku                 = "PerGB2018"
  retention_in_days   = 30
}

resource "azurecaf_name" "application-environment" {
  name          = var.application_name
  resource_type = "azurerm_container_app_environment"
  suffixes      = [var.environment]
}

resource "azurerm_container_app_environment" "application" {
  name                       = azurecaf_name.application-environment.result
  resource_group_name        = var.resource_group
  location                   = var.location
  log_analytics_workspace_id = azurerm_log_analytics_workspace.application.id
}

resource "azurecaf_name" "application" {
  name          = var.application_name
  resource_type = "azurerm_container_app"
  suffixes      = [var.environment]
}

resource "azurerm_container_app_environment_certificate" "application" {
  name                         = "jhipster-lite-container-certificate"
  container_app_environment_id = azurerm_container_app_environment.application.id
  certificate_blob_base64      = var.container_certificate
  certificate_password         = var.container_certificate_password
}

resource "azurerm_container_app" "application" {
  name                         = azurecaf_name.application.result
  container_app_environment_id = azurerm_container_app_environment.application.id
  resource_group_name          = var.resource_group
  revision_mode                = "Single"

  lifecycle {
    ignore_changes = [
      template.0.container["image"]
    ]
  }

  ingress {
    external_enabled = true
    target_port      = 7471
    traffic_weight {
      percentage = 100
      latest_revision = true
    }
    custom_domain {
      name           = var.custom_domain_name
      certificate_id = azurerm_container_app_environment_certificate.application.id
    }
  }

  template {
    container {
      name   = azurecaf_name.application.result
      image  = "jdubois/jhipster-lite/jhipster-lite-native:latest"
      cpu    = 0.25
      memory = "0.5Gi"
    }
    min_replicas = 1
  }
}
