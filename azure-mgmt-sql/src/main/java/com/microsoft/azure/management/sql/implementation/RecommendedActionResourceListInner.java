/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import java.util.List;

/**
 * Response to a list request for the recommended action resource.
 */
public class RecommendedActionResourceListInner {
    /**
     * The value property.
     */
    private List<RecommendedActionResourceInner> value;

    /**
     * The nextLink property.
     */
    private String nextLink;

    /**
     * Get the value value.
     *
     * @return the value value
     */
    public List<RecommendedActionResourceInner> value() {
        return this.value;
    }

    /**
     * Set the value value.
     *
     * @param value the value value to set
     * @return the RecommendedActionResourceListInner object itself.
     */
    public RecommendedActionResourceListInner withValue(List<RecommendedActionResourceInner> value) {
        this.value = value;
        return this;
    }

    /**
     * Get the nextLink value.
     *
     * @return the nextLink value
     */
    public String nextLink() {
        return this.nextLink;
    }

    /**
     * Set the nextLink value.
     *
     * @param nextLink the nextLink value to set
     * @return the RecommendedActionResourceListInner object itself.
     */
    public RecommendedActionResourceListInner withNextLink(String nextLink) {
        this.nextLink = nextLink;
        return this;
    }

}
