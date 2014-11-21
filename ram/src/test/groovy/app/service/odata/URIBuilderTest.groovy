package app.service.odata;

import static org.junit.Assert.assertEquals

import org.apache.olingo.client.api.uri.QueryOption
import org.apache.olingo.client.api.uri.v4.URIBuilder
import org.apache.olingo.client.api.v4.ODataClient
import org.junit.Test

import framework.AbstractTest

public class URIBuilderTest extends AbstractTest {

  private static final String SERVICE_ROOT = "http://host/service";

  @Override
  protected ODataClient getClient() {
    return v4Client;
  }

  @Test
  public void expandWithOptions() throws URISyntaxException {
    final URI uri = getClient().newURIBuilder(SERVICE_ROOT).appendEntitySetSegment("Products").appendKeySegment(5).
            expandWithOptions("ProductDetails", new LinkedHashMap<QueryOption, Object>() {
              private static final long serialVersionUID = 3109256773218160485L;

              {
                put(QueryOption.EXPAND, "ProductInfo");
                put(QueryOption.SELECT, "Price");
              }
            }).expand("Orders", "Customers").build();

    assertEquals(new org.apache.http.client.utils.URIBuilder(SERVICE_ROOT + "/Products(5)").
            addParameter("$expand", "ProductDetails($expand=ProductInfo,$select=Price),Orders,Customers").build(), uri);
  }

  @Test
  public void expandWithLevels() throws URISyntaxException {
    final URI uri = getClient().newURIBuilder(SERVICE_ROOT).appendEntitySetSegment("Products").appendKeySegment(1).
            expandWithOptions("Customer", Collections.<QueryOption, Object>singletonMap(QueryOption.LEVELS, 4)).
            build();

    assertEquals(new org.apache.http.client.utils.URIBuilder(SERVICE_ROOT + "/Products(1)").
            addParameter("$expand", "Customer($levels=4)").build(), uri);
  }

  @Test
  public void count() throws URISyntaxException {
    URI uri = getClient().newURIBuilder(SERVICE_ROOT).appendEntitySetSegment("Products").count().build();

    assertEquals(new org.apache.http.client.utils.URIBuilder(SERVICE_ROOT + "/Products/$count").build(), uri);

    uri = getClient().newURIBuilder(SERVICE_ROOT).appendEntitySetSegment("Products").count(true).build();

    assertEquals(new org.apache.http.client.utils.URIBuilder(SERVICE_ROOT + "/Products").
            addParameter("$count", "true").build(), uri);
  }

  @Test
  public void singleton() throws URISyntaxException {
    final URIBuilder uriBuilder = getClient().newURIBuilder(SERVICE_ROOT).
            appendSingletonSegment("BestProductEverCreated");

    assertEquals(new org.apache.http.client.utils.URIBuilder(
            SERVICE_ROOT + "/BestProductEverCreated").build(), uriBuilder.build());
  }

  @Test
  public void entityId() throws URISyntaxException {
    final URIBuilder uriBuilder = getClient().newURIBuilder(SERVICE_ROOT).
            appendEntityIdSegment("Products(0)");

    assertEquals(new org.apache.http.client.utils.URIBuilder(
            SERVICE_ROOT + "/$entity").addParameter("$id", "Products(0)").build(), uriBuilder.build());
  }

  @Test
  public void boundAction() throws URISyntaxException {
    final URIBuilder uriBuilder = getClient().newURIBuilder(SERVICE_ROOT).
            appendEntitySetSegment("Categories").appendKeySegment(1).
            appendNavigationSegment("Products").appendNavigationSegment("Model").
            appendOperationCallSegment("AllOrders");

    assertEquals(new org.apache.http.client.utils.URIBuilder(
            SERVICE_ROOT + "/Categories(1)/Products/Model.AllOrders()").build(), uriBuilder.build());
  }

  @Test
  public void ref() throws URISyntaxException {
    URIBuilder uriBuilder = getClient().newURIBuilder(SERVICE_ROOT).
            appendEntitySetSegment("Categories").appendKeySegment(1).
            appendNavigationSegment("Products").appendRefSegment();

    assertEquals(new org.apache.http.client.utils.URIBuilder(
            SERVICE_ROOT + "/Categories(1)/Products/$ref").build(), uriBuilder.build());

    uriBuilder = getClient().newURIBuilder(SERVICE_ROOT).
            appendEntitySetSegment("Categories").appendKeySegment(1).
            appendNavigationSegment("Products").appendRefSegment().id("../../Products(0)");

    assertEquals(new org.apache.http.client.utils.URIBuilder(
            SERVICE_ROOT + "/Categories(1)/Products/$ref").addParameter("$id", "../../Products(0)").build(),
            uriBuilder.build());
  }

  @Test
  public void derived() throws URISyntaxException {
    final URIBuilder uriBuilder = getClient().newURIBuilder(SERVICE_ROOT).
            appendEntitySetSegment("Customers").appendDerivedEntityTypeSegment("Model.VipCustomer").appendKeySegment(1);

    assertEquals(new org.apache.http.client.utils.URIBuilder(
            SERVICE_ROOT + "/Customers/Model.VipCustomer(1)").build(), uriBuilder.build());
  }

  @Test
  public void crossjoin() throws URISyntaxException {
    final URIBuilder uriBuilder = getClient().newURIBuilder(SERVICE_ROOT).
            appendCrossjoinSegment("Products", "Sales");

    assertEquals(new org.apache.http.client.utils.URIBuilder(
            SERVICE_ROOT + "/$crossjoin(Products,Sales)").build(), uriBuilder.build());
  }

  @Test
  public void all() throws URISyntaxException {
    final URIBuilder uriBuilder = getClient().newURIBuilder(SERVICE_ROOT).appendAllSegment();

    assertEquals(new org.apache.http.client.utils.URIBuilder(
            SERVICE_ROOT + "/$all").build(), uriBuilder.build());
  }

  @Test
  public void search() throws URISyntaxException {
    final URIBuilder uriBuilder = getClient().newURIBuilder(SERVICE_ROOT).
            appendEntitySetSegment("Products").search("blue OR green");

    assertEquals(new org.apache.http.client.utils.URIBuilder(
            SERVICE_ROOT + "/Products").addParameter("$search", "blue OR green").build(), uriBuilder.build());
  }
}