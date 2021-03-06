/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.sql.ImportExportOperationMode;
import com.microsoft.azure.management.sql.StorageKeyType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Import Azure SQL Database parameters.
 */
@JsonFlatten
public class ImportExtensionRequestParametersInner {
    /**
     * The name of the extenstion.
     */
    private String name;

    /**
     * The type of the extenstion.
     */
    private String type;

    /**
     * The type of Import/Export opertion being performed. Possible values
     * include: 'Import', 'Export'.
     */
    @JsonProperty(value = "properties.operationMode")
    private ImportExportOperationMode operationMode;

    /**
     * The type of the storage key to use. Possible values include:
     * 'StorageAccessKey', 'SharedAccessKey'.
     */
    @JsonProperty(value = "properties.storageKeyType")
    private StorageKeyType storageKeyType;

    /**
     * The storage key to use.
     */
    @JsonProperty(value = "properties.storageKey")
    private String storageKey;

    /**
     * The storage uri to use.
     */
    @JsonProperty(value = "properties.storageUri")
    private String storageUri;

    /**
     * The name of the SQL administrator.
     */
    @JsonProperty(value = "properties.administratorLogin")
    private String administratorLogin;

    /**
     * The password of the SQL administrator.
     */
    @JsonProperty(value = "properties.administratorLoginPassword")
    private String administratorLoginPassword;

    /**
     * The authentication type.
     */
    @JsonProperty(value = "properties.authenticationType")
    private String authenticationType;

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name value.
     *
     * @param name the name value to set
     * @return the ImportExtensionRequestParametersInner object itself.
     */
    public ImportExtensionRequestParametersInner withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the type value.
     *
     * @return the type value
     */
    public String type() {
        return this.type;
    }

    /**
     * Set the type value.
     *
     * @param type the type value to set
     * @return the ImportExtensionRequestParametersInner object itself.
     */
    public ImportExtensionRequestParametersInner withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the operationMode value.
     *
     * @return the operationMode value
     */
    public ImportExportOperationMode operationMode() {
        return this.operationMode;
    }

    /**
     * Set the operationMode value.
     *
     * @param operationMode the operationMode value to set
     * @return the ImportExtensionRequestParametersInner object itself.
     */
    public ImportExtensionRequestParametersInner withOperationMode(ImportExportOperationMode operationMode) {
        this.operationMode = operationMode;
        return this;
    }

    /**
     * Get the storageKeyType value.
     *
     * @return the storageKeyType value
     */
    public StorageKeyType storageKeyType() {
        return this.storageKeyType;
    }

    /**
     * Set the storageKeyType value.
     *
     * @param storageKeyType the storageKeyType value to set
     * @return the ImportExtensionRequestParametersInner object itself.
     */
    public ImportExtensionRequestParametersInner withStorageKeyType(StorageKeyType storageKeyType) {
        this.storageKeyType = storageKeyType;
        return this;
    }

    /**
     * Get the storageKey value.
     *
     * @return the storageKey value
     */
    public String storageKey() {
        return this.storageKey;
    }

    /**
     * Set the storageKey value.
     *
     * @param storageKey the storageKey value to set
     * @return the ImportExtensionRequestParametersInner object itself.
     */
    public ImportExtensionRequestParametersInner withStorageKey(String storageKey) {
        this.storageKey = storageKey;
        return this;
    }

    /**
     * Get the storageUri value.
     *
     * @return the storageUri value
     */
    public String storageUri() {
        return this.storageUri;
    }

    /**
     * Set the storageUri value.
     *
     * @param storageUri the storageUri value to set
     * @return the ImportExtensionRequestParametersInner object itself.
     */
    public ImportExtensionRequestParametersInner withStorageUri(String storageUri) {
        this.storageUri = storageUri;
        return this;
    }

    /**
     * Get the administratorLogin value.
     *
     * @return the administratorLogin value
     */
    public String administratorLogin() {
        return this.administratorLogin;
    }

    /**
     * Set the administratorLogin value.
     *
     * @param administratorLogin the administratorLogin value to set
     * @return the ImportExtensionRequestParametersInner object itself.
     */
    public ImportExtensionRequestParametersInner withAdministratorLogin(String administratorLogin) {
        this.administratorLogin = administratorLogin;
        return this;
    }

    /**
     * Get the administratorLoginPassword value.
     *
     * @return the administratorLoginPassword value
     */
    public String administratorLoginPassword() {
        return this.administratorLoginPassword;
    }

    /**
     * Set the administratorLoginPassword value.
     *
     * @param administratorLoginPassword the administratorLoginPassword value to set
     * @return the ImportExtensionRequestParametersInner object itself.
     */
    public ImportExtensionRequestParametersInner withAdministratorLoginPassword(String administratorLoginPassword) {
        this.administratorLoginPassword = administratorLoginPassword;
        return this;
    }

    /**
     * Get the authenticationType value.
     *
     * @return the authenticationType value
     */
    public String authenticationType() {
        return this.authenticationType;
    }

    /**
     * Set the authenticationType value.
     *
     * @param authenticationType the authenticationType value to set
     * @return the ImportExtensionRequestParametersInner object itself.
     */
    public ImportExtensionRequestParametersInner withAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
        return this;
    }

}
