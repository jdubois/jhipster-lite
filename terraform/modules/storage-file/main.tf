terraform {
  required_providers {
    azurecaf = {
      source  = "aztfmod/azurecaf"
      version = "1.2.16"
    }
  }
}

resource "azurecaf_name" "storage_account" {
  name          = var.application_name
  resource_type = "azurerm_storage_account"
  suffixes      = [var.environment]
}

resource "azurerm_storage_account" "storage-file" {
  name                            = azurecaf_name.storage_account.result
  resource_group_name             = var.resource_group
  location                        = var.location
  account_tier                    = "Standard"
  account_replication_type        = "LRS"
  allow_nested_items_to_be_public = false

  tags = {
    "environment"      = var.environment
    "application-name" = var.application_name
  }
}

resource "azurerm_storage_share" "storage-file" {
  name                 = "user-projects"
  storage_account_name = azurerm_storage_account.storage-file.name
  quota                = 50
}

resource "azurerm_storage_share_directory" "storage-file" {
  name                 = "jhipster-lite"
  share_name           = azurerm_storage_share.storage-file.name
  storage_account_name = azurerm_storage_account.storage-file.name
}
