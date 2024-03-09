package com.luv2code.ecommerce.config;

import com.luv2code.ecommerce.entity.Country;
import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.entity.ProductCategory;
import com.luv2code.ecommerce.entity.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
  //inorder to convert id 1 of category form static to dynamic
  // autowire EntityManger
  private EntityManager entityManager;
  @Autowired
  public MyDataRestConfig(EntityManager theEntityManager){
    entityManager = theEntityManager;
  }

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
    
    HttpMethod[] theUnsupportedActions = { HttpMethod.PUT , HttpMethod.POST  , HttpMethod.DELETE };

    //disable HTTP method for Classes : PUT POST and DELETE
    disableHttpMethod(Product.class, config, theUnsupportedActions);
    disableHttpMethod(ProductCategory.class, config, theUnsupportedActions);
    disableHttpMethod(Country.class, config, theUnsupportedActions);
    disableHttpMethod(State.class, config, theUnsupportedActions);

    // call an internal helper method to exposeIds
     exposeIds(config);
  }

  private void disableHttpMethod(Class theClass ,RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
    config.getExposureConfiguration()
            .forDomainType(theClass)
            .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
            .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
  }

  public void exposeIds(RepositoryRestConfiguration config){
    // expose entity ids
    // -get a list of all entity classes from the entity manager
    Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

    // - create an array of entity types
    List<Class> entityClasses = new ArrayList<>();

    // - get the entity types for the entities
    for (EntityType tempEntityType : entities){
      entityClasses.add(tempEntityType.getJavaType());
    }
    // - expose the entity ids for the array of entity/domain types
    Class[] domainTypes = entityClasses.toArray(new Class[0]);
    config.exposeIdsFor(domainTypes);

  }













}
