/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import retrofit2.Retrofit;
import com.google.common.reflect.TypeToken;
import com.microsoft.azure.AzureServiceResponseBuilder;
import com.microsoft.azure.CloudException;
import com.microsoft.rest.ServiceCall;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceResponse;
import java.io.IOException;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.Response;
import rx.functions.Func1;
import rx.Observable;

/**
 * An instance of this class provides access to all the operations defined
 * in ServerUsages.
 */
public final class ServerUsagesInner {
    /** The Retrofit service to perform REST calls. */
    private ServerUsagesService service;
    /** The service client containing this operation class. */
    private SqlManagementClientImpl client;

    /**
     * Initializes an instance of ServerUsagesInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public ServerUsagesInner(Retrofit retrofit, SqlManagementClientImpl client) {
        this.service = retrofit.create(ServerUsagesService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for ServerUsages to be
     * used by Retrofit to perform actually REST calls.
     */
    interface ServerUsagesService {
        @Headers("Content-Type: application/json; charset=utf-8")
        @GET("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql/servers/{serverName}/usages")
        Observable<Response<ResponseBody>> list(@Path("subscriptionId") String subscriptionId, @Path("resourceGroupName") String resourceGroupName, @Path("serverName") String serverName, @Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * Returns information about Azure SQL Server usage.
     *
     * @param resourceGroupName The name of the Resource Group to which the resource belongs.
     * @param serverName The name of the Azure SQL Server
     * @return the List&lt;ServerMetricInner&gt; object if successful.
     */
    public List<ServerMetricInner> list(String resourceGroupName, String serverName) {
        return listWithServiceResponseAsync(resourceGroupName, serverName).toBlocking().single().getBody();
    }

    /**
     * Returns information about Azure SQL Server usage.
     *
     * @param resourceGroupName The name of the Resource Group to which the resource belongs.
     * @param serverName The name of the Azure SQL Server
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @return the {@link ServiceCall} object
     */
    public ServiceCall<List<ServerMetricInner>> listAsync(String resourceGroupName, String serverName, final ServiceCallback<List<ServerMetricInner>> serviceCallback) {
        return ServiceCall.create(listWithServiceResponseAsync(resourceGroupName, serverName), serviceCallback);
    }

    /**
     * Returns information about Azure SQL Server usage.
     *
     * @param resourceGroupName The name of the Resource Group to which the resource belongs.
     * @param serverName The name of the Azure SQL Server
     * @return the observable to the List&lt;ServerMetricInner&gt; object
     */
    public Observable<List<ServerMetricInner>> listAsync(String resourceGroupName, String serverName) {
        return listWithServiceResponseAsync(resourceGroupName, serverName).map(new Func1<ServiceResponse<List<ServerMetricInner>>, List<ServerMetricInner>>() {
            @Override
            public List<ServerMetricInner> call(ServiceResponse<List<ServerMetricInner>> response) {
                return response.getBody();
            }
        });
    }

    /**
     * Returns information about Azure SQL Server usage.
     *
     * @param resourceGroupName The name of the Resource Group to which the resource belongs.
     * @param serverName The name of the Azure SQL Server
     * @return the observable to the List&lt;ServerMetricInner&gt; object
     */
    public Observable<ServiceResponse<List<ServerMetricInner>>> listWithServiceResponseAsync(String resourceGroupName, String serverName) {
        if (this.client.subscriptionId() == null) {
            throw new IllegalArgumentException("Parameter this.client.subscriptionId() is required and cannot be null.");
        }
        if (resourceGroupName == null) {
            throw new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null.");
        }
        if (serverName == null) {
            throw new IllegalArgumentException("Parameter serverName is required and cannot be null.");
        }
        final String apiVersion = "2014-04-01";
        return service.list(this.client.subscriptionId(), resourceGroupName, serverName, apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<List<ServerMetricInner>>>>() {
                @Override
                public Observable<ServiceResponse<List<ServerMetricInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl1<ServerMetricInner>> result = listDelegate(response);
                        ServiceResponse<List<ServerMetricInner>> clientResponse = new ServiceResponse<List<ServerMetricInner>>(result.getBody().getItems(), result.getResponse());
                        return Observable.just(clientResponse);
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl1<ServerMetricInner>> listDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return new AzureServiceResponseBuilder<PageImpl1<ServerMetricInner>, CloudException>(this.client.mapperAdapter())
                .register(200, new TypeToken<PageImpl1<ServerMetricInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

}
