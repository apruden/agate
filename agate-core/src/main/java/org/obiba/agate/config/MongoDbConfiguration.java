package org.obiba.agate.config;

import java.util.Arrays;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.obiba.runtime.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.google.common.collect.Lists;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import static com.google.common.base.Strings.isNullOrEmpty;

@Configuration
@EnableMongoRepositories("org.obiba.agate.repository")
public class MongoDbConfiguration extends AbstractMongoConfiguration implements EnvironmentAware {

  private static final Logger log = LoggerFactory.getLogger(MongoDbConfiguration.class);

  private RelaxedPropertyResolver propertyResolver;

  @Inject
  private Environment env;

  @Override
  public void setEnvironment(Environment environment) {
    propertyResolver = new RelaxedPropertyResolver(environment, "mongodb.");
  }

  @Override
  protected String getDatabaseName() {
    return propertyResolver.getProperty("databaseName");
  }

  @Override
  public Mongo mongo() throws Exception {
    log.debug("Configuring MongoDB");
    if(isNullOrEmpty(propertyResolver.getProperty("url")) &&
        isNullOrEmpty(propertyResolver.getProperty("databaseName"))) {
      log.error("Your MongoDB configuration is incorrect! The application cannot start. " +
          "Please check your Spring profile, current profiles are: {}", Arrays.toString(env.getActiveProfiles()));
      throw new ApplicationContextException("MongoDB is not configured correctly");
    }
    return new MongoClient(propertyResolver.getProperty("url"));
  }

  @Override
  public CustomConversions customConversions() {
    return new CustomConversions(
      Lists.newArrayList(new VersionReadConverter()));
  }

  @Override
  @Nullable
  protected UserCredentials getUserCredentials() {
    String username = propertyResolver.getProperty("username");
    String password = propertyResolver.getProperty("password");
    return isNullOrEmpty(username) || isNullOrEmpty(password) ? null : new UserCredentials(username, password);
  }

  public static class VersionReadConverter implements Converter<DBObject, Version> {

    @Override
    public Version convert(DBObject dbObject) {
      return new Version((int)dbObject.get("major"), (int)dbObject.get("minor"), (int)dbObject.get("micro"), (String)dbObject.get("qualifier"));
    }
  }

}

