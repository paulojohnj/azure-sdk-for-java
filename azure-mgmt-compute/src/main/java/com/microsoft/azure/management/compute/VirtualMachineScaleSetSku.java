package com.microsoft.azure.management.compute;

import com.microsoft.azure.management.apigeneration.Fluent;

/**
 * A type representing a SKU available for virtual machines in a scale set.
 */
@Fluent
public interface VirtualMachineScaleSetSku {
    /**
     * @return the type of resource the SKU applies to
     */
     String resourceType();

    /**
     * @return the SKU type
     */
    VirtualMachineScaleSetSkuTypes skuType();

    /**
     * @return available scaling information
     */
    VirtualMachineScaleSetSkuCapacity capacity();
}
