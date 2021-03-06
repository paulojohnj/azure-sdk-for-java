/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.ResourceGroups;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.CreatableResourcesImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import com.microsoft.rest.ServiceCall;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceResponse;
import rx.Observable;
import rx.functions.Func1;

/**
 * The implementation for {@link ResourceGroups} and its parent interfaces.
 */
final class ResourceGroupsImpl
        extends CreatableResourcesImpl<ResourceGroup, ResourceGroupImpl, ResourceGroupInner>
        implements ResourceGroups {
    private final ResourceGroupsInner client;
    private final ResourceManagementClientImpl serviceClient;

    /**
     * Creates an instance of the implementation.
     *
     * @param serviceClient the inner resource management client
     */
    ResourceGroupsImpl(final ResourceManagementClientImpl serviceClient) {
        this.serviceClient = serviceClient;
        this.client = serviceClient.resourceGroups();
    }

    @Override
    public PagedList<ResourceGroup> list() {
        return wrapList(client.list());
    }

    @Override
    public PagedList<ResourceGroup> listByTag(String tagName, String tagValue) {
        return wrapList(client.list(Utils.createOdataFilterForTags(tagName, tagValue), null));
    }

    @Override
    public ResourceGroupImpl getByName(String name) {
        return wrapModel(client.get(name));
    }

    @Override
    public void deleteByName(String name) {
        deleteByNameAsync(name).toBlocking().subscribe();
    }

    @Override
    public ServiceCall<Void> deleteByNameAsync(String name, ServiceCallback<Void> callback) {
        return ServiceCall.create(client.deleteWithServiceResponseAsync(name), callback);
    }

    @Override
    public Observable<Void> deleteByNameAsync(String name) {
        return client.deleteAsync(name);
    }

    @Override
    public ResourceGroupImpl define(String name) {
        return wrapModel(name);
    }

    @Override
    public boolean checkExistence(String name) {
        return client.checkExistence(name);
    }

    @Override
    protected ResourceGroupImpl wrapModel(String name) {
        return new ResourceGroupImpl(
                new ResourceGroupInner().withName(name),
                serviceClient);
    }

    @Override
    protected ResourceGroupImpl wrapModel(ResourceGroupInner inner) {
        if (inner == null) {
            return null;
        }
        return new ResourceGroupImpl(inner, serviceClient);
    }

    @Override
    public void beginDeleteByName(String id) {
        beginDeleteByNameAsync(id).toBlocking().subscribe();
    }

    @Override
    public ServiceCall<Void> beginDeleteByNameAsync(String name, ServiceCallback<Void> callback) {
        return ServiceCall.create(beginDeleteByNameAsync(name)
                .flatMap(new Func1<Void, Observable<ServiceResponse<Void>>>() {
                    @Override
                    public Observable<ServiceResponse<Void>> call(Void aVoid) {
                        return null;
                    }
                }), callback);
    }

    @Override
    public Observable<Void> beginDeleteByNameAsync(String name) {
        return client.beginDeleteAsync(name);
    }

    @Override
    public Observable<Void> deleteByIdAsync(String id) {
        return deleteByNameAsync(ResourceUtils.nameFromResourceId(id));
    }
}
