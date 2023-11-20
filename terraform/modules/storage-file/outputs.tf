output "azurerm_storage_account_name" {
  value       = azurerm_storage_account.storage-file.name
  description = "The Azure Blob storage account name."
}

output "azurerm_storage_account_key" {
  value       = azurerm_storage_account.storage-file.primary_access_key
  sensitive   = true
  description = "The Azure Blob storage access key."
}

output "azurerm_storage_share_name" {
  value       = azurerm_storage_share.storage-file.name
  description = "The Azure Blob storage endpoint."
}
