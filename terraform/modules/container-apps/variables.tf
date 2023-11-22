variable "resource_group" {
  type        = string
  description = "The resource group"
}

variable "application_name" {
  type        = string
  description = "The name of your application"
}

variable "environment" {
  type        = string
  description = "The environment (dev, test, prod...)"
  default     = "dev"
}

variable "location" {
  type        = string
  description = "The Azure region where all resources in this example should be created"
}

variable "custom_domain_name" {
  type        = string
  description = "The domain name for Azure Container Apps"
}

variable "container_certificate" {
  type        = string
  sensitive   = true
  description = "The domain certificate for Azure Container Apps"
}

variable "container_certificate_password" {
  type        = string
  sensitive   = true
  description = "The password for the domain certificate for Azure Container Apps"
}

variable "docker_hub_username" {
  type        = string
  sensitive   = true
  description = "The Docker Hub username"
}

variable "docker_hub_password" {
  type        = string
  sensitive   = true
  description = "The Docker Hub password"
}
