/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute;


/**
 * Describes a diagnostics profile.
 */
public class DiagnosticsProfile {
    /**
     * the boot diagnostics.
     */
    private BootDiagnostics bootDiagnostics;

    /**
     * Get the bootDiagnostics value.
     *
     * @return the bootDiagnostics value
     */
    public BootDiagnostics bootDiagnostics() {
        return this.bootDiagnostics;
    }

    /**
     * Set the bootDiagnostics value.
     *
     * @param bootDiagnostics the bootDiagnostics value to set
     * @return the DiagnosticsProfile object itself.
     */
    public DiagnosticsProfile withBootDiagnostics(BootDiagnostics bootDiagnostics) {
        this.bootDiagnostics = bootDiagnostics;
        return this;
    }

}
