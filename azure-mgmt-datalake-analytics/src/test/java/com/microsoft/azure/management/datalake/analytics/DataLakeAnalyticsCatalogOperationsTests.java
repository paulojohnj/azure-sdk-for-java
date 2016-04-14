package com.microsoft.azure.management.datalake.analytics;

import com.microsoft.azure.management.datalake.analytics.models.DataLakeAnalyticsAccount;
import com.microsoft.azure.management.datalake.analytics.models.DataLakeAnalyticsAccountProperties;
import com.microsoft.azure.management.datalake.analytics.models.DataLakeAnalyticsCatalogSecretCreateOrUpdateParameters;
import com.microsoft.azure.management.datalake.analytics.models.DataLakeStoreAccountInfo;
import com.microsoft.azure.management.datalake.analytics.models.USqlCredential;
import com.microsoft.azure.management.datalake.analytics.models.USqlDatabase;
import com.microsoft.azure.management.datalake.analytics.models.USqlProcedure;
import com.microsoft.azure.management.datalake.analytics.models.USqlSecret;
import com.microsoft.azure.management.datalake.analytics.models.USqlTable;
import com.microsoft.azure.management.datalake.analytics.models.USqlTableValuedFunction;
import com.microsoft.azure.management.datalake.analytics.models.USqlType;
import com.microsoft.azure.management.datalake.analytics.models.USqlView;
import com.microsoft.azure.management.datalake.store.models.DataLakeStoreAccount;
import com.microsoft.azure.management.resources.models.ResourceGroup;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataLakeAnalyticsCatalogOperationsTests extends DataLakeAnalyticsManagementTestBase {
    private static String rgName = generateName("javaadlarg");
    private static String location;
    private static String adlsAcct = generateName("javaadlsacct");
    private static String adlaAcct = generateName("javaadlaacct");

    // catalog names
    private static String dbName = generateName("testdb1");
    private static String tableName = generateName("testtable1");
    private static String tvfName = generateName("testtvf1");
    private static String procName = generateName("testproc1");
    private static String viewName = generateName("testview1");
    private static String credentialName = generateName("testcred1");
    private static String secretName = generateName("testsecret1");
    private static String secretPwd = generateName("testsecretpwd1");

    private static String catalogCreationScript = MessageFormat.format("\n" +
            "DROP DATABASE IF EXISTS {0}; CREATE DATABASE {0}; \n" +
            "//Create Table\n" +
            "CREATE TABLE {0}.dbo.{1}\n" +
            "(\n" +
            "        //Define schema of table\n" +
            "        UserId          int, \n" +
            "        Start           DateTime, \n" +
            "        Region          string, \n" +
            "        Query           string, \n" +
            "        Duration        int, \n" +
            "        Urls            string, \n" +
            "        ClickedUrls     string,\n" +
            "    INDEX idx1 //Name of index\n" +
            "    CLUSTERED (Region ASC) //Column to cluster by\n" +
            "    PARTITIONED BY HASH (Region) //Column to partition by\n" +
            ");\n" +
            "DROP FUNCTION IF EXISTS {0}.dbo.{2};\n" +
            "\n" +
            "//create table weblogs on space-delimited website log data\n" +
            "CREATE FUNCTION {0}.dbo.{2}()\n" +
            "RETURNS @result TABLE\n" +
            "(\n" +
            "    s_date DateTime,\n" +
            "    s_time string,\n" +
            "    s_sitename string,\n" +
            "    cs_method string, \n" +
            "    cs_uristem string,\n" +
            "    cs_uriquery string,\n" +
            "    s_port int,\n" +
            "    cs_username string, \n" +
            "    c_ip string,\n" +
            "    cs_useragent string,\n" +
            "    cs_cookie string,\n" +
            "    cs_referer string, \n" +
            "    cs_host string,\n" +
            "    sc_status int,\n" +
            "    sc_substatus int,\n" +
            "    sc_win32status int, \n" +
            "    sc_bytes int,\n" +
            "    cs_bytes int,\n" +
            "    s_timetaken int\n" +
            ")\n" +
            "AS\n" +
            "BEGIN\n" +
            "\n" +
            "    @result = EXTRACT\n" +
            "        s_date DateTime,\n" +
            "        s_time string,\n" +
            "        s_sitename string,\n" +
            "        cs_method string,\n" +
            "        cs_uristem string,\n" +
            "        cs_uriquery string,\n" +
            "        s_port int,\n" +
            "        cs_username string,\n" +
            "        c_ip string,\n" +
            "        cs_useragent string,\n" +
            "        cs_cookie string,\n" +
            "        cs_referer string,\n" +
            "        cs_host string,\n" +
            "        sc_status int,\n" +
            "        sc_substatus int,\n" +
            "        sc_win32status int,\n" +
            "        sc_bytes int,\n" +
            "        cs_bytes int,\n" +
            "        s_timetaken int\n" +
            "    FROM @\"/Samples/Data/WebLog.log\"\n" +
            "    USING Extractors.Text(delimiter:' ');\n" +
            "\n" +
            "RETURN;\n" +
            "END;\n" +
            "CREATE VIEW {0}.dbo.{3} \n" +
            "AS \n" +
            "    SELECT * FROM \n" +
            "    (\n" +
            "        VALUES(1,2),(2,4)\n" +
            "    ) \n" +
            "AS \n" +
            "T(a, b);\n" +
            "CREATE PROCEDURE {0}.dbo.{4}()\n" +
            "AS BEGIN\n" +
            "  CREATE VIEW {0}.dbo.{3} \n" +
            "  AS \n" +
            "    SELECT * FROM \n" +
            "    (\n" +
            "        VALUES(1,2),(2,4)\n" +
            "    ) \n" +
            "  AS \n" +
            "  T(a, b);\n" +
            "END;", dbName, tableName, tvfName, viewName, procName);

    @BeforeClass
    public static void setup() throws Exception {
        createClients();
        location = environmentLocation;
        ResourceGroup group = new ResourceGroup();
        group.setLocation(location);
        resourceManagementClient.getResourceGroupsOperations().createOrUpdate(rgName, group);
        // create storage and ADLS accounts, setting the accessKey
        DataLakeStoreAccount adlsAccount = new DataLakeStoreAccount();
        adlsAccount.setLocation(location);
        adlsAccount.setName(adlsAcct);
        dataLakeStoreAccountManagementClient.getAccountOperations().create(rgName, adlsAcct, adlsAccount);

        // Create the ADLA acct to use.
        DataLakeAnalyticsAccountProperties createProperties = new DataLakeAnalyticsAccountProperties();
        List<DataLakeStoreAccountInfo> adlsAccts = new ArrayList<DataLakeStoreAccountInfo>();
        DataLakeStoreAccountInfo adlsInfo = new DataLakeStoreAccountInfo();
        adlsInfo.setName(adlsAcct);
        adlsAccts.add(adlsInfo);

        createProperties.setDataLakeStoreAccounts(adlsAccts);
        createProperties.setDefaultDataLakeStoreAccount(adlsAcct);

        DataLakeAnalyticsAccount createParams = new DataLakeAnalyticsAccount();
        createParams.setLocation(location);
        createParams.setName(adlaAcct);
        createParams.setProperties(createProperties);
        dataLakeAnalyticsAccountManagementClient.getAccountOperations().create(rgName, adlaAcct, createParams);
        // Sleep for two minutes to ensure the account is totally provisioned.
        Thread.sleep(120000);

        // create the catalog
        runJobToCompletion(dataLakeAnalyticsJobManagementClient, adlaAcct, UUID.randomUUID(), catalogCreationScript);
    }

    @AfterClass
    public static void cleanup() throws Exception {
        // delete the ADLA account first
        try {
            dataLakeAnalyticsAccountManagementClient.getAccountOperations().delete(rgName, adlaAcct);
            resourceManagementClient.getResourceGroupsOperations().delete(rgName);
        }
        catch (Exception e) {
            // ignore failures during cleanup, as it is best effort
        }
    }
    @Test
    public void canGetCatalogItems() throws Exception {
        List<USqlDatabase> dbListResponse = dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().listDatabases(adlaAcct).getBody();
        Assert.assertTrue(dbListResponse.size() >= 1);

        // look for the DB we created
        boolean foundCatalogElement = false;
        for (USqlDatabase db: dbListResponse) {
            if (db.getName().equals(dbName)) {
                foundCatalogElement = true;
                break;
            }
        }
        Assert.assertTrue(foundCatalogElement);

        // Get the specific Database as well
        USqlDatabase dbGetResponse = dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().getDatabase(adlaAcct, dbName).getBody();

        Assert.assertEquals(dbName, dbGetResponse.getName());

        // Get the table list
        List<USqlTable> tableListResponse = dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().listTables(adlaAcct, dbName, "dbo").getBody();

        Assert.assertTrue(tableListResponse.size() >= 1);

        // look for the table we created
        foundCatalogElement = false;
        for (USqlTable table: tableListResponse) {
            if (table.getName().equals(tableName)) {
                foundCatalogElement = true;
                break;
            }
        }
        Assert.assertTrue(foundCatalogElement);

        // Get the specific table as well
        USqlTable tableGetResponse = dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().getTable(
                adlaAcct, dbName, "dbo", tableName).getBody();

        Assert.assertEquals(tableName, tableGetResponse.getName());

        // Get the TVF list
        List<USqlTableValuedFunction> tvfListResponse = dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().listTableValuedFunctions(adlaAcct, dbName, "dbo").getBody();

        Assert.assertTrue(tvfListResponse.size() >= 1);

        // look for the tvf we created
        foundCatalogElement = false;
        for (USqlTableValuedFunction tvf: tvfListResponse) {
            if (tvf.getName().equals(tvfName)) {
                foundCatalogElement = true;
                break;
            }
        }
        Assert.assertTrue(foundCatalogElement);

        // Get the specific TVF as well
        USqlTableValuedFunction tvfGetResponse = dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().getTableValuedFunction(
                adlaAcct, dbName, "dbo", tvfName).getBody();

        Assert.assertEquals(tvfName, tvfGetResponse.getName());

        // Get the View list
        List<USqlView> viewListResponse = dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().listViews(adlaAcct, dbName, "dbo").getBody();

        Assert.assertTrue(viewListResponse.size() >= 1);

        // look for the view we created
        foundCatalogElement = false;
        for (USqlView view: viewListResponse) {
            if (view.getName().equals(viewName)) {
                foundCatalogElement = true;
                break;
            }
        }
        Assert.assertTrue(foundCatalogElement);

        // Get the specific view as well
        USqlView viewGetResponse = dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().getView(
                adlaAcct, dbName, "dbo", viewName).getBody();

        Assert.assertEquals(viewName, viewGetResponse.getName());

        // Get the Procedure list
        List<USqlProcedure> procListResponse = dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().listProcedures(
                adlaAcct, dbName, "dbo").getBody();

        Assert.assertTrue(procListResponse.size() >= 1);

        // look for the procedure we created
        foundCatalogElement = false;
        for (USqlProcedure proc: procListResponse) {
            if (proc.getName().equals(procName)) {
                foundCatalogElement = true;
                break;
            }
        }
        Assert.assertTrue(foundCatalogElement);

        // Get the specific procedure as well
        USqlProcedure procGetResponse = dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().getProcedure(
                adlaAcct, dbName, "dbo", procName).getBody();

        Assert.assertEquals(procName, procGetResponse.getName());

        // Get all the types
        List<USqlType> typeGetResponse = dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().listTypes(
                adlaAcct, dbName, "dbo").getBody();


        Assert.assertNotNull(typeGetResponse);
        Assert.assertTrue(typeGetResponse.size() > 0);

        // Get all the types that are not complex
        USqlType filterOn = new USqlType();
        filterOn.setIsComplexType(false);
        typeGetResponse = dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().listTypes(
                adlaAcct, dbName, "dbo", filterOn, null, null, null, null, null, null).getBody();


        Assert.assertNotNull(typeGetResponse);
        Assert.assertTrue(typeGetResponse.size() > 0);
        foundCatalogElement = false;
        for (USqlType type: typeGetResponse) {
            if (type.getIsComplexType()) {
                foundCatalogElement = true;
                break;
            }
        }
        Assert.assertFalse(foundCatalogElement);
    }

    @Test
    public void  canCreateUpdateDeleteSecretsAndCredentials() throws Exception {
        // create the secret
        DataLakeAnalyticsCatalogSecretCreateOrUpdateParameters createParams = new DataLakeAnalyticsCatalogSecretCreateOrUpdateParameters();
        createParams.setPassword(secretPwd);
        createParams.setUri("https://adlasecrettest.contoso.com:443");
        USqlSecret secretCreateResponse = dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().createSecret(
                adlaAcct, dbName, secretName,
                createParams).getBody();
        try {

        }
        catch(Exception e) {

        }
        
        // Attempt to create the secret again, which should throw
        try {
            USqlSecret secondTry = dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().createSecret(
                adlaAcct,
                dbName, secretName,
                createParams).getBody();
            // should never make it here
            Assert.assertTrue(false);
        }
        catch(Exception e) {
            // expected.
        }

        // Get the secret and ensure the response contains a date.
        USqlSecret secretGetResponse = dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().getSecret(
                adlaAcct, dbName, secretName).getBody();

        Assert.assertNotNull(secretGetResponse);
        Assert.assertNotNull(secretGetResponse.getCreationTime());

        // Create a credential with the secret
        String credentialCreationScript =
                String.format("USE %s; CREATE CREDENTIAL %s WITH USER_NAME = \"scope@rkm4grspxa\", IDENTITY = \"%s\";",
                        dbName, credentialName, secretName);

        runJobToCompletion(dataLakeAnalyticsJobManagementClient, adlaAcct, UUID.randomUUID(), credentialCreationScript);

        // Get the Credential list
        List<USqlCredential> credListResponse = dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().listCredentials(
                adlaAcct, dbName).getBody();
        Assert.assertTrue(credListResponse.size() >= 1);

        // look for the credential we created
        boolean foundCatalogElement = false;
        for (USqlCredential cred: credListResponse) {
            if (cred.getName().equals(credentialName)) {
                foundCatalogElement = true;
                break;
            }
        }
        Assert.assertTrue(foundCatalogElement);

        // Get the specific credential as well
        USqlCredential credGetResponse = dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().getCredential(
                adlaAcct, dbName, credentialName).getBody();
        Assert.assertEquals(credentialName, credGetResponse.getName());

        // Drop the credential (to enable secret deletion)
        String credentialDropScript =
                String.format("USE %s; DROP CREDENTIAL %s;", dbName, credentialName);
        runJobToCompletion(dataLakeAnalyticsJobManagementClient,
                adlaAcct, UUID.randomUUID(),
                credentialDropScript);

        // Delete the secret
        dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().deleteSecret(
                adlaAcct, dbName, secretName);

        // Try to get the secret which should throw
        try {
            dataLakeAnalyticsCatalogManagementClient.getCatalogOperations().getSecret(
                    adlaAcct, dbName, secretName);

            // should never make it here
            Assert.assertTrue("Was able to retrieve a deleted secret", false);
        }
        catch (Exception e) {
            // expected
        }

    }
}
